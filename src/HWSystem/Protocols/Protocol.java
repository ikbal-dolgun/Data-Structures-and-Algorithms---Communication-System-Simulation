package HWSystem.Protocols;

/**
 * Represents the communication protocol for devices in the system.
 * The Protocol interface defines the basic operations that a protocol 
 * must implement: reading, writing, closing the connection, and retrieving 
 * the protocol's name.
 */
public interface Protocol {
    String read();

    void write (String data);

    void close(String logDirectory);
    
    String getProtocolName();
}
