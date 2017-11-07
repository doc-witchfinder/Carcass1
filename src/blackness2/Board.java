/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackness2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.List;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


/**
 *
 * @author lenovo
 */
public class Board extends JPanel implements MouseListener{
 
    public static final int CHURCH=0, CHURCH_ROAD=1, CITY_4SH=2, 
            CITY_3=3, CITY_3SH=4, CITY_3ROAD=5, CITY_3ROADSH=6, CITY_2=7,
            CITY_2SH=8, CITY_2ROAD=9, CITY_2ROADSH=10, CITY_TUNNEL=11,
            CITY_TUNNELSH=12, CITY_2ENDSBY=13, CITY_2ENDSACROSS=14,
            CITY_1END=15, CITY_1ENDTURNL=16, CITY_1ENDTURNR=17, 
            CITY_1ENDCROSSROADS=18, CITY_1ENDROADTHRU=19, 
            ROAD_THRU=20, ROAD_TURN=21, ROAD_SMALLCROSSROADS=22, 
            ROAD_BIGCROSSROADS=23, START=24, BUG=25, BACK=26, NONE=27;
    
    public static final int TILE_SIZE = 85;
   
    public BufferedImage tileset2;
    private int numTilesAcross; 
    private TilesetBasic tileset;
    private Tile test;
    private Field[][] grid;
    private Field rectTop, rectBuffer;
    private Tile startTile; 
    private Tile lastTile, bonusTile;
    private Tile temp;
    private Tile buffer;
    private Tile pileTop;
    
    private int tileCounter = 0;
    private int numTiles;
    
    private ArrayList<Integer> pile = new ArrayList<>();
    private ArrayList<Tile> tiles = new ArrayList<>();
    private ArrayList<Follower> dudes = new ArrayList<>();
    
    private Random rand = new Random();
    
    boolean startPaint = false;
    
    public static int gridWidth = 8;
    public static int gridHeight = 7;
    public static int noPlayers = 4;
    
    // boolean tilePicked = false;
     
    boolean bufferPlaced = false;
    boolean tilePlaced = true;
    
    private int points[]={0, 0, 0, 0};
    
    private BoardInspector inspector;
    
    // te zmenne chyba fajnie będzie uzależnić od gracza...
    private boolean dudeFlag = false;
    
    
    
    public Board() {
    
        initBoard();
        
        addMouseListener(this);
        
        inspector = new BoardInspector(this);
    }
    
    private Image img; 
    
    private void initBoard (){
        
        //URL urlBackgroundImg = getClass().getResource("/ch01/img/wood.jpg");
        // this.imgBackground = new ImageIcon(urlBackgroundImg).getImage();
        
        // początkowy zestaw typów
        for(int i=0; i<7; i++){
            
          dudes.add(new Follower(820+(i*35), 260));
        }
        
        
        //ImageIcon icn = new ImageIcon("src/images/zbik.jpg");
        ImageIcon icn = new ImageIcon("src/images/trawa.jpg");
        
        img = icn.getImage();
        
        // int w = img.getWidth(this);
        // int h = img.getHeight(this);
        setPreferredSize(new Dimension(1000, 750));
        
        tileset = new TilesetBasic();
        
        // grid = createGrid(30, 30, 8, 5, 85); // stare
        grid = createGrid(30, 30, gridHeight, gridWidth, 85);
        
        //lastTile = tileset.getTile(BUG);
        //lastTile.setPosition(grid[5][2]);
                
        placeTile(tiles, START, 3, 3);
        
        
        System.out.println(grid[1][4].isOccupied());
        //placeTile(tiles, CHURCH, 1, 3);
        //placeTile(tiles, CITY_2ROAD, 1, 4);
        //newGame(1, 4);
        //newGame(3, 3);
        
        pile = tileset.generateTiles();
        numTiles = pile.size();
        
        buffer = tileset.getTile(NONE);
        rectBuffer = new Field(930, 140, TILE_SIZE, TILE_SIZE);
        buffer.setPosition(rectBuffer);
        
        pileTop = tileset.getTile(BACK);
        rectTop = new Field(800, 140, TILE_SIZE, TILE_SIZE);
        pileTop.setPosition(rectTop);
        
        //repaint();
        
        for(int g=0; g<6; g++){
                         for(int h=0; h<5; h++){
                              System.out.println("Pole " + g + ", " + h + " : " + grid[g][h].isOccupied());
                          }
                         
                      }
        
    }
    
