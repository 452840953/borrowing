function iflogin() {
    var user;
    $.ajax({
        url: '/admin/get',
        type: 'get',
        async: false,
        dataType: 'json',
        success: function (res) {
            console.log(res.obj);
            if(res.status!=1){
                window.location.href="login.html";
            }else{
                user = res.obj;
            }
        }
    });
    return user;
}
function getQueryVariable(variable)
{
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        if(pair[0] == variable){return pair[1];}
    }
    return(false);
}
