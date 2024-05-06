package Client_Java.model;

import Client_Java.BoggledApp.*;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ClientModel {
    public static Authentication authService;
    public static GameManager gameService;

    public ClientModel() {}

    public void init() {
        String[] params = readConfig();

        String[] args = {"-ORBInitialPort", params[0], "-ORBInitialHost", params[1]};

        connectToServer(args);
    } // end of init

    private void connectToServer(String[] args) {
        try {
            ORB orb = ORB.init(args, null);

            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");

            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            String authServiceName = "Authentication";
            String gameServiceName = "Game Manager";

            authService = AuthenticationHelper.narrow(ncRef.resolve_str(authServiceName));
            gameService = GameManagerHelper.narrow(ncRef.resolve_str(gameServiceName));

            System.out.println("connected to the server");
        } catch (Exception e) {
            e.printStackTrace();
        }
    } // end of connectToServer

    private String[] readConfig() {
        String portNumber = null, hostAddress = null;

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/.config"))){
            String line;

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split("=");
                String key = tokens[0].trim();
                String value = tokens[1].trim();
                switch (key) {
                    case "port":
                        portNumber = value;
                        break;
                    case "host":
                        hostAddress = value;
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String[]{portNumber, hostAddress};
    } // end of readConfig
} // end of ClientModel class
