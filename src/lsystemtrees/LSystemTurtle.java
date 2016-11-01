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
    
    public LSystemTurtle(){
        turtleStack = new Stack<>();
        direction = -Math.PI/2; // heading up to begin with
        position = new Point2D.Double(400,600);
        angleChange = Math.PI/18; // about 10 degrees
    }
    public void buildGenetics(LSystemPlant plant){
        ArrayList<Line2D.Double> lineList = new ArrayList<>();
        turtleStack.clear();
        String details = plant.finalGrowth;
        for(int i = 0; i < details.length(); i++){
            char subChar = details.charAt(i);
            switch(subChar){
                case 'A': lineList.add(moveForward(5));
                    rotate(angleChange); break;
                case 'B': lineList.add(moveForward(5));
                    rotate(-angleChange); break;
                case 'C': lineList.add(moveForward(7));
                    rotate(angleChange); break;
                case 'D': lineList.add(moveForward(7)); 
                    rotate(-angleChange); break;
                case 'E': lineList.add(moveForward(9)); 
                    rotate(angleChange); break;
                case 'F': lineList.add(moveForward(9)); 
                    rotate(-angleChange); break;
                case 'G': lineList.add(moveForward(11)); 
                    rotate(angleChange); break;
                case 'H': lineList.add(moveForward(11)); 
                    rotate(-angleChange); break;
                case 'I': lineList.add(moveForward(13)); 
                    rotate(angleChange); break;
                case 'J': lineList.add(moveForward(13)); 
                    rotate(-angleChange); break;
                case 'K': lineList.add(moveForward(15));
                    rotate(angleChange); break;
                case 'L': lineList.add(moveForward(15)); 
                    rotate(-angleChange); break;
                case 'M': lineList.add(moveForward(17)); 
                    rotate(angleChange); break;
                case 'N': lineList.add(moveForward(17)); 
                    rotate(-angleChange); break;
                case 'O': lineList.add(moveForward(19)); 
                    rotate(angleChange); break;
                case 'P': lineList.add(moveForward(19));
                    rotate(-angleChange); break;
                case 'Q': lineList.add(moveForward(21)); 
                    rotate(angleChange); break;
                case 'R': lineList.add(moveForward(21)); 
                    rotate(-angleChange); break;
                case 'S': lineList.add(moveForward(23)); 
                    rotate(angleChange); break;
                case 'T': lineList.add(moveForward(23));
                    rotate(-angleChange); break;
                case 'U': lineList.add(moveForward(25)); 
                    rotate(angleChange); break;
                case 'V': lineList.add(moveForward(25)); 
                    rotate(-angleChange); break;
                case 'W': lineList.add(moveForward(23)); 
                    rotate(angleChange); break;
                case 'X': lineList.add(moveForward(24));
                    rotate(-angleChange); break;
                case 'Y': lineList.add(moveForward(25)); 
                    rotate(angleChange); break;
                case 'Z': lineList.add(moveForward(26)); 
                    rotate(-angleChange); break;
                case '[': turtleStack.push(
                        new TurtleDetails(new Point2D.Double(position.x,position.y),direction)); 
                    break;
                case ']': if(!turtleStack.empty()) { 
                        TurtleDetails tD = turtleStack.pop();
                        position.x = tD.position.x;
                        position.y = tD.position.y;
                        direction = tD.direction;
                    }
                    break;
                case '-': rotate(-angleChange); break;
                case '+': rotate(angleChange); break;
            }
        }
        plant.branches = lineList;
    }
    public Line2D.Double moveForward(double distance){
        double x = position.x;
        double y = position.y;
        position.x = x + distance *3* Math.cos(direction);
        position.y = y + distance *3* Math.sin(direction);
        return new Line2D.Double(x,y,position.x, position.y);
    }
    public void rotate(double angle){
        direction += angle;
    }
}
class TurtleDetails{
    public Point2D.Double position;
    public double direction;
    public TurtleDetails(Point2D.Double pos, double dir){
        position = pos;
        direction = dir;
    }
}
