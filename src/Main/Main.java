package Main;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import HWSystem.HWSystem;
import HWSystem.Protocols.Protocol;
import HWSystem.Protocols.I2C;
import HWSystem.Protocols.OneWire;
import HWSystem.Protocols.SPI;
import HWSystem.Protocols.UART;

public class Main {
    public static void main(String[] args) {
        ArrayList<Protocol> ports = new ArrayList<>();
        int maxSensors = 0, maxDisplays = 0, maxWirelessAdapters = 0, maxMotorDrivers = 0;

        // Read from config.txt
        try {
            Scanner scanner = new Scanner(new File("config.txt"));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                if (line.startsWith("Port Configuration:")) {
                    // Read and split protocols
                    String[] protocols = line.split(": ")[1].split(",");
                    for (String protocol : protocols) {
                        switch (protocol.trim()) { // Trim to remove spaces
                            case "I2C": ports.add(new I2C()); break;
                            case "SPI": ports.add(new SPI()); break;
                            case "OneWire": ports.add(new OneWire()); break;
                            case "UART": ports.add(new UART()); break;
                        }
                    }
                } else if (line.startsWith("# of sensors:")) {
                    maxSensors = Integer.parseInt(line.split(":")[1].trim());
                } else if (line.startsWith("# of displays:")) {
                    maxDisplays = Integer.parseInt(line.split(":")[1].trim());
                } else if (line.startsWith("# of wireless adapters:")) {
                    maxWirelessAdapters = Integer.parseInt(line.split(":")[1].trim());
                } else if (line.startsWith("# of motor drivers:")) {
                    maxMotorDrivers = Integer.parseInt(line.split(":")[1].trim());
                }
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("Error reading configuration file: " + e.getMessage());
            return;
        }

        // 🔴 Check if ports were populated before creating HWSystem
        if (ports.isEmpty()) {
            System.out.println("Error: No ports were loaded. Check config.txt.");
            return;
        }

        // Create the HWSystem with the loaded configuration
        HWSystem system = new HWSystem(ports, maxSensors, maxDisplays, maxWirelessAdapters, maxMotorDrivers);
        system.run();
    }
}
