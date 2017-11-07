/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackness2;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author lenovo
 */
public class Field extends Rectangle{

    private boolean occupied = false;
    public Tile tile;
    
    
    public Field() {
        super();
    }

    public Field(Rectangle r) {
        super(r);
    }

    public Field(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public Field(int width, int height) {
        super(width, height);
    }

    public Field(Point p, Dimension d) {
        super(p, d);
    }

    public Field(Point p) {
        super(p);
    }

    public Field(Dimension d) {
        super(d);
    }
    
    public void setTile(Tile tile){
        this.tile = tile;
    }
    
    public boolean isOccupied(){
        return occupied;
    }
    
    public void setOccupied(boolean input){
        occupied = input;
    }
}
