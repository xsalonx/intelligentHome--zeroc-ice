import com.zeroc.Ice.Current;
import java.util.Calendar;

public class ThermometerI implements Home.Thermometer {

    @Override
    public double getCurrentTemperature(Current current) {
        return Calendar.getInstance().getTimeInMillis();
    }
}
