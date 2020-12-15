$(document).ready(function() {

    createThumbnails();
});
var host = "http://localhost:8080/"
function populateDetails(event) {
    console.log("Details for:");
    console.log(event);
    console.log(event.data);
    jQuery.ajax({
        url: host + "bikes/detail/" + event.data,
        type: "GET",
        dataType: "json",
    }).done(function (json) {
        console.log(json);
        var details = json;
        $("#details-template #details-header").html(details.title);
        $("#details-template div img").attr("src", host + details.fullImageUrl);
        $("#details-template .card-text").html(details.bikeDescription);

        $("ul.list-group").empty();
        var commentTemplate = '<li class="list-group-item"></li>';
        jQuery.each( details.comments, function( index, comment ){
            var commentTemplate = comment;
            console.log(comment);
            $("ul.list-group").append("<li class=\"list-group-item\"></li>")
            $("ul.list-group li.list-group-item:last").html(comment.comment);
        });
    }); //ajax end
}

function createThumbnails() {
    var host = "http://localhost:8080/"
//    console.log($("body"));

    jQuery.ajax({
        url: host + "bikes/thumbnails",
        type: "GET",
        dataType: "json",
    }).done(function (json) {
        jQuery.each( json, function( index, json ){
            console.log(json);
            var thumbnail = json;
            var bikeId = thumbnail.bikeId;
            var template = $("#template").clone().attr("id", bikeId).toggleClass("invisible")
            $("body div.container .card-group").append(template);
            $("#" + bikeId + " img").attr("src", host + thumbnail.thumbnailImageUrl);
            $("#" + bikeId + " div.card-body h5").html(thumbnail.title);
            $("#" + bikeId + " div.card-body a").attr("href", "#bike-details")
            $("#" + bikeId + " div.card-body a").click(bikeId, populateDetails);
        }); //end each
    }); //ajax end
}

