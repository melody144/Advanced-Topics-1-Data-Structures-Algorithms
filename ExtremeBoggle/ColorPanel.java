import java.io.*;
import java.util.*;
import java.lang.*;
import java.text.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Title: Color Panel
 * Description: This is the Color Panel for the Boggle board. It draws the board + all the solutions
 * Created: 2025-10-28
 * Last Updated: 2025-10-29
 * Author(s): Melody Wu
*/

public class ColorPanel extends JPanel {
    private Rectangle boardButton; // button that you can generate a new board
    private Rectangle solveButton; // button that you can generate the solves

    private boolean newBoard; // if we are generating a new board
    private boolean solveBoard; // if we are writing solves

    private ExtremeBoggle game;

    public ColorPanel(Color color) {
        game = new ExtremeBoggle(); // makes a new Extreme Boggle game

        setBackground(color);
        boardButton = new Rectangle(150, 675, 190, 35);
        solveButton = new Rectangle(410, 675, 190, 35);

        newBoard = false;
        solveBoard = false;

        // mouse listener to detect clicks
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Point p = e.getPoint(); // where the mouse was clicked

                // Check if click is inside the board generator button
                if (boardButton.contains(p)) {
                    newBoard = true;
                    repaint();
                }

                // Check if click is inside the solve generator button
                if (solveButton.contains(p)) {
                    solveBoard = true;
                    repaint();
                }
            }
        });
    }

    // drawing everything
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // draw the buttons
        g.setColor(Color.pink);
        g.fillRoundRect(boardButton.x, boardButton.y, boardButton.width, boardButton.height, 15, 15);
        g.fillRoundRect(solveButton.x, solveButton.y, solveButton.width, solveButton.height, 15, 15);
        
        // draw the box where we put the solutions
        g.setColor(new Color(112, 180, 212)); // light blue color
        g.fillRect(650, 25, 325, 675);

        // drawing the grid + grid lines
        g.setColor(Color.black);
        g.drawRect(125, 100, 500, 500);
        for(int i=2; i<=5; i++){
            g.drawLine(125, 100*i, 625, 100*i);
            g.drawLine(25+100*i, 100, 25+100*i, 600);
        }
        
        // big labels
        Font font1 = new Font("Times New Roman", Font.BOLD, 30);
        g.setFont(font1);
        g.drawString("Extreme Boggle Board", 200, 55);
        g.drawString("All Words", 660, 60);

        // button labels
        Font font2 = new Font("Times New Roman", Font.BOLD, 25);
        g.setFont(font2);
        g.drawString("Generate Board", boardButton.x+10, 700);
        g.drawString("Generate Solve", solveButton.x+10, 700);

        if(newBoard){
            game.makeNewBoard();
            newBoard=false;
        }

        // draw the letters in the board
        game.getBoard().drawBoard(0, 0, g);

        if(solveBoard){
            ArrayList<String> words = game.getBoard().getAllWords();
            solveBoggle(words, g);
            solveBoard=false;
        }
    }

    // method that draws all the solutions to the board
    private void solveBoggle(ArrayList<String> words, Graphics g){
        // write total number of words
        Font font3 = new Font("Times New Roman", Font.BOLD, 10);
        g.setFont(font3);
        g.drawString("Total words found: " + words.size(), 660, 75);

        Font font4 = new Font("Times New Roman", Font.BOLD, 12);
        g.setFont(font4);
        FontMetrics fm = g.getFontMetrics(font4); // measures the font currently in use

        // turning the arraylist of all the words into individual lines that fit in the box
        // adding them to a string, and then starting a new string if it gets to long
        ArrayList<String> lines = new ArrayList<>();
        int index = 0; // which line we're on
        lines.add(0, words.get(0));
        for(int i=1; i<words.size(); i++){
            // if too long --> start new string
            if(fm.stringWidth(lines.get(index))+fm.stringWidth(", ")+fm.stringWidth(words.get(i))>305){
                index++;
                lines.add(index, words.get(i));
            } else { // otherwise, add it to the current string
                lines.set(index, lines.get(index)+", "+words.get(i));
            }
        }

        // printing each line on a new line
        for(int i=0; i<lines.size(); i++){
            g.drawString(lines.get(i), 660, 80+12*(i+1));
        }
    }
}