/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackness2;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 *
 * @author lenovo
 */
public class Tile {

    public static final int CHURCH=0, CHURCH_ROAD=1, CITY_4SH=2, 
            CITY_3=3, CITY_3SH=4, CITY_3ROAD=5, CITY_3ROADSH=6, CITY_2=7,
            CITY_2SH=8, CITY_2ROAD=9, CITY_2ROADSH=10, CITY_TUNNEL=11,
            CITY_TUNNELSH=12, CITY_2ENDSBY=13, CITY_2ENDSACROSS=14,
            CITY_1END=15, CITY_1ENDTURNL=16, CITY_1ENDTURNR=17, 
            CITY_1ENDCROSSROADS=18, CITY_1ENDROADTHRU=19, 
            ROAD_THRU=20, ROAD_TURN=21, ROAD_SMALLCROSSROADS=22, 
            ROAD_BIGCROSSROADS=23, START=24, BUG=25, BACK=26, NONE=27;
    
    public static final int BORDER_PLAIN=0, BORDER_ROAD=1, BORDER_CITY=2;
    
    public static final int CENTER=0, TOP=1, RIGHT=2, BOTTOM=3,
            LEFT=4, RIGHTBOTTOM=5, RIGHTTOP=6, LEFTBOTTOM=7, LEFTTOP=8;
    
    private int borderTop=0, borderBottom=0, borderLeft=0, borderRight=0; 
    private boolean roadEnd = false;
    private boolean shield = false;
    private boolean citiesSeparate = false;
    private boolean isaChurch = false;
    
    private boolean placeCenter = true, placeTop = true, placeRight = true, 
            placeBottom = true, placeLeft = true, placeRightTopCorner = true, placeRightBottomCorner = true, placeLeftTopCorner = true, placeLeftBottomCorner = true;
            
    
    public Slut[] slots = new Slut[9];
    
    private boolean occupied = false;
    private int type = NONE;
    
    private int x=0;
    private int y=0;
    private BufferedImage img;
    private Rectangle area;
    
    public Tile(int type, BufferedImage img){
        this.type = type;
        //this.occupied = occupied;
        this.img = img;
        setEdges(type);     
        newSlots(0,0); // tymczasowo
    }
    
    public Tile(int type, BufferedImage img, Rectangle area){
        this.type = type;
        //this.occupied = occupied;
        this.img = img;
        this.area = area;
        newSlots((int)area.getX(), (int)area.getY()); // upewnić się....
    }
    
    public boolean isOccupied(){
    if(occupied==false)
        return false;
    else
        return true;
}
    
    public int getBorderLeft(){
        return borderLeft;
    }
    
    public int getBorderTop(){
        return borderTop;
    }
    
    public int getBorderRight(){
        return borderRight;
    }
    
    public int getBorderBottom(){
        return borderBottom;
    }
    
