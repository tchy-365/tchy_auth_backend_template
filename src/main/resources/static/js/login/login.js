layui.config({
	dir : "/layui",
    base : "/js/"
}).use('form',function(){
	var form = layui.form;
	layer = parent.layer === undefined ? layui.layer : parent.layer;
	$ = layui.jquery;
	//video背景
	$(window).resize(function(){
		if($(".video-player").width() > $(window).width()){
			$(".video-player").css({"height":$(window).height(),"width":"auto","left":-($(".video-player").width()-$(window).width())/2});
		}else{
			$(".video-player").css({"width":$(window).width(),"height":"auto","left":-($(".video-player").width()-$(window).width())/2});
		}
	}).resize();

	//登录按钮事件
	form.on("submit(login)",function(data){
		$.ajax({
			type:'POST',
			dataType:'json',
			contentType: 'application/json',
			async:false,
			url:'/login/login',
			data: JSON.stringify(data.field),
			success: function (r) {
				if(r.code != 200){
					console.log(r.code);
                    layer.msg(r.msg);
				}else{
                    window.location.href = "/index/toIndex";
				}
            },
			error: function (e) {
            }
		});
		return false;
	});

    //自定义验证
    form.verify({
        username: function (value) {
            if(value == ""){
                return "请输入用户名";
            }
        },
        password: function(value){
            if(value == ""){
                return '请输入密码';
            }
        },
		code:function (value) {
			if(value == ""){
				return '请输入验证码'
			}
        }
    });

});
