var tab=null;
$( function() {
	  tab = new TabView( {
		containerId :'tab_menu',// 容器ID,
		pageid :'page',// pageId 页面 pageID
		cid :'tab_po'// 指定tab的id
	});
	tab.add( {
		id :'firstPage_tab',// tabId
		title :"首页",// tab标题
		url :"FirstPage.action",// 该tab链接的URL
		isClosed :false	// 该tab是否可以关闭
	});
	
});
var index=1;
function addTab(tabtitle,tabid,taburl){
	if($("#"+tabid+"_tab").length>0){
		tab.activate(tabid+'_tab');
	}else{
		var id=tabid+'_tab';
	 
		tab.add( {
			id :id,
			title :tabtitle==""?("标签页面"+(index+1)):tabtitle,
			url :taburl,
			isClosed :true
		});
		index++;
	}
}
function closeTab(id){
	tab.close(id+'_tab');
}