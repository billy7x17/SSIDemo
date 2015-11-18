<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="iamp-identify" uri="/WEB-INF/taglib/iamp-identify"%> 

<script type="text/JavaScript">

var newCount = 1;
var setting = {
        view: {
            addHoverDom: addHoverDom,
            removeHoverDom: removeHoverDom,
            selectedMulti: false
        },
        edit: {
            enable: true,
            editNameSelectAll: true,
            showRemoveBtn: function(treeId,treeNode){  
	            var level = treeNode.level;
	            if(level=='0'){
	                return false;  
	            } else{
	                return true;     
	            }
	         },
            showRenameBtn: true
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            beforeDrag: beforeDrag,
            beforeEditName: beforeEditName,
            beforeRemove: beforeRemove,
            beforeRename: beforeRename,
            onRemove: onRemove,
            onRename: onRename
        }
    };

    var zNodes =[
        { id:1, pId:0, name:'<s:text name="field.label.rultInfo"/>'+'('+'<s:text name="field.label.ruleRelation.and"/>'+')', open:true,relation:'&&'}
    ];
    var log;
    function beforeDrag(treeId, treeNodes) {
        return false;
    }
    function beforeEditName(treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        zTree.selectNode(treeNode);
        var level = treeNode.level;
        if(level=='0' || level=='1'){
        	editRule(zTree,treeNode);
        }else{
            editCondition(zTree,treeNode);
        }
		return false;
    }
    function beforeRemove(treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        zTree.selectNode(treeNode);
        return confirm('<s:text name="field.label.sureDelete"/>' + treeNode.name + "？");
    }
    function onRemove(e, treeId, treeNode) {
    }
    function beforeRename(treeId, treeNode, newName) {
    	 /**
        if (newName.length == 0) {
            alert("节点名称不能为空.");
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            setTimeout(function(){zTree.editName(treeNode)}, 10);
            return false;
        }
        **/
        return true;
    }
    function onRename(e, treeId, treeNode) {
    }
    function addHoverDom(treeId, treeNode) {
    	var level = treeNode.level;
        
        var sObj = $("#" + treeNode.tId + "_span");
        if (treeNode.editNameFlag || $("#addBtn_"+treeNode.id).length>0) return;
        var addStr = "<button type='button' class='add' id='addBtn_" + treeNode.id+ "' title=\'<s:text name="common.title.add" />\' onfocus='this.blur();'></button>";

        if(level=='0' || level=='1'){
            sObj.append(addStr);  
        }

        var btn = $("#addBtn_"+treeNode.id);
        if (btn) btn.live("click", function(){
        	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        	if(level=='1'){
        		addCondition(zTree,treeNode);
        	}else{
        		addRule(zTree,treeNode);
        	}
            return false;
        });
    };
    function removeHoverDom(treeId, treeNode) {
        $("#addBtn_"+treeNode.id).unbind().remove();
    };

    //关系redio的html
    function relationRadioHtml(relation){
    	 //修改时无法使用juery设置选中
        var html = '<div style="float:left;">';
        html = html + '<input type="radio"  id="relation" name="doamin.relation" value="&&" andRelation/><s:text name="field.label.ruleRelation.and"/>'
        html = html + '</div>';
        html = html + '<div style="float:left; margin-left:50px;">';
        html = html + '<input type="radio"  id="relation" name="doamin.relation" value="||" orRelation/><s:text name="field.label.ruleRelation.or"/>';
        html = html + '</div>';
        if(relation=='&&'){
            html = html.replace("andRelation","checked");
            html = html.replace("orRelation","");
        }else{
            html = html.replace("andRelation","");
            html = html.replace("orRelation","checked");
        }
        $("#relationTd").html(html);
    }

  //条件select的html
    function selectOptionHtml(){
    	 $("#ruleColumn").empty();//清空下拉框
         $('<option value="title" ><s:text name="field.label.ruleColumn.alarmTitle"/></option>').appendTo("#ruleColumn");
         $('<option value="text" ><s:text name="field.label.ruleColumn.alarmContent"/></option>').appendTo("#ruleColumn");
         $('<option value="rid" ><s:text name="field.label.ruleColumn.alarmIP"/></option>').appendTo("#ruleColumn");
         $('<option value="type" ><s:text name="field.label.ruleColumn.alarmType"/></option>').appendTo("#ruleColumn");
         $('<option value="lid" ><s:text name="field.label.ruleColumn.alarmLevel"/></option>').appendTo("#ruleColumn");
         $('<option value="nid" ><s:text name="field.label.ruleColumn.alarmOID"/></option>').appendTo("#ruleColumn");
         $('<option value="deviceType" ><s:text name="field.label.ruleColumn.alarmDevice"/></option>').appendTo("#ruleColumn");
         $('<option value="eventTime" ><s:text name="field.label.ruleColumn.alarmTime"/></option>').appendTo("#ruleColumn");
         $('<option value="objectID" ><s:text name="field.label.ruleColumn.alarmObjectID"/></option></option>').appendTo("#ruleColumn");

         $("#ruleColumnType").empty();//清空下拉框
         $('<option value="String" ><s:text name="field.label.ruleColumnType.string"/></option>').appendTo("#ruleColumnType");
         $('<option value="Int" ><s:text name="field.label.ruleColumnType.int"/></option>').appendTo("#ruleColumnType");
         $('<option value="Long" ><s:text name="field.label.ruleColumnType.long"/></option>').appendTo("#ruleColumnType");
       
         $("#ruleOperator").empty();//清空下拉框
         $('<option value="==" ><s:text name="field.label.ruleOperator.eq"/></option>').appendTo("#ruleOperator");
         $('<option value="!=" ><s:text name="field.label.ruleOperator.neq"/></option>').appendTo("#ruleOperator");
         $('<option value=">" ><s:text name="field.label.ruleOperator.great"/></option>').appendTo("#ruleOperator");
         $('<option value=">=" ><s:text name="field.label.ruleOperator.greatEqual"/></option>').appendTo("#ruleOperator");
         $('<option value="&lt;" ><s:text name="field.label.ruleOperator.less"/></option>').appendTo("#ruleOperator");
         $('<option value="&lt;=" ><s:text name="field.label.ruleOperator.lessEqual"/></option>').appendTo("#ruleOperator");
         $('<option value="contain" ><s:text name="field.label.ruleOperator.contains"/></option>').appendTo("#ruleOperator");
         $('<option value="notContain" ><s:text name="field.label.ruleOperator.ncontains"/></option>').appendTo("#ruleOperator");
         $('<option value="regexp" ><s:text name="field.label.ruleOperator.regular"/></option>').appendTo("#ruleOperator");
    }
    
  //添加条件
    function addCondition(zTree,treeNode){
    
        $("#ruleValue").val('');
        selectOptionHtml();
         
        $( "#addCondition" ).dialog({
            title : '<s:text name="field.title.addCondition" />',
            autoOpen : false,
            resizable: false,
            height: 400,
            width: 750,
            modal: true,
            show : "blind",
            buttons: {
            	'<s:text name="common.button.add" />': function() {
                    var ruleColumn = $("#ruleColumn").val();
                    var ruleOperator = $("#ruleOperator").val();
                    var ruleValue = $("#ruleValue").val();
                    var ruleColumnType = $("#ruleColumnType").val();
                    var ruleColumnText = $('#ruleColumn option:selected').text(); 
                    var ruleOperatorText = $('#ruleOperator option:selected').text();
                    
                    if($.trim(ruleColumn) =="" || $.trim(ruleOperator)=="" || $.trim(ruleValue)==""|| $.trim(ruleColumnType)==""){
                        //alert("策略字段、策略字段类型 、策略判断条件、策略值不能为空，请正确填写");
                        alert('<s:text name="field.label.ruleColumn" />'+','+'<s:text name="field.label.ruleColumnType" />,'+'<s:text name="field.label.ruleOperator" />,'+'<s:text name="field.label.ruleValue" />,'+'<s:text name="field.title.notNull" />');
                        return false;
                    }

                    if(ruleColumnType=="String" && (ruleOperator==">" || ruleOperator==">=" || ruleOperator=="<" || ruleOperator=="<=")){
                        //alert("String类型不能选择大于、大于等于、小于、小于等于判断条件");
                        alert('<s:text name="field.title.string.rang" />');
                        return false; 
                    }
                    if(ruleColumnType!="String" && (ruleOperator=="contain" || ruleOperator=="notContain" || ruleOperator=="regexp")){
                       alert('<s:text name="field.title.IntOrLong.use.rang" />');
                       return false; 
                    }

                    // var reg=/^[0-9]*$/;
                    var intReg=/^[-]?\d+$/;
                    // var longReg=/^[0-9]*\.?[0-9]*$/;
                    var longReg = /^(-?\d+)(\.\d+)?$/;
                    if(ruleColumnType=="Int" && (!intReg.test(ruleValue) || Number(ruleValue)>2147483647 || -2147483648>Number(ruleValue))){
                         alert('<s:text name="field.title.Int.rang" />');
                         return false; 
                    }
                    if(ruleColumnType=="Long" && !longReg.test(ruleValue)){
                    	alert('<s:text name="field.title.Long.rang" />');
                        return false;
                    }else if(ruleColumnType=="Long" && longReg.test(ruleValue)){
                    	var valueTemp = ruleValue;
                    	var valueLength = valueTemp.length;
                    	if(valueTemp.indexOf("-")<0 && valueLength>16){
                    		var m = valueTemp.substring(16, valueLength).replace(/./g, "0");
                    		valueTemp = valueTemp.substring(0,16) + m;
                    	}
                    	if(valueTemp.indexOf("-")>-1 && valueLength>17){
                            var m = valueTemp.substring(17, valueLength).replace(/./g, "0");
                            valueTemp = valueTemp.substring(0,17) + m;
                        }
                        //alert(valueTemp + "====" + Number(valueTemp));
                        //alert(Number(valueTemp)>9223372036854775000);
                       // alert(-9223372036854775000>Number(valueTemp));
                    	if(Number(valueTemp)>=9223372036854775000 || -9223372036854775000>=Number(valueTemp)){
                    		 alert('<s:text name="field.title.Long.rang" />');
                             return false;
                    	}
                    }
                    
                    var nodeName = ruleColumnText + " " + ruleOperatorText + " " + ruleValue;
                    zTree.addNodes(treeNode, {id:(100 + newCount++), pId:treeNode.id, name:nodeName, 'ruleColumn':ruleColumn, 'ruleOperator':ruleOperator, 'ruleValue':ruleValue,'ruleColumnType':ruleColumnType});
                    $("#addCondition").dialog( "close" );
                },
                '<s:text name="common.button.cancel" />': function() {
                    $("#addCondition").dialog( "close" );
                }
            },
            close: function() {
                
            }
        });
        
        $("#addCondition").show();
        $("#addCondition").dialog("open"); 
    }

    //添加策略
    function addRule(zTree,treeNode){ 
    	relationRadioHtml('&&');
    	
        $( "#addRule" ).dialog({
            title : '<s:text name="field.title.addRule" />',
            autoOpen : false,
            resizable: false,
            height: 250,
            width: 700,
            modal: true,
            show : "blind",
            buttons: {
                '<s:text name="common.button.add" />': function() {
                    var relation=$('input:radio[name="doamin.relation"]:checked').val();
                    var nodeName = $("#nameValue").val();
                    if(relation==null || $.trim(relation) =="" || nodeName==null || $.trim(nodeName) ==""){
                        //alert("子规则条件间关系、规则名称不能为空，请正确填写");
                        alert('<s:text name="field.title.subRule.condition" />,'+'<s:text name="field.label.nodeName" />,'+'<s:text name="field.title.notNull" />');
                        return false;
                    }else{
                    	var relationText;
                        if(relation=="&&"){
                        	relationText = "(" + '<s:text name="field.label.ruleRelation.and"/>' + ")";
                        }else{
                        	relationText = "(" + '<s:text name="field.label.ruleRelation.or"/>' + ")";
                        }
                        zTree.addNodes(treeNode, {id:(100 + newCount++), pId:treeNode.id, name:(nodeName + relationText), 'relation':relation});
                        $("#addRule").dialog( "close" );
                    }
                },
                '<s:text name="common.button.cancel" />': function() {
                    $("#addRule").dialog( "close" );
                }
            },
            close: function() {
            }
        });
        $("#nameValue").val('<s:text name="field.title.subRule" />');
        $("#addRule").show();
        $("#addRule").dialog("open"); 
        //$(".ui-widget-overlay").height("591px");
    }

  //修改条件
    function editCondition(zTree,treeNode){ 
        
    	selectOptionHtml();
    	 
        $( "#addCondition" ).dialog({
            title : '<s:text name="field.title.editCondition" />',
            autoOpen : false,
            resizable: false,
            height: 400,
            width: 750,
            modal: true,
            show : "blind",
            buttons: {
            	'<s:text name="common.button.add" />': function() {
                    var ruleColumn = $("#ruleColumn").val();
                    var ruleOperator = $("#ruleOperator").val();
                    var ruleValue = $("#ruleValue").val();
                    var ruleColumnType = $("#ruleColumnType").val();
                    var ruleColumnText = $('#ruleColumn option:selected').text(); 
                    var ruleOperatorText = $('#ruleOperator option:selected').text();
                    
                    if($.trim(ruleColumn) =="" || $.trim(ruleOperator)=="" || $.trim(ruleValue)=="" ||$.trim(ruleColumnType)==""){
                        alert('<s:text name="field.label.ruleColumn" />'+','+'<s:text name="field.label.ruleColumnType" />,'+'<s:text name="field.label.ruleOperator" />,'+'<s:text name="field.label.ruleValue" />,'+'<s:text name="field.title.notNull" />');
                        return false;
                    }

                    if(ruleColumnType=="String" && (ruleOperator==">" || ruleOperator==">=" || ruleOperator=="<" || ruleOperator=="<=")){
                        alert('<s:text name="field.title.string.rang" />');
                        return false; 
                    }
                    if(ruleColumnType!="String" && (ruleOperator=="contain" || ruleOperator=="notContain" || ruleOperator=="regexp")){
                        alert('<s:text name="field.title.IntOrLong.use.rang" />');
                       return false; 
                    }

                    var intReg=/^[-]?\d+$/;
                    var longReg = /^(-?\d+)(\.\d+)?$/;
                    if(ruleColumnType=="Int" && (!intReg.test(ruleValue) || Number(ruleValue)>2147483647 || -2147483648>Number(ruleValue))){
                        alert('<s:text name="field.title.Int.rang" />');
                        return false; 
                   }
                   if(ruleColumnType=="Long" && !longReg.test(ruleValue)){
                  	   alert('<s:text name="field.title.Long.rang" />');
                       return false;
                   }else if(ruleColumnType=="Long" && longReg.test(ruleValue)){
                       var valueTemp = ruleValue;
                       var valueLength = valueTemp.length;
                       if(valueTemp.indexOf("-")<0 && valueLength>16){
                           var m = valueTemp.substring(16, valueLength).replace(/./g, "0");
                           valueTemp = valueTemp.substring(0,16) + m;
                       }
                       if(valueTemp.indexOf("-")>-1 && valueLength>17){
                           var m = valueTemp.substring(17, valueLength).replace(/./g, "0");
                           valueTemp = valueTemp.substring(0,17) + m;
                       }
                       //alert(valueTemp + "====" + Number(valueTemp));
                       //alert(Number(valueTemp)>9223372036854775000);
                      // alert(-9223372036854775000>Number(valueTemp));
                       if(Number(valueTemp)>=9223372036854775000 || -9223372036854775000>=Number(valueTemp)){
                      	  alert('<s:text name="field.title.Long.rang" />');
                            return false;
                       }
                   }
                    
                    var nodeName = ruleColumnText + " " + ruleOperatorText + " " + ruleValue;
                    treeNode.name = nodeName;
                    treeNode.ruleColumn = ruleColumn;
                    treeNode.ruleOperator = ruleOperator;
                    treeNode.ruleValue = ruleValue;
                    treeNode.ruleColumnType = ruleColumnType;
                    zTree.updateNode(treeNode);
                    
                    $("#addCondition").dialog( "close" );
                },
                '<s:text name="common.button.cancel" />': function() {
                    $("#addCondition").dialog( "close" );
                }
            },
            close: function() {
            }
        });


        $("#ruleColumn").val(treeNode.ruleColumn);
        $("#ruleOperator").val(treeNode.ruleOperator);
        $("#ruleValue").val(treeNode.ruleValue);
        $("#ruleColumnType").val(treeNode.ruleColumnType);
       
        $("#addCondition").show();
        $("#addCondition").dialog("open"); 
    }

  //修改子策略
    function editRule(zTree,treeNode){ 
        $( "#addRule" ).dialog({
            title : '<s:text name="field.title.editRule" />',
            autoOpen : false,
            resizable: false,
            height: 250,
            width: 700,
            modal: true,
            show : "blind",
            buttons: {
            	'<s:text name="common.button.add" />': function() {
                    var relation=$('input:radio[name="doamin.relation"]:checked').val();
                    var nodeName = $("#nameValue").val();
                    if(relation==null || $.trim(relation) =="" || nodeName==null || $.trim(nodeName) ==""){
                        alert('<s:text name="field.title.subRule.condition" />,'+'<s:text name="field.label.nodeName" />,'+'<s:text name="field.title.notNull" />');
                        return false;
                    }else{
                    	var relationText;
                        if(relation=="&&"){
                            relationText = "(" + '<s:text name="field.label.ruleRelation.and"/>' + ")";
                        }else{
                            relationText = "(" + '<s:text name="field.label.ruleRelation.or"/>' + ")";
                        }
                    	treeNode.name = nodeName + relationText;
                    	treeNode.relation = relation;
                    	zTree.updateNode(treeNode);
                    	$("#addRule").dialog( "close" );
                    }
                },
                '<s:text name="common.button.cancel" />': function() {
                    $("#addRule").dialog( "close" );
                }
            },
            close: function() { 
            }
        });

        
        var relation = treeNode.relation ;
        relationRadioHtml(relation);
      
        var index = (treeNode.name).indexOf("(");
        var oldName = (treeNode.name).substr(0,index);
        $("#nameValue").val(oldName);
        $("#addRule").show();
        $("#addRule").dialog("open"); 
    }