    private void newGame(int startx, int starty){        
        startTile = tileset.getTile(START);
        startTile.setPosition(grid[startx][starty]);
        lastTile = startTile;
        repaint();
        //startTile.drawTile(getGraphics(), startTile.getx(), startTile.gety());
    }
    
    private void placeTile (ArrayList<Tile> tiles, int type, int i, int j){
        Tile temp = tileset.getTile(type);
        //Tile temp = buffer;
        temp.setPosition(grid[i][j]);
        grid[i][j].setOccupied(true);
        grid[i][j].tile = temp;
        tiles.add(temp);
        this.tilePlaced = true;
        this.bufferPlaced = false;
        
        
        repaint();        
    }
    
    private void placeBufferedTile (ArrayList<Tile> tiles, int i, int j){        
        Tile temp = buffer;
        temp.setPosition(grid[i][j]);
        grid[i][j].setOccupied(true);
        grid[i][j].tile = temp;
        tiles.add(temp);
        this.tilePlaced = true;
        buffer = tileset.getTile(NONE);
        buffer.setPosition(rectBuffer);
        //buffer.setType(26);
        repaint();        
    } 
    
    public void addPoints(int player, int points){
        if(player>this.points.length){
            System.out.println("zbyt duży numer gracza przy nadawaniu punktów.");
            return;
        }
        if(player<0){
            System.out.println("Zbyt mały numer gracza przy nadawaniu punktów");
            return;
        }
        if(points<0){
            System.out.println("Błąd! Ujemna liczba punktów");
            return;
        }
        
        this.points[player]+= points;
    }
    
    public int getPoints(int player){
        if(player>this.points.length){
            System.out.println("zbyt duży numer gracza.");
            return 0;
        }
        if(player<0){
            System.out.println("Zbyt mały numer gracza.");
            return 0;
        }
        return this.points[player];
    }
    
    public Tile pickTile(ArrayList<Integer> pile){
        //tutej dodać ifa
        Tile tileTemp = tileset.getTile(pile.get(tileCounter));   
        tileCounter++;
        this.bufferPlaced = true;
        this.tilePlaced = false;
        return tileTemp;
    }
    
    
    @Override
    public void paintComponent(Graphics g){
        
        // init paint
        if(startPaint == false){
        g.drawImage(img, 0, 0, 1100, 750, null);
        //Graphics2D g2d = (Graphics2D) g;
        for(int i=0; i<9; i++){
        test = tileset.getTile(i);
        //g.drawImage(test.getImage(), 30+i*90, 30, null);              
        }
        // grid = createGrid(30, 30, 8, 7, 85); stare
        for(int j=0; j<gridWidth; j++){ // j<7
            for(int i=0; i<gridHeight; i++){ // i<8
                //g2d.fillRect(i, i, WIDTH, HEIGHT);
                g.fillRect(grid[i][j].x, grid[i][j].y, grid[i][j].width, grid[i][j].height);
            }
        }
        startPaint = true;
        }
        //g.drawImage(lastTile.getImage(), lastTile.getx(), lastTile.gety(), null);
        
        if(bufferPlaced==true)
            g.drawImage(buffer.getImage(), buffer.getx(), buffer.gety(), null);
        g.drawImage(pileTop.getImage(), pileTop.getx(), pileTop.gety(), null);
        
        for(int count=0; count<tiles.size(); count++){
        temp = tiles.get(count);
        
        g.drawImage(temp.getImage(), temp.getx(), temp.gety(), null);
        }
        
        for(Follower dude: dudes){
           g.drawImage(dude.getImage(), dude.getX(), dude.getY(), null);
        }
        
    }
    
    public void loadTiles(String s, int numTilesAcross){
        try{
        tileset2 = ImageIO.read(getClass().getResourceAsStream(s));
        } catch(Exception e){
            e.printStackTrace();
        }
        this.numTilesAcross = numTilesAcross;
             
        }
    
    public Field[][] createGrid(int x, int y, int rows, int cols, int tileSize){
        Field[][] temp = new Field[rows][cols];
        
        for(int j=0; j<cols; j++){
            
        for(int i=0; i<rows; i++){
            temp[i][j] = new Field(x+90*j, y+i*90, tileSize, tileSize); // tutaj zmieniłem x na y
        }
    }
    
        return temp;
    }
    
