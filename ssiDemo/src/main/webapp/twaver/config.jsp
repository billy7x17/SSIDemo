<%@page contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8"?>
<config>
    <setting>

    </setting>
    <construction>
       	<%=request.getContextPath()%>/twaver/MainMVC.jsp
    </construction>
    <increment>
        <%=request.getContextPath()%>/twaver/IncrementMVC.jsp
    </increment>
    <select>
        <%=request.getContextPath()%>/twaver/IncrementMVC.jsp
    </select>
    
    <auth>
        <operate name="detail.mail" action="reg" regular="[0-9]+" />
    </auth>
    <!--
    <plugin>
       <factory type="com.neusoft.mid.topology.nj.factory.NJFactory" />
    </plugin>
    -->
</config>
