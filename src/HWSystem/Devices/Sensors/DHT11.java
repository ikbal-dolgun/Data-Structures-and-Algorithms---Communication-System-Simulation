package HWSystem.Devices.Sensors;

import HWSystem.Protocols.Protocol;
import HWSystem.State;

/**
 * Represents the DHT11 temperature and humidity sensor device.
 * This class extends the {@link TempSensor} class and implements the required methods 
 * for a specific temperature sensor, the DHT11.
 */
public class DHT11 extends TempSensor {

    /**
     * Constructs a DHT11 temperature sensor device with the specified communication protocol.
     * The device is initially set to the OFF state.
     *
     * @param protocol the communication protocol used by the DHT11 temperature sensor device
     */
    public DHT11(Protocol protocol) {
        super(protocol);
    }

    /**
     * Turns on the DHT11 sensor, changing its state to ON.
     * This method overrides the {@link TempSensor#turnON()} method to implement device-specific functionality.
     */
    @Override
    public void turnON() {
        deviceState = State.ON;
        System.out.println(getName() + ": Turning ON.");
        protocol.write("turnON");
    }

    /**
     * Turns off the DHT11 sensor, changing its state to OFF.
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
     * In this case, it always returns "DHT11" as the name of the sensor.
     *
     * @return the name of the sensor, "DHT11"
     */
    @Override
    public String getName() {
        return "DHT11";
    }

    /**
     * Converts the sensor's data into a string format.
     * This method provides a simple string representation of the DHT11 sensor's temperature data.
     * 
     * @return a string representing the sensor's temperature data, such as the current temperature
     */
    @Override
    public String data2String() {
        protocol.read();
        return getName() + " " + getDevType() + ": Temperature: 24.00°C.";
    }
}
