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
    
    public void setPosition(int x, int y){
        position.x = x;
        position.y = y;
    }
    public BufferedImage drawPlant(LSystemPlant plant){
        return null;
    }
    public void buildGenetics(LSystemPlant plant){
        ArrayList<Line2D.Double> lineList = new ArrayList<>();
        turtleStack.clear();
        String details = plant.finalGrowth;
        for(int i = 0; i < details.length(); i++){
            char subChar = details.charAt(i);
            switch(subChar){
                case 'A': lineList.add(moveForward(5));
                    direction += angleChange; break;
                case 'B': lineList.add(moveForward(5));
                    direction -= angleChange; break;
                case 'C': lineList.add(moveForward(7));
                    direction += angleChange; break;
                case 'D': lineList.add(moveForward(7)); 
                    direction -= angleChange; break;
                case 'E': lineList.add(moveForward(9)); 
                    direction += angleChange; break;
                case 'F': lineList.add(moveForward(9)); 
                    direction -= angleChange; break;
                case 'G': lineList.add(moveForward(11)); 
                    direction += angleChange; break;
                case 'H': lineList.add(moveForward(11)); 
                    direction -= angleChange; break;
                case 'I': lineList.add(moveForward(13)); 
                    direction += angleChange; break;
                case 'J': lineList.add(moveForward(13)); 
                    direction -= angleChange; break;
                case 'K': lineList.add(moveForward(15));
                    direction += angleChange; break;
                case 'L': lineList.add(moveForward(15)); 
                    direction -= angleChange; break;
                case 'M': lineList.add(moveForward(17)); 
                    direction += angleChange; break;
                case 'N': lineList.add(moveForward(17)); 
                    direction -= angleChange; break;
                case 'O': lineList.add(moveForward(19)); 
                    direction += angleChange; break;
                case 'P': lineList.add(moveForward(19));
                    direction -= angleChange; break;
                case 'Q': lineList.add(moveForward(21)); 
                    direction += angleChange; break;
                case 'R': lineList.add(moveForward(21)); 
                    direction -= angleChange; break;
                case 'S': lineList.add(moveForward(23)); 
                    direction += angleChange; break;
                case 'T': lineList.add(moveForward(23));
                    direction -= angleChange; break;
                case 'U': lineList.add(moveForward(25)); 
                    direction += angleChange; break;
                case 'V': lineList.add(moveForward(25)); 
                    direction -= angleChange; break;
                case 'W': lineList.add(moveForward(23)); 
                    direction += angleChange; break;
                case 'X': lineList.add(moveForward(24));
                    direction -= angleChange; break;
                case 'Y': lineList.add(moveForward(25)); 
                    direction += angleChange; break;
                case 'Z': lineList.add(moveForward(26)); 
                    direction -= angleChange; break;
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
                case '-': direction += angleChange; break;
                case '+': direction -= angleChange; break;
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
}
class TurtleDetails{
    public Point2D.Double position;
    public double direction;
    public TurtleDetails(Point2D.Double pos, double dir){
        position = pos;
        direction = dir;
    }
}
