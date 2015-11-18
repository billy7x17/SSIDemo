/*
 * Midgrid for jQuery -  v1.0.0
 *
 * Copyright (c) 2012 Dreams Liu
 * Dual licensed under the MIT or GPL Version 2 licenses.
 */
(function($) {
  $.fn.Midgrid = function(p) {
    // 防止多次调用
    if (this.Midgrid.init) {
      return;
    }
    var grid = this;

    var key_line = "line";
    var key_index = "index";
    var key_old_index = "old_index";
    var key_original = "original";
    var key_col = "col";

    var options = $.extend({
      /**
       * table宽度自适应
       * false 标识使用table原始宽度
       */
      autoWidth : false,
      /**
       * 支持拖拽列
       * false  标识不支持拖拽列
       */
      dragCol : false,

      /**
       * 拖拽线位置x轴偏移量
       */
      offsetX : 0,

      /**
       * 拖拽线处位置y轴偏移量
       */
      offsetY : 0,

      /**
       * 拖拽指示条最大高度
       */
      maxHeight : 9999999,


      /**
       * 返回标题列对象
       * return jquery对象 标题列
       */
      colModel : function() {
        var rv = $('thead th', grid);
        if (rv.size() == 0) {
          rv = $('tr:first th', grid);
        }
        return rv;
      },
      /**
       * 返回全部数据列对象
       * return jquery对象 数据列
       */
      cellModel : function() {
        var rv = $("tbody td", grid);
        return rv;
      },
      /**
       * 展示拖拽列时出现的浮动div
       * 开放此接口用于自定义浮动效果
       */
      dragDiv : function() {
        var $dragDiv = $("<div style='display:none'></div>");
        $dragDiv.addClass("midgrid");
        $dragDiv.addClass("dragDiv");
        $('body').append($dragDiv);
        return $dragDiv;
      },

      /**
       * 回调方法，在拖拽列之前
       */
      beforeDragCol : function($col, $dragDiv) {
        $dragDiv.html($col.html());
        $col.html("");
      },
      /**
       * 回调方法，在拖拽列之后
       */
      afferDragCol : function($col, $dragDiv) {
        $col.html($dragDiv.html());
        $dragDiv.html("");
      },

      removeCell : function(srcCell, targetCell) {
        var temp = targetCell.html();
        targetCell.html(srcCell.html());
        srcCell.html(temp);

        temp = targetCell.attr(key_original);
        targetCell.attr(key_original, srcCell.attr(key_original));
        srcCell.attr(key_original, temp);

        temp = targetCell.attr(key_old_index);
        targetCell.attr(key_old_index, srcCell.attr(key_old_index));
        srcCell.attr(key_old_index, temp);
      },

      resize : function($grid){

      }

    }, p);// end extend
    /*
     * 列宽度调整标识显示top位置修正值
     */
    options.fixTop = 1;
    /*
     * 列宽度调整标识显示left位置修正值
     * 因为目前为了实现在拖拽行左右两侧均可以出现拖拽效果，而非单纯在在拖拽线上
     * 故采用【.midgrid.line】样式实现，为了保证在拖拽的过程中
     * 拖拽线始终在鼠标下面的效果，故需要增加此修正值
     */
    options.fixLeft = -10;

    /**
     * 展示层
     */
    var veiw = {

    }
    /**
     * 控制层
     */
    var controller = {

    }
    /**
     * 逻辑层
     */
    var model = {
      dragStart : function(e) {
        var data = e.data || {};
        if (data.source == "line") {
          grid.data(key_line, $(this));
          grid.css("cursor", "col-resize");
        } else if (options.dragCol && data.source == "col") {
          var $col = $(this);
          grid.data(key_col, $col);
          var $dragDiv = $(".midgrid.dragDiv");
          $dragDiv.css("left", e.clientX + 1);
          $dragDiv.css("top", e.clientY + 1);
          $dragDiv.width($col.width());
          $dragDiv.height($col.height());

          options.beforeDragCol($col, $dragDiv);
          $dragDiv.show();
        }
        // 不在向上浮现事件
        return false;
      },

      dragMove : function(e) {
        var $line = grid.data(key_line);
        if ($line) {
          $line.addClass("moving");
          $line.css("left", e.pageX + options.fixLeft);
          //如果超过最大高度，则使用最大高度值
          if(options.maxHeight > grid.height()){
              $div.height(grid.height());
          }else{
              $div.height(options.maxHeight);
          }
        }

        var $col = grid.data(key_col);
        if ($col) {
          var $dragDiv = $(".midgrid.dragDiv");
          $dragDiv.css("left", e.clientX + 1);
          $dragDiv.css("top", e.clientY + 1);
        }

        return false;
      },
      /**
       * 拖拽结束
       */
      dragEnd : function(e) {
        grid.css("cursor", "auto");
        //改变行宽
        var $line = grid.data(key_line);
        if ($line) {
          var index = $line.attr(key_index);
          var $col = $(options.colModel().get(index));
          var width = 0;

          /*
           * 此处计算的比较麻烦，主要的原因是因为div的宽度增加
           */
          if($col.offset().left < $line.offset().left){
              //计算列宽度
              width = $line.offset().left - $col.offset().left + Math.abs(options.fixLeft);
              var step = options.colModel().size();
              //设置标题宽度
              $col.width(width);
              //设置列宽度
              options.cellModel().each(function(i) {
                  var $cell = $(this);
                  if ((i % step) != index) {
                    return;
                  }
                  $cell.width(width);
                  $cell.children('div').width(width);
              });

              //防止标题有最小宽度
              if(width < $col.width()){
                  width = $col.width();
                  options.cellModel().each(function(i) {
                      var $cell = $(this);
                      if ((i % step) != index) {
                        return;
                      }
                      $cell.width(width);
                      $cell.children('div').width(width);
                  });
              }
              grid.removeData(key_line);
              model.setDragDivLocation();
          }
        }

        //交换列位置
        var $col = grid.data(key_col);
        if ($col) {
          var $dragDiv = $(".midgrid.dragDiv");

          options.afferDragCol($col, $dragDiv);
          $dragDiv.hide();
          grid.removeData(key_col);
          if (options.colModel().index($(e.target)) > -1
              && $col.attr(key_index) != $(e.target).attr(key_index)) {
            model._moveCol($(e.target), $col);
            model.setDragDivLocation();
          }// end if
        }// end if
      },

      /**
       * 鼠标滑过事件
       */
      mouseover : function() {
        $(this).addClass("mouseover");
      },

      _moveCol : function($src, $target) {
        var step = options.colModel().size();
        var cells = options.cellModel().toArray();

        var srcIndex = 1 * $src.attr(key_index);
        var targetIndex = 1 * $target.attr(key_index);

        var temp = $target.html();
        $target.html($src.html());
        $src.html(temp);

        temp = $target.attr(key_old_index);
        $target.attr(key_old_index, $src.attr(key_old_index));
        $src.attr(key_old_index, temp);

        temp = $target.width();
        $target.width($src.width());
        $src.width(temp);

        for ( var i = 0; i < cells.length; i = i + step) {
          var srcCell = $(cells[i + srcIndex]);
          var targetCell = $(cells[i + targetIndex]);

          options.removeCell(srcCell, targetCell);

        }// end for i

      },

      _init : function() {
        grid.addClass("midgrid");

        $('body').bind("mousemove", this.dragMove);
        $('body').bind("mouseup", this.dragEnd);

        options.dragDiv();

        var step = options.colModel().size();

        // 初始化列表
        options.cellModel().each(function(i) {
          var $cell = $(this);
          $cell.attr(key_old_index, i % step);
          $cell.attr(key_index, i % step);
          $div = $('<div></div>');
          $cell.wrapInner($div);
        });

        // 初始化标题列
        options.colModel().each(function(i) {
          var $col = $(this);
          $col.attr(key_old_index, i);
          $col.attr(key_index, i);
          $col.bind("mousedown", {
            source : "col"
          }, model.dragStart);

          $div = $("<div style='display:none'></div>");
          $div.addClass("midgrid");
          $div.addClass("line");
          $('body').append($div);
          $div.bind("mousedown", {
            source : "line"
          }, model.dragStart);
          $div.bind("mouseover", model.mouseover);

        });

        if(options.autoWidth){
            grid.width(options.colModel().width());
        }

        model.setDragDivLocation();
      },

      /**
       * 设置拖动div的位置和展示效果
       */
      setDragDivLocation : function() {
        options.colModel().each(function(i) {
          var $col = $(this);
          var $div = $('.midgrid.line:eq(' + i + ')');

          var offset = $col.offset();
          offset.left = offset.left + +$col.width()
            + options.fixLeft + options.offsetX;
          offset.top = offset.top + options.fixTop
            + options.offsetY;

          $div.offset(offset);
          //如果超过最大高度，则使用最大高度值
          if(options.maxHeight > grid.height()){
              $div.height(grid.height());
          }else{
              $div.height(options.maxHeight);
          }
          $div.attr(key_index, i);
          $div.show();
        });// end resetCol
      },//end setDragDivLocation

      /**
       *
       */
      callbackResize : function(){
        model.setDragDivLocation();
        if(options.callbackResize){
            options.callbackResize(this);
        }
      }
    }// end object model

    model._init();
    this.Midgrid.callbackResize = model.callbackResize;
    this.Midgrid.init = true;
  }// end fn midgrid
})(jQuery);