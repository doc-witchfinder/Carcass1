/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackness2;

import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;

/**
 *
 * @author lenovo
 */
public class Application extends JFrame {
    
    public Application(){
        initUI();        
    }
    
    public void initUI(){
        
        add(new Board());
        setSize(1100, 750);
                        
        setTitle("Carcassone1");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    public static void main(String[] args){
        
        EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                Application ex = new Application();
                ex.setVisible(true);
            }
        });
        
    }

    
    
}
