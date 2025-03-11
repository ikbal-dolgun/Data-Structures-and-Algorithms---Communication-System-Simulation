package HWSystem.Devices;

import HWSystem.State;
import HWSystem.Protocols.Protocol;

public abstract class Device {
    public Device(Protocol protocol){
        this.protocol = protocol;
        this.deviceState = State.OFF;
    }

    protected Protocol protocol;
    protected State deviceState;

    public abstract void turnON();
    public abstract void turnOFF();

    public abstract String getName();
    public abstract String getDevType();

    public State getState() {
        return deviceState;
    }

    public Protocol getProtocol(){
        return protocol;
    }
}