window.onload = function(){
    $.formValidator.initConfig({formid:"mainForm",wideword:false});

    $("#ruleName").formValidator(
        {onshow:'<s:text name="validator.ruleName.onshow" />',
         onfocus:'<s:text name="validator.ruleName.onfocus" />',
         oncorrect:'<s:text name="common.validator.oncorrect" />'})
         .inputValidator({min:1,max:64,
                 empty:{
                 leftempty:false,
                 rightempty:false,
                 emptyerror:'<s:text name="common.validator.emptyerror" />'},
                 onerrormin:'<s:text name="validator.ruleName.onerrormin" />',
                 onerrormax:'<s:text name="validator.ruleName.onerrormax" />'})
         .functionValidator({
                 fun:function(val,elem){
                 if(val == null || val == ""){
                    return '<s:text name="common.validator.emptyerror" />';
                 }else{
                    var exist = false;
                	$.ajax({  
                         async: false, 
                         url: "alarmRegulationValidatorName.action", 
                         type: "post",   
                         dataType:"text",
                         data : {'domain.ruleName': val},
                         success:function(data) {//这里的data是由请求页面返回的数据   
                             var json = eval("("+data+")");//转换为json对象
                             if(json.result=="exist") {//系统异常
                            	 exist = true;
                             }
                         },   
                         error: function(XMLHttpRequest, textStatus, errorThrown) {
                        	alert('error');
                            return true; 
                         }   
                     }); 
                    if(exist){
                    	return '<s:text name="message.add.dataExsit" />';
                    }else{
                        return true;
                    }
                 }
              }
            });


    $("#ruleDesc").formValidator(
            {onshow:'<s:text name="validator.ruleDesc.onshow" />',
             onfocus:'<s:text name="validator.ruleDesc.onfocus" />',
             oncorrect:'<s:text name="common.validator.oncorrect" />'})
             .inputValidator({min:0,max:512,
                     empty:{
                     leftempty:false,
                     rightempty:false,
                     emptyerror:'<s:text name="common.validator.emptyerror" />'},
                     onerrormin:'<s:text name="validator.ruleDesc.onerrormin" />',
                     onerrormax:'<s:text name="validator.ruleDesc.onerrormax" />'});

    
    //提示消息
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
		})
	})

	  $.fn.zTree.init($("#treeDemo"), setting, zNodes);
	  
};

