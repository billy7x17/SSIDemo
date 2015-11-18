// JavaScript Document
 $(document).ready(function(){
	$(function() {
	    $("#datepicker").datepicker({showOn: 'both', buttonImage: 'themes/default/images/tol_date.png', buttonImageOnly: true, buttonText:''});
		$("#datepicker1").datepicker({showOn: 'both', buttonImage: 'themes/default/images/tol_date.png', buttonImageOnly: true, buttonText:''});
	
		//add by wengcz 2012-1-29, 带时分秒的日期控件，时分秒日期控件
		$('#clanderPickernj').datetimepicker({showOn: 'both', buttonImage: 'themes/default/images/tol_date.png', buttonImageOnly: true, buttonText:'',
			showSecond: true,
			timeFormat: 'hh:mm:ss', 
			timeOnlyTitle: '请选择时间',
			timeText: '时间',
			hourText: '小时',
			minuteText: '分钟',
			secondText: '秒',
			currentText: '当前时间',
			closeText: '确认'
        });
		$('#clanderPickernj1').datetimepicker({showOn: 'both', buttonImage: 'themes/default/images/tol_date.png', buttonImageOnly: true, buttonText:'',
			showSecond: true,
			timeFormat: 'hh:mm:ss', 
			timeOnlyTitle: '请选择时间',
			timeText: '时间',
			hourText: '小时',
			minuteText: '分钟',
			secondText: '秒',
			currentText: '当前时间',
			closeText: '确认'
        });
				
		$('#receiveTime').datetimepicker({showOn: 'both', buttonImage: 'themes/default/images/tol_date.png', buttonImageOnly: true, buttonText:'',
			showSecond: true,
			timeFormat: 'hh:mm:ss', 
			timeOnlyTitle: '请选择时间',
			timeText: '时间',
			hourText: '小时',
			minuteText: '分钟',
			secondText: '秒',
			currentText: '当前时间',
			closeText: '确认'
        });
		
		$('#solveTime').datetimepicker({showOn: 'both', buttonImage: 'themes/default/images/tol_date.png', buttonImageOnly: true, buttonText:'',
			showSecond: true,
			timeFormat: 'hh:mm:ss', 
			timeOnlyTitle: '请选择时间',
			timeText: '时间',
			hourText: '小时',
			minuteText: '分钟',
			secondText: '秒',
			currentText: '当前时间',
			closeText: '确认'
        });
		
		$('#timePickernj').timepicker({showOn: 'both', buttonImage: 'themes/default/images/tol_date.png', buttonImageOnly: true, buttonText:'',
			showSecond: true,
			timeFormat: 'hh:mm:ss',
			timeOnlyTitle: '请选择时间',
			timeText: '时间',
			hourText: '小时',
			minuteText: '分钟',
			secondText: '秒',
			currentText: '当前时间',
			closeText: '确认'
		});
		$('#timePickernj1').timepicker({showOn: 'both', buttonImage: 'themes/default/images/tol_date.png', buttonImageOnly: true, buttonText:'',
			showSecond: true,
			timeFormat: 'hh:mm:ss',
			timeOnlyTitle: '请选择时间',
			timeText: '时间',
			hourText: '小时',
			minuteText: '分钟',
			secondText: '秒',
			currentText: '当前时间',
			closeText: '确认'
		});

	
	});
});
 
// add by na.x 2012-12-07
// 时间控件时间与当前时间比对
function cPTimeBeforeNow(cpTime){
	var now = new Date();
	// 不明白原比较方式为何将当前系统时间提前2分钟，暂时注释掉此操作
    // var nowMS = now.getTime() - 1000 * 60 * 2;
    // now.setTime(nowMS);
    return Date.parse(cpTime) < now.getTime();
}

// 时间控件格式"MM/dd/yyyy HH:mm:ss"

// 时间之间时间比较，跟早
function cPTimeEarlier(time1, time2){
	return Date.parse(time1) < Date.parse(time2);
}