package HWSystem.Devices.Sensors;

import HWSystem.Devices.Device;
import HWSystem.Protocols.Protocol;

/**
 * Abstract class representing a sensor device.
 * This class extends the {@link Device} class and provides functionality common to all sensors.
 * Specific types of sensors should extend this class and implement the required methods.
 */
public abstract class Sensor extends Device {

    /**
     * Constructs a Sensor device with the specified communication protocol.
     * The device is initially set to the OFF state.
     *
     * @param protocol the communication protocol used by the sensor device
     */
    public Sensor(Protocol protocol) {
        super(protocol);
    }

    /**
     * Returns the type of the sensor device.
     * This method appends "Sensor" to the specific sensor type returned by the {@link #getSensType()} method.
     *
     * @return a string representing the full sensor type, e.g., "Temperature Sensor"
     */
    @Override
    public String getDevType() {
        return getSensType() + " Sensor";
    }

    /**
     * Returns the specific type of the sensor.
     * This method should be implemented by subclasses to return the type of sensor they represent.
     *
     * @return a string representing the sensor type, e.g., "Temperature", "Pressure"
     */
    public abstract String getSensType();

    /**
     * Converts the sensor data to a string representation.
     * This method should be implemented by subclasses to return the data in a format suitable for output or display.
     *
     * @return a string representation of the sensor data
     */
    public abstract String data2String();
}
