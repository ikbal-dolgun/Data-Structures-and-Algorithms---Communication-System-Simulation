package HWSystem.Devices.Sensors;

import HWSystem.Protocols.Protocol;

public abstract class TempSensor extends Sensor{
    public TempSensor (Protocol protocol){
        super(protocol);
    }

    public String getSensType() {
        return "TempSensor";
    }

    
    public abstract String data2String();

    public abstract Float getTemp();
}
