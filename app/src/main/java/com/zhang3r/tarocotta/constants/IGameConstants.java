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
        FACE_UP(2),
        FACE_DOWN(3),
        FIGHT_LEFT(4),
        FIGHT_RIGHT(5),
        FIGHT_UP(6),
        FIGHT_DOWN(7),
        DMG_RIGHT(8),
        DMG_LEFT(9),
        DMG_UP(10),
        DMG_DOWN(11);
        private int value;
        private AnimationState(int value){this.value=value;}

        public int getValue(){
            return value;
        }
    }


}
