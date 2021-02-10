$(document).ready(function() {

    createThumbnails();
});
var host = "http://localhost:8080/"

function submitComment(event) {
    var bikeId = event.data;
    var commentContent = $("#commentTextArea").val();
    console.log("I'm a submit comment click event for " + bikeId + " with comment:[" + commentContent + "]");
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
                    $("#commentTextArea").val("");
                    populateDetails(loadEvent);
                }
            }
        }).fail(function(jqXHR, textStatus, errorThrown) {
             console.log("Uh oh we have a failure in submitting comments: " + jqXHR.textStatus);
             console.log(errorThrown);
             console.log(jqXHR.responseJSON);
        });
}

function populateDetails(event) {
//    console.log("Details for:");
//    console.log(event);
//    console.log(event.data);
    var bikeId = event.data;
    //Setup comment submit actions
    $("#commentSubmit").off("click");
    event.stopPropagation();
    $("#commentSubmit").click(bikeId, submitComment);

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
        console.log(json.comments);
        jQuery.each( details.comments, function( index, comment ){
            var commentTemplate = comment;
            console.log("Comment " + index + ": ");
            console.log(comment);
            $("ul.list-group").append("<li class=\"list-group-item\"></li>")
            $("ul.list-group li.list-group-item:last").html(comment.comment);
        });
    })
    .fail(function(jqXHR) {
        console.log("Uh oh we have a failure populating the details: " + jqXHR.status);
    }); //ajax end
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
            $("#" + bikeId + " div.card-footer a").click(bikeId, populateDetails);
        }); //end each
    }); //ajax end
}

