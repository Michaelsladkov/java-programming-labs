package network;

import data.CommandToSend;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class CommandSender {
    private final DatagramChannel datagramChannel;
    private SocketAddress serverSocketAddress;

    public CommandSender(DatagramChannel channel, SocketAddress address) {
        datagramChannel = channel;
        serverSocketAddress = address;
    }

    public CommandSender(DatagramChannel channel) {
        datagramChannel = channel;
        try {
            serverSocketAddress = new InetSocketAddress(InetAddress.getLocalHost(), 0);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void setServerSocketAddress(SocketAddress address) {
        serverSocketAddress = address;
    }

    public void sendCommand(CommandToSend command) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream;
        ObjectOutputStream objectOutputStream;
        datagramChannel.connect(serverSocketAddress);
        byteArrayOutputStream = new ByteArrayOutputStream();
        objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(command);
        objectOutputStream.flush();
        int packageSize = byteArrayOutputStream.size();
        while (datagramChannel.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray())) <
                packageSize) ;
        datagramChannel.disconnect();
    }
}
