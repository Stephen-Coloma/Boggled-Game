package Server_Java.model.mainmenu;

import Server_Java.model.ServerModel;

import java.io.*;

public class EditGameConfigModel {
    private int waitingTime;
    private int roundLength;

    public EditGameConfigModel(int waitingTime, int roundLength) {
        this.waitingTime = waitingTime;
        this.roundLength = roundLength;
    }

    /**This method saves the game configuration to the ServerModel and into the .config file.*/
    public void saveGameConfig(){
        ServerModel.waitingTime = waitingTime;
        ServerModel.roundLength = roundLength;

        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/main/java/.config"));
            String newConfiguration = "";
            String line;
            while ((line = reader.readLine()) != null){
                if (line.contains("waitingTime")){
                    newConfiguration+="waitingTime = " + waitingTime + "\n";
                }else if (line.contains("roundLength")){
                    newConfiguration+="roundLength = " + roundLength + "\n";
                }else {
                    newConfiguration+=line + "\n";
                }
            }
            reader.close();

            PrintWriter writer = new PrintWriter(new FileWriter("src/main/java/.config"), true);
            writer.write(newConfiguration);
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getRoundLength() {
        return roundLength;
    }

    public void setRoundLength(int roundLength) {
        this.roundLength = roundLength;
    }
}
