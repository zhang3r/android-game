package com.zhang3r.tarocotta.constants;

public interface IGameConstants {

       String PLAYER = "player";
       String ENEMY = "enemy";

     enum UnitType {
        CAV, FOOT, ARCHER
    }

     enum UnitState {
        NORMAL, MOVED, DEAD, WAIT, SELECTED
    }


     enum TurnState {
        PLAYER, ENEMY
    }

    enum GameState{
        NORMAL, UNITSELECTED, UNITINANIMATION, UNITATTACKSELECT,
    }




}
