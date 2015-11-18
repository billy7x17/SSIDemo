// JavaScript Document
$(document).ready(function(){
	//最后td的边框为0					   
	$(".list tr th:last-child").css('border-right','none');
	$(".list tr td:last-child").css('border-right','none');	
	$(".list tr:last-child").children("td").css('border-bottom','none');
	//list 的关闭和展开
	
	
	$(".rowEvenBg .list_close").parent(".rowEvenBg").nextUntil(".rowEvenBg").hide();
	$(".rowEvenBg .list_open").parent(".rowEvenBg").nextUntil(".rowEvenBg").show();
	
	
	$(".rowEvenBg .list_sta").click(function(){
		$(this).parent(".rowEvenBg").nextUntil(".rowEvenBg").toggle();
		$(this).hasClass("list_close")?$(this).addClass("list_open").removeClass("list_close"):$(this).addClass("list_close").removeClass("list_open");
	});
	//tr鼠标经过改变背景色
	$("tr").each(function(){
		var tr_bgColor = $(this).css("background-color");		
		
		$(this).hover(function(){
			$(this).css("background-color","#dadada");
		},function(){
			$(this).css("background-color",tr_bgColor);
		
		})
	})
	
})