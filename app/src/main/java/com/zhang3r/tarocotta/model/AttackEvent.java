package com.zhang3r.tarocotta.model;

import android.util.Log;

import com.zhang3r.tarocotta.constants.IGameConstants;
import com.zhang3r.tarocotta.constants.ILogConstants;
import com.zhang3r.tarocotta.model.army.Army;
import com.zhang3r.tarocotta.model.tiles.units.BaseUnit;
import com.zhang3r.tarocotta.model.tiles.units.Impl.RangedAttackImpl;

public class AttackEvent {
    BaseUnit attacker;
    BaseUnit defender;
    public AttackEvent(BaseUnit attacker, BaseUnit defender) {
        this.attacker = attacker;
        this.defender = defender;
    }
    public void calcuateDMG(){
        //get terrain
        //calculate dmg
        //apply dmg
    }
}
