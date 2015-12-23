package com.zhang3r.tarocotta.model.tiles.units.Impl;

import android.content.res.Resources;
import android.graphics.Bitmap;

import com.zhang3r.tarocotta.bitmaps.AnimatedSprite;
import com.zhang3r.tarocotta.bitmaps.spriteFactory.SpriteFactory;
import com.zhang3r.tarocotta.constants.IAppConstants;
import com.zhang3r.tarocotta.model.army.Army;
import com.zhang3r.tarocotta.model.maps.GameMap;
import com.zhang3r.tarocotta.model.tiles.statsFactory.impl.Point;
import com.zhang3r.tarocotta.model.tiles.units.BaseUnit;
import com.zhang3r.tarocotta.model.tiles.units.Interface.Move;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * Created by Zhang3r on 6/12/2015.
 */
public class BasicMoveImpl implements Move {

    public BasicMoveImpl() {
    }


    public List<AnimatedSprite> getMoveTiles(BaseUnit unit, Army army, Army enemyArmy, Resources resources) {
        int movePoints = unit.getStats().getMovePoints();
        int x = unit.getPosition().getX();
        int y = unit.getPosition().getY();
        int mapLengthY = GameMap.getGameMap().getGrid().length;
        int mapLengthX = GameMap.getGameMap().getGrid()[0].length;

        List<AnimatedSprite> spriteList = new ArrayList<>();

        Bitmap moveTile = SpriteFactory.getInstance().getTiles(false);
        LinkedList<Point> points = new LinkedList<>();
        Point origin = new Point(x, y);
        points.add(origin);
        HashMap<String, Integer> seen = new HashMap<>();
        seen.put(origin.toString(),0);
        int i = 0;

        while (!points.isEmpty() && i <= movePoints) {
            int list_size = points.size();
            while (list_size > 0) {
                Point point = points.poll();
                list_size--;
                //check if point is enemy
                int cost = GameMap.getGameMap().getGrid()[point.getX()][point.getY()];
                if (enemyArmy.hasUnitAtLocation(point.getX(), point.getY())) {
                    continue;
                } else {
                    if (!army.hasUnitAtLocation(point.getX(), point.getY())&& cost<=movePoints) {
                        //we are terrain!

                        spriteList.add(AnimatedSprite.create(moveTile,
                                IAppConstants.SPRITE_HEIGHT,
                                IAppConstants.SPRITE_WIDTH, 1, 1, false, point.getX(), point.getY()));
                    }
                    //add generate neighbors


                    // dont add if we already seen the point and if its out of bounds
                    for(Point neighbor: getNeighbors(point)){

                        if(!seen.containsKey(neighbor.toString())){
                            if(neighbor.getX()>=0 && neighbor.getX()<mapLengthX){
                                if(neighbor.getY()>=0&& neighbor.getY()<mapLengthY){
                                    int costTile = GameMap.getGameMap().getGrid()[neighbor.getX()][neighbor.getY()];
                                    seen.put(neighbor.toString(), cost+costTile);
                                    points.add(neighbor);
                                }
                            }
                        }else{
                           int costTile = GameMap.getGameMap().getGrid()[neighbor.getX()][neighbor.getY()];
                           if(seen.get(neighbor.toString())> costTile+cost){
                               seen.put(neighbor.toString(), cost+costTile);
                           }

                        }
                    }

                }
            }
            i++;

        }
        return spriteList;
    }
    private List<Point> getNeighbors(Point point){
        List<Point> neighbors = new ArrayList<>();
        neighbors.add(new Point(point.getX() + 1, point.getY()));
        neighbors.add(new Point(point.getX() - 1, point.getY()));
        neighbors.add(new Point(point.getX(), point.getY() + 1));
        neighbors.add(new Point(point.getX(), point.getY() - 1));
        return neighbors;
    }

    /**
     * return true when we should move
     *
     * @param spriteList
     * @param playerArmy
     * @param enemyArmy
     * @param eventX
     * @param eventY
     * @return
     */
    public boolean unitMoveUpdate(List<AnimatedSprite> spriteList, Army playerArmy, Army enemyArmy, double dX, double dY) {
        return false;
    }

    @Override
    public List<Point> findPath(List<AnimatedSprite> moveList, Point unitDestination, int x, int y) {
        //path finding algorithm
        //dijkstras
        Point source =new Point(x, y);
        Map<String,Point> pathMap = dijkstras(moveList, source);
        //find path
        return findPath(pathMap, unitDestination, source);



    }
    private Map<String,Point> dijkstras(List<AnimatedSprite> moveList, Point source){

        LinkedList<Point> queue = new LinkedList<>();
        HashMap<String, Integer> dist= new HashMap<>();
        HashMap<String, Point> prev= new HashMap<>();
        queue.add(source);
        dist.put(source.toString(),0);
        for(AnimatedSprite v: moveList){
            if(v.getPoint().compareTo(source)!=0){
                queue.add(v.getPoint());
                prev.put(v.getPoint().toString(), null);
                //prevents overflow
                dist.put(v.getPoint().toString(), Integer.MAX_VALUE-2);

            }
        }
        while(!queue.isEmpty()){
            Point p = queue.poll();
            for(Point neighbor:getNeighbors(p)) {
                boolean inMoveList = false;
                for (AnimatedSprite tile : moveList) {
                    if (tile.getPoint().compareTo(neighbor) == 0) {
                        inMoveList = true;
                        break;
                    }
                }
                // no need to bound check since valid tiles has to be in moveList
                if (inMoveList) {
                    int alt = dist.get(p.toString()) + 1;
                    if (alt < dist.get(neighbor.toString())) {
                        dist.put(neighbor.toString(), alt);
                        prev.put(neighbor.toString(), p);
                    }
                }
            }
        }
        return prev;
    }

    private List<Point> findPath(Map<String,Point> pathMap, Point destination, Point source){
        LinkedList<Point> path = new LinkedList<>();
        Point current = destination;
        while(current.compareTo(source)!=0){
            path.addFirst(current);
            current = pathMap.get(current.toString());
        }
        return path;
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
