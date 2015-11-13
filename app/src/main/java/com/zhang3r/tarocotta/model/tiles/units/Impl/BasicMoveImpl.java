package com.zhang3r.tarocotta.model.tiles.units.Impl;

import android.content.res.Resources;
import android.graphics.Bitmap;

import com.zhang3r.tarocotta.bitmaps.AnimatedSprite;
import com.zhang3r.tarocotta.bitmaps.spriteFactory.SpriteFactory;
import com.zhang3r.tarocotta.constants.IAppConstants;
import com.zhang3r.tarocotta.model.army.Army;
import com.zhang3r.tarocotta.model.maps.Map;
import com.zhang3r.tarocotta.model.tiles.statsFactory.impl.Point;
import com.zhang3r.tarocotta.model.tiles.units.BaseUnit;
import com.zhang3r.tarocotta.model.tiles.units.Interface.Move;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by Zhang3r on 6/12/2015.
 */
public class BasicMoveImpl implements Move {

    public BasicMoveImpl() {
    }


    public List<AnimatedSprite> getMoveTiles(BaseUnit unit, Army army, Army enemyArmy, Resources resources) {
        int movePoints = unit.getStats().getMovePoints();
        int x = unit.getX();
        int y = unit.getY();
        int mapLengthY = Map.getMap().getGrid().length;
        int mapLengthX = Map.getMap().getGrid()[0].length;

        List<AnimatedSprite> spriteList = new ArrayList<>();

        Bitmap moveTile = SpriteFactory.getInstance().getTiles(false);
        LinkedList<Point> points = new LinkedList<>();
        points.add(new Point(x, y));
        HashMap<String, Integer> seen = new HashMap<>();
        int i = 0;

        while (!points.isEmpty() && i <= movePoints) {
            int list_size = points.size();
            while (list_size > 0) {
                Point point = points.poll();
                list_size--;
                //check if point is enemy
                seen.put(point.toString(), 1);
                if (enemyArmy.hasUnitAtLocation(point.getX(), point.getY())) {
                    continue;
                } else {
                    if (!army.hasUnitAtLocation(point.getX(), point.getY())) {

                        //we are terrain!
                        spriteList.add(AnimatedSprite.create(moveTile,
                                IAppConstants.SPRITE_HEIGHT,
                                IAppConstants.SPRITE_WIDTH, 1, 1, false, point.getX(), point.getY()));
                    }
                    //add generate neighbors
                    List<Point> neighbors = new ArrayList<>();
                    neighbors.add(new Point(point.getX() + 1, point.getY()));
                    neighbors.add(new Point(point.getX() - 1, point.getY()));
                    neighbors.add(new Point(point.getX(), point.getY() + 1));
                    neighbors.add(new Point(point.getX(), point.getY() - 1));

                    // dont add if we already seen the point and if its out of bounds
                    for(Point neighbor: neighbors){
                        if(!seen.containsKey(neighbor.toString())){
                            if(neighbor.getX()>=0 && neighbor.getX()<=mapLengthX){
                                if(neighbor.getY()>=0&& neighbor.getY()<=mapLengthY){
                                    points.add(neighbor);
                                }
                            }

                        }
                    }

                }
            }
            i++;

        }
        return spriteList;
    }

    /**
     * return true when we have reached our target
     *
     * @param spriteList
     * @param playerArmy
     * @param enemyArmy
     * @param eventX
     * @param eventY
     * @return
     */
    public boolean unitMoveUpdate(List<AnimatedSprite> spriteList, Army playerArmy, Army enemyArmy, double eventX, double eventY) {
        return false;
    }

    //Todo: add impassible terrain

    /**
     * finds the x and y to update for the path.
     *
     * @param target
     * @param playerArmy
     * @param enemeyArmy
     * @return
     */
    public int[] findNextMove(MoveTarget target, Army playerArmy, Army enemeyArmy) {
        return null;
    }

}