function validate(){
	 var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	 var nodes = zTree.transformToArray(zTree.getNodes());  

	 if(nodes.length<=1){
		 alert('<s:text name="field.label.rultInfo" />'+'<s:text name="field.title.notNull" />');
		 return false;
	 }
	 
	 var arr = new Array();
	 var nodeStr = ""; //同一子策略下条件不能重复
	 if (nodes.length>0) {
		  for(var i=0;i<nodes.length;i++){
			  
			  var name = nodes[i].name;
			  var level = nodes[i].level;
			  var tId = nodes[i].tId;
			  var parentTId = nodes[i].parentTId;
			  var ruleColumn = nodes[i].ruleColumn;
			  var ruleOperator = nodes[i].ruleOperator;
			  var ruleValue = nodes[i].ruleValue;
			  var relation = nodes[i].relation;
			  var ruleColumnType = nodes[i].ruleColumnType;

			  if(level=="1" && !nodes[i].isParent){
				  alert(name + ', <s:text name="field.label.rultInfo" />'+'<s:text name="field.title.notNull" />,'+'<s:text name="filed.title.addOrDelete" />'+name);
			      return false;
			  }
			  
			  var nodeStrTemp = name+parentTId+ruleColumn+ruleOperator+ruleValue+ruleColumnType;
			  if(level=="2" && nodeStr.indexOf(nodeStrTemp) > -1){
				//  alert('nodeStr:' + nodeStr);
	            //  alert('nodeStrTemp:' + nodeStrTemp);
				  var pNode = nodes[i].getParentNode();
				  var pName = pNode.name;
				  alert('\'' + pName + '\',<s:text name="filed.title.ruleInfo.rename" />,<s:text name="field.title.rule.condition.sure" />\''+name +'\'<s:text name="field.title.onlyOne" />');
                  return false;
			  }else if(level=="2"){
				  nodeStr = nodeStr + nodeStrTemp + "======";
			  }
			  		  
			  var treeNode=new Object(); 
			  treeNode.name = name;
			  treeNode.level = level;
			  treeNode.pId = parentTId;
			  treeNode.ruleColumn = ruleColumn;
			  treeNode.ruleOperator = ruleOperator;
			  treeNode.ruleValue = ruleValue;
			  treeNode.relation = relation;
              treeNode.ruleColumnType = ruleColumnType;
              treeNode.id = tId;
              treeNode.open = true;
			  arr.push(treeNode);
		  }
	  }

	  var nodesJson = JSON.stringify(arr); 
	  $("#ruleInfo").val(nodesJson);

	  var formCheck = $.formValidator.pageIsValid("1");
	  if(formCheck){
		$("#mainForm").attr("action","alarmRegulationAdd.action");
		$("#mainForm").submit();
	  }else{
		return false;
	  }
} 

