import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main {
    public static void loadWindow (BoardPanel newGame, JFrame window)
    {
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setLocationRelativeTo(null);
        window.setPreferredSize(new Dimension(700,700));
        window.pack();
        window.setVisible(true);

        //Adds the JPanel to the Frame
        window.add(newGame);
    }

    public static void main(String[] args)
    {
        JFrame window = new JFrame("Sudoku-Solver");
        BoardPanel newGame = new BoardPanel(window);
        loadWindow(newGame, window);

        //Adds mouse Listener to the JPanel (Always Running)
        newGame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int mouseX = e.getPoint().x;
                int mouseY = e.getPoint().y;

                newGame.changeValue(mouseX, mouseY);
            }
        });
    }
}