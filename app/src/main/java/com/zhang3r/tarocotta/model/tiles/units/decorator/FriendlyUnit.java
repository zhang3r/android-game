package com.zhang3r.tarocotta.model.tiles.units.decorator;

import android.content.res.Resources;

import com.zhang3r.tarocotta.bitmaps.AnimatedSprite;
import com.zhang3r.tarocotta.constants.IGameConstants;
import com.zhang3r.tarocotta.model.army.Army;
import com.zhang3r.tarocotta.model.tiles.units.BaseUnit;
import com.zhang3r.tarocotta.model.tiles.units.Interface.Attack;
import com.zhang3r.tarocotta.model.tiles.units.Interface.Move;

import java.util.List;

/**
 * Created by Zhang3r on 8/11/2015.
 */
public class FriendlyUnit extends BaseUnit {
    private BaseUnit unit;

    public FriendlyUnit(BaseUnit unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return unit.toString() + " This unit is friendly to your forces.";
    }

    public List<AnimatedSprite> getUnitMoveTiles(int mapLengthX, int mapLengthY, Army army, Army enemyArmy,
                                                 Resources resources) {
        return unit.getUnitMoveTiles(mapLengthX, mapLengthY, army, enemyArmy,
                resources);

    }


    public List<AnimatedSprite> getUnitAttackTiles(int xLength, int yLength,
                                                   Resources resources) {
        return unit.getUnitAttackTiles(xLength, yLength, resources);

    }

    public boolean unitMoveUpdate(List<AnimatedSprite> spriteList,
                                  Army playerArmy, Army enemyArmy, double eventX, double eventY) {

        return unit.unitMoveUpdate(spriteList, playerArmy, enemyArmy, eventX, eventY);

    }


    public int getUnitId() {
        return unit.getUnitId();
    }

    public void setUnitId(int unitId) {
        unit.setUnitId(unitId);
    }

    public int getHitPoints() {
        return unit.getHitPoints();
    }

    public void setHitPoints(int hitPoints) {
        unit.setHitPoints(hitPoints);
    }

    public int getAttack() {
        return unit.getAttack();
    }

    public void setAttack(int attack) {
        unit.setAttack(attack);
    }

    public int getDefense() {
        return unit.getDefense();
    }

    public void setDefense(int defense) {
        unit.setDefense(defense);
    }

    public int getMovePoints() {
        return unit.getMovePoints();
    }

    public void setMovePoints(int movePoints) {
        unit.setMovePoints(movePoints);
    }

    public int getMaxAttackRange() {
        return unit.getMaxAttackRange();
    }

    public void setMaxAttackRange(int maxAttackRange) {
        unit.setMaxAttackRange(maxAttackRange);
    }

    public int getMinAttackRange() {
        return unit.getMinAttackRange();
    }

    public void setMinAttackRange(int minAttackRange) {
        unit.setMinAttackRange(minAttackRange);
    }

    public String getName() {
        return unit.getName();
    }

    public void setName(String name) {
        unit.setName(name);
    }

    public int getX() {
        return unit.getX();
    }

    public void setX(int x) {
        unit.setX(x);
    }

    public int getY() {
        return unit.getY();
    }

    public void setY(int y) {
        unit.setY(y);
    }

    public boolean isUnitSelected() {
        return unit.isUnitSelected();
    }

    public void setUnitSelected(boolean unitSelected) {
        unit.setUnitSelected(unitSelected);
    }

    public IGameConstants.UnitState getState() {
        return unit.getState();
    }

    public void setState(IGameConstants.UnitState state) {
        unit.setState(state);
    }

    @Override
    public AnimatedSprite getSprite() {
        return unit.getSprite();
    }

    @Override
    public void setSprite(AnimatedSprite sprite) {
        unit.setSprite(sprite);
    }

    public Attack getAttackUtil() {
        return unit.getAttackUtil();
    }

    public void setAttackUtil(Attack attackUtil) {
        unit.setAttackUtil(attackUtil);
    }

    public Move getMoveUtil() {
        return unit.getMoveUtil();
    }

    public void setMoveUtil(Move moveUtil) {
        unit.setMoveUtil(moveUtil);
    }

    public IGameConstants.UnitType getUnitType() {
        return unit.getUnitType();
    }

    public void setUnitType(IGameConstants.UnitType unitType) {
        unit.setUnitType(unitType);
    }

    public int getMaxHP() {
        return unit.getMaxHP();
    }

    public void setMaxHP(int maxHP) {
        unit.setMaxHP(maxHP);
    }
}
