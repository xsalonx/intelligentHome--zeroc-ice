import Home.HomeStaffStateException;
import com.zeroc.Ice.Current;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ThermometerI implements Home.Thermometer {

    static final Logger LOGGER = LoggerFactory.getLogger(ThermometerI.class);

    private final TreeMap<Long, Double> temperaturesStore = new TreeMap<>();

    private TempSensor tempSensor = new TempSensor();
    private int timestampSeconds = 1;

    public ThermometerI() {
        new Thread(new TemperatureMeasureTask()).start();
    }

    class TemperatureMeasureTask implements Runnable {
        private boolean end = false;

        @Override
        public void run() {
            while (!end) {
                try {
                    Thread.sleep(timestampSeconds * 1000L);
                    synchronized (temperaturesStore) {
                        temperaturesStore.put(Calendar.getInstance().getTimeInMillis(), tempSensor.getCurrentTemperature());
                    }
                } catch (InterruptedException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
    }



    static class TempSensor {
        private final Random random = new Random();
        double getCurrentTemperature() {
            return 19 + random.nextDouble() * 5;
        }
    }


    @Override
    public Map<Long, Double> getMeasuresInTimeRange(long t1, long t2, Current current) throws HomeStaffStateException {
        synchronized (temperaturesStore) {
            return temperaturesStore.subMap(t1, t2);
        }
    }

    @Override
    public double getCurrentTemperature(Current current) {
        return tempSensor.getCurrentTemperature();
    }
}
