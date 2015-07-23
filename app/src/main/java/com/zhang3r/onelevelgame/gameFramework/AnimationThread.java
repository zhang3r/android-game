package com.zhang3r.onelevelgame.gameFramework;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.View;

import com.zhang3r.onelevelgame.R;
import com.zhang3r.onelevelgame.bitmaps.AnimatedSprite;
import com.zhang3r.onelevelgame.constants.IAppConstants;
import com.zhang3r.onelevelgame.constants.IButtonConstants;
import com.zhang3r.onelevelgame.constants.IGameConstants;
import com.zhang3r.onelevelgame.constants.IGameConstants.TurnState;
import com.zhang3r.onelevelgame.constants.IGameConstants.UnitState;
import com.zhang3r.onelevelgame.constants.ILogConstants;
import com.zhang3r.onelevelgame.fragments.MapFragment;
import com.zhang3r.onelevelgame.mapFactory.MapFactory;
import com.zhang3r.onelevelgame.mapFactory.MapFactoryImpl;
import com.zhang3r.onelevelgame.model.AI.AI;
import com.zhang3r.onelevelgame.model.AI.ShittyAI;
import com.zhang3r.onelevelgame.model.AttackEvent;
import com.zhang3r.onelevelgame.model.army.Army;
import com.zhang3r.onelevelgame.model.maps.Map;
import com.zhang3r.onelevelgame.model.tiles.terrain.BaseTerrain;
import com.zhang3r.onelevelgame.model.tiles.terrain.PlainTerrain;
import com.zhang3r.onelevelgame.model.tiles.terrain.RockyTerrain;
import com.zhang3r.onelevelgame.model.tiles.terrain.TerrainFactory;
import com.zhang3r.onelevelgame.model.tiles.terrain.TreeTerrain;
import com.zhang3r.onelevelgame.model.tiles.units.BaseUnit;
import com.zhang3r.onelevelgame.model.tiles.units.CavalryUnit;
import com.zhang3r.onelevelgame.model.tiles.units.InfantryUnit;

import java.util.LinkedList;
import java.util.List;

public class AnimationThread extends Thread {
    public MapFactory mapFactory = new MapFactoryImpl();
    //
    int screenWidth;
    int screenHeight;
    int viewWidth;
    int viewHeight;
    Resources resources;
    View view;

    // Are we running?
    private volatile boolean run;

    // Used to figure out elapsed time between frames
    private long lastTime;
    private String message;
    private float mScaleFactor;
    private int unitOrigPosX;
    private int unitOrigPosY;
    private List<AnimatedSprite> moveSprites;
    private List<AnimatedSprite> attackSprites;
    private MediaPlayer song;
    private boolean isAttack;
    private Army enemyArmy;
    private Army playerArmy;
    private BaseUnit unitToMove;
    private BaseUnit unitSelected;
    private int tileSelected;
    private AI shittyAi;
    private TerrainFactory terrainFactory;

    private TurnState state;
    private RectF currViewport;

    // handle to the surface manager object we interact with
    private SurfaceHolder surfaceHolder;

    private Paint textPainter;

    public AnimationThread(SurfaceHolder surfaceHolder, Context context,
                           int screenWidth, int screenHeight, View view) {
        this.surfaceHolder = surfaceHolder;
        this.mScaleFactor = 1.f;
        this.moveSprites = new LinkedList<>();
        this.attackSprites = new LinkedList<>();

        Map.getMap().setGrid(mapFactory.initialize(1));
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.resources = view.getResources();
        this.viewWidth = view.getWidth();
        this.viewHeight = view.getHeight();
        this.view = view;

        this.unitToMove = null;
        this.unitSelected = null;
        this.tileSelected = 0;
        this.shittyAi = new ShittyAI();
        // initiate the text painter
        textPainter = new Paint();
        textPainter.setARGB(255, 255, 255, 255);
        textPainter.setTextSize(32);
        playerArmy = initializePlayerArmies(1);
        enemyArmy = initializeEnemyArmies(1);
        currViewport = new RectF(IAppConstants.AXIS_X_MIN,
                IAppConstants.AXIS_Y_MIN, -screenWidth, -screenHeight);
        state = TurnState.PLAYER;
        terrainFactory = new TerrainFactory();
        initializeMap(playerArmy, enemyArmy);
        // music thank to Arkane
        song = MediaPlayer.create(context, R.raw.attack_on_titan_theme);
        song.setLooping(true);
        song.start();

    }

