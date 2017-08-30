/**
 * Created by Kotomi on 2017/7/24.
 */

function drawParallel(data){
    var schema = [
        {name: 'date1', index: 0, text: '开始日期'},
        {name: 'date2', index: 1, text: '结束日期'},
        {name: 'count', index: 2, text: '重叠度'},
        {name: 'distance', index: 3, text: '距离'},
    ];
    var lineStyle = {
        normal: {
            width: 2,
            opacity: 0.5,
        }
    };
    option_lefttoptime = {
        legend: {
            top: 5,
            data: ['competitor', 'origin', 'cooperator'],
            itemGap: 5,
            textStyle: {
                color: '#222',
                fontSize: 14
            }
        },
        parallelAxis: [
            {dim: 0, name: schema[0].text, type:'time',min:new Date('2015-01-01'),max:new Date('2017-06-31') },
            {dim: 1, name: schema[1].text, type:'time',min:new Date('2015-01-01'),max:new Date('2017-06-31')},
            {dim: 2, name: schema[2].text, type:'value'},
            {dim: 3, name: schema[3].text, type:'value'}
        ],
        // visualMap: {
        //     show: true,
        //     dimension: 2,
        //     min: 0,
        //     max: 60,
        //     inRange: {
        //         color: ['#d94e5d','#eac736','#50a3ba'].reverse,
        //     }
        // },
        parallel: {
            left: '5%',
            right: '5%',
            bottom: '15%',
            top: '15%',
            layout:'vertical',
            parallelAxisDefault: {
                type: 'value',
                name: '重叠度',
                nameLocation: 'end',
                nameGap: 20,
                nameTextStyle: {
                    color: '#000',
                    fontSize: 12
                },
                splitLine: {
                    show: false
                },
                axisLabel: {
                    textStyle: {
                        color: '#2a333d'
                    }
                }
            }
        },
        series: [
            {
                name: 'competitor',
                type: 'parallel',
                lineStyle: lineStyle,
                data: data[0]
            },
            {
                name: 'origin',
                type: 'parallel',
                lineStyle: lineStyle,
                data: data[1]
            },
            {
                name: 'cooperator',
                type: 'parallel',
                lineStyle: lineStyle,
                data: data[2]
            }
        ]
    };
    if (option_lefttoptime && typeof option_lefttoptime === "object") {
        myChart_lefttoptime.setOption(option_lefttoptime, true);
        console.log( "draw done" );
    }
}