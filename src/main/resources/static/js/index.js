let url = new URL(document.location);
let searchParams = url.searchParams;

let paramYear = searchParams.get("y");
let paramMonth = searchParams.get("m") - 1;

let countWeeks = 0;

$(document).ready(function () {
    window.calendar = $("#calendar");

    if (!searchParams.has("y")) {
        renderPage(new Date());
    } else if (searchParams.has("m")) {
        renderPage(new Date(Number(paramYear), Number(paramMonth)));
    }
});

function renderPage(date) {
    window.currentDate = new Date();

    let year = date.getFullYear();
    window.month = date.getMonth();
    if (month === currentDate.getMonth()) {
        date.setDate(1);
    }
    let monthsFull = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
    
    $(".date").text(monthsFull[month] + ", " + year);

    let prevMonth = new Date(year, month - 1);
    let nextMonth = new Date(year, month + 1);
    $("#prev_month").attr("href", `/?y=${prevMonth.getFullYear()}&m=${prevMonth.getMonth() + 1}`);
    $("#next_month").attr("href", `/?y=${nextMonth.getFullYear()}&m=${nextMonth.getMonth() + 1}`);

    loadCountTasks(year, month)
        .then(function () {
            createCalendar(date, month);
        });
}

function createCalendar(date) {
    createFirstWeekWithPrevMonth(date)
        .then(function () {
            createOtherWeeks(date);
        });
}

function getDay(date) {
    let dayOfWeek = date.getDay();
    if (dayOfWeek === 0) dayOfWeek = 7;
    return dayOfWeek - 1;
}

function createFirstWeekWithPrevMonth(date) {
    let d = $.Deferred();
    let firstDayOfWeek = getDay(date);

    if (firstDayOfWeek !== 0) {
        let week = $("<tr></tr>");

        let daysPrevMonth = date;
        daysPrevMonth.setDate(daysPrevMonth.getDate() - firstDayOfWeek);

        for (let i = 0; i < firstDayOfWeek; i++) {
            addDayInWeek(daysPrevMonth, week);
            daysPrevMonth.setDate(daysPrevMonth.getDate() + 1);
        }

        while (getDay(date) !== 0) {
            addDayInWeek(date, week);
            date.setDate(date.getDate() + 1);
        }

        addWeekInCalendar(week);
    }
    d.resolve();
    return d.promise();
}

function createOtherWeeks(date) {
    let d = $.Deferred();
    let week;

    while (countWeeks < 6) {
        week = $("<tr></tr>");
        for (let i = getDay(date); i < 7; i++) {
            addDayInWeek(date, week);
            date.setDate(date.getDate() + 1);
        }
        addWeekInCalendar(week);
    }
    d.resolve();
    return d.promise();
}

function addWeekInCalendar(week) {
    window.calendar.append(week);
    countWeeks++;
}

function addDayInWeek(date, week) {
    let dayElem = $(`<td><a class="stretched-link" href="/?y=${date.getFullYear()}&m=${date.getMonth() + 1}&d=${date.getDate()}">${date.getDate()}</a></td>`);

    if (getDay(date) === 5 || getDay(date) === 6) {
        dayElem.addClass("weekend");
    }
    if (dateCompare(date, window.currentDate)) {
        dayElem.addClass("today");
    }
    if (date.getMonth() !== window.month) {
        dayElem.addClass("another-month");
    } else {
        for (let i = 0; i < window.countTasks.length; i++) {
            let dayWithCount = new Date(window.countTasks[i].date);
            if (dateCompare(date, dayWithCount)) {
                dayElem.append(`<div class="count">${window.countTasks[i].count}</div>`);
                window.countTasks.shift();
                break;
            }
        }
    }

    week.append(dayElem);
}

function loadCountTasks(year, month) {
    return $.ajax({
        method: "GET",
        url: `/api/tasks/count/month?y=${year}&m=${month + 1}`,
        dataType: "json",
        success: function (result) {
            window.countTasks = result;
        },
        error: function (jqXHR) {
            console.log(jqXHR);
        }
    });
}

function dateCompare(date1, date2) {
    return (date1.getFullYear() === date2.getFullYear() &&
        date1.getMonth() === date2.getMonth() &&
        date1.getDate() === date2.getDate());
}