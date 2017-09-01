//the main shop
        var origin = "65480779";


        // function getOriginInfo(){
        //     $.ajax({
        //         type: "post",
        //         url: server_context + "/shopinfo?shopid=" + origin,
        //         dataType: "json",
        //         error: function () {
        //             console.log("search erro");
        //         },
        //         success: function (shopInfo) {
        //             console.log("get origin info success");
        //         }
        //     })
        // }

        // function getCompetitor(){
        //     $.ajax({
        //         type: "post",
        //         url: server_context + "/shopcompetition?shopid="+origin,
        //         dataType: "json",
        //         error: function () {
        //             console.log("search erro");
        //         },
        //         success: function (competitors) {
        //             console.log("get competitor success");
        //             var dtd = $.Deferred();
        //             dtd.resolve(competitors);
        //             return dtd.promise();
        //         }
        //     })
        // }


        $.when($.ajax({
            type: "post",
            url: server_context + "/shopinfo?shopid=" + origin,
            dataType: "json",
            error: function () {
                console.log("search erro");
            },
            success: function (shopInfo) {
                console.log("get origin info success");
            }
        }), $.ajax({
            type: "post",
            url: server_context + "/shopcompetition?shopid="+origin,
            dataType: "json",
            error: function () {
                console.log("search erro");
            },
            success: function (competitors) {
                console.log("get competitor success");
            }
        }))
            .done(function(data1,data2){
                var originInfo = data1[0];
                var competitors =data2[0];
                // if(shopInfo.length == 0) return;
                // var originInfo = shopInfo[0];
                /** right-map */
                var myCenter=new google.maps.LatLng(originInfo.latitude,originInfo.longitude);
                var mapProp = {
                    zoom: 10,
                    center: myCenter,
                    mapTypeId: google.maps.MapTypeId.ROADMAP
                };
                var map = new google.maps.Map(d3.select("#right-up-map").node(),mapProp);

                /** left-scatter */
                drawScatter(competitors, originInfo, map);
                /** right - map -scatter*/
                drawmap(competitors,map,myCenter);

            })
            .fail(function(){ alert("出错啦！"); });


// $.ajax({
        //     type: "post",
        //     url: server_context + "/shopinfo?shopid=" + origin,
        //     dataType: "json",
        //     error: function () {
        //         console.log("search erro");
        //     },
        //     success: function (shopInfo) {
        //         console.log("get origin info success");
        //         if(shopInfo.length == 0) return;
        //
        //         var originInfo = shopInfo[0];
        //
        //         /** right-map */
        //         var myCenter=new google.maps.LatLng(originInfo.latitude,originInfo.longitude);
        //         var mapProp = {
        //             zoom: 15,
        //             center: myCenter,
        //             mapTypeId: google.maps.MapTypeId.ROADMAP
        //         };
        //         var map = new google.maps.Map(d3.select("#right-up-map").node(),mapProp);
        //
        //         /* left-scatter | right-map-scatter */
        //         $.ajax({
        //             type: "post",
        //             url: server_context + "/shopcompetition?shopid="+origin,
        //             dataType: "json",
        //             error: function () {
        //                 console.log("search erro");
        //             },
        //             success: function (competitors) {
        //                 console.log("search success");
        //                 drawScatter(competitors,originInfo);
        //                 drawmap(competitors,originInfo);
        //             }
        //         })
        //
        //
        //     }
        // })





