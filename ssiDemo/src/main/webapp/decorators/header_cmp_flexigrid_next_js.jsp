<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="iamp-identify" uri="/WEB-INF/taglib/iamp-identify"%> 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>

<!-- add by wengcz 2012-7-26,My97日期控件 -->
<script src="<%=request.getContextPath()%>/javascript/My97DatePicker/WdatePicker.js" ></script>
<!-- 
<script src="<%=request.getContextPath()%>/javascript/jquery-1.6.1.min.js"></script>
 -->
<!-- 增加Flexigrid -->
<link href="<%=request.getContextPath()%>/themes/default/styles/form.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/javascript/Flexigrid/demo/style.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/javascript/Flexigrid/css/flexigrid.pack.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/blue/styles/right_side.css" />
<script src="<%=request.getContextPath()%>/javascript/Flexigrid/js/flexigrid.pack.js"></script>

<!-- add by wengcz 2013-2-26,操作系统安装使用dialog -->
<link href="<%=request.getContextPath()%>/themes/default/styles/dialog.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/stylesheets/jquery-ui.css" rel="stylesheet" type="text/css">
<!-- 
<script src="<%=request.getContextPath()%>/javascript/jquery-ui-1.8.16.custom/development-bundle/ui/jquery-ui-1.8.16.custom.js"></script>
 -->
<script src="<%=request.getContextPath()%>/javascript/dialog.js"></script>

<!-- add by wengcz 2013-2-26,操作系统安装使用progressbar -->
 <link href="<%=request.getContextPath()%>/themes/blue/styles/processbarNeu.css" rel="stylesheet" type="text/css">
<%-- 
<link href="<%=request.getContextPath()%>/javascript/charts/jquery.ui.all.css" rel="stylesheet" type="text/css"> 
<script src="<%=request.getContextPath()%>/javascript/jquery-ui-1.8.16.custom/development-bundle/ui/jquery.ui.core.js"></script>
<script src="<%=request.getContextPath()%>/javascript/jquery-ui-1.8.16.custom/development-bundle/ui/jquery.ui.widget.js"></script>
<script src="<%=request.getContextPath()%>/javascript/jquery-ui-1.8.16.custom/development-bundle/ui/jquery.ui.progressbar.js"></script>
--%>  

 


<style type="text/css">
.messages {display: none;}
.searchTable{
    font-family:'Microsoft Yahei','微软雅黑',Arial,Verdana;
    font-size:12px;
    margin-top: 6px;
}
.searchTable td{height:30px;}
.searchTable li{ color:#FFF; float:right; height:22px; line-height:22px;margin-left:10px; text-align:center; width:82px; }
.searchTable li a{display:block;color:#ffffff;}
.flexigrid .hDiv{height:24px;}
</style>
</head>
<body>
    <decorator:body />
<script>
//获取列文本
//pid:id,cell_idx:json data index
function get_cell_text(pid, cell_idx) {
    //return jQuery('#row' + pid).children('td').eq(cell_idx).text();
    return jQuery("[id='row" + pid + "']").children('td').eq(cell_idx).text();
}
function set_cell_text(pid, cell_idx , context) {
    return jQuery('#row' + pid).children('td').eq(cell_idx).html(jQuery('#row' + pid).children('td').eq(cell_idx).html().replace(jQuery('#row' + pid).children('td').eq(cell_idx).text(),context));
}
//显示详细信息
function viewDetail(url){
    var rw= "300";//显示宽度
    $right = $("#detail");
    //只有未显示时执行
    if($right.width()==0){
        $list = $(".flexigrid");
        $rToolBar = $(".rightToolbar");
        $list.animate({width:$list.width() - rw});
        $rToolBar.animate({width:$rToolBar.width() - rw});
        $right.animate({ width: rw-5, borderWidth:'1px' },
            function(){
                $right.load(url);
            }
        );
    }else{
        $right.load(url);
    }
}
//post提交
function viewDetailPost(url,data){
    var rw= "300";//显示宽度
    $right = $("#detail");
    //只有未显示时执行
    if($right.width()==0){
        $list = $(".flexigrid");
        $rToolBar = $(".rightToolbar");
        $list.animate({width:$list.width() - rw});
        $rToolBar.animate({width:$rToolBar.width() - rw});
        $right.animate({ width: rw-5, borderWidth:'1px' },
            function(){
                $right.load(url,data,function(){});
            }
        );
    }else{
        $right.load(url,data,function(){});
    }
}
function closeDetail(){
    $right = $("#detail");
    var rw = $right.width() + 5;
    $list = $(".flexigrid");
    $rToolBar = $(".rightToolbar");
    $list.animate({width:$list.width() + rw});
    $rToolBar.animate({width:$rToolBar.width() + rw});
    $right.empty();
    $right.animate({ width: 0, borderWidth:0});
    
}
function resizebDiv(){
    var sDivH = $(".sDiv").height();
    if($(".sDiv").is(":hidden")){
        $(".bDiv").height($(".bDiv").height() + sDivH - 1);
    } else {
        $(".bDiv").height($(".bDiv").height() - sDivH - 1);
    }
}
//将分钟转换成天
function minute2day(minute){
	if(minute > 0){
		var day = parseInt(minute / (24 * 60));
		var hour = parseInt((minute % (24 * 60)) / 60);
		var min = (minute % (24 * 60)) % 60;
		var result;
		if(day != 0){
			reuslt = day + "天" + hour + "小时" + min + "分钟";
		} else {
			if(hour != 0){
				reuslt = hour + "小时" + min + "分钟";
			} else {
				reuslt = 0;
			}
		} 
		return reuslt;
	} else {
		return 0;
	}
}
$(window).resize(function() {
    var winHeight = $(window).height();
    var sDivH = $(".sDiv").height();
    if($(".sDiv").is(":hidden")){
        $(".bDiv").height(winHeight - 158);
    } else {
        $(".bDiv").height(winHeight - 158 - sDivH - 1);
    }
});

$(function(){
    //点击查询区域,隐藏详细
    //  $(".tDiv").click(function() {closeDetail();});
    
    //点击查询按钮，隐藏详细
	$("#searchClone").click(
        function() {
            var detail_width = $("#detail").width();
            var sDivH = $(".sDiv").height();
            if(sDivH <20 && detail_width > 0){
                closeDetail();
            }
    });

    //点击查询form中的按钮，以藏详细
	   $(".pageButon1").click(function() {
        var detail_width = $("#detail").width();
        if(detail_width > 0){
            closeDetail();
        }
        });
    
    //点击分页区域，隐藏详细
    //$(".pDiv").click(function() {closeDetail();});
});

</script>
</body>
</html>