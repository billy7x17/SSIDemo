<%@page contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String name = request.getParameter("name");
    name = new String(name.getBytes("iso8859-1"), "UTF-8");
    /* System.out.println("incrementMVC.jsp:[" + System.currentTimeMillis() + "]" + name); */
%>
<?xml version="1.0" encoding="UTF-8"?>
<increment xmlns="http://www.neusoft.com/mid/increment">
    <setting>
        <auto>
            <zoomOverview>false</zoomOverview>
            <increment>true</increment>
        </auto>
        <style>
            <bg_fill_color>0xFFFFFF</bg_fill_color>
            <bg_gradient_color>0xFFFFFF</bg_gradient_color>
            <selectColor>0X15A230</selectColor>
        </style>
        <toolbar>
          <navigationName>
            首页
          </navigationName>
        </toolbar>
        <operatingSpace>
            <!-- <tree type="show"></tree> -->
        </operatingSpace>
    </setting>
<alerm seq="31" level="WARNING" count="1">
    </alerm>
	<alerm seq="root" level="MINOR">
        <property name="objectID" value="objectID4" desc="对象标识"/>
        <property name="alarmGrade" value="alarmGrade4" desc="告警级别"/>
        <property name="alarmID" value="alarmID4" desc="告警ID"/>
        <property name="alarmTitle" value="alarmTitle4" desc="告警标题"/>
        <property name="alarmType" value="alarmType4" desc="告警分类"/>
        <property name="count" value="4" desc="重复次数"/>
        <property name="alarmTime" value="alarmTime4" desc="最后发生时间"/>
        <property name="firstAlarmTime" value="firstAlarmTime4" desc="首次发生时间"/>
        <property name="alarmContent" value="alarmContent4" desc="告警内容"/>
   </alerm>
   <alerm seq="19" level="CRITICAL">
        <property name="objectID" value="objectID4" desc="对象标识"/>
        <property name="alarmGrade" value="alarmGrade4" desc="告警级别"/>
        <property name="alarmID" value="alarmID4" desc="告警ID"/>
        <property name="alarmTitle" value="alarmTitle4" desc="告警标题"/>
        <property name="alarmType" value="alarmType4" desc="告警分类"/>
        <property name="count" value="4" desc="重复次数"/>
        <property name="alarmTime" value="alarmTime4" desc="最后发生时间"/>
        <property name="firstAlarmTime" value="firstAlarmTime4" desc="首次发生时间"/>
        <property name="alarmContent" value="alarmContent4" desc="告警内容"/>
    </alerm>
    
    <alerm seq="28" level="CRITICAL">
         <property name="objectID" value="objectID4" desc="对象标识"/>
        <property name="alarmGrade" value="alarmGrade4" desc="告警级别"/>
        <property name="alarmID" value="alarmID4" desc="告警ID"/>
        <property name="alarmTitle" value="alarmTitle4" desc="告警标题"/>
        <property name="alarmType" value="alarmType4" desc="告警分类"/>
        <property name="count" value="4" desc="重复次数"/>
        <property name="alarmTime" value="alarmTime4" desc="最后发生时间"/>
        <property name="firstAlarmTime" value="firstAlarmTime4" desc="首次发生时间"/>
        <property name="alarmContent" value="alarmContent4" desc="告警内容"/>
    </alerm> 
    
    
    
    <synch><%=System.currentTimeMillis()%></synch>
</increment>
