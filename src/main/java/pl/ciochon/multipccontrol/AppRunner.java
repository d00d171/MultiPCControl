package pl.ciochon.multipccontrol;

import pl.ciochon.multipccontrol.client.Client;
import pl.ciochon.multipccontrol.server.Server;

import java.io.IOException;

/**
 * Created by Konrad Ciochoń on 2017-03-27.
 */
public class AppRunner {

    private static void incorrectUsage() {
        System.out.println("Usage : [CLIENT {SERVER_HOST} {SERVER_PORT}] || [SERVER {LISTEN_PORT}]");
        System.exit(0);
    }

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            incorrectUsage();
        } else {
            String option = args[0];
            if (option.toUpperCase().equals("CLIENT")) {
                if (args.length != 3) {
                    incorrectUsage();
                }
                Client client = new Client(args[1], Integer.valueOf(args[2]));
                client.start();
            } else if (option.toUpperCase().equals("SERVER")) {
                if (args.length != 2) {
                    incorrectUsage();
                }
                Server server = new Server(Integer.valueOf(args[1]));
                server.start();
            } else {
                incorrectUsage();
            }
        }
    }

}
