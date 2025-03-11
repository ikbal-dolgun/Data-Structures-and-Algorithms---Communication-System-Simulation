package HWSystem.Devices.Sensors;

import HWSystem.Protocols.Protocol;
import HWSystem.State;

public class BME280 extends TempSensor{
    public BME280(Protocol protocol){
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
        return "BME280";
    }

    public String data2String() {
        return getName() + " measured: " + String.format("%.2f", getTemp()) + "C.";
    }

    public Float getTemp() {
        float temp = (float) Math.random() * 10.0f;
        return temp;
    }
}
