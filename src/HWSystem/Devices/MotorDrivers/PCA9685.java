package HWSystem.Devices.MotorDrivers;

import HWSystem.Protocols.Protocol;
import HWSystem.State;

public class PCA9685 extends MotorDriver {
    public PCA9685(Protocol protocol){
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
        return "PCA9685";
    }

    public void setMotorSpeed(int speed) {
        protocol.write(getName() + " Setting speed to: " + speed);
    }
}
