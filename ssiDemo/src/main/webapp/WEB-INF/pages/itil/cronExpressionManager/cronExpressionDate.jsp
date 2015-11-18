<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="iamp-identify" uri="/WEB-INF/taglib/iamp-identify"%>

<script src="<%=request.getContextPath()%>/javascript/enzyme/enzyme-base-1.0.js"></script>
<script src="<%=request.getContextPath()%>/javascript/enzyme/enzyme-html-1.0.js"></script>
<script src="<%=request.getContextPath()%>/javascript/enzyme/enzyme-service-1.0.js"></script>
<script src="<%=request.getContextPath()%>/javascript/enzyme/enzyme-cornExpression-1.0.js"></script>

<script language="javascript">
<!--
	var cornExp = new Enzyme.CornExpression();

    jQuery(function(){
    	$("#tabs").tabs();

    	Enzyme.HTML.createCheckBoxGroup({target : jQuery('#minuteCheckGroup'), maxSize : 60, rowSize : 12, checkBoxName : 'minuteAssign'});
        Enzyme.HTML.createCheckBoxGroup({target : jQuery('#hourCheckGroup'), maxSize : 24, rowSize : 12, checkBoxName : 'hourAssign'});
        Enzyme.HTML.createCheckBoxGroup({target : jQuery('#dayCheckGroup'), maxSize : 31, rowSize : 10, startStep : 1, checkBoxName : 'dayAssign'});
        Enzyme.HTML.createCheckBoxGroup({target : jQuery('#monthCheckGroup'), maxSize : 12, rowSize : 12, startStep : 1, checkBoxName : 'monthAssign'});
        Enzyme.HTML.createCheckBoxGroup({target : jQuery('#weekCheckGroup'), maxSize : 7, rowSize : 7, startStep : 1, checkBoxName : 'weekAssign'});
        Enzyme.HTML.createCheckBoxGroup({target : jQuery('#yearCheckGroup'), maxSize : 30, rowSize : 10, startStep : 2010, checkBoxName : 'yearAssign'});

        Enzyme.HTML.createSelect({target : jQuery('#yearSelect'), startStep : 2010, maxSize : 30 });
        Enzyme.HTML.createSelect({target : jQuery('#minuteCycleStart'), maxSize : 60 });
        Enzyme.HTML.createSelect({target : jQuery('#minuteCycleEvery'), maxSize : 60 });
        Enzyme.HTML.createSelect({target : jQuery('#minuteScaleStart'), maxSize : 60 });
        Enzyme.HTML.createSelect({target : jQuery('#minuteScaleEnd'), maxSize : 60 });
        Enzyme.HTML.createSelect({target : jQuery('#hourCycleStart'), maxSize : 24 });
        Enzyme.HTML.createSelect({target : jQuery('#hourCycleEvery'), maxSize : 24 });
        Enzyme.HTML.createSelect({target : jQuery('#hourScaleStart'), maxSize : 24 });
        Enzyme.HTML.createSelect({target : jQuery('#hourScaleEnd'), maxSize : 24 });
        Enzyme.HTML.createSelect({target : jQuery('#dayCycleStart'), startStep:1,maxSize : 31 });
        Enzyme.HTML.createSelect({target : jQuery('#dayCycleEvery'), startStep:0,maxSize : 31 });
        Enzyme.HTML.createSelect({target : jQuery('#dayScaleStart'), startStep:1,maxSize : 31 });
        Enzyme.HTML.createSelect({target : jQuery('#dayScaleEnd'), startStep:1,maxSize : 31 });
        Enzyme.HTML.createSelect({target : jQuery('#monthCycleStart'), startStep:1,maxSize : 12 });
        Enzyme.HTML.createSelect({target : jQuery('#monthCycleEvery'), startStep:0,maxSize : 13 });
        Enzyme.HTML.createSelect({target : jQuery('#monthScaleStart'), startStep:1,maxSize : 12 });
        Enzyme.HTML.createSelect({target : jQuery('#monthScaleEnd'), startStep:1,maxSize : 12 });

        jQuery('#cronExpressionButton').bind('click', function(){
          createExpression();
        });

        jQuery('#selectCronExpressionButton').bind('click', function(){
            var corn = jQuery('#cronExpression').val();
            if(corn != ""){
                var local = new Enzyme.CornExpression({ cronExpression : corn});
                showExpression(local);
            }
        });

        jQuery('#chineseCronExpressionButton').bind('click', function(){
            try{
              jQuery.messager.alert('提示信息', new Enzyme.CornExpression({ cronExpression : jQuery('#cronExpression').val()}).getChineseCornExp());
            }catch(e){
              jQuery.messager.alert('错误', e.message);
            }//end try
        });

        jQuery("[name='minuteFlag']").bind('click', {checkBoxname : 'minuteCycle,minuteScale,minuteAssign'}, toggleCheckBoxGroup);
        jQuery("[name='hourFlag']").bind('click', {checkBoxname : 'hourCycle,hourScale,hourAssign'}, toggleCheckBoxGroup);
        jQuery("[name='dayFlag']").bind('click', {checkBoxname : 'dayCycle,dayScale,dayAssign'}, toggleCheckBoxGroup);
        jQuery("[name='monthFlag']").bind('click', {checkBoxname : 'monthCycle,monthScale,monthAssign'}, toggleCheckBoxGroup);
        jQuery("[name='weekFlag']").bind('click', {checkBoxname : ',,weekAssign'}, toggleCheckBoxGroup);
        jQuery("[name='yearFlag']").bind('click', {checkBoxname : ',,yearAssign'}, toggleCheckBoxGroup);

        showExpression(cornExp);
    });

    function showExpression(localCornExp){
        var temp = "";
        temp = localCornExp.getMinutesExp();
        if(temp == '*'){
            jQuery("[name='minuteFlag']:first").click();
        }else if(temp.indexOf("/")>0){
            jQuery("[name='minuteFlag'][value='cycle']").click();
            selectSelectGroup(temp, 'minuteCycle');
        }else if(temp.indexOf("-")>0){
            jQuery("[name='minuteFlag'][value='scale']").click();
            selectSelectGroup(temp, 'minuteScale');
        }else{
            jQuery("[name='minuteFlag']:last").click();
            selectCheckBoxGroup(temp, 'minuteAssign');
        }

        temp = localCornExp.getHoursExp();
        if(temp == '*'){
            jQuery("[name='hourFlag']:first").click();
        }else if(temp.indexOf("/")>0){
            jQuery("[name='hourFlag'][value='cycle']").click();
            selectSelectGroup(temp, 'hourCycle');
        }else if(temp.indexOf("-")>0){
            jQuery("[name='hourFlag'][value='scale']").click();
            selectSelectGroup(temp, 'hourScale');
        }else{
            jQuery("[name='hourFlag']:last").click();
            selectCheckBoxGroup(temp, 'hourAssign');
        }

        temp = localCornExp.getDaysOfMonthExp();
        if(temp == '*'){
            jQuery("[name='dayFlag']:first").click();
        }else if(temp.indexOf("/")>0){
            jQuery("[name='dayFlag'][value='cycle']").click();
            selectSelectGroup(temp, 'dayCycle');
        }else if(temp.indexOf("-")>0){
            jQuery("[name='dayFlag'][value='scale']").click();
            selectSelectGroup(temp, 'dayScale');
        }else{
            jQuery("[name='dayFlag']:last").click();
            selectCheckBoxGroup(temp, 'dayAssign');
        }

        temp = localCornExp.getMonthsExp();
        if(temp == '*'){
            jQuery("[name='monthFlag']:first").click();
        }else if(temp.indexOf("/")>0){
        	jQuery("[name='monthFlag'][value='cycle']").click();
        	selectSelectGroup(temp, 'monthCycle');
        }
        else if(temp.indexOf("-")>0){
            jQuery("[name='monthFlag'][value='scale']").click();
            selectSelectGroup(temp, 'monthScale');
        }else{
            jQuery("[name='monthFlag']:last").click();
            selectCheckBoxGroup(temp, 'monthAssign');
        }

        temp = localCornExp.getDaysOfWeekExp();
        if(temp == '?'){
            jQuery("[name='weekFlag']:first").click();
        }else{
            jQuery("[name='weekFlag']:last").click();
            selectCheckBoxGroup(temp, 'weekAssign');
        }

        temp = localCornExp.getYearExp();
        if(temp == '*' || temp == ''){
            jQuery("[name='yearFlag']:first").click();
        }else{
            jQuery("[name='yearFlag']:last").click();
            selectCheckBoxGroup(temp, 'yearAssign');
        }
    }

    function selectCheckBoxGroup(localCornExp, checkBoxName){
         if(localCornExp.indexOf(',') > 0){
             var ary = localCornExp.split(',');
             for(var i = 0; i < ary.length; i++){
             	jQuery('[name="' + checkBoxName +'"][value="' + ary[i] + '"]').attr('checked', 'checked');
             }//end for i
         }else{
              jQuery('[name="' + checkBoxName +'"][value="' + localCornExp + '"]').attr('checked', 'checked');
         }
    }

    function selectSelectGroup(localCornExp, SelectName){
         if(localCornExp.indexOf('/') > 0){
             var ary = localCornExp.split('/');
             jQuery('#'+SelectName+'Start').attr('value', ary[0]);
             jQuery('#'+SelectName+'Every').attr('value', ary[1]);
         }else if(localCornExp.indexOf('-') > 0){
             var ary = localCornExp.split('-');
             jQuery('#'+SelectName+'Start').attr('value', ary[0]);
             jQuery('#'+SelectName+'End').attr('value', ary[1]);
         }else{
              jQuery('[name="' + SelectName +'"][option value="' + localCornExp + '"]').attr('selected', 'selected');
         }
    }

    function toggleCheckBoxGroup(event){
    	// add by na.x 2013-10-17 begin
		// 在 jQuery ui的dialog中，click()方法无法激活单选按钮选中的样式，原因未知，暂时以强制转换样式的方法处理
    	var input_name = jQuery(this).attr("name");
    	jQuery("[name='" + input_name + "']").next("span").attr("class", "ui-radio");
    	jQuery(this).next("span").attr("class", "ui-radio ui-radio-state-checked ui-radio-checked");
    	// add by na.x 2013-10-17 end
        var val = jQuery(this).val();
        var checkBoxname = event.data.checkBoxname;
        checkBoxname = checkBoxname.split(',');
        if(val == 'cycle'){
            if(checkBoxname[0]){
                jQuery("[name='" + checkBoxname[0] +"']").removeAttr('disabled');
            }
            if(checkBoxname[1]){
                jQuery("[name='" + checkBoxname[1] +"']").attr('disabled', 'disabled');
            }
            if(checkBoxname[2]){
                jQuery("[name='" + checkBoxname[2] +"']").attr('disabled', 'disabled');
            }
        }else if(val == 'scale'){
            if(checkBoxname[0]){
                jQuery("[name='" + checkBoxname[0] +"']").attr('disabled', 'disabled');
            }
            if(checkBoxname[1]){
                jQuery("[name='" + checkBoxname[1] +"']").removeAttr('disabled');
            }
            if(checkBoxname[2]){
                jQuery("[name='" + checkBoxname[2] +"']").attr('disabled', 'disabled');
            }
        }else if(val == 'assign'){
            if(checkBoxname[0]){
                jQuery("[name='" + checkBoxname[0] +"']").attr('disabled', 'disabled');
            }
            if(checkBoxname[1]){
                jQuery("[name='" + checkBoxname[1] +"']").attr('disabled', 'disabled');
            }
            if(checkBoxname[2]){
                jQuery("[name='" + checkBoxname[2] +"']").removeAttr('disabled');
            }
        }else{
            jQuery("[name='" + checkBoxname[i] +"']").attr('disabled', 'disabled');
        }

    }

    function createExpression(){
        var minuteFlag = jQuery("[name='minuteFlag']:checked").val();
        var hourFlag = jQuery("[name='hourFlag']:checked").val();
        var dayFlag = jQuery("[name='dayFlag']:checked").val();
        var monthFlag = jQuery("[name='monthFlag']:checked").val();
        var weekFlag = jQuery("[name='weekFlag']:checked").val();
        if(dayFlag  && weekFlag=="Assign" ){
            jQuery.messager.alert('错误', "天、周不能同时为指定.");
            return ;
        }
        cornExp.setMinutesExp(createExp(minuteFlag, 'minute'));
        cornExp.setHoursExp(createExp(hourFlag, 'hour'));
        cornExp.setDaysOfMonthExp(createExp(dayFlag, 'day'));
        cornExp.setMonthsExp(createExp(monthFlag, 'month'));

        var week = createExp(weekFlag, 'week');
        if(weekFlag == 'cycle'){
            cornExp.setDaysOfWeekExp('?');
        }else if(weekFlag == 'assign'){
            cornExp.setDaysOfMonthExp('?');
            cornExp.setDaysOfWeekExp(week);
        }else{
            cornExp.setDaysOfWeekExp(week);
        }

        var yearFlag = jQuery("[name='yearFlag']:checked").val();
        if(yearFlag == 'assign'){
        	var year = createExp(yearFlag, 'year');
        	cornExp.setYearExp(year);
        } else {
        	cornExp.setYearExp("");
        }

        jQuery('#cronExpression').val(cornExp.getCornExpression());
    }

    function createExp(flag, checkBox){
        var buf = '';
        if(flag == 'cycle'){
            buf = '*';
            var temp = jQuery('#'+checkBox+'CycleStart').val();
            if(typeof(temp) != 'undefined'){
                buf =  temp;
            }else{
                buf = '*';
            }
             temp = jQuery('#'+checkBox+'CycleEvery').val();
            if(typeof(temp) != 'undefined'){
                buf  = buf+ '/'+ temp;
            }else{
                buf = '*';
            }
        }else if(flag == 'scale'){
            buf = '*';
            var tempS = jQuery('#'+checkBox+'ScaleStart').val();
            var tempE = jQuery('#'+checkBox+'ScaleEnd').val();
            if(typeof(tempS) != 'undefined'){
                buf =  tempS;
            }else{
                buf = '*';
            }
            if(typeof(tempE) != 'undefined'){
                buf  = buf+ '-'+ tempE;
            }else{
                buf = '*';
            }
        }else{
            jQuery('[name="' + checkBox +'Assign'+ '"]:checked').each(function(){
              buf += jQuery(this).val() + ',';
            });
            buf = buf.substr(0, buf.length - 1);
        }
        if(buf == ''){
          buf = '*';
        }
        if(buf=='0-0' || buf=='0/0' || buf=='1-1' || buf=='1/1' || buf=='1-0' || buf=='1/0'){
            buf = '*';
        }
        return buf;
    }

    jQuery(function(){
        var cronExpression = "<s:property value='domain.cronExpression' />";
        if(cronExpression != "null" && cronExpression != ""){
            $('#cronExpression').val(cronExpression);
            var corn = jQuery('#cronExpression').val();
            if(corn != ""){
                var local = new Enzyme.CornExpression({ cronExpression : corn});
                showExpression(local);
            }
        }
        $("#callBbackBtn").click(function(){
            createExpression();
            var cronExp = $("#cronExpression").val();
            //window.opener.setExecuteTime(cronExp);
            window.parent.setExecuteTime(cronExp);
            window.parent.dialog_close();
            });
        $("#cancel").click(function(){
            window.parent.dialog_close();
            });

      	//去掉鼠标移入移出效果
        $("tr").each(function(){
            var tr_bgColor = $(this).css("background-color");
            $(this).hover(function(){
                $(this).css("background-color","transparent");
            },function(){
                $(this).css("background-color","transparent");
            })
        })
        });
