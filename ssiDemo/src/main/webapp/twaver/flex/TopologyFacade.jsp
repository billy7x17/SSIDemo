<%@page import="java.io.IOException"%>
<%@page import="java.io.FileNotFoundException"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.util.Properties"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- saved from url=(0014)about:internet -->
<%
	Properties prop = new Properties();

	String realpath = request.getRealPath("/WEB-INF/classes/conf/other");  
	try{  
		//读取配置文件
		FileInputStream in = new FileInputStream(realpath+"/topo.properties");  
		prop.load(in);  
	}  
	catch(FileNotFoundException e){  
	    out.println(e);  
	}  
	catch(IOException e){
		out.println(e);
	} 
%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en"> 
    <!-- 
    Smart developers always View Source. 
    
    This application was built using Adobe Flex, an open source framework
    for building rich Internet applications that get delivered via the
    Flash Player or to desktops via Adobe AIR. 
    
    Learn more about Flex at http://flex.org 
    // -->
    <head>
        <title></title>
        <meta name="google" value="notranslate" />         
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <!-- Include CSS to eliminate any default margins/padding and set the height of the html element and 
             the body element to 100%, because Firefox, or any Gecko based browser, interprets percentage as 
             the percentage of the height of its parent container, which has to be set explicitly.  Fix for
             Firefox 3.6 focus border issues.  Initially, don't display flashContent div so it won't show 
             if JavaScript disabled.
        -->
        <style type="text/css" media="screen"> 
            html, body  { height:100%; }
            body { margin:0; padding:0; overflow:auto; text-align:center; 
                   background-color: #ffffff; }   
            object:focus { outline:none; }
            #flashContent { display:none; }
        </style>
        
        
        <!-- liujingwei add begin -->
        <script src="<%=request.getContextPath()%>/javascript/jquery-1.6.1.min.js"></script>
        <script type="text/javascript" src="swfobject.js"></script>
		<script type="text/javascript" src="rightClick.js"></script>
		<!-- add by wengcz 2013-2-26,操作系统安装使用dialog -->
		<link href="<%=request.getContextPath()%>/themes/default/styles/dialog.css" rel="stylesheet" type="text/css">
		<script src="<%=request.getContextPath()%>/javascript/dialog.js"></script>
		<script type="text/javascript">

		/*定时刷新告警全量jsp文件*/
		setInterval("refreshAlarm()", <%=prop.getProperty("alarmInteval")%>);
		/*定时刷新告警声音提醒*/
		setInterval("getAlarmCount4Sound()", <%=prop.getProperty("soundInteval")%>);
		
		/*全局变量 -- 告警数组：每三秒从后台进行同步，便于前台知道节点是否有状态*/
		var alarmList = [];
		
		function refreshAlarm(){
			$.ajax({
				 type: "POST",
				 url: "topoAlarmGraph.action",
				 dataType:"json",
				 success:function(arr){
					 alarmList = [];
					 for(i in arr)
						 alarmList.push(arr[i].agentId);
				 }
			});
		}

      function getAlarmCount4Sound(){
        $.ajax({
          type: "POST",
          url: "alarmCount.action",
          data: {'siteId' : <s:property value="#session.topoSiteId" escape="true" />},
          success: function(result){
            if (0 != result) {
              Play('<%=prop.getProperty("soundFileName")%>');
            };
          }
        });
      }
		</script>
		
        <script type="text/javascript">
           /**
            * 此方法用于与flex进行数据交互
            * 用于给flex组件提供基础的配置参数
            */
           function config(){
        	   
   				var sessionSiteId = '<s:property value="#session.userInfo.siteId" />';
        	   
                var config = {};
                var host = "<%=request.getContextPath()%>/twaver/";
                //构建交互的url
                config.construction = host + "MainMVC.jsp";
                //增量交互的url
                config.increment = host + "IncrementMVC_" + sessionSiteId + ".jsp";
                
                //详细画面宽度
                config.detailPanelWidth = "410";
                //详细画面高度
                config.detailPanelHeight = "550";
                
                //双击选择交互的url
                config.select = host + "selectMVC.jsp";
                
                //双击交互方法
                config.detailBeforeButtonClickFunction = "detailBeforeButtonClick";
                
                //首面的name，用于区分不同面的标识值
                //当面是通过钻取元素打开时，此属性的值为钻取元素的seq值
                //与后台交互时，作为表单name的值提交
                config.topName = "rootLayer" + '<s:property value="#session.topoSiteId" escape="true" />';
                //增量同步时间间隔，毫秒单位
                config.incrementTimer = <%=prop.getProperty("alarmInteval")%>;
                //可以使用控制台
                config.console = true;
                
                /*
                 * 跨域访问策略文件url
                 * 此属性为数组，目的是为了跨多个域准备
                 */ 
                var policyFileList = new Array();
                policyFileList[0] = "http:// localhost:8080/direc/crossdomain.xml"
                config.policyFileList = policyFileList;
                
                /*
                 * 字体设置
                 */
                 config.font = {
                   name : "Yahei",
                   size : "13"
                 }
                 
                 /*
                 * 调试配置
                 */
                 config.debug = {
                   console : true
                 }
                
                  /*
                  * 加载资源
                  */
                  config.plugins = {
                    setting : {},
                    items : [
                      {
                        name : "honeywell",
                        url  : "<%=request.getContextPath()%>/twaver/module/HoneywellPlugin.swf"
                      }
                    ]
                  }//end resouces

                return config;
            }
            
          	//鼠标经过，图片颜色改变
          	$(".opera li a").hover(
          		function(){
          			var imgBg = $(this).children(".icon").css("background-image").replace(".png","-white.png");
          			$(this).children(".icon").css("background-image",imgBg)
          			
          		},function(){
          			var imgBg = $(this).children(".icon").css("background-image").replace("-white.png",".png");
          			$(this).children(".icon").css("background-image",imgBg)
          		}
          	);
            	
          	/*鼠标右键前由flex传入参数的方法*/
            function beforeRightMenu(type, value , x , y){
              if(type == 'node' && value != 'root'){
	              $.ajax({
	            	 async: false,
					 type: "POST",
	   				 url: "rightClickMenu.action",
	   				 data: {"agentId" : value},
	   				 dataType: 'text',
	   				 success: function(text){
	   					 if (text) {
	   						$('.opera').html(text);
						 	$(".opera").css("display","block");
			       			$(".opera").css("position","absolute");
			       			$(".opera").css("z-index","999");
			       			if (x+$('.opera').width()>=$(document).width()) {
			       				$(".opera").css("left",x - $('.opera').width() - 16);
							}else{
								$(".opera").css("left",x);
							}
			       			$(".opera").css("top",y);
						}
	   				 }
	   				}); 
              }
	            
            }
            
          	/*播放声音*/
            function  Play(sound)
            {
                  if(navigator.appName == "Microsoft Internet Explorer")
                  {
                    document.getElementsByTagName("bgsound").remove;

                    var snd = document.createElement("bgsound");
                    document.getElementsByTagName("body")[0].appendChild(snd);
                    snd.src = sound;
                 }
                 else
                 {
                    document.getElementsByTagName("audio").remove;
                    var audio = document.createElement('audio');

                    var source = document.createElement('source');   
                    source.type = "audio/mpeg";
                    source.type = "audio/mpeg";
                    source.src = sound;   
                    source.autoplay = "autoplay";
                    source.controls = "controls";
                    audio.appendChild(source); 
                    audio.play();
                 }
                    
            }

          	/*节点双击事件*/
            function detailBeforeButtonClick(args){
              var seq = args[0];
              var name = args[1];
              /* alert("seq:" + args[0]);
              alert("name:" + args[1]);
              alert("image:" + args[2]);
              alert("type:" + args[3]); */
              //viewDetail("deviceDetail.action?deviceDomain.agentId=" + seq + "&deviceDomain.agentName=" + name + "&isTopo=true");
              
              /*让IE8浏览器支持Array.indexOf函数*/
              if (!Array.prototype.indexOf)
              {
                Array.prototype.indexOf = function(elt /*, from*/)
                {
                  var len = this.length >>> 0;
                  var from = Number(arguments[1]) || 0;
                  from = (from < 0)
                       ? Math.ceil(from)
                       : Math.floor(from);
                  if (from < 0)
                    from += len;
                  for (; from < len; from++)
                  {
                    if (from in this &&
                        this[from] === elt)
                      return from;
                  }
                  return -1;
                };
              }
              
       		  if(-1 != alarmList.indexOf(seq))
              	viewDetail("topoAlarmDetail.action?agentId=" + seq);
       		  else{
       			  closeDetail();
       			  window.alert("该节点没有告警");
       		  }
             /*  return false; */
            }
                
            /*设备详情*/
			function getDetail(agentId , agentName){
				viewDetail("deviceDetail.action?deviceDomain.agentId=" + agentId + "&deviceDomain.agentName=" + agentName + "&isTopo=true");
			}
            
            /*告警详情*/
            function getAlarm(agentId){
            	viewDetail("topoAlarmDetail.action?agentId=" + agentId);
            }
            
          //显示详细信息
          function viewDetail(url){
              var rw= "322";//显示宽度
              $right = $("#detail");
              //只有未显示时执行
              if($right.width()==0){
                  /* $list = $("#TopologyFacade");
                  $list.animate({width:$list.width() - rw}); */
                  $right.animate({ width: rw},
                      function(){
                          $right.load(url);
                      }
                  );
                  $right.css("border-left","5px solid #d8d8d8");
              }else{
                  $right.load(url);
              }
              
              var winHeight = $(window).height() - 17; //17px为workarea的margin-top
        	  $(".baseTab").height(winHeight - ($(".BasicInformation").height()+11) - (18 + 12*2));
              /* console.log($(".baseTab")); */
          }
          
          //关闭详细信息
          function closeDetail(){
        	    $right = $("#detail");
        	    var rw = $right.width();
        	    /* $list = $("#TopologyFacade");
        	    $list.animate({width:$list.width() + rw}); */
        	    $right.empty();
        	    $right.animate({ width: 0});
        	    $right.css('width',0);
        	    $right.css("border-left","none");
        	}
        </script>
        
        <!-- liujingwei add finish -->
        
        
        
        <!-- Enable Browser History by replacing useBrowserHistory tokens with two hyphens -->
        <!-- BEGIN Browser History required section -->
        <link rel="stylesheet" type="text/css" href="history/history.css" />
        <link rel="stylesheet" type="text/css" href="css/detail.css" />
        <link href="<%=request.getContextPath()%>/stylesheets/jquery-ui.css" rel="stylesheet" type="text/css">
		<script src="<%=request.getContextPath()%>/javascript/jquery-ui-1.8.16.custom/development-bundle/ui/jquery-ui-1.8.16.custom.js"></script>
        <script type="text/javascript" src="history/history.js"></script>
        <!-- END Browser History required section -->  
        <script type="text/javascript" src="swfobject.js"></script>
        <script type="text/javascript">
            // For version detection, set to min. required Flash Player version, or 0 (or 0.0.0), for no version detection. 
            var swfVersionStr = "11.1.0";
            // To use express install, set to playerProductInstall.swf, otherwise the empty string. 
            var xiSwfUrlStr = "playerProductInstall.swf";
            var flashvars = {};
            var params = {};
            params.quality = "high";
            params.bgcolor = "#ffffff";
            params.allowscriptaccess = "sameDomain";
            params.allowfullscreen = "true";
            
            //右键屏蔽
            params.wmode = "opaque";
            params.menu = "false";
            
            var attributes = {};
            attributes.id = "TopologyFacade";
            attributes.name = "TopologyFacade";
            attributes.align = "left";
            swfobject.embedSWF(
                "TopologyFacade.swf", "flashContent", 
                "100%", (window.parent.document.getElementById("mainTest").offsetHeight - 18)+"px", 
                swfVersionStr, xiSwfUrlStr, 
                flashvars, params, attributes);
            // JavaScript enabled so display the flashContent div in case it is not replaced with a swf object.
            swfobject.createCSS("#flashContent", "display:block;text-align:left;");
        </script>
		
		<!-- ##### Google Analytics begin ##### -->
		<script type="text/javascript">
		   var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
		   document.write(unescape("%3Cscript src='ga.js' type='text/javascript'%3E%3C/script%3E"));
		</script>
		
		<script type="text/javascript">
		try {
		   var pageTracker = _gat._getTracker("UA-7655349-1");
		   pageTracker._trackPageview();
		} catch(err) {}
		
		</script>
		<!-- ##### Google Analytics end ##### -->
    </head>
    <body onload="RightClick.init();">
        <!-- SWFObject's dynamic embed method replaces this alternative HTML content with Flash content when enough 
             JavaScript and Flash plug-in support is available. The div is initially hidden so that it doesn't show
             when JavaScript is disabled.
        -->
        
        <!--[if !IE]>-->
           <div id="flashContent" style="display:none" style="border:20px solid:#F11">
	            <p>
	                To view this page ensure that Adobe Flash Player version 
	                11.1.0 or greater is installed. 
	            </p>
	            <script type="text/javascript"> 
	                var pageHost = ((document.location.protocol == "https:") ? "https://" : "http://"); 
	                document.write("<a href='http://www.adobe.com/go/getflashplayer'><img src='" 
	                                + pageHost + "www.adobe.com/images/shared/download_buttons/get_flash_player.gif' alt='Get Adobe Flash player' /></a>" ); 
	            </script> 
	        </div>
        <!--<![endif]-->
        <!--[if IE]> 
        <div id="flashContentDiv">
	        <div id="flashContent">
	            <p>
	                To view this page ensure that Adobe Flash Player version 
	                11.1.0 or greater is installed. 
	            </p>
	            <script type="text/javascript"> 
	                var pageHost = ((document.location.protocol == "https:") ? "https://" : "http://"); 
	                document.write("<a href='http://www.adobe.com/go/getflashplayer'><img src='" 
	                                + pageHost + "www.adobe.com/images/shared/download_buttons/get_flash_player.gif' alt='Get Adobe Flash player' /></a>" ); 
	            </script> 
	        </div>
        </div>
        <![endif]-->
        <noscript>
            <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="100%" height="100%" id="TopologyFacade" style="-moz-user-select:   none;">
                <param name="movie" value="TopologyFacade.swf" />
                <param name="quality" value="high" />
                <param name="bgcolor" value="#ffffff" />
                <param name="allowScriptAccess" value="sameDomain" />
                <param name="allowFullScreen" value="true" />
                
                <!--[if !IE]>-->
                <object type="application/x-shockwave-flash" data="TopologyFacade.swf" width="100%" height="100%"  id="TopologyFacade">
                    <param name="quality" value="high" />
                    <param name="bgcolor" value="#ffffff" />
                    <param name="allowScriptAccess" value="sameDomain" />
                    <param name="allowFullScreen" value="true" />
                <!--<![endif]-->
                <!--[if gte IE 6]>-->
                    <p> 
                        Either scripts and active content are not permitted to run or Adobe Flash Player version
                        11.1.0 or greater is not installed.
                    </p>
                <!--<![endif]-->
                    <a href="http://www.adobe.com/go/getflashplayer">
                        <img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash Player" />
                    </a>
                <!--[if !IE]>-->
                </object>
                <!--<![endif]-->
            </object>
        </noscript>
        <div id="detail" class="view-detail"></div>
        <ul class="opera" id="opera" oncontextmenu='return false;'>
        </ul>
   </body>
	<script type="text/javascript">
		
	   /* document.getElementById('opera').onmouseover = function(e){  
		    if( !e ) e = window.event;  
		    var reltg = e.relatedTarget ? e.relatedTarget : e.fromElement;  
		    while( reltg && reltg != this ) reltg = reltg.parentNode;  
		    if( reltg != this ){  
		    	 $('.opera').hide(); 
		    }  
		}   */
		   
	   /*右键菜单 鼠标移出时消失*/
		document.getElementById('opera').onmouseout = function(e){  
		    if( !e ) e = window.event;  
		    var reltg = e.relatedTarget ? e.relatedTarget : e.toElement;  
		    while( reltg && reltg != this ) reltg = reltg.parentNode;  
		    if( reltg != this ){  
		    	$('.opera').hide();
		   }  
		}  
	</script>
</html>