    private void update(){
        
    }
    
    private void draw(){
        
    }
    
    private void drawToScreen(){
        Graphics g2 = getGraphics();
        g2.drawImage(img, 0, 0, null);
        g2.dispose();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //System.out.println("Kliknąłeś w " + e.getPoint().x + ", " + e.getPoint().y);
        
        if(e.getButton() == MouseEvent.BUTTON1){
           
          if(rectTop.contains(e.getPoint()) && tileCounter<numTiles){
              System.out.println("Kliknąłeś w stosik " + tileCounter);
              
              //pileTop = tileset.getTile(CHURCH);
              //buffer = tileset.getTile(NONE)
              if(tilePlaced == true){ // niedokończone!
              buffer = pickTile(pile); 
              buffer.setPosition(rectBuffer);
              System.out.println("Kafelek typu " + buffer.getType());
              tilePlaced = false;
              }
              //buffer.setImage();
             repaint();    
             
          } 
          boolean flag = false;
          
          // breakpoint
          
          // kładzenie kafelków na planszy
          for(int i=0; i<gridHeight; i++){
              for(int j=0; j<gridWidth; j++){
                  if(grid[i][j].contains(e.getPoint())&&tilePlaced==false&&grid[i][j].isOccupied()==false){
                      
                      System.out.println(i + ", " + j + " : " + grid[i][j].isOccupied());
                      System.out.println("Kwadrat o wsp. x=" + grid[i][j].getX() + ", y=" + grid[i][j].getY());
                      System.out.println(buffer.getType());
                      
                      if(inspector.bordersRight(buffer, grid, i, j)){
                      placeBufferedTile(tiles, i , j);  
                      tilePlaced = true; // ogarnąć
                      
                      flag=true;
                      // odświeżanie statusu sąsiednich dróg.
                      System.out.println("Teraz wjedzie inspektor <lenny>");
                      dudeFlag = false;
                      inspector.updateRoads(this.grid, i, j);
                      }
                      //for(int g=0; g<8; g++){
                      //    for(int h=0; h<5; h++){
                      //        System.out.println("Pole " + g + ", " + h + " : " + grid[g][h].isOccupied);
                      //    }    
                      //}
                  }                      
              }                      
              if(flag)
                  break;
          }               
        }
        
        // obrót kliknięciem prawego przycisku na stosiku...
        if(e.getButton() == MouseEvent.BUTTON3){           
          //if(rectBuffer.contains(e.getPoint())&& tileCounter<numTiles){
              //System.out.println("Kliknąłeś w aktualny tajel...");
              System.out.println("obrót aktualnego kafelka...");
              
              if(this.tilePlaced == false)
                buffer.rotate();
              repaint();
              
          }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
        // testuję kładzenie nowych dude'ów
        if(e.getButton() == MouseEvent.BUTTON2){
                   
            for(int i=0; i<Board.gridHeight; i++){
              for(int j=0; j<Board.gridWidth; j++){
                  if(grid[i][j].contains(e.getPoint())&&grid[i][j].isOccupied()==true&&dudeFlag==false){
                      System.out.println("No elo coś się xD stało");
                      int temp = 0;
                      boolean flagPlaced = false;
                      
                      for(int g=0; g<9; g++){
                          if(grid[i][j].tile.slots[g].contains(e.getPoint())&&grid[i][j].tile.slots[g].enabled==true)
                          {
                            dudes.add(new Follower((int) grid[i][j].tile.slots[g].getX(), (int) grid[i][j].tile.slots[g].getY()));
                            repaint();
                            flagPlaced = true;
                            temp = g;
                            dudeFlag = true;
                            break;
                          }
                      }
                      
                      //Oddzielona funkcja do nadawania punktów
                      if(flagPlaced){
                          //jakakolwiek domyślna wartość.
                        inspector.spill(grid, i, j, grid[i][j].tile.slots[temp], 0, temp);
                      }   
                      
                      System.out.println("Typ kafelka: " + buffer.getType());
                      
                  }                      
              }                      
              
          }
            
        }
    
    }

    @Override
    public void mousePressed(MouseEvent e) {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void setDudeFlag(boolean status){
        this.dudeFlag = status;
    }
    
}
