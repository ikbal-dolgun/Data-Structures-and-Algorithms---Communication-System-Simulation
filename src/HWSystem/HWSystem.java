package HWSystem;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import HWSystem.Devices.Device;
import HWSystem.Devices.Displays.Display;
import HWSystem.Devices.Displays.LCD;
import HWSystem.Devices.Displays.OLED;
import HWSystem.Devices.MotorDrivers.MotorDriver;
import HWSystem.Devices.MotorDrivers.PCA9685;
import HWSystem.Devices.MotorDrivers.SparkFunMD;
import HWSystem.Devices.Sensors.GY_951;
import HWSystem.Devices.Sensors.BME280;
import HWSystem.Devices.Sensors.DHT11;
import HWSystem.Devices.Sensors.MPU6050;
import HWSystem.Devices.Sensors.Sensor;
import HWSystem.Devices.WirelessIOs.Bluetooth;
import HWSystem.Devices.WirelessIOs.Wifi;
import HWSystem.Devices.WirelessIOs.WirelessIO;
import HWSystem.Protocols.I2C;
import HWSystem.Protocols.OneWire;
import HWSystem.Protocols.Protocol;
import HWSystem.Protocols.SPI;
import HWSystem.Protocols.UART;

/**
 * Represents a hardware system that connects and manages various types of devices
 * using different protocols.
 * The system manages sensors, displays, wireless I/O devices, and motor drivers.
 */
public class HWSystem {
    private ArrayList<Protocol> ports;
    private ArrayList<Device> devices;

    private ArrayList<Display> displays;
    private ArrayList<MotorDriver> motorDrivers;
    private ArrayList<Sensor> sensors;
    private ArrayList<WirelessIO> wirelessIOs;

    private LinkedList<String> commandList = new LinkedList<>();

    /**
     * Constructs a HWSystem object.
     * 
     * @param ports the list of communication protocols to be used by the devices
     * @param maxSensors the maximum number of sensors allowed in the system
     * @param maxDisplays the maximum number of displays allowed in the system
     * @param maxWirelessAdapters the maximum number of wireless adapters allowed in the system
     * @param maxMotorDrivers the maximum number of motor drivers allowed in the system
     */
    public HWSystem(ArrayList<Protocol> ports, int maxSensors, int maxDisplays, int maxWirelessAdapters, int maxMotorDrivers) {
        this.ports = ports;
    
        // Initialize device lists
        this.devices = new ArrayList<>(ports.size());
        this.sensors = new ArrayList<>(maxSensors);
        this.displays = new ArrayList<>(maxDisplays);
        this.wirelessIOs = new ArrayList<>(maxWirelessAdapters);
        this.motorDrivers = new ArrayList<>(maxMotorDrivers);
    
        // Fill `devices` list with `null` (matching port size)
        for (int i = 0; i < ports.size(); i++) {
            this.devices.add(null);
        }

        while (this.sensors.size() < maxSensors) sensors.add(null);
        while (this.displays.size() < maxDisplays) displays.add(null);
        while (this.wirelessIOs.size() < maxWirelessAdapters) wirelessIOs.add(null);
        while (this.motorDrivers.size() < maxMotorDrivers) motorDrivers.add(null);
    }
    
    /**
     * Turns on the device located at the specified port ID.
     * 
     * @param portID the port ID of the device to be turned on
     * @return true if the device is successfully turned on, false otherwise
     */
    public Boolean turnOnDevice(int portID) {
        if (devices.get(portID) == null){
            System.err.println("Port is empty. Turn ON command can not be executed.");
            return false;
        }
        if (devices.get(portID).getState() == State.ON){
            System.err.println("Device is already active. Turn ON command can not be executed.");
            return false;
        }
        devices.get(portID).turnON();
        return true;
    }

    /**
     * Turns off the device located at the specified port ID.
     * 
     * @param portID the port ID of the device to be turned off
     * @return true if the device is successfully turned off, false otherwise
     */
    public Boolean turnOffDevice(int portID) {
        if (devices.get(portID) == null){
            System.err.println("Port is empty. Turn OFF command can not be executed.");
            return false;
        }
        if (devices.get(portID).getState() == State.OFF){
            System.err.println("Device is already off. Turn OFF command can not be executed.");
            return false;
        }
        devices.get(portID).turnOFF();
        return true;
    }

