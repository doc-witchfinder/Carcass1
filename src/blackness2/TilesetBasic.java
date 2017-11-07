/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackness2;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author lenovo
 */
public class TilesetBasic extends Tileset{
    
    public static final int CHURCH=0, CHURCH_ROAD=1, CITY_4SH=2, 
            CITY_3=3, CITY_3SH=4, CITY_3ROAD=5, CITY_3ROADSH=6, CITY_2=7,
            CITY_2SH=8, CITY_2ROAD=9, CITY_2ROADSH=10, CITY_TUNNEL=11,
            CITY_TUNNELSH=12, CITY_2ENDSBY=13, CITY_2ENDSACROSS=14,
            CITY_1END=15, CITY_1ENDTURNL=16, CITY_1ENDTURNR=17, 
            CITY_1ENDCROSSROADS=18, CITY_1ENDROADTHRU=19, 
            ROAD_THRU=20, ROAD_TURN=21, ROAD_SMALLCROSSROADS=22, 
            ROAD_BIGCROSSROADS=23, START=24, BUG=25, BACK=26, NONE=27;
    
    public int tiles_startx=15, tiles_starty=15;
    
    private BufferedImage tileset;
    public BufferedImage[] subimages = new BufferedImage[27];
    private BufferedImage bug;
    private BufferedImage back;
    public BufferedImage none;
    
    //private int[] tiles = {0,0,0,0,1,1,2,3,3,3,4,5,6,6,7,7,7,8,8,9,9,9,
    //10,10,11,12,12,13,13,14,14,15,15,15,15,15,16,16,16,17,17,17,18,18,18,19,19,
    //19,20,20,20,20,20,20,20,20,21,21,21,21,21,21,21,21,21,22,22,22,22,23}; 
    
    //ArrayList<Integer> array; 
    
    
    
    TilesetBasic(){
        
        try{
        tileset = ImageIO.read(new File("src/images/tiles_basic.jpg"));
        bug = ImageIO.read(new File("src/images/bug.jpg"));
        back = ImageIO.read(new File("src/images/back.jpg"));
        none = ImageIO.read(new File("src/images/brak.jpg"));
        
        } catch (IOException e){
            e.printStackTrace();
        }
        
        int count=0;
        
        for(int j=0; j<5; j++){
            for(int i=0; i<5; i++){
               subimages[count] = tileset.getSubimage(16+i*105, 16+j*118, 85, 85);
               count++;
            }
        }
        
    }
    
    public Tile getTile(int type){
        
        if (type>=0&&type<25)
            return new Tile(type, subimages[type]);
        else if (type==BUG)
            return new Tile(type, bug);
        else if (type==NONE)
            return new Tile(type, none);
        if (type==BACK)
            return new Tile(type, back);
        else 
            return new Tile(BUG, bug);

        //switch(type){  
            //case CHURCH: 
            //    return new Tile(CHURCH, subimages[CHURCH]);
            //default: 
            //    return new Tile(BUG, bug);
                    
            /*case CHURCH_ROAD:
                return new Tile(CHURCH_ROAD, subimages[CHURCH_ROAD]);
            case CITY_4SH:
                return new Tile(CITY_4SH, subimages[CITY_4SH]);
            case CITY_3:
                return new Tile(CITY_3, subimages[CITY_3]);
            case CITY_3SH:
                return new Tile(CITY_3SH, subimages[CITY_3SH]);
            case CITY_3ROAD:
                return new Tile(CITY_3ROAD, subimages[CITY_3ROAD]);    
            case CITY_3ROADSH:
                return new Tile(CITY_3ROADSH, subimages[CITY_3ROADSH]);
            case CITY_2:
                return new Tile(CITY_2, subimages[CITY_2]);    
            case CITY_2SH:
                return new Tile(CITY_2, subimages[CITY_2]); */
        }
    
    public ArrayList<Integer> generateTiles(){
        ArrayList<Integer> temp = new ArrayList<>();
        temp.addAll(Arrays.asList(0,0,0,0,1,1,2,3,3,3,4,5,6,6,7,7,7,8,8,9,9,9,
        10,10,11,12,12,13,13,14,14,15,15,15,15,15,16,16,16,17,17,17,18,18,18,19,19,
        19,20,20,20,20,20,20,20,20,21,21,21,21,21,21,21,21,21,22,22,22,22,23));
        
        // W tym przypadku od razu mieszam zbiór płytek
        
        Collections.shuffle(temp);
        
        /*
        for(int i=0; i<temp.size(); i++){
            System.out.println("płytka " + i + ": " + temp.get(i));
        }
        */
        
        return temp;
    }
    
    }
    
    

