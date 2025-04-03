package HWSystem.Devices.Sensors;

import HWSystem.Protocols.Protocol;
import HWSystem.State;

/**
 * Represents the GY-951 IMU sensor device.
 * This class extends the {@link IMUSensor} class and implements the required methods 
 * for a specific IMU sensor, the GY-951.
 */
public class GY_951 extends IMUSensor {

    /**
     * Constructs a GY-951 IMU sensor device with the specified communication protocol.
     * The device is initially set to the OFF state.
     *
     * @param protocol the communication protocol used by the GY-951 IMU sensor device
     */
    public GY_951(Protocol protocol) {
        super(protocol);
    }

    /**
     * Turns on the GY-951 sensor, changing its state to ON.
     * This method overrides the {@link IMUSensor#turnON()} method to implement device-specific functionality.
     */
    @Override
    public void turnON() {
        deviceState = State.ON;
        System.out.println(getName() + ": Turning ON.");
        protocol.write("turnON");
    }

    /**
     * Turns off the GY-951 sensor, changing its state to OFF.
     * This method overrides the {@link IMUSensor#turnOFF()} method to implement device-specific functionality.
     */
    @Override
    public void turnOFF() {
        deviceState = State.OFF;
        System.out.println(getName() + ": Turning OFF.");
        protocol.write("turnOFF");
    }

    /**
     * Returns the name of the sensor device.
     * In this case, it always returns "GY-951" as the name of the sensor.
     *
     * @return the name of the sensor, "GY-951"
     */
    @Override
    public String getName() {
        return "GY-951";
    }

    /**
     * Converts the sensor's data into a string format.
     * This method provides a simple string representation of the GY-951 sensor's data, such as acceleration and rotation.
     * 
     * @return a string representing the sensor's data, such as acceleration and rotation
     */
    @Override
    public String data2String() {
        protocol.read();
        return getName() + " " + getDevType() + ": Acceleration: 1.00m/s^2, Rotation: 0.50Hz.";
    }
}