//-->
</script>

<div id="tabs" style="width:564px;height:252px;">
    <ul>
        <li style="width:52px"><a href="#minuteDiv">分钟</a></li>
        <li style="width:52px"><a href="#hourDiv">小时</a></li>
        <li style="width:52px"><a href="#dayDiv">天</a></li>
        <li style="width:52px"><a href="#monthDiv">月</a></li>
        <li style="width:52px"><a href="#weekDiv">星期</a></li>
        <li style="width:52px"><a href="#yearDiv">年</a></li>
    </ul>

  <div id="minuteDiv" title="分钟" style="padding:0;">
    <table border="1" width="100%">
      <tr>
        <td>
          <input type="radio" name="minuteFlag" value="cycle" id="minuteCycleRadio" >
          周&nbsp;&nbsp;&nbsp;期:开始时间
          <select id="minuteCycleStart" name="minuteCycle">
          </select>&nbsp;&nbsp;
          时间间隔:
          <select id="minuteCycleEvery" name="minuteCycle" >
          </select>
        </td>
      </tr>
      <tr>
        <td>
          <input type="radio" name="minuteFlag" value="scale" id="minuteScaleRadio">
          时间段:开始时间
          <select id="minuteScaleStart" name="minuteScale">
          </select>&nbsp;&nbsp;
          结束时间:
          <select id="minuteScaleEnd" name="minuteScale">
          </select>
        </td>
      </tr>
      <tr>
        <td>
          <input type="radio" name="minuteFlag" value="assign" id="minuteAssignRadio">
          指定</td>
      </tr>
      <tr>
        <td id="minuteCheckGroup"> </td>
      </tr>
    </table>
  </div>
  <div id="hourDiv" title="小时" style="padding:0;">
    <table border="1" width="100%">
      <tr>
        <td>
          <input type="radio" name="hourFlag" value="cycle" id="hourCycleRadio">
           周&nbsp;&nbsp;&nbsp;期:开始时间

          <select id="hourCycleStart" name="hourCycle">
          </select>&nbsp;&nbsp;
          时间间隔:
          <select id="hourCycleEvery" name="hourCycle">
          </select> </td>
      </tr>
       <tr>
        <td>
          <input type="radio" name="hourFlag" value="scale" id="hourScaleRadio">
          时间段:开始时间
          <select id="hourScaleStart" name="hourScale">
          </select>&nbsp;&nbsp;
          结束时间:
          <select id="hourScaleEnd" name="hourScale">
          </select>

        </td>
      </tr>
      <tr>
        <td>
          <input type="radio" name="hourFlag" value="assign" id="hourAssignRadio">
          指定</td>
      </tr>
      <tr>
        <td id="hourCheckGroup"> </td>
      </tr>
    </table>
  </div>
  <div id="dayDiv" title="天" style="padding:0;">
    <table border="1" width="100%">
      <tr>
        <td>
          <input type="radio" name="dayFlag" value="cycle" id="dayCycleRadio">
         周&nbsp;&nbsp;&nbsp;期:开始时间
          <select id="dayCycleStart" name="dayCycle">
          </select>&nbsp;&nbsp;
          时间间隔:
          <select id="dayCycleEvery" name="dayCycle">
          </select> </td>
      </tr>
       <tr>
        <td>
          <input type="radio" name="dayFlag" value="scale" id="dayScaleRadio">
          时间段:开始时间
          <select id="dayScaleStart" name="dayScale">
          </select>&nbsp;&nbsp;
          结束时间:
          <select id="dayScaleEnd" name="dayScale">
          </select>
        </td>
      </tr>
      <tr>
        <td>
          <input type="radio" name="dayFlag" value="assign" id="dayAssignRadio">
          指定</td>
      </tr>
      <tr>
        <td id="dayCheckGroup"> </td>
      </tr>
    </table>
  </div>
  <div id="monthDiv" title="月" style="padding:0;">
    <table border="1" width="100%">
      <tr>
        <td>
          <input type="radio" name="monthFlag" value="cycle" id="monthCycleRadio">
          周&nbsp;&nbsp;&nbsp;期:开始时间
          <select id="monthCycleStart" name="monthCycle">
          </select>&nbsp;&nbsp;
          时间间隔:
          <select id="monthCycleEvery" name="monthCycle">
          </select> </td>
      </tr>
       <tr>
        <td>
          <input type="radio" name="monthFlag" value="scale" id="monthScaleRadio">
          时间段:开始时间
          <select id="monthScaleStart" name="monthScale">
          </select>&nbsp;&nbsp;
          结束时间:
          <select id="monthScaleEnd" name="monthScale">
          </select>
        </td>
      </tr>
      <tr>
        <td>
          <input type="radio" name="monthFlag" value="assign" id="monthAssignRadio">
          指定</td>
      </tr>
      <tr>
        <td id="monthCheckGroup"> </td>
      </tr>
    </table>
  </div>
  <div id="weekDiv" title="星期" style="padding:0;">
    <table border="1" width="100%">
      <tr>
        <td>
          <input type="radio" name="weekFlag" value="cycle" id="weekCycleRadio">
          不指定 </td>
      </tr>
      <tr>
        <td>
          <input type="radio" name="weekFlag" value="assign" id="weekAssignRadio">
          指定</td>
      </tr>
      <tr>
        <td id="weekCheckGroup"> </td>
      </tr>
    </table>
  </div>
  <div id="yearDiv" title="年" style="padding:0;">
    <table border="1" width="100%">
      <tr>
        <td>
          <input type="radio" name="yearFlag" value="cycle" id="yearCycleRadio">
          年循环 </td>
      </tr>
      <tr>
        <td>
          <input type="radio" name="yearFlag" value="assign" id="yearAssignRadio">
          指定</td>
      </tr>
      <tr>
        <td id="yearCheckGroup"> </td>
      </tr>
    </table>
  </div>
</div>
<table border="0" style="width:564px;">
  <tr style="display:none">
    <td>
      <div id="cronExpressionDiv"> Cron
        Expression:
        <input type="text" id="cronExpression" name="cronExpression" class="easyui-validatebox" required="true" value="" />
        <input type="button" id="cronExpressionButton" name="create" value="生成表达式" />
        <input type="button" id="selectCronExpressionButton" name="selectCronExpressionButton" value="回显画面" />
        <input type="button" id="chineseCronExpressionButton" name="chineseCronExpressionButton" value="文字描述" />
      </div>
    </td>
  </tr>
  <tr>
    <td align="right">
        <input type="button" id="callBbackBtn" name="chineseCronExpressionButton" value="确定" />
        <input type="button" id="cancel" name="chineseCronExpressionButton" value="取消" />
    </td>
  </tr>
</table>
