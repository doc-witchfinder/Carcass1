/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackness2;

import java.awt.Rectangle;
import java.util.ArrayList;

/**
 *
 * @author lenovo
 */
public class BoardInspector {
    
    private Board board;
    
    BoardInspector(Board board){
        this.board = board;
    }
    
    public boolean bordersRight(Tile buffer, Field[][] grid, int i, int j){
        
        int width = Board.gridWidth;
        int height = Board.gridHeight;
        
        boolean vicinity = false;
        boolean resultLeft, resultTop, resultRight, resultBottom;
        resultLeft = resultTop = resultRight = resultBottom = false;
        
        if(grid[i][j].isOccupied())
            return false;
        
        // lewa strona
        if(j>0){
            if(grid[i][j-1].isOccupied()==false){
                resultLeft = true;
                System.out.println("Lewa pusta.");
            } else {
                vicinity = true;
                System.out.println("Lewo: granica_obok = " + grid[i][j-1].tile.getBorderRight() + ", granica_środek = " + buffer.getBorderLeft());
                if(grid[i][j-1].isOccupied()==true&&grid[i][j-1].tile.getBorderRight()==buffer.getBorderLeft()){
                resultLeft = true;
                }
            }
        } else if(j<=0){
                System.out.println("Lewa poza planszą.");
                resultLeft = true;
            }
        
        // góra
        if(i>0){
            if(grid[i-1][j].isOccupied()==false){
                resultTop = true;
                System.out.println("Góra pusta.");
            } else {
                vicinity = true;
                System.out.println("Góra: granica_obok = " + grid[i-1][j].tile.getBorderBottom() + ", granica_środek = " + buffer.getBorderTop());
                if(grid[i-1][j].isOccupied()==true&&grid[i-1][j].tile.getBorderBottom()==buffer.getBorderTop()){
                resultTop = true;
                
                }
            } 
        } else if(i<=0){
                System.out.println("Góra poza planszą.");
                resultTop = true;
            }
        
        // prawo o/o/o/
        if(j<width-1){
            if(grid[i][j+1].isOccupied()==false){
                resultRight = true;
                System.out.println("Prawo puste.");
            } else {
                vicinity = true;
                System.out.println("Prawo: granica_obok = " + grid[i][j+1].tile.getBorderLeft() + ", granica_środek = " + buffer.getBorderRight());
                if(grid[i][j+1].isOccupied()==true&&grid[i][j+1].tile.getBorderLeft()==buffer.getBorderRight()){
                resultRight = true;
                }
            }
        } else if(j>=width-1){
                System.out.println("Prawo poza planszą.");
                resultRight = true;
            }
        
        // dół
        if(i<height-1){
            if(grid[i+1][j].isOccupied()==false){
                resultBottom = true;
                System.out.println("Dół pusty.");
            } else {
                System.out.println("Dół: granica_obok = " + grid[i+1][j].tile.getBorderTop() + ", granica_środek = " + buffer.getBorderBottom());
                vicinity = true;
                if(grid[i+1][j].isOccupied()==true&&grid[i+1][j].tile.getBorderTop()==buffer.getBorderBottom()){
                resultBottom = true;
                }
            }  
        }
        else if(i>=height-1){
                System.out.println("Dół poza planszą.");
                resultBottom = true;
            }
        
        if(vicinity == false){
            System.out.println("n i e p o p r a w n e pole, brak sąsiadów,,,,,!!!1");
            return false;
        }
        if(resultTop&&resultRight&&resultBottom&&resultLeft)
            return true;
        else
            return false;
    }
    
    
    // raczej nieaktualne
    public void  checkRoads(Field[][] grid, int i, int j){
        
       if(grid[i-1][j].isOccupied()&&grid[i-1][j].tile.getBorderRight()==1){
          if(grid[i-1][j].tile.slots[Tile.RIGHT].player!=Slut.NO_PLAYER){
              grid[i][j].tile.slots[Tile.LEFT].player=grid[i-1][j].tile.slots[Tile.RIGHT].player;
              if(grid[i-1][j].tile.getRoadEnds()==false){
                  
              }
          }
                    
       }
       if(grid[i+1][j].isOccupied()){
          // co teras? 
       }
       if(grid[i][j+1].isOccupied()){
          // co teras? 
       }
       if(grid[i][j-1].isOccupied()){
          // co teras? 
       }  
    }
    
    // !!!
    // Mechanika:
    // 1. Sprawdzić czy któryś sąsiad z drogą ma nadany kolor - updateRoads(xxx)
    // Jeżeli tak - ofensywa koloru - spill(xxx)
    // Zrobić tak dla każdego koloru który jest nadany i tak jakby dla każdego 
    // sąsiada
    
    // update roads jest wywoływane przy okazji akcji kładzenia kafelka na planszy
    
