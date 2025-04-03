package HWSystem.Protocols;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

/**
 * Represents an SPI (Serial Peripheral Interface) communication protocol for a device.
 * Implements the {@link Protocol} interface to provide the necessary 
 * operations to read data, write data, close the protocol with logging, 
 * and retrieve the protocol's name.
 */
public class SPI implements Protocol {
    private Stack<String> logs = new Stack<>();
    private int portID;

    /**
     * Constructs an SPI protocol object for a specified port ID.
     * 
     * @param ID the port ID associated with the SPI protocol
     */
    public SPI(int ID) {
        portID = ID;
    }

    /**
     * Reads data from the SPI protocol.
     * Logs the action and returns a message indicating that data is being read.
     * 
     * @return a string indicating the protocol's read action
     */
    public String read() {
        String log = "Reading";
        this.logs.push(log);
        return getProtocolName() + ": Reading.";
    }

    /**
     * Writes data to the SPI protocol.
     * Logs the action and the data being written.
     * 
     * @param data the data to be written to the protocol
     */
    public void write(String data) {
        String log = String.format("Writing \"%s\"", data);
        this.logs.push(log);
    }

    /**
     * Closes the SPI protocol and logs the actions to a specified directory.
     * The logs are written to a file with the protocol's name and port ID.
     * 
     * @param logDirectory the directory where the protocol logs will be stored
     */
    public void close(String logDirectory) {
        // Create a file for the protocol (use the protocol name and port ID)
        String fileName = logDirectory + File.separator + getProtocolName() + "_" + portID + ".log";
        File logFile = new File(fileName);

        try (FileWriter writer = new FileWriter(logFile)) {  // Open the file for writing
            // Write the logs in reverse order from the stack
            while (!logs.isEmpty()) {
                writer.write(logs.pop() + "\n");  // Write each log and move to the next one
            }

            // Write "Port Opened" at the end
            writer.write("Port Opened.\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the name of the protocol.
     * 
     * @return the name of the protocol as a string ("SPI")
     */
    public String getProtocolName() {
        return "SPI";
    }
}
