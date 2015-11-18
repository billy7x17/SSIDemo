<?xml version="1.0" encoding="UTF-8"?>
<!-- edited with XMLSpy v2009 (http://www.altova.com) by dreams (LG) -->
<%@page contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String name = request.getParameter("name");
    name = new String(name.getBytes("iso8859-1"), "UTF-8");
    System.out.println("subCanvas:" + name);
%>
<network name="<%=name%>" xmlns="http://www.neusoft.com/mid">
    <setting>
        <auto>
            <zoomOverview>false</zoomOverview>
            <increment>true</increment>
        </auto>
        <style>
            <bg_fill_color>0xFFFFFF</bg_fill_color>
            <bg_gradient_color>0xFFFFFF</bg_gradient_color>
            <selectColor>0XFFFFFF</selectColor>
        </style>
        <toolbar>
          <navigationName>
            <%=name%>
          </navigationName>
        </toolbar>
        <operatingSpace>
            <!-- <tree type="show"></tree> -->
        </operatingSpace>
    </setting>
    <!-- round,symmetry,topbottom,bottomtop,leftright,rightleft -->
    
    <layout type="AutoLayout" style="round">
    <!--
    <node seq="nodeMain" name="VM-C-0000-I-Main" image="Firewall1"> </node>
    
    <node seq="nodeMain1" name="VM-C-0000-I-Main" image="Router"> </node>
    -->
    
    <node seq="center" name="XXX组" image="Router"> </node>
    
    
<%
            int size = 10;
			for(int i = 0; i < size; i++){
%>
                <node seq="node<%=i%>" name="VM-C-0000-I-<%=i%>" 
                image="enable_server" width="4" height="4"> 
                </node>
<%
			}//end for i
            
            for(int i = 0; i < size; i++){

                //out.println("<link from=\"nodeMain1\" to=\"node" + i + "\"/>");
                //out.println("<link from=\"nodeMain\" to=\"node" + i+ "\"/>");

                
                out.println("<link from=\"center\" to=\"node" + i + "\"/>");
            }//end for i
%>

        <!--
        <group name="业务网络02" type="" expanded="true">
            <node>bas07</node>
            <node>bas08</node>
            <node>Cluster04</node>
            <node>CloudDiskServer</node>
        </group>
         出口层 
        <link from="Router1" to="Router2"/>
        -->
        
    </layout>
    <alerm seq="node11" level="CRITICAL">
    </alerm>
    <synch><%=System.currentTimeMillis()%></synch>
</network>
