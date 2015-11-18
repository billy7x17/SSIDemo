<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="iamp-identify" uri="/WEB-INF/taglib/iamp-identify"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<script type="text/JavaScript">
//请保持原有验证功能，因为在其他后台程序中需要该数据的完整性和一致性。
$(function(){

	var msg = "<s:property value='msg'/>";
	var errorMsg = "<s:property value='errorMsg'/>";
	if(msg != null && msg != ""){
		$("#msgTip").html(msg);
	    $(".messages").show("slow");
		$(".messages").delay(5000).hide("slow");
	}else if(errorMsg != null && errorMsg != ""){
		$("#msgTip").attr("class","msgErrors");
		$("#msgTip").html(errorMsg);
	    $(".messages").show("slow");
		$(".messages").delay(5000).hide("slow");
	}
	//去掉鼠标移入移出效果
	$("tr").each(function(){
		var tr_bgColor = $(this).css("background-color");
		$(this).hover(function(){
		    $(this).css("background-color","transparent");
		},function(){
			$(this).css("background-color","transparent");
		});
	});
	var typeId = '<s:property value="mibinfoDomain.typeId"/>';
	if (typeId=='43' ||typeId=='44' || typeId=='45') {  
        $("#mibParentIdTR").hide();
        $("#mibCollectionTR").hide();
        $("#ifShowLineTR").show();
    }else if(typeId =='33' || typeId=='34'||typeId=='42'){
    	$("#ifShowLineTR").hide();
    	$("#mibParentIdTR").show();
        $("#mibCollectionTR").show();
    }else if(typeId=='32'  || typeId=='31' ){
    	$("#ifShowLineTR").show();
    	$("#mibParentIdTR").show();
        $("#mibCollectionTR").show();
    }else if(typeId=='46' ||typeId=='36' ||typeId=='37'){
    	$("#ifShowLineTR").show();//只能显示在折线上.
    	$("#mibParentIdTR").hide();
    	$("#mibCollectionTR").hide();
    }else if(typeId=='35'){
    	$("#ifShowLineTR").show();
    	$("#mibParentIdTR").show();
        $("#mibCollectionTR").show();//CPU和物理磁盘百分比显示在折线上 其他不能显示在折线上。
    }
});

