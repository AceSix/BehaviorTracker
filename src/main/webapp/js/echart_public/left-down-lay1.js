/**
 * Created by Kotomi on 2017/7/13.
 */
var width_lay1 = 380 , height_lay1 = 380 ;
var svg_lay1 = d3.select("#left-down-lay1").append("svg")
    .attr("width",width_lay1)
    .attr("height",height_lay1);
var dw=width_lay1/2;
var dh=height_lay1/2;

//    中心
var center=svg_lay1.append("g");
var outerRadius = 160;
var pie1 = d3.pie().value(function(d) { return d.num; })
    .startAngle(Math.PI)
    .endAngle(Math.PI*2)
    .padAngle(0.08);
var pie2= d3.pie().value(function(d) { return d.num; })
    .startAngle(0)
    .endAngle(Math.PI)
    .padAngle(0.05);
var shopid="76383406";
$.ajax({
    type: "post",
    url: server_context+"/shopcompetition?shopid="+shopid,
    dataType: "json",
    error: function(){console.log("erro");},
    success:function(data) {
        drawCooperationCircle(data.cooperation,outerRadius,center,dw,dh);
        drawCompetitionCircle(data.competition,outerRadius,center,dw,dh);
        shopMouseover(data.cooperation,data.competition);
    }});

function drawCompetitionCircle(data,outerRadius,center,dw,dh){
    pie1(data).forEach(function(d){
        var x1=outerRadius*Math.cos(d.startAngle);
        var y1=outerRadius*Math.sin(d.startAngle);
        var x2=outerRadius*Math.cos(d.endAngle-d.padAngle);
        var y2=outerRadius*Math.sin(d.endAngle-d.padAngle);
        var t1=center.append("g").attr("id","center"+d.data.shopid);
        t1.append("path")
            .attr("transform","translate("+dw+","+dh+")")
            .attr("d","M"+x1+","+y1+"A"+outerRadius+","+outerRadius+",0,0,1,"+x2+","+y2+"Q-10,"+(outerRadius*(-0.8))+",0,"+(outerRadius*(-0.3))+"Q-10,"+(outerRadius*(-0.8))+","+x1+","+y1)
            .attr("stroke",fillCircleColor(d.data.cateid))
            .attr("fill",fillCircleColor(d.data.cateid));
        t1.append("title")
            .text(d.data.shopname);
    })
}
function drawCooperationCircle(data,outerRadius,center,dw,dh){
    pie2(data).forEach(function(d){
        var x1=outerRadius*Math.cos(d.startAngle);
        var y1=outerRadius*Math.sin(d.startAngle);
        var x2=outerRadius*Math.cos(d.endAngle-d.padAngle);
        var y2=outerRadius*Math.sin(d.endAngle-d.padAngle);
        var t2=center.append("g").attr("id","center"+d.data.shopid);
        t2.append("path")
            .attr("transform","translate("+dw+","+dh+")")
            .attr("d","M"+x1+","+y1+"A"+outerRadius+","+outerRadius+",0,0,1,"+x2+","+y2+"Q-10,"+(outerRadius*(0.8))+",0,"+(outerRadius*(0.3))+"Q-10,"+(outerRadius*(0.8))+","+x1+","+y1)                .attr("stroke",fillCircleColor(d.data.cateid))
            .attr("fill",fillCircleColor(d.data.cateid));
        t2.append("title")
            .text(d.data.shopname);
    })
}

function shopMouseover(cooperation,competition){
    //        店铺的竞争网络；后台交互数据 x2,y2
    competition.forEach(function(d){
        d3.select("#center"+d.shopid)
            .on("mouseover",function(){
                var x1=$("#dot"+d.shopid).find("circle").attr("cx");
                var y1=$("#dot"+d.shopid).find("circle").attr("cy");
                var x2=$("#dot21270425").find("circle").attr("cx");
                var y2=$("#dot21270425").find("circle").attr("cy");
                d3.select("#dot"+d.shopid).append("path")
                    .attr("class","line")
                    .attr("d","M"+x1+","+y1+"L"+x2+","+y2)
                    .attr("stroke","blue");
            })
            .on("mouseout",function(){
                d3.select("#dot"+d.shopid).selectAll(".line").attr("d",null)
            })
    })
    cooperation.forEach(function(d){
        d3.select("#center"+d.shopid)
            .on("mouseover",function(){
                var x1=$("#dot"+d.shopid).find("circle").attr("cx");
                var y1=$("#dot"+d.shopid).find("circle").attr("cy");
                var x2=$("#dot21270425").find("circle").attr("cx");
                var y2=$("#dot21270425").find("circle").attr("cy");
                d3.select("#dot"+d.shopid).append("path")
                    .attr("class","line")
                    .attr("d","M"+x1+","+y1+"L"+x2+","+y2)
                    .attr("stroke","blue");
            })
            .on("mouseout",function(){
                d3.select("#dot"+d.shopid).selectAll(".line").attr("d",null)
            })
    })
}