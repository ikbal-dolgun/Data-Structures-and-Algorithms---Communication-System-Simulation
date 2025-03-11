package HWSystem.Devices.Displays;

import HWSystem.Protocols.Protocol;
import HWSystem.State;

public class LCD extends Display{
    public LCD(Protocol protocol){
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
        return "LCD";
    }

    public void printData(String data) {
        protocol.write(getName() + " printing: " + data);      
    }
}
