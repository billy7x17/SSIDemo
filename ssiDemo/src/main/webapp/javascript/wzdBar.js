// JavaScript Document
$(document).ready(function(){
	//向导右侧按钮状态样式					   
	
	
	$(".wzdBarR span").click(function(){
		$(this).css('background','url(themes/default/images/btn_small_focus.png) no-repeat left top');
		$(this).css('color','#fff');
		
		$(this).siblings("span").css('background','url(themes/default/images/btn_small_normal.png) no-repeat left top');
		$(this).siblings("span").css('color','#666');
		
		//柱状 饼状 曲线 图 显示和隐藏
		var ind = $(this).index();
		$($(".graph").children("div")[ind]).show().siblings().hide();
	})
	$(".wzdBarR span").hover(function(){
		$(this).css('text-decoration','underline');
	},function(){
		$(this).css('text-decoration','none');
	})
})