function validateTextarea(){
    if($("#ruleDesc").val().length > 512)
      {
        $("#ruleDesc").val($("#ruleDesc").val().substring(0,512));
      }
}


//-->

</script>
<%-- 当前位置 --%>
<div class="rightToolbar">
  <div class="rightToolbarCrumbs">
  <h2 class="sec-label"><s:text name="function.title"/></h2>
  <ul class="bread-cut">
	  <li><s:text name="menu.title"/></li>
	  <li><s:text name="common.lable.arrow"/> </li>
	  <li><s:text name="alarmConfig.title"/></li>
	  <li><s:text name="common.lable.arrow"/> </li>
	  <li><s:text name="function.title"/> </li>
	  <li><s:text name="common.lable.arrow"/></li>
	  <li><s:text name="common.title.add"></s:text>
  </ul>
  </div>
</div>
	 <!--内容部分 main star-->
<div class="rightDisplayFwSj">
	<div class="formGroup">
		  <div class="formLabel"><s:text name="common.lable.baseInfo"/><s:text name="common.lable.line"/> </div>
	       <form id="mainForm" method="post" action="alarmRegulationAdd.action" > 
	        <input type='hidden' id ='ruleInfo' name='domain.ruleInfo'>
	        <table class="addTable" align="center" cellpadding="0" cellspacing="0" border="0">
	          <tr>
	               <td class="tdLabel" align="right" valign="top"><s:text name="common.red.point" /><s:text name="field.label.ruleName"/><s:text name="common.lable.point" /></td>
	               <td class="tdInput" valign="top">
	                <s:textfield name="domain.ruleName" id="ruleName" maxlength="16" cssClass="input" ></s:textfield>
	               </td>
	               <td class="tdVad" valign="top"><div id="ruleNameTip"></div></td>
	          </tr>
               <tr >
                  <td class="tdLabel" align="right" valign="top"><s:text name="common.red.point" /><s:text name="field.label.ruleContent"/><s:text name="common.lable.point" /></td>
                  <td  valign="top" colspan="2" >  
	                  <div style="overflow-x:auto;">
				        <ul id="treeDemo" class="ztree"></ul>
				      </div>
				  </td>
	           </tr>
	           <tr>
                  <td class="tdLabel" align="right" valign="top"><s:text name="field.label.ruleDesc"/><s:text name="common.lable.point" /></td>
                  <td  class="tdInput" valign="top">        
                      <textarea name="domain.ruleDesc" id="ruleDesc" class="newTextarea" onkeydown="validateTextarea();" onkeyup="validateTextarea();"></textarea>
                  </td>
                  <td class="tdVad" valign="top"><div id="ruleDescTip"></div></td>
               </tr> 
	        </table>    
	      </form>
	 </div>
	 <div class="messages succcess" style="top:25px">
      <div id="msgTip" class="msgSuccess"></div>
    </div>
    <div class="serverEventButton">
	    <ul class="rightToolButtonjhjFwSj">
	        <li><a href="#" class="buttonFwSj" onclick="validate();return false;"><s:property value="getText('common.button.add')"/></a></li>
	        <li><a href="alarmRegulationBase.action"  class="buttonGrey" ><s:property value="getText('common.button.cancel')" /></a></li>
	    </ul>
	</div>  
