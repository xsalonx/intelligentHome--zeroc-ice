import com.zeroc.Ice.*;
import com.zeroc.Ice.Object;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class Server {

    private static String adapterName = "IHAdapter";
    public static void main(String[] args) {
        try (Communicator communicator =
                     com.zeroc.Ice.Util.initialize(args)) {

            System.out.println(Arrays.toString(args));
            ObjectAdapter adapter =
                    communicator.createObjectAdapter(adapterName);
            Class<?> termClass = Class.forName("ThermometerI");
            Constructor<?> constructor = termClass.getConstructor();
            adapter.add((Object) constructor.newInstance(), com.zeroc.Ice.Util.stringToIdentity("Thermometer_1"));

            adapter.activate();
            communicator.waitForShutdown();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}