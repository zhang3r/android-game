package com.zhang3r.onelevelgame.model.tiles.units.Impl;

import com.zhang3r.onelevelgame.bitmaps.AnimatedSprite;
import com.zhang3r.onelevelgame.constants.IAppConstants;
import com.zhang3r.onelevelgame.constants.IGameConstants;

/**
 * Created by Zhang3r on 6/12/2015.
 */
public class Stats {
    private int unitId;
    private int hitPoints;
    private int x;
    private int y;
    private int movePoints;
    private boolean unitSelected;
    private IGameConstants.UnitState state;
    private String name;
    private int attackRange;
    private int mp;
    private int attack;
    private int defense;
    private int evasion;
    private int armor;
    private int luck;
    private int morale;
    private int skill;
    private int magic;
    private int resistance;
    private int speed;
    private int strength;
    private AnimatedSprite sprite;



    public Stats(){
        this.unitId = (int)(Math.random()*100);
        this.hitPoints = 20;
        this.movePoints = 5;
        this.attackRange = 1;
        this.attack = 1;
        this.defense = 1;
        this.evasion = 1;
        this.armor = 0;
        this.luck = 1;
        this.morale = 1;
        this.skill = 1;
        this.magic = 1;
        this.resistance = 1;
        this.speed = 1;
        this.strength = 1;
        this.x = (int)(Math.random()* IAppConstants.AXIS_X_MAX);
        this.y = (int)(Math.random()* IAppConstants.AXIS_Y_MAX);;
        this.name = "";
        this.state = IGameConstants.UnitState.NORMAL;
    }

    public Stats(int unitId, String name,
                 int hitPoints, int movePoints, int attackRange, int attack, int defense, int x, int y){
        this.unitId = unitId;
        this.hitPoints = hitPoints;
        this.movePoints = movePoints;
        this.attackRange = attackRange;
        this.attack = attack;
        this.defense = defense;
        this.evasion = 1;
        this.armor = 0;
        this.luck = 1;
        this.morale = 1;
        this.skill = 1;
        this.magic = 1;
        this.resistance = 1;
        this.speed = 1;
        this.strength = 1;
        this.x = x;
        this.y = y;
        this.name = name;
        this.state = IGameConstants.UnitState.NORMAL;

    }

    public Stats(int unitId, AnimatedSprite unitSprite, String name, int hitPoints,int movePoints,int x,int y){
        this();
        this.unitId = unitId;
        this.sprite= unitSprite;
        this.name= name;
        this.hitPoints=hitPoints;
        this.movePoints=movePoints;
        this.x=x;
        this.y=y;
    }

    //TODO
    public int calculateDamage(){
        return -1;
    }
    //TODO
    public int claculateCollateralDamage(){
        return  -1;
    }

    //TODO
    public String printStats(){
        return null;
    }




    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getMovePoints() {
        return movePoints;
    }

    public void setMovePoints(int movePoints) {
        this.movePoints = movePoints;
    }

    public boolean isUnitSelected() {
        return unitSelected;
    }

    public void setUnitSelected(boolean unitSelected) {
        this.unitSelected = unitSelected;
    }

    public IGameConstants.UnitState getState() {
        return state;
    }

    public void setState(IGameConstants.UnitState state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getEvasion() {
        return evasion;
    }

    public void setEvasion(int evasion) {
        this.evasion = evasion;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getLuck() {
        return luck;
    }

    public void setLuck(int luck) {
        this.luck = luck;
    }

    public int getMorale() {
        return morale;
    }

    public void setMorale(int morale) {
        this.morale = morale;
    }

    public int getSkill() {
        return skill;
    }

    public void setSkill(int skill) {
        this.skill = skill;
    }

    public int getMagic() {
        return magic;
    }

    public void setMagic(int magic) {
        this.magic = magic;
    }

    public int getResistance() {
        return resistance;
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public AnimatedSprite getSprite() {
        return sprite;
    }

    public void setSprite(AnimatedSprite sprite) {
        this.sprite = sprite;
    }

}
