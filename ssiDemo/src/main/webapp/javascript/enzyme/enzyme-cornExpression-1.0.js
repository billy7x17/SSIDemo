/*******************************************************************************
 * @(#)enzyme_cornExpression.js 2011-7-6
 *
 * Copyright 2011 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/

/**
 * 此js库的为cornExpression表达式提供支持
 * @author <a href="mailto:liujingwei@neusoft.com">Dreams Liu </a>
 * @version $Revision 1.1 $ 2011-7-6 下午02:44:22
 */

/**
 * 此方法的主要目的是构建CronExpression类实例
 *
 * @param options
 *            有两种参数值：String、Object String类型：cron表达式字符串
 *
 * @returns {Object}
 * @exception ReferenceError cron表达式解析异常时
 */
Enzyme.CornExpression = function(options) {
  if(!options){
	  options = {};
  }
  if(options.constructor == String) {
    if (!Enzyme.CornExpression.cronValidate(options)) {
    	throw new ReferenceError("Parse CornExpression error:" + options);
    };
  }

  var empty = {};
  var defaults = {
    cronExpression : '0 * * * * ?',

    // 秒 0-59 , - * /
    secondsExp : '0',

    // 分 0-59 , - * /
    minutesExp : '*',

    // 小时 0-23 , - * /
    hoursExp : '*',

    // 日 1-31 , - * ? / L W C
    daysOfMonthExp : '*',

    // 月 1-12 or JAN-DEC , - * /
    monthsExp : '*',

    // 周几 1-7 or SUN-SAT , - * ? / L C #
    daysOfWeekExp : '?',

    // 年 (可选字段) empty, 1970-2099 , - * /
    yearExp : ''

  };
  var settings = jQuery.extend(empty, defaults, options);

  if (!Enzyme.CornExpression.cronValidate(settings.cronExpression)) {
	  throw new ReferenceError("Parse CornExpression error:" + settings.cronExpression);
  };

  this.getSetting = function(key) {
    return settings[key];
  };
  this.setSetting = function(key, value) {
    settings[key] = value;
  };
  var cronExpression = this.getSetting('cronExpression');

  var ary = cronExpression.split(" ");
  if (ary.length < 6) {
    throw {
      message : 'length error'
    };
  } else if (ary.length > 6) {
    this.setSetting('yearExp', ary[6]);
  }

  this.setSetting('secondsExp', ary[0]);
  this.setSetting('minutesExp', ary[1]);
  this.setSetting('hoursExp', ary[2]);
  this.setSetting('daysOfMonthExp', ary[3]);
  this.setSetting('monthsExp', ary[4]);
  this.setSetting('daysOfWeekExp', ary[5]);

  this.getCornExpression = function() {
    var buf = "";
    buf += this.getSetting('secondsExp') + " ";
    buf += this.getSetting('minutesExp') + " ";
    buf += this.getSetting('hoursExp') + " ";
    buf += this.getSetting('daysOfMonthExp') + " ";
    buf += this.getSetting('monthsExp') + " ";
    buf += this.getSetting('daysOfWeekExp');
    if(this.getSetting('yearExp')){
    	buf += " " + this.getSetting('yearExp');
    }
    return buf;
  };
  this.getChineseCornExp = function() {
    var rv = '';
    var temp = '';

    if (this.getSetting('monthsExp') == '*') {
      rv += '每月 - ';
    } else {
      rv += '第[' + this.getSetting('monthsExp') + ']月 - ';
    }

    if (this.getSetting('daysOfWeekExp') == '?') {
    } else if (this.getSetting('daysOfWeekExp') == '*') {
      rv += '每周 - ';
    } else {
      rv += '周[' + this.getSetting('daysOfWeekExp') + '] - ';
    }

    temp = this.getSetting('daysOfMonthExp');
    if (temp == '?') {

    } else if (temp == '*') {
      rv += '每天 - ';
    } else {
      rv += '第[' + temp + ']天 - ';
    }

    temp = this.getSetting('hoursExp');
    if (temp == '*') {
      rv += '每小时 - ';
    } else {
      rv += '第[' + temp + ']小时 - ';
    }

    temp = this.getSetting('minutesExp');
    if (temp == '*') {
      rv += '每分钟 - ';
    } else {
      rv += '第[' + temp + ']分钟 - ';
    }

    temp = this.getSetting('secondsExp');
    if (temp == '*') {
      rv += '每秒 - ';
    } else {
      rv += '第[' + temp + ']秒 ';
    }

    // rv += "执行";
    return rv;
  };
  this.setSecondsExp = function(exp) {
    this.setSetting('secondsExp', exp);
  };
  this.getSecondsExp = function() {
    return this.getSetting('secondsExp');
  };
  this.setMinutesExp = function(exp) {
    this.setSetting('minutesExp', exp);
  };
  this.getMinutesExp = function() {
    return this.getSetting('minutesExp');
  };
  this.setHoursExp = function(exp) {
    this.setSetting('hoursExp', exp);
  };
  this.getHoursExp = function() {
    return this.getSetting('hoursExp');
  };
  this.setDaysOfMonthExp = function(exp) {
    this.setSetting('daysOfMonthExp', exp);
  };
  this.getDaysOfMonthExp = function() {
    return this.getSetting('daysOfMonthExp');
  };
  this.setMonthsExp = function(exp) {
    this.setSetting('monthsExp', exp);
  };
  this.getMonthsExp = function() {
    return this.getSetting('monthsExp');
  };
  this.setDaysOfWeekExp = function(exp) {
    this.setSetting('daysOfWeekExp', exp);
  };
  this.getDaysOfWeekExp = function() {
    return this.getSetting('daysOfWeekExp');
  };
  this.setYearExp = function(exp) {
    this.setSetting('yearExp', exp);
  };
  this.getYearExp = function() {
    return this.getSetting('yearExp');
  };
}; // end function [Enzyme.CornExpression]
/**
 * 此方法的主要目的是校验字符串是否是标准的cron表达式
 *
 * @param cronExpression
 *            cron表达式字符串
 * @returns {Boolean} true 表示字符串是标准表达式 false 表示字符串不是标准表达式
 */
