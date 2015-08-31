package com.zhang3r.tarocotta.model;

import android.util.Log;

import com.zhang3r.tarocotta.constants.IGameConstants;
import com.zhang3r.tarocotta.constants.ILogConstants;
import com.zhang3r.tarocotta.model.army.Army;
import com.zhang3r.tarocotta.model.tiles.units.BaseUnit;
import com.zhang3r.tarocotta.model.tiles.units.Impl.RangedAttackImpl;

public class AttackEvent {
    private int damageDelt;
    private int recoil;
    private StringBuilder details;

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

    public AttackEvent(){
        details= new StringBuilder();
    }

    public static AttackEvent attack(BaseUnit attacker, BaseUnit defender, Army army, Army enemyUnits) {
        int damage =attacker.getAttack()-defender.getDefense();
        int recoil =damage/5;
        boolean isRanged = attacker.getAttackUtil().getClass().isInstance(RangedAttackImpl.class);
        if(isRanged){
            recoil=0;
        }
        defender.setHitPoints(defender.getHitPoints() - damage);
        attacker.setHitPoints(attacker.getHitPoints() - recoil);

        AttackEvent ae = new AttackEvent();
        ae.setDamageDelt(damage);
        ae.setRecoil(recoil);
        Log.d(ILogConstants.DEBUG_TAG, attacker.getName() + " attacked " + defender.getName());
        ae.details.append(attacker.getName()+" dealt "+damage+ " damage to "+defender.getName()+" and took "+recoil+"damage in recoil");
        if (defender.getHitPoints() <= 0
                || attacker.getHitPoints() <= 0) {
            synchronized (army) {
                synchronized (enemyUnits) {
                    if (defender.getHitPoints() <= 0) {
                        defender.setState(IGameConstants.UnitState.DEAD);
                        Log.d(ILogConstants.DEBUG_TAG, defender.getName() + " died in battle");
                        ae.details.append("\n");
                        ae.details.append( defender.getName() + " died in battle");
                        enemyUnits.remove(defender);
                    }
                    if (attacker.getHitPoints() <= 0) {
                        //army.remove(this);
                        Log.d(ILogConstants.DEBUG_TAG,  attacker.getName()+" died in battle");
                        ae.details.append("\n");
                        ae.details.append( defender.getName() + " died in battle");
                        attacker.setState(IGameConstants.UnitState.DEAD);
                    }
                }
            }


        }
        return ae;
    }
    @Override
    public String toString(){
        return details.toString();
    }

}
