/*******************************************************************************
 * @(#)enzyme_workflow.js 2011-7-6
 *
 * Copyright 2011 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/

/**
 * 此js库的主要目的是封装对workflow操作
 * @author <a href="mailto:liujingwei@neusoft.com">Dreams Liu </a>
 * @version $Revision 1.1 $ 2011-7-6 下午02:44:22
 */

Enzyme.Workflow = function(options){
	var defaults = {
		url : Enzyme.getServerURL() + 'workflow/Service/flowInvoke.do',
		resultType : 'json',
		dataType : 'text',
		data : {},
		beforeSend : jQuery.noop,
		error : jQuery.noop,
		success : jQuery.noop,
		complete : jQuery.noop
	};
	var settings = {};
	settings = jQuery.extend(settings, defaults, options);

	this.createFlowInstance = function(operations){
		var defaults = {
			 header : {
				 operationType : "service_workflow_create_instance",
				 userName : ""
			 },
			 body : {
				 procDefName : "default",
			   procInstName : "default",
				 requestFrom : "enzyme-js"
			 }
		}

		jQuery.extend(true, defaults, operations);
		this._rander(defaults);
	}
	
	this.getFlowInstance = function(operations){
	  var defaults = {
			 header : {
				 operationType : "service_workflow_get_instance",
				 userName : ""
			 },
			 body : {
				 procDefName : "default",
			   
			 }
		}//end object defaults 
		
		jQuery.extend(true, defaults, operations);
		this._rander(defaults);
	}

	this._rander = function(param){
		var postData = {
		    jsonData : JSON.stringify(param)
		}
	  var para = {
      type: "POST",
      dataType : settings.dataType,
      url : settings.url,
      data : postData,
      beforeSend : settings.beforeSend,
      error : settings.error,
      success : function(text) {
        if(text == "") {
          return ;
        }
        var json = JSON.parse(text);
        settings.success(json);
      }
		}//end para

		jQuery.ajax(para);
	}//end _rander
}