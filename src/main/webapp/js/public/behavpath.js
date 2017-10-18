function calData(root,type,n){

    var data0 = [], line01 = [], line02 = [],
        data1 = [], line10 = [], line12 = [],
        data2 = [], line20 = [], line21 = [];
    var len = 0;
    var i = 0, j =0;
    var mark = type;
    if(mark == "total"){
        i = n;
    }else{
        i =1;
    }
    while(i > 0) {
        if(mark == "total" ){
            type = "type" + (i-1);
        }
        eval("root." + type).forEach(function (d) {
            for (var item in d) {
                if (len < d[item].length) {
                    len = d[item].length;
                }
            }

        })
        i--;
    }

    for (var i = 0; i < len; i++) {
        data0.push(0);
        data1.push(0);
        data2.push(0);
        line01.push(0);
        line02.push(0);
        line10.push(0);
        line12.push(0);
        line20.push(0);
        line21.push(0);
    }

    if(mark == "total"){
        j = n;
    }else{
        j =1;
    }
    while( j > 0) {
        if(mark == "total"){
            type = "type"+ (j-1);
        }
        eval("root." + type).forEach(function (d) {

            for (var item in d) {
                var p = d[item];
                p.forEach(function (pi, i) {
                    if (p[i] == 0) {

                    }
                    if (p[i] == 1) {
                        data1[i]++;
                    }
                    if (p[i] == 2) {
                        data2[i]++;
                    }
                    switch (p[i]) {
                        case 0: {
                            data0[i]++;
                            if (p[i + 1] == 1) {
                                line01[i]++;
                            }
                            if (p[i + 1] == 2) {
                                line02[i]++;
                            }
                            break;
                        }
                        case 1: {
                            data1[i]++;
                            if (p[i + 1] == 0) {
                                line10[i]++;
                            }
                            if (p[i + 1] == 2) {
                                line12[i]++;
                            }
                            break;
                        }
                        case 2: {
                            data2[i]++;
                            if (p[i + 1] == 0) {
                                line20[i]++;
                            }
                            if (p[i + 1] == 1) {
                                line21[i]++;
                            }
                            break;
                        }
                        default:
                            break;
                    }
                })
            }

        })
        j--;
    }
    data0 = data0.map(function(d){return 2*d;});
    data1 = data1.map(function(d){return 2*d;});
    data2 = data2.map(function(d){return 2*d;});
    return {"len":len,"data0":data0,"line01":line01,"line02":line02,
        "data1":data1,"line10":line10,"line12":line12,
        "data2":data2,"line20":line20,"line21":line21,}
}