    public void updateRoads(Field[][] grid, int i, int j){
        
        for(int player=0; player<Board.noPlayers; player++){
        // współrzędne javove - x idzie w prawo, y w dół.
        // sprawdzam z góry
        if(i>0){
            if(grid[i][j].tile.getBorderTop()==Tile.BORDER_ROAD&&grid[i-1][j].isOccupied()){
                System.out.println("Z góry droga, sprawdzam...");
                if(grid[i-1][j].tile.slots[Tile.BOTTOM].players[player]==true&&grid[i][j].tile.slots[Tile.TOP].players[player]==false){
                    spill(grid, i-1, j, grid[i-1][j].tile.slots[Tile.BOTTOM], player, Tile.BOTTOM);   
                    System.out.println("Poszuo?");
                }
            }
        }
        // sprawdzam z prawej
        if(j<Board.gridWidth-1){
            if(grid[i][j].tile.getBorderRight()==Tile.BORDER_ROAD&&grid[i][j+1].isOccupied()){
                System.out.println("Z prawej droga, sprawdzam...");
                if(grid[i][j+1].tile.slots[Tile.LEFT].players[player]==true&&grid[i][j].tile.slots[Tile.RIGHT].players[player]==false)
                    spill(grid, i, j+1, grid[i][j+1].tile.slots[Tile.LEFT], player, Tile.LEFT);
            }
        }
        // sprawdzam z dołu
        if(i<Board.gridHeight-1){
            if(grid[i][j].tile.getBorderBottom()==Tile.BORDER_ROAD&&grid[i+1][j].isOccupied()){
                System.out.println("Z dołu droga, sprawdzam...");
                if(grid[i+1][j].tile.slots[Tile.TOP].players[player]==true&&grid[i][j].tile.slots[Tile.BOTTOM].players[player]==false)
                    spill(grid, i+1, j, grid[i+1][j].tile.slots[Tile.TOP], player, Tile.TOP);
            }
        }
        // sprawdzam z lewej
        if(j>0){
            if(grid[i][j].tile.getBorderLeft()==Tile.BORDER_ROAD&&grid[i][j-1].isOccupied()){
                System.out.println("Z lewej droga, sprawdzam...");
                if(grid[i][j-1].tile.slots[Tile.RIGHT].players[player]==true&&grid[i][j].tile.slots[Tile.LEFT].players[player]==false)
                    spill(grid, i, j-1, grid[i][j-1].tile.slots[Tile.RIGHT], player, Tile.RIGHT);
                
            }
        }
        }
        
    }
    
    // raczej nieaktualne
    public void updatePlayers(Slut slot1, Slut slot2){
        if(slot1.player1==false&&slot2.player1)
            slot1.player1=true;
        if(slot1.player2==false&&slot2.player2)
            slot1.player2=true;
        if(slot1.player3==false&&slot2.player3)
            slot1.player3=true;
        if(slot1.player4==false&&slot2.player4)
            slot1.player4=true;
    }
    
    // Mechanika: najpierw 
    
