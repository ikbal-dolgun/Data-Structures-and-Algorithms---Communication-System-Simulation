package HWSystem.Devices.Displays;

import HWSystem.Devices.Device;
import HWSystem.Protocols.Protocol;

public abstract class Display extends Device{
    public Display(Protocol protocol){
        super(protocol);
    }

    public String getDevType() {
        return "Diplay";
    }

    public abstract void printData(String data);
}