Enzyme.CornExpression.cronValidate = function (cronExpression ) {
  // alert("校验函数的开始！");
  var cronParams = cronExpression.split(" ");

  if (cronParams.length < 6 || cronParams.length > 7) {
    return false;
  }

  // CronTrigger cronTrigger = new CronTrigger();
  // cronTrigger.setCronExpression( cronExpression );

  if (cronParams[3] == "?" || cronParams[5]=="?") {
    // Check seconds param
    if (!checkSecondsField(cronParams[0])) {
      return false;
    }

    // Check minutes param
    if (!checkMinutesField(cronParams[1])) {
      return false;
    }

    // Check hours param
    if (!checkHoursField(cronParams[2])) {
      return false;
    }

    // Check day-of-month param
    if (!checkDayOfMonthField(cronParams[3])) {
      return false;
    }

    // Check months param
    if (!checkMonthsField(cronParams[4])) {
      return false;
    }

    // Check day-of-week param
    if (!checkDayOfWeekField(cronParams[5])) {
      return false;
    }

    // Check year param
    if (cronParams.length == 7) {
      if (!checkYearField(cronParams[6])) {
        return false;
      }
    }

    return true;
  } else {
    return false;
  }
};
/**
 *
 * @param secondsField
 * @returns
 */
function checkSecondsField(secondsField) {
  return checkField(secondsField, 0, 59);
}

/**
 *
 * @param secondsField
 * @param minimal
 * @param maximal
 * @returns
 */
function checkField(secondsField, minimal, maximal) {
  if (secondsField.indexOf("-") > -1 ) {
    var startValue = secondsField.substring(0, secondsField.indexOf( "-" ));
    var endValue = secondsField.substring(secondsField.indexOf( "-" ) + 1);

    if (!(checkIntValue(startValue, minimal, maximal, true) && checkIntValue(endValue, minimal, maximal, true))) {
      return false;
    }
    try {
      var startVal = parseInt(startValue, 10);
      var endVal = parseInt(endValue, 10);

      return endVal > startVal;
    } catch (e) {
      return false;
    }
  } else if (secondsField.indexOf(",") > -1) {
    return checkListField(secondsField, minimal, maximal);
  } else if (secondsField.indexOf( "/" ) > -1) {
    return checkIncrementField( secondsField, minimal, maximal );
  } else if (secondsField.indexOf( "*" ) != -1) {
    return true;
  } else {
    return checkIntValue(secondsField, minimal, maximal);
  }
}

/**
 *
 * @param value
 * @param minimal
 * @param maximal
 * @param checkExtremity
 * @returns {Boolean}
 */
function checkIntValue(value, minimal, maximal, checkExtremity) {
  try {
    var val = parseInt(value, 10);
    // 判断是否为整数
    if (value == val) {
      if (checkExtremity) {
        if (val < minimal || val > maximal) {
          return false;
        }
      }

      return true;
    }

    return false;
  } catch (e) {
    return false;
  }
}

/**
 *
 * @param minutesField
 * @returns
 */
function checkMinutesField(minutesField) {
  return checkField(minutesField, 0, 59);
}

/**
 *
 * @param hoursField
 * @returns
 */
function checkHoursField(hoursField) {
  return checkField(hoursField, 0, 23);
}

