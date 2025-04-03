package HWSystem;

/**
 * Enum representing the state of a device.
 * <p>
 * The two possible states for a device are:
 * </p>
 * <ul>
 *     <li>{@link #ON} - The device is powered on and active.</li>
 *     <li>{@link #OFF} - The device is powered off and inactive.</li>
 * </ul>
 */
public enum State {
    /** The device is powered on and active. */
    ON,

    /** The device is powered off and inactive. */
    OFF;
}
