/*******************************************************************************
 * @(#)enzyme_base.js 2011-7-6
 *
 * Copyright 2011 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/

/**
 * 此js库为其他enzyme类库进行基础支持
 * @author <a href="mailto:liujingwei@neusoft.com">Dreams Liu </a>
 * @version $Revision 1.1 $ 2011-7-6 下午02:44:22
 */
var Enzyme = new function($){
	/*
	 * 获得服务器地址
	 */
	this.getServerURL = function(){
	  return "http://localhost:8888/";
	}
	
	window.showMessage = function(message){
		  alert(message);
	}
	
	window.debug = function(message){
		if(console){
			console.log(message);
		}
	}
	
	
}(jQuery);
