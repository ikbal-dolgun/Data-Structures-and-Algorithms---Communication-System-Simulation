package HWSystem.Devices;

import HWSystem.State;
import HWSystem.Protocols.Protocol;

/**
 * Abstract class representing a generic device that uses a communication protocol.
 * A device can be turned on or off, and it has a state (ON/OFF).
 * It also holds a protocol that governs its communication with other devices.
 */
public abstract class Device {
    protected Protocol protocol;
    protected State deviceState;

    /**
     * Constructs a device with a specified protocol.
     * The device is initially set to the OFF state.
     * 
     * @param protocol the communication protocol used by the device
     */
    public Device(Protocol protocol){
        this.protocol = protocol;
        this.deviceState = State.OFF;
    }

    /**
     * Turns the device on. The actual implementation is defined in subclasses.
     */
    public abstract void turnON();

    /**
     * Turns the device off. The actual implementation is defined in subclasses.
     */
    public abstract void turnOFF();

    /**
     * Gets the name of the device.
     * The actual implementation is defined in subclasses.
     * 
     * @return the name of the device as a string
     */
    public abstract String getName();

    /**
     * Gets the type of the device.
     * The actual implementation is defined in subclasses.
     * 
     * @return the type of the device as a string
     */
    public abstract String getDevType();

    /**
     * Gets the current state of the device (ON or OFF).
     * 
     * @return the current state of the device
     */
    public State getState() {
        return deviceState;
    }

    /**
     * Gets the protocol associated with the device.
     * 
     * @return the protocol used by the device
     */
    public Protocol getProtocol(){
        return protocol;
    }
}
