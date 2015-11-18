<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="iamp-identify" uri="/WEB-INF/taglib/iamp-identify"%> 
<script>
var winHeight = $(window).height() - 17; //17px为workarea的margin-top
$(".baseTab").height(winHeight - ($(".BasicInformation").height()+11) - (18 + 12*2));
</script>
<decorator:body />
