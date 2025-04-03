package HWSystem.Devices.Sensors;

import HWSystem.Protocols.Protocol;
import HWSystem.State;

/**
 * Represents the BME280 temperature, humidity, and pressure sensor device.
 * This class extends the {@link TempSensor} class and implements the required methods 
 * for a specific temperature sensor, the BME280.
 */
public class BME280 extends TempSensor {

    /**
     * Constructs a BME280 temperature sensor device with the specified communication protocol.
     * The device is initially set to the OFF state.
     *
     * @param protocol the communication protocol used by the BME280 temperature sensor device
     */
    public BME280(Protocol protocol) {
        super(protocol);
    }

    /**
     * Turns on the BME280 sensor, changing its state to ON.
     * This method overrides the {@link TempSensor#turnON()} method to implement device-specific functionality.
     */
    @Override
    public void turnON() {
        deviceState = State.ON;
        System.out.println(getName() + ": Turning ON.");
        protocol.write("turnON");
    }

    /**
     * Turns off the BME280 sensor, changing its state to OFF.
     * This method overrides the {@link TempSensor#turnOFF()} method to implement device-specific functionality.
     */
    @Override
    public void turnOFF() {
        deviceState = State.OFF;
        System.out.println(getName() + ": Turning OFF.");
        protocol.write("turnOFF");
    }

    /**
     * Returns the name of the sensor device.
     * In this case, it always returns "BME280" as the name of the sensor.
     *
     * @return the name of the sensor, "BME280"
     */
    @Override
    public String getName() {
        return "BME280";
    }

    /**
     * Converts the sensor's data into a string format.
     * This method provides a simple string representation of the BME280 sensor's temperature data.
     * 
     * @return a string representing the sensor's temperature data, such as the current temperature
     */
    @Override
    public String data2String() {
        protocol.read();
        return getName() + " " + getDevType() + ": Temperature: 24.00°C.";
    }
}
