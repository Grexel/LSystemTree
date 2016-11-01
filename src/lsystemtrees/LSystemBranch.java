/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lsystemtrees;

import java.awt.geom.Line2D;

/**
 * Branches should have width, with the trunk being the widest to the tips
 * being the thinnest. To do this we need to know how far from the trunk the 
 * branch is. LSystem Turtle needs to create these and LSystem plant needs to 
 * contain these.
 * @author Jeff
 */
public class LSystemBranch {
    private Line2D.Double line;
    public void line(Line2D.Double l){line = l;}
    public Line2D.Double line(){return line;}
    
    private int branchesFromRoot;
    public void branchesFromRoot(int l){branchesFromRoot = l;}
    public int branchesFromRoot(){return branchesFromRoot;}
    private int branchesFromTip;
    public void branchesFromTip(int l){branchesFromTip = l;}
    public int branchesFromTip(){return branchesFromTip;}
    
    public LSystemBranch connectingBranch;
    public void connectingBranch(LSystemBranch l){connectingBranch = l;}
    public LSystemBranch connectingBranch(){return connectingBranch;}
    
    public LSystemBranch(Line2D.Double l, int bfr, LSystemBranch connector){
        line = l;
        branchesFromRoot = bfr;
        connectingBranch = connector;
        branchesFromTip = 0;
    }
}
