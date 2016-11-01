/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lsystemtrees;

import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 *  An individual 
 * @author Jeff
 */
public class LSystemPlant {
    
    private LSystemGenetics geneRules;
    private String seed; //axiom
    public String finalGrowth;
    public ArrayList<Line2D.Double> branches;
    
    public LSystemPlant(){
        branches = new ArrayList<>();
        geneRules = new LSystemGenetics();
        seed = "A";
        grow(5);
        parseGenetics();
    }
    public void grow(int numOfIterations){
        finalGrowth = geneRules.expand(seed,numOfIterations);
    }
    public void parseGenetics(){
        LSystemTurtle turtle = new LSystemTurtle();
        turtle.buildGenetics(this);
    }
}
