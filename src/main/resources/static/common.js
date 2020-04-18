//获取地址栏参数
function GetQueryString(name)
{
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  unescape(r[2]); return null;
}

function hasText(str) {
    if (str != undefined && str != null)
        return str.trim() != "";
    return false;
}

function buildXxx(json) {
    /**
     * 解析json然后构造Xxx
     */
}