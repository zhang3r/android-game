package com.zhang3r.onelevelgame.constants;

public interface IGameConstants {
    public final static String FRIENDLY = "friendly";
    public final static String PLAYER = "player";
    public final static String ENEMY = "enemy";

    public enum UnitState {
        NORMAL, MOVED, DEAD, WAIT
    }

    ;

    public enum TurnState {
        PLAYER, ENEMY
    }

    ;

}