$(function(){
	$.formValidator.initConfig({formid:"mainForm",wideword:false});

	//名称
	$("#mibName")
	    .formValidator
	    (
	    	{onshow:'<s:text name="device.js.onshow.MibName" />'
		    	,onfocus:'<s:text name="device.js.onshow.MibName" />'
			    ,oncorrect:'<s:text name="mib.js.oncorrect" />'}
	    )
	    .inputValidator
	    ({
	   		min:1,max:32, 
	   		empty:
	   			{	
	   				leftempty:false,
	   				rightempty:false,
	           	 	emptyerror:'<s:text name="mib.js.emptyerror" />'
	           	},
	         onerror:'<s:text name="mib.js.not.null.name" />',
	         onerrormax:'<s:text name="mib.js.onshow.32char" />'
	     });

		//描述验证
	
	   $("#description").formValidator({
			onshow : '<s:text name="device.js.onshow.MibDesc" />',
			onfocus : '<s:text name="device.js.onshow.MibDesc" />',
			oncorrect : '<s:text name="mib.js.oncorrect" />'
		}).inputValidator({
			min : 0,
			max : 100,
			onerrormax : '<s:text name="mib.js.200char" />'
		});

		//父节点
		$("#mibParentId").formValidator({
			onshow : '<s:text name="mib.headerValue" />',
			onfocus : '<s:text name="mib.headerValue" />',
			oncorrect : '<s:text name="mib.js.oncorrect" />'
		}).inputValidator({
			min : 0,
			max : 200,
			onerrormax : '<s:text name="mib.headerValue" />'
		});

		//设备类型
		$("#typeId").formValidator({
			onshow : '<s:text name="mib.headerValue" />',
			onfocus : '<s:text name="mib.headerValue" />',
			oncorrect : '<s:text name="mib.js.oncorrect" />'
		}).inputValidator({
			min : 1,
			max : 64,
			empty : {
				leftempty : false,
				rightempty : false,
				emptyerror : '<s:text name="mib.js.emptyerror" />'
			},
			onerror : '<s:text name="mib.headerValue" />',
			onerrormax : '<s:text name="mib.headerValue" />'
		});

		//指标单位
		$("#indexUnit").formValidator({
			onshow : '<s:text name="mib.headerValue" />',
			onfocus : '<s:text name="mib.headerValue" />',
			oncorrect : '<s:text name="mib.js.oncorrect" />'
		}).inputValidator({
			min : 1,
			max : 64,
			empty : {
				leftempty : false,
				rightempty : false,
				emptyerror : '<s:text name="mib.js.emptyerror" />'
			},
			onerror : '<s:text name="mib.headerValue" />',
			onerrormax : '<s:text name="mib.headerValue" />'
		});
		//指标分组
		$("#indexGroup").formValidator({
			onshow : '<s:text name="mib.headerValue" />',
			onfocus : '<s:text name="mib.headerValue" />',
			oncorrect : '<s:text name="mib.js.oncorrect" />'
		}).inputValidator({
			min : 1,
			max : 64,
			empty : {
				leftempty : false,
				rightempty : false,
				emptyerror : '<s:text name="mib.js.emptyerror" />'
			},
			onerror : '<s:text name="mib.headerValue" />',
			onerrormax : '<s:text name="mib.headerValue" />'
		});

		//业务实例
		$("#systemId").formValidator({
			onshow : '<s:text name="mib.headerValue" />',
			onfocus : '<s:text name="mib.headerValue" />',
			oncorrect : '<s:text name="mib.js.oncorrect" />'
		}).inputValidator({
			min : 0,
			max : 64,
			empty : {
				leftempty : false,
				rightempty : false,
				emptyerror : '<s:text name="mib.js.emptyerror" />'
			},
			onerror : '<s:text name="mib.headerValue" />',
			onerrormax : '<s:text name="mib.headerValue" />'
		});

		//页面加载后如果指标分组是进程或功耗则隐藏是否显示在折线图上单选按钮
		//groupifShowLine();
	});

	function validate() {
		var formCheck = $.formValidator.pageIsValid("1");
		
		//校验修改后的指标单位，指标分组，以及是否显示在折线图上是否正确
		if (!ifHaveSameUnitAndUnderFiveLine()) {
			return false;
		}

		if (formCheck) {
			$("#mainForm").submit();
		} else {
			return false;
		}
	}

	function validateOid() {
		var oidValue = $("#oid").val();

		if (oidValue.charAt(oidValue.length - 1) == '.') {
			return false;
		} else if (oidValue.charAt(0) == '.') {

			oidValue = oidValue.substring(1, oidValue.length);

		}

		var oidValueArr = oidValue.split('.');
		if (oidValueArr.length <= 1) {
			return false;
		}

		for ( var i = 0; i < oidValueArr.length; i++) {

			if (isNaN(oidValueArr[i])) {
				return false;
			}
		}

		return true;
	}
	function validateTextarea() {
		if ($("#description").val().length > 200) {
			$("#description").val($("#description").val().substring(0, 200));
		}
	}
