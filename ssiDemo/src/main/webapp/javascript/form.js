// JavaScript Document
$(document).ready(function(){
//模仿select下拉框之被选中值的背景
	$(".selectSetVal li").hover(function(){
		$(this).css('background','#d9d9d9')
	},function(){
		$(this).css('background','none')
	})
//模仿select下拉列框之得到被选中的值

   $(".selectSetVal li").click(function(){
		var val = $(this).text();
		$(this).parent().siblings(".selectGet").children(".selectGetVal").text(val);
		$(this).parent(".selectSetVal").slideUp("fast");
	})
//模仿select下拉列框之点击箭头显示下拉框
	$(".selectSetVal").hide();
   $(".selectGet").click(function(){
		$(this).siblings(".selectSetVal").slideDown();
	})
//鼠标离开下拉列表 列表收起
    $(".formCon").hover(function(){},function(){
		$(this).find(".selectSetVal").slideUp();
	})
	
	$(".buttonbarRig").hover(function(){},function(){
		$(this).find(".selectSetVal").slideUp();
	})
	
//模仿 select option
   $(".inputAuto2 span").click(function(){
		$(this).addClass("optionSel");
		$(this).siblings("span").removeClass("optionSel");
	})
   //test
   $(".btn1").hover(function(){
		$(this).removeClass("btn_add").addClass("btn_add_sel");
		$(".btn2").removeClass("btn_remove_sel").addClass("btn_remove");
		$(".btn3").removeClass("btn_addAll_sel").addClass("btn_addAll");
		$(".btn4").removeClass("btn_removeAll_sel").addClass("btn_removeAll");
	},function(){})
   $(".btn2").hover(function(){
		$(this).removeClass("btn_remove").addClass("btn_remove_sel");
		$(".btn1").removeClass("btn_add_sel").addClass("btn_add");
		$(".btn3").removeClass("btn_addAll_sel").addClass("btn_addAll");
		$(".btn4").removeClass("btn_removeAll_sel").addClass("btn_removeAll");
	},function(){})
   $(".btn3").hover(function(){
		$(this).removeClass("btn_addAll").addClass("btn_addAll_sel");
		$(".btn2").removeClass("btn_remove_sel").addClass("btn_remove");
		$(".btn1").removeClass("btn_add_sel").addClass("btn_add");
		$(".btn4").removeClass("btn_removeAll_sel").addClass("btn_removeAll");
	},function(){})
   $(".btn4").hover(function(){
		$(this).removeClass("btn_removeAll").addClass("btn_removeAll_sel");
		$(".btn2").removeClass("btn_remove_sel").addClass("btn_remove");
		$(".btn3").removeClass("btn_addAll_sel").addClass("btn_addAll");
		$(".btn1").removeClass("btn_add_sel").addClass("btn_add");
	},function(){})
   
   
   //下拉加输入框
   var myData = [
			"小李",
			"小赵",
			"小张",
			"小小",
			"C",
			"C++",
			"Clojure",
			"COBOL",
			"ColdFusion",
			"Erlang",
			"Fortran",
			"Groovy",
			"Haskell",
			"Java",
			"JavaScript",
			"Lisp",
			"Perl",
			"PHP",
			"Python",
			"Ruby",
			"Scala",
			"Scheme"
		];
		
//		$( ".input" ).autocomplete({
//			source: myData
//		});
		
//input 提示信息
//   $(".input").css("color","#ccc");
   $(".input").css("color","#666");
  
  
   $(".input").click(function(){

		 
		$(this).attr("val","inputVal");
		
		 $(this).css("color","#666");
	})

//内容框提示信息
		$(".searchBarTex").css("color","#ccc");
    
		$(".searchBarTex").disabled = true;
		
		$(".searchBarTex").click(function(){
			$(this).disabled = false;
			$(this).text('');
			$(".searchBarTex").css("color","#666");
		})
//点击input弹出提示信息
       $(".msgWarning").hide();
       $(".input").click(function(){
			$(this).parent().next(".msgWarning").show();
		})
})