// JavaScript Document
$(function(){
		   
//导航下拉菜单的显示和隐藏
	$(".navSub").hide();
	$(".navBar li").hover(function(){
		var ind = $(this).index();
		//导航下拉没有内容不执行下拉菜单操作
		if( $.trim($($(".navSub")[ind]).text()) == "" ) return;
		
		$($(".navSub")[ind]).slideDown().siblings("ul").slideUp("fast");
		//导航下拉菜单的列表宽度ie7兼容
		
		var indL = $(this).index();
		var nS = $($(".navSub")[indL]).width();
		$($(".navSub")[indL]).children("li").width(nS);
		 
	},function(){
		var ind = $(this).index();
		$($(".navSub")[ind]).hide();
	})
		
	$(".navSub").hover(function(){
		
	  $(this).show();
	  var ind = $(this).index()-1;
	$($(".navBar li")[ind]).addClass("navSel");
	$($(".navBar li")[ind]).find('a').css('color','#FFFFFF');
	 						
	},function(){
		$(this).slideUp("fast");
		var ind = $(this).index()-1;	
		$($(".navBar li")[ind]).removeClass("navSel");
		$($(".navBar li")[ind]).find('a').css('color','#364858');
	})
	
//下拉菜单最后一条样式
	$(".navSub li:last-child").css('background','none');
	
//下拉菜单的最小宽度
		$(".navBar li").each(function(){
			var ind = $(this).index();   
			var navBarWidth = $(this).width()-10;
			var subList = $(".navSub")[ind];
			if(subList != undefined)
			{
			$(subList).css('min-width', navBarWidth + 'px');
			}
			
			//下拉菜单左侧横坐标
			var ind2 = ind + 1;
			var leftWidht = $(this).offset().left;
			$(".subX_"+ind2).css("left",leftWidht)
		}); 

	

	
});