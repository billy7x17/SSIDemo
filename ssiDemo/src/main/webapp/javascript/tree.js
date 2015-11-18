// JavaScript Document
//tree
$(document).ready(function(){
	
	$(".treeTit").click(function(){
		$(this).siblings("dl").hasClass("display_none")?$(this).siblings("dl").removeClass("display_none").addClass("display_block"):$(this).siblings("dl").removeClass("display_block").addClass("display_none");
		$(this).siblings("dl").hasClass("display_none")?$(this).children(".treeTitImg").addClass("TitClose").removeClass("TitOpen"):$(this).children(".treeTitImg").addClass("TitOpen").removeClass("TitClose");
	});
					   
	$(".leftTre dt").click(function(){
		$(this).parent("dl").hasClass("tree_open")?$(this).parent("dl").attr("class","tree_close"):$(this).parent("dl").attr("class","tree_open");
	});
	$(".leftTre").each(function(){
		$(this).find("dl").last().css('background','none');
		$(".leftTre dl dd:last-child").css('background','url(themes/default/images/tree_line_last.gif) no-repeat 24px top');
	});
	/*$(".leftAcdTit").hover(function(){
		$(this).css	("text-decoration","underline");				
	},function(){
		$(this).css	("text-decoration","none");
	});*/
	//add by sunwei
	$(".leftAcdTit").click(function(){
		var $leftTre = $(this).next(".leftTre");
		if(!$leftTre.html()){
			//����Ϊ��������
			$leftTre.css("display","none").siblings(".leftTre").hide();
			$(".leftAcdTit").removeClass("leftAcdTitBlack").children("span").removeClass("icon-down").removeClass("icon-right").addClass('icon-right');
			$(this).addClass("leftAcdTitBlack").css("margin-bottom","4px");
		}else{
			$leftTre.show().siblings(".leftTre").hide();
			$(".leftAcdTit").removeClass("leftAcdTitBlack").children("span").removeClass("icon-down").removeClass("icon-right").addClass('icon-right');
			$(this).addClass("leftAcdTitBlack").children("span").addClass("icon-down");
		}
		/*$(".leftAcdTit").removeClass("leftAcdTitBlack").children("span").removeClass("icon-down").removeClass("icon-right").addClass('icon-right');
		$(this).addClass("leftAcdTitBlack").children("span").addClass("icon-down");*/
		
		
		/*$(this).addClass("active").siblings().removeClass("active");
		$(this).children('.icon-right').addClass('icon-down');
		$(this).siblings().children('.icon-right').removeClass('icon-down').addClass('icon-right');
		
		$(this).next('.nav-content').css('display','block').siblings('.nav-content').css('display','none');*/
		});
	//����tree������󱳾���ɫ
	$(" .tree_dt_txt").click(function(){
		$(this).css('background','#838994');
		$(this).css('color','#fff');
		$(this).css('text-decoration','none');
		
		$(".tree_dt_txt").not($(this)).css('background','none');
		$(".tree_dt_txt").not($(this)).css('color','#3a3d40');
		$(this).hover(function(){
			$(this).css('text-decoration','underline');
		},function(){
			$(this).css('text-decoration','none');
		});
	});
	//�����߶�����Ӧ
	var leftTitHeight = $(".leftAcdTit").height();
	var leftAcdHeight = $(".leftAcd").height();
	var titleNo = $(".leftAcd .leftAcdTit").length;
	var leftTreHeight = leftAcdHeight - leftTitHeight*titleNo-(titleNo-1)*4-1;
    $(".leftTre").height(leftTreHeight);
	
	//tree 2,3��������꾭����»���
	$(".treeTitTxt").hover(function(){
		$(this).css	("text-decoration","underline");	
	},function(){
		$(this).css	("text-decoration","none");
	})
	$(".tree_dt_txt").hover(function(){
		$(this).css	("text-decoration","underline");	
	},function(){
		$(this).css	("text-decoration","none");
	})
	$(window).resize(function() {
    	/*var headerHeight = $(".header").height() + $(".info-bar").height();
		var footerHeight = $(".footer").height();
		var winHeight = $(window).height();
		var workareaHeight = winHeight - headerHeight - footerHeight - 7;
		$(".leftAcd").css("height",workareaHeight + "px");
		//var leftAcdHeight = $(".leftAcd").height();
		var leftTreHeight = leftAcdHeight - leftTitHeight*titleNo-(titleNo-1)*4-1;
	    $(".leftTre").height(leftTreHeight);
	    $(".leftTre .scroll-pane").css("height",leftTreHeight + "px");*/
    });
})