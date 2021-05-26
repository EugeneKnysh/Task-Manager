let url = new URL(document.location);
let searchParams = url.searchParams;

let paramYear = searchParams.get("y");
let paramMonth = searchParams.get("m") - 1;
let paramDay = searchParams.get("d");

$(document).ready(function () {
    renderPage(new Date(Number(paramYear), Number(paramMonth), Number(paramDay)));
});

function renderPage(date) {
    let year = date.getFullYear();
    let month = date.getMonth();
    let day = date.getDate();
    let monthsFull = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];

    $(".date").html(`<a href="/?y=${year}&m=${month + 1}">${monthsFull[month]}</a> ${day}, ${year}`);

    loadTasksOnDay(year, month, day)
        .then(function (result) {
            return insertTasksOnPage(result, $(".tasks"));
        })
        .then(function () {
            $(".delete-button").click(function () {
                deleteTask(this.id);
            });
        });

    $(".add-button").click(function () {
        addTask(date, $(".time-select").val(), $("#text").val());
    });
}

function loadTasksOnDay(year, month, day) {
    return $.ajax({
        method: "GET",
        url: `/api/tasks/list/day?y=${year}&m=${month + 1}&d=${day}`,
        dataType: "json",
        error: function (jqXHR) {
            console.log(jqXHR);
        }
    });
}

function insertTasksOnPage(tasks, element) {
    let d = $.Deferred();
    $.each(tasks, function (index, value) {
        let task = $(`<div class="task">`)
            .append(`<div class="time">${value.time.substr(0, 5)}</div>`)
            .append(`<div class="text-task">${value.text}</div>`)
            .append(`<button id="${value.id}" type="button" class="btn btn-outline-danger delete-button">X</button>`);

        element.append(task);
    });
    d.resolve();
    return d.promise();
}

function deleteTask(id) {
    $.ajax({
        method: "DELETE",
        url: `/api/task/delete?id=${id}`,
        success: function () {
            window.location.reload();
        },
        error: function (jqXHR) {
            console.log(jqXHR);
        }
    });
}

function addTask(date, time, text) {
    let strMonth = ((date.getMonth() + 1) < 10) ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1);
    let strDate = `${date.getFullYear()}-${strMonth}-${date.getDate()}`;

    let taskDTO = {
        date: strDate,
        time: time,
        text: text
    }
    console.log(taskDTO);

    $.ajax({
        method: "POST",
        url: "/api/task/add",
        data: JSON.stringify(taskDTO),
        contentType: "application/json",
        success: function () {
            window.location.reload();
        },
        error: function (jqXHR) {
            console.log(jqXHR);
        }
    });
}