var batchArray = [];
var exportParam=[];
( function($) {
	$.addFlex = function(t, p) {
		if (t.grid)
			return false;
		p = $.extend( {
			height : 200,
			width : 'auto',
			striped : true,
			novstripe : false,
			minwidth : 30,
			minheight : 80,
			resizable : true,
			url : false,
			method : 'POST',
			dataType : 'xml',
			errormsg : '发生错误',
			usepager : false,
			nowrap : true,
			page : 1,
			total : 1,
			useRp : true,
			rp : 10,
			rpOptions : [ 10, 20, 30, 40, 50 ],
			title : false,
			pagestat : '显示记录从{from}到{to}，总数 {total} 条',
			pagetext : '第',
			outof : '页 共',
			findtext : '查找',
			params : [],
			procmsg : '正在处理数据，请稍候 ...',
			query : '',
			qtype : '',
			nomsg : '没有符合条件的记录存在',
			minColToggle : 1,
			showToggleBtn : true,
			singleToggleBtn : false,
			hideOnSubmit : true,
			autoload : true,
			blockOpacity : 0.5,
			preProcess : false,
			onDragCol : false,
			onToggleCol : false,
			onChangeSort : false,
			onSuccess : false,
			onError : false,
			onSubmit : false,
			onRowSelect : false,
			copyOnRowSelect : false
		}, p);
		$(t).show().attr( {
			cellPadding : 0,
			cellSpacing : 0,
			border : 0
		}).removeAttr('width');
		var g = {
			hset : {},
			rePosDrag : function() {
				var cdleft = 0 - this.hDiv.scrollLeft;
				if (this.hDiv.scrollLeft > 0)
					cdleft -= Math.floor(p.cgwidth / 2);
				$(g.cDrag).css( {
					top : g.hDiv.offsetTop + 1
				});
				var cdpad = this.cdpad;
				$('div', g.cDrag).hide();
				$('thead tr:first th:visible', this.hDiv).each( function() {
					var n = $('thead tr:first th:visible', g.hDiv).index(this);
					var cdpos = parseInt($('div', this).width());
					if (cdleft == 0)
						cdleft -= Math.floor(p.cgwidth / 2);
					cdpos = cdpos + cdleft + cdpad;
					if (isNaN(cdpos)) {
						cdpos = 0
					}
					$('div:eq(' + n + ')', g.cDrag).css( {
						'left' : cdpos + 'px'
					}).show();
					cdleft = cdpos
				})
			},
			fixHeight : function(newH) {
				newH = false;
				if (!newH)
					newH = $(g.bDiv).height();
				var hdHeight = $(this.hDiv).height();
				$('div', this.cDrag).each( function() {
					$(this).height(newH + hdHeight)
				});
				var nd = parseInt($(g.nDiv).height());
				if (nd > newH)
					$(g.nDiv).height(newH).width(200);
				else
					$(g.nDiv).height('auto').width('auto');
				$(g.block).css( {
					height : newH,
					marginBottom : (newH * -1)
				});
				var hrH = g.bDiv.offsetTop + newH;
				if (p.height != 'auto' && p.resizable)
					hrH = g.vDiv.offsetTop;
				$(g.rDiv).css( {
					height : hrH
				})
			},
			dragStart : function(dragtype, e, obj) {
				if (dragtype == 'colresize') {
					$(g.nDiv).hide();
					$(g.nBtn).hide();
					var n = $('div', this.cDrag).index(obj);
					var ow = $('th:visible div:eq(' + n + ')', this.hDiv)
							.width();
					$(obj).addClass('dragging').siblings().hide();
					$(obj).prev().addClass('dragging').show();
					this.colresize = {
						startX : e.pageX,
						ol : parseInt(obj.style.left),
						ow : ow,
						n : n
					};
					$('body').css('cursor', 'col-resize')
				} else if (dragtype == 'vresize') {
					var hgo = false;
					$('body').css('cursor', 'row-resize');
					if (obj) {
						hgo = true;
						$('body').css('cursor', 'col-resize')
					}
					this.vresize = {
						h : p.height,
						sy : e.pageY,
						w : p.width,
						sx : e.pageX,
						hgo : hgo
					}
				} else if (dragtype == 'colMove') {
					$(g.nDiv).hide();
					$(g.nBtn).hide();
					this.hset = $(this.hDiv).offset();
					this.hset.right = this.hset.left
							+ $('table', this.hDiv).width();
					this.hset.bottom = this.hset.top
							+ $('table', this.hDiv).height();
					this.dcol = obj;
					this.dcoln = $('th', this.hDiv).index(obj);
					this.colCopy = document.createElement("div");
					this.colCopy.className = "colCopy";
					this.colCopy.innerHTML = obj.innerHTML;
					if ($.browser.msie) {
						this.colCopy.className = "colCopy ie"
					}
					$(this.colCopy).css( {
						position : 'absolute',
						float : 'left',
						display : 'none',
						textAlign : obj.align
					});
					$('body').append(this.colCopy);
					$(this.cDrag).hide()
				}
				$('body').noSelect()
			},
			dragMove : function(e) {
				if (this.colresize) {
					var n = this.colresize.n;
					var diff = e.pageX - this.colresize.startX;
					var nleft = this.colresize.ol + diff;
					var nw = this.colresize.ow + diff;
					if (nw > p.minwidth) {
						$('div:eq(' + n + ')', this.cDrag).css('left', nleft);
						this.colresize.nw = nw
					}
				} else if (this.vresize) {
					var v = this.vresize;
					var y = e.pageY;
					var diff = y - v.sy;
					if (!p.defwidth)
						p.defwidth = p.width;
					if (p.width != 'auto' && !p.nohresize && v.hgo) {
						var x = e.pageX;
						var xdiff = x - v.sx;
						var newW = v.w + xdiff;
						if (newW > p.defwidth) {
							this.gDiv.style.width = newW + 'px';
							p.width = newW
						}
					}
					var newH = v.h + diff;
					if ((newH > p.minheight || p.height < p.minheight)
							&& !v.hgo) {
						this.bDiv.style.height = newH + 'px';
						p.height = newH;
						this.fixHeight(newH)
					}
					v = null
				} else if (this.colCopy) {
					$(this.dcol).addClass('thMove').removeClass('thOver');
					if (e.pageX > this.hset.right || e.pageX < this.hset.left
							|| e.pageY > this.hset.bottom
							|| e.pageY < this.hset.top) {
						$('body').css('cursor', 'move')
					} else {
						$('body').css('cursor', 'pointer')
					}
					$(this.colCopy).css( {
						top : e.pageY + 10,
						left : e.pageX + 20,
						display : 'block'
					})
				}
			},
			dragEnd : function() {
				if (this.colresize) {
					var n = this.colresize.n;
					var nw = this.colresize.nw;
					$('th:visible div:eq(' + n + ')', this.hDiv).css('width',
							nw);
					$('tr', this.bDiv).each(
							function() {
								$('td:visible div:eq(' + n + ')', this).css(
										'width', nw)
							});
					this.hDiv.scrollLeft = this.bDiv.scrollLeft;
					$('div:eq(' + n + ')', this.cDrag).siblings().show();
					$('.dragging', this.cDrag).removeClass('dragging');
					this.rePosDrag();
					this.fixHeight();
					this.colresize = false
				} else if (this.vresize) {
					this.vresize = false
				} else if (this.colCopy) {
					$(this.colCopy).remove();
					if (this.dcolt != null) {
						if (this.dcoln > this.dcolt)
							$('th:eq(' + this.dcolt + ')', this.hDiv).before(
									this.dcol);
						else
							$('th:eq(' + this.dcolt + ')', this.hDiv).after(
									this.dcol);
						this.switchCol(this.dcoln, this.dcolt);
						$(this.cdropleft).remove();
						$(this.cdropright).remove();
						this.rePosDrag();
						if (p.onDragCol) {
							p.onDragCol(this.dcoln, this.dcolt)
						}
					}
					this.dcol = null;
					this.hset = null;
					this.dcoln = null;
					this.dcolt = null;
					this.colCopy = null;
					$('.thMove', this.hDiv).removeClass('thMove');
					$(this.cDrag).show()
				}
				$('body').css('cursor', 'default');
				$('body').noSelect(false)
			},
			toggleCol : function(cid, visible) {
				var ncol = $("th[axis='col" + cid + "']", this.hDiv)[0];
				var n = $('thead th', g.hDiv).index(ncol);
				var cb = $('input[value=' + cid + ']', g.nDiv)[0];
				if (visible == null) {
					visible = ncol.hidden
				}
				if ($('input:checked', g.nDiv).length < p.minColToggle
						&& !visible) {
					return false
				}
				if (visible) {
					ncol.hidden = false;
					$(ncol).show();
					cb.checked = true
				} else {
					ncol.hidden = true;
					$(ncol).hide();
					cb.checked = false
				}
				$('tbody tr', t).each( function() {
					if (visible) {
						$('td:eq(' + n + ')', this).show()
					} else {
						$('td:eq(' + n + ')', this).hide()
					}
				});
				this.rePosDrag();
				if (p.onToggleCol) {
					p.onToggleCol(cid, visible)
				}
				return visible
			},
			switchCol : function(cdrag, cdrop) {
				$('tbody tr', t).each(
						function() {
							if (cdrag > cdrop)
								$('td:eq(' + cdrop + ')', this).before(
										$('td:eq(' + cdrag + ')', this));
							else
								$('td:eq(' + cdrop + ')', this).after(
										$('td:eq(' + cdrag + ')', this))
						});
				if (cdrag > cdrop) {
					$('tr:eq(' + cdrop + ')', this.nDiv).before(
							$('tr:eq(' + cdrag + ')', this.nDiv))
				} else {
					$('tr:eq(' + cdrop + ')', this.nDiv).after(
							$('tr:eq(' + cdrag + ')', this.nDiv))
				}
				if ($.browser.msie && $.browser.version < 7.0) {
					$('tr:eq(' + cdrop + ') input', this.nDiv)[0].checked = true
				}
				this.hDiv.scrollLeft = this.bDiv.scrollLeft
			},
			scroll : function() {
				this.hDiv.scrollLeft = this.bDiv.scrollLeft;
				this.rePosDrag()
			},
			addData : function(data) {
				/*add by naxu :调整遮盖层等div高度宽度位置等
				 * 由于flexigrid高度定义为非参数设定，而是直接强制修改页面div高度宽度所致
				 */
				g.fixHeight();
				if (p.dataType == 'json') {
					data = $.extend( {
						rows : [],
						page : 0,
						total : 0
					}, data)
				}
				if (p.preProcess) {
					data = p.preProcess(data)
				}
				$('.pReload', this.pDiv).removeClass('loading');
				
				/**
				 * 由于if (!data) {... return false;}导致了后面的代码执行被终止
				 * 照成了查询无数据的时候，在未设置某些参数时，g.block遮蔽层无法被取消
				 * 顾在方法开始处就隐藏遮蔽层
				 */
				//liujingwei add beging
				$(g.block).hide();
				//liujingwei add finish
				
				
				this.loading = false;
				if (!data) {
					$('.pPageStat', this.pDiv).html(p.errormsg);
					return false
				}
				if (p.dataType == 'xml') {
					p.total = +$('rows total', data).text()
				} else {
					p.total = data.total
				}
				if (p.total == 0) {
					$('tr, a, td, div', t).unbind();
					$(t).empty();
					p.pages = 1;
					p.page = 1;
					this.buildpager();
					$('.pPageStat', this.pDiv).html(p.nomsg);
					return false
				}
				p.pages = Math.ceil(p.total / p.rp);
				if (p.dataType == 'xml') {
					p.page = +$('rows page', data).text()
				} else {
					p.page = data.page
				}
				this.buildpager();
				var tbody = document.createElement('tbody');
				if (p.dataType == 'json') {
					$
							.each(
									data.rows,
									function(i, row) {
										var tr = document.createElement('tr');
										if (!(i % 2) && p.striped) {
											tr.className = 'erow'
										}
										if (row.id) {
											tr.id = 'row' + row.id
										}
										$('thead tr:first th', g.hDiv)
												.each(
														function() {
															var td = document
																	.createElement('td');
															var idx = $(this)
																	.attr(
																			'axis')
																	.substr(3);
															td.align = this.align;
															if (typeof row.cell[idx] != "undefined") {
																td.innerHTML = (row.cell[idx] != null) ? row.cell[idx]
																		: ''
															} else {
																td.innerHTML = row.cell[p.colModel[idx].name]
															}
															$(td)
																	.attr(
																			'abbr',
																			$(
																					this)
																					.attr(
																							'abbr'));
															// update by zhaoc 2012-09-19
															if($(this).attr('checkbox')){
																//update by zhaoc 2013-04-23 增加复选框是否被选中属性
																td.innerHTML = "<input type='checkbox' name='checkValue' value='"+row.id+"' "+(row.cell[idx]=="checked" ? "checked='checked'":"")+"/>";
															}else{
																td.innerHTML = row.cell[idx];
															}
															// update by zhaoc end
															$(tr).append(td);
															td = null

														});
										if ($('thead', this.gDiv).length < 1) {
											for (idx = 0; idx < cell.length; idx++) {
												var td = document
														.createElement('td');
												if (typeof row.cell[idx] != "undefined") {
													td.innerHTML = (row.cell[idx] != null) ? row.cell[idx]
															: ''
												} else {
													td.innerHTML = row.cell[p.colModel[idx].name]
												}
												$(tr).append(td);
												td = null
											}
										}
										$(tbody).append(tr);
										tr = null
									})
				} else if (p.dataType == 'xml') {
					var i = 1;
					$("rows row", data).each(
							function() {
								i++;
								var tr = document.createElement('tr');
								if (!(i % 2) && p.striped) {
									tr.className = 'erow'
								}
								var nid = $(this).attr('id');
								if (nid) {
									tr.id = 'row' + nid
								}
								nid = null;
								var robj = this;
								$('thead tr:first th', g.hDiv).each(
										function() {
											var td = document
													.createElement('td');
											var idx = $(this).attr('axis')
													.substr(3);
											td.align = this.align;
											td.innerHTML = $(
													"cell:eq(" + idx + ")",
													robj).text();
											$(td).attr('abbr',
													$(this).attr('abbr'));
											$(tr).append(td);
											td = null
										});
								if ($('thead', this.gDiv).length < 1) {
									$('cell', this).each( function() {
										var td = document.createElement('td');
										td.innerHTML = $(this).text();
										$(tr).append(td);
										td = null
									})
								}
								$(tbody).append(tr);
								tr = null;
								robj = null
							})
				}
				$('tr', t).unbind();
				$(t).empty();
				$(t).append(tbody);
				this.addCellProp();
				this.addRowProp();
				this.rePosDrag();
				tbody = null;
				data = null;
				i = null;
				if (p.onSuccess) {
					p.onSuccess(this)
				}
				if (p.hideOnSubmit) {
					$(g.block).remove()
				}
				this.hDiv.scrollLeft = this.bDiv.scrollLeft;
				if ($.browser.opera) {
					$(t).css('visibility', 'visible')
				}
			},
			changeSort : function(th) {
				if (this.loading) {
					return true
				}
				$(g.nDiv).hide();
				//update by zhaoc 2012-10-15 如果是 single开关则 改变排序不影响显示
				if(!p.singleToggleBtn) {
					$(g.nBtn).hide();
				}
				if (p.sortname == $(th).attr('abbr')) {
					if (p.sortorder == 'asc') {
						p.sortorder = 'desc'
					} else {
						p.sortorder = 'asc'
					}
				}
				$(th).addClass('sorted').siblings().removeClass('sorted');
				$('.sdesc', this.hDiv).removeClass('sdesc');
				$('.sasc', this.hDiv).removeClass('sasc');
				$('div', th).addClass('s' + p.sortorder);
				p.sortname = $(th).attr('abbr');
				if (p.onChangeSort) {
					p.onChangeSort(p.sortname, p.sortorder)
				} else {
					this.populate()
				}
			},
			buildpager : function() {
				$('.pcontrol input', this.pDiv).val(p.page);
				$('.pcontrol span', this.pDiv).html(p.pages);
				var r1 = (p.page - 1) * p.rp + 1;
				var r2 = r1 + p.rp - 1;
				if (p.total < r2) {
					r2 = p.total
				}
				var stat = p.pagestat;
				stat = stat.replace(/{from}/, r1);
				stat = stat.replace(/{to}/, r2);
				stat = stat.replace(/{total}/, p.total);
				$('.pPageStat', this.pDiv).html(stat)
			},
			populate : function() {
				if (this.loading) {
					return true
				}
				if (p.onSubmit) {
					var gh = p.onSubmit();
					if (!gh) {
						return false
					}
				}
				this.loading = true;
				if (!p.url) {
					return false
				}
				$('.pPageStat', this.pDiv).html(p.procmsg);
				$('.pReload', this.pDiv).addClass('loading');
				$(g.block).css( {
					top : g.bDiv.offsetTop
				});
				if (p.hideOnSubmit) {
					$(this.gDiv).prepend(g.block)
				}
				if ($.browser.opera) {
					$(t).css('visibility', 'hidden')
				}
				if (!p.newp) {
					p.newp = 1
				}
				if (p.page > p.pages) {
					p.page = p.pages
				}
				var param = [ {
					name : 'page',
					value : p.newp
				}, {
					name : 'rp',
					value : p.rp
				}, {
					name : 'sortname',
					value : p.sortname
				}, {
					name : 'sortorder',
					value : p.sortorder
				}, {
					name : 'query',
					value : p.query
				}, {
					name : 'qtype',
					value : p.qtype
				} ];
				if (p.params.length) {
					for ( var pi = 0; pi < p.params.length; pi++) {
						param[param.length] = p.params[pi]
					}
				}
				$.ajax( {
					type : p.method,
					url : p.url,
					data : param,
					dataType : p.dataType,
					async : false,
					success : function(data) {
						g.addData(data)
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						try {
							if (p.onError)
								p.onError(XMLHttpRequest, textStatus,
										errorThrown)
						} catch (e) {
						}
					}
				})
			},
			doSearch : function() {
				p.query = $('input[name=q]', g.sDiv).val();
				p.qtype = $('select[name=qtype]', g.sDiv).val();
				p.newp = 1;
				this.populate()
			},
			changePage : function(ctype) {
				if (this.loading) {
					return true
				}
				switch (ctype) {
				case 'first':
					p.newp = 1;
					break;
				case 'prev':
					if (p.page > 1) {
						p.newp = parseInt(p.page) - 1
					}
					break;
				case 'next':
					if (p.page < p.pages) {
						p.newp = parseInt(p.page) + 1
					}
					break;
				case 'last':
					p.newp = p.pages;
					break;
				case 'input':
					var nv = parseInt($('.pcontrol input', this.pDiv).val());
					if (isNaN(nv)) {
						nv = 1
					}
					if (nv < 1) {
						nv = 1
					} else if (nv > p.pages) {
						nv = p.pages
					}
					$('.pcontrol input', this.pDiv).val(nv);
					p.newp = nv;
					break
				}
				if (p.newp == p.page) {
					return false
				}
				if (p.onChangePage) {
					p.onChangePage(p.newp)
				} else {
					this.populate()
				}

				/**
				 * add by wangjh 2012.09.28
				 * 为了区分flexgrid查询按钮事件和翻页按钮事件
				 *
				 * 当点击查询按钮时在页面添加name为clearSession的hidden标签
				 * 在点击翻页按钮时去掉此标签带的参数
				 */
				var flag = false;
				if (p.params.length) {
					for ( var pi = 0; pi < p.params.length; pi++) {
						//alert(p.params[pi].name);
						if(p.params[pi].name == "clearSession"){
							flag = true;
						}
					}
				}
				if(flag){
					p.params.pop();
				}
				/**
				 * add by wangjh 2012.09.28
				 */
			},
			addCellProp : function() {
				$('tbody tr td', g.bDiv).each( function() {
					var tdDiv = document.createElement('div');
					var n = $('td', $(this).parent()).index(this);
					var pth = $('th:eq(' + n + ')', g.hDiv).get(0);
					if (pth != null) {
						if (p.sortname == $(pth).attr('abbr') && p.sortname) {
							this.className = 'sorted'
						}
						$(tdDiv).css( {
							textAlign : pth.align,
							width : $('div:first', pth)[0].style.width
						});
						if (pth.hidden) {
							$(this).css('display', 'none')
						}
					}
					if (p.nowrap == false) {
						$(tdDiv).css('white-space', 'normal')
					}
					if (this.innerHTML == '') {
						this.innerHTML = '&nbsp;'
					}
					tdDiv.innerHTML = this.innerHTML;
					var prnt = $(this).parent()[0];
					var pid = false;
					if (prnt.id) {
						pid = prnt.id.substr(3)
					}
					if (pth != null) {
						if (pth.process)
							pth.process(tdDiv, pid)
					}
					$(this).empty().append(tdDiv).removeAttr('width')
				})
			},
			getCellDim : function(obj) {
				var ht = parseInt($(obj).height());
				var pht = parseInt($(obj).parent().height());
				var wt = parseInt(obj.style.width);
				var pwt = parseInt($(obj).parent().width());
				var top = obj.offsetParent.offsetTop;
				var left = obj.offsetParent.offsetLeft;
				var pdl = parseInt($(obj).css('paddingLeft'));
				var pdt = parseInt($(obj).css('paddingTop'));
				return {
					ht : ht,
					wt : wt,
					top : top,
					left : left,
					pdl : pdl,
					pdt : pdt,
					pht : pht,
					pwt : pwt
				}
			},
			addRowProp : function() {
				$('tbody tr', g.bDiv).each( function() {
					//click change to dblclick
					$(this).dblclick( function(e) {
						var obj = (e.target || e.srcElement);
						if (obj.href /*|| obj.type*/)
							return true;
					    //update by wengcz 2013-4-18,点击多选框，特殊处理
						if(obj.type=="checkbox")
						{
							if(p.onRowSelect) {
								p.copyOnRowSelect = p.onRowSelect;
								p.onRowSelect = false;
							}
						}else{
							$(this).toggleClass('trSelected');
							if(p.copyOnRowSelect) {
								p.onRowSelect = p.copyOnRowSelect;
								p.copyOnRowSelect = false;
							}
						}

					// update by zhaoc 2012-09-19 这里的修改使checkbox的选中状态与行的trSelected css保持同步.
						//var isSelected = $(this).hasClass("trSelected");$(this).find("input[type='checkbox']").each(function() {isSelected ? $(this).attr("checked","checked") : $(this).removeAttr("checked");});
						// update by zhaoc end
						if (p.singleSelect)
							$(this).siblings().removeClass('trSelected')
						//add by sunwei
						if (p.onRowSelect){
							var pid = this.id.substr(3);
							p.onRowSelect(pid);
						}
					}).mousedown( function(e) {
						if (e.shiftKey) {
							$(this).toggleClass('trSelected');
							g.multisel = true;
							this.focus();
							$(g.gDiv).noSelect()
						}
					}).mouseup( function() {
						if (g.multisel) {
							g.multisel = false;
							$(g.gDiv).noSelect(false)
						}
					}).hover( function(e) {
						if (g.multisel) {
							$(this).toggleClass('trSelected')
						}
					}, function() {
					});
					if ($.browser.msie && $.browser.version < 7.0) {
						$(this).hover( function() {
							$(this).addClass('trOver')
						}, function() {
							$(this).removeClass('trOver')
						})
					}
				})
			},
			pager : 0
		};
		if (p.colModel) {
			thead = document.createElement('thead');
			var tr = document.createElement('tr');
			for ( var i = 0; i < p.colModel.length; i++) {
				var cm = p.colModel[i];
				var th = document.createElement('th');
				th.innerHTML = cm.display;
				if (cm.name && cm.sortable) {
					$(th).attr('abbr', cm.name)
				}
				$(th).attr('axis', 'col' + i);
				if (cm.align) {
					th.align = cm.align
				}
				if (cm.width) {
					$(th).attr('width', cm.width)
				}
				if ($(cm).attr('hide')) {
					th.hidden = true
				}
				if (cm.process) {
					th.process = cm.process
				}
				//update by zhaoc 2012-09-19
				if (cm.checkbox){$(th).attr('checkbox',cm.checkbox); }
				//update by zhaoc end
				$(tr).append(th)
			}
			$(thead).append(tr);
			$(t).prepend(thead)
		}
		g.gDiv = document.createElement('div');
		g.mDiv = document.createElement('div');
		g.hDiv = document.createElement('div');
		g.bDiv = document.createElement('div');
		g.vDiv = document.createElement('div');
		g.rDiv = document.createElement('div');
		g.cDrag = document.createElement('div');
		g.block = document.createElement('div');
		g.nDiv = document.createElement('div');
		g.nBtn = document.createElement('div');
		g.iDiv = document.createElement('div');
		g.tDiv = document.createElement('div');
		g.sDiv = document.createElement('div');
		g.pDiv = document.createElement('div');
		if (!p.usepager) {
			g.pDiv.style.display = 'none'
		}
		g.hTable = document.createElement('table');
		g.gDiv.className = 'flexigrid';
		if (p.width != 'auto') {
			g.gDiv.style.width = p.width + 'px'
		}
		if ($.browser.msie) {
			$(g.gDiv).addClass('ie')
		}
		if (p.novstripe) {
			$(g.gDiv).addClass('novstripe')
		}
		$(t).before(g.gDiv);
		$(g.gDiv).append(t);
		if (p.buttons) {
			g.tDiv.className = 'tDiv';
			var tDiv2 = document.createElement('div');
			tDiv2.className = 'tDiv2';
			for ( var i = 0; i < p.buttons.length; i++) {
				var btn = p.buttons[i];
				if (!btn.separator) {
					var btnDiv = document.createElement('div');
					btnDiv.className = 'fbutton';
					btnDiv.innerHTML = "<div><span>" + btn.name
							+ "</span></div>";
					if (btn.bclass)
						$('span', btnDiv).addClass(btn.bclass).css( {
							paddingLeft : 20
						});
					btnDiv.onpress = btn.onpress;
					btnDiv.name = btn.name;
					if (btn.onpress) {
						$(btnDiv).click( function() {
							this.onpress(this.name, g.gDiv)
						})
					}
					$(tDiv2).append(btnDiv);
					if ($.browser.msie && $.browser.version < 7.0) {
						$(btnDiv).hover( function() {
							$(this).addClass('fbOver')
						}, function() {
							$(this).removeClass('fbOver')
						})
					}
				} else {
					$(tDiv2).append("<!--<div class='btnseparator'></div>-->")
				}
			}
			$(g.tDiv).append(tDiv2);
			$(g.tDiv).append("<div style='clear:both'></div>");
			$(g.gDiv).prepend(g.tDiv)
		}
		g.hDiv.className = 'hDiv';
		$(t).before(g.hDiv);
		g.hTable.cellPadding = 0;
		g.hTable.cellSpacing = 0;
		$(g.hDiv).append('<div class="hDivBox"></div>');
		$('div', g.hDiv).append(g.hTable);
		var thead = $("thead:first", t).get(0);
		if (thead)
			$(g.hTable).append(thead);
		thead = null;
		if (!p.colmodel)
			var ci = 0;
		$('thead tr:first th', g.hDiv)
				.each(
						function(i) {
							var thdiv = document.createElement('div');
							if ($(this).attr('abbr')) {
								$(this).click( function(e) {
									if (!$(this).hasClass('thOver'))
										return false;
									var obj = (e.target || e.srcElement);
									if (obj.href || obj.type)
										return true;
									g.changeSort(this)
								});
								if ($(this).attr('abbr') == p.sortname) {
									this.className = 'sorted';
									thdiv.className = 's' + p.sortorder
								}
							}
							if (this.hidden) {
								$(this).hide()
							}
							if (!p.colmodel) {
								$(this).attr('axis', 'col' + ci++)
							}
							$(thdiv).css( {
								textAlign : this.align,
								width : this.width + 'px'
							});
							thdiv.innerHTML = this.innerHTML;
							$(this)
									.empty()
									.append(thdiv)
									.removeAttr('width')
									.mousedown( function(e) {
										//alert("a");
										g.dragStart('colMove', e, this)
									})
									.hover(
											function() {
												if (!g.colresize
														&& !$(this).hasClass(
																'thMove')
														&& !g.colCopy) {
													$(this).addClass('thOver')
												}
												if ($(this).attr('abbr') != p.sortname
														&& !g.colCopy
														&& !g.colresize
														&& $(this).attr('abbr')) {
													$('div', this).addClass(
															's' + p.sortorder)
												} else if ($(this).attr('abbr') == p.sortname
														&& !g.colCopy
														&& !g.colresize
														&& $(this).attr('abbr')) {
													var no = (p.sortorder == 'asc') ? 'desc'
															: 'asc';
													$('div', this).removeClass(
															's' + p.sortorder)
															.addClass('s' + no)
												}
												if (g.colCopy) {
													var n = $('th', g.hDiv)
															.index(this);
													if (n == g.dcoln) {
														return false
													}
													if (n < g.dcoln) {
														$(this).append(
																g.cdropleft)
													} else {
														$(this).append(
																g.cdropright)
													}
													g.dcolt = n
												} else if (!g.colresize) {
													var nv = $('th:visible',
															g.hDiv).index(this);
													var onl = parseInt($(
															'div:eq(' + nv + ')',
															g.cDrag)
															.css('left'));
													var nw = jQuery(g.nBtn)
															.outerWidth();
													var nl = onl
															- nw
															+ Math
																	.floor(p.cgwidth / 2);
													$(g.nDiv).hide();
													//update by zhaoc 2012-10-15 如果是 single开关则 改变列宽不影响显示
													if(!p.singleToggleBtn) {
														$(g.nBtn).hide();
													}
													//update by zhaoc 2012-10-15 增加 显示隐藏列只在第一列显示
													if(p.singleToggleBtn) {
														if(i == 0) {
															$(g.nBtn).css( {
																'left' : nl,
																top : g.hDiv.offsetTop
															}).show();
															var ndw = parseInt($(g.nDiv)
																	.width());
															$(g.nDiv).css( {
																top : g.bDiv.offsetTop
															});
															if ((nl + ndw) > $(g.gDiv)
																	.width()) {
																$(g.nDiv).css('left',
																		onl - ndw + 1)
															} else {
																$(g.nDiv).css('left',
																		nl)
															}
														}
													} else {
														$(g.nBtn).css( {
															'left' : nl,
															top : g.hDiv.offsetTop
														}).show();
														var ndw = parseInt($(g.nDiv)
																.width());
														$(g.nDiv).css( {
															top : g.bDiv.offsetTop
														});
														if ((nl + ndw) > $(g.gDiv)
																.width()) {
															$(g.nDiv).css('left',
																	onl - ndw + 1)
														} else {
															$(g.nDiv).css('left',
																	nl)
														}
													}
													if ($(this).hasClass(
															'sorted')) {
														$(g.nBtn).addClass(
																'srtd')
													} else {
														$(g.nBtn).removeClass(
																'srtd')
													}
												}
											},
											function() {
												$(this).removeClass('thOver');
												if ($(this).attr('abbr') != p.sortname) {
													$('div', this).removeClass(
															's' + p.sortorder)
												} else if ($(this).attr('abbr') == p.sortname) {
													var no = (p.sortorder == 'asc') ? 'desc'
															: 'asc';
													$('div', this).addClass(
															's' + p.sortorder)
															.removeClass(
																	's' + no)
												}
												if (g.colCopy) {
													$(g.cdropleft).remove();
													$(g.cdropright).remove();
													g.dcolt = null
												}
											})
						});
		g.bDiv.className = 'bDiv';
		$(t).before(g.bDiv);
		$(g.bDiv).css( {
			height : (p.height == 'auto') ? 'auto' : p.height + "px"
			/*height : ($(window).height()-$(".rightToolbar").height()-$(".sDiv").height()-$(".hDiv").height()-$('.pDiv').height())*/
		}).scroll( function(e) {
			g.scroll()
		}).append(t);
		if (p.height == 'auto') {
			$('table', g.bDiv).addClass('autoht')
		}
		g.addCellProp();
		g.addRowProp();
		var cdcol = $('thead tr:first th:first', g.hDiv).get(0);
		if (cdcol != null) {
			g.cDrag.className = 'cDrag';
			g.cdpad = 0;
			g.cdpad += (isNaN(parseInt($('div', cdcol).css('borderLeftWidth'))) ? 0
					: parseInt($('div', cdcol).css('borderLeftWidth')));
			g.cdpad += (isNaN(parseInt($('div', cdcol).css('borderRightWidth'))) ? 0
					: parseInt($('div', cdcol).css('borderRightWidth')));
			g.cdpad += (isNaN(parseInt($('div', cdcol).css('paddingLeft'))) ? 0
					: parseInt($('div', cdcol).css('paddingLeft')));
			g.cdpad += (isNaN(parseInt($('div', cdcol).css('paddingRight'))) ? 0
					: parseInt($('div', cdcol).css('paddingRight')));
			g.cdpad += (isNaN(parseInt($(cdcol).css('borderLeftWidth'))) ? 0
					: parseInt($(cdcol).css('borderLeftWidth')));
			g.cdpad += (isNaN(parseInt($(cdcol).css('borderRightWidth'))) ? 0
					: parseInt($(cdcol).css('borderRightWidth')));
			g.cdpad += (isNaN(parseInt($(cdcol).css('paddingLeft'))) ? 0
					: parseInt($(cdcol).css('paddingLeft')));
			g.cdpad += (isNaN(parseInt($(cdcol).css('paddingRight'))) ? 0
					: parseInt($(cdcol).css('paddingRight')));
			$(g.bDiv).before(g.cDrag);
			var cdheight = $(g.bDiv).height();
			var hdheight = $(g.hDiv).height();
			$(g.cDrag).css( {
				top : -hdheight + 'px'
			});
			$('thead tr:first th', g.hDiv).each( function() {
				var cgDiv = document.createElement('div');
				$(g.cDrag).append(cgDiv);
				if (!p.cgwidth) {
					p.cgwidth = $(cgDiv).width()
				}
				$(cgDiv).css( {
					height : cdheight + hdheight
				}).mousedown( function(e) {
					g.dragStart('colresize', e, this)
				});
				if ($.browser.msie && $.browser.version < 7.0) {
					g.fixHeight($(g.gDiv).height());
					$(cgDiv).hover( function() {
						g.fixHeight();
						$(this).addClass('dragging')
					}, function() {
						if (!g.colresize)
							$(this).removeClass('dragging')
					})
				}
			})
		}
		if (p.striped) {
			$('tbody tr:even', g.bDiv).addClass('erow')
		}
		if (p.resizable && p.height != 'auto') {
			g.vDiv.className = 'vGrip';
			$(g.vDiv).mousedown( function(e) {
				g.dragStart('vresize', e)
			}).html('<span></span>');
			$(g.bDiv).after(g.vDiv)
		}
		if (p.resizable && p.width != 'auto' && !p.nohresize) {
			g.rDiv.className = 'hGrip';
			$(g.rDiv).mousedown( function(e) {
				g.dragStart('vresize', e, true)
			}).html('<span></span>').css('height', $(g.gDiv).height());
			if ($.browser.msie && $.browser.version < 7.0) {
				$(g.rDiv).hover( function() {
					$(this).addClass('hgOver')
				}, function() {
					$(this).removeClass('hgOver')
				})
			}
			$(g.gDiv).append(g.rDiv)
		}
		if (p.usepager) {
			g.pDiv.className = 'pDiv';
			g.pDiv.innerHTML = '<div class="pDiv2"></div>';
			$(g.bDiv).after(g.pDiv);
			var html = ' <div class="pGroup"> <div class="pFirst pButton"><span></span></div><div class="pPrev pButton"><span></span></div> </div> <!--<div class="btnseparator"></div>--> <div class="pGroup"><span class="pcontrol">'
					+ p.pagetext
					+ ' <input type="text" size="4" value="1" /> '
					+ p.outof
					+ ' <span> 1 </span> 页</span></div> <!--<div class="btnseparator"></div>--> <div class="pGroup"> <div class="pNext pButton"><span></span></div><div class="pLast pButton"><span></span></div> </div> <!--<div class="btnseparator"></div>--> <div class="pGroup"> <div class="pReload pButton"><span></span></div> </div> <!--<div class="btnseparator"></div>--> <div class="pGroup"><span class="pPageStat"></span></div>';
			$('div', g.pDiv).html(html);
			$('.pReload', g.pDiv).click( function() {
				g.populate()
			});
			$('.pFirst', g.pDiv).click( function() {
				g.changePage('first')
			});
			$('.pPrev', g.pDiv).click( function() {
				g.changePage('prev')
			});
			$('.pNext', g.pDiv).click( function() {
				g.changePage('next')
			});
			$('.pLast', g.pDiv).click( function() {
				g.changePage('last')
			});
			$('.pcontrol input', g.pDiv).keydown( function(e) {
				if (e.keyCode == 13)
					g.changePage('input')
			});
			if ($.browser.msie && $.browser.version < 7)
				$('.pButton', g.pDiv).hover( function() {
					$(this).addClass('pBtnOver')
				}, function() {
					$(this).removeClass('pBtnOver')
				});
			if (p.useRp) {
				var opt = '', sel = '';
				for ( var nx = 0; nx < p.rpOptions.length; nx++) {
					if (p.rp == p.rpOptions[nx])
						sel = 'selected="selected"';
					else
						sel = '';
					opt += "<option value='" + p.rpOptions[nx] + "' " + sel
							+ " >" + p.rpOptions[nx] + "&nbsp;&nbsp;</option>"
				}
				$('.pDiv2', g.pDiv)
						.prepend(
								"<div class='pGroup'><span class='pcontrol'>每页显示<select name='rp'>"
										+ opt
										+ "</select>条&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></div> <!--<div class='btnseparator'></div>-->");
				$('select', g.pDiv).change( function() {
					if (p.onRpChange) {
						p.onRpChange(+this.value)
					} else {
						p.newp = 1;
						p.rp = +this.value;
						g.populate()
					}
				})
			}
			if (p.searchitems) {
				//update by zhaoc
				if($('div.tDiv2').html()==null) {
					g.tDiv.className = 'tDiv';
					var tDiv2 = document.createElement('div');
					tDiv2.className = 'tDiv2';

					var btnDiv = document.createElement('div');
					btnDiv.className = 'fbutton';
					btnDiv.innerHTML = "<div id='searchClone'><span>查询</span></div>";
					$('span', btnDiv).addClass('dSearch').css( {
						paddingLeft : 20
					});

					$(tDiv2).append(btnDiv);
					$(g.tDiv).append(tDiv2);
					$(g.tDiv).append("<div style='clear:both'></div>");
					$(g.gDiv).prepend(g.tDiv)
				} else {
					var btnDiv = document.createElement('div');
					btnDiv.className = 'fbutton';
					btnDiv.innerHTML = "<div id='searchClone'><span>查询</span></div>";
					$('span', btnDiv).addClass('dSearch').css( {
						paddingLeft : 20
					});
					$('div.tDiv2').append(btnDiv);
				}
				//update by zhaoc
				$('#searchClone').click( function() { //flexigride自带查询
					$(g.nBtn).hide();//add by zhaoc 2012-10-16 点击查询按钮删除以藏显示列的开关小箭头
					$(g.sDiv).slideToggle('normal', function() {
						$('.sDiv:visible input:first', g.gDiv).trigger('focus');
						/*
						 * add by naxu
						 * IE在点击查询按钮后,会自动触发window.resize,调用header_cmp_flexigrid_js.jsp公共头文件中的方法,修改flexgrid数据部分.bDiv高度
						 * Firefox不会触发window.resize,手动调用header_cmp_flexigrid_js.jsp公共头文件中的resizebDiv()方法,达到修改数据.bDiv高度的效果
						 */
						resizebDiv();

					})
				});

				//update by zhaoc
				/*$('.pDiv2', g.pDiv)
						.prepend(
								"<div class='pGroup'> <div class='pSearch pButton'><span></span></div> </div>  <!--<div class='btnseparator'></div>-->");*/

				$('.pSearch', g.pDiv).click( function() {
					$(g.sDiv).slideToggle('', function() {
						$('.sDiv:visible input:first', g.gDiv).trigger('focus')
					})
				});
				g.sDiv.className = 'sDiv';
				var sitems = p.searchitems;
				var sopt = '<option value="">-----------请选择-----------</option>', sel = '';
				for ( var s = 0; s < sitems.length; s++) {
					if (p.qtype == '' && sitems[s].isdefault == true) {
						p.qtype = sitems[s].name;
						sel = 'selected="selected"'
					} else {
						sel = ''
					}
					sopt += "<option value='" + sitems[s].name + "' " + sel
							+ " >" + sitems[s].display
							+ "&nbsp;&nbsp;</option>"
				}
				if (p.qtype == '') {
					p.qtype = sitems[0].name
				}
				$(g.sDiv).append(
						"<div class='sDiv2'>"
								+ "<table border='0'><tr><td>"
								+ p.findtext
								+ " <input type='text' value='" + p.query
								+ "' size='30' name='q' class='input' /> </td>"
								+ "<td> <select name='qtype' class='newSelect'>" + sopt
								+ "</select></td></tr>"
								+ "<tr><td colspan='2' align='right'>&nbsp;&nbsp;" +
										"<ul class='singTable'>" +
										"<li class='pageButon2' name='qclear1'>" +
										"<a name='qclear2' href='#'>重置条件</a>" +
										"</li>" +
										"<li class='pageButon2' name='qbutton1'>" +
										"<a name='qbutton2' href='#'>提交查询</a>" +
										"</li>" +
										"</ul></td>"
								//+ "&nbsp;&nbsp;<input type='button' value='提交查询' name='qbutton' />"
								//+ "&nbsp;&nbsp;<div name='qbutton' class='fbutton'style='position:relative;left:500px;float:left'><div id='searchClone'><span class='dSearch' style='padding-left: 20px;'>提交查询</span></div></div>"
								+ "</tr></table>"		
								+ "</div>");
				//update by zhaoc
				if($("#searchDiv").html()!=null) {
					$("#searchDiv").each(function(){
				       //alert($(this).html());
				       $(g.sDiv).append(
							"<div class='sDiv2'><form>" + $(this).html() +"</form></div>");
				    });
				}
				$('input[name=q]', g.sDiv).keydown( function(e) {
					if (e.keyCode == 13) {
						g.doSearch()
					}
				});
				$('input[name=qbutton]', g.sDiv).click( function() {
					g.doSearch()
				});
				$('a[name=qbutton2]', g.sDiv).click( function() {
					g.doSearch()
				});
				$('a[name=qclear2]', g.sDiv).click( function() {
					$("input[name=q]").val("");
					$("select[name=qtype] option").eq(0).attr('selected', 'selected');
				});
				$('select[name=qtype]', g.sDiv).keydown( function(e) {
					if (e.keyCode == 13) {
						g.doSearch()
					}
				});
				$('input[value=Clear]', g.sDiv).click( function() {
					$('input[name=q]', g.sDiv).val('');
					p.query = '';
					g.doSearch()
				});
				//$(g.bDiv).after(g.sDiv)
				//update by zhaoc
				$(g.tDiv).after(g.sDiv)
			}
			//自定义查询
			if(p.customSearch) {
				//update by zhaoc
				if($('div.tDiv2').html()==null) {
					/*g.tDiv.className = 'tDiv';*/
					var tDiv2 = document.createElement('div');
					/*tDiv2.className = 'tDiv2';*/

					var btnDiv = document.createElement('div');
					/*btnDiv.className = 'fbutton';
					btnDiv.innerHTML = "<div id='searchClone'><span>查询</span></div>";*/
					/*$('span', btnDiv).addClass('dSearch').css( {
						paddingLeft : 20
					});*/

					$(tDiv2).append(btnDiv);
					$(g.tDiv).append(tDiv2);
					$(g.tDiv).append("<div style='clear:both'></div>");
					$(g.gDiv).prepend(g.tDiv)
				} /*else {
					var btnDiv = document.createElement('div');
					btnDiv.className = 'fbutton';
					btnDiv.innerHTML = "<div id='searchClone'><span>查询</span></div>";
					$('span', btnDiv).addClass('dSearch').css( {
						paddingLeft : 20
					});
					$('div.tDiv2').append(btnDiv);
				}*/
				//update by zhaoc
				$('#search').click( function() { //自定义查询
					$(g.block).hide();//add bu naxu 点击查询按钮时隐藏灰色遮盖层,调整数据部分.bDiv高度后,对应修改遮罩层高度,然后再显示遮罩层
					$(g.nBtn).hide();//add by zhaoc 2012-10-16 点击查询按钮删除以藏显示列的开关小箭头
					$(g.sDiv).slideToggle('normal', function() {
						$('.sDiv:visible input:first', g.gDiv).trigger('focus');
						/*
						 * add by naxu
						 * IE在点击查询按钮后,会自动触发window.resize,调用header_cmp_flexigrid_js.jsp公共头文件中的方法,修改flexgrid数据部分.bDiv高度
						 * Firefox不会触发window.resize,手动调用header_cmp_flexigrid_js.jsp公共头文件中的resizebDiv()方法,达到修改数据.bDiv高度的效果
						 */
						resizebDiv();
						// add by naxu 调整遮罩层上边界位置
						$(g.block).css( {
							top : g.bDiv.offsetTop
						});
						// add by naxu 重新定义拖拽条位置及长度
						g.rePosDrag();
						/*
						 * add by naxu :调整遮盖层等div高度宽度位置等
						 * 由于flexigrid高度定义为非参数设定，而是直接强制修改页面div高度宽度所致
						 */
						g.fixHeight();
						$(g.block).show();//add bu naxu 待查询条件滑动显示完成后，恢复显示遮盖层
					})
				});

				g.sDiv.className = 'sDiv';
				//update by zhaoc
				if($("#searchDiv").html()!=null) {
					$("#searchDiv").each(function(){
				       $(g.sDiv).append(
							"<div class='sDiv2'><form>" + $(this).html() +"</form></div>");
				    });
				}
				$(g.tDiv).after(g.sDiv)
			}
		}
		
		//lichp add begin 2014-01-16
		//当一个页面初始化多个flexigrid的查询功能，避免按钮全部初始化到第一个grid
		if(p.expandSearch) {
			var id = p.expandSearch;
			g.tDiv.className = 'tDiv';
			var tDiv2 = document.createElement('div');
			tDiv2.className = 'tDiv2';

			var btnDiv = document.createElement('div');
			btnDiv.className = 'fbutton';
			btnDiv.innerHTML = "<div id='searchClone" + id + "'><span>查询</span></div>";					
			$('span', btnDiv).addClass('dSearch').css( {
				paddingLeft : 20
			});

			$(tDiv2).append(btnDiv);
			$(g.tDiv).append(tDiv2);
			$(g.tDiv).append("<div style='clear:both'></div>");
			$(g.gDiv).prepend(g.tDiv);			

			$('#searchClone' + id).bind("click",function(){
				$(g.block).hide();
				$(g.nBtn).hide();
				$(g.sDiv).slideToggle('normal', function() {
					$('.sDiv:visible input:first', g.gDiv).trigger('focus');
					if($(".sDiv:eq(" + id + ")").is(":hidden")) {
						$(this).siblings(".bDiv").height($(this).siblings(".bDiv").height() + $(this).height()-1);
					} else if($(".sDiv:eq(" + id + ")").is(":visible")) {
						$(this).siblings(".bDiv").height($(this).siblings(".bDiv").height() - $(this).height()-1);
					}

					$(g.block).css( {
						top : g.bDiv.offsetTop
					});
					g.rePosDrag();
					g.fixHeight();
					$(g.block).show();
				})
			});			
			g.sDiv.className = 'sDiv';			
			if($("#searchDiv" + id).html()!=null) {
				$("#searchDiv" + id).each(function(){
			       $(g.sDiv).append(
						"<div class='sDiv2'><form>" + $(this).html() +"</form></div>");
			    });
			}
			$(g.tDiv).after(g.sDiv)
		} 
		//lichp add end
		
		$(g.pDiv, g.sDiv).append("<div style='clear:both'></div>");
		if (p.title) {
			g.mDiv.className = 'mDiv';
			g.mDiv.innerHTML = '<div class="ftitle">' + p.title + '</div>';
			$(g.gDiv).prepend(g.mDiv);
			if (p.showTableToggleBtn) {
				$(g.mDiv)
						.append(
								'<div class="ptogtitle" title="Minimize/Maximize Table"><span></span></div>');
				$('div.ptogtitle', g.mDiv).click( function() {
					$(g.gDiv).toggleClass('hideBody');
					$(this).toggleClass('vsble')
				})
			}
		}
		g.cdropleft = document.createElement('span');
		g.cdropleft.className = 'cdropleft';
		g.cdropright = document.createElement('span');
		g.cdropright.className = 'cdropright';
		g.block.className = 'gBlock';
		var gh = $(g.bDiv).height();
		var gtop = g.bDiv.offsetTop;
		$(g.block).css( {
			width : g.bDiv.style.width,
			height : gh,
			background : 'white',
			position : 'relative',
			marginBottom : (gh * -1),
			zIndex : 1,
			top : gtop,
			left : '0px'
		});
		$(g.block).fadeTo(0, p.blockOpacity);
		if ($('th', g.hDiv).length) {
			g.nDiv.className = 'nDiv';
			g.nDiv.innerHTML = "<table cellpadding='0' cellspacing='0'><tbody></tbody></table>";
			$(g.nDiv).css( {
				marginBottom : (gh * -1),
				display : 'none',
				top : gtop
			}).noSelect();
			var cn = 0;
			$('th div', g.hDiv).each(
					function(i) {
						var kcol = $("th[axis='col" + cn + "']", g.hDiv)[0];
						var chk = 'checked="checked"';
						if (kcol.style.display == 'none') {
							chk = ''
						}
						//update by zhaoc signle 2012-10-16 开关第一项默认选中不可以被修改
						if(p.singleToggleBtn) {
							if(i != 0) {
								$('tbody', g.nDiv).append(
										'<tr><td class="ndcol1"><input type="checkbox" '
										+ chk + ' class="togCol" value="' + cn
										+ '" /></td><td class="ndcol2">'
										+ this.innerHTML + '</td></tr>');
							} else {
								$('tbody', g.nDiv).append(
										'<tr><td class="ndcol1"><input type="checkbox" '
										+ chk + ' class="togCol" value="' + cn
										+ '" disabled="true" /></td><td class="ndcol2">'
										+ this.innerHTML + '</td></tr>');
							}
						} else {
							$('tbody', g.nDiv).append(
									'<tr><td class="ndcol1"><input type="checkbox" '
									+ chk + ' class="togCol" value="' + cn
									+ '" /></td><td class="ndcol2">'
									+ this.innerHTML + '</td></tr>');
						}
						cn++
					});
			if ($.browser.msie && $.browser.version < 7.0)
				$('tr', g.nDiv).hover( function() {
					$(this).addClass('ndcolover')
				}, function() {
					$(this).removeClass('ndcolover')
				});
			$('td.ndcol2', g.nDiv).click(
					function() {
						//add by zhaoc 2012-10-16 增加 single开关 第一项不允许被修改 默认选中
						if(p.singleToggleBtn && $(this).prev().find('input').val() == 0) {
							return false;
						}
						if ($('input:checked', g.nDiv).length <= p.minColToggle
								&& $(this).prev().find('input')[0].checked)
							return false;
						return g.toggleCol($(this).prev().find('input').val())
					});
			$('input.togCol', g.nDiv).click(
					function() {
						if ($('input:checked', g.nDiv).length < p.minColToggle
								&& this.checked == false)
							return false;
						$(this).parent().next().trigger('click')
					});
			$(g.gDiv).prepend(g.nDiv);
			$(g.nBtn).addClass('nBtn').html('<div></div>').attr('title',
					'隐藏/显示 列').click( function() {
				$(g.nDiv).toggle();
				return true
			});
			if (p.showToggleBtn) {
				$(g.gDiv).prepend(g.nBtn)

			}
		}
		$(g.iDiv).addClass('iDiv').css( {
			display : 'none'
		});
		$(g.bDiv).append(g.iDiv);
		$(g.bDiv).hover( function() {
			$(g.nDiv).hide();
			//update by zhaoc 2012-10-15 如果是 single开关则 鼠标移动不影响显示
			if(!p.singleToggleBtn) {
				$(g.nBtn).hide()
			}
		}, function() {
			if (g.multisel) {
				g.multisel = false
			}
		});
		$(g.gDiv).hover( function() {
		}, function() {
			$(g.nDiv).hide();
			//update by zhaoc 2012-10-15 如果是 single开关则 鼠标移动不影响显示
			if(!p.singleToggleBtn) {
				$(g.nBtn).hide()
			}
		});
		$(document).mousemove( function(e) {
			g.dragMove(e)
		}).mouseup( function(e) {
			g.dragEnd()
		}).hover( function() {
		}, function() {
			g.dragEnd()
		});
		if ($.browser.msie && $.browser.version < 7.0) {
			$('.hDiv,.bDiv,.mDiv,.pDiv,.vGrip,.tDiv, .sDiv', g.gDiv).css( {
				width : '100%'
			});
			$(g.gDiv).addClass('ie6');
			if (p.width != 'auto') {
				$(g.gDiv).addClass('ie6fullwidthbug')
			}
		}
		g.rePosDrag();
		g.fixHeight();
		t.p = p;
		t.grid = g;
		if (p.url && p.autoload) {
			g.populate()
		}
		return t
	};
	var docloaded = false;
	$(document).ready( function() {
		docloaded = true
	});
	$.fn.flexigrid = function(p) {
		return this.each( function() {
			if (!docloaded) {
				$(this).hide();
				var t = this;
				$(document).ready( function() {
					$.addFlex(t, p)
				})
			} else {
				$.addFlex(this, p)
			}
		})
	};
	$.fn.flexReload = function(p) {
		return this.each( function() {
			if (this.grid && this.p.url)
				this.grid.populate()
		})
	};
	$.fn.flexOptions = function(p) {
		return this.each( function() {
			if (this.grid)
				$.extend(this.p, p)
		})
	};
	//update by zhaoc
	$.fn.submitSearch = function() {
		var p = "";
		return this.each( function() {
			if (this.grid && this.p.url) {
				p = "[{name:'opensearch',value:'on'}";
				$(".sDiv2 input, .sDiv2 select, .sDiv2 textarea").each(function(){
                    var value = $(this).val();
                    //replace [']
                    value = value.replace("'","\\'");
                    //trim
                    value = $.trim(value);

					if($(this).attr("type")=='radio'){
						if($(this).attr("checked")) {
							p += (",{name:'" +$(this).attr("name") + "',value:'" + value + "'" + "}");
						}
					} else if($(this).attr("type")=='checkbox'){
						if($(this).attr("checked")) {
							p += (",{name:'" +$(this).attr("name") + "',value:'" + value + "'" + "}");
						}
					}
					else {
						p += (",{name:'" +$(this).attr("name") + "',value:'" + value + "'" + "}");
					}
				 });
				p += "]";
				var op;
				eval("op = " + p);
				$.extend(this.p, {params:op, newp : 1})
				this.grid.populate()
			}
		})
	};	
	//update by lichp
	$.fn.submitParamSearch = function(id) {
		var p = "";
		return this.each( function() {
			if (this.grid && this.p.url) {
				p = "[{name:'opensearch',value:'on'}";
				$(".sDiv2:eq("+ id +") input, .sDiv2:eq("+ id +") select, .sDiv2:eq("+ id +") textarea").each(function(){
                    var value = $(this).val();
                    //replace [']
                    value = value.replace("'","\\'");
                    //trim
                    value = $.trim(value);

					if($(this).attr("type")=='radio'){
						if($(this).attr("checked")) {
							p += (",{name:'" +$(this).attr("name") + "',value:'" + value + "'" + "}");
						}
					} else if($(this).attr("type")=='checkbox'){
						if($(this).attr("checked")) {
							p += (",{name:'" +$(this).attr("name") + "',value:'" + value + "'" + "}");
						}
					}
					else {
						p += (",{name:'" +$(this).attr("name") + "',value:'" + value + "'" + "}");
					}
				 });
				p += "]";
				var op;
				eval("op = " + p);
				$.extend(this.p, {params:op, newp : 1})
				this.grid.populate()
			}
		})
	};
	//update by zhaoc
	$.fn.resetSearch = function() {
		return this.each( function() {
			if (this.grid && this.p.url) {
				$(".sDiv2 form").each(function(){
					$(this)[0].reset();
				 });
			}
		})
	};
	$.fn.flexToggleCol = function(cid, visible) {
		return this.each( function() {
			if (this.grid)
				this.grid.toggleCol(cid, visible)
		})
	};
	$.fn.flexAddData = function(data) {
		return this.each( function() {
			if (this.grid)
				this.grid.addData(data)
		})
	};
	$.fn.noSelect = function(p) {
		var prevent = (p == null) ? true : p;
		if (prevent) {
			return this.each( function() {
				if ($.browser.msie || $.browser.safari)
					$(this).bind('selectstart', function() {
						return false
					});
				else if ($.browser.mozilla) {
					$(this).css('MozUserSelect', 'none');
					$('body').trigger('focus')
				} else if ($.browser.opera)
					$(this).bind('mousedown', function() {
						return false
					});
				else
					$(this).attr('unselectable', 'on')
			})
		} else {
			return this.each( function() {
				if ($.browser.msie || $.browser.safari)
					$(this).unbind('selectstart');
				else if ($.browser.mozilla)
					$(this).css('MozUserSelect', 'inherit');
				else if ($.browser.opera)
					$(this).unbind('mousedown');
				else
					$(this).removeAttr('unselectable', 'on');
			});
		}
	};
	$.fn.flexSearch = function(p) {
		return this.each( function() {
			if (this.grid && this.p.searchitems)
				this.grid.doSearch();
		});
	};
	$.fn.getCheckValue = function() {
        //如果batchArray有值，表示页面使用里翻页记录复选框功能
        //否则页面没有使用翻页记录复选框功能
		if(batchArray.length > 0){
			return batchArray;
		}else{
			var arry = [];
			$("input[name='checkValue']").each(function(){
				if($(this).attr("checked")) {
					arry.push($(this).val());
				}
			});
			return arry;
		}
	};
})(jQuery);