<%-- 判断是否是相同单位，或是否超过5条线 --%>
function ifHaveSameUnitAndUnderFiveLine(){
	var ifhaveflag = false;
	//判断用户在点击按钮前是否已经选择指标单位和指标分组
	if ($("#typeId").val()=='') {
		$('#ifShowLine0').attr('checked','checked');
		return ifhaveflag;
	}else if ($("#indexUnit").val()=='') {
		$('#ifShowLine0').attr('checked','checked');
		return ifhaveflag;
	}else if ($("#indexGroup").val()=='') {
		$('#ifShowLine0').attr('checked','checked');
		return ifhaveflag;
	} else {
		//判断指标是否可以显示在折线图上
		if ($("input[name='ifShowLine']:checked").val() == 1) {
			$.ajax({
				type:"POST",
				url:"showLineJudge.action",
				dataType:"text",
				async:false,
				data:{typeId:""+$("#typeId").val(),indexUnit:""+$("#indexUnit").val(),indexGroup:""+$("#indexGroup").val()},
				success:function(json) {
					//var result = eval("("+json+")");
					//判断是否是相同单位或曲线个数是否过多
					if (''!=json) {
						alert(json);
					} else {
						ifhaveflag = true;
					}
				}
			});
		} else {
			//不用判断是否显示在折线图上
			ifhaveflag = true;
		}
		
		if(ifhaveflag==false){
            return false;
        }

		//判断OId是否重复
       var oRepeatJudgeFlag = false;
       var promtValue="";
        $.ajax({
            type:"POST",
            url:"oRepeatJudge.action",
            dataType:"text",
            async:false,
            data:{typeId:""+$("#typeId").val(),oid:""+'<s:property value="mibinfoDomain.oid"/>',mibId:'<s:property value="mibinfoDomain.mibId"/>'},
            success:function(json) {
                if (''==json) {
                    oRepeatJudgeFlag = true;
                }else{
    				promtValue = json;
    			}
            }
        });
        if (oRepeatJudgeFlag == true) {
            return true;
        } else {
            alert(promtValue);
            return false;
        }
        
	}
}

function changDeviceType(obj){
    var selectValue = $(obj).val();
   
    if (selectValue=='43' ||selectValue=='44' || selectValue=='45') {  
        $("#mibParentIdTR").hide();
        $("#mibCollectionTR").hide();
        $("#ifShowLineTR").show();
        
    }else if(selectValue =='33'  ||selectValue=='42'){
    	$("#ifShowLineTR").hide();
    	$("#mibParentIdTR").show();
        $("#mibCollectionTR").show();
    }else if(selectValue=='32'|| selectValue=='31' || selectValue=='34' ){
    	$("#ifShowLineTR").show();
    	$("#mibParentIdTR").show();
        $("#mibCollectionTR").show();
    }else if(selectValue=='46' ||selectValue=='36' ||selectValue=='37'){
    	$("#ifShowLineTR").show();//只能显示在折线上.
    	$("#mibParentIdTR").hide();
    	$("#mibCollectionTR").hide();
    }else if(selectValue=='35'){
    	$("#ifShowLineTR").show();
    	$("#mibParentIdTR").show();
        $("#mibCollectionTR").show();//CPU和物理磁盘百分比显示在折线上 其他不能显示在折线上。
    }
	getMibGroupValue(selectValue);
    var deviceTypeId = '-100';
    if (null !=$("#typeId").val() && ''!=$("#typeId").val()) {
    	deviceTypeId = $("#typeId").val();
    }
    $.ajax({
        type:"post",
        url:"getParentMibInfoByDeviceType.action",
        dataType:"text",
        async: true,
        data:'deviceTypeId='+deviceTypeId,
        complete:function(json){
    		$("#mibParentId").empty();
            $("<option value=''>" + '<s:text name="common.lable.select" />' + "</option>").appendTo("#mibParentId");
            var jsontemp = json.responseText;
            var templateAry = [];
            
            templateAry = eval("("+jsontemp+")");//转换为json数组
            for(var k = 0 ; k < templateAry.length ; k++) {
                if(null != templateAry[k] ){
                    $("<option value=\'"+templateAry[k].mibId+"\'>"+templateAry[k].mibName+"</option>").appendTo("#mibParentId");//添加下拉框的option
                }
            }
        }, 
	     error: function(XMLHttpRequest, textStatus, errorThrown) {alert('获取性能指标父节点失败'); }
    });
}

function getMibGroupValue(typeId){
	$.ajax({
		url:"getMibGroupByDeviceTypeId.action?typeId="+typeId,
		type:"POST",
		dataType:"json",
		success:function(data){
			$("#indexGroup").empty();
			$("<option value=''>"+'<s:text name="common.lable.select" />'+"</option>").appendTo("#indexGroup");
			if(data!=null){
				for(var i=0;i<data.length;i++){
					$("<option value=\""+data[i].indexGroup+"\">"+data[i].groupName+"</option>").appendTo("#indexGroup");
				}
			}
		},
		error:function(XMLHttpRequest,textStatus,errorThrown){
			
		}
	});
}


