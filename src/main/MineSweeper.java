import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * 
 * @author DanielMerken
 *
 */
public class MineSweeper extends JFrame implements ActionListener{
   
   private Board board;
   
   public MineSweeper () {
      board = new Board(this);
      add(board, BorderLayout.CENTER);
      JButton playAgainButton = new JButton("Play Again");
      add(playAgainButton, BorderLayout.PAGE_END);
      playAgainButton.addActionListener(this);
      pack();
      setTitle("Mine Sweeper");
      setLocationRelativeTo(null);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }
     
   public void actionPerformed(ActionEvent e) {
      board.restart();
   }
    
   public static void main(String[] args) {
      JFrame game = new MineSweeper();
      game.setVisible(true);
   }
}