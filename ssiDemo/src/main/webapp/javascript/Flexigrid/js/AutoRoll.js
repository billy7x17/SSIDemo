var gData;
function getData(data) {
	gData = data;
	return data;
}
function autoRoll(data, maxrow, time){
	while(gData.rows.length > 0) {
		gData.rows.pop();
	}
	var obj = data;
	//gData.page = obj.page;
	gData.total = obj.total;
	var olength = obj.rows.length;
	for(var i = 0; i < olength;i++){
		for(var j = 0; j < gData.rows.length;j++){
			if(obj.rows[i].id == gData.rows[j].id){
				gData.rows.splice(j, 1);
			}
		}
		
		gData.rows.unshift(obj.rows[i]);
	}
	
	while(gData.rows.length > maxrow) {
		gData.rows.pop();
	}
     $("#flexigrid").flexAddData(gData);
	    // 添加滚动样式
		var h = $("#flexigrid tr:first-child").height();
		$("#flexigrid").css('marginTop',-h * olength + 'px');
		$("#flexigrid").animate({ 
			marginTop:"0px"
		  }, time * olength /* ,function(){
				for(var i = 0; i < olength;i++){
					$("#flexigrid tr").each(function(index) {
						if(index > olength - 1) {
							if(obj.rows[i].id == $(this).attr("id").substr(3)){
								$("#flexigrid tr").eq(index).remove();
								
								for(var j = 0; j < gData.rows.length;j++){
									if(j > olength - 1 && obj.rows[i].id == gData.rows[j].id){
										gData.rows.splice(j, 1);
									}
								}
							}
						}
					});
				}
		  }*/
		  );

}