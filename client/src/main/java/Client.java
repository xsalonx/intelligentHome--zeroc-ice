import com.zeroc.Ice.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//https://pypi.org/project/zeroc-icecertutils/
import java.lang.Exception;
import java.util.Scanner;

public class Client {

    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);
    private static String adapterName = "HomeAdapter";
    public static void main(String[] args) {
        try (Communicator communicator =
                     Util.initialize(args)) {

            Scanner scanner = new Scanner(System.in);
            String cmd = "";
            while (!"quit".equals(cmd)) {
                try {
                    System.out.print("==> ");
                    cmd = scanner.nextLine().trim();
                    if (cmd.startsWith("term")) {
                        cmd = cmd.substring("term".length()).trim();
                        int thermometer_index = Integer.parseInt(cmd);
                        String objectName = "Thermometer_" + thermometer_index;
                        com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy(
                                objectName + "@" + adapterName);
                        Home.ThermometerPrx temp = Home.ThermometerPrx.checkedCast(base);
                        System.out.printf("%s: %f\n", objectName, temp.getCurrentTemperature());
                    }
                } catch (Home.HomeStaffStateException e){
                    LOGGER.error(e.getMessage());
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }

            }
        }
    }

}