/**
 *
 * @param dayOfMonthField
 * @returns
 */
function checkDayOfMonthField(dayOfMonthField) {
  if (dayOfMonthField == "?") {
    return true;
  }

  if (dayOfMonthField.indexOf("L") >= 0) {
    return checkFieldWithLetter(dayOfMonthField, "L", 1, 7, -1, -1);
  } else if ( dayOfMonthField.indexOf("W") >= 0) {
    return checkFieldWithLetter(dayOfMonthField, "W", 1, 31, -1, -1);
  } else if (dayOfMonthField.indexOf("C") >= 0) {
    return checkFieldWithLetter(dayOfMonthField, "C", 1, 31, -1, -1);
  } else {
    return checkField( dayOfMonthField, 1, 31 );
  }
}

/**
 *
 * @param monthsField
 * @returns
 */
function checkMonthsField(monthsField) {
  /*
   * monthsField = StringUtils.replace( monthsField, "JAN", "1" ); monthsField =
   * StringUtils.replace( monthsField, "FEB", "2" ); monthsField =
   * StringUtils.replace( monthsField, "MAR", "3" ); monthsField =
   * StringUtils.replace( monthsField, "APR", "4" ); monthsField =
   * StringUtils.replace( monthsField, "MAY", "5" ); monthsField =
   * StringUtils.replace( monthsField, "JUN", "6" ); monthsField =
   * StringUtils.replace( monthsField, "JUL", "7" ); monthsField =
   * StringUtils.replace( monthsField, "AUG", "8" ); monthsField =
   * StringUtils.replace( monthsField, "SEP", "9" ); monthsField =
   * StringUtils.replace( monthsField, "OCT", "10" ); monthsField =
   * StringUtils.replace( monthsField, "NOV", "11" ); monthsField =
   * StringUtils.replace( monthsField, "DEC", "12" );
   */

  monthsField.replace("JAN", "1");
  monthsField.replace("FEB", "2");
  monthsField.replace("MAR", "3");
  monthsField.replace("APR", "4");
  monthsField.replace("MAY", "5");
  monthsField.replace("JUN", "6");
  monthsField.replace("JUL", "7");
  monthsField.replace("AUG", "8");
  monthsField.replace("SEP", "9");
  monthsField.replace("OCT", "10");
  monthsField.replace("NOV", "11");
  monthsField.replace("DEC", "12");

  return checkField(monthsField, 1, 31);
}

/**
 *
 * @param dayOfWeekField
 * @returns
 */
function checkDayOfWeekField(dayOfWeekField) {
  /*
   * dayOfWeekField = StringUtils.replace( dayOfWeekField, "SUN", "1" );
   * dayOfWeekField = StringUtils.replace( dayOfWeekField, "MON", "2" );
   * dayOfWeekField = StringUtils.replace( dayOfWeekField, "TUE", "3" );
   * dayOfWeekField = StringUtils.replace( dayOfWeekField, "WED", "4" );
   * dayOfWeekField = StringUtils.replace( dayOfWeekField, "THU", "5" );
   * dayOfWeekField = StringUtils.replace( dayOfWeekField, "FRI", "6" );
   * dayOfWeekField = StringUtils.replace( dayOfWeekField, "SAT", "7" );
   */

  dayOfWeekField.replace("SUN", "1" );
  dayOfWeekField.replace("MON", "2" );
  dayOfWeekField.replace("TUE", "3" );
  dayOfWeekField.replace("WED", "4" );
  dayOfWeekField.replace("THU", "5" );
  dayOfWeekField.replace("FRI", "6" );
  dayOfWeekField.replace("SAT", "7" );

  if (dayOfWeekField == "?") {
    return true;
  }

  if (dayOfWeekField.indexOf("L") >= 0) {
    return checkFieldWithLetter(dayOfWeekField, "L", 1, 7, -1, -1);
  } else if (dayOfWeekField.indexOf("C") >= 0) {
    return checkFieldWithLetter(dayOfWeekField, "C", 1, 7, -1, -1);
  } else if (dayOfWeekField.indexOf("#") >= 0) {
    return checkFieldWithLetter(dayOfWeekField, "#", 1, 7, 1, 5);
  } else {
    return checkField(dayOfWeekField, 1, 7);
  }
}

/**
 *
 * @param yearField
 * @returns
 */
function checkYearField(yearField) {
  return checkField(yearField, 1970, 2099);
}

/**
 *
 * @param value
 * @param letter
 * @param minimalBefore
 * @param maximalBefore
 * @param minimalAfter
 * @param maximalAfter
 * @returns {Boolean}
 */
