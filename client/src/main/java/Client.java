import com.zeroc.Ice.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.Exception;
import java.util.Calendar;
import java.util.Map;
import java.util.Scanner;

public class Client {

    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) {
        String adapterName = "IHAdapter";
        String serverPrefix = "IHServer_";
        String serverName = "home1";

        try (Communicator communicator =
                     Util.initialize(args)) {

            Scanner scanner = new Scanner(System.in);
            String cmd = "";
            while (!"quit".equals(cmd)) {
                try {
                    System.out.print("==> ");
                    cmd = scanner.nextLine().trim();
                    String[] cmdAndParams = cmd.split(" +");
                    cmd = String.join(" ", cmdAndParams);
                    if (cmd.matches("set server .+")) {
                        serverName = cmdAndParams[2];
                    } else if (cmd.matches("set adapter .+")) {
                        adapterName = cmdAndParams[2];
                    } else if (cmd.startsWith("term")) {
                        String objectName = String.format(
                                "Thermometer_%s@%s%s.%s", cmdAndParams[1], serverPrefix, serverName, adapterName);

                        com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy(objectName);
                        Home.ThermometerPrx thermometer = Home.ThermometerPrx.checkedCast(base);

                        if (cmd.matches("term \\d+ curr")) {

                            System.out.printf("%f\n", thermometer.getCurrentTemperature());
                        } else if (cmd.matches("term \\d+ range .+")) {
                            long upper, lower;
                            if (cmdAndParams[3].equals("-n")) {
                                upper = Calendar.getInstance().getTimeInMillis();
                            } else {
                                throw new RuntimeException("other date range than with upper equal to NOW is not implemented");
                            }

                            if (cmdAndParams[4].equals("-m")) {
                                lower = 60000 * Long.parseLong(cmdAndParams[5]);
                            } else {
                                throw new RuntimeException("other time unit than minutes is not implemented, use (-m)");
                            }

                            Map<Long, Double> res = thermometer.getMeasuresInTimeRange(lower, upper);
                            System.out.println(res);
                        } else if (cmd.matches("term \\d+ stream")) {
                            OutputStream outputStream = new OutputStream(communicator);

                        } else {
                            LOGGER.error("incorrect command");
                        }
                    } else if (cmd.matches("print .+")) {
                        if (cmd.matches("print target")) {
                            System.out.printf("@%s%s.%s%n", serverPrefix, serverName, adapterName);
                        } else {
                            LOGGER.error("incorrect command");
                        }
                    } else {
                        LOGGER.error("incorrect command");
                    }


                } catch (Home.HomeStaffStateException e){
                    LOGGER.error(e.getMessage());
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                }

            }
        }
    }

}