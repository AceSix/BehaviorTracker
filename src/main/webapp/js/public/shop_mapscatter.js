var widthS = $("#shop_mapscatter").width();
var heightS = 509;
var margin  = 20;

var origin = 13837617;
//adjust map
var mapscale = 18400;
var mapcenter = [121.4648, 31.2891] // [lng, lat]

drawmap(mapscale, mapcenter);

function drawmap(mapscale, mapcenter){
    //version1 absolute location
    //version2 relative location


    var projection = d3.geo.azimuthal()
        .mode("equidistant")
        .origin(mapcenter)
        //40.0654569182409','longitude':'116.4033058302324 [121.4648,31.2891] [116.404, 39.915]
        .scale(mapscale)//2589000
        .translate([170, 180]);

    var path = d3.geo.path()
        .projection(projection);

    d3.select("#shop_mapscatter").select("svg").remove();
    var svg = d3.select("#shop_mapscatter").append("svg")
        .attr("width", widthS - margin)
        .attr("height", heightS - margin * 1.8);

    var areas = svg.append("svg:g")
        .attr("id", "areas");

    d3.json("../data/map/shanghai.json", function(collection) {
        areas.selectAll("path")
            .data(collection.features)
            .enter().append("svg:path")
            .attr("class","shopmap")
            .attr("d", path)
            .attr("fill", "#00688B")
            .append("svg:title")
            .text(function (d) {
                return d.properties.name
            })
    })

    //add shops
    $.ajax({
        type: "post",
        url: server_context+"/competitor?shopid=" + origin,
        dataType: "json",
        error: function(){console.log("erro");},
        success: function(competitors){

            var center = svg.append("svg:g")
                .attr("id", "center");
            var circles = svg.append("svg:g")
                .attr("id", "competitor");

            var max = 0;
            competitors.forEach(function(d){
                if(d.destination == origin)  return;
               if(d.num > max ){
                   max = d.num;
               }
           })



            competitors.forEach(function(d){

                if(d.destination == origin) {
                    center.attr("transform", "translate(" + projection([d.lng,d.lat])[0] + "," + projection([d.lng,d.lat])[1] + ")")
                        .append("polygon")
                        .attr("id", d.destination)
                        .attr("points","20,0 32,36 2,12 38,12 8,36")
                        .style("fill", "red")
                        .attr("stroke", "red")
                        .on("click", onclick);

                }else{
                    circles.append("svg:circle")
                        .attr("id", d.destination)
                        .attr("class", "shop")
                        .attr("cx", projection([d.lng,d.lat])[0])
                        .attr("cy", projection([d.lng,d.lat])[1])
                        .attr("r", 10 - 9 * (max - d.num) / (max - 1))

                        .on("click", onclick);

                }
            })


            function onclick(){
                var shopid  = this.id;
                competitors.forEach(function(d){
                    if(d.destination == shopid){
                        mapscale = mapscale * 5;
                        console.log(mapscale);
                        drawmap(mapscale, [d.lng ,d.lat] );
                    }
                })

            }
        }
    })

};

$(function() {
    $("#zoomout").click(function(){
        mapscale = 18400 ;
        console.log(mapscale);
       drawmap(mapscale, mapcenter);
    });
});


$(function() {
    $("#zoomin").click(function(){
        mapscale = mapscale * 5;
        console.log(mapscale);
        drawmap(mapscale, mapcenter);
    });
});


