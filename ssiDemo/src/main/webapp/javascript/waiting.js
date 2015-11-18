var dialog = '<div id="dialog-modal" title="提示消息" style="display:none">';
	dialog += '<p style="margin-left:15px;">请求处理中......</p>';
	dialog += '</div>';
	var dSubmit = null;
	
	$(function(){
		var $mainForm = $("#mainForm");
		//画面不存在form时
		if(!$mainForm[0]){
			return ;
		}
		
		//替换默认提交方法
	    dSubmit = $mainForm[0].submit;
	    $mainForm[0].submit = function(){
	    	checkSubmit();
		};
		$mainForm.bind("submit", checkSubmit);
		$mainForm.append(dialog);

	});//end init function

	
	function showDailog(){
		$( "#dialog-modal" ).dialog({
    		 height: 140,
    		 resizable: false,
    		 dialogClass: "alert",
    		 modal: true
   		 });

 		 $(".ui-dialog-titlebar-close").hide();
    }
    
    function checkSubmit(){
    	var $mainForm = $("#mainForm");
    	var submited = $mainForm.data("submited");
    	showDailog();
    	if(submited){
        	return false;
        }else{
        	$mainForm.data("submited", "true");
        	$mainForm[0].submit = dSubmit;
        	$mainForm[0].submit();
            return false;
        }//end if
    }//end fun