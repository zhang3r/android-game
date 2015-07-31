package com.zhang3r.onelevelgame.constants;

import android.content.res.Resources;

public interface IGameConstants {
    public final static String FRIENDLY = "friendly";
    public final static String PLAYER = "player";
    public final static String ENEMY = "enemy";

    public enum UnitType {
        CAV, FOOT, ARCHER
    }

    public enum UnitState {
        NORMAL, MOVED, DEAD, WAIT, SELECTED
    }


    public enum TurnState {
        PLAYER, ENEMY
    }


}
