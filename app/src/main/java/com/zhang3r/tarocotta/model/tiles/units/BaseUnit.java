package com.zhang3r.tarocotta.model.tiles.units;

import android.content.res.Resources;

import com.zhang3r.tarocotta.bitmaps.AnimatedSprite;
import com.zhang3r.tarocotta.constants.IAppConstants;
import com.zhang3r.tarocotta.constants.IGameConstants.UnitType;
import com.zhang3r.tarocotta.constants.IGameConstants.UnitState;
import com.zhang3r.tarocotta.model.army.Army;
import com.zhang3r.tarocotta.model.sprite.Tile;
import com.zhang3r.tarocotta.model.tiles.statsFactory.StatsFactory;
import com.zhang3r.tarocotta.model.tiles.statsFactory.impl.Point;
import com.zhang3r.tarocotta.model.tiles.statsFactory.impl.Stats;
import com.zhang3r.tarocotta.model.tiles.statsFactory.impl.StatsFactoryImpl;
import com.zhang3r.tarocotta.model.tiles.units.Impl.BasicMoveImpl;
import com.zhang3r.tarocotta.model.tiles.units.Impl.MeleeAttackImpl;
import com.zhang3r.tarocotta.model.tiles.units.Interface.Attack;
import com.zhang3r.tarocotta.model.tiles.units.Interface.Move;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BaseUnit{

    private String id;
    private AnimatedSprite animation;
    private Stats stats;
    private List<Integer> items;
    private Point position;
    private UnitState unitState;
    private String unitDescription;
    private String characterDescription;



    private Attack attackUtil;
    private Move moveUtil;
    private Direction direction;

    public enum Direction{
        UP(3),DOWN(4),LEFT(1),RIGHT(2);
        private final int mask;
        private Direction(int mask)
        {
            this.mask = mask;
        }
        public int getMask()
        {
            return mask;
        }
    }

    public BaseUnit(UnitType type){
        this.id = UUID.randomUUID().toString();
        this.stats = StatsFactoryImpl.createStat(type);
        this.unitState = UnitState.NORMAL;
        this.position = new Point(0,0);
        this.items = new ArrayList<>();
        this.direction =Direction.LEFT;
        this.moveUtil = new BasicMoveImpl();
        this.attackUtil = new MeleeAttackImpl();
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public List<Integer> getItems() {
        return items;
    }

    public void setItems(List<Integer> items) {
        this.items = items;
    }


    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }


    public UnitState getUnitState() {
        return unitState;
    }

    public void setUnitState(UnitState unitState) {
        this.unitState = unitState;
    }

    public String getUnitDescription() {
        return unitDescription;
    }

    public void setUnitDescription(String unitDescription) {
        this.unitDescription = unitDescription;
    }

    public String getCharacterDescription() {
        return characterDescription;
    }

    public void setCharacterDescription(String characterDescription) {
        this.characterDescription = characterDescription;
    }
    public Attack getAttackUtil() {
        return attackUtil;
    }

    public void setAttackUtil(Attack attackUtil) {
        this.attackUtil = attackUtil;
    }

    public AnimatedSprite getAnimation() {
        return animation;
    }

    public void setAnimation(AnimatedSprite animation) {
        this.animation = animation;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Move getMoveUtil() {
        return moveUtil;
    }

    public void setMoveUtil(Move moveUtil) {
        this.moveUtil = moveUtil;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
