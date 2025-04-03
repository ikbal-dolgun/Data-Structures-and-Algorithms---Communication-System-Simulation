package HWSystem.Devices.WirelessIOs;

import HWSystem.Protocols.Protocol;
import HWSystem.State;

/**
 * Represents a WiFi wireless I/O device.
 * This class extends the {@link WirelessIO} class and provides functionality to send and receive data
 * using WiFi as a communication protocol.
 */
public class Wifi extends WirelessIO {

    /**
     * Constructs a WiFi wireless I/O device with the specified communication protocol.
     * The device is initially set to the OFF state.
     *
     * @param protocol the communication protocol used by the WiFi device
     */
    public Wifi(Protocol protocol) {
        super(protocol);
    }

    /**
     * Turns the WiFi device ON.
     * Sets the device state to ON and sends a "turnON" command via the protocol.
     */
    @Override
    public void turnON() {
        deviceState = State.ON;
        System.out.println(getName() + ": Turning ON.");
        protocol.write("turnON");
    }

    /**
     * Turns the WiFi device OFF.
     * Sets the device state to OFF and sends a "turnOFF" command via the protocol.
     */
    @Override
    public void turnOFF() {
        deviceState = State.OFF;
        System.out.println(getName() + ": Turning OFF.");
        protocol.write("turnOFF");
    }

    /**
     * Returns the name of the WiFi device.
     *
     * @return the name of the device as a string, which is "Wifi"
     */
    @Override
    public String getName() {
        return "Wifi";
    }

    /**
     * Receives data from the WiFi device.
     * This method simulates the process of receiving data and prints out the received data.
     * The data is then returned.
     *
     * @return the data received from the WiFi device
     */
    @Override
    public String recvData() {
        System.out.println(getName() + ": Received \"" + deviceData + "\"");
        protocol.read();
        return deviceData;
    }

    /**
     * Sets the data to be transmitted via WiFi.
     * This method stores the data and sends it using the protocol.
     * 
     * @param data the data to be transmitted by the WiFi device
     */
    @Override
    public void setData(String data) {
        this.deviceData = data;
        protocol.write(data);
        System.out.println(getName() + ": Sending \"" + data + "\"");
    }
}
