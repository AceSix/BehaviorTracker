//the main shop
var origin = "65480779";

/* left-scatter */

$.ajax({
    type: "post",
    url: server_context + "/shopcompetition?shopid="+origin,
    dataType: "json",
    error: function () {
        console.log("search erro");
    },
    success: function (competitors) {
        console.log("search success");
        drawScatter(competitors,origin);
    }
})


/* right-map */
$.ajax({
    type: "post",
    url: server_context + "/shoplocation?shopid="+origin,
    dataType: "json",
    error: function () {
        console.log("erro");
    },
    success: function (geodata) {
        console.log( "success" );
        drawmap(geodata);
    }
});


