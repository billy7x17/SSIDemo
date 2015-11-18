<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="<%=request.getContextPath()%>/javascript/jquery-1.6.1.min.js"></script>
<style type="text/css">
.site
{
	/* background: red; */
	cursor:pointer;
	position: absolute;
	z-index: 100;
}
.person
{
	background: url("themes/blue/images/person.png") no-repeat;
}
</style>
<script type="text/javascript" language="javascript">
/*站点点击进入拓扑图区域坐标*/
var loc = 
	[
	{left:268.54775,top: 297.54855,	height:33.7302	,width:111.7544},
	{left:44.55725	,top: 573.54549,	height:15.17859	,width:15.17859},
	{left:100.43445,top: 603.67074,	height:15.17859	,width:15.17859},
	{left:143.78745,top: 585.35106,	height:15.17859	,width:15.17859},
	{left:172.5584	,top: 557.47574,	height:22.64742	,width:22.64742},
	{left:200.3506	,top: 530.41902,	height:15.17859	,width:15.17859},
	{left:223.9539	,top: 507.28974,	height:15.17859	,width:15.17859},
	{left:247.79805,top: 483.91953,	height:15.17859	,width:15.17859},
	{left:269.2337	,top: 462.47676,	height:15.17859	,width:15.17859},
	{left:290.66935,top: 441.27492,	height:15.17859	,width:15.17859},
	{left:316.6233	,top: 417.29076,	height:22.64742	,width:22.64742},
	{left:339,top: 393.08892,	height:15.17859	,width:15.17859},
	{left:363.7933	,top: 368.86383,	height:22.64742	,width:22.64742},
	{left:422,top: 310.69086,	height:15.17859	,width:15.17859},
	{left:472.1392	,top: 261.2044	, height:22.64742	,width:22.64742},
	{left:500.4131	,top: 233.38861,	height:15.17859	,width:15.17859},
	{left:523.77555,top: 208.09096,	height:15.17859	,width:15.17859},
	{left:565.0707	,top: 197.08074,	height:22.64742	,width:22.64742},
	{left:623.96915,top: 185.68447,	height:15.17859	,width:15.17859},
	{left:648.7767	,top: 161.10961,	height:15.17859	,width:15.17859},
	{left:675.13915,top: 135.9208	, height:22.64742	,width:22.64742},
	{left:733.76015,top: 132.9208	, height:15.17859	,width:15.17859},
	{left:781.2076	,top: 101.5999	, height:15.17859	,width:15.17859},
	{left:118.67765,top: 641.50078,	height:33.00741	,width:101.6387},
	{left:661.62085,top: 26.10603	, height:33.7302	,width:128.6139}
	];
/*告警图标显示坐标*/
var alarmLoc = 
[
	{left:260.87125	,top:306.94482},
	{left:6.2621		,top:531.49158},
	{left:73.2184		,top:626.79102},
	{left:245.1853	,top:600.325},
	{left:115.608		,top:543.87482},
	{left:274.32815	,top:541.98366},
	{left:164.98225	,top:496.00231},
	{left:365.6103	,top:493.83394},
	{left:174.8571	,top:449.50282},
	{left:365.85115	,top:455.52607},
	{left:201.3506	,top:404.68984},
	{left:421.24665	,top:408.54472},
	{left:308.04715	,top:348.55315},
	{left:512.28795	,top:327.11038},
	{left:392.5855	,top:243.82116},
	{left:577.31745	,top:249.12162},
	{left:453.2797	,top:189.85284},
	{left:534.44615	,top:177.32448},
	{left:649.8133	,top:213.46398},
	{left:595.3812	,top:144.07614},
	{left:640.661		,top:111.79152},
	{left:794.6586	,top:154.84543},
	{left:715.3245	,top:81.84364},
	{left:258.20655	,top:646.65612},
	{left:650.87115	,top:26.74323}
];
var color = ['red' , 'org' , 'yellow' , 'lemon'];
$(document).ready(function(){
	
	var offsetL = ($('body').width()-$("#map").width())/2;
	
	$("#map").css("margin-left",offsetL + 'px');
	
	/*取得后台数据*/
	var sites = <s:property value='sites'/>;
	var alarmGrades = <s:property value='alarmGrades'/>;
	var html ='';
	/*画站点区域*/
	var cssclass = "site";
	if (1 == sites.length) {
		cssclass += " person";
	}
	for ( var i = 0; i < sites.length; i++) {
		html += '<div id="site_' + sites[i].siteId + '" class="' + cssclass +'" onclick="clickParentMenu(\'告警管理\',\'alarm\',\'alarmManager.action?siteId='+sites[i].siteId +'&specifiedAction=alarmTopologyPCDiv\',this); return false;" style="height:';
		html += loc[sites[i].siteId - 1].height + 'px;width:' + loc[sites[i].siteId - 1].width +
		'px;left:' + (loc[sites[i].siteId - 1].left + offsetL) + 'px;top:' + loc[sites[i].siteId - 1].top +'px;"></div>';
	}
	
	/*debug*/
	 /* for ( var i = 0; i < 25; i++) {
		html += '<div class="site" style="height:16';
		html += 'px;width:16' +
		'px;left:' + (alarmLoc[i].left+offsetL) + 'px;top:' + alarmLoc[i].top +'px;"></div>';
		} */
	 
	/*画告警区域*/
	html += '<div id="gradeDiv">';
	 for ( var i = 0; i < alarmGrades.length; i++) {
		html += '<img src="themes/blue/images/warn-' + color[alarmGrades[i].grade] + '-16.png" onclick="clickParentMenu(\'告警管理\',\'alarm\',\'alarmManager.action?siteId='+(sites[alarmGrades[i].siteId-1]||sites[0]).siteId +'&specifiedAction=alarmTopologyPCDiv\',this); return false;"' +
		'style="cursor:pointer;position:absolute;z-index:101;left:' + (alarmLoc[alarmGrades[i].siteId-1].left+offsetL) + 'px;top:' + alarmLoc[alarmGrades[i].siteId-1].top + 'px;">';
	}
	 html += '</div>';
	$('body').append(html);
	
	setInterval("getGradePer10s()", 10000);
});

function clickParentMenu(tabtitle,tabid,taburl,obj){
	window.parent.clickMenu(tabtitle,tabid,taburl,obj);
}

function getGradePer10s(){
	$.ajax({
		method:"POST",
		url:"getGradesByAjax.action",
		dataType:"json",
		success:function(alarmGrades){
			var html = '';
			for ( var i = 0; i < alarmGrades.length; i++) {
				html += '<img src="themes/blue/images/warn-' + color[alarmGrades[i].grade] + '-16.png" style="position:absolute;z-index:101;left:' + (alarmLoc[alarmGrades[i].siteId-1].left+offsetL) + 'px;top:' + alarmLoc[alarmGrades[i].siteId-1].top + 'px;">';
			} 
			$('#gradeDiv').html(html);
		}
	});
}
</script>
</head>
<body style="margin: 0px">
<div id="map" style="background: url(/honeywell/themes/blue/images/station-img.png) no-repeat;height: 671px;width: 1158px;">
</div>

</body>
</html>