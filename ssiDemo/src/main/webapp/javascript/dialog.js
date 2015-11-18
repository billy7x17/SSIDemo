// JavaScript Document
$(document).ready(function(){
  //提示对话框点击关闭						   
	$(".dialog_closeD").click(function(){
		$(this).parent().parent("div").slideUp(200);
	})
	
	//导入 test
	//$(".succcess").hide();
	
	//$(".searchBar").hide();
	$(".tolSearch").click(function(){
		$(".searchBar").show();
	})
	
	
//提示信息5秒后自动消失
	function messagesHide(){
		
		//$(".messages").hide("slow");
		
		$(".feedback_dialog").hide("slow");
	}
	function autoPlay(){
	   setInterval(messagesHide,5000);
	}
	autoPlay();
	
	//测试弹出提示信息
	// $(".messages").hide();
	
	
	
	
	$("#addBtn").click(function(){
		$(".messages").show("slow");
		$(".addCent").hide();
		
		$(".messages").delay(5000).hide("slow");
		$(".feedback_dialog").delay(5000).hide("slow");
	});
	
	//$("#fileBtnTest").click(function(){
	//	$(".messages").show("slow");
	//	$("#searchBarTest").hide();
		
	//	$(".messages").delay(5000).hide("slow");
	//	$(".feedback_dialog").delay(5000).hide("slow");
	//});
	
	
	
//test鼠标放在下右下拉部分弹出对话框
    
   $(".feedback_dialog").hide();
   $(".buttonbarRig").hover(function(){
		$(".feedback_dialog").show("slow");
	},function(){})
   
//test鼠标放在"下一步"部分弹出对话框 
    //$(".error_dialog").hide();
   // $(".testMessage3").click(function(){
	//	$(".error_dialog").show()
	//})
	
})