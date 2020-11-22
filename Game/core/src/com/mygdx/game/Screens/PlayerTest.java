package com.mygdx.game.Screens;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.mygdx.game.AiPath.Node;
import com.mygdx.game.MainGame;
import com.mygdx.game.Sprites.Fire;
import com.mygdx.game.Sprites.GameObject;
import com.mygdx.game.Sprites.Infiltrator;
import com.mygdx.game.Sprites.Player;
import com.mygdx.game.Sprites.System;
import com.mygdx.game.Screens.MainMenuScreen;
import com.badlogic.gdx.graphics.OrthographicCamera;

import java.awt.*;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;


public class PlayerTest implements Screen {
    private TextureRegion textureRegion;
    private TextureAtlas textureAtlas;
    private Animation<TextureRegion> downAnimation;
    private float time = 0f;
    private Animation<TextureRegion> idleAnimation;
    private  Animation<TextureRegion> upAnimation;
    private  Animation<TextureRegion> rightAnimation;
    private  Animation<TextureRegion> leftAnimation;
    private  Animation<TextureRegion> teleportAnimation;
    private OrthographicCamera cam;
    private  int teleportingState;
    public float xPos = 0;
    public float yPos = 0;
    public float speed = 5;

    private ArrayList<System> systemList;
    private ArrayList<Infiltrator> infiltrators;

    private Texture teleporter;
    public static int[][] teleporterLocations = {{500,MainGame.Game_Height/2}, {MainGame.Game_Width - 500,MainGame.Game_Height/2}};
    public Texture backgroundMap;

    public Player player;

    MainGame game;
    public PlayerTest (MainGame game) {
        this.game = game;

    }

    final int playerWidth = 100;
    final int playerHeight = 100;
    final int mapScale = 4;
    @Override
    public void render (float delta) {
        //---------------Camera-----------------//
        // Update cam pos to center on the player
        inputLoop();
        infiltratorCaptureLoop();

        cam.position.x = player.getX()+playerWidth/2;
        cam.position.y = player.getY()+playerHeight/2;
        cam.update();
        game.batch.setProjectionMatrix(cam.combined);


        Gdx.gl.glClearColor(0, 0, 0.06f, 1);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        game.batch.draw(teleporter,500,MainGame.Game_Height/2,
                teleporter.getWidth() / 6,teleporter.getHeight() / 6);
        game.batch.draw(teleporter, MainGame.Game_Width - 500,MainGame.Game_Height/2,
                teleporter.getWidth() / 6,teleporter.getHeight() / 6);

        //Draw Background
        game.batch.draw(backgroundMap,0,0, backgroundMap.getWidth()* mapScale, backgroundMap.getHeight() * mapScale);


        //Draw Systems
        for (int i = 0; i < systemList.size(); i++) {
            game.batch.draw(systemList.get(i).render(delta),systemList.get(i).getX(),systemList.get(i).getY(),systemList.get(i).getWidth(),systemList.get(i).getHeight());
        }


        //Draw Infiltrator + Check for interaction
        for (int i = 0; i < infiltrators.size(); i++) {
            game.batch.draw((infiltrators.get(i)).render(delta),(infiltrators.get(i)).getX(),(infiltrators.get(i)).getY(),playerWidth,playerHeight);
            for (int j = 0; j < systemList.size(); j++) {
                if(systemList.get(j).infiltratorInteract(infiltrators.get(i).getX(),infiltrators.get(i).getY())){
                    //java.lang.System.out.println("InfilInteract");
                }
            }
        }




        //Draw Player
        game.batch.draw(player.render(delta), player.getX(),player.getY(),playerWidth,playerHeight);


        game.batch.end();


    }

    private void infiltratorCaptureLoop(){
        ArrayList<Integer> toRemove = new ArrayList<Integer>();
        for (int i = 0; i < infiltrators.size(); i++) {
            if(infiltrators.get(i).isCaptured()){
                toRemove.add(new Integer(i));
            }
        }
        for (int i = 0; i <toRemove.size(); i++) {
            infiltrators.remove(toRemove.get(i).intValue()-i);
        }
    }

