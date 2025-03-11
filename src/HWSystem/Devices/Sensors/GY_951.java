package HWSystem.Devices.Sensors;

import HWSystem.Protocols.Protocol;
import HWSystem.State;

public class GY_951 extends IMUSensor{
    public GY_951(Protocol protocol){
        super(protocol);
    }

    public void turnON() {
        deviceState = State.ON;
        System.out.println(getName() + " Turning ON.");
        protocol.write("TurnON");
    }
    public void turnOFF() {
        deviceState = State.OFF;
        System.out.println(getName() + " Turning OFF.");
        protocol.write("TurnOFF");
    }
    public String getName() {
        return "GY-951";
    }

    public String data2String() {
        return getName() + " measured Accel:" + String.format("%.2f", getAccel()) + ", Rot: " + String.format("%.2f", getRot());
    }

    public Float getAccel() {
        float temp = (float) Math.random() * 10.0f;
        return temp;
    }

    public Float getRot() {
        float temp = (float) Math.random() * 10.0f;
        return temp;
    }
}
