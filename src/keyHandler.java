import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class keyHandler implements KeyListener {
    BoardPanel panel;

    public keyHandler (BoardPanel panel)
    {
        this.panel = panel;
    }

    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        //panel.resetBoard();

        switch (e.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                System.out.println("Solve");
                panel.start();
                break;
            case KeyEvent.VK_C:
                if (panel.solved)
                {
                    panel.resetBoard();
                }
                break;
            case KeyEvent.VK_BACK_SPACE:
                panel.changeArrayVal(0);
                break;
            default:
                //parse value of char if its between 1 - 9
                char c = e.getKeyChar();
                int val = c - '0';

                if (val >= 1 && val <= 9)
                {
                    panel.changeArrayVal(val);
                }
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {

    }
}