    /**
     * Adds a device to the system at a specified port with a specific device ID.
     * 
     * @param devName the name of the device to be added
     * @param portID the port ID where the device will be connected
     * @param devID the device ID to uniquely identify the device
     * @return true if the device is successfully added, false otherwise
     */
    public Boolean addDev(String devName, int portID, int devID) {
        // Check if portID is within valid range
        if (portID >= devices.size()) {
            System.err.println("Invalid port ID.");
            return false;
        }

        // Check if devID is within valid range for the respective list
        switch (devName) {
            case "DHT11":
            case "BME280":
            case "MPU6050":
            case "GY951":
                if (devID >= sensors.size()) {
                    System.err.println("Invalid device ID for Sensor.");
                    return false;
                }
                if (sensors.get(devID) != null) {
                    System.err.println("Sensor ID " + devID + " is already occupied.");
                    return false;
                }
                break;
            case "LCD":
            case "OLED":
                if (devID >= displays.size()) {
                    System.err.println("Invalid device ID for Display.");
                    return false;
                }
                if (displays.get(devID) != null) {
                    System.err.println("Display ID " + devID + " is already occupied.");
                    return false;
                }
                break;
            case "Bluetooth":
            case "Wifi":
                if (devID >= wirelessIOs.size()) {
                    System.err.println("Invalid device ID for Wireless Adapters.");
                    return false;
                }
                if (wirelessIOs.get(devID) != null) {
                    System.err.println("Wireless IO ID " + devID + " is already occupied.");
                    return false;
                }
                break;
            case "PCA9685":
            case "SparkFunMD":
                if (devID >= motorDrivers.size()) {
                    System.err.println("Invalid device ID for Motor Drivers.");
                    return false;
                }
                if (motorDrivers.get(devID) != null) {
                    System.err.println("Motor Driver ID " + devID + " is already occupied.");
                    return false;
                }
                break;
            default:
                System.err.println("Unknown device type.");
                return false;
        }

        //if port is occupied
        if (devices.get(portID) != null) {
            System.err.println("Port is already occupied.");
            return false;
        }

        Protocol protocol = ports.get(portID);

        // Ensure protocol is valid
        if (protocol == null) {
            System.err.println("Protocol not found for port " + portID);
            return false;
        }

        switch (devName) {
            case "DHT11":
                if (!(protocol instanceof OneWire)) {
                    System.err.println("Incompatible protocol.");
                    return false;
                }
                sensors.set(devID, new DHT11(protocol));
                devices.set(portID, sensors.get(devID));
                break;
            case "BME280":
                if (!(protocol instanceof I2C || protocol instanceof SPI)) {
                    System.err.println("Incompatible protocol.");
                    return false;
                }
                sensors.set(devID, new BME280(protocol));
                devices.set(portID, sensors.get(devID));
                break;
            case "MPU6050":
                if (!(protocol instanceof I2C)) {
                    System.err.println("Incompatible protocol.");
                    return false;
                }
                sensors.set(devID, new MPU6050(protocol));
                devices.set(portID, sensors.get(devID));
                break;
            case "GY951":
                if (!(protocol instanceof SPI || protocol instanceof UART)) {
                    System.err.println("Incompatible protocol.");
                    return false;
                }
                sensors.set(devID, new GY_951(protocol));
                devices.set(portID, sensors.get(devID));
                break;
            case "LCD":
                if (!(protocol instanceof I2C)) {
                    System.err.println("Incompatible protocol.");
                    return false;
                }
                displays.set(devID, new LCD(protocol));
                devices.set(portID, displays.get(devID));
                break;
            case "OLED":
                if (!(protocol instanceof SPI)) {
                    System.err.println("Incompatible protocol.");
                    return false;
                }
                displays.set(devID, new OLED(protocol));
                devices.set(portID, displays.get(devID));
                break;
            case "Bluetooth":
                if (!(protocol instanceof UART)) {
                    System.err.println("Incompatible protocol.");
                    return false;
                }
                wirelessIOs.set(devID, new Bluetooth(protocol));
                devices.set(portID, wirelessIOs.get(devID));
                break;
            case "Wifi":
                if (!(protocol instanceof SPI || protocol instanceof UART)) {
                    System.err.println("Incompatible protocol.");
                    return false;
                }
                wirelessIOs.set(devID, new Wifi(protocol));
                devices.set(portID, wirelessIOs.get(devID));
                break;
            case "PCA9685":
                if (!(protocol instanceof I2C)) {
                    System.err.println("Incompatible protocol.");
                    return false;
                }
                motorDrivers.set(devID, new PCA9685(protocol));
                devices.set(portID, motorDrivers.get(devID));
                break;
            case "SparkFunMD":
                if (!(protocol instanceof SPI)) {
                    System.err.println("Incompatible protocol.");
                    return false;
                }
                motorDrivers.set(devID, new SparkFunMD(protocol));
                devices.set(portID, motorDrivers.get(devID));
                break;
            default:
                System.err.println("Unknown device type.");
                return false;
        }
        System.out.println("Device added.");
        return true;
    }

    
    /**
     * Removes the device from the specified port.
     * 
     * @param portID the port ID of the device to be removed
     * @return true if the device is successfully removed, false otherwise
     */
    public Boolean rmDev(int portID) {
        if (devices.get(portID) == null){
            System.err.println("Port is already empty. Remove Device command can not be executed.");
            return false;
        }
        if (devices.get(portID).getState() == State.ON){
            System.err.println("Device is active. Remove Device command can not be executed.");
            return false;
        }

        //Store to remove instance later
        Device device = devices.get(portID);
        devices.set(portID, null); // Remove from the device list

        // Remove from specific type lists
        if (device instanceof Sensor) sensors.remove(device);
        else if (device instanceof Display) displays.remove(device);
        else if (device instanceof WirelessIO) wirelessIOs.remove(device);
        else if (device instanceof MotorDriver) motorDrivers.remove(device);

        System.out.println("Device removed.");
        return true;
    }

