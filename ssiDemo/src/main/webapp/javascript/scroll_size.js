$(function(){
//������������߶�����Ӧ		   
		   var headerHeight = $(".header").height();
		   var footerHeight = $(".footer").height();
		   var winHeight = $(window).height();
		   var workareaHeight = winHeight - headerHeight - footerHeight - 14;
		   
		   
		   
		   var scrollHeight1 = workareaHeight - $(".tabBar").height() - $(".toolbar").height() - $(".wzdBar").height() - $(".buttonbar").height();
		   
		   
           $('.workareaMain.scroll-pane').css("height", scrollHeight1 + "px");
           //add by sunwei
           $('.workareaIframeMain.scroll-pane').css("height", scrollHeight1-30 + "px");
		   
		   $(window).resize(function() {	
				   var headerHeight = $(".header").height();
				   var footerHeight = $(".footer").height();
				   var winHeight = $(window).height();
				   var workareaHeight = winHeight - headerHeight - footerHeight - 14;
				   
				   
				   
				   var scrollHeight1 = workareaHeight - $(".tabBar").height() - $(".toolbar").height() - $(".wzdBar").height() - $(".buttonbar").height();
				   
				   
				   $('.workareaMain.scroll-pane').css("height", scrollHeight1 + "px");	
				 //add by sunwei
		           $('.workareaIframeMain.scroll-pane').css("height", scrollHeight1-30 + "px");
				   
				   
		   });	
		   
		   
	//tree �������ֹ������߶�����Ӧ		   
	$(function(){
		var leftTreHeight = $(".leftTre").height();
		
		$(".leftTre .scroll-pane").css("height",leftTreHeight + "px");
	
	})
		   
//������ͼƬ�߶�	
	$('.workarea .scroll-pane').jScrollPane(
		{
			verticalDragMinHeight: 20,
			verticalDragMaxHeight: 20,
			showArrows: true,
			autoReinitialise: true
		}
	);
	
	
	$('.leftTre .scroll-pane').jScrollPane(
		{
			verticalDragMinHeight: 20,
			verticalDragMaxHeight: 20,
			showArrows: true,
			autoReinitialise: true
		}
	);
	
	$('.inputAuto2 .scroll-pane').jScrollPane(
		{
			verticalDragMinHeight: 20,
			verticalDragMaxHeight: 20,
			showArrows: true,
			autoReinitialise: true
		}
	);
	//add by sunwei
	$('.iframeWorkarea .scroll-pane').jScrollPane(
			{
				verticalDragMinHeight: 20,
				verticalDragMaxHeight: 20,
				showArrows: true,
				autoReinitialise: true
			}
		);
	
    
    var winHeight = $(window).height();
    var $topDiv = $('.rightToolbar');
    var $displayDiv = $('.rightDisplayFwSj');
    
    var height = $topDiv.height() + $displayDiv.height() + 25;
        $displayDiv.height(winHeight - $topDiv.height() -12);
    $(window).resize(function() {   
        var winHeight = $(window).height();
        var $topDiv = $('.rightToolbar');
        var $displayDiv = $('.rightDisplayFwSj');
        
        var height = $topDiv.height() + $displayDiv.height() + 25;
            $displayDiv.height(winHeight - $topDiv.height() - 12);
    });
	
});