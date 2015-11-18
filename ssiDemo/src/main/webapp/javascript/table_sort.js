// JavaScript Document

$(document).ready(function(){
//table 排序
	//$('.example').dataTable( {"sDom": '' } );
	var oTable = $('.example').dataTable( {"sDom": ''} );
	
	var oSettings = oTable.fnSettings();                       	
	var initLenTxt = $(".selectGetW_60 .selectGetVal").text(); 
	var initLen = parseInt( initLenTxt );                      
    oSettings._iDisplayLength = initLen;	                   
	oTable.fnDraw(); 
	
	
	
	
   $("#pageUl li").click(function(){
												
		var val = $(this).text();
		var len = parseInt(val);
		oSettings._iDisplayLength = len;	
	    oTable.fnDraw();
		$('input').checkBox();	
	});
   
   
   
   
   $(".pageNo").keypress(function(event){
		if(event.which == 13){
			//alert(oSettings.aiDisplay.length);
			var pageNo = $(this).attr("value");
			pageNo = parseInt($.trim(pageNo));
			
			if(isNaN(pageNo) || pageNo < 1) return; 
			
			
			var len = oSettings._iDisplayLength; 
			var startPos = len * (pageNo - 1);   
			
			
			oSettings._iDisplayStart = startPos; 
			oTable.fnDraw(false); 
			$('input').checkBox();	
		}
		
	});
   
   
   $(".btn_next").click(function(){
		oTable.fnPageChange( 'next' );
		$('input').checkBox();	
	});
   
   $(".btn_previous").click(function(){
		oTable.fnPageChange( 'previous' );
		$('input').checkBox();	
	});
   
   $(".btn_first").click(function(){
		oTable.fnPageChange( 'first' );
		$('input').checkBox();	
	});
   
   $(".btn_last").click(function(){
		oTable.fnPageChange( 'last' );
		$('input').checkBox();	
	});
})