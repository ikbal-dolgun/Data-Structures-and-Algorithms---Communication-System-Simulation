package HWSystem.Protocols;

public class UART implements Protocol{
    public String read() {
        return getProtocolName() + ": Reading.";
    }
    
    public void write(String data) {
        System.out.printf("%s: Writing \"%s\".\n", getProtocolName(), data);
    }

    public String getProtocolName() {
        return "UART";
    }
}