</div>
	
	<!-- 添加条件 -->
 <div id="addCondition" style="display:none;">
 	<div class="formGroup">
     <table class="addTable" align="center" cellpadding="0" cellspacing="0" border="0">
        <tr>
           <td class="tdLabel" align="left" valign="top" colspan="4" >
                <s:text name="field.label.addType"/><s:text name="common.lable.point" />
                <s:text name="field.label.addType.condition"/>
           </td>
        </tr> 
        <tr>
           <td class="tdInput" align="left" valign="top" style="width:150px;">
                <s:text name="common.red.point" /><s:text name="field.label.ruleColumn"/><s:text name="common.lable.point" />
           </td>
           <td  class="tdInput" align="left" valign="top" style="width:150px;"> 
                <s:text name="common.red.point" /><s:text name="field.label.ruleColumnType"/><s:text name="common.lable.point" />  
           </td>
           <td  class="tdInput" align="left" valign="top" style="width:150px;"> 
                <s:text name="common.red.point" /><s:text name="field.label.ruleOperator"/><s:text name="common.lable.point" />
           </td>
           <td class="tdInput" align="left" valign="top" style="width:200px;">
                <s:text name="common.red.point" /><s:text name="field.label.ruleValue"/><s:text name="common.lable.point" />
           </td> 
        </tr> 
        <tr>
           <td class="tdInput" align="left" valign="top">
               <select id="ruleColumn" name="domain.ruleColumn" class="newSelect" size="5" style="width:140px;height:154px;margin-left:5px;">
               </select>
           </td>
           <td  class="tdInput" align="left" valign="top" >        
               <select id="ruleColumnType" name="domain.ruleColumnType" class="newSelect" size="5" style="width:100px;height:154px;margin-left:5px;">
               </select>
           </td>
            <td  class="tdInput" align="left" valign="top" >        
               <select id="ruleOperator" name="domain.ruleOperator" class="newSelect" size="5" style="width:100px;height:154px;margin-left:5px;">
               </select>
           </td>
           <td class="tdInput" align="left" valign="top" style="width:200px;">
               <input name="domain.ruleValue" id="ruleValue" maxlength="32"  style="width: 200px;margin-left:5px;" />
          </td>  
        </tr> 
     </table>
    </div>
 </div>
 
