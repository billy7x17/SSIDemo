/*******************************************************************************
 * @(#)enzyme_service.js 2011-7-6
 *
 * Copyright 2011 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/

/**
 * 此js库的主要目的是封装对于enzyme的service操作支持
 * @author <a href="mailto:liujingwei@neusoft.com">Dreams Liu </a>
 * @version $Revision 1.1 $ 2011-7-6 下午02:44:22
 */
Enzyme.Service = function (options) {
  var empty = {};
  var defaults = {
    url : '/bip/Service/ServiceServletJsonInvoker',
    serviceUri : 'testServiceURI',
    resultType : 'json',
    dataType : 'text',
    data : {},
    beforeSend : jQuery.noop,
    error : jQuery.noop,
    success : jQuery.noop,
    complete : jQuery.noop
  };
  var settings = jQuery.extend(empty, defaults, options);

  this.parseObjectToJson = function (src) {
    var rv = "";
    try {
      rv = JSON.stringify(src);
    } catch(e) {
      jQuery.error(e);
    }
    return rv;
  };
  this.setData = function (data) {
    settings.data = data;
  }
  this.getData = function() {
    return settings.data;
  }
  this.rander = function() {
    var postData = 'serviceUri=' + settings.serviceUri
    + '&' + 'resultType=' + settings.resultType
    + '&' + 'dataType=' + settings.dataType
    + '&' + 'data=' + this.parseObjectToJson(settings.data);

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
  }
}//end function [Enzyme.Service]