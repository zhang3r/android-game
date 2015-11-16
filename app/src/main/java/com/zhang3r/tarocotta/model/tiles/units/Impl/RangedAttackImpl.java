package com.zhang3r.tarocotta.model.tiles.units.Impl;

import android.content.res.Resources;
import android.graphics.Bitmap;

import com.zhang3r.tarocotta.bitmaps.AnimatedSprite;
import com.zhang3r.tarocotta.bitmaps.spriteFactory.SpriteFactory;
import com.zhang3r.tarocotta.constants.IAppConstants;
import com.zhang3r.tarocotta.model.maps.Map;
import com.zhang3r.tarocotta.model.tiles.statsFactory.impl.Point;
import com.zhang3r.tarocotta.model.tiles.units.BaseUnit;
import com.zhang3r.tarocotta.model.tiles.units.Interface.Attack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Zhang3r on 6/14/2015.
 */
public class RangedAttackImpl implements Attack {
    public RangedAttackImpl() {
    }

    public List<AnimatedSprite> getUnitAttackTiles(BaseUnit unit, int xLength, int yLength, Resources resources) {
        int attackRange = unit.getStats().getMaxAttackRange();

        int x = unit.getX();
        int y = unit.getY();
        int mapLengthY = Map.getMap().getGrid().length;
        int mapLengthX = Map.getMap().getGrid()[0].length;

        List<AnimatedSprite> spriteList = new ArrayList<>();

        Bitmap attackTile = SpriteFactory.getInstance().getTiles(false);
        LinkedList<Point> points = new LinkedList<>();
        points.add(new Point(x, y));
        HashMap<String, Integer> seen = new HashMap<>();
        int i = 0;

        while (!points.isEmpty() && i <= attackRange) {
            int list_size = points.size();
            while (list_size > 0) {
                Point point = points.poll();
                spriteList.add(AnimatedSprite.create(attackTile,
                        IAppConstants.SPRITE_HEIGHT,
                        IAppConstants.SPRITE_WIDTH, 1, 1, false, point.getX(), point.getY()));
                list_size--;
                //check if point is enemy
                seen.put(point.toString(), 1);

                //add generate neighbors
                List<Point> neighbors = new ArrayList<>();
                neighbors.add(new Point(point.getX() + 1, point.getY()));
                neighbors.add(new Point(point.getX() - 1, point.getY()));
                neighbors.add(new Point(point.getX(), point.getY() + 1));
                neighbors.add(new Point(point.getX(), point.getY() - 1));

                // dont add if we already seen the point and if its out of bounds
                for (Point neighbor : neighbors) {
                    if (!seen.containsKey(neighbor.toString())) {
                        if (neighbor.getX() >= 0 && neighbor.getX() <= mapLengthX) {
                            if (neighbor.getY() >= 0 && neighbor.getY() <= mapLengthY) {
                                seen.put(neighbor.toString(), 1);
                                points.add(neighbor);
                            }
                        }

                    }

                }
                i++;

            }
        }
        return spriteList;
    }
}
