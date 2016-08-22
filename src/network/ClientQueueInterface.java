package network;

/**
 * Created by quang on 08/18/16.
 */
public interface ClientQueueInterface {
    void canTransmit(boolean state);

    void transmitNext();
}