    /**
     * Lists all the ports in the system, showing their protocol and occupancy status.
     */
    public void listPorts() {
        System.out.println("list of ports:");

        for(int i = 0; i < ports.size(); i++){
            System.out.print(i + " " + ports.get(i).getProtocolName());
            if(devices.get(i) == null){
                System.out.print(" empty");
            }else{
                String devName = devices.get(i).getName();
                String devType = devices.get(i).getDevType();
                int devID = findDevID(devices.get(i));
                State devState = devices.get(i).getState();
                System.out.print(" occupied " + devName + " " + devType + " " + devID + " " + devState);
            }
            System.out.println("");
        }
    }

    /**
     * Lists all devices of a specific type in the system.
     * 
     * @param devType the type of devices to list (e.g., "Sensor", "Display", "WirelessIO", "MotorDriver")
     */
    public void listDevType(String devType) {
        System.out.println("list of " + devType + ":");
    
        ArrayList<? extends Device> list;
        switch (devType) {
            case "Sensor":
                list = sensors;
                break;
            case "Display":
                list = displays;
                break;
            case "WirelessIO":
                list = wirelessIOs;
                break;
            case "MotorDriver":
                list = motorDrivers;
                break;
            default:
                list = null;
        }

        if (list == null) {
            System.err.println("Invalid device type to list or no device in this type to list.");
            return;
        }
    
        for (int devID = 0; devID < list.size(); devID++) {
            Device device = list.get(devID);
            int portID = devices.indexOf(device); // Find portID dynamically
            Protocol protocol = device.getProtocol();
    
            System.out.println(device.getName() + " " + devID + " " + portID + " " + protocol.getProtocolName());
        }
    }
    
    /**
     * Reads data from the sensor at the specified device ID.
     * 
     * @param devID the device ID of the sensor to read from
     * @return true if the sensor data is successfully read, false otherwise
     */
    public Boolean readSensor(int devID) {
        if (sensors.size() <= devID){
            System.err.println("Device number: " + devID + "is out of bounds. There is only " + sensors.size() + "in this type");
            return false;
        }
        if (sensors.get(devID) == null) {
            System.err.println("There is no device with devID: " + devID);
            return false;
        }
        if (sensors.get(devID).getState() == State.OFF){
            System.err.println("Device is not active. Read Sensor command can not be executed.");
            return false;
        }
        System.out.println(sensors.get(devID).data2String());
        return true;
    }

    /**
     * Prints data to the display at the specified device ID.
     * 
     * @param devID the device ID of the display
     * @param data the data to print on the display
     * @return true if the data is successfully printed, false otherwise
     */
    public Boolean printDisplay(int devID, String data) {
        if (displays.size() <= devID){
            System.err.println("Device number: " + devID + "is out of bounds. There is only " + displays.size() + "in this type");
            return false;
        }
        if (displays.get(devID) == null) {
            System.err.println("There is no device with devID: " + devID);
            return false;
        }
        if (displays.get(devID).getState() == State.OFF){
            System.err.println("Device is not active. Print Display command can not be executed.");
        }
        displays.get(devID).printData(data);;
        return true;
    }

