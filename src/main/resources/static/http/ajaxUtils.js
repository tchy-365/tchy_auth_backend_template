

layer = parent.layer === undefined ? layui.layer : parent.layer;

// post同步
function postAsyncFalse(urls,datas){
    var resultData = "";
    $.ajax({
        type : "POST",
        dataType:'json', //通过POST方式上传请求
        contentType: 'application/json',
        async:false,
        data : JSON.stringify(datas),                                 //上传的参数
        url : urls,     //请求的url。与后端@Request Mapping注解中的值一致。
        success : function(result) {		      //请求成功的回调函数
            resultData = result
        },
        error : function(e) {		      //请求失败的回调函数
            resultData = e
        }
    });
    return resultData;
}


// post异步
function postAsyncTrue(urls,datas){
    var resultData = "";
    $.ajax({
        type : "POST",
        dataType:'json', //通过POST方式上传请求
        contentType: 'application/json',
        async:true,
        data : JSON.stringify(datas),                                 //上传的参数
        url : urls,     //请求的url。与后端@Request Mapping注解中的值一致。
        success : function(result) {		      //请求成功的回调函数
            resultData = result
        },
        error : function(e) {		      //请求失败的回调函数
            resultData = e
        }
    });
    return resultData;
}



// get异步
function getAsyncTrue(urls,datas){
    var resultData = "";
    $.ajax({
        type : "GET",
        dataType:'json', //通过POST方式上传请求
        contentType: 'application/json',
        async:true,
        data : JSON.stringify(datas),                                 //上传的参数
        url : urls,     //请求的url。与后端@Request Mapping注解中的值一致。
        success : function(result) {		      //请求成功的回调函数
            resultData = result
        },
        error : function(e) {		      //请求失败的回调函数
            resultData = e
        }
    });
    return resultData;
}


// get同步
function getAsyncFalse(urls,datas){
    var resultData = "";
    $.ajax({
        type : "GET",
        dataType:'json', //通过POST方式上传请求
        contentType: 'application/json',
        async:false,
        data : JSON.stringify(datas),                                 //上传的参数
        url : urls,     //请求的url。与后端@Request Mapping注解中的值一致。
        success : function(result) {		      //请求成功的回调函数
            resultData = result
        },
        error : function(e) {		      //请求失败的回调函数
            resultData = e
        }
    });
    return resultData;
}