package com.zhang3r.tarocotta.constants;

public interface IGameConstants {

       String PLAYER = "player";
       String ENEMY = "enemy";

     enum UnitType {
        CAV, FOOT, ARCHER
    }

     enum UnitState {
        NORMAL, SELECTED,  MOVED, ATTACK,  ANIMATION, WAIT, DEAD
    }


     enum TurnState {
        PLAYER, ENEMY
    }

    enum GameState{
        NORMAL, UNITSELECTED, UNITINANIMATION, UNITATTACKSELECT,
    }




}
