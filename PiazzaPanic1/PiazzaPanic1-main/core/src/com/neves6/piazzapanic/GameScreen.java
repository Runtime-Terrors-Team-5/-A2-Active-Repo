package com.neves6.piazzapanic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameScreen extends ScreenAdapter {
    PiazzaPanicGame game;
    OrthographicCamera camera;
    SpriteBatch batch;
    BitmapFont fontBlack;
    BitmapFont fontGreen;
    Stage stage;
    int winWidth;
    int winHeight;
    TiledMap map;
    OrthogonalTiledMapRenderer renderer;
    ScenarioGameMaster gm;
    float unitScale;
    float wScale;
    float hScale;
    int INITIAL_WIDTH;
    int INITIAL_HEIGHT;
    int[] renderableLayers = { 0, 1, 2 };
    Texture selectedTexture;
    Texture bar1;
    Texture bar2;
    Texture moneyUI;
    Texture lock;


    Music music_background;
    // constructor from selecting a level 4 is endless mode
    public GameScreen(PiazzaPanicGame game, int level) {
        this.game = game;
        fontBlack = new BitmapFont(Gdx.files.internal("fonts/IBM_Plex_Mono_SemiBold_Black.fnt"));
        fontGreen = new BitmapFont(Gdx.files.internal("fonts/IBM_Plex_Mono_SemiBold.fnt"));
        fontGreen.setColor(0,250,0,50);
        //bg = new Texture(Gdx.files.internal("title_screen_large.png"));
        this.INITIAL_WIDTH = Gdx.graphics.getWidth();
        this.INITIAL_HEIGHT = Gdx.graphics.getHeight();
        if (level == 1 || level == 2 || level == 3 || level == 4) {
            map = new TmxMapLoader().load("tilemaps/level1.tmx");
            gm = new ScenarioGameMaster(game, map, 3, 5, level);
            unitScale = Gdx.graphics.getHeight() / (12f*32f);
            wScale = unitScale * 32f;
            hScale = unitScale * 32f;
            renderer = new OrthogonalTiledMapRenderer(map, unitScale);
        }
        selectedTexture = new Texture(Gdx.files.internal("people/selected.png"));
        bar2 = new Texture(Gdx.files.internal("icons/bar2.png"));
        bar1 = new Texture(Gdx.files.internal("icons/bar1.png"));
        moneyUI = new Texture(Gdx.files.internal("icons/moneyUI.png"));
        lock = new Texture(Gdx.files.internal("icons/locked.png"));
        music_background = Gdx.audio.newMusic(Gdx.files.internal("sounds/background.mp3"));
        music_background.setLooping(true);
        music_background.play();
    }

    // constructor when loading a game from the file
    public GameScreen(PiazzaPanicGame game) {
        this.game = game;
        fontBlack = new BitmapFont(Gdx.files.internal("fonts/IBM_Plex_Mono_SemiBold_Black.fnt"));
        fontGreen = new BitmapFont(Gdx.files.internal("fonts/IBM_Plex_Mono_SemiBold.fnt"));
        fontGreen.setColor(0,250,0,50);
        //bg = new Texture(Gdx.files.internal("title_screen_large.png"));
        this.INITIAL_WIDTH = Gdx.graphics.getWidth();
        this.INITIAL_HEIGHT = Gdx.graphics.getHeight();

        map = new TmxMapLoader().load("tilemaps/level1.tmx");
        saveData data = SaveAndLoadHandler.getSave();
        gm = data.loadGameMaster(game);
        unitScale = Gdx.graphics.getHeight() / (12f*32f);
        wScale = unitScale * 32f;
        hScale = unitScale * 32f;
        bar2 = new Texture(Gdx.files.internal("icons/bar2.png"));
        bar1 = new Texture(Gdx.files.internal("icons/bar1.png"));
        moneyUI = new Texture(Gdx.files.internal("icons/moneyUI.png"));
        renderer = new OrthogonalTiledMapRenderer(map, unitScale);
        selectedTexture = new Texture(Gdx.files.internal("people/selected.png"));
    }


    @Override
    public void show(){
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();

        stage = new Stage();

        Gdx.graphics.setResizable(false);
    }

    @Override
    public void render(float delta) {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                //keys for movement
                if (keyCode == Input.Keys.W) {
                    gm.tryMove("up");
                }
                if (keyCode == Input.Keys.A) {
                    gm.tryMove("left");
                }
                if (keyCode == Input.Keys.S) {
                    gm.tryMove("down");
                }
                if (keyCode == Input.Keys.D) {
                    gm.tryMove("right");
                }
                // keys to switch control of the chef
                if (keyCode == Input.Keys.NUM_1) {
                    gm.setSelectedChef(1);
                }
                if (keyCode == Input.Keys.NUM_2) {
                    gm.setSelectedChef(2);
                }

                if (keyCode == Input.Keys.NUM_3) {
                    gm.setSelectedChef(3);
                }
                // key to interact with all stations
                if (keyCode == Input.Keys.E) {
                    gm.tryInteract();
                }
                // key to save the game
                if (keyCode == Input.Keys.O){
                    SaveAndLoadHandler.setSave(gm);
                }
                if (keyCode == Input.Keys.C) {
                    gm.unlockMachine(1); //unlocks the grill
                }
                if (keyCode == Input.Keys.V) {
                    gm.unlockMachine(2); //unlocks the forming station
                }
                if (keyCode == Input.Keys.B) {
                    gm.unlockMachine(3); //unlocks the pizza station
                }

                if (keyCode == Input.Keys.X) {
                    gm.unlockChef(); //unlocks the chef
                }
//                for testing gamewinscreen
//                if (keyCode == Input.Keys.P) {
//                    game.setScreen(new GameWinScreen(game,0, gm.getCustomersServed()));
//                }


                return true;
            }
        });

        gm.tickUpdate(delta);
        //enters if the player has 0 rep point and loses
        if (gm.repPoint==0){
            //this.dispose();
            game.setScreen(new GameWinScreen(game,0, gm.getCustomersServed()));
        }

        Gdx.gl20.glViewport( 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        winWidth = Gdx.graphics.getWidth();
        winHeight = Gdx.graphics.getHeight();

        camera.update();
        //game.batch.setProjectionMatrix(camera.combined);

        renderer.setView(camera);
        renderer.render(renderableLayers);

        game.batch.begin();
        game.batch.draw(gm.getChef(gm.getChefsLength()).getUiIcon(), 16 * wScale,7 * wScale,150 * unitScale, 140 * unitScale);
        //System.out.println(gm.getSelectedChef());
        //draws the chefs and their inventory
        for (int i=1;i<gm.getChefsLength()+1;i++){
            game.batch.draw(gm.getChef(i).getTxNow(), gm.getChef(i).getxCoord() * wScale,
                gm.getChef(i).getyCoord() * hScale, 32 * unitScale, 32 * unitScale);
            gm.getChef(i).setInventoryTextures();
            for(int j=0;j<gm.getChef(i).getInvItems().size();j++){
                if(!gm.getChef(i).getInvItems().isEmpty()){ game.batch.draw(gm.getChef(i).getInvItems().get(j), (19 - j) * wScale, (float) ((10 - ((i-1)*1.425)) * wScale),42 * unitScale, 42 * unitScale);}
            }
        }
        // draws the power ups
        for(PowerUp inst: PowerUp.PowerUps){
            if(!inst.getActive()) {
                game.batch.draw(inst.texture, inst.getxCoord() * wScale, inst.getyCoord() * hScale, 32 * unitScale, 32 * unitScale);
            }
        }

        gm.generatePowerUp();
        gm.clearPowerUp();
        gm.getPowerUp();
        gm.powerUpEffect();

        // draws a lock on the unlockable station if not unlocked
        if(!gm.formingStationUnlocked){game.batch.draw(lock, 14 * wScale, 4 * hScale, 32 * unitScale, 32 * unitScale);}
        if(!gm.goldGrillUnlocked){game.batch.draw(lock, 14 * wScale, 5 * hScale, 32 * unitScale, 32 * unitScale);}
        if(!gm.pizzaStationUnlocked){game.batch.draw(lock, 14 * wScale, 6 * hScale, 32 * unitScale, 32 * unitScale);}

        //draws how many reppoints the player has remaining
        game.batch.draw(gm.repIcon, 1 * wScale, 1 * hScale, 93 * unitScale, 45 * unitScale);

        // draws an indicator to show the player which chefs he is controlling
        game.batch.draw(selectedTexture, gm.getChef(gm.getSelectedChef()).getxCoord() * wScale,
            gm.getChef(gm.getSelectedChef()).getyCoord() * hScale, 32 * unitScale,
            32 * unitScale);

        //draws the customer and their order, the first customer also has a timer bar to count down
        // when they will leave
        if (gm.getCustomersSize() >= 1) {
            game.batch.draw(bar2,  8 * wScale, 3 * hScale, 32 * unitScale, 10 * unitScale);
            game.batch.draw(bar1,  8 * wScale, 3 * hScale, (gm.getCustomerRemainingTime() * unitScale)/2, 10 * unitScale);
            game.batch.draw(gm.getFirstCustomer().getTxUp(), 8 * wScale, 2 * hScale, 32 * unitScale, 32 * unitScale);
            game.batch.draw(gm.getFirstCustomer().getOrderTexture(), 8 * wScale, (2 * hScale) -20, 27 * unitScale, 27 * unitScale);
            for (int i = 1; i < gm.getCustomersSize(); i++) {
                game.batch.draw(gm.getFirstCustomer().getTxLeft(), (8+i) * wScale, 2 * hScale, 32 * unitScale, 32 * unitScale);
                game.batch.draw(gm.customers.get(i).getOrderTexture(), (8+i) * wScale, (2 * hScale) -20, 27 * unitScale, 27 * unitScale);
            }
        }

        // draws a timer to count down when a machine is processing an ingredient
        // and when it is done and how long you have before it fails
        for (int i=0;i<gm.getChefsLength();i++){
            if (gm.getChef(i+1).getMachineInteractingWith() != null){
                Machine currentMachine = gm.getChef(i+1).getMachineInteractingWith();
                if (currentMachine.getActive() && currentMachine.getRuntime() >= currentMachine.getProcessingTime()){
                    fontGreen.draw(game.batch, gm.getMachineTimerForChefDone(i),
                        gm.getChef(i+1).getxCoord() * wScale, gm.getChef(i+1).getyCoord()
                            * hScale + 2*(hScale/3f), 32 * unitScale, 1, false);
                }
                else {
                    fontBlack.draw(game.batch, gm.getMachineTimerForChef(i),
                        gm.getChef(i+1).getxCoord() * wScale, gm.getChef(i+1).getyCoord()
                            * hScale + 2*(hScale/3f), 32 * unitScale, 1, false);

                }

            }
        }
        //draws the contents of the tray
        for(int i=0;i<gm.trayTextures.size();i++){
            game.batch.draw(gm.trayTextures.get(i),  12 * wScale, (float) (3.2 * hScale), 32 * unitScale, 32 * unitScale);
        }


        //game.batch.draw(recipes, 20, 20);
        //fontBlack.draw(game.batch, gm.generateCustomersTrayText(), winWidth - (5*(winWidth/8f)), winHeight - 20, (3*(winWidth/8f)), -1, true);
        game.batch.draw(moneyUI,(16*wScale), (6 * hScale),150 * unitScale,32 * unitScale);
        fontBlack.draw(game.batch, gm.generateMoneyText(), (float) (17.1*wScale), (float) (6.68 * hScale), (3*(winWidth/8f)), -1, true);
        fontBlack.draw(game.batch, gm.generateTimerText(), winWidth - (winWidth/3f), 40, (winWidth/3f), -1, false);
        game.batch.end();

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        if (width == INITIAL_WIDTH && height == INITIAL_HEIGHT) {
            super.resize(width, height);
            camera.setToOrtho(false, width, height);
            unitScale = Gdx.graphics.getHeight() / (12f*32f);
            wScale = unitScale * 32f;
            hScale = unitScale * 32f;
            renderer = new OrthogonalTiledMapRenderer(map, unitScale);
        } else {
            Gdx.graphics.setWindowedMode(INITIAL_WIDTH, INITIAL_HEIGHT);
        }
    }

    @Override
    public void hide(){
        batch.dispose();
        fontBlack.dispose();
        fontGreen.dispose();
        selectedTexture.dispose();
        //recipes.dispose();
        map.dispose();
    }
    @Override
    public void dispose(){
        batch.dispose();
        fontBlack.dispose();
        fontGreen.dispose();
        stage.dispose();
        map.dispose();
        renderer.dispose();
        selectedTexture.dispose();
        //recipes.dispose();
    }

    // returns gm for testing
    public ScenarioGameMaster returnGM(){
        return this.gm;
    }
}
