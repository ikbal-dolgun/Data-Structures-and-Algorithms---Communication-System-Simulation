package HWSystem.Devices.MotorDrivers;

import HWSystem.Devices.Device;
import HWSystem.Protocols.Protocol;

/**
 * Represents a motor driver device.
 * This class extends the {@link Device} class and provides the structure for motor driver devices, 
 * allowing interaction with a protocol and controlling the motor speed.
 */
public abstract class MotorDriver extends Device {

    /**
     * Constructs a motor driver with the specified communication protocol.
     * The device is initially set to the OFF state.
     * 
     * @param protocol the communication protocol used by the motor driver
     */
    public MotorDriver(Protocol protocol){
        super(protocol);
    }

    /**
     * Gets the type of device, which is "MotorDriver".
     * 
     * @return the type of device as a string, which is "MotorDriver"
     */
    @Override
    public String getDevType() {
        return "MotorDriver";
    }

    /**
     * Sets the speed of the motor. This method is abstract and should be implemented 
     * in subclasses to specify how the motor speed is controlled.
     * 
     * @param speed the speed of the motor to be set
     */
    public abstract void setMotorSpeed(int speed);
}
