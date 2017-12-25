/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package general;

import java.awt.Point;

/**
 *
 * @author longang
 */
public class hhNode {
    private Point point;
    private String label;
    public hhNode(String label){
        this.label = label;
    }
    public String getLabel(){
        return this.label;
    }
    public void setLoc(Point loc){
        point = loc;
    }
    public Point getLoc(){
        return point;
    }
}
