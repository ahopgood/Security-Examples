$(document).ready(function() {

    createThumbnails();
});
var host = "http://localhost:8080/"

function submitComment(event) {
    var bikeId = event.data;
//    console.log("I'm a submit comment click event for " + bikeId);
    var commentContent = $("#commentTextArea").val();
//    console.log(commentContent);
    jQuery.ajax({
            url: host + "bikes/" + bikeId + "/comments/",
            type: "POST",
            contentType: "application/json",
            dataType: "json",
            data: "{\"comment\":\"" + commentContent + "\"}",
            statusCode: {
                200: function() {
//                    console.log("Call completed for " + bikeId);
                    var loadEvent = jQuery.Event( "madeup" );
                    loadEvent.data = bikeId;
                    $("#commentSubmit").off("click");
                    populateDetails(loadEvent);
                }
            }
        });
}

function populateDetails(event) {
//    console.log("Details for:");
//    console.log(event);
//    console.log(event.data);
    var bikeId = event.data;
    jQuery.ajax({
        url: host + "bikes/detail/" + bikeId,
        type: "GET",
        dataType: "json",
    }).done(function (json) {
//        console.log(json);
        var details = json;
        $("#details").removeClass("d-none");
        $("#details-template #details-header").html(details.title);
        $("#details-template div img").attr("src", host + details.fullImageUrl);
        $("#details-template .card-text").html(details.bikeDescription);

        $("ul.list-group").empty();
        var commentTemplate = '<li class="list-group-item"></li>';
        jQuery.each( details.comments, function( index, comment ){
            var commentTemplate = comment;
//            console.log(comment);
            $("ul.list-group").append("<li class=\"list-group-item\"></li>")
            $("ul.list-group li.list-group-item:last").html(comment.comment);
        });
    }); //ajax end

    //Setup comment submit actions
    $("#commentSubmit").click(bikeId, submitComment);
}

function createThumbnails() {
//    console.log($("body"));

    jQuery.ajax({
        url: host + "bikes/thumbnails",
        type: "GET",
        dataType: "json",
    }).done(function (json) {
        jQuery.each( json, function( index, json ){
//            console.log(json);
            var thumbnail = json;
            var bikeId = thumbnail.bikeId;
            var template = $("#template").clone().attr("id", bikeId).toggleClass("d-none");
            $("body div.container .card-group.row").append(template);
            $("#" + bikeId + " img").attr("src", host + thumbnail.thumbnailImageUrl);
            $("#" + bikeId + " div.card-body h5").html(thumbnail.title);
            $("#" + bikeId + " div.card-body a").attr("href", "#bike-details")
            $("#" + bikeId + " div.card-body a").click(bikeId, populateDetails);
        }); //end each
    }); //ajax end
}

