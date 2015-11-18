/*******************************************************************************
 * @(#)enzyme_html.js 2011-7-6
 *
 * Copyright 2011 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/

/**
 * 此js库的主要目的是提供html支持
 * @author <a href="mailto:liujingwei@neusoft.com">Dreams Liu </a>
 * @version $Revision 1.1 $ 2011-7-6 下午02:44:22
 */
Enzyme.HTML = {
	createSelect : function(options) {
		var empty = {};
		var defaults = {
			target : {},
			startStep : 0,
			maxSize : 10,
			getText : function(index) {
				return index;
			},
			getValue : function(index) {
				return index;
			}
		};
		var settings = jQuery.extend(empty, defaults, options);
		var buf = "";
		for ( var i = settings.startStep; i < settings.startStep
				+ settings.maxSize; i++) {
			buf += "<option value='" + settings.getValue(i) + "'>"
					+ settings.getText(i) + "</option>";
		} // end for i
		if (!jQuery.isEmptyObject(settings.target)) {
			jQuery(settings.target).append(buf);
		}
	},
	createCheckBoxGroup : function(options) {
		var empty = {};
		var defaults = {
			target : {},
			startStep : 0,
			maxSize : 10,
			rowSize : 10,
			checkBoxName : 'checkBoxName',
			getText : function(index) {
				return index;
			},
			getValue : function(index) {
				return index;
			}
		};
		var settings = jQuery.extend(empty, defaults, options);

		var startStep = settings.startStep;
		var target = settings.target;
		var maxSize = settings.maxSize;
		var rowSize = settings.rowSize;

		var jt = jQuery(target);
		jQuery('#' + settings.checkBoxName + 'Table', jt).remove();
		var step = 1;

		var buf = "";
		for ( var i = startStep; i < maxSize + startStep; i++) {
			if (i == startStep) {
				buf = "<table id='" + settings.checkBoxName + "Table'><tr>";
			}

			buf += "<td>";
			buf += "<input type='checkbox' name='" + settings.checkBoxName
					+ "' value='" + settings.getValue(i) + "'>"
					+ settings.getText(i) + "</input>";
			buf += "</td>";

			if (step % rowSize == 0 && i != startStep
					&& i != (maxSize + startStep) - 1) {
				buf += "</tr><tr>";
			}

			if (i == (maxSize + startStep) - 1) {
				buf += "</tr></table>";
			}
			step++;
		} // end for i
		jt.append(buf);
	},

	getIframeElementValue : function(swin, name) {
		var value = "";
		var element = swin.document.getElementById(name);
		if (!element) {
			value = jQuery(element).val();
		} else {
			element = swin.document.getElementsByName(name);
			if (element && element[0]) {
				value = jQuery(element[0]).val();
			}
		}// end else if
		return value;
	},

	setIframeElementValue : function(twin, name, value) {
		try {
			var element = twin.document.getElementById(name);
			if (!element) {
				jQuery(element).val(value);
			} else {
				element = twin.document.getElementsByName(name);
				jQuery(element).val(value);
			}// end else if
		} catch (e) {

		}// end try
	}
}
// end object [Enzyme.HTML]


