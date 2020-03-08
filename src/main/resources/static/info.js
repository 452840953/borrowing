$("#addbtn").click(function(){
    var url = $("#addbtn").attr("url");
    $.ajax({
        //几个参数需要注意一下
        type: "POST",//方法类型
        dataType: "json",//预期服务器返回的数据类型
        url: "/"+url+"/add" ,//url
        data: $('#addform').serialize(),
        success: function (result) {
            console.log(result);//打印服务端返回的数据(调试用)
            if (result.status == 1) {
                alert(result.obj);
                window.location.reload();
            }else{
                alert(result.obj);
            }
        },
        error : function() {
            alert("异常！");
        }
    });
});

$("#changebtn").click(function(){
    var url = $("#changebtn").attr("url");
    $.ajax({
        //几个参数需要注意一下
        type: "POST",//方法类型
        dataType: "json",//预期服务器返回的数据类型
        url: "/"+url+"/change" ,//url
        data: $('#changeform').serialize(),
        success: function (result) {
            console.log(result);//打印服务端返回的数据(调试用)
            if (result.status == 1) {
                alert(result.obj);
                window.location.reload();
            }else{
                alert(result.obj);
            }
        },
        error : function() {
            alert("异常！");
        }
    });
});

function search(url,item,search) {
    var info;
    $.ajax({
        url: '/'+url+'/search?item='+item+'&search='+search,
        type: 'get',
        async: false,
        dataType: 'json',
        success: function (res) {
            if(res.status==1){
                info = res.obj;
            }
        }
    });
    return info;
}

function getall(url) {
    var info;
    $.ajax({
        url: '/'+url+'/all',
        type: 'get',
        async: false,
        dataType: 'json',
        success: function (res) {
            if(res.status==1){
                info = res.obj;
            }
        }
    });
    return info;
}

function getone(id,url) {
    var info;
    $.ajax({
        url: '/'+url+'/get?id='+id,
        type: 'get',
        async: false,
        dataType: 'json',
        success: function (res) {
            if(res.status==1){
                info = res.obj;
            }
        }
    });
    return info;
}

function del(url,id) {
    $.ajax({
        url: '/'+url+'/del?id='+id,
        type: 'get',
        dataType: 'json',
        success: function (res) {
            info = res.obj;
            alert(info);
            if(res.status==1){
                window.location.reload();
            }
        }
    });
}
