package HWSystem.Devices.Displays;

import HWSystem.Devices.Device;
import HWSystem.Protocols.Protocol;

/**
 * Abstract class representing a display device. Inherits from the {@link Device} class.
 * A display device can print data and is associated with a communication protocol.
 */
public abstract class Display extends Device {

    /**
     * Constructs a display device with the specified communication protocol.
     * The device is initially set to the OFF state.
     * 
     * @param protocol the communication protocol used by the display device
     */
    public Display(Protocol protocol){
        super(protocol);
    }

    /**
     * Gets the type of the device. For this class, it always returns "Display".
     * 
     * @return the type of the device as a string, which is "Display"
     */
    @Override
    public String getDevType() {
        return "Display";
    }

    /**
     * Abstract method to print data on the display. The actual implementation
     * should be provided in subclasses, depending on the specific display technology.
     * 
     * @param data the data to be printed on the display
     */
    public abstract void printData(String data);
}
