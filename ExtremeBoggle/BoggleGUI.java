import java.io.*;
import java.util.*;
import java.lang.*;
import java.text.*;
import javax.swing.*;
import java.awt.*;

/**
 * Title: Boggle GUI
 * Description: This is the GUI for the Boggle board
 * Created: 2025-10-28
 * Last Updated: 2025-10-29
 * Author(s): Melody Wu
*/

public class BoggleGUI{
    
    public static void main(String[]args){
        // frame object --> overall canvas
        JFrame theGUI = new JFrame();
        theGUI.setTitle("Extreme Boggle");
        theGUI.setSize(1000, 750);
        theGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ColorPanel panel = new ColorPanel(Color.lightGray);
        
        Container pane = theGUI.getContentPane();
        pane.add(panel);
        
        theGUI.setVisible(true); // background + frame appears
    }
    
}
