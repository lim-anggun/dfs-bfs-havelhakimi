/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package general;

/**
 *
 * @author longang
 */
public class Node {
    public String color = "WHITE";
    public String label;
    int nodeLoc = -1;
    public Node(String label){
        this.label = label;
    }
    public void setLoc(int loc){
        nodeLoc = loc;
    }
    public int getLoc(){
        return nodeLoc;
    }
}
