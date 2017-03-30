package pl.ciochon.multikeyboard.host.client;

import pl.ciochon.multikeyboard.host.mouse.generator.MOUSEINPUT;
import pl.ciochon.multikeyboard.host.mouse.generator.MouseInputSerializable;
import pl.ciochon.multikeyboard.host.mouse.hook.MouseHook;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Konrad Ciochoń on 2017-03-28.
 */
public class Client {

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

                    System.out.println("Client started");
                    while (true) {
                        MOUSEINPUT mouseinput = mouseInputQueue.take();
                        oos.writeObject(new MouseInputSerializable(mouseinput));
                        oos.flush();
                    }

                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        };

        clientThread.start();

        MouseHook mouseHook = new MouseHook(mouseInputQueue);
        mouseHook.start();

    }

}
