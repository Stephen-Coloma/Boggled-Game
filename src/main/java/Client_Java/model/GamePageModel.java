package Client_Java.model;

import Client_Java.BoggledApp.Round;

public class GamePageModel {
    private int gid;
    private Round round;

    public GamePageModel(int gid, Round round) {
        this.gid = gid;
        this.round = round;
    }

    public int getGid() {
        return gid;
    }

    public Round getRound() {
        return round;
    }
} // end of GamePageModel class