    /**
     * Reads data from the wireless I/O device at the specified device ID.
     * 
     * @param devID the device ID of the wireless I/O device
     * @return true if data is successfully read from the wireless I/O device, false otherwise
     */
    public Boolean readWireless(int devID) {
        if (wirelessIOs.size() <= devID){
            System.err.println("Device number: " + devID + "is out of bounds. There is only " + wirelessIOs.size() + "in this type");
            return false;
        }
        if (wirelessIOs.get(devID) == null) {
            System.err.println("There is no device with devID: " + devID);
            return false;
        }
        if (wirelessIOs.get(devID).getState() == State.OFF){
            System.err.println("Device is not active. Read Wireless command can not be executed.");
        }
        wirelessIOs.get(devID).recvData();
        return true;
    }

     /**
     * Writes data to the wireless I/O device at the specified device ID.
     * 
     * @param devID the device ID of the wireless I/O device
     * @param data the data to write to the wireless I/O device
     * @return true if the data is successfully written, false otherwise
     */
    public Boolean writeWireless(int devID, String data) {
        if (wirelessIOs.size() <= devID){
            System.err.println("Device number: " + devID + "is out of bounds. There is only " + wirelessIOs.size() + "in this type");
            return false;
        }
        if (wirelessIOs.get(devID) == null) {
            System.err.println("There is no device with devID: " + devID);
            return false;
        }
        if (wirelessIOs.get(devID).getState() == State.OFF){
            System.err.println("Device is not active. Read Wireless command can not be executed.");
            return false;
        }
        wirelessIOs.get(devID).setData(data);
        return true;
    }

    /**
     * Sets the speed for the motor driver at the specified device ID.
     * 
     * @param devID the device ID of the motor driver
     * @param speed the speed value to set for the motor driver
     * @return true if the motor speed is successfully set, false otherwise
     */
    public Boolean setMotorSpeed(int devID, int speed) {
        if (motorDrivers.size() < devID){
            System.err.println("Device number: " + devID + "is out of bounds. There is only " + motorDrivers.size() + "in this type");
            return false;
        }
        if (motorDrivers.get(devID) == null) {
            System.err.println("There is no device with devID: " + devID);
            return false;
        }
        if (motorDrivers.get(devID).getState() == State.OFF){
            System.err.println("Device is not active. Set Motor Speed command can not be executed.");
            return false;
        }
        motorDrivers.get(devID).setMotorSpeed(speed);
        return true;
    }

    /**
     * Finds the device ID of the specified device in the list of devices.
     * 
     * @param device the device whose ID is to be found
     * @return the device ID if the device is found, otherwise -1
     */
    private int findDevID(Device device) {
        if (device instanceof Sensor) return sensors.indexOf(device);
        if (device instanceof Display) return displays.indexOf(device);
        if (device instanceof WirelessIO) return wirelessIOs.indexOf(device);
        if (device instanceof MotorDriver) return motorDrivers.indexOf(device);
        return -1; // Should not happen
    }

    /**
     * Reads commands from the user input and stores them in the commandList.
     * The loop will continue reading commands until the user inputs the command "exit".
     * 
     * This method uses a scanner to get user input from the console and adds each input to a 
     * command list. It terminates when the user types "exit".
     */
    public void getCommands(){
        Scanner scanner = new Scanner(System.in);
        while(true){
            String input = scanner.nextLine().trim();
            commandList.add(input);
            if (input.equals("exit"))break;
        }
        scanner.close();
    }

