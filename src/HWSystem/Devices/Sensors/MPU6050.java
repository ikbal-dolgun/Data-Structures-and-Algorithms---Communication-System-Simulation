package HWSystem.Devices.Sensors;

import HWSystem.Protocols.Protocol;
import HWSystem.State;

/**
 * Represents the MPU6050 IMU sensor device.
 * This class extends the {@link IMUSensor} class and implements the required methods 
 * for a specific IMU sensor, the MPU6050.
 */
public class MPU6050 extends IMUSensor {

    /**
     * Constructs an MPU6050 IMU sensor device with the specified communication protocol.
     * The device is initially set to the OFF state.
     *
     * @param protocol the communication protocol used by the MPU6050 IMU sensor device
     */
    public MPU6050(Protocol protocol) {
        super(protocol);
    }

    /**
     * Turns on the MPU6050 sensor, changing its state to ON.
     * This method overrides the {@link IMUSensor#turnON()} method to implement device-specific functionality.
     */
    @Override
    public void turnON() {
        deviceState = State.ON;
        System.out.println(getName() + ": Turning ON.");
        protocol.write("turnON");
    }

    /**
     * Turns off the MPU6050 sensor, changing its state to OFF.
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
     * In this case, it always returns "MPU6050" as the name of the sensor.
     *
     * @return the name of the sensor, "MPU6050"
     */
    @Override
    public String getName() {
        return "MPU6050";
    }

    /**
     * Converts the sensor's data into a string format.
     * This method provides a simple string representation of the MPU6050 sensor's data, such as acceleration and rotation.
     * 
     * @return a string representing the sensor's data, such as acceleration and rotation
     */
    @Override
    public String data2String() {
        protocol.read();
        return getName() + " " + getDevType() + ": Acceleration: 1.00m/s^2, Rotation: 0.50Hz.";
    }
}
