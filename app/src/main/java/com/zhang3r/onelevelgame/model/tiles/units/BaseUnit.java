package com.zhang3r.onelevelgame.model.tiles.units;

import android.content.res.Resources;

import com.zhang3r.onelevelgame.bitmaps.AnimatedSprite;
import com.zhang3r.onelevelgame.constants.IAppConstants;
import com.zhang3r.onelevelgame.constants.IGameConstants.UnitType;
import com.zhang3r.onelevelgame.constants.IGameConstants.UnitState;
import com.zhang3r.onelevelgame.model.army.Army;
import com.zhang3r.onelevelgame.model.sprite.Tile;
import com.zhang3r.onelevelgame.model.tiles.units.Impl.BasicMoveImpl;
import com.zhang3r.onelevelgame.model.tiles.units.Impl.MeleeAttackImpl;
import com.zhang3r.onelevelgame.model.tiles.units.Interface.Attack;
import com.zhang3r.onelevelgame.model.tiles.units.Interface.Move;

import java.util.List;

public abstract class BaseUnit extends Tile {
    //unit stats
    private int unitId;
    private int hitPoints;
    private int attack;
    private int defense;
    private int movePoints;
    private int maxAttackRange;
    private int minAttackRange;
    private String name;
    //unit location
    private int x;
    private int y;
    // unit attributes
    private boolean unitSelected;
    private UnitState state;
    private AnimatedSprite sprite;
    //utility
    private Attack attackUtil;
    private Move moveUtil;



    private UnitType unitType;


    public BaseUnit() {
        unitId = (int) Math.random() * 100;
        hitPoints = 20;
        attack = 0;
        defense = 0;
        movePoints = 5;
        maxAttackRange = 0;
        minAttackRange = 0;
        name = "Basic Unit " + unitId;
        state = UnitState.NORMAL;
        attackUtil = new MeleeAttackImpl();
        moveUtil = new BasicMoveImpl();
    }

    public List<AnimatedSprite> getUnitMoveTiles(int mapLengthX, int mapLengthY,Army army, Army enemyArmy,
                                                 Resources resources) {
        setUnitSelected(true);
        return moveUtil.getMoveTiles(mapLengthX, mapLengthY, x, y, army, enemyArmy, resources, getUnitId(), getMovePoints());

    }


    public List<AnimatedSprite> getUnitAttackTiles(int xLength, int yLength,
                                                   Resources resources) {
        setUnitSelected(true);
        return attackUtil.getUnitAttackTiles(getUnitId(), xLength, yLength, resources, getX(), getY(), maxAttackRange, minAttackRange);

    }

    public boolean unitMoveUpdate(List<AnimatedSprite> spriteList,
                                  Army playerArmy, Army enemyArmy, double eventX, double eventY) {

        if (unitSelected && moveUtil.unitMoveUpdate(spriteList, playerArmy, enemyArmy, eventX, eventY)) {
            unitSelected = false;
            setX((int) eventX / IAppConstants.SPRITE_WIDTH);
            setY((int) eventY / IAppConstants.SPRITE_HEIGHT);

            getSprite().setXPos( getX()*IAppConstants.SPRITE_HEIGHT);
            getSprite().setYPos( getY()*IAppConstants.SPRITE_WIDTH);
            setUnitSelected(false);
            return true;
        }
        return false;

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

    public int getMovePoints() {
        return movePoints;
    }

    public void setMovePoints(int movePoints) {
        this.movePoints = movePoints;
    }

    public int getMaxAttackRange() {
        return maxAttackRange;
    }

    public void setMaxAttackRange(int maxAttackRange) {
        this.maxAttackRange = maxAttackRange;
    }

    public int getMinAttackRange() {
        return minAttackRange;
    }

    public void setMinAttackRange(int minAttackRange) {
        this.minAttackRange = minAttackRange;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isUnitSelected() {
        return unitSelected;
    }

    public void setUnitSelected(boolean unitSelected) {
        this.unitSelected = unitSelected;
    }

    public UnitState getState() {
        return state;
    }

    public void setState(UnitState state) {
        this.state = state;
    }

    @Override
    public AnimatedSprite getSprite() {
        return sprite;
    }

    @Override
    public void setSprite(AnimatedSprite sprite) {
        this.sprite = sprite;
    }

    public Attack getAttackUtil() {
        return attackUtil;
    }

    public void setAttackUtil(Attack attackUtil) {
        this.attackUtil = attackUtil;
    }

    public Move getMoveUtil() {
        return moveUtil;
    }

    public void setMoveUtil(Move moveUtil) {
        this.moveUtil = moveUtil;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public void setUnitType(UnitType unitType) {
        this.unitType = unitType;
    }
}