<!-- 添加策略节点 -->
 <div id="addRule" style="display:none;"> 
 	<div class="formGroup">
     <table class="addTable" align="center" cellpadding="0" cellspacing="0" border="0">
        <tr>
           <td class="tdLabel" align="right" valign="top">
				<s:text name="field.label.addType"/><s:text name="common.lable.point" /></td>
           <td  class="tdInput" valign="top">        
                <s:text name="field.label.addType.node"/>
           </td>
           <td class="tdVad" valign="top"><div id="relationTip"></div></td>
        </tr> 
         <tr>
           <td class="tdLabel" align="right" valign="top">
               <s:text name="common.red.point" /><s:text name="field.label.nodeName"/><s:text name="common.lable.point" /></td>
           <td  class="tdInput" valign="top">        
                <s:textfield name="domain.nameValue" id="nameValue" maxlength="16" cssClass="input" ></s:textfield>
           </td>
           <td class="tdVad" valign="top"><div id="nameValueTip"></div></td>
        </tr> 
        <tr>
           <td class="tdLabel" align="right" valign="top"><s:text name="field.label.ruleRelation"/><s:text name="common.lable.point" /></td>
           <td  valign="top" id="relationTd" colspan="2">
              
           </td>
           <td class="tdVad" valign="top"><div id="relationTip"></div></td>
        </tr> 
     </table>
    </div>
 </div>
	