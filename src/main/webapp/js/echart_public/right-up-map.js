/**
 * Created by Kotomi on 2017/7/13.
 */
$.ajax({
    type: "post",
    url: server_context + "/shoplocation",
    dataType: "json",
    error: function () {
    console.log("erro");
},
success: function (geodata) {
    console.log( "success" );
    drawmap(geodata);
}
});
function drawmap(geodata) {
    $.get('../data/baidumap/shanghai.json', function (shanghaiJson) {
        echarts.registerMap('shanghai', shanghaiJson);
        //   转化成 组件可以识别的data


        var convertedData=converteData_map(geodata);
        option_map = {
            animation: true,
            animationDuration: 1000,
            animationEasing: 'cubicInOut',
            animationDurationUpdate: 1000,
            animationEasingUpdate: 'cubicInOut',
            title: [
                {
                    text: '上海店铺',
                    subtext: 'data from dianping.com',
                    sublink: 'www.dianping.com',
                    left: 'center',
                    textStyle: {
                        color: '#fff'
                    }
                },
                {
                    id: 'statistic',
                    right: 120,
                    top: 40,
                    width: 100,
                    textStyle: {
                        color: '#fff',
                        fontSize: 16
                    }
                }
            ],
            toolbox: {
                iconStyle: {
                    normal: {
                        borderColor: '#fff'
                    },
                    emphasis: {
                        borderColor: '#b1e4ff'
                    }
                }
            },
            brush: {
                outOfBrush: {
                    color: '#abc'
                },
                brushStyle: {
                    borderWidth: 2,
                    color: 'rgba(0,0,0,0.2)',
                    borderColor: 'rgba(0,0,0,0.5)',
                },
                seriesIndex: 0,
                throttleType: 'debounce',
                throttleDelay: 300,
                geoIndex: 0
            },
            bmap: {
                center: [121.5025, 31.237015],
                zoom: 14,
                roam: true,
                mapStyle: {
                    styleJson: [{
                        'featureType': 'water',
                        'elementType': 'all',
                        'stylers': {
                            'color': '#d1d1d1'
                        }
                    }, {
                        'featureType': 'land',
                        'elementType': 'all',
                        'stylers': {
                            'color': '#f3f3f3'
                        }
                    }, {
                        'featureType': 'railway',
                        'elementType': 'all',
                        'stylers': {
                            'visibility': 'off'
                        }
                    }, {
                        'featureType': 'highway',
                        'elementType': 'all',
                        'stylers': {
                            'color': '#fdfdfd'
                        }
                    }, {
                        'featureType': 'highway',
                        'elementType': 'labels',
                        'stylers': {
                            'visibility': 'off'
                        }
                    }, {
                        'featureType': 'arterial',
                        'elementType': 'geometry',
                        'stylers': {
                            'color': '#fefefe'
                        }
                    }, {
                        'featureType': 'arterial',
                        'elementType': 'geometry.fill',
                        'stylers': {
                            'color': '#fefefe'
                        }
                    }, {
                        'featureType': 'poi',
                        'elementType': 'all',
                        'stylers': {
                            'visibility': 'off'
                        }
                    }, {
                        'featureType': 'green',
                        'elementType': 'all',
                        'stylers': {
                            'visibility': 'off'
                        }
                    }, {
                        'featureType': 'subway',
                        'elementType': 'all',
                        'stylers': {
                            'visibility': 'off'
                        }
                    }, {
                        'featureType': 'manmade',
                        'elementType': 'all',
                        'stylers': {
                            'color': '#d1d1d1'
                        }
                    }, {
                        'featureType': 'local',
                        'elementType': 'all',
                        'stylers': {
                            'color': '#d1d1d1'
                        }
                    }, {
                        'featureType': 'arterial',
                        'elementType': 'labels',
                        'stylers': {
                            'visibility': 'off'
                        }
                    }, {
                        'featureType': 'boundary',
                        'elementType': 'all',
                        'stylers': {
                            'color': '#fefefe'
                        }
                    }, {
                        'featureType': 'building',
                        'elementType': 'all',
                        'stylers': {
                            'color': '#d1d1d1'
                        }
                    }, {
                        'featureType': 'label',
                        'elementType': 'labels.text.fill',
                        'stylers': {
                            'color': '#999999'
                        }
                    }]
                }
            },
            series: [
                {
                    name: 'competitor',
                    type: 'scatter',
                    coordinateSystem: 'bmap',
                    data: convertedData[0],
                    symbolSize: function(d){
                        // console.log(d);
                        return d[2]/2;
                    },
                    label: {
                        normal: {
                            formatter: '{b}',
                            position: 'right',
                            textStyle:{
                                fontWeight: 'bold',
                                fontSize: 16
                            },
                            show: false
                        },
                        emphasis: {
                            show: true
                        }
                    },
                    itemStyle: {
                        normal: {
                            color:
                            function(param){
                            // console.log(d)
                            if(param.data.shopid == origin){
                                // console.log(param.data)
                                return '#ddb926';
                            }else{
                                if(param.data.cate == "奶茶"){
                                    return  '#fb767b';
                                }else{
                                    return '#2cbbe8';
                                }
                            }
                        }

                        }
                    }
                }
            ]
        }



        myChart_map.on('brushselected', renderBrushed);

        myChart_map.setOption(option_map);


        function renderBrushed(params) {
            var selectedItems = "";
            //    输出这些点的坐标
            var mainSeries = params.batch[0].selected[0];
            for (var i = 0; i < mainSeries.dataIndex.length; i++) {
                var rawIndex = mainSeries.dataIndex[i];
                var dataitem = convertedData[rawIndex].shopid;
                selectedItems +=  dataitem + ",";
            }

            $.ajax({
                type: "post",
                url: server_context + "/shoplocation?shops="+selectedItems,
                dataType: "json",
                error: function () {
                    console.log("erro");
                },
                success: function () {
                    console.log( "backstage success" );

                }
            });

        };
    })
}