//请保持原有验证功能，因为在其他后台程序中需要该数据的完整性和一致性。
</script>
<!-- 当前位置 -->
<div class="rightToolbar">
  <div class="rightToolbarCrumbs">
   <h2 class="sec-label"><s:text name="function.title"/></h2>
   <ul class="bread-cut">
	  <li><s:text name="menu.title"/></li>
	  <li><s:text name="common.lable.arrow"/> </li>
	  <li><s:text name="function.title"/> </li>
	  <li><s:text name="common.lable.arrow"/></li>
	  <li><s:text name="common.title.edit"></s:text>
	</ul>
  </div>
</div>

<div class="rightDisplayFwSj">
	<div class="formGroup"> 
		<div class="formLabel"><s:text name="common.lable.baseInfo"/><s:text name="common.lable.line"/></div>	 	  	
      	<form id="mainForm" method="post" action="mibInfoUpdate.action" > 
       	   <s:hidden id="oid" name="oid" />
     	   <table class="addTable" align="center" cellpadding="0" cellspacing="0" >
             <tr>
	            <td class="tdLabel" align="right" valign="top">
	            	<s:text name="common.red.point"/><s:text name="mib.mibName"/><s:text name="common.lable.point"/>
	            </td>
	            <td class="tdInput" valign="top">
            	  <input type="text" id="mibName" name="mibinfoDomain.mibName" class="input" maxlength="100" value="<s:property value="mibinfoDomain.mibName"/>"/>
            	</td>
           		<td class="tdVad" valign="top"><div id="mibNameTip"></div></td>
         	</tr>
           
            <tr>
	            <td class="tdLabel" align="right" valign="top">
	            	<s:text name="common.red.point"/><s:text name="mib.mibDeviceName"/><s:text name="common.lable.point"/>
	            </td>
	            <td class="tdInput" valign="top">
            	   <s:select list="deviceListType" listKey="typeId" listValue="typeName" name="mibinfoDomain.typeId" id="typeId" cssClass="newSelect" value="%{mibinfoDomain.typeId}" headerKey="" headerValue="%{getText('common.lable.select')}" onchange="changDeviceType(this)">          
                   </s:select>
                </td>
            	<td class="tdVad" valign="top"><div id="typeIdTip"></div></td>
            </tr>
         
		 	<tr>
            	<td class="tdLabel" align="right" valign="top">
            		<s:text name="common.red.point"/><s:text name="mib.oid"/><s:text name="common.lable.point"/>
            	</td>
	            <td class="tdInput" valign="top" style="color: #666;">
	             <s:property value="mibinfoDomain.oid"/>
	            </td>
            	<td class="tdVad" valign="top"><div id="oidTip"></div></td>
            </tr>
         
         	<tr>
               <td class="tdLabel" align="right" valign="top">
               	   <s:text name="common.red.point"/><s:text name="mib.indexGroup"/> <s:text name="common.lable.point" />
               </td>
               <td  class="tdInput" valign="top">
              		<s:select list="groupList" listKey="indexGroup" listValue="groupName"  id="indexGroup" name="mibinfoDomain.indexGroup" cssClass="newSelect" value="%{mibinfoDomain.indexGroup}"   headerKey="" headerValue="%{getText('common.lable.select')}">
              		</s:select>
               </td>
           	   <td class="tdVad" valign="top"><div id="indexGroupTip"></div></td>
          	</tr>
         
	        <tr>
            	<td class="tdLabel" align="right" valign="top">
            		<s:text name="common.red.point"/><s:text name="mib.indexUnit"/> <s:text name="common.lable.point"/>
            	</td>
            	<td class="tdInput" valign="top">
             		<s:select list="unitMap" listKey="key" listValue="value" id="indexUnit" name="mibinfoDomain.indexUnit" cssClass="newSelect" value="%{mibinfoDomain.indexUnit}"  headerKey="" headerValue="%{getText('common.lable.select')}">
             		</s:select>
            	</td>
            	<td class="tdVad" valign="top"><div id="indexUnitTip"></div></td>
	        </tr>
         
		 
             <tr id="mibParentIdTR">
	            <td class="tdLabel" align="right" valign="top">
	            	<s:text name="mib.parent.point"/><s:text name="common.lable.point"/>
	            </td>
	            <td class="tdInput" valign="top">
	                <s:select list="parentMibInfoList" listKey="mibId" listValue="mibName" name="mibParentId" id="mibParentId" value="%{mibinfoDomain.parentId}" cssClass="newSelect" headerKey="" headerValue="%{getText('common.lable.select')}">          
	                </s:select>
	            </td>
	            <td class="tdVad" valign="top"><div id="mibParentIdTip"></div></td>
             </tr>
         
		     <tr id="mibCollectionTR">
                <td class="tdLabel" align="right" valign="top">
                	<s:text name="mib.is.collection1"/><s:text name="common.lable.point"/></td>
                <td  class="tdInput" valign="top" colspan="2">
                    <div style="float:left;color: #666; ">
               	      <input  type="radio" id="isCollection0" name="mibinfoDomain.isCollection" value="0" <s:if test="mibinfoDomain.isCollection == 0">checked</s:if>/><s:text name="mib.is.collection3"/>
                    </div>
		            <div style="float:left;margin-left:50px;color: #666;">
		                <input  type="radio" id="isCollection" name="mibinfoDomain.isCollection" value="1" <s:if test="mibinfoDomain.isCollection == 1">checked</s:if> /><s:text name="mib.is.collection2"/>
		            </div>
                	<div id="alarmGradeTip" style="float:left; margin-left:70px;"></div>
                </td>
              </tr>
		 
		 	 <tr id="ifShowLineTR">
	           <td class="tdLabel" align="right" valign="top">
	           		<s:text name="mib.ifShowLine"/><s:text name="common.lable.point"/>
	           	</td>
	           <td  class="tdInput" valign="top" colspan="2">
	           <div style="float:left;color: #666; ">
	               <input  type="radio" id="ifShowLine0" name="mibinfoDomain.ifShowLine" value="0" <s:if test="mibinfoDomain.ifShowLine == 0">checked</s:if>/><s:text name="mib.is.collection3"/>
	           </div>
	           <div style="float:left;margin-left:50px;color: #666;">
	               <input  type="radio" id="ifShowLine" name="mibinfoDomain.ifShowLine" value="1" <s:if test="mibinfoDomain.ifShowLine == 1">checked</s:if> /><s:text name="mib.is.collection2"/>
	           </div>
	           <div id="ifShowLineTip" style="float:left; margin-left:70px;"></div></td>
	         </tr>
		 	
              <tr>
	             <td class="tdLabel" align="right" valign="top"><s:text name="mib.agentDesc"/><s:text name="common.lable.point"/></td>
	             <td  class="tdInput" valign="top">        
                      <textarea name="mibinfoDomain.description" id="description" class="newTextarea" onkeydown="validateTextarea();" onkeyup="validateTextarea();"><s:property value="mibinfoDomain.description"/></textarea>
	             </td>
	             <td class="tdVad" valign="top"><div id="descriptionTip"></div></td>
	  		  </tr> 
          </table>
           <input type="hidden" name="mibinfoDomain.mibId" id="mibId" value="<s:property value="mibinfoDomain.mibId"/>"/>   
       </form>
    </div> 
    <!-- 提示信息start -->
    <div class="messages succcess" style="top:25px;left:37%;">
  		<div id="msgTip" class="msgSuccess"></div>
    </div>
    <!-- 提示信息end-->
    <%-- 按钮栏 --%>
	<div class="serverEventButton">
	    <ul class="rightToolButtonjhjFwSj">
	        <li><a href="#" class="buttonFwSj" onclick="validate();return false;"><s:property value="getText('common.button.add')"/></a></li>
	        <li><a href="mibList.action" class="buttonGrey" ><s:property value="getText('common.button.cancel')" /></a></li>
	    </ul>
	</div>	              
</div>  
	