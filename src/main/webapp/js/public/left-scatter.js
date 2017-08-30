

function drawScatter( data , shopid ){
    //map of data
    var mapData = d3.map(data,function(d){return d.destination});

    // range : [ minNum , maxNum , minDis , maxDis ]
    var range=  function(data){
        var res = [] ;
        var maxNum = 0 , minNum = 200 ,
            maxDis = 0 , minDis = 100000 ;
        for(var i = 0 ; i < data.length ; i++ ){
            if( data[i].destination == data[i].origin ) {
                data.splice(i , 1);
                continue;
            }
            if( maxNum < data[i].num){
                maxNum = data[i].num ;
            }

            if( minNum > data[i].num){
                minNum = data[i].num ;
            }

            if( maxDis < data[i].distance){
                maxDis = data[i].distance ;
            }

            if( minDis > data[i].distance){
                minDis = data[i].distance ;
            }


        }

        res.push(minNum);
        res.push(maxNum);
        res.push(minDis);
        res.push(maxDis);

        return res;
    }


    var width = $("#left-scatter").width(), height = $("#left-scatter").height();
    var margin = 50;
    var svg = d3.select("#left-scatter").append("svg")
        .attr("width",width)
        .attr("height",height);

    //Asix
    var gAsix = svg.append("g");

    var x = d3.scaleLinear()
        .domain([ range(data)[2] , range(data)[3] ])
        .range([margin , width-margin]);

    var y = d3.scaleLinear()
        .domain([ range(data)[0] , range(data)[1] ])
        .range([height-margin , margin]);

    var xAxis = d3.axisBottom(x)
        .ticks(6);
    var yAxis = d3.axisLeft(y)
        .ticks(8);

    gAsix.append("g")
        .attr("transform", "translate(0 ,"+ (height-margin)+")")
        .call(xAxis);

    gAsix.append("g")
        .attr("transform", "translate("+margin+",0)")
        .call(yAxis);

    //Scatter
    var gScatter = svg.append("g");

    var circles = gScatter.selectAll("circle")
        .data(data)
        .enter()
        .append("circle")
        .attr("class", "circles")
        .attr("cx", function(d){ return x(d.distance); })
        .attr("cy", function(d){ return y(d.num); })
        .attr("r", function(d){ return d.num*0.5; } )
        .attr("id", function(d){ return d.destination;})
        .style("fill" ,  "#FB767B" );

    var mouseOn = function(){
        var circle = d3.select(this);
        var circleParam = mapData.get(circle.attr("id"));

        //transition to increase size/opacity of bubble
        circle.transition()
            .duration(800)
            .style("opacity", 0.5)
            .attr("r", function(){
                var r = circleParam.num*0.5
                return r+2;
            })


        //show text
        gScatter.append("g")
            .attr("class" , "info")
            .append("text")
            .attr("x" , circle.attr("cx") )
            .attr("y" , circle.attr("cy"))
            .attr("dx" ,function() {
                if (circle.attr("cx") > width / 2) {
                    return -60;
                } else {
                    return 10;
                }
            })
            .attr("dy" , 10)
            .style("font-size" , "10px")
            .text(circleParam.destination+":"+circleParam.shopname);
    }


    var mouseOff = function(){
        var circle = d3.select(this);
        var circleParam = mapData.get(circle.attr("id"));

        // go back to original size and opacity
        circle.transition()
            .duration(800).style("opacity", 1)
            .attr("r",function(){
                var r = circleParam.num*0.5
                return r;
            })

        //fade out text
        d3.selectAll(".info").transition().duration(100)
            .styleTween("opacity",
                function() { return d3.interpolate(.5, 0); })
            .remove();
    }

    circles.on("mouseover" , mouseOn)
    circles.on("mouseout" , mouseOff);


}