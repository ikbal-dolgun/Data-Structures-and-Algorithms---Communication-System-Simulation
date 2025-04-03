package HWSystem.Devices.Sensors;

import HWSystem.Protocols.Protocol;

/**
 * Abstract class representing an Inertial Measurement Unit (IMU) sensor device.
 * This class extends the {@link Sensor} class and provides functionality common to all IMU sensors.
 * Specific IMU sensors should extend this class and implement the required methods.
 */
public abstract class IMUSensor extends Sensor {

    /**
     * Constructs an IMUSensor device with the specified communication protocol.
     * The device is initially set to the OFF state.
     *
     * @param protocol the communication protocol used by the IMU sensor device
     */
    public IMUSensor(Protocol protocol) {
        super(protocol);
    }

    /**
     * Returns the type of the sensor as "IMUSensor".
     * This method overrides the {@link Sensor#getSensType()} method to specify that this is an IMU sensor.
     *
     * @return a string representing the sensor type, which is always "IMUSensor"
     */
    @Override
    public String getSensType() {
        return "IMUSensor";
    }

    /**
     * Converts the IMU sensor data to a string representation.
     * This method should be implemented by subclasses to return the sensor's data in a suitable string format.
     * 
     * @return a string representation of the IMU sensor data
     */
    public abstract String data2String();
}
