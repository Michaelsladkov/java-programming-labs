package util;

import data.CommandToSend;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.PortUnreachableException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class CommandSender {
    private final DatagramChannel datagramChannel;
    private final SocketAddress serverSocketAddress;

    public CommandSender(DatagramChannel channel, SocketAddress address) {
        datagramChannel = channel;
        serverSocketAddress = address;
    }

    public void sendCommand(CommandToSend command) {
        ByteArrayOutputStream byteArrayOutputStream;
        ObjectOutputStream objectOutputStream;
        try {
            datagramChannel.connect(serverSocketAddress);
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(command);
            objectOutputStream.flush();
            int packageSize = byteArrayOutputStream.size();
            while (datagramChannel.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray())) <
                   packageSize) ;
            datagramChannel.disconnect();
            Thread.sleep(50);
        } catch (PortUnreachableException|BindException e) {
            System.out.println("Server temporary not available, try again later");
        } catch (IOException e) {
            System.out.println("Failed to send command");
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
