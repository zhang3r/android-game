package com.zhang3r.tarocotta.model.tiles.units;

import android.content.res.Resources;

import com.zhang3r.tarocotta.bitmaps.AnimatedSprite;
import com.zhang3r.tarocotta.constants.IAppConstants;
import com.zhang3r.tarocotta.constants.IGameConstants.UnitType;
import com.zhang3r.tarocotta.constants.IGameConstants.UnitState;
import com.zhang3r.tarocotta.model.army.Army;
import com.zhang3r.tarocotta.model.sprite.Tile;
import com.zhang3r.tarocotta.model.tiles.statsFactory.StatsFactory;
import com.zhang3r.tarocotta.model.tiles.statsFactory.impl.Stats;
import com.zhang3r.tarocotta.model.tiles.statsFactory.impl.StatsFactoryImpl;
import com.zhang3r.tarocotta.model.tiles.units.Impl.BasicMoveImpl;
import com.zhang3r.tarocotta.model.tiles.units.Impl.MeleeAttackImpl;
import com.zhang3r.tarocotta.model.tiles.units.Interface.Attack;
import com.zhang3r.tarocotta.model.tiles.units.Interface.Move;

import java.util.List;

public abstract class BaseUnit extends Tile {
    //unit stats
    private int unitId;
    private Stats stats;
    private String name;
    //unit location
    private int x;
    private int y;
    // unit attributes
    private boolean unitSelected;
    private UnitState state;
    //utility
    private Attack attackUtil;
    private Move moveUtil;



    private UnitType unitType;


    public BaseUnit() {
        super();
        unitId = (int) Math.random() * 100;
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
        return attackUtil.getUnitAttackTiles(getUnitId(), xLength, yLength, resources, getX(), getY(), stats.getMaxAttackRange(), stats.getMinAttackRange());

    }

    public boolean unitMoveUpdate(List<AnimatedSprite> spriteList,
                                  Army playerArmy, Army enemyArmy, double eventX, double eventY) {

        if (unitSelected && moveUtil.unitMoveUpdate(spriteList, playerArmy, enemyArmy, eventX, eventY)) {

            setX((int) eventX / IAppConstants.SPRITE_WIDTH);
            setY((int) eventY / IAppConstants.SPRITE_WIDTH);

//            getSprite().setXPos( getX()*IAppConstants.SPRITE_WIDTH);
//            getSprite().setYPos( getY()*IAppConstants.SPRITE_WIDTH);
            setUnitSelected(false);
            return true;
        }
        return false;

    }

    public abstract String getDescription();


    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public int getHitPoints() {
        return stats.getHitPoints();
    }

    public void setHitPoints(int hitPoints) {
        stats.setHitPoints(hitPoints);
    }

    public int getAttack() {
        return stats.getAttack();
    }

    public void setAttack(int attack) {
        stats.setAttack(attack);
    }

    public int getDefense() {
       return stats.getDefense();
    }

    public void setDefense(int defense) {
        stats.setDefense(defense);
    }

    public int getMovePoints() {
        return stats.getMovePoints();
    }

    public void setMovePoints(int movePoints) {
        stats.setMovePoints(movePoints);
    }

    public int getMaxAttackRange() {
        return stats.getMaxAttackRange();
    }

    public void setMaxAttackRange(int maxAttackRange) {
        stats.setMaxAttackRange(maxAttackRange);
    }

    public int getMinAttackRange() {
        return stats.getMinAttackRange();
    }

    public void setMinAttackRange(int minAttackRange) {
        stats.setMinAttackRange(minAttackRange);
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

    public int getMaxHP() {
        return stats.getMaxHP();
    }

    public void setMaxHP(int maxHP) {
        stats.setMaxHP(maxHP);
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }
}
