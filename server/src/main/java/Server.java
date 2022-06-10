import com.zeroc.Ice.*;
import com.zeroc.Ice.Object;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Server {

    private static final String OBJECT_FILE_CONF_ENV_VAR_NAME = "OBJECT_CONF_FILE";
    private static String adapterName = "IHAdapter";
    private static ObjectAdapter adapter;

    public static void main(String[] args) {
        try (Communicator communicator =
                     com.zeroc.Ice.Util.initialize(args)) {
            System.out.println(Arrays.toString(args));
            adapter = communicator.createObjectAdapter(adapterName);

            initObjects();

            adapter.activate();
            communicator.waitForShutdown();
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void initObjects()
            throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String objectConfPath = System.getenv(OBJECT_FILE_CONF_ENV_VAR_NAME);
        System.out.println("objects_conf_path=" + objectConfPath);

        String conf = new String(Files.readAllBytes(Paths.get(objectConfPath)));
        System.out.println(conf);

        String[] classAndObjectName = conf.trim().split("\n+");
        System.out.println(Arrays.toString(classAndObjectName));
        for (String con : classAndObjectName) {
            String[] c_o = con.trim().split(" *: *");
            String objectClassName = c_o[0];
            String objectName = c_o[1];
            System.out.printf("try to inst: %s : %s", objectClassName, objectName);
            Class<?> termClass = Class.forName(objectClassName);
            Constructor<?> constructor = termClass.getConstructor();
            adapter.add((Object) constructor.newInstance(), com.zeroc.Ice.Util.stringToIdentity(objectName));
        }
    }
}