    // TODO: temp initalization: idealy this would vary by level
    private Army initializePlayerArmies(int level) {
        Army army = Army.create(IGameConstants.PLAYER);
        int x = 6;
        int y = 5;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap infantryBitMap = BitmapFactory.decodeResource(resources,
                R.drawable.sword);
        infantryBitMap = Bitmap.createScaledBitmap(infantryBitMap, 50, 50, false);
        Bitmap cavBitMap = BitmapFactory.decodeResource(resources,
                R.drawable.cavalry);
        cavBitMap = Bitmap.createScaledBitmap(cavBitMap, 50, 50, false);

        BaseUnit unit = InfantryUnit.create(1, "player Infantry unit 1", x, y);
        unit.setSprite(AnimatedSprite.create(infantryBitMap,
                IAppConstants.SPRITE_HEIGHT, IAppConstants.SPRITE_WIDTH, 1, 1,
                true, x, y));
        army.add(unit);
        BaseUnit unit2 = CavalryUnit.create(1, "player cavalry unit 1", x + 3, y + 2);
        unit2.setSprite(AnimatedSprite.create(cavBitMap,
                IAppConstants.SPRITE_HEIGHT, IAppConstants.SPRITE_WIDTH, 1, 1,
                true, x + 3, y + 2));
        army.add(unit2);

        return army;

    }

    // TODO: temp initailization: idealy this would vary by level
    private Army initializeEnemyArmies(int level) {
        Army army = Army.create(IGameConstants.ENEMY);
        int x = 7;
        int y = 2;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap baseBitMap = BitmapFactory.decodeResource(resources,
                R.drawable.base_enemy_tile);
        baseBitMap = Bitmap.createScaledBitmap(baseBitMap, 50, 50, false);
        Bitmap archBitMap = BitmapFactory.decodeResource(resources,
                R.drawable.bow);
        archBitMap = Bitmap.createScaledBitmap(archBitMap, 50, 50, false);
        BaseUnit unit = InfantryUnit.create(1, "enemy Infantry unit 1", x, y);
        unit.setSprite(AnimatedSprite.create(baseBitMap,
                IAppConstants.SPRITE_HEIGHT, IAppConstants.SPRITE_WIDTH, 1, 1,
                true, x, y));
        army.add(unit);
        BaseUnit unit2 = CavalryUnit.create(1, "enemy cavalry unit 1", x + 3, y + 2);
        unit2.setSprite(AnimatedSprite.create(archBitMap,
                IAppConstants.SPRITE_HEIGHT, IAppConstants.SPRITE_WIDTH, 1, 1,
                true, x + 3, y + 2));
        army.add(unit2);

        return army;

    }

    // TODO: temp initialize: idealy this would load bitmap based on number in
    // the 2d array
    private void initializeMap(Army player, Army enemy) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;


