
package 
{

import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSCondition;
import mx.styles.CSSSelector;
import mx.styles.CSSStyleDeclaration;
import mx.styles.IStyleManager2;
import mx.utils.ObjectUtil;
import mx.skins.halo.ButtonSkin;
import mx.core.UITextField;
import mx.skins.halo.ListDropIndicator;
import mx.skins.halo.ScrollArrowSkin;
import mx.skins.halo.ToolTipBorder;
import mx.skins.halo.ComboBoxArrowSkin;
import mx.skins.halo.ApplicationBackground;
import mx.skins.halo.ScrollTrackSkin;
import mx.skins.halo.HaloBorder;
import mx.skins.halo.DefaultDragImage;
import mx.skins.halo.CheckBoxIcon;
import mx.skins.halo.BrokenImageBorderSkin;
import mx.skins.halo.AccordionHeaderSkin;
import mx.skins.halo.ButtonBarButtonSkin;
import mx.skins.halo.ScrollThumbSkin;
import mx.skins.halo.HaloFocusRect;
import mx.skins.halo.PanelSkin;
import mx.skins.halo.BusyCursor;
import mx.skins.halo.TitleBackground;
[ExcludeClass]

public class _WrapperAgent_Styles
{
    [Embed(_resolvedSource='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/libs/framework.swc$Assets.swf', symbol='mx.skins.cursor.HBoxDivider', source='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/libs/framework.swc$Assets.swf', original='Assets.swf', _line='700', _pathsep='true', _file='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/themes/Halo/halo.swc$defaults.css')]
    private static var _embed_css_Assets_swf_1746260725_mx_skins_cursor_HBoxDivider_1897914428:Class;
    [Embed(_resolvedSource='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/libs/framework.swc$Assets.swf', symbol='mx.skins.cursor.VBoxDivider', source='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/libs/framework.swc$Assets.swf', original='Assets.swf', _line='702', _pathsep='true', _file='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/themes/Halo/halo.swc$defaults.css')]
    private static var _embed_css_Assets_swf_1746260725_mx_skins_cursor_VBoxDivider_597821946:Class;
    [Embed(_resolvedSource='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/libs/framework.swc$Assets.swf', symbol='CloseButtonDown', source='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/libs/framework.swc$Assets.swf', original='Assets.swf', _line='1604', _pathsep='true', _file='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/themes/Halo/halo.swc$defaults.css')]
    private static var _embed_css_Assets_swf_1746260725_CloseButtonDown_1191931247:Class;
    [Embed(_resolvedSource='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/libs/framework.swc$Assets.swf', symbol='mx.skins.cursor.BusyCursor', source='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/libs/framework.swc$Assets.swf', original='Assets.swf', _line='571', _pathsep='true', _file='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/themes/Halo/halo.swc$defaults.css')]
    private static var _embed_css_Assets_swf_1746260725_mx_skins_cursor_BusyCursor_11959375:Class;
    [Embed(_resolvedSource='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/libs/framework.swc$Assets.swf', symbol='__brokenImage', source='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/libs/framework.swc$Assets.swf', original='Assets.swf', _line='1442', _pathsep='true', _file='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/themes/Halo/halo.swc$defaults.css')]
    private static var _embed_css_Assets_swf_1746260725___brokenImage_1134102215:Class;
    [Embed(_resolvedSource='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/libs/framework.swc$Assets.swf', symbol='mx.skins.cursor.DragMove', source='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/libs/framework.swc$Assets.swf', original='Assets.swf', _line='717', _pathsep='true', _file='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/themes/Halo/halo.swc$defaults.css')]
    private static var _embed_css_Assets_swf_1746260725_mx_skins_cursor_DragMove_330426389:Class;
    [Embed(_resolvedSource='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/libs/framework.swc$Assets.swf', symbol='CloseButtonOver', source='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/libs/framework.swc$Assets.swf', original='Assets.swf', _line='1605', _pathsep='true', _file='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/themes/Halo/halo.swc$defaults.css')]
    private static var _embed_css_Assets_swf_1746260725_CloseButtonOver_1027761281:Class;
    [Embed(_resolvedSource='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/libs/framework.swc$Assets.swf', symbol='TreeNodeIcon', source='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/libs/framework.swc$Assets.swf', original='Assets.swf', _line='1662', _pathsep='true', _file='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/themes/Halo/halo.swc$defaults.css')]
    private static var _embed_css_Assets_swf_1746260725_TreeNodeIcon_1008563420:Class;
    [Embed(_resolvedSource='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/libs/framework.swc$Assets.swf', symbol='TreeFolderClosed', source='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/libs/framework.swc$Assets.swf', original='Assets.swf', _line='1665', _pathsep='true', _file='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/themes/Halo/halo.swc$defaults.css')]
    private static var _embed_css_Assets_swf_1746260725_TreeFolderClosed_960217531:Class;
    [Embed(_resolvedSource='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/libs/framework.swc$Assets.swf', symbol='mx.skins.cursor.DragReject', source='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/libs/framework.swc$Assets.swf', original='Assets.swf', _line='718', _pathsep='true', _file='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/themes/Halo/halo.swc$defaults.css')]
    private static var _embed_css_Assets_swf_1746260725_mx_skins_cursor_DragReject_1157113725:Class;
    [Embed(_resolvedSource='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/libs/framework.swc$Assets.swf', symbol='TreeDisclosureOpen', source='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/libs/framework.swc$Assets.swf', original='Assets.swf', _line='1664', _pathsep='true', _file='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/themes/Halo/halo.swc$defaults.css')]
    private static var _embed_css_Assets_swf_1746260725_TreeDisclosureOpen_2064120152:Class;
    [Embed(_resolvedSource='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/libs/framework.swc$Assets.swf', symbol='mx.containers.FormItem.Required', source='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/libs/framework.swc$Assets.swf', original='Assets.swf', _line='776', _pathsep='true', _file='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/themes/Halo/halo.swc$defaults.css')]
    private static var _embed_css_Assets_swf_1746260725_mx_containers_FormItem_Required_2053182368:Class;
    [Embed(_resolvedSource='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/libs/framework.swc$Assets.swf', symbol='mx.skins.BoxDividerSkin', source='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/libs/framework.swc$Assets.swf', original='Assets.swf', _line='698', _pathsep='true', _file='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/themes/Halo/halo.swc$defaults.css')]
    private static var _embed_css_Assets_swf_1746260725_mx_skins_BoxDividerSkin_1053332441:Class;
    [Embed(_resolvedSource='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/libs/framework.swc$Assets.swf', symbol='mx.skins.cursor.DragCopy', source='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/libs/framework.swc$Assets.swf', original='Assets.swf', _line='714', _pathsep='true', _file='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/themes/Halo/halo.swc$defaults.css')]
    private static var _embed_css_Assets_swf_1746260725_mx_skins_cursor_DragCopy_330138809:Class;
    [Embed(_resolvedSource='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/libs/framework.swc$Assets.swf', symbol='TreeDisclosureClosed', source='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/libs/framework.swc$Assets.swf', original='Assets.swf', _line='1663', _pathsep='true', _file='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/themes/Halo/halo.swc$defaults.css')]
    private static var _embed_css_Assets_swf_1746260725_TreeDisclosureClosed_1648797174:Class;
    [Embed(_resolvedSource='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/libs/framework.swc$Assets.swf', symbol='mx.skins.cursor.DragLink', source='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/libs/framework.swc$Assets.swf', original='Assets.swf', _line='716', _pathsep='true', _file='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/themes/Halo/halo.swc$defaults.css')]
    private static var _embed_css_Assets_swf_1746260725_mx_skins_cursor_DragLink_330400814:Class;
    [Embed(_resolvedSource='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/libs/framework.swc$Assets.swf', symbol='CloseButtonUp', source='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/libs/framework.swc$Assets.swf', original='Assets.swf', _line='1606', _pathsep='true', _file='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/themes/Halo/halo.swc$defaults.css')]
    private static var _embed_css_Assets_swf_1746260725_CloseButtonUp_45123336:Class;
    [Embed(_resolvedSource='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/libs/framework.swc$Assets.swf', symbol='CloseButtonDisabled', source='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/libs/framework.swc$Assets.swf', original='Assets.swf', _line='1603', _pathsep='true', _file='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/themes/Halo/halo.swc$defaults.css')]
    private static var _embed_css_Assets_swf_1746260725_CloseButtonDisabled_1401910183:Class;
    [Embed(_resolvedSource='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/libs/framework.swc$Assets.swf', symbol='TreeFolderOpen', source='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/libs/framework.swc$Assets.swf', original='Assets.swf', _line='1666', _pathsep='true', _file='C:/Program Files/Adobe/Adobe Flash Builder 4.6/sdks/4.6.0/frameworks/themes/Halo/halo.swc$defaults.css')]
    private static var _embed_css_Assets_swf_1746260725_TreeFolderOpen_1625149015:Class;
    public static function init(fbs:IFlexModuleFactory):void
    {
        var styleManager:IStyleManager2 = fbs.getImplementation("mx.styles::IStyleManager2") as IStyleManager2;
        

        var conditions:Array = null;
        var condition:CSSCondition = null;
        var selector:CSSSelector = null;
        var style:CSSStyleDeclaration;
        var effects:Array;
        
        var mergedStyle:CSSStyleDeclaration;

        
        //
        // mx.containers.Accordion
        //
        selector = null;
        conditions = null;
        conditions = null;
        selector = new CSSSelector("mx.containers.Accordion", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration("mx.containers.Accordion");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.borderStyle = "solid";
                this.paddingTop = -1;
                this.backgroundColor = 0xffffff;
                this.borderSkin = mx.skins.halo.HaloBorder;
                this.verticalGap = -1;
                this.paddingLeft = -1;
                this.paddingBottom = -1;
                this.paddingRight = -1;
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }



        
        //
        // mx.containers.accordionClasses.AccordionHeader
        //
        selector = null;
        conditions = null;
        conditions = null;
        selector = new CSSSelector("mx.containers.accordionClasses.AccordionHeader", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration("mx.containers.accordionClasses.AccordionHeader");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.downSkin = null;
                this.overSkin = null;
                this.paddingTop = 0;
                this.selectedDisabledSkin = null;
                this.selectedUpSkin = null;
                this.fontSize = "10";
                this.skin = mx.skins.halo.AccordionHeaderSkin;
                this.paddingLeft = 5;
                this.paddingRight = 5;
                this.upSkin = null;
                this.fontWeight = "bold";
                this.selectedDownSkin = null;
                this.textAlign = "left";
                this.disabledSkin = null;
                this.horizontalGap = 2;
                this.paddingBottom = 0;
                this.selectedOverSkin = null;
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }



        
        //
        // mx.controls.Alert
        //
        selector = null;
        conditions = null;
        conditions = null;
        selector = new CSSSelector("mx.controls.Alert", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration("mx.controls.Alert");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.borderColor = 0x869ca7;
                this.paddingTop = 2;
                this.roundedBottomCorners = true;
                this.color = 0xffffff;
                this.buttonStyleName = "alertButtonStyle";
                this.backgroundColor = 0x869ca7;
                this.borderAlpha = 0.9;
                this.backgroundAlpha = 0.9;
                this.paddingLeft = 10;
                this.paddingBottom = 2;
                this.paddingRight = 10;
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }



        
        //
        // mx.core.Application
        //
        selector = null;
        conditions = null;
        conditions = null;
        selector = new CSSSelector("mx.core.Application", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration("mx.core.Application");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.backgroundSize = "100%";
                this.paddingTop = 24;
                this.backgroundColor = 0x869ca7;
                this.backgroundImage = mx.skins.halo.ApplicationBackground;
                this.backgroundGradientAlphas = [1, 1];
                this.horizontalAlign = "center";
                this.paddingLeft = 24;
                this.paddingBottom = 24;
                this.paddingRight = 24;
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }



        
        //
        // mx.controls.Button
        //
        selector = null;
        conditions = null;
        conditions = null;
        selector = new CSSSelector("mx.controls.Button", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration("mx.controls.Button");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.fontWeight = "bold";
                this.paddingTop = 2;
                this.cornerRadius = 4;
                this.textAlign = "center";
                this.labelVerticalOffset = 0;
                this.emphasizedSkin = null;
                this.verticalGap = 2;
                this.horizontalGap = 2;
                this.paddingBottom = 2;
                this.skin = mx.skins.halo.ButtonSkin;
                this.paddingLeft = 10;
                this.paddingRight = 10;
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }



        
        //
        // mx.controls.ButtonBar
        //
        selector = null;
        conditions = null;
        conditions = null;
        selector = new CSSSelector("mx.controls.ButtonBar", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration("mx.controls.ButtonBar");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.lastButtonStyleName = "";
                this.firstButtonStyleName = "";
                this.textAlign = "center";
                this.horizontalAlign = "center";
                this.verticalAlign = "middle";
                this.verticalGap = 0;
                this.horizontalGap = 0;
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }



        
        //
        // mx.controls.buttonBarClasses.ButtonBarButton.buttonBarFirstButtonStyle
        //
        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "buttonBarFirstButtonStyle");
        conditions.push(condition); 
        selector = new CSSSelector("mx.controls.buttonBarClasses.ButtonBarButton", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration("mx.controls.buttonBarClasses.ButtonBarButton.buttonBarFirstButtonStyle");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.skin = null;
                this.focusRoundedCorners = "tl bl";
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }

        //
        // mx.controls.buttonBarClasses.ButtonBarButton.buttonBarLastButtonStyle
        //
        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "buttonBarLastButtonStyle");
        conditions.push(condition); 
        selector = new CSSSelector("mx.controls.buttonBarClasses.ButtonBarButton", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration("mx.controls.buttonBarClasses.ButtonBarButton.buttonBarLastButtonStyle");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.skin = null;
                this.focusRoundedCorners = "tr br";
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }

        //
        // mx.controls.buttonBarClasses.ButtonBarButton
        //
        selector = null;
        conditions = null;
        conditions = null;
        selector = new CSSSelector("mx.controls.buttonBarClasses.ButtonBarButton", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration("mx.controls.buttonBarClasses.ButtonBarButton");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.upSkin = null;
                this.selectedDownSkin = null;
                this.overSkin = null;
                this.downSkin = null;
                this.selectedDisabledSkin = null;
                this.selectedUpSkin = null;
                this.disabledSkin = null;
                this.horizontalGap = 1;
                this.skin = mx.skins.halo.ButtonBarButtonSkin;
                this.selectedOverSkin = null;
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }



        
        //
        // mx.controls.CheckBox
        //
        selector = null;
        conditions = null;
        conditions = null;
        selector = new CSSSelector("mx.controls.CheckBox", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration("mx.controls.CheckBox");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.downSkin = null;
                this.iconColor = 0x2b333c;
                this.selectedDownIcon = null;
                this.selectedUpSkin = null;
                this.overIcon = null;
                this.skin = null;
                this.upSkin = null;
                this.selectedDownSkin = null;
                this.selectedOverIcon = null;
                this.selectedDisabledIcon = null;
                this.textAlign = "left";
                this.horizontalGap = 5;
                this.paddingBottom = 2;
                this.downIcon = null;
                this.icon = mx.skins.halo.CheckBoxIcon;
                this.overSkin = null;
                this.disabledIcon = null;
                this.paddingTop = 2;
                this.selectedDisabledSkin = null;
                this.upIcon = null;
                this.paddingLeft = 0;
                this.paddingRight = 0;
                this.fontWeight = "normal";
                this.selectedUpIcon = null;
                this.labelVerticalOffset = 0;
                this.disabledSkin = null;
                this.selectedOverSkin = null;
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }



        
        //
        // mx.controls.ComboBase
        //
        selector = null;
        conditions = null;
        conditions = null;
        selector = new CSSSelector("mx.controls.ComboBase", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration("mx.controls.ComboBase");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.borderSkin = mx.skins.halo.HaloBorder;
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }



        
        //
        // mx.controls.ComboBox
        //
        selector = null;
        conditions = null;
        conditions = null;
        selector = new CSSSelector("mx.controls.ComboBox", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration("mx.controls.ComboBox");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.fontWeight = "bold";
                this.disabledIconColor = 0x919999;
                this.paddingTop = 0;
                this.dropdownStyleName = "comboDropdown";
                this.leading = 0;
                this.arrowButtonWidth = 22;
                this.cornerRadius = 5;
                this.editableSkin = null;
                this.skin = mx.skins.halo.ComboBoxArrowSkin;
                this.paddingLeft = 5;
                this.paddingBottom = 0;
                this.paddingRight = 5;
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }



        
        //
        // mx.controls.List.comboDropdown
        //
        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "comboDropdown");
        conditions.push(condition); 
        selector = new CSSSelector("mx.controls.List", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration("mx.controls.List.comboDropdown");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.fontWeight = "normal";
                this.leading = 0;
                this.paddingLeft = 5;
                this.paddingRight = 5;
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }



        
        //
        // mx.core.Container
        //
        selector = null;
        conditions = null;
        conditions = null;
        selector = new CSSSelector("mx.core.Container", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration("mx.core.Container");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.borderStyle = "none";
                this.borderSkin = mx.skins.halo.HaloBorder;
                this.cornerRadius = 0;
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }



        
        //
        // mx.containers.ControlBar
        //
        selector = null;
        conditions = null;
        conditions = null;
        selector = new CSSSelector("mx.containers.ControlBar", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration("mx.containers.ControlBar");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.disabledOverlayAlpha = 0;
                this.borderStyle = "controlBar";
                this.paddingTop = 10;
                this.verticalAlign = "middle";
                this.paddingLeft = 10;
                this.paddingBottom = 10;
                this.paddingRight = 10;
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }



        
        //
        // .dateFieldPopup
        //
        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "dateFieldPopup");
        conditions.push(condition); 
        selector = new CSSSelector("", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration(".dateFieldPopup");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.dropShadowEnabled = true;
                this.backgroundColor = 0xffffff;
                this.dropShadowVisible = true;
                this.borderThickness = 0;
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }

        //
        // .errorTip
        //
        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "errorTip");
        conditions.push(condition); 
        selector = new CSSSelector("", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration(".errorTip");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.fontWeight = "bold";
                this.borderStyle = "errorTipRight";
                this.paddingTop = 4;
                this.borderColor = 0xce2929;
                this.color = 0xffffff;
                this.fontSize = 9;
                this.shadowColor = 0x000000;
                this.paddingLeft = 4;
                this.paddingBottom = 4;
                this.paddingRight = 4;
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }

        //
        // .headerDragProxyStyle
        //
        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "headerDragProxyStyle");
        conditions.push(condition); 
        selector = new CSSSelector("", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration(".headerDragProxyStyle");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.fontWeight = "bold";
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }

        //
        // .swatchPanelTextField
        //
        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "swatchPanelTextField");
        conditions.push(condition); 
        selector = new CSSSelector("", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration(".swatchPanelTextField");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.borderStyle = "inset";
                this.borderColor = 0xd5dddd;
                this.highlightColor = 0xc4cccc;
                this.backgroundColor = 0xffffff;
                this.shadowCapColor = 0xd5dddd;
                this.shadowColor = 0xd5dddd;
                this.paddingLeft = 5;
                this.buttonColor = 0x6f7777;
                this.borderCapColor = 0x919999;
                this.paddingRight = 5;
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }

        //
        // .todayStyle
        //
        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "todayStyle");
        conditions.push(condition); 
        selector = new CSSSelector("", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration(".todayStyle");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.color = 0xffffff;
                this.textAlign = "center";
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }

        //
        // .weekDayStyle
        //
        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "weekDayStyle");
        conditions.push(condition); 
        selector = new CSSSelector("", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration(".weekDayStyle");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.fontWeight = "bold";
                this.textAlign = "center";
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }

        //
        // .windowStatus
        //
        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "windowStatus");
        conditions.push(condition); 
        selector = new CSSSelector("", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration(".windowStatus");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.color = 0x666666;
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }

        //
        // .windowStyles
        //
        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "windowStyles");
        conditions.push(condition); 
        selector = new CSSSelector("", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration(".windowStyles");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.fontWeight = "bold";
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }

        //
        // global
        //
        selector = null;
        conditions = null;
        conditions = null;
        selector = new CSSSelector("global", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration("global");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.lineHeight = "120%";
                this.unfocusedTextSelectionColor = 0xe8e8e8;
                this.kerning = false;
                this.caretColor = 0x0167ff;
                this.iconColor = 0x111111;
                this.verticalScrollPolicy = "auto";
                this.horizontalAlign = "left";
                this.filled = true;
                this.showErrorTip = true;
                this.textDecoration = "none";
                this.columnCount = "auto";
                this.liveDragging = true;
                this.dominantBaseline = "roman";
                this.fontThickness = 0;
                this.focusBlendMode = "normal";
                this.blockProgression = "tb";
                this.buttonColor = 0x6f7777;
                this.indentation = 17;
                this.autoThumbVisibility = true;
                this.textAlignLast = "start";
                this.paddingTop = 0;
                this.textAlpha = 1.0;
                this.chromeColor = 0xcccccc;
                this.rollOverColor = 0xb2e1ff;
                this.bevel = true;
                this.fontSize = 10;
                this.shadowColor = 0xeeeeee;
                this.columnGap = 0;
                this.paddingLeft = 0;
                this.paragraphEndIndent = 0;
                this.fontWeight = "normal";
                this.indicatorGap = 14;
                this.focusSkin = mx.skins.halo.HaloFocusRect;
                this.dropShadowEnabled = false;
                this.breakOpportunity = "auto";
                this.leading = 2;
                this.symbolColor = 0x000000;
                this.renderingMode = "cff";
                this.iconPlacement = "left";
                this.borderThickness = 1;
                this.paragraphStartIndent = 0;
                this.layoutDirection = "ltr";
                this.contentBackgroundColor = 0xffffff;
                this.backgroundSize = "auto";
                this.paragraphSpaceAfter = 0;
                this.borderColor = 0xb7babc;
                this.shadowDistance = 2;
                this.stroked = false;
                this.digitWidth = "default";
                this.verticalAlign = "top";
                this.ligatureLevel = "common";
                this.firstBaselineOffset = "ascent";
                this.fillAlphas = [0.6, 0.4, 0.75, 0.65];
                this.version = "4.0.0";
                this.shadowDirection = "center";
                this.fontLookup = "auto";
                this.lineBreak = "toFit";
                this.repeatInterval = 35;
                this.openDuration = 250;
                this.paragraphSpaceBefore = 0;
                this.fontFamily = "Verdana";
                this.paddingBottom = 0;
                this.strokeWidth = 1;
                this.lineThrough = false;
                this.textFieldClass = mx.core.UITextField;
                this.alignmentBaseline = "useDominantBaseline";
                this.trackingLeft = 0;
                this.verticalGridLines = true;
                this.fontStyle = "normal";
                this.dropShadowColor = 0x000000;
                this.accentColor = 0x0099ff;
                this.backgroundImageFillMode = "scale";
                this.selectionColor = 0x7fceff;
                this.borderWeight = 1;
                this.focusRoundedCorners = "tl tr bl br";
                this.paddingRight = 0;
                this.borderSides = "left top right bottom";
                this.disabledIconColor = 0x999999;
                this.textJustify = "interWord";
                this.focusColor = 0x009dff;
                this.borderVisible = true;
                this.selectionDuration = 250;
                this.typographicCase = "default";
                this.highlightAlphas = [0.3, 0];
                this.unfocusedSelectionColor = 0xe8e8e8;
                this.fillColor = 0xffffff;
                this.showErrorSkin = true;
                this.textRollOverColor = 0x2b333c;
                this.rollOverOpenDelay = 200;
                this.digitCase = "default";
                this.shadowCapColor = 0xd5dddd;
                this.inactiveTextSelectionColor = 0xe8e8e8;
                this.backgroundAlpha = 1.0;
                this.justificationRule = "space";
                this.roundedBottomCorners = true;
                this.dropShadowVisible = false;
                this.trackingRight = 0;
                this.fillColors = [0xffffff, 0xcccccc, 0xffffff, 0xeeeeee];
                this.horizontalGap = 8;
                this.borderCapColor = 0x919999;
                this.leadingModel = "auto";
                this.selectionDisabledColor = 0xdddddd;
                this.closeDuration = 250;
                this.embedFonts = false;
                this.letterSpacing = 0;
                this.focusAlpha = 0.4;
                this.borderAlpha = 1.0;
                this.baselineShift = 0;
                this.focusedTextSelectionColor = 0xa8c6ee;
                this.fontSharpness = 0;
                this.modalTransparencyDuration = 100;
                this.justificationStyle = "pushInKinsoku";
                this.contentBackgroundAlpha = 1;
                this.borderStyle = "inset";
                this.textRotation = "auto";
                this.fontAntiAliasType = "advanced";
                this.errorColor = 0xff0000;
                this.direction = "ltr";
                this.cffHinting = "horizontalStem";
                this.horizontalGridLineColor = 0xf7f7f7;
                this.locale = "en";
                this.cornerRadius = 0;
                this.modalTransparencyColor = 0xdddddd;
                this.disabledAlpha = 0.5;
                this.textIndent = 0;
                this.verticalGridLineColor = 0xd5dddd;
                this.themeColor = 0x009dff;
                this.tabStops = null;
                this.modalTransparency = 0.5;
                this.smoothScrolling = true;
                this.columnWidth = "auto";
                this.textAlign = "left";
                this.horizontalScrollPolicy = "auto";
                this.textSelectedColor = 0x2b333c;
                this.interactionMode = "mouse";
                this.whiteSpaceCollapse = "collapse";
                this.fontGridFitType = "pixel";
                this.horizontalGridLines = false;
                this.fullScreenHideControlsDelay = 3000;
                this.useRollOver = true;
                this.repeatDelay = 500;
                this.focusThickness = 2;
                this.verticalGap = 6;
                this.disabledColor = 0xaab3b3;
                this.inactiveSelectionColor = 0xe8e8e8;
                this.modalTransparencyBlur = 3;
                this.slideDuration = 300;
                this.color = 0x0b333c;
                this.fixedThumbSize = false;
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }

        //
        // .activeButtonStyle
        //
        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "activeButtonStyle");
        conditions.push(condition); 
        selector = new CSSSelector("", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration(".activeButtonStyle");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }

        //
        // .activeTabStyle
        //
        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "activeTabStyle");
        conditions.push(condition); 
        selector = new CSSSelector("", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration(".activeTabStyle");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.fontWeight = "bold";
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }

        //
        // .alertButtonStyle
        //
        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "alertButtonStyle");
        conditions.push(condition); 
        selector = new CSSSelector("", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration(".alertButtonStyle");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.color = 0x0b333c;
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }

        //
        // .comboDropdown
        //
        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "comboDropdown");
        conditions.push(condition); 
        selector = new CSSSelector("", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration(".comboDropdown");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.shadowDirection = "center";
                this.fontWeight = "normal";
                this.dropShadowEnabled = true;
                this.leading = 0;
                this.backgroundColor = 0xffffff;
                this.shadowDistance = 1;
                this.cornerRadius = 0;
                this.borderThickness = 0;
                this.paddingLeft = 5;
                this.paddingRight = 5;
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }

        //
        // .dataGridStyles
        //
        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "dataGridStyles");
        conditions.push(condition); 
        selector = new CSSSelector("", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration(".dataGridStyles");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.fontWeight = "bold";
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }

        //
        // .headerDateText
        //
        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "headerDateText");
        conditions.push(condition); 
        selector = new CSSSelector("", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration(".headerDateText");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.fontWeight = "bold";
                this.textAlign = "center";
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }

        //
        // .linkButtonStyle
        //
        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "linkButtonStyle");
        conditions.push(condition); 
        selector = new CSSSelector("", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration(".linkButtonStyle");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.paddingTop = 2;
                this.paddingLeft = 2;
                this.paddingBottom = 2;
                this.paddingRight = 2;
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }

        //
        // .opaquePanel
        //
        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "opaquePanel");
        conditions.push(condition); 
        selector = new CSSSelector("", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration(".opaquePanel");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.borderColor = 0xffffff;
                this.backgroundColor = 0xffffff;
                this.headerColors = [0xe7e7e7, 0xd9d9d9];
                this.footerColors = [0xe7e7e7, 0xc7c7c7];
                this.borderAlpha = 1;
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }

        //
        // .plain
        //
        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "plain");
        conditions.push(condition); 
        selector = new CSSSelector("", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration(".plain");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.paddingTop = 0;
                this.backgroundColor = 0xffffff;
                this.backgroundImage = "";
                this.horizontalAlign = "left";
                this.paddingLeft = 0;
                this.paddingBottom = 0;
                this.paddingRight = 0;
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }

        //
        // .popUpMenu
        //
        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "popUpMenu");
        conditions.push(condition); 
        selector = new CSSSelector("", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration(".popUpMenu");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.fontWeight = "normal";
                this.textAlign = "left";
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }

        //
        // .richTextEditorTextAreaStyle
        //
        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "richTextEditorTextAreaStyle");
        conditions.push(condition); 
        selector = new CSSSelector("", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration(".richTextEditorTextAreaStyle");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }

        //
        // .textAreaVScrollBarStyle
        //
        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "textAreaVScrollBarStyle");
        conditions.push(condition); 
        selector = new CSSSelector("", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration(".textAreaVScrollBarStyle");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }

        //
        // .textAreaHScrollBarStyle
        //
        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "textAreaHScrollBarStyle");
        conditions.push(condition); 
        selector = new CSSSelector("", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration(".textAreaHScrollBarStyle");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }



        
        //
        // mx.containers.DividedBox
        //
        selector = null;
        conditions = null;
        conditions = null;
        selector = new CSSSelector("mx.containers.DividedBox", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration("mx.containers.DividedBox");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.dividerThickness = 3;
                this.dividerColor = 0x6f7777;
                this.dividerAffordance = 6;
                this.verticalDividerCursor = _embed_css_Assets_swf_1746260725_mx_skins_cursor_VBoxDivider_597821946;
                this.dividerSkin = _embed_css_Assets_swf_1746260725_mx_skins_BoxDividerSkin_1053332441;
                this.horizontalDividerCursor = _embed_css_Assets_swf_1746260725_mx_skins_cursor_HBoxDivider_1897914428;
                this.dividerAlpha = 0.75;
                this.verticalGap = 10;
                this.horizontalGap = 10;
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }



        
        //
        // mx.managers.DragManager
        //
        selector = null;
        conditions = null;
        conditions = null;
        selector = new CSSSelector("mx.managers.DragManager", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration("mx.managers.DragManager");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.copyCursor = _embed_css_Assets_swf_1746260725_mx_skins_cursor_DragCopy_330138809;
                this.moveCursor = _embed_css_Assets_swf_1746260725_mx_skins_cursor_DragMove_330426389;
                this.rejectCursor = _embed_css_Assets_swf_1746260725_mx_skins_cursor_DragReject_1157113725;
                this.linkCursor = _embed_css_Assets_swf_1746260725_mx_skins_cursor_DragLink_330400814;
                this.defaultDragImageSkin = mx.skins.halo.DefaultDragImage;
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }



        
        //
        // mx.containers.Form
        //
        selector = null;
        conditions = null;
        conditions = null;
        selector = new CSSSelector("mx.containers.Form", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration("mx.containers.Form");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.paddingTop = 16;
                this.verticalGap = 6;
                this.paddingLeft = 16;
                this.paddingBottom = 16;
                this.paddingRight = 16;
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }



        
        //
        // mx.containers.FormItem
        //
        selector = null;
        conditions = null;
        conditions = null;
        selector = new CSSSelector("mx.containers.FormItem", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration("mx.containers.FormItem");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.indicatorSkin = _embed_css_Assets_swf_1746260725_mx_containers_FormItem_Required_2053182368;
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }



        
        //
        // mx.controls.FormItemLabel
        //
        selector = null;
        conditions = null;
        conditions = null;
        selector = new CSSSelector("mx.controls.FormItemLabel", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration("mx.controls.FormItemLabel");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.textAlign = "right";
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }



        
        //
        // mx.containers.HDividedBox
        //
        selector = null;
        conditions = null;
        conditions = null;
        selector = new CSSSelector("mx.containers.HDividedBox", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration("mx.containers.HDividedBox");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.dividerThickness = 3;
                this.dividerColor = 0x6f7777;
                this.dividerAffordance = 6;
                this.verticalDividerCursor = _embed_css_Assets_swf_1746260725_mx_skins_cursor_VBoxDivider_597821946;
                this.dividerSkin = _embed_css_Assets_swf_1746260725_mx_skins_BoxDividerSkin_1053332441;
                this.horizontalDividerCursor = _embed_css_Assets_swf_1746260725_mx_skins_cursor_HBoxDivider_1897914428;
                this.dividerAlpha = 0.75;
                this.verticalGap = 10;
                this.horizontalGap = 10;
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }



        
        //
        // mx.controls.Image
        //
        selector = null;
        conditions = null;
        conditions = null;
        selector = new CSSSelector("mx.controls.Image", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration("mx.controls.Image");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.layoutDirection = "ltr";
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }



        
        //
        // mx.controls.listClasses.ListBase
        //
        selector = null;
        conditions = null;
        conditions = null;
        selector = new CSSSelector("mx.controls.listClasses.ListBase", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration("mx.controls.listClasses.ListBase");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.borderStyle = "solid";
                this.paddingTop = 2;
                this.backgroundColor = 0xffffff;
                this.backgroundDisabledColor = 0xdddddd;
                this.dropIndicatorSkin = mx.skins.halo.ListDropIndicator;
                this._creationPolicy = "auto";
                this.paddingLeft = 2;
                this.paddingBottom = 2;
                this.paddingRight = 0;
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }



        
        //
        // mx.controls.NavBar
        //
        selector = null;
        conditions = null;
        conditions = null;
        selector = new CSSSelector("mx.controls.NavBar", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration("mx.controls.NavBar");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.borderSkin = mx.skins.halo.HaloBorder;
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }



        
        //
        // mx.containers.Panel
        //
        selector = null;
        conditions = null;
        conditions = null;
        selector = new CSSSelector("mx.containers.Panel", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration("mx.containers.Panel");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.statusStyleName = "windowStatus";
                this.borderStyle = "default";
                this.borderColor = 0xe2e2e2;
                this.paddingTop = 0;
                this.backgroundColor = 0xffffff;
                this.cornerRadius = 4;
                this.titleBackgroundSkin = mx.skins.halo.TitleBackground;
                this.borderAlpha = 0.4;
                this.borderThicknessTop = 2;
                this.paddingLeft = 0;
                this.paddingRight = 0;
                this.resizeEndEffect = "Dissolve";
                this.titleStyleName = "windowStyles";
                this.roundedBottomCorners = false;
                this.dropShadowEnabled = true;
                this.borderThicknessRight = 10;
                this.resizeStartEffect = "Dissolve";
                this.borderSkin = mx.skins.halo.PanelSkin;
                this.borderThickness = 0;
                this.borderThicknessLeft = 10;
                this.paddingBottom = 0;
            };
        }

        effects = style.mx_internal::effects;
        if (!effects)
        {
            effects = style.mx_internal::effects = [];
        }


          effects.push("resizeEndEffect");
          effects.push("resizeStartEffect");
          effects.push("resizeEndEffect");
          effects.push("resizeStartEffect");

        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }



        
        //
        // mx.controls.TextArea.richTextEditorTextAreaStyle
        //
        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "richTextEditorTextAreaStyle");
        conditions.push(condition); 
        selector = new CSSSelector("mx.controls.TextArea", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration("mx.controls.TextArea.richTextEditorTextAreaStyle");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }

        //
        // mx.controls.TextArea
        //
        selector = null;
        conditions = null;
        conditions = null;
        selector = new CSSSelector("mx.controls.TextArea", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration("mx.controls.TextArea");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.borderStyle = "solid";
                this.backgroundColor = 0xffffff;
                this.verticalScrollBarStyleName = "textAreaVScrollBarStyle";
                this.horizontalScrollBarStyleName = "textAreaHScrollBarStyle";
                this.backgroundDisabledColor = 0xdddddd;
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }



        
        //
        // mx.controls.scrollClasses.ScrollBar
        //
        selector = null;
        conditions = null;
        conditions = null;
        selector = new CSSSelector("mx.controls.scrollClasses.ScrollBar", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration("mx.controls.scrollClasses.ScrollBar");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.borderColor = 0xb7babc;
                this.thumbOffset = 0;
                this.paddingTop = 0;
                this.trackColors = [0x94999b, 0xe7e7e7];
                this.trackSkin = mx.skins.halo.ScrollTrackSkin;
                this.downArrowSkin = mx.skins.halo.ScrollArrowSkin;
                this.cornerRadius = 4;
                this.upArrowSkin = mx.skins.halo.ScrollArrowSkin;
                this.paddingLeft = 0;
                this.paddingBottom = 0;
                this.thumbSkin = mx.skins.halo.ScrollThumbSkin;
                this.paddingRight = 0;
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }



        
        //
        // mx.core.ScrollControlBase
        //
        selector = null;
        conditions = null;
        conditions = null;
        selector = new CSSSelector("mx.core.ScrollControlBase", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration("mx.core.ScrollControlBase");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.borderSkin = mx.skins.halo.HaloBorder;
                this.focusRoundedCorners = "tl tr bl br";
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }



        
        //
        // mx.controls.SWFLoader
        //
        selector = null;
        conditions = null;
        conditions = null;
        selector = new CSSSelector("mx.controls.SWFLoader", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration("mx.controls.SWFLoader");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.brokenImageSkin = _embed_css_Assets_swf_1746260725___brokenImage_1134102215;
                this.brokenImageBorderSkin = mx.skins.halo.BrokenImageBorderSkin;
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }



        
        //
        // mx.controls.HScrollBar.textAreaVScrollBarStyle
        //
        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "textAreaVScrollBarStyle");
        conditions.push(condition); 
        selector = new CSSSelector("mx.controls.HScrollBar", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration("mx.controls.HScrollBar.textAreaVScrollBarStyle");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }



        
        //
        // mx.controls.VScrollBar.textAreaHScrollBarStyle
        //
        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "textAreaHScrollBarStyle");
        conditions.push(condition); 
        selector = new CSSSelector("mx.controls.VScrollBar", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration("mx.controls.VScrollBar.textAreaHScrollBarStyle");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }



        
        //
        // mx.controls.TextInput
        //
        selector = null;
        conditions = null;
        conditions = null;
        selector = new CSSSelector("mx.controls.TextInput", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration("mx.controls.TextInput");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.paddingTop = 0;
                this.backgroundColor = 0xffffff;
                this.borderSkin = mx.skins.halo.HaloBorder;
                this.backgroundDisabledColor = 0xdddddd;
                this.paddingLeft = 0;
                this.paddingRight = 0;
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }



        
        //
        // mx.containers.TitleWindow
        //
        selector = null;
        conditions = null;
        conditions = null;
        selector = new CSSSelector("mx.containers.TitleWindow", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration("mx.containers.TitleWindow");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.closeButtonDisabledSkin = _embed_css_Assets_swf_1746260725_CloseButtonDisabled_1401910183;
                this.paddingTop = 4;
                this.dropShadowEnabled = true;
                this.backgroundColor = 0xffffff;
                this.closeButtonOverSkin = _embed_css_Assets_swf_1746260725_CloseButtonOver_1027761281;
                this.closeButtonUpSkin = _embed_css_Assets_swf_1746260725_CloseButtonUp_45123336;
                this.closeButtonDownSkin = _embed_css_Assets_swf_1746260725_CloseButtonDown_1191931247;
                this.cornerRadius = 8;
                this.paddingLeft = 4;
                this.paddingBottom = 4;
                this.paddingRight = 4;
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }



        
        //
        // mx.controls.Tree
        //
        selector = null;
        conditions = null;
        conditions = null;
        selector = new CSSSelector("mx.controls.Tree", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration("mx.controls.Tree");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.disclosureOpenIcon = _embed_css_Assets_swf_1746260725_TreeDisclosureOpen_2064120152;
                this.folderClosedIcon = _embed_css_Assets_swf_1746260725_TreeFolderClosed_960217531;
                this.folderOpenIcon = _embed_css_Assets_swf_1746260725_TreeFolderOpen_1625149015;
                this.disclosureClosedIcon = _embed_css_Assets_swf_1746260725_TreeDisclosureClosed_1648797174;
                this.verticalAlign = "middle";
                this.defaultLeafIcon = _embed_css_Assets_swf_1746260725_TreeNodeIcon_1008563420;
                this.paddingLeft = 2;
                this.paddingRight = 0;
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }



        
        //
        // mx.managers.CursorManager
        //
        selector = null;
        conditions = null;
        conditions = null;
        selector = new CSSSelector("mx.managers.CursorManager", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration("mx.managers.CursorManager");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.busyCursor = mx.skins.halo.BusyCursor;
                this.busyCursorBackground = _embed_css_Assets_swf_1746260725_mx_skins_cursor_BusyCursor_11959375;
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }



        
        //
        // mx.controls.ToolTip
        //
        selector = null;
        conditions = null;
        conditions = null;
        selector = new CSSSelector("mx.controls.ToolTip", conditions, selector);
        mergedStyle = styleManager.getMergedStyleDeclaration("mx.controls.ToolTip");
        style = new CSSStyleDeclaration(selector, styleManager, mergedStyle == null);

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.borderStyle = "toolTip";
                this.paddingTop = 2;
                this.borderColor = 0x919999;
                this.backgroundColor = 0xffffcc;
                this.borderSkin = mx.skins.halo.ToolTipBorder;
                this.cornerRadius = 2;
                this.fontSize = 9;
                this.paddingLeft = 4;
                this.paddingBottom = 2;
                this.backgroundAlpha = 0.95;
                this.paddingRight = 4;
            };
        }



        if (mergedStyle != null && 
            (mergedStyle.defaultFactory == null ||
            ObjectUtil.compare(new style.defaultFactory(), new mergedStyle.defaultFactory())))
        {
            styleManager.setStyleDeclaration(style.mx_internal::selectorString, style, false);
        }


    }
}

}
