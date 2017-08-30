/**
 * Created by Kotomi on 2017/7/22.
 */
function toDate(v) {
    var date = new Date();
    date.setTime(v.time);
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    m = m<10?'0'+m:m;
    var s = date.getSeconds();
    var d = date.getDate();
    d = d<10?("0"+d):d;
    var h = date.getHours();
    h = h<10?("0"+h):h;
    var M = date.getMinutes();
    M = M<10?("0"+M):M;
    var S = date.getSeconds();
    S = S<10?("0"+S):s;
    var str = y+"-"+m+"-"+d+" "+h+":"+M+":"+S;
    return new Date(str);
}