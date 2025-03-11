package HWSystem.Devices.Sensors;

import HWSystem.Protocols.Protocol;

public abstract class IMUSensor extends Sensor{
    public IMUSensor (Protocol protocol){
        super(protocol);
    }

    public String getSensType() {
        return "IMUSensor";
    }

    
    public abstract String data2String();
    
    public abstract Float getAccel();

    public abstract Float getRot();
}
