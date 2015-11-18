

import flash.events.Event;
import flash.events.EventDispatcher;
import flash.events.IEventDispatcher;
import mx.core.IPropertyChangeNotifier;
import mx.events.PropertyChangeEvent;
import mx.utils.ObjectProxy;
import mx.utils.UIDUtil;

import com.neusoft.mid.topology.canvas.RootCanvas;

class BindableProperty
{
	/*
	 * generated bindable wrapper for property rootCanvas (public)
	 * - generated setter
	 * - generated getter
	 * - original public var 'rootCanvas' moved to '_1925893926rootCanvas'
	 */

    [Bindable(event="propertyChange")]
    public function get rootCanvas():com.neusoft.mid.topology.canvas.RootCanvas
    {
        return this._1925893926rootCanvas;
    }

    public function set rootCanvas(value:com.neusoft.mid.topology.canvas.RootCanvas):void
    {
    	var oldValue:Object = this._1925893926rootCanvas;
        if (oldValue !== value)
        {
            this._1925893926rootCanvas = value;
           if (this.hasEventListener("propertyChange"))
               this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "rootCanvas", oldValue, value));
        }
    }



}
