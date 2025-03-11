package HWSystem.Devices.WirelessIOs;

import HWSystem.Protocols.Protocol;
import HWSystem.State;

public class Wifi extends WirelessIO{
    public Wifi(Protocol protocol){
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
        return "Wifi";
    }

    public String recvData() {
        protocol.write("Stored data in " + getName() + " is " + deviceData);
        return deviceData;
    }

    public void setData(String data) {
        deviceData = data;
        protocol.write("Setting " + getName() + " data to: " + data);
    }
}
