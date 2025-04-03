package HWSystem.Devices.Sensors;

import HWSystem.Protocols.Protocol;

/**
 * Abstract class representing a temperature sensor device.
 * This class extends the {@link Sensor} class and provides functionality common to all temperature sensors.
 * Specific temperature sensors should extend this class and implement the required methods.
 */
public abstract class TempSensor extends Sensor {

    /**
     * Constructs a TempSensor device with the specified communication protocol.
     * The device is initially set to the OFF state.
     *
     * @param protocol the communication protocol used by the temperature sensor device
     */
    public TempSensor(Protocol protocol) {
        super(protocol);
    }

    /**
     * Returns the type of the sensor as "TempSensor".
     * This method overrides the {@link Sensor#getSensType()} method to specify that this is a temperature sensor.
     *
     * @return a string representing the sensor type, which is always "TempSensor"
     */
    @Override
    public String getSensType() {
        return "TempSensor";
    }

    /**
     * Converts the temperature sensor data to a string representation.
     * This method should be implemented by subclasses to return the sensor's data in a suitable string format.
     * 
     * @return a string representation of the temperature sensor data
     */
    public abstract String data2String();
}
