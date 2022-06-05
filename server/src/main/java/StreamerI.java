import com.zeroc.Ice.Current;

public class StreamerI implements Home.Streamer
{
    @Override
    public byte[] stream(Current current) throws Home.HomeStaffStateException {
        return new byte[10];
    }
}