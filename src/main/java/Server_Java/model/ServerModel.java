package Server_Java.model;

import Server_Java.model.implementations.AuthenticationImpl;
import Server_Java.model.implementations.BoggledApp.Authentication;
import Server_Java.model.implementations.BoggledApp.AuthenticationHelper;
import Server_Java.model.implementations.BoggledApp.GameService;
import Server_Java.model.implementations.BoggledApp.GameServiceHelper;
import Server_Java.model.implementations.GameServiceImpl;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ServerModel {
    private AuthenticationImpl authentication;
    private GameServiceImpl gameManager;
    public static int waitingTime;
    public static int roundLength;
    private static Set<String> wordBank;
    private String filepath = "src/main/java/Server_Java/res/words.txt";


    public ServerModel() {
        //reading the config file
        String[] params = readConfig();
        String[] gameParams = readGameConfig();
        String[] args = new String[]{"-ORBInitialPort", params[0], "-ORBInitialHost", params[1]};
        waitingTime = Integer.parseInt(gameParams[0]);
        roundLength = Integer.parseInt(gameParams[1]);

        //preparing the word bank
        prepareWordBank(filepath);

        //open the CORBA connection
        openConnection(args);
    }

    private String[] readConfig() {
        List<String> params  = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/.config"))){
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("=");

                String key = parts[0].trim();
                String value = parts[1].trim();
                if (key.equals("host") || key.equals("port")) {
                    params.add(value);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return params.toArray(new String [0]);
    }
    private String[] readGameConfig() {
        List<String> gameParams = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/.config"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("=");

                String key = parts[0].trim();
                String value = parts[1].trim();
                if (key.equals("waitingTime") || key.equals("roundLength")) {
                    gameParams.add(value);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return gameParams.toArray(new String[0]);
    }

    private void prepareWordBank(String filepath){
        wordBank = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            // read each line from the text file
            while ((line = reader.readLine()) != null) {
                // check if the word is not already in the HashSet
                if (!wordBank.contains(line)) {
                    // add the word to the HashSet
                    wordBank.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isFoundInWordBank(String word){
        // check if the word is present in the HashSet
        return wordBank.contains(word);
    }

    private void openConnection(String[] args) {
        try {
            // create and initialize the ORB
            ORB orb = ORB.init(args, null);

            // get reference to rootpoa & activate the POAManager
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            // create servant and register it with the ORB
            authentication = new AuthenticationImpl();
            gameManager = new GameServiceImpl();

            // get object reference from the servant
            org.omg.CORBA.Object authenticationRef = rootpoa.servant_to_reference(authentication);
            org.omg.CORBA.Object gameManagerRef = rootpoa.servant_to_reference(gameManager);

            Authentication aRef = AuthenticationHelper.narrow(authenticationRef);
            GameService gRef = GameServiceHelper.narrow(gameManagerRef);

            // get the root naming context
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");

            // Use NamingContextExt which is part of the Interoperable
            // Naming Service (INS) specification.
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            // bind the Object Reference in Naming
            String authenticationFriendlyName = "Authentication";
            String gameManagerFriendlyName = "Game Manager";

            NameComponent[] authenticationPath = ncRef.to_name(authenticationFriendlyName);
            NameComponent[] gameManagerPath = ncRef.to_name(gameManagerFriendlyName);

            ncRef.rebind(authenticationPath, aRef);
            ncRef.rebind(gameManagerPath, gRef);
            System.out.println("Boggled Server ready and waiting ...");
            // wait for invocations from clients
            orb.run();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
