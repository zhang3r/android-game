package com.zhang3r.onelevelgame.model;

import android.util.Log;

import com.zhang3r.onelevelgame.constants.IGameConstants;
import com.zhang3r.onelevelgame.constants.ILogConstants;
import com.zhang3r.onelevelgame.model.army.Army;
import com.zhang3r.onelevelgame.model.tiles.units.BaseUnit;

public class AttackEvent {
    private int damageDelt;
    private int recoil;

    public int getDamageDelt() {
        return damageDelt;
    }

    public void setDamageDelt(int damageDelt) {
        this.damageDelt = damageDelt;
    }

    public int getRecoil() {
        return recoil;
    }

    public void setRecoil(int recoil) {
        this.recoil = recoil;
    }

    public static AttackEvent attack(BaseUnit attacker, BaseUnit defender, Army army, Army enemyUnits) {

        defender.setHitPoints(defender.getHitPoints() - 20);
        attacker.setHitPoints(attacker.getHitPoints() - 5);
        AttackEvent ae = new AttackEvent();
        ae.setDamageDelt(20);
        ae.setRecoil(5);
        Log.d(ILogConstants.DEBUG_TAG,attacker.getName()+" attacked "+defender.getName());
        if (defender.getHitPoints() <= 0
                || attacker.getHitPoints() <= 0) {
            synchronized (army) {
                synchronized (enemyUnits) {
                    if (defender.getHitPoints() <= 0) {
                        defender.setState(IGameConstants.UnitState.DEAD);
                        Log.d(ILogConstants.DEBUG_TAG,  defender.getName()+" died in battle");
                        enemyUnits.remove(defender);
                    }
                    if (attacker.getHitPoints() <= 0) {
                        //army.remove(this);
                        Log.d(ILogConstants.DEBUG_TAG,  attacker.getName()+" died in battle");
                        attacker.setState(IGameConstants.UnitState.DEAD);
                    }
                }
            }


        }
        return ae;
    }

}
