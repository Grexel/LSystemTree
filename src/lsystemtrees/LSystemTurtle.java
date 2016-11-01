/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lsystemtrees;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Jeff
 */
public class LSystemTurtle {
    public Stack<TurtleDetails> turtleStack;
    public double direction;
    public Point2D.Double position;
    public double angleChange;
    public LSystemBranch lastBranch;
    
    public LSystemTurtle(){
        turtleStack = new Stack<>();
        direction = -Math.PI/2; // heading up to begin with
        position = new Point2D.Double(400,600);
        angleChange = Math.PI/18; // about 10 degrees
        lastBranch = null;
    }
    public void buildGenetics(LSystemPlant plant){
        ArrayList<LSystemBranch> lineList = new ArrayList<>();
        turtleStack.clear();
        String details = plant.finalGrowth;
        for(int i = 0; i < details.length(); i++){
            char subChar = details.charAt(i);
            switch(subChar){
                case 'A': lineList.add(moveForward(5,turtleStack.size()));
                    rotate(angleChange); break;
                case 'B': lineList.add(moveForward(5,turtleStack.size()));
                    rotate(-angleChange); break;
                case 'C': lineList.add(moveForward(7,turtleStack.size()));
                    rotate(angleChange); break;
                case 'D': lineList.add(moveForward(7,turtleStack.size())); 
                    rotate(-angleChange); break;
                case 'E': lineList.add(moveForward(9,turtleStack.size())); 
                    rotate(angleChange); break;
                case 'F': lineList.add(moveForward(9,turtleStack.size())); 
                    rotate(-angleChange); break;
                case 'G': lineList.add(moveForward(11,turtleStack.size())); 
                    rotate(angleChange); break;
                case 'H': lineList.add(moveForward(11,turtleStack.size())); 
                    rotate(-angleChange); break;
                case 'I': lineList.add(moveForward(13,turtleStack.size())); 
                    rotate(angleChange); break;
                case 'J': lineList.add(moveForward(13,turtleStack.size())); 
                    rotate(-angleChange); break;
                case 'K': lineList.add(moveForward(15,turtleStack.size()));
                    rotate(angleChange); break;
                case 'L': lineList.add(moveForward(15,turtleStack.size())); 
                    rotate(-angleChange); break;
                case 'M': lineList.add(moveForward(17,turtleStack.size())); 
                    rotate(angleChange); break;
                case 'N': lineList.add(moveForward(17,turtleStack.size())); 
                    rotate(-angleChange); break;
                case 'O': lineList.add(moveForward(19,turtleStack.size())); 
                    rotate(angleChange); break;
                case 'P': lineList.add(moveForward(19,turtleStack.size()));
                    rotate(-angleChange); break;
                case 'Q': lineList.add(moveForward(21,turtleStack.size())); 
                    rotate(angleChange); break;
                case 'R': lineList.add(moveForward(21,turtleStack.size())); 
                    rotate(-angleChange); break;
                case 'S': lineList.add(moveForward(23,turtleStack.size())); 
                    rotate(angleChange); break;
                case 'T': lineList.add(moveForward(23,turtleStack.size()));
                    rotate(-angleChange); break;
                case 'U': lineList.add(moveForward(25,turtleStack.size())); 
                    rotate(angleChange); break;
                case 'V': lineList.add(moveForward(25,turtleStack.size())); 
                    rotate(-angleChange); break;
                case 'W': lineList.add(moveForward(23,turtleStack.size())); 
                    rotate(angleChange); break;
                case 'X': lineList.add(moveForward(24,turtleStack.size()));
                    rotate(-angleChange); break;
                case 'Y': lineList.add(moveForward(25,turtleStack.size())); 
                    rotate(angleChange); break;
                case 'Z': lineList.add(moveForward(26,turtleStack.size())); 
                    rotate(-angleChange); break;
                case '[': turtleStack.push(
                        new TurtleDetails(new Point2D.Double(position.x,position.y),direction,lastBranch)); 
                    break;
                case ']': if(!turtleStack.empty()) { 
                        TurtleDetails tD = turtleStack.pop();
                        position.x = tD.position.x;
                        position.y = tD.position.y;
                        direction = tD.direction;
                        lastBranch = tD.branch;
                    }
                    break;
                case '-': rotate(-angleChange); break;
                case '+': rotate(angleChange); break;
            }
        }
        calculateBranchesFromTip(lineList);
        plant.branches = lineList;
    }
    public LSystemBranch moveForward(double distance,int branchesFromRoot){
        double x = position.x;
        double y = position.y;
        position.x = x + distance *3* Math.cos(direction);
        position.y = y + distance *3* Math.sin(direction);
        LSystemBranch br = new LSystemBranch(new Line2D.Double(x,y,position.x, position.y),branchesFromRoot,lastBranch);
        lastBranch = br;
        return br;
    }
    
    public void rotate(double angle){
        direction += angle;
    }
    public void calculateBranchesFromTip(ArrayList<LSystemBranch> lineList){
        //iterate down through branches, checking branchesfromtip, 
        //if branches > branch.fromtip, branch.fromtip = branch
        for(LSystemBranch branch : lineList){
            int branches = 0; // default is it's a tip.
            //connectingBranch will be null if it's the root
            if(branch.connectingBranch() != null){
                LSystemBranch currentBranch = branch;
                do
                {
                    LSystemBranch nextBranch = currentBranch.connectingBranch();
                    branches++;
                    if(nextBranch.branchesFromTip() < branches){
                        nextBranch.branchesFromTip(branches);
                    }
                    currentBranch = nextBranch;
                }while(currentBranch.connectingBranch() != null);
            }
        }
    }
}
class TurtleDetails{
    public Point2D.Double position;
    public double direction;
    public LSystemBranch branch;
    public TurtleDetails(Point2D.Double pos, double dir, LSystemBranch connection){
        position = pos;
        direction = dir;
        branch = connection;
    }
}
