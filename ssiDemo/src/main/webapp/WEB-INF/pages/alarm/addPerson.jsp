<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<style type="text/css">
.tdLabel1{width:220px;}
.tdInput1{width:290px;}
.searchUserTable{
    font-family:'Microsoft Yahei','微软雅黑',Arial,Verdana;
    font-size:12px;
    margin-top: 6px;
}
.searchUserTable td{height:30px;}
.searchUserTable li{ color:#FFF; float:right; height:22px; line-height:22px;margin-left:10px; text-align:center; width:82px; }
.searchUserTable li a{display:block;color:#ffffff;}
}
</style>
<script type="text/javascript" >
var userInfoArray="";
function choosePerson(fileid) {
	//添加用户信息.
	if($("#personTable").html()==""){
		 addUserInfo();
	}
	 $("#personEmail").dialog({
			title : '<s:text name="field.title.addEmail"/>',
		    autoOpen : false,
		    resizable: false,
		    height: 520,
		    width: 820,
		    modal: true,
		    buttons: {
		        '<s:text name="common.button.add" />': function() {
		        		var oldValue = $("#notifyPersonMail").val();
		        		userInfoArray = "";
		        		$("input[name='userCheckValue']").each(function(){
		        			if($(this).attr("checked")){
		        				var email = $(this).parent().siblings().find("div").html();
		        				email += (email!=''?";":"");
		        				userInfoArray+=email;
		        			}
		        		});
		                if(userInfoArray ==""){
		                	alert('<s:text name="web.confirm.choosePerson"/>');
		                	return false;
		                }else{
		                	if(oldValue!=""){
		                		//判断新的邮件是否包含原邮件
		                		if(userInfoArray.indexOf(oldValue)=='-1'){
		                			userInfoArray = userInfoArray+oldValue;
		                		} else{
			                		userInfoArray = userInfoArray.substring(0, userInfoArray.length-1);
			                	}
		                	}else{
		                		userInfoArray = userInfoArray.substring(0, userInfoArray.length-1);
		                	}
		                	$("#notifyPersonMail").empty();
		                	$("#notifyPersonMail").val(userInfoArray);
		                	 validateTextarea(fileid, 1024);
		                	$("#personEmail").dialog("close");
		                }
		        },
		        '<s:text name="common.button.cancel" />': function() {
		        	$("#personEmail").dialog("close");
		        }
		    },
		    close: function() {
		    	//$("#personEmail").dialog("close");
		    }
		});
	$("#personEmail").show();
	$("#personEmail").dialog("open");  
}



function addUserInfo(){
	$.ajax({
		type:'POST',
		url:'selectPersonData.action',
		data:{'userName':$("#userName").val(),'siteId':$("#siteId").val()},
		dataType:'text',
		async:false,
		success:function(data){
			var dataList = data.split(";");
			var persons = eval("("+dataList[0]+")");
			var sites = eval("("+dataList[1]+")");
			//生产用户表
			createUserTable(persons);
			addSite(sites);
		}
	});
}
function createUserTable(persons){
	$("#personTable").empty();
	$('<thead><tr><td width="30px"><input type="checkbox" id="allUser" onclick="checkClick(this)" style="margin-left:10px;"/></td>'
		+'<td width="60px;"><s:text name="field.user.username"/></td>'
		+'<td width="80px;"><s:text name="field.user.sitename"/></td> '
		+'<td width="80px;"><s:text name="field.user.email"/></td> '
		+'<td width="80px;"><s:text name="field.user.phone"/> </td>'
		+'<td width="40px;"><s:text name="field.user.sex"/></td>'
		+'<td width="60px;"><s:text name="field.user.userNum"/></td> </tr> ').appendTo("#personTable");
	for(var i=0;i<persons.length;i++){
		$('<tr><td ><input name="userCheckValue"  type="checkbox" id='+persons[i].userId+' style="margin-left:10px;"/> </td>'
		   +'<td>'+persons[i].userName+'</td>'
		   +'<td>'+persons[i].siteName+'</td>'
		   +'<td><div>'+persons[i].email+'</div></td>'
		   +'<td>'+persons[i].mobile+'</td>'
		   +'<td>'+(persons[i].sex).replace('1','<s:text name="field.user.sex1"/>').replace('2','<s:text name="field.user.sex2"/>').replace('3','<s:text name="field.user.sex3"/>')+'</td>'
		   +'<td>'+persons[i].personnelId+'</td>'
		   +'</tr>').appendTo("#personTable");
	}
	$("#personTable tr:odd").css("background","f8f8f8");
}
//下拉表中添加站点信息.
function addSite(sites){
	$('<option value=""><s:text name="common.lable.select"/> </option>').appendTo("#siteId");
	for(var i=0;i<sites.length;i++){
		$('<option value='+sites[i].siteId+'>'+sites[i].siteName+'</option>').appendTo("#siteId");
	}
}
//展示查询内容
function showSeachDiv(){
	var playVal = $("#searchUserDiv").css("display");
	if(playVal=="none"){
		$("#searchUserDiv").css("display","block");
	}else if(playVal=="block"){
		$("#searchUserDiv").css("display","none");
	}
}

function checkClick(obj){
	 if(obj.checked) {
		$("input[name='userCheckValue']").each(function(){
			if(!$(this).attr("checked")) {
				$(this).attr("checked",true);
			}
		});
	}else{
		$("input[name='userCheckValue']").each(function(){
			$(this).attr("checked",false);
		});
	} 
}

function searchUser(){
	addUserInfo();
}

function resetUser(){
	$("#userName").val("");
	$("#siteId").val("");
}

//校验文本域
function validateTextarea(fieldId, length) {
	if ($("#" + fieldId).val().length > length) {
		$("#" + fieldId).val($("#" + fieldId).val().substring(0, length));
	}
}
</script>