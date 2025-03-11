package HWSystem;

import java.util.ArrayList;
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

public class HWSystem {
    private ArrayList<Protocol> ports;
    private ArrayList<Device> devices;

    private ArrayList<Display> displays;
    private ArrayList<MotorDriver> motorDrivers;
    private ArrayList<Sensor> sensors;
    private ArrayList<WirelessIO> wirelessIOs;

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
    }
    
    
    public Boolean turnOnDevice(int portID) {
        if (devices.get(portID) == null){
            System.out.println("Port is empty.");
            return false;
        }
        if (devices.get(portID).getState() == State.ON){
            System.out.println("Device is already active.");
            return false;
        }
        devices.get(portID).turnON();
        return true;
    }

    public Boolean turnOffDevice(int portID) {
        if (devices.get(portID) == null){
            System.out.println("Port is empty.");
            return false;
        }
        if (devices.get(portID).getState() == State.OFF){
            System.out.println("Device is already off.");
            return false;
        }
        devices.get(portID).turnOFF();
        return true;
    }

    public Boolean addDev(String devName, int portID, int devID) {
        if (devices.get(portID) != null) {
            System.out.println("Port is already occupied.");
            return false;
        }

        Protocol protocol = ports.get(portID);
        Device newDevice = null;

        switch (devName) {
            case "DHT11":
                if (!(protocol instanceof OneWire)) {
                    System.out.println("Incompatible protocol.");
                    return false;
                }
                newDevice = new DHT11(protocol);
                break;
            case "BME280":
                if (!(protocol instanceof I2C || protocol instanceof SPI)) {
                    System.out.println("Incompatible protocol.");
                    return false;
                }
                newDevice = new BME280(protocol);
                break;
            case "MPU6050":
                if (!(protocol instanceof I2C)) {
                    System.out.println("Incompatible protocol.");
                    return false;
                }
                newDevice = new MPU6050(protocol);
                break;
            case "GY951":
                if (!(protocol instanceof SPI || protocol instanceof UART)) {
                    System.out.println("Incompatible protocol.");
                    return false;
                }
                newDevice = new GY_951(protocol);
                break;
            case "LCD":
                if (!(protocol instanceof I2C)) {
                    System.out.println("Incompatible protocol.");
                    return false;
                }
                newDevice = new LCD(protocol);
                break;
            case "OLED":
                if (!(protocol instanceof SPI)) {
                    System.out.println("Incompatible protocol.");
                    return false;
                }
                newDevice = new OLED(protocol);
                break;
            case "Bluetooth":
                if (!(protocol instanceof UART)) {
                    System.out.println("Incompatible protocol.");
                    return false;
                }
                newDevice = new Bluetooth(protocol);
                break;
            case "Wifi":
                if (!(protocol instanceof SPI || protocol instanceof UART)) {
                    System.out.println("Incompatible protocol.");
                    return false;
                }
                newDevice = new Wifi(protocol);
                break;
            case "PCA9685":
                if (!(protocol instanceof I2C)) {
                    System.out.println("Incompatible protocol.");
                    return false;
                }
                newDevice = new PCA9685(protocol);
                break;
            case "SparkFunMD":
                if (!(protocol instanceof SPI)) {
                    System.out.println("Incompatible protocol.");
                    return false;
                }
                newDevice = new SparkFunMD(protocol);
                break;
            default:
                System.out.println("Unknown device type.");
                return false;
        }

    devices.set(portID, newDevice);
    if (newDevice instanceof Sensor) sensors.add((Sensor) newDevice);
    else if (newDevice instanceof Display) displays.add((Display) newDevice);
    else if (newDevice instanceof WirelessIO) wirelessIOs.add((WirelessIO) newDevice);
    else if (newDevice instanceof MotorDriver) motorDrivers.add((MotorDriver) newDevice);

    return true;
}

    public Boolean rmDev(int portID) {
        if (devices.get(portID) == null){
            System.out.println("Port is already empty.");
            return false;
        }
        if (devices.get(portID).getState() == State.ON){
            System.out.println("Device is active.");
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

        System.out.println("Device removed successfully.");
        return true;
    }

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
            System.out.println("Invalid device type or no device in this type.");
            return;
        }
    
        for (int devID = 0; devID < list.size(); devID++) {
            Device device = list.get(devID);
            int portID = devices.indexOf(device); // Find portID dynamically
            Protocol protocol = device.getProtocol();
    
            System.out.println(device.getName() + " " + devID + " " + portID + " " + protocol.getProtocolName());
        }
    }
    

    public Boolean readSensor(int devID) {
        if (sensors.size() <= devID){
            System.out.println("Device number is out of bounds.");
            return false;
        }
        if (sensors.get(devID) == null) {
            System.out.println("There is no such device.");
            return false;
        }
        if (sensors.get(devID).getState() == State.OFF){
            System.out.println("Device is not active");
        }
        sensors.get(devID).data2String();
        return true;
    }

    public Boolean printDisplay(int devID, String data) {
        if (displays.size() <= devID){
            System.out.println("Device number is out of bounds.");
            return false;
        }
        if (displays.get(devID) == null) {
            System.out.println("There is no such device.");
            return false;
        }
        if (displays.get(devID).getState() == State.OFF){
            System.out.println("Device is not active");
        }
        displays.get(devID).printData(data);;
        return true;
    }

    public Boolean readWireless(int devID) {
        if (wirelessIOs.size() <= devID){
            System.out.println("Device number is out of bounds.");
            return false;
        }
        if (wirelessIOs.get(devID) == null) {
            System.out.println("There is no such device.");
            return false;
        }
        if (wirelessIOs.get(devID).getState() == State.OFF){
            System.out.println("Device is not active");
        }
        wirelessIOs.get(devID).recvData();
        return true;
    }

    public Boolean writeWireless(int devID, String data) {
        if (wirelessIOs.size() <= devID){
            System.out.println("Device number is out of bounds.");
            return false;
        }
        if (wirelessIOs.get(devID) == null) {
            System.out.println("There is no such device.");
            return false;
        }
        if (wirelessIOs.get(devID).getState() == State.OFF){
            System.out.println("Device is not active");
            return false;
        }
        wirelessIOs.get(devID).setData(data);
        return true;
    }

    public Boolean setMotorSpeed(int devID, int speed) {
        if (motorDrivers.size() < devID){
            System.out.println("Device number is out of bounds.");
            return false;
        }
        if (motorDrivers.get(devID) == null) {
            System.out.println("There is no such device.");
            return false;
        }
        if (motorDrivers.get(devID).getState() == State.OFF){
            System.out.println("Device is not active");
            return false;
        }
        motorDrivers.get(devID).setMotorSpeed(speed);
        return true;
    }

    private int findDevID(Device device) {
        if (device instanceof Sensor) return sensors.indexOf(device);
        if (device instanceof Display) return displays.indexOf(device);
        if (device instanceof WirelessIO) return wirelessIOs.indexOf(device);
        if (device instanceof MotorDriver) return motorDrivers.indexOf(device);
        return -1; // Should not happen
    }

    public void run(){
        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.print("\nCommand: ");
            String input = scanner.nextLine().trim();
            String[] parts = input.split(" ", 4);

            Boolean commandResult = true;

            String command = parts[0];
            switch (command) {
                case "exit":
                    scanner.close();
                    return;

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

                case "turnON":
                    if (parts.length < 2) {
                        System.out.println("Usage: turnON <portID>");
                        continue;
                    }
                    int portOn = Integer.parseInt(parts[1]);
                    commandResult = turnOnDevice(portOn);
                    break;

                case "turnOFF":
                    if (parts.length < 2) {
                        System.out.println("Usage: tunrOFF <portID>");
                        continue;
                    }
                    int portOff = Integer.parseInt(parts[1]);
                    commandResult = turnOffDevice(portOff);
                    break;

                case "addDev":
                    if (parts.length < 4) {
                        System.out.println("Usage: addDev <devName> <portID> <devID>");
                        continue;
                    }
                    String devName = parts[1];
                    int portID = Integer.parseInt(parts[2]);
                    int devID = Integer.parseInt(parts[3]);
                    commandResult = addDev(devName, portID, devID);
                    break;

                case "rmDev":
                    if (parts.length < 2) {
                        System.out.println("Usage: rnDev <portID>");
                        continue;
                    }
                    int rmPort = Integer.parseInt(parts[1]);
                    commandResult = rmDev(rmPort);
                    break;
                
                case "readSensor":
                    if (parts.length < 2) {
                        System.out.println("Usage: readSensor <devID>");
                        continue;
                    }
                    int sensorID = Integer.parseInt(parts[1]);
                    commandResult = readSensor(sensorID);
                    break;

                case "printDisplay":
                    if (parts.length < 3) {
                        System.out.println("Usage: printDisplay <devID> <data>");
                        continue;
                    }
                    int displayID = Integer.parseInt(parts[1]);
                    String displayData = parts[2];
                    commandResult = printDisplay(displayID, displayData);
                    break;

                case "readWireless":
                    if (parts.length < 2) {
                        System.out.println("Usage: readWireless <devID>");
                        continue;
                    }
                    int wirelessID = Integer.parseInt(parts[1]);
                    commandResult = readWireless(wirelessID);
                    break;

                case "writeWireless":
                    if (parts.length < 3) {
                        System.out.println("Usage: writeWireless <devID> <data>");
                        continue;
                    }
                    int writeID = Integer.parseInt(parts[1]);
                    String writeData = parts[2];
                    commandResult = writeWireless(writeID, writeData);
                    break;

                case "setMotorSpeed":
                    if (parts.length < 3) {
                        System.out.println("Usage: setMotorSpeed <devID> <speed>");
                        continue;
                    }
                    int motorID = Integer.parseInt(parts[1]);
                    int speed = Integer.parseInt(parts[2]);
                    commandResult = setMotorSpeed(motorID, speed);
                    break;

                default:
                    System.out.println("Invalid command: " + command);
            }
            if(!commandResult) System.out.println("Command failed.");
        }
    }
}
