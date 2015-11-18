<?xml version="1.0" encoding="UTF-8"?>
<!-- edited with XMLSpy v2009 (http://www.altova.com) by dreams (LG) -->
<%@page contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String name = request.getParameter("name");
    System.out.println("rootCanvas:" + name);
%>
<construction name="<%=name%>" xmlns="http://www.neusoft.com/mid">
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
    
    <layout type="GridAutoLayout" style="topbottom">
        <row name="网络出口层">
            <rank>
                <row>
                    <rank/>
                    <rank/>
                    <rank>
                        <node seq="Router1" name="3X10G" image="Router">
                        </node>
                    </rank>
                    <rank/>
                    <rank>
                        <node seq="Router2" name="3X10G" image="Router">
                        </node>
                    </rank>
                    <rank/>
                    <rank/>
                </row>
                <row>
                    <rank/>
                    <rank>
                        <node seq="Router3" name="CMNET出口路由1" image="Router">
                        </node>
                    </rank>
                    <rank/>
                    <rank>
                        <node seq="Router4" name="CMNET出口路由2" image="Router">
                        </node>
                    </rank>
                    <rank/>
                </row>
            </rank>
        </row>
        
        <row name="核心层">
            <rank>
                <row>
                    <rank/>
                    <rank/>
                    <rank name="0.0-1">
                        <node seq="Firewall1" name="核心业务防火墙1" image="Firewall1" type="detailCanvas"/>
                    </rank>
                    <rank/>
                    <rank>
                        <node seq="Firewall2" name="核心业务防火墙2" image="Firewall1"/>
                    </rank>
                    <rank/>
                    <rank/>
                </row>
                <row>
                    <rank/>
                    <rank>
                        <node seq="csw01" name="核心交换1" image="Core_Switch"  type="detailCanvas"/>
                    </rank>
                    <rank/>
                    <rank>
                        <node seq="csw02" name="核心交换2" image="Core_Switch"/>
                    </rank>
                    <rank/>
                </row>
            </rank>
        </row>
        
        <row name="总线层" customizationHeight="40">
            <rank>
                <node seq="bus" name="系统总线" image="" type="bus" oppositeWidth="0.8">
                </node>
            </rank>
        </row>
        <row name="业务层">
            <rank name="存储网络">
                <row>
                    <rank/>
                    <rank>
                        <node seq="bas01" name="存储汇聚交换机" image="Aggregation_Switch"/>
                    </rank>
                    <rank>
                        <node seq="bas02" name="存储汇聚交换机" image="Aggregation_Switch"/>
                    </rank>
                    <rank/>
                </row>
                <row>
                    <rank/>
                    <rank>
                        <node seq="Cluster01" name="接入交换机" image="Cluster" type="subCanvas">
                            <property name="height" value="20"/>
                        </node>
                    </rank>
                    <rank/>
                </row>
                <row>
                    <rank>
                        <node seq="disk" name="磁盘阵列" image="DiskArray"/>
                    </rank>
                </row>
            </rank>
            <rank name="业务网络01">
                <row>
                    <rank>
                        <node seq="bas03" name="业务汇聚交换机" image="Aggregation_Switch"/>
                    </rank>
                    <rank>
                    
                    </rank>
                    <rank>
                        <node seq="bas04" name="业务汇聚交换机" image="Aggregation_Switch"/>
                    </rank>
                    <rank>
                    
                    </rank>
                    <rank>
                        <node seq="bas05" name="业务汇聚交换机" image="Aggregation_Switch"/>
                    </rank>
                    <rank>
                    
                    </rank>
                    <rank>
                        <node seq="bas06" name="业务汇聚交换机" image="Aggregation_Switch"/>
                    </rank>
                </row>
                <row>
                    <rank>
                        <node seq="bsw03" name="业务接入交换机" image="Access_Switch"/>
                    </rank>
                    <rank>
                    
                    </rank>
                    <rank>
                        <node seq="bsw04" name="业务接入交换机" image="Access_Switch"/>
                    </rank>
                    <rank>
                    
                    </rank>
                    <rank>
                        <node seq="bsw05" name="业务接入交换机" image="Access_Switch"/>
                    </rank>
                    <rank>
                    
                    </rank>
                    <rank>
                        <node seq="bsw06" name="业务接入交换机" image="Access_Switch"/>
                    </rank>
                </row>
                <row>
                    <rank>
                        <node seq="ServerCluster01" name="虚拟集群" image="ServerCluster">
                            <property name="height" value="32"/>
                        </node>
                    </rank>
                    <rank>
                        <node seq="ServerCluster02" name="虚拟集群" image="ServerCluster">
                            <property name="height" value="32"/>
                        </node>
                    </rank>
                </row>
            </rank>
            <rank name="业务网络02">
                <row>
                    <rank/>
                    <rank>
                        <node seq="bas07" name="业务汇聚交换机01" image="Aggregation_Switch" type="subCanvas"/>
                    </rank>
                    <rank>
                        <node seq="bas08" name="业务汇聚交换机02" image="Aggregation_Switch"/>
                    </rank>
                    <rank/>
                </row>
                <row>
                    <rank/>
                    <rank>
                        <node seq="Cluster04" name="接入交换机" image="Cluster">
                            <property name="height" value="32"/>
                        </node>
                    </rank>
                    <rank/>
                </row>
                <row>
                    <rank>
                        <node seq="CloudDiskServer" name="云存储服务器" image="CloudDiskServer">
                            <property name="width" value="32"/>
                        </node>
                    </rank>
                </row>
            </rank>
        </row>
        <group name="网络出口层" type="" expanded="true">
            <node seq="Router1"></node>
            <node seq="Router2"></node>
            <node seq="Router3"></node>
            <node seq="Router4"></node>
        </group>
        
        <group name="核心层" type="" expanded="true">
            <node seq="Firewall1"></node>
            <node seq="Firewall2"></node>
            <node seq="csw01"></node>
            <node seq="csw02"></node>
        </group>
        <group name="存储网络" type="" expanded="true">
            <node seq="bas01"></node>
            <node seq="bas02"></node>
            <node seq="Cluster01"></node>
            <node seq="disk"></node>
        </group>
        

        <group name="业务网络01" type="" expanded="true">
            <node seq="bas03"></node>
            <node seq="bas04"></node>
            <node seq="bas05"></node>
            <node seq="bas06"></node>
            <node seq="bsw03"></node>
            <node seq="bsw04"></node>
            <node seq="bsw05"></node>
            <node seq="bsw06"></node>
            <node seq="ServerCluster01"></node>
            <node seq="ServerCluster02"></node>
        </group>
        <group name="业务网络02" type="" expanded="true">
            <node seq="bas07"></node>
            <node seq="bas08"></node>
            <node seq="Cluster04"></node>
            <node seq="CloudDiskServer"></node>
        </group>
        
        <group name="总括" type="" expanded="true">
          <group name="存储网络" ></group>
          <group name="业务网络01" ></group>
          <group name="业务网络02" ></group>
        </group>
        
        <!-- 出口层 -->
        <link from="Router1" to="Router2"/>
        <link from="Router1" to="Router3"/>
        <link from="Router4" to="Router2"/>
        <link from="Router4" to="Router3"/>
        <link from="Router3" to="Firewall1"/>
        <link from="Router3" to="Firewall2"/>
        <link from="Router4" to="Firewall1"/>
        <link from="Router4" to="Firewall2"/>
        <!-- 核心层 -->
        <link from="Firewall1" to="Firewall2"/>
        <link from="Firewall1" to="csw01"/>
        <link from="csw02" to="csw01"/>
        <link from="csw02" to="Firewall2"/>
        <!-- BUS层 -->
        <link from="csw01" to="bus"/>
        <link from="csw02" to="bus"/>
        <link from="bus" to="bas01"/>
        <link from="bus" to="bas02"/>
        <link from="bus" to="bas03"/>
        <link from="bus" to="bas04"/>
        <link from="bus" to="bas05"/>
        <link from="bus" to="bas06"/>
        <link from="bus" to="bas07"/>
        <link from="bus" to="bas08"/>
        <!-- 存储网络 -->
        <link from="Cluster01" to="bas01"/>
        <link from="Cluster01" to="bas02"/>
        <link from="Cluster01" to="disk"/>
        <!-- 业务网络01 -->
        <link from="bas03" to="bsw03"/>
        <link from="bas04" to="bsw04"/>
        <link from="bas05" to="bsw05"/>
        <link from="bas06" to="bsw06"/>
        <link from="bsw03" to="ServerCluster01"/>
        <link from="bsw04" to="ServerCluster01"/>
        <link from="bsw05" to="ServerCluster02"/>
        <link from="bsw06" to="ServerCluster02"/>

        <!-- 业务网络02 -->
        <link from="Cluster04" to="bas07"/>
        <link from="Cluster04" to="bas08"/>
        <link from="Cluster04" to="CloudDiskServer"/>
    </layout>
    
    
    
    <synch><%=System.currentTimeMillis()%></synch>
</construction>
