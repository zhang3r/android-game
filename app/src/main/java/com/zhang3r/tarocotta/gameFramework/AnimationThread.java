package com.zhang3r.tarocotta.gameFramework;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zhang3r.tarocotta.R;
import com.zhang3r.tarocotta.bitmaps.AnimatedSprite;
import com.zhang3r.tarocotta.bitmaps.spriteFactory.SpriteFactory;
import com.zhang3r.tarocotta.constants.IAppConstants;
import com.zhang3r.tarocotta.constants.IButtonConstants;
import com.zhang3r.tarocotta.constants.IGameConstants;
import com.zhang3r.tarocotta.constants.IGameConstants.AnimationState;
import com.zhang3r.tarocotta.constants.IGameConstants.GameState;
import com.zhang3r.tarocotta.constants.IGameConstants.TurnState;
import com.zhang3r.tarocotta.constants.IGameConstants.UnitState;
import com.zhang3r.tarocotta.constants.ILogConstants;
import com.zhang3r.tarocotta.constants.ResourceConstant;
import com.zhang3r.tarocotta.fragments.MapFragment;
import com.zhang3r.tarocotta.mapFactory.MapFactory;
import com.zhang3r.tarocotta.mapFactory.MapFactoryImpl;
import com.zhang3r.tarocotta.model.AI.AI;
import com.zhang3r.tarocotta.model.AI.ShittyAI;
import com.zhang3r.tarocotta.model.AttackEvent;
import com.zhang3r.tarocotta.model.ViewTransferDTO;
import com.zhang3r.tarocotta.model.army.Army;
import com.zhang3r.tarocotta.model.maps.GameMap;
import com.zhang3r.tarocotta.model.tiles.statsFactory.impl.Point;
import com.zhang3r.tarocotta.model.tiles.terrain.PlainTerrain;
import com.zhang3r.tarocotta.model.tiles.terrain.RockyTerrain;
import com.zhang3r.tarocotta.model.tiles.terrain.TerrainFactory;
import com.zhang3r.tarocotta.model.tiles.terrain.TreeTerrain;
import com.zhang3r.tarocotta.model.tiles.units.BaseUnit;
import com.zhang3r.tarocotta.terminate.AllUnitsDestroyed;
import com.zhang3r.tarocotta.terminate.TerminateCondition;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AnimationThread extends Thread {
    public MapFactory mapFactory = new MapFactoryImpl();
    int screenWidth;
    int screenHeight;


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
    private BaseUnit unitSelected;
    private BaseUnit unitDefending;
    private int tileSelected;
    private AI shittyAi;
    private TerrainFactory terrainFactory;
    private TerminateCondition terminateCondition;
    private Context context;
    private TurnState state;
    private RectF currViewport;
    private volatile GameState gameState;
    private Dialog battleAnimation;
    private AttackEvent ae;
    private Bitmap mapBackground;
    private int turns;
    private volatile long now;
    private Point unitDestination;
    private volatile boolean turnChange;
    List<Point> path;

    // handle to the surface manager object we interact with
    private final SurfaceHolder surfaceHolder;

    private Paint textPainter;

    public AnimationThread(SurfaceHolder surfaceHolder, Context context,
                           int screenWidth, int screenHeight, View view) {

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.surfaceHolder = surfaceHolder;
        this.mScaleFactor = 1.f;
        this.moveSprites = new LinkedList<>();
        this.attackSprites = new LinkedList<>();
        this.terminateCondition = new AllUnitsDestroyed();
        GameMap.getGameMap().setGrid(mapFactory.initialize(1));
        ResourceConstant.resources = view.getResources();

        this.view = view;
        this.context = context;
        this.unitSelected = null;

        this.tileSelected = 0;
        this.shittyAi = new ShittyAI();
        // initiate the text painter
        battleAnimation = new Dialog(context);
        battleAnimation.setContentView(R.layout.attack_animation_dialog);

        textPainter = new Paint();
        textPainter.setARGB(255, 255, 255, 255);
        textPainter.setTextSize(32);
        currViewport = new RectF(IAppConstants.AXIS_X_MIN,
                IAppConstants.AXIS_Y_MIN, -screenWidth, -screenHeight);
        state = TurnState.PLAYER;
        terrainFactory = new TerrainFactory();
        initializeMap(playerArmy, enemyArmy);
        // music thank to Arkane
        song = MediaPlayer.create(context, R.raw.attack_on_titan_theme);
        song.setVolume(0, 0);
        song.setLooping(true);
        //song.start();
        gameState = GameState.NORMAL;
        turns = 1;


    }

    // TODO: temp initalization: idealy this would vary by level
    private Army initializePlayerArmies(int level) {

        Army army = Army.create(IGameConstants.PLAYER);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;

        Bitmap testTile = SpriteFactory.getInstance().getUnit(IGameConstants.UnitType.FOOT, false);
        BaseUnit unit = new BaseUnit(IGameConstants.UnitType.FOOT);
        unit.setAnimation(AnimatedSprite.create(testTile, IAppConstants.SPRITE_HEIGHT, IAppConstants.SPRITE_HEIGHT, 1, 5, true, 1, 1));
        unit.getStats().setMovePoints(5);
        unit.setPosition(new Point(1, 1));
        unit.setUnitName("PLAYER");
        army.add(unit);

        return army;

    }

    // TODO: temp initailization: idealy this would vary by level
    private Army initializeEnemyArmies(int level) {
        //7 infantry
        Army army = Army.create(IGameConstants.ENEMY);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap testTile = SpriteFactory.getInstance().getUnit(IGameConstants.UnitType.FOOT, false);
        BaseUnit unit = new BaseUnit(IGameConstants.UnitType.FOOT);
        unit.setAnimation(AnimatedSprite.create(testTile, IAppConstants.SPRITE_HEIGHT, IAppConstants.SPRITE_HEIGHT, 1, 5, true, 2, 1));
        unit.getStats().setMovePoints(5);
        unit.getStats().setAttack(40);
        unit.setPosition(new Point(2, 1));
        unit.setUnitName("ENEMY");
        army.add(unit);


        return army;

    }

    // TODO: temp initialize: idealy this would load bitmap based on number in
    // the 2d array
    private void initializeMap(Army player, Army enemy) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;


        for (int y = 0; y < GameMap.getGameMap().getGrid().length; y++) {
            for (int x = 0; x < GameMap.getGameMap().getGrid()[y].length; x++) {
                // TODO:
                // if not in terrrain
                int tile = 0;
                int random = (int) (15 * Math.random());
                if (random == 1) {
                    tile = 1;
                    //tile = new RockyTerrain(resources, x, y);
                } else if (random <= 3) {
                    tile = 2;
                    //tile = new TreeTerrain(resources, x, y);
                } else {
                    tile = 0;
                    //tile = new PlainTerrain(resources, x, y);
                }
                //initialize the map
                GameMap.getGameMap().getGrid()[y][x] = tile;
            }
        }


    }

    @Override
    public void start() {

        final Dialog dialog = new Dialog(context);
        dialog.setTitle(context.getString(R.string.dialogIntroTitle));
        dialog.setContentView(R.layout.custom_dialog);
        // set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText(context.getString(R.string.dialogIntro));

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        dialogButton.setText(context.getString(R.string.dialogOK));

        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        IAppConstants.VIEW_WIDTH = view.getWidth();
        IAppConstants.VIEW_HEIGHT = view.getHeight();
        // merging map sprites into 1 sheet.
        IAppConstants.SPRITE_WIDTH = view.getWidth() / 10;
        IAppConstants.SPRITE_HEIGHT = view.getHeight() / 5;
        terrainFactory = new TerrainFactory();
        terrainFactory.addTerrain(new PlainTerrain(resources, 0, 0));
        terrainFactory.addTerrain(new TreeTerrain(resources, 0, 0));
        terrainFactory.addTerrain(new RockyTerrain(resources, 0, 0));
        mapBackground = combineImages(GameMap.getGameMap().getGrid(), IAppConstants.SPRITE_WIDTH, IAppConstants.SPRITE_HEIGHT);

        this.setRunning(true);
        Log.d(ILogConstants.SYSTEM_ERROR_TAG, "Start run is " + run);
        enemyArmy = initializeEnemyArmies(1);
        playerArmy = initializePlayerArmies(1);
        super.start();
    }

    public Bitmap combineImages(int[][] map, int spriteWidth, int spriteHeight) {

        int width = spriteWidth * map[0].length;
        int height = spriteHeight * map.length;

        Bitmap cs = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas comboImage = new Canvas(cs);
        for (int y = 0; y < GameMap.getGameMap().getGrid().length; y++) {
            for (int x = 0; x < GameMap.getGameMap().getGrid()[y].length; x++) {
                comboImage.drawBitmap(TerrainFactory.getTerrain(map[y][x]).getSprite().getAnimation(), x * spriteWidth, y * spriteHeight, null);
            }
        }
        return cs;
    }

    /**
     * *****************************************************************
     * ************************** GAME LOOP ******************************
     * ******************************************************************
     */
    @Override
    public void run() {
        Log.d(ILogConstants.SYSTEM_ERROR_TAG, "run is " + run);
        while (run) {
            Canvas c = null;
            try {
                c = surfaceHolder.lockCanvas();
                if (c != null) {
                    synchronized (surfaceHolder) {
                        doDraw(c);
                        updatePhysics();

                    }
                } else {
                    break;
                }
            } finally {
                if (c != null) {
                    surfaceHolder.unlockCanvasAndPost(c);
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
        //attack sprite
        if (state == TurnState.ENEMY) {
            //dont move until turn changed
            if(!turnChange) {
                AI();
            }
        }
        if (isAttack)
            updateAttack();
        //move
        updateMove();
        synchronized (terrainFactory) {
            for (int i = 0; i < terrainFactory.getSize(); i++) {
                TerrainFactory.getTerrain(i).getSprite().Update(now);
            }
        }
        synchronized (playerArmy) {
            for (int i = 0; i < playerArmy.getUnits().size(); i++) {
                if (playerArmy.getUnits().get(i).getUnitState() != UnitState.DEAD) {
                    playerArmy.getUnits().get(i).getAnimation().Update(now);
                }
            }
        }
        synchronized (enemyArmy) {
            for (int i = 0; i < enemyArmy.getUnits().size(); i++) {
                if (enemyArmy.getUnits().get(i).getUnitState() != UnitState.DEAD) {
                    enemyArmy.getUnits().get(i).getAnimation().Update(now);
                }
            }
        }
        checkWinCond();
    }

    private void sendToUI() {
        ViewTransferDTO dto = new ViewTransferDTO(turns, playerArmy, enemyArmy, terminateCondition, unitSelected, TerrainFactory.getTerrain(tileSelected));
        MapFragment.onDTOUpdate(dto);
    }

    private void updateAttack() {

        //after animation
        if (gameState == GameState.UNIT_IN_ANIMATION && isAttack && unitSelected.getAnimation().getCurrentFrame() == unitSelected.getAnimation().getNumFrames()) {
            //reset unit animation
            unitDefending.getAnimation().setCurrentAnimation(unitDefending.getAnimation().getCurrentAnimation() & 3);
            unitSelected.getAnimation().setCurrentAnimation(unitSelected.getAnimation().getCurrentAnimation() & 3);
            AttackEvent ae = new AttackEvent(unitSelected, unitDefending);
            ae.calcuateDMG();
            //setting unit state
            unitSelected.setUnitState(UnitState.WAIT);
            unitSelected = null;
            isAttack = false;
            synchronized (gameState) {
                gameState = GameState.NORMAL;
            }
            attackSprites.clear();
        }

    }

    private void updateMove() {
        if (gameState == GameState.UNIT_IN_ANIMATION && unitSelected.getUnitState() == UnitState.MOVE_ANIMATION) {
            //calculate unobstructed path

            if (unitDestination != null && unitSelected.getPosition().compareTo(unitDestination) == 0) {
                unitDestination = null;
            }
            if (unitDestination == null && !path.isEmpty()) {
                unitDestination = path.remove(0);
            }
            if (unitDestination != null) {
                //calculate move distance with time

                int deltaX = unitDestination.getX() - unitSelected.getPosition().getX();
                int deltaY = unitDestination.getY() - unitSelected.getPosition().getY();
                //update direction
                if (deltaX < 0) {
                    unitSelected.getAnimation().setCurrentAnimation(AnimationState.FACE_LEFT.getValue());
                } else if (deltaX > 0) {
                    unitSelected.getAnimation().setCurrentAnimation(AnimationState.FACE_RIGHT.getValue());
                } else if (deltaY < 0) {
                    unitSelected.getAnimation().setCurrentAnimation(AnimationState.FACE_UP.getValue());
                } else if (deltaY > 0) {
                    unitSelected.getAnimation().setCurrentAnimation(AnimationState.FACE_DOWN.getValue());
                }

                //update unitSelected sprite
                int spriteX = unitSelected.getAnimation().getXPos();
                int spriteY = unitSelected.getAnimation().getYPos();
                unitSelected.getAnimation().setXPos(spriteX + deltaX * IAppConstants.SPRITE_WIDTH / 2);
                unitSelected.getAnimation().setYPos(spriteY + deltaY * IAppConstants.SPRITE_HEIGHT / 2);
                //update unitSelected position
                spriteX = unitSelected.getAnimation().getXPos();
                spriteY = unitSelected.getAnimation().getYPos();


                //move
                if (unitSelected.getAnimation().getCurrentAnimation() == AnimationState.FACE_DOWN.getValue() || unitSelected.getAnimation().getCurrentAnimation() == AnimationState.FACE_RIGHT.getValue()) {
                    //moving right and down
                    unitSelected.getPosition().setX(spriteX / IAppConstants.SPRITE_WIDTH);
                    unitSelected.getPosition().setY((int) Math.ceil(spriteY * 1.0 / IAppConstants.SPRITE_HEIGHT));
                } else {
                    //moving left and up
                    unitSelected.getPosition().setX((int) Math.ceil(spriteX * 1.0 / IAppConstants.SPRITE_WIDTH));
                    unitSelected.getPosition().setY((int) Math.floor(spriteY * 1.0 / IAppConstants.SPRITE_HEIGHT));

                }

            } else if (path.isEmpty()) {
                //finished
                //set game state
                gameState = GameState.UNIT_SELECTED;
                unitSelected.setUnitState(UnitState.MOVED);
                unitSelected.getAnimation().setXPos(unitSelected.getPosition().getX()* IAppConstants.SPRITE_WIDTH);
                unitSelected.getAnimation().setYPos(unitSelected.getPosition().getY()* IAppConstants.SPRITE_HEIGHT);
                //clear movesprite
                moveSprites.clear();
            }
        }
    }

    // animations!!!
    private void AI() {
        synchronized (playerArmy) {
            synchronized (enemyArmy) {
                /************************************************* AI HOOK UP *******************************************/

                shittyAi.AiMove(enemyArmy, playerArmy);
                state = TurnState.PLAYER;
                enemyArmy.setEndTurnState();
                // reset unit state
                playerArmy.resetUnitState();
                turns++;
                now = System.currentTimeMillis();
                Log.d(ILogConstants.DEBUG_TAG, "A"+(now));
                turnChange = true;


            }
        }
    }

    private void checkWinCond() {
        /*******************************************  END LEVEL CONDITION **************************************/
        synchronized (enemyArmy) {

            if (terminateCondition.isWin(enemyArmy)) {
                Looper.myLooper().prepare();
                new AlertDialog.Builder(context)
                        .setTitle(context.getString(R.string.winTitle))
                        .setMessage(terminateCondition.getTerminateString())
                        .setPositiveButton(context.getString(R.string.mainMenu), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // main menu
                                dialog.dismiss();
                                run = false;

                                ((Activity) context).finish();
                                Looper.myLooper().quitSafely();

                            }
                        })
                        .setNegativeButton(context.getString(R.string.playAgain), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // play again
                                dialog.dismiss();
                                run = false;
                                Intent i = new Intent("com.zhang3r.onelevelgame.MAINACTIVITY");
                                context.startActivity(i);
                                ((Activity) context).finish();
                                Looper.myLooper().quit();


                            }
                        })

                        .show();
                Looper.myLooper().loop();
            }
        }
        synchronized (playerArmy) {

            if (terminateCondition.isLose(playerArmy)) {
                Looper.myLooper().prepare();
                new AlertDialog.Builder(context)
                        .setTitle(context.getString(R.string.loseTitle))
                        .setMessage(terminateCondition.getTerminateString())
                        .setPositiveButton(context.getString(R.string.mainMenu), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                run = false;
                                dialog.dismiss();
                                Looper.myLooper().quitSafely();
                                Intent i = new Intent("com.zhang3r.onelevelgame.MAINMENU");
                                context.startActivity(i);
                                ((Activity) context).finish();


                            }
                        })
                        .setNegativeButton(context.getString(R.string.playAgain), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // play again
                                run = false;
                                dialog.dismiss();

                                Looper.myLooper().quitSafely();
                                Intent i = new Intent("com.zhang3r.onelevelgame.MAINACTIVITY");

                                context.startActivity(i);
                                ((Activity) context).finish();


                            }
                        })

                        .show();
                Looper.myLooper().loop();
            }
        }
    }

    /**
     * *******************************************************************
     * ********************* Touch Event Handler ***************************
     * ********************************************************************
     */
    public void doUp(MotionEvent e) {
        if(turnChange){
            return;
        }
        if (gameState == GameState.UNIT_IN_ANIMATION) {
            return;
        }
        double x = e.getX() / mScaleFactor;
        double y = e.getY() / mScaleFactor;
        Army army = getArmy(false);
        if (army != null) {
            unitOnTouch(army, x - currViewport.left, y - currViewport.top);
        } else {
            throw new RuntimeException("invaid turn state");
        }
        sendToUI();

    }

    public void doScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                         float distanceY) {

        if (currViewport.left - distanceX > 0) {
            currViewport.left = 0;
            currViewport.right = -screenWidth/mScaleFactor;
        } else if (currViewport.right - distanceX  <=  -1 * mapBackground.getWidth()) {


            currViewport.right = -1 * mapBackground.getWidth();
            currViewport.left = (-1 * mapBackground.getWidth() + screenWidth/mScaleFactor);

        } else {
            currViewport.right = currViewport.right -  distanceX;
            currViewport.left = currViewport.left - distanceX;
        }
        // top
        if (currViewport.top - distanceY > 0) {
            currViewport.top = 0;
            currViewport.bottom = -screenHeight/mScaleFactor;
        } else if (currViewport.bottom - distanceY  <=  -1 * mapBackground.getHeight()) {
            currViewport.bottom = -1 * mapBackground.getHeight();
            currViewport.top = currViewport.bottom + screenHeight / mScaleFactor ;
        } else {
            currViewport.bottom =  currViewport.bottom-distanceY;
            currViewport.top = currViewport.top-distanceY;
        }

    }

    public void doScale(ScaleGestureDetector scaleGestureDetector) {
        //change camera
        mScaleFactor *= scaleGestureDetector.getScaleFactor();
        // Don't let the object get too small or too large.
        mScaleFactor = Math.max(0.5f, Math.min(mScaleFactor, 1.3f));
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


        canvas.drawBitmap(mapBackground, currViewport.left, currViewport.top, null);

        if (moveSprites != null) {
            synchronized (moveSprites) {
                for (AnimatedSprite animatedSprite : moveSprites) {
                    animatedSprite.draw(canvas, currViewport.left,
                            currViewport.top, currViewport.right,
                            currViewport.bottom);
                }

            }
        }

        //units
        synchronized (playerArmy) {

            for (BaseUnit unit : playerArmy.getUnits()) {
                if (unit.getUnitState() != UnitState.DEAD) {
                    AnimatedSprite animatedSprite = unit.getAnimation();
                    animatedSprite.draw(canvas, currViewport.left,
                            currViewport.top, currViewport.right,
                            currViewport.bottom);
                }
            }
        }
        synchronized (enemyArmy) {

            for (BaseUnit unit : enemyArmy.getUnits()) {
                if (unit.getUnitState() != UnitState.DEAD) {
                    AnimatedSprite animatedSprite = unit.getAnimation();
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
        // Turn Change System
        if(turnChange) {
            if (System.currentTimeMillis() - now < 1000) {
                if (state == TurnState.ENEMY) {
                    canvas.drawARGB(128, 255, 0, 0);
                } else {
                    canvas.drawARGB(128, 0, 0, 255);
                }
                Paint p = new Paint();

                p.setColor(Color.WHITE);
                p.setTextSize(200);
                //TODO change to more modular
                StringBuilder sb = new StringBuilder();
                sb.append(state == TurnState.ENEMY ? "Enemy" : "Player");
                sb.append(" Turn");
                canvas.drawText(sb.toString(),currViewport.left + Math.abs(Math.abs(currViewport.right - currViewport.left) - p.measureText(sb.toString()))/2, Math.abs(currViewport.top - currViewport.bottom)/2, p);
            } else if (System.currentTimeMillis() - now > 1000) {
                turnChange = false;
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
            Point pos = unit.getPosition();
            if ((int) (xPos / IAppConstants.SPRITE_WIDTH) == pos.getX()
                    && (int) (yPos / IAppConstants.SPRITE_HEIGHT) == pos.getY()) {
                Log.d(ILogConstants.DEBUG_TAG, "unit found! ");
                return unit;
            }
        }
        return null;
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
            throw new RuntimeException("invalid army state");
        }
        return army;
    }

    private boolean unitOnTouch(Army army, double x, double y) {

        // if unit was clicked on
        if (gameState != GameState.UNIT_IN_ANIMATION && gameState != GameState.UNIT_ATTACK_SELECT) {
            Log.d(ILogConstants.GESTURE_TAG, "GameState: " + gameState);
            BaseUnit unit = unitDetection(army.getUnits(), x, y);
            if (unit != null) {
                if (unit.getUnitState() == UnitState.NORMAL) {
                    //no unit was selected
                    if (gameState == GameState.NORMAL) {
                        gameState = GameState.UNIT_SELECTED;
                        unit.setUnitState(UnitState.SELECTED);
                        moveSprites = unit.getMoveUtil().getMoveTiles(unit, army, enemyArmy, resources);
                        unitSelected = unit;
                    } else if (gameState == GameState.UNIT_SELECTED) {
                        //if another unit was selected
                        //reset original unit
                        unitSelected.setUnitState(UnitState.NORMAL);
                        unit.setUnitState(UnitState.SELECTED);
                        moveSprites = unit.getMoveUtil().getMoveTiles(unit, army, enemyArmy, resources);
                        unitSelected = unit;
                    }
                    unitOrigPosX = unitSelected.getPosition().getX();
                    unitOrigPosY = unitSelected.getPosition().getY();

                }

            } else {

                unit = unitDetection(getArmy(true).getUnits(), x, y);
                //not a friendly unit
                if (unit != null) {
                    if (gameState != GameState.UNIT_ATTACK_SELECT) {
                        if (unitSelected != null) {
                            unitSelected.setUnitState(UnitState.NORMAL);
                        }
                        moveSprites = new ArrayList<>();
                        gameState = GameState.NORMAL;
                        unitSelected = unit;
                    } else if (gameState == GameState.UNIT_ATTACK_SELECT) {
                        //attack select unit
                    }
                } else {
                    //if terrain
                    //TODO if terrain is within moveTiles
                    if (gameState == GameState.UNIT_SELECTED) {
                        unitDestination = new Point((int) (x / IAppConstants.SPRITE_WIDTH), (int) (y / IAppConstants.SPRITE_HEIGHT));
                        boolean inMoveSprites = false;
                        synchronized (moveSprites) {
                            for (AnimatedSprite moveTile : moveSprites) {
                                if (moveTile.getPoint().compareTo(unitDestination) == 0) {
                                    inMoveSprites = true;
                                    break;
                                }
                            }
                        }
                        if (inMoveSprites) {
                            //if unit needs to move
                            unitOrigPosX = unitSelected.getPosition().getX();
                            unitOrigPosY = unitSelected.getPosition().getY();

                            unitSelected.setUnitState(UnitState.MOVE_ANIMATION);

                            //unit move
                            path = unitSelected.getMoveUtil().findPath(moveSprites, unitDestination, unitSelected.getPosition().getX(), unitSelected.getPosition().getY());
                            unitDestination = null;
                            gameState = GameState.UNIT_IN_ANIMATION;


                        } else {
                            //terrain info
                            //clear move
                            synchronized (unitSelected) {
                                synchronized (moveSprites) {
                                    gameState = GameState.NORMAL;
                                    moveSprites.clear();
                                    unitSelected.setUnitState(UnitState.NORMAL);
                                    unitSelected = null;
                                }
                            }
                        }
                    }
                }
            }
        } else if (gameState == GameState.UNIT_ATTACK_SELECT) {
            BaseUnit enemyUnit = unitDetection(getArmy(true).getUnits(), x, y);
            if (enemyUnit != null) {
                //enemy unit found
                //fire off Attack Event
                //set relevant unit in animation
                //TODO: find out about direction
                unitDefending = enemyUnit;
//                unitSelected.getAnimation().setCurrentAnimation(AnimationState.FIGHT_LEFT.getValue());
//                unitDefending.getAnimation().setCurrentAnimation(AnimationState.DMG_RIGHT.getValue());
                unitSelected.getAnimation().setCurrentAnimation(2);
                unitDefending.getAnimation().setCurrentAnimation(2);
                //resetting frames
                unitSelected.getAnimation().setCurrentFrame(0);
                unitDefending.getAnimation().setCurrentFrame(0);
                synchronized (attackSprites) {
                    attackSprites.clear();
                }
                gameState = GameState.UNIT_IN_ANIMATION;

            }
        }


        return true;
    }

    public void buttonEventHandler(String s) {
        if(turnChange){
            return;
        }

        if (s.equals(IButtonConstants.attack)) {
            if (gameState != GameState.UNIT_IN_ANIMATION && unitSelected != null) {

                isAttack = true;
                synchronized (attackSprites) {
                    attackSprites.clear();
                    attackSprites.addAll(unitSelected.getAttackUtil().getUnitAttackTiles(unitSelected, getArmy(false), getArmy(true),
                            resources));
                }
                synchronized (moveSprites){
                    moveSprites.clear();
                }
                synchronized (gameState) {
                    gameState = GameState.UNIT_ATTACK_SELECT;
                }
            }
        } else if (s.equals(IButtonConstants.item)) {
            // TODO: item logic
//            if (unitToMove != null) {
//                synchronized (unitToMove) {
//                    unitToMove.setState(UnitState.WAIT);
//                }
//            }
        } else if (s.equals(IButtonConstants.special)) {
            // TODO: specials logic
//            if (unitToMove != null) {
//                synchronized (unitToMove) {
//                    unitToMove.setState(UnitState.WAIT);
//                }
//            }
        } else if (s.equals(IButtonConstants.wait)) {
            // wait logic
            if (unitSelected != null) {
                synchronized (unitSelected) {
                    unitSelected.setUnitState(UnitState.WAIT);
                }
                unitSelected = null;
            }

        } else if (s.equals(IButtonConstants.cancel)) {
            //unit move
            if (gameState == GameState.UNIT_SELECTED && unitSelected != null && (unitSelected.getUnitState() == UnitState.SELECTED || unitSelected.getUnitState() == UnitState.MOVED)) {
                synchronized (moveSprites) {
                    moveSprites.clear();
                }
                synchronized (attackSprites) {
                    attackSprites.clear();
                }
                synchronized (unitSelected) {
                    Point newPos = new Point(unitOrigPosX, unitOrigPosY);
                    unitSelected.setPosition(newPos);
                    unitSelected.getAnimation().setPoint(newPos);
                    unitSelected.getAnimation().setXPos(unitOrigPosX * IAppConstants.SPRITE_WIDTH);
                    unitSelected.getAnimation().setYPos(unitOrigPosY * IAppConstants.SPRITE_HEIGHT);
                    unitSelected.setUnitState(UnitState.NORMAL);
                }
                gameState = GameState.NORMAL;
                unitSelected = null;
            } else if (gameState == GameState.UNIT_ATTACK_SELECT && unitSelected != null) {
                synchronized (attackSprites) {
                    attackSprites.clear();
                }
                gameState = GameState.UNIT_SELECTED;
                isAttack = false;

            }
        } else if (s.equals(IButtonConstants.endTurn)) {

//                finish unit animations
            //TODO #50 if there is a unit not moved display warning message
            if (gameState != GameState.UNIT_IN_ANIMATION) {
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
                if (unitSelected != null) {
                    synchronized (unitSelected) {
                        unitSelected = null;
                    }
                }

                // if player has unit in move state they will be waited
                if (state == TurnState.PLAYER) {

                    state = TurnState.ENEMY;
                    playerArmy.setEndTurnState();
                    enemyArmy.resetUnitState();
                    turnChange=true;

                    now = System.currentTimeMillis();

                    Log.d(ILogConstants.DEBUG_TAG, "P"+(now));


                    // reset unit state
                } else if (state == TurnState.ENEMY) {
                    state = TurnState.PLAYER;
                    enemyArmy.setEndTurnState();
                    // reset unit state
                    playerArmy.resetUnitState();
                    turnChange=true;
                    now = System.currentTimeMillis();

                    Log.d(ILogConstants.DEBUG_TAG, "E"+(now));
                    turns++;
                } else {
                    Log.d(ILogConstants.SYSTEM_ERROR_TAG,
                            "things has gone awry please check state and army");
                }
                gameState = GameState.NORMAL;
                //send data to fragments
                sendToUI();

            }

        }

    }

}
