// JavaScript Document
var popupStatus = 0;  
//使用Jquery加载弹窗  
function loadPopup() {  
    //仅在开启标志popupStatus为0的情况下加载     
    if(popupStatus==0){  
        $("#backgroundPopup").css({  
            "opacity": "0.7"  
        });  
        $("#backgroundPopup").fadeIn("slow");  
        $("#popupContact").fadeIn("slow");  
        popupStatus = 1;  
    }  
}  
//使用Jquery去除弹窗效果  
function disablePopup() {  
    //仅在开启标志popupStatus为1的情况下去除  
    if(popupStatus==1){
        $("#backgroundPopup").fadeOut("slow");  
        $("#popupContact").fadeOut("slow");  
        popupStatus = 0;  
    }  
}  

//将弹出窗口定位在屏幕的中央   
function centerPopup(){  
    var sWidth;var sHeight;  
    sWidth=window.screen.availWidth;  
    sHeight = document.body.scrollHeight;    
    var popupHeight = $("#popupContact").height();  
    var popupWidth = $("#popupContact").width();  
    //居中设置      
    $("#popupContact").css({  
        "position": "absolute",  
        "top": sHeight/2-popupHeight/2,  
        "left": sWidth/2-popupWidth/2  
    });  
	
    //以下代码仅在IE6下有效         
    $("#backgroundPopup").css({  
        "height": window.screen.availHeight,  
        "width":window.screen.availWidth  
    });  
      
}  

function showdiv()//调用事件  
{  
 centerPopup();  
 loadPopup();  
}

$(document).ready(function(){  
                  
    //关闭层  
    $("#popupContactClose").click(function(){  
        disablePopup();  
    });  
	//弹出框上边距
	var mTop = -$(".error_dialog").height();
	$("#popupContact").css("margin-top",mTop + 'px')
	
  
}); 