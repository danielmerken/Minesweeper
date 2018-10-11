import java.lang.Math;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;

/**
 * @author DanielMerken
 */

public class Board extends JPanel {
   
   private final int GRID_SIZE = 20;
   private final int BOMB_COUNT = 70; 
   private final int CELL_SIZE = 20;
    
   private Random rand;
   private Cell[][] cells;
   private boolean gameOver = false;
   private JFrame frame;

   public Board(JFrame frame) {
	   this.frame = frame;
	   
      setBackground(Color.black);
      setFocusable(true);
      setPreferredSize(new Dimension(GRID_SIZE * CELL_SIZE, GRID_SIZE * CELL_SIZE));
      
      rand = new Random();
      cells = generateCells();
      
      addMouseListener(new MouseAdapter() {
         public void mousePressed(MouseEvent e){
            if (!gameOver) {
               for (int i = 0; i < GRID_SIZE; i++) {
                  for (int j = 0; j < GRID_SIZE; j++) {
                     if (cells[i][j].contains(e.getPoint())) {
                        if ((e.isControlDown() && 
                            SwingUtilities.isLeftMouseButton(e)) ||
                            SwingUtilities.isRightMouseButton(e)) {
                           cells[i][j].toggleFlag();
                        } else if (SwingUtilities.isLeftMouseButton(e)) {
                           if (!cells[i][j].isExposed()) {
                              if (cells[i][j].isBomb()) {
                                 cells[i][j].setExposed();
                                 gameOver(false);
                              } else {
                                 mine(i, j);
                              }
                           }
                        }
                     }
                  }
               }
               repaint();
            }
         }
      });
   }
   
   private void mine(int x, int y) {
      cells[x][y].setExposed();
      if (checkVictory()) {
         gameOver(true);
      } else if (cells[x][y].toString().equals("0")) {
         for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
               if (inBounds(i, j) && !cells[i][j].isExposed()) {
                  mine(i, j);
               }
            }
         }
      }
   }
   
   private void gameOver(Boolean win) {
	   repaint();
	   gameOver = true;
	   String text;
	   if (win) {
		   text = "You Win!";
	   } else {
		   text = "You Lose!";
	   }
	   JOptionPane.showMessageDialog(frame, text);
   }
   
   private boolean checkVictory() {
      for (int i = 0; i < GRID_SIZE; i++) {
         for (int j = 0; j < GRID_SIZE; j++) {
            if (!cells[i][j].isExposed() && !cells[i][j].isBomb()) {
               return false;
            }
         }
      }
      return true;
   }
   
   private boolean inBounds(int x, int y) {
      return x >= 0 && y >= 0 && x < GRID_SIZE && y < GRID_SIZE;
   }
   
   /**
    * Constructsa 2d array of cells to make up a boggle board
    * @return a 2d array of cells representing a boggle board with random
    * cells set to be bombs
    */
   private Cell[][] generateCells() {
      if (GRID_SIZE > Math.pow(BOMB_COUNT, 2)) {
         throw new IllegalArgumentException("Too many bombs to fit in grid");
      }
      Cell[][] result = new Cell[GRID_SIZE][GRID_SIZE];
      // Create cells
      for (int i = 0; i < GRID_SIZE; i++) {
         for (int j = 0; j < GRID_SIZE; j++) {
            result[i][j] = new Cell(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE);
         }
      }
      // Select bombs
      for (int i = 0; i < BOMB_COUNT; i++) {
         int x = rand.nextInt(GRID_SIZE);
         int y = rand.nextInt(GRID_SIZE);
         if (!result[x][y].isBomb()) {
            result[x][y].setBomb();
         }
      }
      // Label cells
      for (int i = 0; i < GRID_SIZE; i++) {
         for (int j = 0; j < GRID_SIZE; j++) {
            if (result[i][j].isBomb()) {
               result[i][j].setString("B");
            } else {
               int count = 0;
               for (int u = i - 1; u <= i + 1; u++) {
                  for (int v = j - 1; v <= j + 1; v++) {
                     if (inBounds(u, v) &&
                         result[u][v].isBomb()) {
                         count++;
                     }
                  }
               }
               result[i][j].setString(Integer.toString(count));
            }
         }
      }
      return result;
   }
   
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g;
      for (int i = 0; i < GRID_SIZE; i++) {
         for (int j = 0; j < GRID_SIZE; j++) {
            cells[i][j].draw(g2);
         }
      } 
   }
   
   /**
    * Restarts the board
    */
   public void restart() {
      cells = generateCells();
      gameOver = false;
      revalidate();
      repaint();
   }
}