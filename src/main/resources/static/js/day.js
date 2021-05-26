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

    // $(".date").text(monthsFull[month] + " " + day + ", " + year);
}

function loadTasksOnDay(year, month, day) {
    return $.ajax({
        method: "GET",
        url: `/api/tasks/list/day?y=${year}&m=${month + 1}&d=${day}`,
        dataType: "json",
        success: function (result) {
            window.countTasks = result;
        },
        error: function (jqXHR) {
            console.log(jqXHR);
        }
    });
}