        for (int y = 0; y < Map.getMap().getGrid().length; y++) {
            for (int x = 0; x < Map.getMap().getGrid()[y].length; x++) {
                // TODO:
                // if not in terrrain
                int tile = 0;
                int random = (int) (15 * Math.random());
                if (random == 1) {
                    tile = 1;
                    //tile = new RockyTerrain(resources, x, y);
                } else if (random <= 3) {
                    tile =2;
                    //tile = new TreeTerrain(resources, x, y);
                } else {
                    tile =0;
                    //tile = new PlainTerrain(resources, x, y);
                }
                //initialize the map
                Map.getMap().getGrid()[y][x] = tile;
            }
        }
        terrainFactory.addTerrain(new PlainTerrain(resources, 0, 0));
        terrainFactory.addTerrain(new TreeTerrain(resources, 0, 0));
        terrainFactory.addTerrain(new RockyTerrain(resources, 0, 0));


    }

    /**
     * *****************************************************************
     * ************************** GAME LOOP ******************************
     * ******************************************************************
     */
    @Override
    public void run() {
        while (run) {
            Canvas c = null;
            try {
                c = surfaceHolder.lockCanvas();
                if (c != null) {
                    synchronized (surfaceHolder) {
                        updatePhysics();
                        doDraw(c);
                    }
                } else {
                    break;
                }
            } finally {
                if (c != null) {
                    surfaceHolder.unlockCanvasAndPost(c);
                } else {
                    break;
                }
            }
        }
        song.stop();
    }

    /**
     * ******************************************************************
     * ********************** Game State Updater **************************
     * *******************************************************************
     */
    private void updatePhysics() {
        long now = System.currentTimeMillis();
        if (lastTime > now)
            return;
        synchronized (Map.getMap().getGrid()) {
            synchronized (terrainFactory) {
                for (int y = 0; y < Map.getMap().getGrid().length; y++) {
                    for (int x = 0; x < Map.getMap().getGrid()[y].length; x++) {

                            AnimatedSprite animatedSprite = terrainFactory.getTerrain(Map.getMap().getGrid()[y][x]).getSprite();
                            animatedSprite.Update(now);

                    }
                }
            }
        }
        synchronized (playerArmy) {
            synchronized (enemyArmy) {
                /************************************************* AI HOOK UP *******************************************/
                if (state == TurnState.ENEMY) {
                    shittyAi.AiMove(enemyArmy, playerArmy);
                    state = TurnState.PLAYER;
                    enemyArmy.setEndTurnState();
                    // reset unit state
                    playerArmy.resetUnitState();
                }
                /************************************************* END OF AI ********************************************/
                List<BaseUnit> units = new LinkedList<>();
                units.addAll(playerArmy.getUnits());
                units.addAll(enemyArmy.getUnits());
                for (BaseUnit unit : units) {
                    AnimatedSprite animatedSprite = unit.getSprite();
                    animatedSprite.Update(now);
                }
            }
        }
        lastTime = now;
    }

    /**
     * *******************************************************************
     * ********************* Touch Event Handler ***************************
     * ********************************************************************
     */
    public void doUp(MotionEvent e) {
        double x = e.getX() / mScaleFactor;
        double y = e.getY() / mScaleFactor;
        Army army = getArmy(false);
        if (army != null) {
            Log.d(ILogConstants.DEBUG_TAG, "clicked on unit");
            //
            unitOnTouch(army, x - currViewport.left, y - currViewport.top);
        } else {
            throw new RuntimeException("invaid turn state");
        }

    }

    public void doScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                         float distanceY) {

        Log.d(ILogConstants.DEBUG_TAG, "new Y: " + (currViewport.bottom));
        Log.d(ILogConstants.DEBUG_TAG, "map Y: " + (screenHeight));
        // left bound
        if (currViewport.left - distanceX > 0) {
            currViewport.left = 0;
            currViewport.right = -screenWidth;
        } else if (((currViewport.right - distanceX) * mScaleFactor) < (-1 * (Map.getMap().getGrid()[0].length
                * IAppConstants.SPRITE_WIDTH + 10))) {
            currViewport.right = (-1 * (Map.getMap().getGrid()[0].length
                    * IAppConstants.SPRITE_WIDTH + 10));
            currViewport.left = currViewport.right + screenWidth;
        } else {
            currViewport.right -= distanceX;
            currViewport.left -= distanceX;
        }
        // top
        if (currViewport.top - distanceY > 0) {
            currViewport.top = 0;
            currViewport.bottom = -screenHeight;
        } else if (((currViewport.bottom - distanceY) * mScaleFactor) < (-1 * (Map.getMap().getGrid().length
                * IAppConstants.SPRITE_HEIGHT + 10))) {
            currViewport.bottom = (-1 * (Map.getMap().getGrid().length
                    * IAppConstants.SPRITE_HEIGHT + 10));
            currViewport.top = currViewport.bottom + screenHeight;
        } else {
            currViewport.bottom -= distanceY;
            currViewport.top -= distanceY;
        }

    }

    public void doScale(ScaleGestureDetector scaleGestureDetector) {
        mScaleFactor *= scaleGestureDetector.getScaleFactor();

        // Don't let the object get too small or too large.
        mScaleFactor = Math.max(0.9f, Math.min(mScaleFactor, 1.1f));
        Log.d(ILogConstants.DEBUG_TAG, "Scale Factor: " + mScaleFactor);
        view.invalidate();

    }

    /**
     * *****************************************************************************************
     * **************************** draw stuff to canvas****************************************
     * *****************************************************************************************
     */
    private void doDraw(Canvas canvas) {
        // drawing background color. operations on the canvas accumulate
        // this is like clearing the screen //replace with background image
        canvas.save();
        canvas.scale(mScaleFactor, mScaleFactor);
        // terrain
        synchronized (Map.getMap().getGrid()) {
            synchronized (terrainFactory) {
                for (int y = 0; y < Map.getMap().getGrid().length; y++) {
                    for (int x = 0; x < Map.getMap().getGrid()[y].length; x++) {

                        AnimatedSprite animatedSprite = terrainFactory.getTerrain(Map.getMap().getGrid()[y][x]).getSprite();
                        animatedSprite.setYPos(y * IAppConstants.SPRITE_HEIGHT);
                        animatedSprite.setXPos(x * IAppConstants.SPRITE_WIDTH);
                        animatedSprite.draw(canvas, currViewport.left,
                                currViewport.top, currViewport.right,
                                currViewport.bottom);

                    }
                }
            }
        }

        if (moveSprites != null) {
            synchronized (moveSprites) {
                for (AnimatedSprite animatedSprite : moveSprites) {
                    animatedSprite.draw(canvas, currViewport.left,
                            currViewport.top, currViewport.right,
                            currViewport.bottom);
                }

            }
        }
        if (attackSprites != null) {
            synchronized (attackSprites) {
                for (AnimatedSprite animatedSprite : attackSprites) {
                    animatedSprite.draw(canvas, currViewport.left,
                            currViewport.top, currViewport.right,
                            currViewport.bottom);
                }

            }
        }
        //units

        synchronized (playerArmy) {

            for (BaseUnit unit : playerArmy.getUnits()) {
                AnimatedSprite animatedSprite = unit.getSprite();
                animatedSprite.draw(canvas, currViewport.left,
                        currViewport.top, currViewport.right,
                        currViewport.bottom);
            }
        }
        synchronized (enemyArmy) {

            for (BaseUnit unit : enemyArmy.getUnits()) {
                AnimatedSprite animatedSprite = unit.getSprite();
                animatedSprite.draw(canvas, currViewport.left,
                        currViewport.top, currViewport.right,
                        currViewport.bottom);
            }
        }

        // sending Unit to ui panel
        if (unitSelected != null) {
            MapFragment.getUnitSelected(unitSelected);
        }
        // sending message to ui panel
        if (message != null) {
            MapFragment.getMessageToUi(message);
        }
        // TODO send tile info to ui panel
        if (tileSelected >= 0) {
            {
            MapFragment.getTerrainSelected(terrainFactory.getTerrain(tileSelected));


            }
        }

    }

    // so we can pause the game
    public void setRunning(boolean b) {
        run = b;
    }

    /**
     * ***********************************************************************
     * ***************************** GAME LOGIC ******************************
     * ***********************************************************************
     */

    private BaseUnit unitDetection(List<BaseUnit> unitList, double xPos,
                                   double yPos) {
        for (BaseUnit unit : unitList) {
            if ((int) (xPos / IAppConstants.SPRITE_WIDTH) == unit.getX()
                    && (int) (yPos / IAppConstants.SPRITE_HEIGHT) == unit.getY()) {
                Log.d(ILogConstants.DEBUG_TAG, "unit found! unit is " + unit.getName());
                return unit;
            }
        }
        return null;
    }

    private int getTile(double xPos, double yPos) {
        int x = (int) (xPos / IAppConstants.SPRITE_WIDTH);
        int y = (int) (yPos / IAppConstants.SPRITE_HEIGHT);
        return Map.getMap().getGrid()[y][x];
    }

    private Army getArmy(boolean reverse) {
        Army army = null;
        if (state == TurnState.PLAYER && !reverse
                || (state == TurnState.ENEMY && reverse)) {
            army = playerArmy;
        } else if ((state == TurnState.ENEMY && !reverse)
                || (state == TurnState.PLAYER && reverse)) {
            army = enemyArmy;
        }
        if (army == null) {
            throw new RuntimeException("invaid army state");
        }
        return army;
    }

    private boolean unitOnTouch(Army army, double x, double y) {
        boolean isClickOnUnit = false;
        if (!isAttack) {
            BaseUnit unit = unitDetection(army.getUnits(), x, y);
            unitSelected = unit;
            // get tile
            tileSelected = getTile(x, y);

            if (unit != null && unit.getState() == UnitState.NORMAL) {
                unitToMove = unit;
                isClickOnUnit = true;
                // adding moveSprite
                synchronized (moveSprites) {
                    moveSprites.clear();
                    moveSprites.addAll(unitToMove.getUnitMoveTiles(
                            Map.getMap().getGrid()[0].length, Map.getMap().getGrid().length, playerArmy,enemyArmy, resources));
                }
                unitToMove.setState(UnitState.SELECTED);
            } else {
                // if unit has already been clicked on previously
                if (unitToMove != null
                        && (unitToMove.getState() == UnitState.SELECTED)) {
                    boolean unitMoved = false;
                    unitOrigPosX = unitToMove.getX();
                    unitOrigPosY = unitToMove.getY();
                    synchronized (moveSprites) {
                        unitMoved = unitToMove.unitMoveUpdate(moveSprites,
                                playerArmy, enemyArmy, x, y);
                        if (unitMoved) {
                            unitToMove.setState(UnitState.MOVED);
                            Log.d(ILogConstants.DEBUG_TAG, "unitToMove state " + unitToMove.getState());
                        }
                    }

                    synchronized (moveSprites) {
                        moveSprites.clear();
                    }
                    // if unit has moved
                    if (unitMoved) {
                        if (state == TurnState.PLAYER
                                && !playerArmy.hasUnmovedUnits()) {
                            state = TurnState.ENEMY;
                            // set unit moved state
                            playerArmy.setEndTurnState();
                            // reset unit state
                            enemyArmy.resetUnitState();
                        } else if (state == TurnState.ENEMY
                                && !enemyArmy.hasUnmovedUnits()) {
                            state = TurnState.PLAYER;
                            enemyArmy.setEndTurnState();
                            // reset unit state
                            playerArmy.resetUnitState();
                        } else {
                            Log.d(ILogConstants.DEBUG_TAG,
                                    "army has unmoved units nothing to do");
                        }
                    } else {
                        // unit cancelled the move
                        synchronized (unitToMove) {
                            unitToMove.setState(UnitState.NORMAL);

                        }
                    }
                } else {
                    // if unitToMove is not null display this message
                    // display error Message this unit has already
                    // completed move this turn
                    if (unitToMove != null) {
                        message = "this unit has already moved";
                    }
                    // if unitToMove is enemy display enemy stats
                    // detect click is enemy unit
                    BaseUnit enemyUnit = unitDetection(
                            getArmy(true).getUnits(), x, y);
                    if (enemyUnit != null) {
                        // display enemy unit stats
                        unitSelected = enemyUnit;
                        // get tile
                        tileSelected = getTile(x, y);

                    } else {
                        // if unitToMove is neither display tile stats.
                        // MapFragment.getunit
                        message = "you've clicked on a land tile!";
                        // get tile
                        tileSelected = getTile(x, y);

                    }
                }
            }
        } else if (unitToMove != null && isAttack
                && unitToMove.getState() == UnitState.MOVED) {
            //TODO: move this logic to BaseUnit
            // is attack
            // getting enemy units
            Army enemyUnits = getArmy(true);
            BaseUnit defender = unitDetection(enemyUnits.getUnits(), x, y);
            if (defender != null) {
                // TODO: update attack calculations
                isAttack = false;
                AttackEvent ae = AttackEvent.attack(unitToMove, defender, army, enemyUnits);

                synchronized (attackSprites) {
                    attackSprites.clear();
                }

                synchronized (unitToMove) {
                    unitToMove.setState(UnitState.WAIT);
                }
                // TODO: update messaging
                message = unitToMove.getName() + " dealt " + ae.getDamageDelt()
                        + " damage and took " + ae.getRecoil() + " from "
                        + defender.getName();


                unitSelected = unitToMove;
                // get tile
                tileSelected = getTile(x, y);
            }

        }

        return isClickOnUnit;
    }

    public BaseUnit getUnitToMove() {
        return unitToMove;
    }

    public void setUnitToMove(BaseUnit unitToMove) {
        this.unitToMove = unitToMove;
    }

    public void buttonEventHandler(String s) {
        if (s.equals(IButtonConstants.attack)) {
            Log.d(ILogConstants.DEBUG_TAG, "attack button pressed" + unitToMove.getState());
            if (unitToMove != null && unitToMove.getState() == UnitState.MOVED) {

                // 1. display unit attack range;
                isAttack = true;
                synchronized (attackSprites) {
                    attackSprites.clear();
                    attackSprites.addAll(unitToMove.getUnitAttackTiles(
                            Map.getMap().getGrid()[0].length, Map.getMap().getGrid().length, resources));
                }

            }
        } else if (s.equals(IButtonConstants.item)) {
            // TODO: item logic
            if (unitToMove != null) {
                synchronized (unitToMove) {
                    unitToMove.setState(UnitState.WAIT);
                }
            }
        } else if (s.equals(IButtonConstants.special)) {
            // TODO: specials logic
            if (unitToMove != null) {
                synchronized (unitToMove) {
                    unitToMove.setState(UnitState.WAIT);
                }
            }
        } else if (s.equals(IButtonConstants.wait)) {
            // wait logic

            if (unitToMove != null && unitToMove.getState() == UnitState.MOVED) {
                synchronized (unitToMove) {

                    unitToMove.setState(UnitState.WAIT);
                    if (attackSprites.size() != 0) {
                        synchronized (attackSprites) {
                            attackSprites.clear();
                        }
                    }

                    unitToMove = null;
                    Log.d(ILogConstants.DEBUG_TAG, "enemy state is: "
                            + enemyArmy.getUnits().size()
                            + " player state is :"
                            + playerArmy.getUnits().size());

                    if (state == TurnState.PLAYER
                            && !playerArmy.hasUnmovedUnits()) {
                        state = TurnState.ENEMY;
                        // set unit moved state
                        playerArmy.setEndTurnState();
                        // reset unit state
                        enemyArmy.resetUnitState();
                    } else if (state == TurnState.ENEMY
                            && !enemyArmy.hasUnmovedUnits()) {
                        state = TurnState.PLAYER;
                        enemyArmy.setEndTurnState();
                        // reset unit state
                        playerArmy.resetUnitState();
                    } else {
                        Log.d(ILogConstants.SYSTEM_ERROR_TAG,
                                "there are still units unmoved nothing to do");
                    }

                }
            }
        } else if (s.equals(IButtonConstants.cancel)) {
            if (isAttack) {
                if (attackSprites.size() != 0) {
                    synchronized (attackSprites) {
                        attackSprites.clear();
                    }
                }
                isAttack = false;
            }
            if (unitToMove != null) {
                synchronized (unitToMove) {
                    unitToMove.setX(unitOrigPosX);
                    unitToMove.setY(unitOrigPosY);
                    unitToMove.getSprite().setXPos(
                            unitOrigPosX * IAppConstants.SPRITE_WIDTH);
                    unitToMove.getSprite().setYPos(
                            unitOrigPosY * IAppConstants.SPRITE_HEIGHT);

                    unitToMove.setState(UnitState.NORMAL);
                }
            }
            // 2. return tile to previous position
        } else if (s.equals(IButtonConstants.endTurn)) {
            if (attackSprites.size() != 0) {
                synchronized (attackSprites) {
                    attackSprites.clear();
                }
            }
            if (moveSprites.size() != 0) {
                synchronized (moveSprites) {
                    moveSprites.clear();
                }
            }
            // TODO check logic
            // if player has unit in move state they will be waited
            if (state == TurnState.PLAYER) {

                state = TurnState.ENEMY;
                playerArmy.setEndTurnState();
                enemyArmy.resetUnitState();
                // reset unit state
            } else if (state == TurnState.ENEMY) {
                state = TurnState.PLAYER;
                enemyArmy.setEndTurnState();
                // reset unit state
                playerArmy.resetUnitState();
            } else {
                Log.d(ILogConstants.SYSTEM_ERROR_TAG,
                        "things has gone awry please check state and army");
            }

        }

    }

}
