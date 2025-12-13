import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import impl.RMIServiceImpl; // <-- ĐÃ IMPORT ĐÚNG

public class ServerMain {
    public static void main(String[] args) {
        try {
            RMIServiceImpl service = new RMIServiceImpl();

            Registry registry = LocateRegistry.createRegistry(1098);

            // rebind với đối tượng đã tạo
            registry.rebind("RMIService", service);

            System.out.println(">> Server is RUNNING on port 1098...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}