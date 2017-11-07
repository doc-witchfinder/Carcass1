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
public class Slut extends Rectangle{
    
    public static final int NO_PLAYER=0, BLACK=1, RED=2, YELLOW=3, BLUE=4, GREEN=5;
    
    
    public boolean enabled = true;
    public int player = NO_PLAYER;
    public boolean player1 = false;
    public boolean player2 = false;
    public boolean player3 = false;
    public boolean player4 = false;
    public boolean[] players = new boolean[]{false, false, false, false}; // tablica mówiąca którzy gracze zajmują dane pole
    public boolean occupied = false;
    
    public Slut() {
        super();
    }

    public Slut(Rectangle r) {
        super(r);
    }

    public Slut(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public Slut(int width, int height) {
        super(width, height);
    }

    public Slut(Point p, Dimension d) {
        super(p, d);
    }

    public Slut(Point p) {
        super(p);
    }

    public Slut(Dimension d) {
        super(d);
    }
    
    public void setTile(Tile tile){
        //this.tile = tile;
    }
    
    public void setPosition(int x, int y, int width, int height){
        super.setBounds(x, y, width, height);
    }
    
}
