package HWSystem.Devices.WirelessIOs;

import HWSystem.Devices.Device;
import HWSystem.Protocols.Protocol;

/**
 * Abstract class representing a Wireless Input/Output (WirelessIO) device.
 * This class extends the {@link Device} class and provides functionality for
 * setting and receiving data over a specified wireless communication protocol.
 */
public abstract class WirelessIO extends Device {

    /**
     * The data to be transmitted or received by the wireless I/O device.
     */
    protected String deviceData;

    /**
     * Constructs a WirelessIO device with the specified communication protocol.
     * The device is initially set to the OFF state.
     *
     * @param protocol the communication protocol used by the wireless I/O device
     */
    public WirelessIO(Protocol protocol) {
        super(protocol);
    }

    /**
     * Returns the type of device, which is "WirelessIO".
     *
     * @return the device type as a string, which is "WirelessIO"
     */
    @Override
    public String getDevType() {
        return "WirelessIO";
    }

    /**
     * Sets the data to be transmitted or sent by the wireless I/O device.
     * 
     * @param data the data to be set for transmission
     */
    public abstract void setData(String data);

    /**
     * Receives data from the wireless I/O device.
     * 
     * @return the received data as a string
     */
    public abstract String recvData();
}
