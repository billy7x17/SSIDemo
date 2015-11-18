/*
 * Snapshot for jQuery -  v1.4
 * Version 1.0.1
 * Copyright (c) 2012 Dreams Liu
 * Dual licensed under the MIT or GPL Version 2 licenses.
 *
 */
(function($) {
  $.fn.snapshot = function(p) {
    // apply default properties
    p = $.extend({
      // 文件下载地址
      downloadURL : 'snaphotDownloadFile.action',
      // 链接目标
      downloadTarget : 'blank',

      // 文件分割符
      fileSeparater : '\\',

      //默认的input元素的id前缀
      preId : 'snapshot',

      /**
       * 元素过滤方法
       * 此方法会在快照处理最后调用，用于对快照后的内容元素进行过滤
       * 默认移除id以Tip结尾的元素
       * 参数 $element 标识当前元素
       * 返回值  boolean类型
       * true 标识移除元素保留
       * false 标识保留元素
       */
      removeFilter : function($element) {
        var rv = false;
        var id = $element.attr("id");
        if(id){
          var reg = new RegExp(".*Tip", "");
          rv = reg.test(id);
        }
        return rv;
      },
      /**
       * 元素id属性过滤方法
       * 此方法会在快照处理最后调用，用于对快照后的内容元素的id属性进行过滤
       * 以防止快照内容展示的页面中对校验js框架的干扰
       * 默认对input和textarea元素进行id属性的过滤
       * 设置相关类型的html标签特定的属性值
       * 参数 $element 标识当前元素
       */
      changeFilter : function($element){
    	  var type = $element[0].tagName;
          /*
           * 如果是input或textarea类型，则设置为只读
           * 对于id的替换考虑到如果放到外边可能会干扰其他标签
           * 故只针对特定的标签进行替换
           */
        if(type == "INPUT" || type == "TEXTAREA"){
        	
        	//元素的id属性进行过滤
            var id = $element.attr("id") || "";
            $element.attr("id", p.preId + id);
            $element.attr("readonly", true);
            //如果是下拉框，则设置成为不可操作
        }else if(type == "SELECT"){
        	 //元素的id属性进行过滤
        	 var id = $element.attr("id") || "";
             $element.attr("id", p.preId + id);
             $element.attr("disabled", true);
            //如果是A标签，则替换onclick属性
        }else if(type == "A"){
        	 //元素的id属性进行过滤
        	 var id = $element.attr("id") || "";
        	 //if(!id.match("^file_snapshot_")){
        	 if(id.indexOf("file_snapshot_") == -1){
        		$element.attr("onclick", "return false;");
        	 }
        	 $element.attr("id", p.preId + id);
        }
        
      }
    }, p);// end extend

    var executive = {
      /**
       * 复制制定区域内的html内容 $src jquery对象 要复制的区域 返回值 jquery对象 要复制区域的html代码的jquery对象
       */
      district : function district($src) {
        var $target = $('<div style="display:hidden"></div>');
        $target.append($src.html());

        // 复制input
        this.copyInput($src, $target);

        // 复制textarea
        this.copyTextArea($src, $target);

        // 复制radio
        this.copyRadio($src, $target);

        // 复制checkbox
        this.copyCheck($src, $target);

        // 复制select
        this.copySelect($src, $target);

        // 复制文件
        this.copyFile($src, $target);

        //获得所有元素
        var $all = $("*", $target);
        $all.each(function(){
          var $element = $(this);
          var rv = p.removeFilter($element);
          if(rv){
          /**
           * /$element.remove(); 
           * //validator提示td中写入空格，否则ie7下快照居中显示
           **/
           $element.replaceWith("&nbsp;&nbsp;");
            //如果元素未移除，进行属性过滤
          }else{
              p.changeFilter($element);
          }
        });

        return $target;
      },

      /**
       * 复制input数据到目标html
       *
       */
      copyInput : function copyInput($src, $target) {
        var $target_element = $(':text', $target);
        var $src_element = $(':text', $src);
        $src_element.each(function(index) {
          $($target_element.get(index)).attr("value", $(this).val());
          $($target_element.get(index)).attr("readonly", "readonly");
          $($target_element.get(index)).css("border", "0");
        });// end each
      },// end fun

      /**
       * 复制textArea数据到目标html
       *
       */
      copyTextArea : function copyTextArea($src, $target) {
        var $target_element = $('textArea', $target);
        var $src_element = $('textArea', $src);
        $src_element.each(function(index) {
          $($target_element.get(index)).text($(this).attr("value"));
          $($target_element.get(index)).css("border", "0");
        });// end each
      },// end fun

      /**
       * 复制radio数据到目标html
       *
       */
      copyRadio : function copyRadio($src, $target) {
        var $target_element = $(':radio', $target);
        var $src_element = $(':radio', $src);
        $src_element.each(function(index) {
          $($target_element.get(index)).removeAttr('checked');
          $($target_element.get(index)).attr("value", $(this).val());
          if (this.checked) {
            $($target_element.get(index)).attr("checked", "checked");
          }
        });// end each
      },// end fun

      /**
       * 复制checkbos数据到目标html
       *
       */
      copyCheck : function copyCheck($src, $target) {
        var $target_element = $(':checkbox', $target);
        var $src_element = $(':checkbox', $src);
        $src_element.each(function(index) {
          $($target_element.get(index)).removeAttr('checked');
          $($target_element.get(index)).attr("value", $(this).val());
          if (this.checked) {
            $($target_element.get(index)).attr("checked", "checked");
          }
        });// end each
      },// end fun

      /**
       * 复制select数据到目标html
       *
       */
      copySelect : function copySelect($src, $target) {
        var $target_element = $('option', $target);
        var $src_element = $('option', $src);
        $src_element.each(function(index) {
        	  /*
             * 调用此方法的目的是去掉副本内容的选中值，原来的目的是防止副本与原内容不一致。
             * 此内容已经废弃不用。
             */
            //$($target_element.get(index)).removeAttr('selected');
            //$($target_element.get(index)).attr("value", $(this).val());
             if (this.selected) {
                 $($target_element.get(index)).attr("selected", "selected");
               }
        	
          //if ($(this).attr("selected")) {
        	//  $target_element.get(index).selected = true;
          //}
        });// end each
      },// end fun

      copyFile : function($src, $target) {
        var $target_element = $(':file', $target);
        var $src_element = $(':file', $src);
        $src_element
            .each(function(index) {
              var filename = $(this).val();
              var splitname = filename.split(p.fileSeparater);
              var sortname = splitname[splitname.length - 1];
              var $temp = $("<a href='#'>" + sortname + "</a>");
              $temp.attr('id', 'file_snapshot_' + $(this).attr('name'));
               var encodeName = encodeURIComponent(sortname,"UTF-8");
              $temp.attr("href", p.downloadURL + '?domain.downloadFileName=' + encodeName + '&actInstID=needReplace');
             // $temp.attr("target", p.downloadTarget);

              $($target_element.get(index)).replaceWith($temp);
            });// end each
      }// end fun
    }// end Object executive
    return executive.district(this);
  };// end fun snapshot
})(jQuery);
