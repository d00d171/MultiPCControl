package pl.ciochon.multipccontrol.client;

import org.apache.log4j.Logger;
import pl.ciochon.multipccontrol.mouse.generator.nativeapi.MOUSEINPUT;
import pl.ciochon.multipccontrol.mouse.hook.MouseHook;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Konrad Ciochoń on 2017-03-28.
 */
public class Client {

    private static Logger logger = Logger.getLogger(Client.class);

    private String host;

    private int port;

    private LinkedBlockingQueue<MOUSEINPUT> mouseInputQueue = new LinkedBlockingQueue();

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() {

        Thread clientThread = new Thread() {
            @Override
            public void run() {
                try {
                    Socket sock = new Socket(host, port);
                    ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());

                    logger.info("Client started");
                    while (true) {
                        MOUSEINPUT mouseinput = mouseInputQueue.take();
                        logger.trace(mouseinput);
                        oos.writeObject(mouseinput);
                        oos.flush();
                    }

                } catch (Exception e) {
                    logger.error("Client error", e);
                }
            }
        };

        clientThread.start();

        MouseHook mouseHook = new MouseHook(mouseInputQueue);
        mouseHook.start();

    }

}
