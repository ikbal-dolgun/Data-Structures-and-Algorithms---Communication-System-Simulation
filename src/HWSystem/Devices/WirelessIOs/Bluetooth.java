package HWSystem.Devices.WirelessIOs;

import HWSystem.Protocols.Protocol;
import HWSystem.State;

/**
 * Represents a Bluetooth wireless I/O device.
 * This class extends the {@link WirelessIO} class and provides functionality to send and receive data
 * using Bluetooth as a communication protocol.
 */
public class Bluetooth extends WirelessIO {

    /**
     * Constructs a Bluetooth wireless I/O device with the specified communication protocol.
     * The device is initially set to the OFF state.
     *
     * @param protocol the communication protocol used by the Bluetooth device
     */
    public Bluetooth(Protocol protocol) {
        super(protocol);
    }

    /**
     * Turns the Bluetooth device ON.
     * Sets the device state to ON and sends a "turnON" command via the protocol.
     */
    @Override
    public void turnON() {
        deviceState = State.ON;
        System.out.println(getName() + ": Turning ON.");
        protocol.write("turnON");
    }

    /**
     * Turns the Bluetooth device OFF.
     * Sets the device state to OFF and sends a "turnOFF" command via the protocol.
     */
    @Override
    public void turnOFF() {
        deviceState = State.OFF;
        System.out.println(getName() + ": Turning OFF.");
        protocol.write("turnOFF");
    }

    /**
     * Returns the name of the Bluetooth device.
     *
     * @return the name of the device as a string, which is "Bluetooth"
     */
    @Override
    public String getName() {
        return "Bluetooth";
    }

    /**
     * Receives data from the Bluetooth device.
     * This method simulates the process of receiving data and prints out the received data.
     * The data is then returned.
     *
     * @return the data received from the Bluetooth device
     */
    @Override
    public String recvData() {
        System.out.println(getName() + ": Received \"" + deviceData + "\"");
        protocol.read();
        return deviceData;
    }

    /**
     * Sets the data to be transmitted via Bluetooth.
     * This method stores the data and sends it using the protocol.
     * 
     * @param data the data to be transmitted by the Bluetooth device
     */
    @Override
    public void setData(String data) {
        this.deviceData = data;
        protocol.write(data);
        System.out.println(getName() + ": Sending \"" + data + "\"");
    }
}
