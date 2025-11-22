package server;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerMain {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1098);
            registry.rebind("RMIService", new RMIServiceImpl());
            System.out.println(">> Server is RUNNING on port 1098...");
        } catch (Exception e) { e.printStackTrace(); }
    }
}