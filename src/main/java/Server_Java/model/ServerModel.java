package Server_Java.model;

import Server_Java.model.implementations.AuthenticationImpl;
import Server_Java.model.implementations.BoggledApp.Authentication;
import Server_Java.model.implementations.BoggledApp.AuthenticationHelper;
import Server_Java.model.implementations.BoggledApp.GameManager;
import Server_Java.model.implementations.BoggledApp.GameManagerHelper;
import Server_Java.model.implementations.GameManagerImpl;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

public class ServerModel {
    private AuthenticationImpl authentication;
    private GameManagerImpl gameManager;
    private String[] args = {"-ORBInitialPort 2000", "ORBInitialHost localhost"};
    public static int WAITING_TIME = 10;
    public static int ROUND_LENGTH = 30;

    public ServerModel() {
        try {
            // create and initialize the ORB
            ORB orb = ORB.init(args, null);

            // get reference to rootpoa & activate the POAManager
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            // create servant and register it with the ORB
            authentication = new AuthenticationImpl();
            gameManager = new GameManagerImpl();

            // get object reference from the servant
            org.omg.CORBA.Object authenticationRef = rootpoa.servant_to_reference(authentication);
            org.omg.CORBA.Object gameManagerRef = rootpoa.servant_to_reference(gameManager);
            Authentication aRef = AuthenticationHelper.narrow(authenticationRef);
            GameManager gRef = GameManagerHelper.narrow(gameManagerRef);

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
            System.out.println("HelloServer ready and waiting ...");
            // wait for invocations from clients
            orb.run();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
