package HWSystem.Devices.WirelessIOs;

import HWSystem.Devices.Device;
import HWSystem.Protocols.Protocol;

public abstract class WirelessIO extends Device{
    public WirelessIO(Protocol protocol){
        super(protocol);
    }
    
    protected String deviceData;
    public String getDevType() {
        return "WirelessIO";
    }

    public abstract void setData(String data);

    public abstract String recvData();
}
