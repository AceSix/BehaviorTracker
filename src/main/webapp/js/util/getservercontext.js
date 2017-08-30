/**
 * Created by Kotomi on 2017/7/13.
 */
//获取项目的根目录
var localObj = window.location;
var contextPath = localObj.pathname.split("/")[1];
var basePath = "/"+contextPath;
var server_context=basePath;