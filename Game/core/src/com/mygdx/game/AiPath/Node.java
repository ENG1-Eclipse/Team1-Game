package com.mygdx.game.AiPath;

import java.util.ArrayList;


//Pro tip. Alcohol help program in java. God i miss C. Good old pointers and malloc() - J 2k20
//Java bad C good
//Also this is not the best way and is borked.
//Good luck :) vodka helps!


public class Node {
    /*
    Idea. Each node indicates a point the infiltrator can move to. Calling getNextNodeToTarget will find the next node to the target name. Limit of 20 nodes max travel

     */
    private ArrayList<Node> children;
    private String name;
    private int xPos,yPos;

    public Node(int x,int y,String name) {
        this.name = name;
        xPos = x;
        yPos = y;
        children = new ArrayList<Node>();
    }
    public void setName(String newName){
        name = newName;
    }
    public void addLink(Node nodeToLink){
        children.add(nodeToLink);
    }


    /*
    Runs a depth first search to find the path to target.
    String Target: The name of the target node
    ArrayList<Node> history: null (internal use only)
    Returns: null (node not found)
             ArrayList<Node>(list of nodes to get to target node)
     */
    public ArrayList<Node> getPathToTargetNode(String Target,ArrayList<Node> history){
        //System.out.print("Checking:");
        //System.out.println(getName());
        if(history == null) {
            history = new ArrayList<Node>();
        }
        if(this.name == Target){
            ArrayList<Node>temp= new ArrayList<Node>();
            temp.add(0,this);
            //System.out.println("Found System!");
            return temp;
        }
        history.add(this);
        for (int i = 0; i < children.size(); i++) {
            Node temp = children.get(i);
            boolean visited = false;

            for (int j = 0; j < history.size(); j++) {
                if(temp.getName() == history.get(j).getName()){
                    visited = true;
                }
            }

            if(!visited){
                ArrayList<Node> temp1 = children.get(i).getPathToTargetNode(Target,history);
                if(temp1!= null){
                    temp1.add(0,this);
                    return temp1;
                }
            }
        
        }
        return null;
    }

    public int getX() {
        return xPos;
    }
    public int getY(){
        return yPos;
    }
    public String getName(){
        return name;
    }

}
