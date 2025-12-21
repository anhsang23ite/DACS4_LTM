import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import impl.RMIServiceImpl; // <-- ĐÃ IMPORT ĐÚNG
import impl.RMIServiceImpl;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class ServerMain {
    public static void main(String[] args) {
        try {
            RMIServiceImpl service = new RMIServiceImpl();

            Registry registry = LocateRegistry.createRegistry(1098);

            // rebind với đối tượng đã tạo
            registry.rebind("RMIService", service);

            System.out.println(">> Server is RUNNING on port 1098...");
            System.setProperty("java.rmi.server.hostname", "127.0.0.1");

            try {
                LocateRegistry.createRegistry(1098);
                System.out.println(">> RMI Registry created on port 1098");
            } catch (Exception e) {
                System.out.println(">> RMI Registry already exists");
            }

            Naming.rebind(
                    "rmi://127.0.0.1:1098/RMIService",
                    new RMIServiceImpl()
            );

            System.out.println(">> Server is RUNNING on 127.0.0.1:1098");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
