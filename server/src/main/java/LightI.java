import Home.HomeStaffStateException;
import com.zeroc.Ice.Current;

import java.util.Random;

public class LightI implements Home.Light{

    private double brightness = (new Random()).nextDouble();
    @Override
    public void setBrightness(double b, Current current) throws HomeStaffStateException {
        if (0 <= b && b <= 1) {
            brightness = b;
        } else {
            throw new HomeStaffStateException("brightness must  be between 0 and 1");
        }
    }

    @Override
    public double getBrightness(Current current) throws HomeStaffStateException {
        return brightness;
    }
}
