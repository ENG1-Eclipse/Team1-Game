package com.mygdx.game.Screens;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.mygdx.game.AiPath.Node;
import com.mygdx.game.Collision.CheatCollision;
import com.mygdx.game.MainGame;
import com.mygdx.game.Sprites.Fire;
import com.mygdx.game.Sprites.Infiltrator;
import com.mygdx.game.Sprites.Player;
import com.mygdx.game.Sprites.System;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;


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
        java.lang.System.out.println("DEBUG: inputLoop()");
        inputLoop();
        java.lang.System.out.println("DEBUG: infilCapLoop()");
        infiltratorCaptureLoop();
        java.lang.System.out.println("DEBUG: sysAssi()");
        systemAssignmentLoop();

        java.lang.System.out.println("DEBUG: Camera");
        cam.position.x = player.getX()+playerWidth/2;
        cam.position.y = player.getY()+playerHeight/2;
        cam.update();
        game.batch.setProjectionMatrix(cam.combined);


        Gdx.gl.glClearColor(0, 0, 0.06f, 1);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();


        //Draw Background
        java.lang.System.out.println("DEBUG: Background");
        game.batch.draw(backgroundMap,0,0, backgroundMap.getWidth()* mapScale, backgroundMap.getHeight() * mapScale);


        java.lang.System.out.println("DEBUG: Systems");
        //Draw Systems
        for (int i = 0; i < systemList.size(); i++) {
            game.batch.draw(systemList.get(i).render(delta),systemList.get(i).getX(),systemList.get(i).getY(),systemList.get(i).getWidth(),systemList.get(i).getHeight());
        }


        //Draw Infiltrator + Check for interaction
        java.lang.System.out.println("DEBUG: Infil");
        for (int i = 0; i < infiltrators.size(); i++) {
            game.batch.draw((infiltrators.get(i)).render(delta),(infiltrators.get(i)).getX(),(infiltrators.get(i)).getY(),playerWidth,playerHeight);
            for (int j = 0; j < systemList.size(); j++) {
                if(systemList.get(j).infiltratorInteract(infiltrators.get(i).getX(),infiltrators.get(i).getY())){
                    //java.lang.System.out.println("InfilInteract");
                }
            }
        }




        //Draw Player
        java.lang.System.out.println("DEBUG: Player");
        game.batch.draw(player.render(delta), player.getX(),player.getY(),playerWidth,playerHeight);


        game.batch.end();
        java.lang.System.out.println("DEBUG: END");


    }


    private void systemAssignmentLoop(){
        String temp;
        ArrayList <System>systemsLeft = new ArrayList<System>();
        for (int i = 0; i < infiltrators.size(); i++) {
            int counter = 0;
            for (int k = 0; k < systemList.size(); k++) {
                if (systemList.get(k).getStatus()) {
                    systemsLeft.add(systemList.get(k));
                }
            }
            if(systemsLeft.size()==0) {
                //quit();
            }
            for (int j = 0; j < systemList.size(); j++) {

                if (!systemList.get(j).getStatus() && Objects.equals(systemList.get(j).getSystemName(), infiltrators.get(i).getTargetName())) {
                    System nextSystem = null;
                    if(systemsLeft.size()>1) {
                        Random random = new Random();


                        nextSystem = systemsLeft.get(random.nextInt(systemsLeft.size() - 1));

                        infiltrators.get(i).updateTargetNode(nextSystem.getSystemName(), infiltrators.get(i).getCurrentNode());
                        //java.lang.System.out.print("New target node:");
                        //java.lang.System.out.println(nextSystem.getSystemName());
                    }
                    else if(systemsLeft.size()==1){
                        nextSystem = systemsLeft.get(0);
                        infiltrators.get(i).updateTargetNode(nextSystem.getSystemName(), infiltrators.get(i).getCurrentNode());

                    }

                }

            }
        }
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
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            //Escape
            quit();
        }
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
        }else if(Gdx.input.isKeyPressed(Input.Keys.T)){
            player.updateInput(Input.Keys.T);
        }else{
            player.updateInput(-1);
            //Idle
        }


    }


    public static int[]  teleportTo (){
        //Quick and dirty function to determine where to teleport (from 2 teleporters)
        int[][] teleporterLocations = {{345 * 4,579 * 4}, {740 * 4,499 * 4}, {1140 * 4, 829 * 4}, {1230 * 4, 549 * 4},
                {1180 * 4, 159 * 4}, {1700 * 4, 589 * 4}};
        //TODO Teleporter location choice
        return teleporterLocations[3];
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
        player.speed = 7;

        infiltrators = new ArrayList<Infiltrator>();
        systemList = new ArrayList<System>();
        //Creating the infiltrator nodes to create the node map for the "AI"
        // Numbered nodes are turning points
        // Nodes with letters are systems

        ArrayList<Node> nodes;//Will be populated with the nodes at some point
        //Creating nodes!
        // See paper for node positions and links

        Node node0  = new Node((1226)*(mapScale),(mapScale)*(backgroundMap.getHeight()-487),"0");
        Node node6 = new Node((1220)*(mapScale),(mapScale)*(backgroundMap.getHeight()-227),"6");
        Node nodeB0 = new Node((1165)*(mapScale),(mapScale)*(backgroundMap.getHeight()-194),"Bunk0");
        Node nodeB1 = new Node(1276*(mapScale),(mapScale)*(backgroundMap.getHeight()-204),"Bunk1");
        Node node2 =new Node(1227*(mapScale),(mapScale)*(backgroundMap.getHeight()-846),"2");
        Node nodeD = new Node(1287*(mapScale),(mapScale)*(backgroundMap.getHeight()-824),"Disposal");
        Node nodeS = new Node(1170*(mapScale),(mapScale)*(backgroundMap.getHeight()-882),"Storage");
        Node node7 = new Node(1600*(mapScale),(mapScale)*(backgroundMap.getHeight()-499),"7");
        Node node1 = new Node(1666*(mapScale),(mapScale)*(backgroundMap.getHeight()-460),"1");
        Node nodeN = new Node(1662*(mapScale),(mapScale)*(backgroundMap.getHeight()-558),"Navigation");
        Node nodeC = new Node(1781*(mapScale),(mapScale)*(backgroundMap.getHeight()-490),"Command");
        Node node3 = new Node(736*(mapScale),(mapScale)*(backgroundMap.getHeight()-493),"3");
        Node node8 = new Node(708*(mapScale),(mapScale)*(backgroundMap.getHeight()-480),"8");
        Node nodeM1 = new Node(666*(mapScale),(mapScale)*(backgroundMap.getHeight()-480),"Medbay1");
        Node node4 = new Node(623*(mapScale),(mapScale)*(backgroundMap.getHeight()-506),"4");
        Node nodeM0 =new Node(600*(mapScale),(mapScale)*(backgroundMap.getHeight()-484),"Medbay0");
        Node node5 = new Node(282*(mapScale),(mapScale)*(backgroundMap.getHeight()-494),"5");
        Node nodeE = new Node(232*(mapScale),(mapScale)*(backgroundMap.getHeight()-493),"Engine");




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
        systemList.add(new System(nodeE.getX(),nodeE.getY(),"Engine"));
        systemList.add(new System(nodeM0.getX(),nodeM0.getY(),"Medbay0"));
        systemList.add(new System(nodeM1.getX(),nodeM1.getY(),"Medbay1"));
        systemList.add(new System(nodeB0.getX(),nodeB0.getY(),"Bunk0"));
        systemList.add(new System(nodeB1.getX(),nodeB1.getY(),"Bunk1"));
        systemList.add(new System(nodeS.getX(),nodeS.getY(),"Storage"));
        systemList.add(new System(nodeD.getX(),nodeD.getY(),"Disposal"));
        systemList.add(new System(nodeN.getX(),nodeN.getY(),"Navigation"));
        systemList.add(new System(nodeC.getX(),nodeC.getY(),"Command"));


        // Add infiltrators
        infiltrators.add(new Infiltrator(mapScale));
        infiltrators.get(infiltrators.size()-1).setPos(node0.getX(),node0.getY());
        infiltrators.get(infiltrators.size()-1).updateTargetNode("Medbay0",node0);

        infiltrators.add(new Infiltrator(mapScale));
        infiltrators.get(infiltrators.size()-1).setPos(node0.getX(),node0.getY());
        infiltrators.get(infiltrators.size()-1).updateTargetNode("Engine",node0);

        infiltrators.add(new Infiltrator(mapScale));
        infiltrators.get(infiltrators.size()-1).setPos(node0.getX(),node0.getY());
        infiltrators.get(infiltrators.size()-1).updateTargetNode("Bunk0",node0);

        infiltrators.add(new Infiltrator(mapScale));
        infiltrators.get(infiltrators.size()-1).setPos(node0.getX(),node0.getY());
        infiltrators.get(infiltrators.size()-1).updateTargetNode("Storage",node0);

        teleporter = new Texture("map\\teleporter.png");

        player.setPos(node0.getX(),node0.getY());



        cam = new OrthographicCamera(1920, 1080);

        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();
        //java.lang.System.out.print(backgroundMap.getWidth());
        //java.lang.System.out.println(backgroundMap.getHeight());






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