    public void setEdges(int type){
        
        switch(type){
            case CHURCH: this.isaChurch = true;
                break;
            case 1: this.borderBottom = BORDER_ROAD;
                this.roadEnd = true;
                this.isaChurch = true;
                //this.slots[BOTTOM].enabled = true;
                //this.slots[CENTER].enabled = true;
                break; // 1, klasztor z drogą
            case 2: this.borderBottom = this.borderTop = this.borderLeft = this.borderRight = BORDER_CITY;
                //this.slots[CENTER].enabled = true;
                break;
            case CITY_3: this.borderRight = this.borderTop = this.borderLeft = BORDER_CITY;
                this.citiesSeparate = false; // 3,  miasto potrójne.
                
                break;
            case CITY_3SH: this.borderLeft = this.borderRight = this.borderTop = BORDER_CITY;
                this.shield = true; // 4, miasto portrójne z tarczą.
                break;
            case CITY_3ROAD: this.borderBottom = BORDER_ROAD;
                this.borderRight = this.borderTop = this.borderLeft = BORDER_CITY;
                this.citiesSeparate = false;
                this.roadEnd = true; // 5, miasto potrójne z drogą.
            case CITY_3ROADSH: this.borderBottom = BORDER_ROAD;
                this.borderLeft = this.borderRight = this.borderTop = BORDER_CITY;
                this.shield = true;
                this.roadEnd = true; // 6: potrójne miasto z drogą (koniec) i tarczą.
                break; 
            case CITY_2: this.borderLeft = this.borderTop = BORDER_CITY; // 7, miasto skos.
                this.citiesSeparate = false;                
                break;
            case CITY_2SH: this.borderLeft = this.borderTop = BORDER_CITY;
                this.shield = true;
                this.citiesSeparate = false; // 8
                break;
            case CITY_2ROAD: this.borderTop = this.borderLeft = BORDER_CITY;
                this.borderBottom = this.borderRight = BORDER_ROAD;
                this.citiesSeparate = false; // 9 
                break;
            case CITY_2ROADSH: this.borderTop = this.borderLeft = BORDER_CITY;
                this.borderRight = this.borderBottom = BORDER_ROAD;
                this.shield = true; // 10
                break;
            case CITY_TUNNEL: this.borderLeft = this.borderRight = BORDER_CITY;
                this.citiesSeparate = false; // 11
            case CITY_TUNNELSH: this.borderLeft = this.borderRight = BORDER_CITY;
                this.shield = true;
                this.citiesSeparate = false; // 12
                break;
            case CITY_2ENDSBY: this.borderTop = this.borderLeft = BORDER_CITY; // 13, 2 granice miast.
                break; 
            case CITY_2ENDSACROSS: this.borderTop = this.borderBottom = BORDER_CITY;
                this.citiesSeparate = true; // 14, miasta naprzeciw.
                break;
            case CITY_1END: this.borderTop = BORDER_CITY; // 15 
                break; 
            case CITY_1ENDTURNL: this.borderTop = BORDER_CITY;
                this.borderLeft = this.borderBottom = BORDER_ROAD; // 16,  koniec miasta i skręt w lewo.
                break; 
            case CITY_1ENDTURNR: this.borderTop = BORDER_CITY;
                this.borderBottom = this.borderRight = BORDER_ROAD;
                this.citiesSeparate = true; // 17
                break;
            case CITY_1ENDCROSSROADS: this.borderBottom = this.borderLeft = this.borderRight = BORDER_ROAD;
                this.borderTop = BORDER_CITY; // 18
                this.citiesSeparate = false;
                break;
            case CITY_1ENDROADTHRU: this.borderLeft = this.borderRight = BORDER_ROAD;
                this.borderTop = BORDER_CITY; // 19
                break;
            case ROAD_THRU: this.borderTop = this.borderBottom = BORDER_ROAD; // 20
                break;                       
            case ROAD_TURN: this.borderLeft = this.borderBottom = BORDER_ROAD; // 21
                break;
            case ROAD_SMALLCROSSROADS: this.borderBottom = this.borderLeft = this.borderRight = 1; // 22
                this.roadEnd = true;
                break;
            case ROAD_BIGCROSSROADS: this.borderBottom = this.borderRight = this.borderTop = this.borderLeft = BORDER_ROAD; // 23
                this.roadEnd = true;
                break;
            case START: this.borderTop = BORDER_CITY;
                this.borderRight = this.borderLeft = BORDER_ROAD;
                this.citiesSeparate = true;
                break;
        }
        
    }
    
    public void setOccupuied(boolean state){
        this.occupied = state;
    } 
    
    public int getType(){
        return type;
    }
    
    public void setImage(BufferedImage img){
        this.img = img;
    }
    
