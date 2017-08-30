/**
 * Created by Kotomi on 2017/6/4.
 */
//实现两个文件一起读取
var getCsv = function (csvUrl) {
    var defer = $.Deferred();
    d3.csv(csvUrl, function (error, rows) {
        if (error) {
            defer.reject(error);
        }
        defer.resolve(rows);
    });
    return defer.promise();
};
var getJson = function (jsonUrl) {
    var defer = $.Deferred();
    d3.json(jsonUrl, function (error, rows) {
        if (error) {
            defer.reject(error);
        }
        defer.resolve(rows);
    });
    return defer.promise();
};