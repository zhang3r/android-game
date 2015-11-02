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
import android.widget.ImageView;
import android.widget.TextView;

import com.zhang3r.tarocotta.R;
import com.zhang3r.tarocotta.bitmaps.AnimatedSprite;
import com.zhang3r.tarocotta.bitmaps.spriteFactory.SpriteFactory;
import com.zhang3r.tarocotta.constants.IAppConstants;
import com.zhang3r.tarocotta.constants.IButtonConstants;
import com.zhang3r.tarocotta.constants.IGameConstants;
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
import com.zhang3r.tarocotta.model.army.Army;
import com.zhang3r.tarocotta.model.maps.Map;
import com.zhang3r.tarocotta.model.tiles.terrain.BaseTerrain;
import com.zhang3r.tarocotta.model.tiles.terrain.PlainTerrain;
import com.zhang3r.tarocotta.model.tiles.terrain.RockyTerrain;
import com.zhang3r.tarocotta.model.tiles.terrain.TerrainFactory;
import com.zhang3r.tarocotta.model.tiles.terrain.TreeTerrain;
import com.zhang3r.tarocotta.model.tiles.units.ArcheryUnit;
import com.zhang3r.tarocotta.model.tiles.units.BaseUnit;
import com.zhang3r.tarocotta.model.tiles.units.CavalryUnit;
import com.zhang3r.tarocotta.model.tiles.units.InfantryUnit;
import com.zhang3r.tarocotta.model.tiles.units.decorator.EnemyUnit;
import com.zhang3r.tarocotta.model.tiles.units.decorator.FriendlyUnit;
import com.zhang3r.tarocotta.model.tiles.units.decorator.IAllegiance;
import com.zhang3r.tarocotta.terminate.AllUnitsDestroyed;
import com.zhang3r.tarocotta.terminate.TerminateCondition;

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
    private int tileSelected;
    private AI shittyAi;
    private TerrainFactory terrainFactory;
    private TerminateCondition terminateCondition;
    private Context context;
    private TurnState state;
    private RectF currViewport;
    private GameState gameState;
    private static int dx;
    private static int dy;
    private Dialog battleAnimation;
    private AttackEvent ae;
    private Bitmap mapBackground;

    // handle to the surface manager object we interact with
    private SurfaceHolder surfaceHolder;

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
        Map.getMap().setGrid(mapFactory.initialize(1));
        ResourceConstant.resources = view.getResources();

        this.view = view;
        this.context = context;
        this.unitToMove = null;

        this.tileSelected = 0;
        this.shittyAi = new ShittyAI();
        // initiate the text painter
        battleAnimation = new Dialog(context);
        battleAnimation.setContentView(R.layout.attack_animation_dialog);

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
        song.setVolume(0, 0);
        song.setLooping(true);
        //song.start();
        gameState = GameState.NORMAL;


    }

    // TODO: temp initalization: idealy this would vary by level
    private Army initializePlayerArmies(int level) {
        Army army = Army.create(IGameConstants.PLAYER);

        int xUpper = (Map.getMap().getGrid().length - 1) / 5;
        int yUpper = Map.getMap().getGrid().length - 1;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap infantryBitMap = SpriteFactory.getInstance().getUnit(IGameConstants.UnitType.FOOT, false);
        Bitmap cavBitMap = SpriteFactory.getInstance().getUnit(IGameConstants.UnitType.CAV, false);
        Bitmap archerBitMap = SpriteFactory.getInstance().getUnit(IGameConstants.UnitType.ARCHER, false);

        //1 cav
        //1 archer
        //2 infantry
//        BaseUnit unit = InfantryUnit.create(1, "player unit 1", (int) (Math.random() * xUpper), (int) (Math.random() * yUpper));
//        unit = new FriendlyUnit(unit);
//        unit.setSprite(AnimatedSprite.create(infantryBitMap,
//                IAppConstants.SPRITE_HEIGHT, IAppConstants.SPRITE_WIDTH, 2, 2,
//                true, unit.getX(), unit.getY()));
//        Log.d(ILogConstants.DEBUG_TAG, "unit created At: " + unit.getX() + " ," + unit.getY());
//        army.add(unit);
//        BaseUnit unit2 = CavalryUnit.create(2, "player unit 2", (int) (Math.random() * xUpper), (int) (Math.random() * yUpper));
//        //unit2 = new FriendlyUnit(unit2);
//        unit2.setSprite(AnimatedSprite.create(cavBitMap,
//                IAppConstants.SPRITE_HEIGHT, IAppConstants.SPRITE_WIDTH, 2, 2,
//                true, unit2.getX(), unit2.getY()));
//        Log.d(ILogConstants.DEBUG_TAG, "unit created At: " + unit2.getX() + " ," + unit2.getY());
//        army.add(unit2);
//        BaseUnit unit3 = ArcheryUnit.create(3, "player unit 3", (int) (Math.random() * xUpper), (int) (Math.random() * yUpper));
//        unit3 = new FriendlyUnit(unit3);
//        unit3.setSprite(AnimatedSprite.create(archerBitMap,
//                IAppConstants.SPRITE_HEIGHT, IAppConstants.SPRITE_WIDTH, 2, 2,
//                true, unit3.getX(), unit3.getY()));
//        Log.d(ILogConstants.DEBUG_TAG, "unit created At: " + unit3.getX() + " ," + unit3.getY());
//        army.add(unit3);
//
//        BaseUnit unit4 = InfantryUnit.create(4, "player unit 4", (int) (Math.random() * xUpper), (int) (Math.random() * yUpper));
//        unit4 = new FriendlyUnit(unit4);
//        unit4.setSprite(AnimatedSprite.create(infantryBitMap,
//                IAppConstants.SPRITE_HEIGHT, IAppConstants.SPRITE_WIDTH, 2, 2,
//                true, unit4.getX(), unit4.getY()));
//        Log.d(ILogConstants.DEBUG_TAG, "unit created At: " + unit4.getX() + " ," + unit4.getY());
//        army.add(unit4);

        return army;

    }

    // TODO: temp initailization: idealy this would vary by level
    private Army initializeEnemyArmies(int level) {
        //7 infantry
        Army army = Army.create(IGameConstants.ENEMY);
        int xUpper = (Map.getMap().getGrid().length - 1) / 5;
        int xLower = Map.getMap().getGrid().length - 1 - xUpper;
        int yUpper = Map.getMap().getGrid().length - 1;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap baseBitMap = SpriteFactory.getInstance().getUnit(IGameConstants.UnitType.FOOT, true);
//        for (int i = 0; i <= 7; i++) {
//            BaseUnit unit = InfantryUnit.create(i, "enemy Infantry unit " + i, ((int) (Math.random() * xUpper) + xLower), (int) (Math.random() * yUpper));
//            unit = new EnemyUnit(unit);
//            unit.setSprite(AnimatedSprite.create(baseBitMap,
//                    IAppConstants.SPRITE_HEIGHT, IAppConstants.SPRITE_WIDTH, 2, 2,
//                    true, unit.getX(), unit.getY()));
//            army.add(unit);
//        }


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
                    tile = 2;
                    //tile = new TreeTerrain(resources, x, y);
                } else {
                    tile = 0;
                    //tile = new PlainTerrain(resources, x, y);
                }
                //initialize the map
                Map.getMap().getGrid()[y][x] = tile;
            }
        }


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
                } else {
                    break;
                }
            }
        }
        song.stop();
    }

    @Override
    public void start() {

        final Dialog dialog = new Dialog(context);
        dialog.setTitle(context.getString(R.string.dialogIntroTitle));
        dialog.setContentView(R.layout.custom_dialog);
        // set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText(context.getString(R.string.dialogIntro));
//        ImageView image = (ImageView) dialog.findViewById(R.id.image);
//        image.setImageResource(R.mipmap.ic_launcher);

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
        IAppConstants.SPRITE_WIDTH = view.getWidth()/10;
        IAppConstants.SPRITE_HEIGHT = view.getHeight()/5;
        terrainFactory= new TerrainFactory();
        terrainFactory.addTerrain(new PlainTerrain(resources, 0, 0));
        terrainFactory.addTerrain(new TreeTerrain(resources, 0, 0));
        terrainFactory.addTerrain(new RockyTerrain(resources, 0, 0));
        mapBackground = combineImages(Map.getMap().getGrid(), IAppConstants.SPRITE_WIDTH, IAppConstants.SPRITE_HEIGHT);

        this.setRunning(true);
        Log.d(ILogConstants.SYSTEM_ERROR_TAG, "Start run is " + run);
        super.start();
    }

    public Bitmap combineImages(int[][] map,int spriteWidth,int spriteHeight) { // can add a 3rd parameter 'String loc' if you want to save the new image - left some code to do that at the bottom

        int width = spriteWidth*map[0].length;
        int height = spriteHeight*map.length;

        Bitmap cs = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas comboImage = new Canvas(cs);
        for (int y = 0; y < Map.getMap().getGrid().length; y++) {
            for (int x = 0; x < Map.getMap().getGrid()[y].length; x++) {
                //TODO change to sprite resolver
                comboImage.drawBitmap(terrainFactory.getTerrain(map[y][x]).getSprite().getAnimation(), x*spriteWidth, y*spriteHeight, null);
            }
        }
        return cs;
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

        synchronized (terrainFactory) {
            for (int i = 0; i < terrainFactory.getSize(); i++) {
                terrainFactory.getTerrain(i).getSprite().Update(now);
            }
        }
        // animations!!!
        if (gameState == GameState.UNITINANIMATION) {

            if (unitToMove.getX() * IAppConstants.SPRITE_WIDTH != unitToMove.getSprite().getXPos() || unitToMove.getY() * IAppConstants.SPRITE_WIDTH != unitToMove.getSprite().getYPos()) {
                //move
                if (unitToMove.getX() * IAppConstants.SPRITE_WIDTH != unitToMove.getSprite().getXPos()) {
                    unitToMove.getSprite().setXPos(unitToMove.getSprite().getXPos() + (dx >> 31 | 1) * IAppConstants.SPRITE_WIDTH / 4);
                } else if (unitToMove.getY() * IAppConstants.SPRITE_WIDTH != unitToMove.getSprite().getYPos()) {
                    unitToMove.getSprite().setYPos(unitToMove.getSprite().getYPos() + (dy >> 31 | 1) * IAppConstants.SPRITE_WIDTH / 4);
                }


            } else if (ae != null && unitToMove.getSprite().getCurrentFrame() == 1) {
                //animation is still going on do nothing :)


            } else {
                //animations ended :(
                if (battleAnimation.isShowing()) {
                    battleAnimation.dismiss();
                }
                gameState = GameState.UNITSELECTED;

            }

        }

//        synchronized (playerArmy) {
//            synchronized (enemyArmy) {
//                /************************************************* AI HOOK UP *******************************************/
//                if (state == TurnState.ENEMY) {
//                    shittyAi.AiMove(enemyArmy, playerArmy);
//                    state = TurnState.PLAYER;
//                    enemyArmy.setEndTurnState();
//                    // reset unit state
//                    playerArmy.resetUnitState();
//                }
//                /************************************************* END OF AI ********************************************/
//                List<BaseUnit> units = new LinkedList<>();
//                units.addAll(playerArmy.getUnits());
//                units.addAll(enemyArmy.getUnits());
//                for (BaseUnit unit : units) {
//                    AnimatedSprite animatedSprite = unit.getSprite();
//                    animatedSprite.Update(now);
//                }
//
//
//            }
//        }
//        /*******************************************  END LEVEL CONDITION **************************************/
//        synchronized (enemyArmy) {
//
//            if (terminateCondition.isWin(enemyArmy)) {
//                Looper.myLooper().prepare();
//                new AlertDialog.Builder(context)
//                        .setTitle(context.getString(R.string.winTitle))
//                        .setMessage(terminateCondition.getTerminateString())
//                        .setPositiveButton(context.getString(R.string.mainMenu), new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                // main menu
//                                dialog.dismiss();
//                                run = false;
//
//                                ((Activity) context).finish();
//                                Looper.myLooper().quitSafely();
//
//                            }
//                        })
//                        .setNegativeButton(context.getString(R.string.playAgain), new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                // play again
//                                dialog.dismiss();
//                                run = false;
//                                Intent i = new Intent("com.zhang3r.onelevelgame.MAINACTIVITY");
//                                context.startActivity(i);
//                                ((Activity) context).finish();
//                                Looper.myLooper().quit();
//
//
//                            }
//                        })
//
//                        .show();
//                Looper.myLooper().loop();
//            }
//        }
//        synchronized (playerArmy) {
//
//            if (terminateCondition.isLose(playerArmy)) {
//                Looper.myLooper().prepare();
//                new AlertDialog.Builder(context)
//                        .setTitle(context.getString(R.string.loseTitle))
//                        .setMessage(terminateCondition.getTerminateString())
//                        .setPositiveButton(context.getString(R.string.mainMenu), new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                run = false;
//                                dialog.dismiss();
//                                Looper.myLooper().quitSafely();
//                                Intent i = new Intent("com.zhang3r.onelevelgame.MAINMENU");
//                                context.startActivity(i);
//                                ((Activity) context).finish();
//
//
//                            }
//                        })
//                        .setNegativeButton(context.getString(R.string.playAgain), new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                // play again
//                                run = false;
//                                dialog.dismiss();
//
//                                Looper.myLooper().quitSafely();
//                                Intent i = new Intent("com.zhang3r.onelevelgame.MAINACTIVITY");
//
//                                context.startActivity(i);
//                                ((Activity) context).finish();
//
//
//                            }
//                        })
//
//                        .show();
//                Looper.myLooper().loop();
//            }
//        }
//
//        lastTime = now;
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

            // Log.d(ILogConstants.DEBUG_TAG, "clicked on unit");
            unitOnTouch(army, x - currViewport.left, y - currViewport.top);
        } else {
            throw new RuntimeException("invaid turn state");
        }

    }

    public void doScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                         float distanceY) {
              // left bound
        if (currViewport.left - distanceX > 0) {
            currViewport.left = 0;
            currViewport.right = -IAppConstants.VIEW_WIDTH;
        } else if ((currViewport.right - distanceX)*mScaleFactor <= -1*mapBackground.getWidth()) {

            currViewport.right = -1*mapBackground.getWidth();
            currViewport.left = (currViewport.right + IAppConstants.VIEW_WIDTH)*mScaleFactor;

        } else {
            currViewport.right -= distanceX;
            currViewport.left -= distanceX;
        }
        // top
        if (currViewport.top - distanceY > 0) {
            currViewport.top = 0;
            currViewport.bottom = -IAppConstants.VIEW_HEIGHT;
        } else if ((currViewport.bottom - distanceY) *mScaleFactor<= -1*mapBackground.getHeight()) {

            currViewport.bottom = -1*mapBackground.getHeight();
            currViewport.top = (currViewport.bottom + IAppConstants.VIEW_HEIGHT)*mScaleFactor;

        } else {
            currViewport.bottom -= distanceY;
            currViewport.top -= distanceY;
        }

    }

    public void doScale(ScaleGestureDetector scaleGestureDetector) {

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

        canvas.drawBitmap(mapBackground,currViewport.left, currViewport.top,null);

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

//        synchronized (playerArmy) {
//
//            for (BaseUnit unit : playerArmy.getUnits()) {
//                AnimatedSprite animatedSprite = unit.getSprite();
//                animatedSprite.draw(canvas, currViewport.left,
//                        currViewport.top, currViewport.right,
//                        currViewport.bottom);
//            }
//        }
//        synchronized (enemyArmy) {
//
//            for (BaseUnit unit : enemyArmy.getUnits()) {
//                AnimatedSprite animatedSprite = unit.getSprite();
//                animatedSprite.draw(canvas, currViewport.left,
//                        currViewport.top, currViewport.right,
//                        currViewport.bottom);
//            }
//        }

        // sending Unit to ui panel
        MapFragment.getUnitSelected(unitToMove);

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
//                Log.d(ILogConstants.DEBUG_TAG, "xpos " + xPos + " ypos " + yPos + " unit found! unit is " + unit.getName() + " at x: " + unit.getX() + " y: " + unit.getY());
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
        BaseUnit unit = unitDetection(army.getUnits(), x, y);
        if (unit != null) {
            if (gameState == GameState.NORMAL && unit != unitToMove) {
                //get friendly unit
                if (unit.getState() == UnitState.NORMAL) {
                    // synchronized (unitToMove) {
                    unitToMove = unit;
                    // }
                    synchronized (moveSprites) {
                        moveSprites.clear();
                        moveSprites.addAll(unitToMove.getUnitMoveTiles(
                                Map.getMap().getGrid().length, Map.getMap().getGrid()[0].length, playerArmy, enemyArmy, resources));
                    }
                    gameState = GameState.UNITSELECTED;
                }
            }

        } else if (gameState == GameState.UNITATTACKSELECT) {
            Army enemyUnits = getArmy(true);
            BaseUnit defender = unitDetection(enemyUnits.getUnits(), x, y);
            if (defender != null) {
                isAttack = false;
                synchronized (attackSprites) {
                    attackSprites.clear();
                }
                if (unitToMove != null) {
                    synchronized (unitToMove) {
                        unitToMove.setState(UnitState.WAIT);
                    }
                }
                //TODO animation hook in
                ae = AttackEvent.attack(unitToMove, defender, army, enemyUnits);
                message = ae.toString();

                // get tile
                tileSelected = getTile(x, y);
                ImageView defenderSprite = (ImageView) battleAnimation.findViewById(R.id.defender);
                ImageView attackerSprite = (ImageView) battleAnimation.findViewById(R.id.attacker);
                //kick off animations
                //change height
                defenderSprite.setImageBitmap(defender.getSprite().getAnimation());
                attackerSprite.setImageBitmap(unitToMove.getSprite().getAnimation());
                battleAnimation.show();
                gameState = GameState.UNITINANIMATION;
            }
        } else if (gameState == GameState.UNITSELECTED) {
            if (unitToMove != null && unitToMove.getState() == UnitState.NORMAL) {
                boolean unitMoved = false;
                unitOrigPosX = unitToMove.getX();
                unitOrigPosY = unitToMove.getY();
                synchronized (moveSprites) {
                    //Call animation
                    //Movement updated for unit but not for sprite
                    //sprite update in animation
                    unitMoved = unitToMove.unitMoveUpdate(moveSprites,
                            playerArmy, enemyArmy, x, y);

                    synchronized (unitToMove) {
                        unitToMove.setState(UnitState.MOVED);
                    }
                    moveSprites.clear();
                    if (unitMoved) {
                        dx = (unitToMove.getX() - unitToMove.getSprite().getXPos() / IAppConstants.SPRITE_WIDTH);
                        dy = (unitToMove.getY() - unitToMove.getSprite().getYPos() / IAppConstants.SPRITE_WIDTH);
                        Log.d(ILogConstants.DEBUG_TAG, "dx " + (dx >> 31 | 1) + " dy " + (dy >> 31 | 1));
                        gameState = GameState.UNITINANIMATION;
                    } else {
                        gameState = GameState.NORMAL;
                    }
                }
            }
        }
        return true;
    }

    public BaseUnit getUnitToMove() {
        return unitToMove;
    }

    public void setUnitToMove(BaseUnit unitToMove) {
        this.unitToMove = unitToMove;
    }

    public void buttonEventHandler(String s) {

        if (s.equals(IButtonConstants.attack)) {
            if (gameState != GameState.UNITINANIMATION) {
                if (unitToMove != null && gameState == GameState.UNITSELECTED) {

                    // 1. display unit attack range;
                    isAttack = true;
                    synchronized (attackSprites) {
                        attackSprites.clear();
                        attackSprites.addAll(unitToMove.getUnitAttackTiles(
                                Map.getMap().getGrid()[0].length, Map.getMap().getGrid().length, resources));
                    }
                    gameState = GameState.UNITATTACKSELECT;

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
            if (gameState != GameState.UNITINANIMATION) {
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
                    gameState = GameState.NORMAL;
                }
            }
        } else if (s.equals(IButtonConstants.cancel)) {
            if (unitToMove != null) {
                if (unitToMove.getState() == UnitState.MOVED) {
                    synchronized (unitToMove) {
                        if (unitOrigPosX != -1 && unitOrigPosY != -1) {
                            unitToMove.setX(unitOrigPosX);
                            unitToMove.setY(unitOrigPosY);
                            unitToMove.getSprite().setXPos(
                                    unitOrigPosX * IAppConstants.SPRITE_WIDTH);
                            unitToMove.getSprite().setYPos(
                                    unitOrigPosY * IAppConstants.SPRITE_HEIGHT);

                            unitToMove.setState(UnitState.NORMAL);
                        }
                    }
                }
                synchronized (attackSprites) {
                    attackSprites.clear();
                }
                gameState = GameState.NORMAL;
            }
            // 2. return tile to previous position
        } else if (s.equals(IButtonConstants.endTurn)) {
            if (gameState != GameState.UNITINANIMATION) {
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
                if (unitToMove != null) {
                    synchronized (unitToMove) {
                        unitToMove = null;
                    }
                }

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
                gameState = GameState.NORMAL;
            }

        }

    }

}