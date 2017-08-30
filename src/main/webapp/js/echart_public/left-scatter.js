/**
 * Created by Kotomi on 2017/7/23.
 */

function drawScatter(data,origin){

   //  var myChart_rightscatter = echarts.getInstanceByDom(dom_rightscatter) ;
   // if( myChart_rightscatter != null){
   //     myChart_rightscatter.dispose();
   // }

    var convertedData = convertData_rightscatter(data);
    option_rightscatter = {
        backgroundColor: new echarts.graphic.RadialGradient(0.3, 0.3, 0.8, [{
            offset: 0,
            color: '#f7f8fa'
        }, {
            offset: 1,
            color: '#f7f8fa'
        }]),
        title: {
            text: origin +' overlap overview with other shops'
        },
        legend: {
            right: 10,
            data: ['competitor','cooperator']
        },
        tooltip : {
            // trigger: 'axis',
            showDelay : 0,
            formatter : function (params) {
                    return params.value[2] + ', '
                        + params.value[3] ;
            }
        },
        xAxis: {
            name : "distance",
            splitLine: {
                lineStyle: {
                    type: 'dashed'
                }
            }
        },
        yAxis: {
            name : "overlap" ,
            splitLine: {
                lineStyle: {
                    type: 'dashed'
                }
            },
            scale: true
        },
        series: [{
            name: 'competitor',
            data: convertedData[0],
            type: 'scatter',
            symbolSize: function(d){
                return d[1]/2;
            },
            label: {
                emphasis: {
                    show: false,
                    // formatter: ,
                    position: 'top'
                }
            },
            itemStyle: {
                normal: {
                    shadowBlur: 10,
                    shadowColor: 'rgba(120, 36, 50, 0.5)',
                    shadowOffsetY: 5,
                    color:  new echarts.graphic.RadialGradient(0.4, 0.3, 1, [{
                                    offset: 0,
                                    color: 'rgb(251, 118, 123)'
                                }, {
                                    offset: 1,
                                    color: 'rgb(204, 46, 72)'
                                }])

                    }
                }},{
            name: 'cooperator',
            data: convertedData[1],
            type: 'scatter',
            symbolSize: function(d){
                return d[1]/2;
            },
            label: {
                emphasis: {
                    show: false,
                    position: 'top'
                }
            },
            itemStyle: {
                normal: {
                    shadowBlur: 10,
                    shadowColor: 'rgba(25, 100, 150, 0.5)',
                    shadowOffsetY: 5,
                    color:  new echarts.graphic.RadialGradient(0.4, 0.3, 1, [{
                        offset: 0,
                        color: 'rgb(129, 227, 238)'
                    }, {
                        offset: 1,
                        color: 'rgb(25, 183, 207)'
                    }])
                }
            }
        }]
    };;
    if (option_rightscatter && typeof option_rightscatter === "object") {
        myChart_rightscatter.setOption(option_rightscatter, true);
    }
}