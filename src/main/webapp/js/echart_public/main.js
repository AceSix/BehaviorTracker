/**
 * Created by Kotomi on 2017/7/23.
 */
//the main shop
var origin = "";
/** left-up-map */
var dom_map = document.getElementById("left-up-map");
var myChart_map = echarts.init(dom_map);
var app_map = {};
var option_map = null;
var converteData_map = function (geodata) {
    var res = [];
    var map_center = [];
    for (var i = 0; i < geodata.length; i++) {
        if(geodata[i].destination == origin) {
            geodata[i].count=50;
            map_center = [geodata[i].longitude, geodata[i].latitude];
        }
        if(geodata[i].count < 10) continue;
        res.push({
            shopid: geodata[i].destination,
            name:  geodata[i].shopname,
            cate: geodata[i].cate,
            value: [geodata[i].longitude, geodata[i].latitude,geodata[i].count]
        });
    }
    return [res,map_center];
};

/** left-up-time */
var dom_lefttoptime = document.getElementById("left-up-time");
var myChart_lefttoptime= echarts.init(dom_lefttoptime);
var app_lefttoptime = {};
var option_lefttoptime = null;
var converteData_lefttoptime = function( data ){
    var res1 = [];
    var res2 = [];
    var res3 = [];
    var category = '';
    for( var i = 0 ; i < data.length ; i++ ){
        if(typeof(data[i].distance) === "undefine") data[i].distance=0;
        if(data[i].shopid != origin && data[i].cate == "奶茶") {
            res1.push([toDate(data[i].mintime), toDate(data[i].maxtime), data[i].count , data[i].distance]);
        }else{
            if(data[i].shopid == origin) {
                res2.push([toDate(data[i].mintime), toDate(data[i].maxtime), 60, data[i] .distance]);
            }else{
                res3.push([toDate(data[i].mintime), toDate(data[i].maxtime), data[i].count , data[i].distance]);
            }
        }
    }
    return [res1 , res2 , res3];
}
/** right-scatter */
var dom_rightscatter = document.getElementById("right-scatter");
var myChart_rightscatter = echarts.init(dom_rightscatter);
var app_rightscatter = {};
var option_rightscatter = null;
app_rightscatter.title = 'title';
var convertData_rightscatter = function(data){
    var res1 = [];
    var res2 = [];
    for(var i  = 0 ; i < data.length ; i++){
        if(data[i].count < 10) continue;
        if(data[i].destination != origin){
            if(data[i].cate == "奶茶"){
                res1.push([data[i].distance,data[i].count,data[i].shopname,data[i].destination]);
            }else{
                res2.push([data[i].distance,data[i].count,data[i].shopname,data[i].destination])
            }
        }
    }
    return [res1,res2];
}


function search(){
    //scatter plot : show overlap overview
    origin = $("#origin").val();
    $.ajax({
        type: "post",
        url: server_context + "/shopcompetition?shopid="+origin,
        dataType: "json",
        error: function () {
            console.log("erro");
        },
        success: function (competitors) {
            console.log("success");
            drawScatter(competitors,origin);
        }
    })

//    scatter map : show the location
    $.ajax({
        type: "post",
        url: server_context + "/shoplocation?shopid="+ origin,
        dataType: "json",
        error: function () {
            console.log("erro");
        },
        success: function (geodata) {
            option_map.series[0].data = converteData_map(geodata)[0];
            option_map.bmap.center = converteData_map(geodata)[1];
            // option_map.series[0].itemStyle.normal.color =
            myChart_map.setOption(option_map);
        }
    });

//  time parallel : show the start and end time of shops
    $.ajax({
        type: "post",
        url: server_context + "/shopdetail?shopid=" + origin,
        dataType: "json",
        error: function () {
            console.log("erro");
        },
        success: function (data) {
            console.log("success");
            var convertedData = converteData_lefttoptime(data) ;
            drawParallel( convertedData );
        }});

}