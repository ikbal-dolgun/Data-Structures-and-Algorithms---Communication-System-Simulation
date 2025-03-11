package HWSystem.Devices.Sensors;

import HWSystem.Devices.Device;
import HWSystem.Protocols.Protocol;

public abstract class Sensor extends Device{
    public Sensor(Protocol protocol){
        super(protocol);
    }

    public String getDevType() {
        return getSensType() + " Sensor";
    }
    
    public abstract String getSensType();

    public abstract String data2String();
}