function checkFieldWithLetter(value, letter, minimalBefore, maximalBefore,
minimalAfter, maximalAfter) {
  var canBeAlone = false;
  var canHaveIntBefore = false;
  var canHaveIntAfter = false;
  var mustHaveIntBefore = false;
  var mustHaveIntAfter = false;

  if (letter == "L") {
    canBeAlone = true;
    canHaveIntBefore = true;
    canHaveIntAfter = false;
    mustHaveIntBefore = false;
    mustHaveIntAfter = false;
  }
  if (letter == "W" || letter == "C") {
    canBeAlone = false;
    canHaveIntBefore = true;
    canHaveIntAfter = false;
    mustHaveIntBefore = true;
    mustHaveIntAfter = false;
  }
  if (letter == "#") {
    canBeAlone = false;
    canHaveIntBefore = true;
    canHaveIntAfter = true;
    mustHaveIntBefore = true;
    mustHaveIntAfter = true;
  }

  var beforeLetter = "";
  var afterLetter = "";

  if (value.indexOf(letter) >= 0 ) {
    beforeLetter = value.substring( 0, value.indexOf(letter));
  }

  if (!value.endsWith(letter)) {
    afterLetter = value.substring( value.indexOf( letter ) + 1 );
  }

  if (value.indexOf(letter) >= 0) {
    if (letter == value) {
      return canBeAlone;
    }

    if (canHaveIntBefore) {
      if (mustHaveIntBefore && beforeLetter.length == 0) {
        return false;
      }

      if (!checkIntValue(beforeLetter, minimalBefore, maximalBefore, true)) {
        return false;
      }
    } else {
      if (beforeLetter.length > 0 ) {
        return false;
      }
    }

    if (canHaveIntAfter) {
      if ( mustHaveIntAfter && afterLetter.length == 0 ) {
        return false;
      }

      if (!checkIntValue(afterLetter, minimalAfter, maximalAfter, true)) {
        return false;
      }
    } else {
      if (afterLetter.length > 0) {
        return false;
      }
    }
  }

  return true;
}

/*
* function checkIntValue(value, minimal, maximal) { return checkIntValue(value,
* minimal, maximal, true); }
*/
/**
 *
 * @param value
 * @param minimal
 * @param maximal
 * @returns
 */
function checkIncrementField(value, minimal, maximal) {
  var start = value.substring(0, value.indexOf("/"));

  var increment = value.substring(value.indexOf("/") + 1);

  if (!("*" == start)) {
    return checkIntValue(start, minimal, maximal, true) && checkIntValue(increment, minimal, maximal, false);
  } else {
    return checkIntValue(increment, minimal, maximal, true);
  }
}

function checkListField(value, minimal, maximal ) {
  var st = value.split(",");

  var values = new Array(st.length);

  for(var j = 0; j < st.length; j++) {
    values[j] = st[j];
  }

  var previousValue = -1;

  for (var i= 0; i < values.length; i++) {
    var currentValue = values[i];

    if (!checkIntValue(currentValue, minimal, maximal, true)) {
      return false;
    }

    try {
      var val = parseInt(currentValue, 10);

      if (val <= previousValue) {
        return false;
      } else {
        previousValue = val;
      }
    } catch (e) {
      // we have always an int
    }
  }

  return true;
}

/**
 * 将cron表达式翻译成中文描述
 * @param expression
 * @returns {String}
 */
function translateCronExpression(expression){
	var unit = new Array("秒", "分", "时", "日", "月", "周", "年");
	var intervalUnit = new Array("秒钟", "分钟", "小时", "天", "个月", "周", "年");
	var desc = new Array(7);
	var exArr = expression.split(" ");
	// 处理年
	if(exArr.length == 7){
		if(exArr[6] == "*"){
			desc[6] = "每年";
		} else {
			desc[6] = exArr[6].replace(/,/g, "、") + unit[6];
		}
	} else {
		desc[6] = "每年";
	}
	// 处理周
	if(exArr[5] == "?"){
		desc[5] = "";
	} else {
		desc[5] = "每个" + unit[5] + exArr[5].replace(/,/g, "、");
	}
	// 处理月日时分秒
	for(var i = 4;i >= 0; i--){
		if(exArr[i] == "?"){
			desc[i] = "";
			continue;
		}
		if(exArr[i] == "*"){
			desc[i] = "每" + unit[i];
			continue;
		}
		if (exArr[i].indexOf("/") != -1){
			var dateArr = exArr[i].split("/");
			desc[i] = "，从" + dateArr[0] + unit[i] + "开始每隔" + dateArr[1] + intervalUnit[i];
			continue;
		}
		desc[i] = "的" + exArr[i].replace(/,/g, "、") + unit[i];
	}
	return desc[6] + desc[4] + desc[5] + desc[3] + desc[2] + desc[1] + desc[0] + "执行";
}