    private void inputLoop(){
        if(Gdx.input.isKeyPressed(Input.Keys.E)) {
            //TODO: INTERACTION
            for (int i = 0; i < infiltrators.size(); i++) {
                infiltrators.get(i).playerInteract(player.getX(),player.getY());
            }

        }else if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            //Forward
            player.updateInput(Input.Keys.W);
        }else if(Gdx.input.isKeyPressed(Input.Keys.A)){
            //Left
            player.updateInput(Input.Keys.A);
        }else if(Gdx.input.isKeyPressed(Input.Keys.S)){
            //Down
            player.updateInput(Input.Keys.S);
        }else if(Gdx.input.isKeyPressed(Input.Keys.D)){
            //Right
            player.updateInput(Input.Keys.D);
        }else if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            //Escape
            quit();
        }else{
            player.updateInput(-1);
            //Idle
        }


    }


    public static boolean  isTeleportValid (float xLoc, float yLoc){
        //Function to check if the player is within range of a teleporter
        for (int[] coords : teleporterLocations) {
            if ((xLoc > coords[0] - MainGame.Game_Width/16 && xLoc < coords[0] + MainGame.Game_Width/16) &&
                    (yLoc > coords[1] - MainGame.Game_Height/16 && yLoc < coords[1] + MainGame.Game_Height/16)){
                return true;
            }
        }
        return false;
    }

    public static int[]  teleportFrom (float xLoc, float yLoc){
        //Quick and dirty function to determine where to teleport (from 2 teleporters)
        int[][] teleporterLocations = {{500,MainGame.Game_Height/2}, {MainGame.Game_Width - 500,MainGame.Game_Height/2}};
        int i = 0;
        for (int[] coords : teleporterLocations) {
            if ((xLoc > coords[0] - MainGame.Game_Width/16 && xLoc < coords[0] + MainGame.Game_Width/16) &&
                    (yLoc > coords[1] - MainGame.Game_Height/16 && yLoc < coords[1] + MainGame.Game_Height/16)){
                if (i == 0) {
                    return teleporterLocations[i + 1];
                } else {
                    return teleporterLocations[i - 1];
                }
            }
            i++;
        }
        return null;
    }

    public void quit(){
        game.setScreen(new MainMenuScreen(game));
        dispose();
    }

    public Fire fireTest;
    @Override
    public void show() {
        backgroundMap = new Texture("map\\mappng.png");

        player = new Player(mapScale,playerWidth,playerHeight);
        player.xPos = (backgroundMap.getWidth() * (mapScale-1))-(MainGame.Game_Width / 2);
        player.yPos = (backgroundMap.getHeight()* mapScale) - (MainGame.Game_Width/2);

        infiltrators = new ArrayList<Infiltrator>();
        systemList = new ArrayList<System>();
        //Creating the infiltrator nodes to create the node map for the "AI"
        // Numbered nodes are turning points
        // Nodes with letters are systems

        ArrayList<Node> nodes;//Will be populated with the nodes at some point
        //Creating nodes!
        // See paper for node positions and links

        // WHY IS THE PLAYER POSITION OFFSET FROM THE MAP!!!!!!
        // VODKA DEBUG TIME
        Node node0  = new Node((1226)*(mapScale),(mapScale)*(backgroundMap.getHeight()-487),"0");
        Node node6 = new Node((1220)*(mapScale),(mapScale)*(backgroundMap.getHeight()-227),"6");
        Node nodeB0 = new Node((1165)*(mapScale),(mapScale)*(backgroundMap.getHeight()-194),"B0");
        Node nodeB1 = new Node(1276*(mapScale),(mapScale)*(backgroundMap.getHeight()-204),"B1");
        Node node2 =new Node(1227*(mapScale),(mapScale)*(backgroundMap.getHeight()-846),"2");
        Node nodeD = new Node(1287*(mapScale),(mapScale)*(backgroundMap.getHeight()-824),"D");
        Node nodeS = new Node(1170*(mapScale),(mapScale)*(backgroundMap.getHeight()-882),"S");
        Node node7 = new Node(1600*(mapScale),(mapScale)*(backgroundMap.getHeight()-499),"7");
        Node node1 = new Node(1666*(mapScale),(mapScale)*(backgroundMap.getHeight()-460),"1");
        Node nodeN = new Node(1662*(mapScale),(mapScale)*(backgroundMap.getHeight()-558),"N");
        Node nodeC = new Node(1781*(mapScale),(mapScale)*(backgroundMap.getHeight()-490),"C");
        Node node3 = new Node(736*(mapScale),(mapScale)*(backgroundMap.getHeight()-493),"3");
        Node node8 = new Node(708*(mapScale),(mapScale)*(backgroundMap.getHeight()-480),"8");
        Node nodeM1 = new Node(666*(mapScale),(mapScale)*(backgroundMap.getHeight()-480),"M1");
        Node node4 = new Node(623*(mapScale),(mapScale)*(backgroundMap.getHeight()-506),"4");
        Node nodeM0 =new Node(600*(mapScale),(mapScale)*(backgroundMap.getHeight()-484),"M0");
        Node node5 = new Node(282*(mapScale),(mapScale)*(backgroundMap.getHeight()-494),"5");
        Node nodeE = new Node(232*(mapScale),(mapScale)*(backgroundMap.getHeight()-493),"E");
        // Creating links between nodes
        // P.S. there is prob a better way but hey ho

        node0.addLink(node6);
        node0.addLink(node7);
        node0.addLink(node2);
        node0.addLink(node3);

        node6.addLink(node0);
        node6.addLink(nodeB0);
        node6.addLink(nodeB1);

        nodeB0.addLink(node6);

        nodeB1.addLink(node6);

        node2.addLink(node0);
        node2.addLink(nodeD);
        node2.addLink(nodeS);

        nodeD.addLink(node2);

        nodeS.addLink(node2);

        node1.addLink(nodeC);
        node1.addLink(node7);

        node7.addLink(node1);
        node7.addLink(nodeN);
        node7.addLink(node0);

        nodeN.addLink(node7);

        nodeC.addLink(node1);

        node3.addLink(node0);
        node3.addLink(node4);
        node3.addLink(node8);

        node8.addLink(node3);
        node8.addLink(nodeM1);

        nodeM1.addLink(node8);

        node4.addLink(nodeM0);
        node4.addLink(node3);
        node4.addLink(node5);

        nodeM0.addLink(node4);

        node5.addLink(node4);
        node5.addLink(nodeE);

        nodeE.addLink(node5);

        //Populate node list with the linked nodes
        nodes = new ArrayList<Node>();
        nodes.add(node0);
        nodes.add(node6);
        nodes.add(nodeB0);
        nodes.add(nodeB1);
        nodes.add(node2);
        nodes.add(nodeD);
        nodes.add(nodeS);
        nodes.add(node1);
        nodes.add(node7);
        nodes.add(nodeN);
        nodes.add(nodeC);
        nodes.add(node3);
        nodes.add(node8);
        nodes.add(nodeM1);
        nodes.add(node4);
        nodes.add(nodeM0);
        nodes.add(node5);
        nodes.add(nodeE);



        //Add systems
        systemList.add(new System(player.getX(),player.getY(),"test"));

        // Add infiltrators
        infiltrators.add(new Infiltrator(mapScale));
        //infiltrators.get(infiltrators.size()-1).setPos(player.getX(),player.getY()-500);
        infiltrators.get(infiltrators.size()-1).setPos(node0.getX(),node0.getY());
        java.lang.System.out.println("Test Start");
        infiltrators.get(infiltrators.size()-1).updateTargetNode("E",node0);
        java.lang.System.out.println("Test End");
        teleporter = new Texture("map\\teleporter.png");

        player.setPos(node0.getX(),node0.getY());



        cam = new OrthographicCamera(1920, 1080);

        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();
        java.lang.System.out.print("map W:");
        java.lang.System.out.print(backgroundMap.getWidth());
        java.lang.System.out.print(" map H:");
        java.lang.System.out.println(backgroundMap.getHeight());






    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {

        textureAtlas.dispose();
    }
}