function defaultOnSuccess() {
	if(batchArray.length > 0) {
		$("input[name='checkValue']").each(function(){
			for(var i = 0; i < batchArray.length; i++) {
				if($(this).val() == batchArray[i]) {
					$(this).attr("checked","true");
				}
			}
		});
	}
	$("input[name='checkValue']").each(function(){
		$(this).click(function() {
			if($(this).attr("checked")) {
				batchArray.push($(this).val());
			} else {
				var index = -1;
				for(var i = 0; i < batchArray.length; i++) {
					if(batchArray[i] == $(this).val()) {
						index = i;
					}
				}
				if(index != -1) {
					batchArray.splice(index,1);
				}
			}
		});
	});
}

//flexigrid全选按钮
function checkclick(c) {
	if(c.checked) {
		$("input[name='checkValue']").each(function(){
			if(!$(this).attr("checked")) {
				$(this).attr("checked",true);
			}
		});

	}else{
		$("input[name='checkValue']").each(function(){
			$(this).attr("checked",false);
		});
	}
	/*var aar = $("#flexigrid").getCheckValue();
	var str = "";
	$(aar).each(function(index,data){
		str +=("," + data);
	});
	alert(str);*/
}

//flexigrid全选按钮扩展 by li-chp
function checkClickNew(c) {
	var id = c.id + "flexigrid";
	if(c.checked) {
		$("#"+ id +" input[name='checkValue']").each(function(){
			if(!$(this).attr("checked")) {
				$(this).attr("checked",true);
				batchArray.push($(this).val());
			}
		});

	}else{
		$("#"+ id +" input[name='checkValue']").each(function(){
			$(this).attr("checked",false);
			var index = -1;
			for(var i = 0; i < batchArray.length; i++) {
				if(batchArray[i] == $(this).val()) {
					index = i;
				}
			}
			if(index != -1) {
				batchArray.splice(index,1);
			}
		});
	}
}