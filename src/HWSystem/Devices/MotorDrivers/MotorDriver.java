package HWSystem.Devices.MotorDrivers;

import HWSystem.Devices.Device;
import HWSystem.Protocols.Protocol;

public abstract class MotorDriver extends Device{
    public MotorDriver(Protocol protocol){
        super(protocol);
    }
    public String getDevType() {
        return "MotorDriver";
    }

    public abstract void setMotorSpeed (int speed);
}
        