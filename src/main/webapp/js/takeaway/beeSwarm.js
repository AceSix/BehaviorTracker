/**
 * Created by Kotomi on 2017/4/19.
 */


var svgbeeswarm = d3.select("#beeswarm").append("svg")
        .attr("width",200)
        .attr("height",100).attr("fill","transparent");
var margin = {top: 40, right: 40, bottom: 40, left: 40},
    widthbeeswarm = svgbeeswarm.attr("width") - margin.left - margin.right,
    heightbeeswarm = svgbeeswarm.attr("height") - margin.top - margin.bottom;

var formatValue = d3.format(",r");

var x = d3.scaleLinear()
    .range([0, widthbeeswarm]);

var gbeeswarm = svgbeeswarm.append("g")
    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

d3.csv("../data/beeSwarm.csv", type, function(error, data) {
    if (error) throw error;
    // console.log("csv的data",data);
    x.domain(d3.extent(data, function(d) { return d.value; }));

    var simulation = d3.forceSimulation(data)
        .force("x", d3.forceX(function(d) { return x(d.value); }).strength(1))
        .force("y", d3.forceY(heightbeeswarm / 2))
        .force("collide", d3.forceCollide(5))
        .stop();

    for (var i = 0; i < 120; ++i) simulation.tick();

    gbeeswarm.append("g")
        .attr("class", "axis axis--x")
        .attr("transform", "translate(0," + heightbeeswarm/2 + ")")
        .call(d3.axisBottom(x));

    var tick=svgbeeswarm.selectAll(".tick");
//维诺图
    var cell = gbeeswarm.append("g")
        .attr("class", "cells")
        .selectAll("g")
        .data(
            d3.voronoi()
                .extent([[-margin.left, -margin.top], [widthbeeswarm + margin.right, heightbeeswarm + margin.top]])
                .x(function(d) {return d.x; })
                .y(function(d) { return d.y; })
                .polygons(data)
        )
        .enter().append("g");
    cell.append("circle")
        .attr("fill","#ffcc00")
        .attr("r", function(d){return d.data.followers/10;})
        .attr("cx", function(d) { return d.data.x; })
        .attr("cy", function(d) { return d.data.y; });

    cell.append("path")
        .attr("d", function(d) { return "M" + d.join("L") + "Z"; });
 //title:对 svgBeeSwarm 中的元素的纯文本描述 - 并不作为图形的一部分来显示。用户代理会将其显示为工具提示
    cell.append("title")
        .text(function(d) { return d.data.id + "\n偏好度：" + formatValue(d.data.value)
            +"\n影响度：" + (d.data.followers); });

});

function type(d) {
    if (!d.value) return;
    d.value = +d.value;
    return d;
}

