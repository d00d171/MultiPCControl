package pl.ciochon.multipccontrol.server;

import org.apache.log4j.Logger;
import pl.ciochon.multipccontrol.mouse.generator.MouseEventGenerator;
import pl.ciochon.multipccontrol.mouse.generator.nativeapi.MOUSEINPUT;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Konrad Ciochoń on 2017-03-28.
 */
public class Server {
    private int port;

    private static Logger logger = Logger.getLogger(Server.class);

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

                    logger.info("Server started");
                    while (true) {
                        MOUSEINPUT mouseinput = (MOUSEINPUT) ois.readObject();
                        System.out.println(mouseinput.toString());
                        MouseEventGenerator.generateEvent(mouseinput);
                    }
                } catch (Exception e) {
                    logger.error("Server error occured: ", e);
                }
            }
        };

        serverThread.start();

    }

}
