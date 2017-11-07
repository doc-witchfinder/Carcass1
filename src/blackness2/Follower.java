/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackness2;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Dr. Witchfinder
 */
public class Follower {
    
    public final static int BLACK=0, RED=1, GREEN=2, BLUE=3; // kolory.
    public final static int PLAYER_1=1, PLAYER_2=2, PLAYER_3=3, PLAYER_4=4; // numery graczy.
    public final static int WAITING=0, ON_ROAD=1, IN_CITY=2, ON_A_PLAIN=3, IN_MONASTERY=4; // status
    public final static int CENTER=100, TOP=200, RIGHT=300, BOTTOM=400, LEFT=500; // pozycja pionka na kafelku
    
    // właściwości typa
    private int color=BLACK;
    private int player=PLAYER_1;
    private int status;
    private int position;
    private int x=0, y=0;
    
    
    private Field field;
    
    private BufferedImage img;
    
    public Follower(){
        try{
        img = ImageIO.read(new File("src/images/dude_black_2.png"));
        
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
    public Follower(Field field){
        try{
        img = ImageIO.read(new File("src/images/dude_black_2.png"));
        
        } catch (IOException e){
            e.printStackTrace();
        }
        
        this.field = field;   
    }
    
    public Follower(int x, int y){
        try{
        img = ImageIO.read(new File("src/images/dude_black_2.png"));
        
        } catch (IOException e){
            e.printStackTrace();
        }
        
        this.x = x;
        this.y = y;
    }
    
    public Follower(Field field, int x, int y){
        try{
        img = ImageIO.read(new File("src/images/dude_black_2.png"));
        
        } catch (IOException e){
            e.printStackTrace();
        }
        
        this.field = field;
        this.x = x;
        this.y = y;
    }
    
    public BufferedImage getImage(){
        // dodać obsługę nulli?
        return this.img;
    }
    
    public int getX(){
        return this.x;
    }
    
    public int getY(){
        return this.y;
    }
    
    public void setColor(int color){
        switch(color){
            case 0: this.color=BLACK;
                break;
            case 1: this.color=RED;
                break;
            case 2: this.color=GREEN;
                break;
            case 3: this.color=BLUE;
                break;
            default: this.color=BLACK;
        }
    }
    
    public void setPlayer(int player){
        switch(player){
            case 1: this.player = PLAYER_1;
                break;
            case 2: this.player = PLAYER_2;
                break;
            case 3: this.player = PLAYER_3;
                break;
            case 4: this.player = PLAYER_4;
                break;
            default: this.player = PLAYER_1;
        }
    }
    
    public void setStatus(int status){
        switch(status){
            case WAITING: this.status = WAITING;
                break;
            case ON_ROAD: this.status = ON_ROAD;
                break;
            case IN_CITY: this.status = IN_CITY;
                break;
            case ON_A_PLAIN: this.status = ON_A_PLAIN;
        }
    }
    
    public void setPosition(int position){
        switch(position){
            case CENTER: this.position = CENTER;
                break;
            case TOP: this.position = TOP;
                break;
            case RIGHT: this.position = RIGHT;
                break;
            case BOTTOM: this.position = BOTTOM;
                break;
            case LEFT: this.position = LEFT;
                break;
            default: this.position = CENTER;
                break;
            
        }
    }
    
    public void setField(Field field){
        this.field = field;
    }
    
}
