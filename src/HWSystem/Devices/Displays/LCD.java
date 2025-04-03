package HWSystem.Devices.Displays;

import HWSystem.Protocols.Protocol;
import HWSystem.State;

/**
 * Represents an LCD (Liquid Crystal Display) device.
 * This class extends the {@link Display} class and provides the implementation 
 * for turning the display on and off, printing data, and interacting with a protocol.
 */
public class LCD extends Display {

    /**
     * Constructs an LCD display with the specified communication protocol.
     * The device is initially set to the OFF state.
     * 
     * @param protocol the communication protocol used by the LCD display
     */
    public LCD(Protocol protocol){
        super(protocol);
    }

    /**
     * Turns the LCD display ON, changes its state to ON, and writes a "turnON" command 
     * to the protocol to perform any necessary actions.
     */
    @Override
    public void turnON() {
        deviceState = State.ON;
        System.out.println(getName() + ": Turning ON.");
        protocol.write("turnON");
    }

    /**
     * Turns the LCD display OFF, changes its state to OFF, and writes a "turnOFF" command 
     * to the protocol to perform any necessary actions.
     */
    @Override
    public void turnOFF() {
        deviceState = State.OFF;
        System.out.println(getName() + ": Turning OFF.");
        protocol.write("turnOFF");
    }

    /**
     * Gets the name of the device, which is "LCD".
     * 
     * @return the name of the device as a string, which is "LCD"
     */
    @Override
    public String getName() {
        return "LCD";
    }

    /**
     * Prints the specified data on the LCD display. This method writes the data to the protocol 
     * and outputs the action to the console.
     * 
     * @param data the data to be printed on the LCD display
     */
    @Override
    public void printData(String data) {
        protocol.write(data);
        System.out.println(getName() + ": Printing \"" + data + "\"");
    }
}