    public void setPlace(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public boolean getRoadEnds(){
        return this.roadEnd;
    }
    
    public void setSlots(int x, int y){
        int width = img.getWidth();
        int height = img.getHeight();
        int dudeWidth = 20;
        int dudeHeight = 20;
        slots[CENTER].setPosition(x+width/2-dudeWidth/2, y+height/2-dudeHeight/2, dudeWidth, dudeHeight);
        slots[TOP].setPosition(x+width/2-dudeWidth/2, y, dudeWidth, dudeHeight);
        slots[RIGHT].setPosition(x+width-dudeWidth, y+height/2-dudeHeight/2, dudeWidth, dudeHeight);
        slots[BOTTOM].setPosition(x+width/2-dudeWidth/2, y+height-dudeHeight, dudeWidth, dudeHeight);
        slots[LEFT].setPosition(x, y+width/2-dudeWidth/2, dudeWidth, dudeHeight);
        slots[RIGHTBOTTOM].setPosition(x+width-dudeWidth, y+height-dudeHeight, dudeWidth, dudeHeight);
        slots[RIGHTTOP].setPosition(x+width-dudeWidth, y, dudeWidth, dudeHeight);
        slots[LEFTTOP].setPosition(x, y, dudeWidth, dudeHeight);
        slots[LEFTBOTTOM].setPosition(x, y+height-dudeHeight, dudeWidth, dudeHeight);
        
        // slots[RIGHT].enabled = false; // debug
    }
    
    public void newSlots(int x, int y){
        int width = img.getWidth();
        int height = img.getHeight();
        int dudeWidth = 20;
        int dudeHeight = 20;
        slots[CENTER] = new Slut(x+width/2-dudeWidth/2, y+height/2-dudeHeight/2, dudeWidth, dudeHeight);
        slots[TOP] = new Slut(x+width/2-dudeWidth/2, y, dudeWidth, dudeHeight);
        slots[RIGHT] = new Slut(x+width-dudeWidth, y+height/2-dudeHeight/2, dudeWidth, dudeHeight);
        slots[BOTTOM] = new Slut(x+width/2-dudeWidth/2, y+height-dudeHeight, dudeWidth, dudeHeight);
        slots[LEFT] = new Slut(x, y+width/2-dudeWidth/2, dudeWidth, dudeHeight);
        slots[RIGHTBOTTOM] = new Slut(x+width-dudeWidth, y+height-dudeHeight, dudeWidth, dudeHeight);
        slots[RIGHTTOP] = new Slut(x+width-dudeWidth, y, dudeWidth, dudeHeight);
        slots[LEFTTOP] = new Slut(x, y, dudeWidth, dudeHeight);
        slots[LEFTBOTTOM] = new Slut(x, y+height-dudeHeight, dudeWidth, dudeHeight);
        
        // slots[RIGHT].enabled = false; // debug
    }
    
    
    public void setType(int type){
    if(type>25||type<0)
        type=26;    
        this.type=type;
    }
    
    //wykonuje obrót płytki w prawo
    public void rotate(){
        int temp=borderTop;
        borderTop=borderLeft;
        borderLeft=borderBottom;
        borderBottom=borderRight;
        borderRight=temp;
        
        
        //slots[CENTER]= 
        //boolean boolTemp;
        boolean slotTemp; 
        slotTemp = slots[RIGHT].enabled;
        slots[RIGHT].enabled=slots[TOP].enabled;
        slots[TOP].enabled=slots[LEFT].enabled;
        slots[LEFT].enabled=slots[BOTTOM].enabled;
        slots[BOTTOM].enabled=slotTemp;
        slotTemp = slots[RIGHTTOP].enabled; 
        slots[RIGHTTOP].enabled = slots[LEFTTOP].enabled; 
        slots[LEFTTOP].enabled = slots[LEFTBOTTOM].enabled;
        slots[LEFTBOTTOM].enabled = slots[RIGHTBOTTOM].enabled;
        slots[RIGHTBOTTOM].enabled = slotTemp; 
        
        /*boolTemp = placeRight;
        placeRight = placeTop;
        placeTop = placeLeft;
        placeLeft = placeBottom;
        placeBottom = boolTemp;
        boolTemp = placeRightTopCorner;
        placeRightTopCorner = placeLeftTopCorner;
        placeLeftTopCorner = placeLeftBottomCorner;
        placeLeftBottomCorner = boolTemp;*/
        
        
        
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.PI/2, img.getWidth()/2, img.getHeight()/2);
        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
        img = op.filter(img, null);
    }
    
    public BufferedImage getImage(){
        return this.img;
    }
    
    //public void drawTile(Graphics g, int x, int y){
    //    g.drawImage(img, x, y, null);
    //    
    //}
 
    public void setPosition(Field rect){
        this.area = rect;
        this.x = rect.x;
        this.y = rect.y;
        this.setSlots(rect.x, rect.y); // Tutaj zmiana!
    }
    
    public int getx(){
        return area.x;
    }
    
    public int gety(){
        return area.y;
    }
}
