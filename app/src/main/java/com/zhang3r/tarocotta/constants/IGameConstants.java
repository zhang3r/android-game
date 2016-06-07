package com.zhang3r.tarocotta.constants;

public interface IGameConstants {

       String PLAYER = "player";
       String ENEMY = "enemy";

     enum UnitType {
        CAV, FOOT, ARCHER
    }

     enum UnitState {
        NORMAL, SELECTED,  MOVED, ATTACK,  ANIMATION, WAIT, MOVE_ANIMATION, DEAD
    }


     enum TurnState {
        PLAYER, ENEMY
    }

    enum GameState{
        NORMAL, UNIT_SELECTED, UNIT_IN_ANIMATION, UNIT_ATTACK_SELECT,
    }

    enum AnimationState{
        FACE_LEFT(0),
        FACE_RIGHT(1),
        FACE_UP(0),
        FACE_DOWN(1),
        FIGHT_LEFT(0),
        FIGHT_RIGHT(1),
        FIGHT_UP(0),
        FIGHT_DOWN(1),
        DMG_RIGHT(0),
        DMG_LEFT(1),
        DMG_UP(0),
        DMG_DOWN(1);
        private int value;
        AnimationState(int value){this.value=value;}

        public int getValue(){
            return value;
        }
    }


}
