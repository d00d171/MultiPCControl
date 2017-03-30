package pl.ciochon.multikeyboard.host.server;

import pl.ciochon.multikeyboard.host.mouse.generator.MOUSEINPUT;
import pl.ciochon.multikeyboard.host.mouse.generator.MouseEventGenerator;
import pl.ciochon.multikeyboard.host.mouse.generator.MouseInputSerializable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Konrad Ciochoń on 2017-03-28.
 */
public class Server {
    private int port;

    public Server(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        Thread serverThread = new Thread() {
            @Override
            public void run() {
                try {
                    ServerSocket sersock = new ServerSocket(port);
                    Socket sock = sersock.accept();

                    ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());

                    System.out.println("Server started");
                    while (true) {
                        MOUSEINPUT mouseinput = ((MouseInputSerializable) ois.readObject()).toMOUSEINPUT();
                        System.out.println(mouseinput.toString());
                        MouseEventGenerator.generateEvent(mouseinput);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        };

        serverThread.start();

    }

}
