/*******************************************************************************
 * @(#)enzyme_across.js 2011-7-6
 *
 * Copyright 2011 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/

/**
 * 此js库的主要目的是封装跨模板功能
 * @author <a href="mailto:liujingwei@neusoft.com">Dreams Liu </a>
 * @version $Revision 1.1 $ 2011-7-6 下午02:44:22
 */
var across = function($) {
}(jQuery);
/**
 *
 */
function fireEvent(eventArg, options) {
  var exp = "";
  var settings = {
    dataCoyp : true,
    getDataFunName : "getValue",
    setDataFunName : "setValue",
    invokeFunName  : "eventAction"
  };
  jQuery.extend(settings, options);
  var bind = eventArg.bind;

  var sourceIframe = jQuery.data(document, eventArg.sourceInstanceId);
  var targetIframe = jQuery.data(document, eventArg.targetInstanceId);
  if(sourceIframe && targetIframe) {
    var swin = sourceIframe;
    var twin = targetIframe;

    var arg = new Object();
    for(var i = 0; i < bind.length; i++) {
      var value = "";
      try {
        value = eval("swin." + settings.getDataFun + "();");
      } catch(e) {
        value = Enzyme.HTML.getIframeElementValue(swin, bind[i].srcName);
      }

      if(settings.dataCoyp) {
        try {
          exp = "twin." + settings.setDataFunName + "(" + bind[i] + ", " + value +")";
          eval(exp);
        } catch(e) {
        	Enzyme.HTML.setIframeElementValue(twin, bind[i].targetName, value);
        }
      }
      bind[i].value = value;
    }// end for i

    // 调用事件目标
    try {
      exp = "twin." + settings.invokeFunName + "(" + bind + ");";
      eval(bind);
    } catch(e) {
    }
  };// end if sourceIframe && targetIframe
}// end fun



/**
 *
 */
function handlerEvent(templateInstanceId, options) {
  var empty = {};
  var defaults = {
    url : '/bip/Service/ServiceServletJsonInvoker',
    serviceUri : 'service-common-across-getEventService',
    dataType : 'text',
    data : {},
    beforeSend : jQuery.noop,
    error : jQuery.noop,
    complete : jQuery.noop,
    success : function(rb) {
        if(text == "") {
            return ;
          } else {
            var rb = eval("(" + text + ")");
            if(rb.resultCode == '0'){
              fireEvent(rb.message, options);
            }
          }
        }// end success
  };
  var settings = jQuery.extend(empty, defaults, options);
  var service = new Enzyme.Service(settings);
  service.rander();
}