    // chuj jebać biedę
    public void spill(Field[][] grid, int i, int j, Slut slot1, int player, int position){
        // slot1 to slot w którym rozpocznie się spilling
        // player to gracz dla którego sprawdzamy.
        // postition to krawędź od której rozpocznie się spilling
        
        if(slot1.players[player]==false){
        slot1.players[player]=true;
        slot1.occupied=true;
        board.addPoints(player, 1);
        }
        
        
        // Pomocnicza funkcja
        System.out.println("Punkty z drogi gracza " + player +": " + board.getPoints(player));
        
        if (grid[i][j].tile.getRoadEnds()==true){
            switch(position){
                //ten switch wymusza 'odwrót' przy napotkaniu skrzyżowania i okupowanie dróg w kierunku powrotnym.
                //sprawdzanie zwrotne?
                case Tile.RIGHT:
                        // napisać funkcję do sprawdzania czy granice istnieją z danej strony...
                        if(j<Board.gridWidth-1)
                               if(grid[i][j+1].isOccupied())
                                spill(grid, i, j+1, grid[i][j+1].tile.slots[Tile.LEFT], player, Tile.LEFT);
                    break;
                case Tile.BOTTOM:
                        // TODO
                        if(i<Board.gridHeight-1)
                            if(grid[i+1][j].isOccupied())
                                spill(grid, i+1, j, grid[i+1][j].tile.slots[Tile.TOP], player, Tile.TOP);
                    break;
                case Tile.LEFT:
                        // TODO
                        if(j>0)
                            if(grid[i][j-1].isOccupied())
                                spill(grid, i, j-1, grid[i][j-1].tile.slots[Tile.RIGHT], player, Tile.RIGHT);
                    break;
                case Tile.TOP:
                        // TODO
                        if(i>0)
                            if(grid[i-1][j].isOccupied())
                                spill(grid, i-1, j, grid[i-1][j].tile.slots[Tile.BOTTOM], player, Tile.BOTTOM);
                    break;
            }
            
        } else {
                    // nadawanie true slotowi na drugim końcu drogi.
                    
                    
                    // rozlewanie w górę
                    // if(grid[i][j].tile.getBorderTop()==Tile.BORDER_ROAD&&grid[i][j].tile.slots[Tile.TOP].players[player]==false){
                    if(grid[i][j].tile.getBorderTop()==Tile.BORDER_ROAD){
                        grid[i][j].tile.slots[Tile.TOP].players[player]=true;
                        if(i>0){
                            if(grid[i-1][j].isOccupied()){
                                
                                if(grid[i-1][j].tile.slots[Tile.BOTTOM].players[player]==false){
                                // rozlewanie dalej
                                spill(grid, i-1, j, grid[i-1][j].tile.slots[Tile.BOTTOM], player, Tile.BOTTOM);
                                }
                            }
                        }
                    }
                    
                    // rozlewanie w prawo
                    if(grid[i][j].tile.getBorderRight()==Tile.BORDER_ROAD){
                        grid[i][j].tile.slots[Tile.RIGHT].players[player]=true;
                        if(j<Board.gridWidth-1){
                            if(grid[i][j+1].isOccupied()){
                                
                                if(grid[i][j+1].tile.slots[Tile.LEFT].players[player]==false){
                                // rozlewanie dalej
                                spill(grid, i, j+1, grid[i][j+1].tile.slots[Tile.LEFT], player, Tile.LEFT);
                                }
                            }
                        }
                    }
                    
                    // rozlewanie w dół
                    if(grid[i][j].tile.getBorderBottom()==Tile.BORDER_ROAD){
                        grid[i][j].tile.slots[Tile.BOTTOM].players[player]=true;
                        if(i<Board.gridHeight-1){
                            if(grid[i+1][j].isOccupied()){
                                
                                if(grid[i+1][j].tile.slots[Tile.TOP].players[player]==false){
                                // rozlewanie dalej
                                spill(grid, i+1, j, grid[i+1][j].tile.slots[Tile.TOP], player, Tile.TOP);
                                }
                            }
                        }
                    }
                    
                    
                    // rozlewanie w lewo
                    if(grid[i][j].tile.getBorderLeft()==Tile.BORDER_ROAD){
                        grid[i][j].tile.slots[Tile.LEFT].players[player]=true;
                        if(j>0){
                            if(grid[i][j-1].isOccupied()){
                                
                                if(grid[i][j-1].tile.slots[Tile.RIGHT].players[player]==false){
                                // rozlewanie dalej
                                spill(grid, i, j-1, grid[i][j-1].tile.slots[Tile.RIGHT], player, Tile.RIGHT);
                                }
                            }
                        }
                    }
                    // rozlewanie w prawo
//                    if(j<Board.gridWidth-1){
//                        if(grid[i][j+1].isOccupied())
//                        {
//                            if(grid[i][j].tile.getBorderRight()==Tile.BORDER_ROAD&&grid[i][j+1].tile.slots[Tile.LEFT].players[player]==false){
//                                grid[i][j].tile.slots[Tile.RIGHT].players[player]=true;
//                                // nadanie true graczowi na drugim końcu drogi.
//                                spill(grid, i, j+1, grid[i][j+1].tile.slots[Tile.LEFT], player, Tile.LEFT);
//                            }
//                        }
//                    }
//                    
//                    // rozlewanie w dół
//                    if(i<Board.gridHeight-1){
//                        if(grid[i+1][j].isOccupied())
//                        {
//                            if(grid[i][j].tile.getBorderBottom()==Tile.BORDER_ROAD&&grid[i+1][j].tile.slots[Tile.TOP].players[player]==false){
//                                grid[i][j].tile.slots[Tile.BOTTOM].players[player]=true;
//                                // nadanie true graczowi na drugim końcu drogi.
//                                spill(grid, i+1, j, grid[i+1][j].tile.slots[Tile.TOP], player, Tile.TOP);
//                            }
//                        }
//                    }
//                    
//            }       // rozlewanie w lewo
//                    if(j>0){
//                        if(grid[i][j-1].isOccupied())
//                        {
//                            if(grid[i][j].tile.getBorderLeft()==Tile.BORDER_ROAD&&grid[i][j-1].tile.slots[Tile.RIGHT].players[player]==false){
//                                grid[i][j].tile.slots[Tile.LEFT].players[player]=true;
//                                // nadanie true graczowi na drugim końcu drogi.
//                                spill(grid, i, j-1, grid[i][j-1].tile.slots[Tile.RIGHT], player, Tile.RIGHT);
//                            }
//                        }
                   }
            
            // Jeżeli nie ma skrzyżowania lub końca drogi (patrz wyżej)
            
            // 1. należy nadać gracza slotowi (Slutowi :3) na drugim końcu drogi
            
            // 2. W przyszłości dodać do punktacji dla danego gracza?
            
            // 3. Zrobić Spill dla sąsiada przy drugim końcu drogi, albo
            // sprawdzać po kolei albo od razu w wybrany (tylko trzeba najpierw 
            // określić który to. <3 buziaczki   
        
    }
    
    public void rotateTile(){
        
    }
    
    public void placeDude(int x, int y){
                
    }
    
    
}
