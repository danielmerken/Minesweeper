import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.Color;

/**
 * Class representing a cell in a minesweeper board. Tracks whether the cell
 * is a bomb, if its flagged, if its exposed, and its string representation.
 * Extends rectangle to
 * @author DanielMerken
 */
public class Cell extends Rectangle {
   private boolean bomb;
   private boolean exposed;
   private boolean flag;
   private String rep;

   /**
    * Creates a new cell with the given x and y position in the grid. By
    * default, cell is not flagged, a bomb, or exposed.
    * @param x x-coordinate for cell to be drawn at
    * @param y y-coordinate for cell to be drawn at
    * @param size width and height of the cell to be drawn
    */
   public Cell(int x, int y, int size) {
      super(x, y, size, size);
      bomb = false;
      exposed = false;
      flag = false;
   }
   
   /**
    * If the cell is flagged, unflag it, else flag it.
    */
   public void toggleFlag() {
      flag = !flag;
   }
   
   /**
    * Set the current space to be a bomb
    */
   public void setBomb() {
      bomb = true;
   }
   
   /**
    * Set the current space to be exposed
    */
   public void setExposed() {
      exposed = true;
   }
   
   /**
    * Gets whether or not this cell is exposed
    * @return true if this cell is exposed, else returns false
    */
   public boolean isExposed() {
      return exposed;
   }
   
   /**
    * Gets whether or not this cell is a bomb
    * @return true if this cell is a bomb, else returns false
    */
   public boolean isBomb() {
      return bomb;
   }
   
   /**
    * Sets the string representation of this cell
    * @param rep the text that will be drawn on this cell
    */
   public void setString(String rep) {
      this.rep = rep;
   }
   
   @Override
   public String toString() {
      return rep;
   }
   
   public void draw(Graphics2D g2) {
      if (exposed) {
         g2.setColor(Color.white);
         g2.fill(this);
         g2.setColor(Color.black);
         g2.drawString(rep, super.x, super.y + super.height);
      } else {
         g2.setColor(Color.gray);
         g2.fill(this);
         if (flag) {
            g2.setColor(Color.black);
            g2.drawString("'B'", super.x, super.y + super.height);
         }
      }
      g2.setColor(Color.black);
      g2.draw(this);
   }
}