    /**
     * Executes the commands stored in the commandList.
     * The method processes commands sequentially and performs actions based on the command.
     * Commands include operations like listing devices, turning devices on/off, 
     * adding/removing devices, reading sensor data, printing data to displays, 
     * reading/writing data to wireless devices, and setting motor speed.
     * 
     * The command format is as follows:
     * - "exit": Terminates the command loop.
     * - "list <type>": Lists available ports or devices (e.g., "list ports", "list Sensor").
     * - "turnON <portID>": Turns on the device connected to the specified port.
     * - "turnOFF <portID>": Turns off the device connected to the specified port.
     * - "addDev <devName> <portID> <devID>": Adds a new device with the specified name, port, and device ID.
     * - "rmDev <portID>": Removes a device from the specified port.
     * - "readSensor <devID>": Reads the sensor data from the specified device.
     * - "printDisplay <devID> <data>": Prints the specified data to the specified display device.
     * - "readWireless <devID>": Reads data from the specified wireless I/O device.
     * - "writeWireless <devID> <data>": Writes data to the specified wireless I/O device.
     * - "setMotorSpeed <devID> <speed>": Sets the motor speed for the specified motor driver.
     * 
     * If an invalid command or insufficient arguments are provided, an error message is displayed.
     */
    public void exeCommands(){
        while(!commandList.isEmpty()){
            String[] parts = commandList.pollFirst().split(" ", 4);

            String command = parts[0];
            switch (command) {
                case "exit":
                    System.out.println("Exitting ...");
                    break;
                case "list":
                    if (parts.length < 2) {
                        System.out.println("Usage: list <ports|Sensor|Display|MotorDriver|WirelessIO>");
                        continue;
                    }

                    String listType = parts[1];
                    switch (listType) {
                        case "ports":
                            listPorts();
                            break;
                    
                        case "Sensor":
                        case "Display":
                        case "MotorDriver":
                        case "WirelessIO":
                            listDevType(listType);
                            break;

                        default:
                            System.out.println("Invalid list type. Usage: list <ports|Sensor|Display|MotorDriver|WirelessIO>");
                            break;
                    }
                    break;
                case "turnON" :
                    if (parts.length < 2) {
                        System.out.println("Usage: turnON <portID>");
                        continue;
                    }
                    int portOn = Integer.parseInt(parts[1]);
                    turnOnDevice(portOn);
                break;
                case "turnOFF" :
                    if (parts.length < 2) {
                        System.out.println("Usage: tunrOFF <portID>");
                        continue;
                    }
                    int portOff = Integer.parseInt(parts[1]);
                    turnOffDevice(portOff);
                    break;
                case "addDev" :
                    if (parts.length < 4) {
                        System.out.println("Usage: addDev <devName> <portID> <devID>");
                        continue;
                    }
                    String devName = parts[1];
                    int portID = Integer.parseInt(parts[2]);
                    int devID = Integer.parseInt(parts[3]);
                    addDev(devName, portID, devID);
                    break;
                case "rmDev" :
                    if (parts.length < 2) {
                        System.out.println("Usage: rnDev <portID>");
                        continue;
                    }
                    int rmPort = Integer.parseInt(parts[1]);
                    rmDev(rmPort);
                    break;
                case "readSensor" :
                    if (parts.length < 2) {
                        System.out.println("Usage: readSensor <devID>");
                        continue;
                    }
                    int sensorID = Integer.parseInt(parts[1]);
                    readSensor(sensorID);
                    break;
                case "printDisplay" :
                    if (parts.length < 3) {
                        System.out.println("Usage: printDisplay <devID> <data>");
                        continue;
                    }
                    int displayID = Integer.parseInt(parts[1]);
                    String displayData = parts[2];
                    printDisplay(displayID, displayData);
                    break;
                case "readWireless" :
                    if (parts.length < 2) {
                        System.out.println("Usage: readWireless <devID>");
                        continue;
                    }
                    int wirelessID = Integer.parseInt(parts[1]);
                    readWireless(wirelessID);
                    break;
                case "writeWireless" :
                    if (parts.length < 3) {
                        System.out.println("Usage: writeWireless <devID> <data>");
                        continue;
                    }
                    int writeID = Integer.parseInt(parts[1]);
                    String writeData = parts[2];
                    writeWireless(writeID, writeData);
                    break;
                case "setMotorSpeed" :
                    if (parts.length < 3) {
                        System.out.println("Usage: setMotorSpeed <devID> <speed>");
                        continue;
                    }
                    int motorID = Integer.parseInt(parts[1]);
                    int speed = Integer.parseInt(parts[2]);
                    setMotorSpeed(motorID, speed);
                    break;
                default:
                    System.err.println("Invalid command: " + command);
            }
        }
    }

    /**
     * Logs the current state of the ports to the specified directory.
     * For each port in the system, it calls the close method of the protocol 
     * associated with the port to log its data to the directory.
     * 
     * If the specified directory does not exist, it is created.
     * 
     * @param logDirectory the directory where the port logs will be stored
     */
    public void logPorts(String logDirectory){
        File directory = new File(logDirectory);

        if(!directory.exists()){
            directory.mkdirs();
        }

        for(int i = 0; i < ports.size(); i++){
            ports.get(i).close(logDirectory);
        }
    }
}
