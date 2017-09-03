// get a array of a specific key
var convertJson = function(data, key){
    var res = [] ;
    for(var i = 0 ; i < data.length ; i++){
        if(data[i][key] == null) continue;
        res.push(data[i][key]);
    }
    return res;
}