import com.zeroc.Ice.*;

import java.util.Arrays;

public class Server {

    private static String adapterName = "HomeAdapter";
    public static void main(String[] args) {
        try (Communicator communicator =
                     com.zeroc.Ice.Util.initialize(args)) {

            System.out.println(Arrays.toString(args));
            ObjectAdapter adapter =
                    communicator.createObjectAdapter(adapterName);

            adapter.add(new ThermometerI(), com.zeroc.Ice.Util.stringToIdentity("Thermometer_1"));

            adapter.activate();
            communicator.waitForShutdown();
        }
    }
}