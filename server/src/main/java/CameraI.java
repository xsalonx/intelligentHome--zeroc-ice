import com.zeroc.Ice.Current;

import java.nio.ByteBuffer;
import java.util.Random;

public class CameraI implements Home.Camera {

    @Override
    public GetStreamMarshaledResult getStream(Current current) {
        int frame_size = 640 * 480 * 3;
        byte[] bytes = new byte[frame_size];
        new Random().nextBytes(bytes);
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        return new GetStreamMarshaledResult(buffer, current);
    }

//    @Override
//    public void shutdownStream(Current current) {
//    }
}
