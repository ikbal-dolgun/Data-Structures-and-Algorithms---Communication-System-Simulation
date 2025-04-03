package Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import HWSystem.HWSystem;
import HWSystem.Protocols.Protocol;
import HWSystem.Protocols.I2C;
import HWSystem.Protocols.OneWire;
import HWSystem.Protocols.SPI;
import HWSystem.Protocols.UART;

/**
 * Main class to initialize the hardware system based on configuration file input.
 * It reads a configuration file that specifies the port configuration, 
 * the number of devices (sensors, displays, wireless adapters, and motor drivers), 
 * and initializes the hardware system accordingly.
 * 
 * <p>This class is responsible for processing the configuration file, setting up the 
 * hardware system, and executing commands on the system.</p>
 * 
 * <p>It also logs the configuration of the ports after processing the commands.</p>
 */
public class Main {

    /**
     * The entry point of the application. This method reads the configuration file,
     * initializes the hardware system with the specified configurations, 
     * and processes commands for the hardware system.
     *
     * @param args Command-line arguments. The first argument should be the path 
     *             to the configuration file and the second argument should be the 
     *             log directory for logging the port configuration.
     */
    public static void main(String[] args) {
        // Check if the configuration file is provided as an argument
        if (args.length == 0) {
            System.err.println("Error: Configuration file must be provided as an argument.");
            return;
        }

        String configFile = args[0];
        ArrayList<Protocol> ports = new ArrayList<>();
        int maxSensors = 0, maxDisplays = 0, maxWirelessAdapters = 0, maxMotorDrivers = 0;

        try {
            // Using Scanner to read the configuration file line by line
            Scanner scanner = new Scanner(new File(configFile));
            
            // Read through the file to process each line
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                
                // Process the port configuration line
                if (line.startsWith("Port Configuration:")) {
                    String[] portNames = line.substring("Port Configuration:".length()).split(",");
                    for (int i = 0; i < portNames.length; i++) {
                        String portName = portNames[i].trim();
                        // Add corresponding port to the ports list based on its type
                        switch (portName) {
                            case "I2C":
                                ports.add(new I2C(i));
                                break;
                            case "OneWire":
                                ports.add(new OneWire(i));
                                break;
                            case "SPI":
                                ports.add(new SPI(i));
                                break;
                            case "UART":
                                ports.add(new UART(i));
                                break;
                            default:
                                break;
                        }
                    }
                }
                
                // Process the number of devices for each category
                else if (line.startsWith("# of sensors:")) {
                    maxSensors = Integer.parseInt(line.substring("# of sensors:".length()).trim());
                } else if (line.startsWith("# of displays:")) {
                    maxDisplays = Integer.parseInt(line.substring("# of displays:".length()).trim());
                } else if (line.startsWith("# of wireless adapters:")) {
                    maxWirelessAdapters = Integer.parseInt(line.substring("# of wireless adapters:".length()).trim());
                } else if (line.startsWith("# of motor drivers:")) {
                    maxMotorDrivers = Integer.parseInt(line.substring("# of motor drivers:".length()).trim());
                }
            }
            
            // Close the scanner after reading the file
            scanner.close();
        } catch (FileNotFoundException e) {
            // Handle file not found exception
            System.err.println("Error reading the configuration file: " + e.getMessage());
            return;
        }

        // Create the hardware system object using the parsed configurations
        HWSystem system = new HWSystem(ports, maxSensors, maxDisplays, maxWirelessAdapters, maxMotorDrivers);
        // Get and execute commands for the hardware system
        system.getCommands();
        system.exeCommands();
        // Log the ports configuration to the specified log directory
        system.logPorts(args[1]);
    }
}