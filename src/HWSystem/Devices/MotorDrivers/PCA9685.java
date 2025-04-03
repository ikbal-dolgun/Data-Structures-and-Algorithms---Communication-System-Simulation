package HWSystem.Devices.MotorDrivers;

import HWSystem.Protocols.Protocol;
import HWSystem.State;

/**
 * Represents the PCA9685 motor driver, which is a specific implementation of a motor driver.
 * This class extends the {@link MotorDriver} class and provides functionality to control a motor 
 * using a specified communication protocol.
 */
public class PCA9685 extends MotorDriver {

    /**
     * Constructs a PCA9685 Motor Driver with the specified communication protocol.
     * The motor driver is initially set to the OFF state.
     * 
     * @param protocol the communication protocol used by the motor driver
     */
    public PCA9685(Protocol protocol){
        super(protocol);
    }

    /**
     * Turns the motor driver ON and sends the corresponding command to the protocol.
     */
    @Override
    public void turnON() {
        deviceState = State.ON;
        System.out.println(getName() + ": Turning ON.");
        protocol.write("turnON");
    }

    /**
     * Turns the motor driver OFF and sends the corresponding command to the protocol.
     */
    @Override
    public void turnOFF() {
        deviceState = State.OFF;
        System.out.println(getName() + ": Turning OFF.");
        protocol.write("turnOFF");
    }

    /**
     * Gets the name of the motor driver.
     * 
     * @return the name of the motor driver as a string, which is "PCA9685"
     */
    @Override
    public String getName() {
        return "PCA9685";
    }

    /**
     * Sets the speed of the motor and sends the speed value to the protocol.
     * 
     * @param speed the speed of the motor to be set
     */
    @Override
    public void setMotorSpeed(int speed) {
        System.out.println(getName() + " Setting speed to: " + speed);
        protocol.write(String.format("%d", speed));
    }
}
