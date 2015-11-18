// JavaScript Document
var pageWidth="";
$(document).ready(function(){
	
	//页面内容部分宽度 高度自适应
	
	// 高度
	var headerHeight = $(".header").height()+$(".info-bar").height();
	var footerHeight = $(".footer").height();

	var winHeight = $(window).height();
	
	var leftAcdH = winHeight - headerHeight - footerHeight - 12; 
	$(".leftAcd").height(leftAcdH - 7);		
	$(".left_side").height(leftAcdH+11);
	$("#page").height(leftAcdH-3);
	
	var workareaH = winHeight - headerHeight - footerHeight - 15/*页脚的margin-top*/ + 13 - 17/*left部分和workarea的margin-top*/; //减去上面和下面的17px
	
	$(".workarea").height(workareaH+1);
		$(".login_main").height(workareaH+8);
	//add by sunwei
		$(".iframeWorkarea").height(workareaH + 11);
	
	
	// 宽度
	var html_w = $(window).width();
	$(".wrapper").width(html_w);
	
	var treeWidth = $(".left_side").width();
		
	var workarea_w = html_w - treeWidth;						   
	$("#mainTest").width(workarea_w - 21-20-18);
		$(".login_main").width(workarea_w);
	pageWidth = workarea_w - 17;
	
	$(window).resize(function() {	
		// 高度
		var headerHeight = $(".header").height()+$(".info-bar").height();
		var footerHeight = $(".footer").height();

		var winHeight = $(window).height();
		
		var leftAcdH = winHeight - headerHeight - footerHeight - 12; 
		/*$(".leftAcd").height(leftAcdH - 7);	*/	
		/*$(".left_side").height(leftAcdH+11);*/
		$("#page").height(leftAcdH-3);
		$("#page").width("100%");
		$("#page").children().height(leftAcdH-3);
		
		var workareaH = winHeight - headerHeight - footerHeight - 15/*页脚的margin-top*/ + 13 - 17/*left部分和workarea的margin-top*/; //减去上面和下面的17px
		
		/*$(".workarea").height(workareaH+1);*/
			$(".login_main").height(workareaH+8);
		//add by sunwei
			$(".iframeWorkarea").height(workareaH + 11);
		
		
		// 宽度
		var html_w = $(window).width();
		$(".wrapper").width(html_w);
		/*$(".header").width(html_w);
		$("#page").width(html_w);
		$(".footer").width(html_w);*/
		
		var treeWidth = $(".left_side").width();
		
		var workarea_w = html_w - treeWidth;						   
		/*$(".workarea").width(workarea_w - 21-20-18);*///
			$(".login_main").width(workarea_w);
		
	});	

});