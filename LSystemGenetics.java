/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lsystemtrees;

import java.util.*;
import java.util.Random;

/**
 *
 * @author Jeff
 */
public class LSystemGenetics {
    private static final Random rand = new Random();
    private static final String[] SYMBOLS = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N",
        "O","P","Q","R","S","T","U","V","W","X","Y","Z","[","]","+","-"};
    private static final String[] OPERATORS = {"[","]","+","-"};
    public HashMap<String,String> dnaRules;
    
    public LSystemGenetics(){
        //new dnaRules
        dnaRules = randomRules(1,5);
    }
    public HashMap<String,String> randomRules(int minLength,int maxLength){
        HashMap<String,String> map = new HashMap<String,String>() {};
        for(int i = 0; i < SYMBOLS.length; i++){
            boolean closebranch = rand.nextBoolean();
            String randomRule = (closebranch) ? "[" : "";
            
            int numberOfSymbols = rand.nextInt(maxLength - minLength) + minLength;
            for(int j = 0; j < numberOfSymbols; j++){
                randomRule += SYMBOLS[rand.nextInt(SYMBOLS.length-4)];
            }
            randomRule += (closebranch) ? "]" : "";
            map.put(SYMBOLS[i], randomRule);
        }
        map.replace("[", "[");
        map.replace("]", "]");
        map.replace("-", "-");
        map.replace("+", "+");
        return map;
    }
    public String expand(String seed, int iterations){
        StringBuilder holder = new StringBuilder(seed);
        StringBuilder workingMem = new StringBuilder();
        //run through iterations amount
        for(int i = 0 ; i < iterations; i++){
            //replace seed key with map value
            for(int index = 0; index < holder.length(); index++){
                workingMem.append(dnaRules.get(holder.substring(index, index+1)));
            }
            //flip workingMem to holder
            holder.append(workingMem);
            workingMem.setLength(0);
        }
        return holder.toString();
    }
}
