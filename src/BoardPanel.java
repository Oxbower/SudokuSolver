import javax.swing.*;
import java.awt.*;

/**
 * Draws the board and UI for the sudoku solver
 * */

public class BoardPanel extends JPanel implements Runnable {
    int board_Width = 450;
    int board_Height = 450;
    int boxSize = 50;
    int nine_by_nine = 150;
    boolean solved;

    int startingCoordRow;
    int startingCoordColumn;

    int clickedRowCoord;
    int clickedColumnCoord;

    int FPS = 60;

    JFrame window;
    Thread gameThread;
    GameLogic logic;

    keyHandler key = new keyHandler(this);

    public BoardPanel (JFrame window)
    {
        this.window = window;
        starGameThread();
        logic = new GameLogic(this);
        this.addKeyListener(key);
        this.setFocusable(true);
    }

    //Sudoku Board
    /*
    * No same number in a row or column, no same number inside one of the squares for the 9 x 9
    * */
    int [][] boardValues =
        {
                //row, column
                {0, 0, 0,   0, 0, 0,   0, 0, 0},
                {0, 0, 0,   0, 0, 0,   0, 0, 0},
                {0, 0, 0,   0, 0, 0,   0, 0, 0},

                {0, 0, 0,   0, 0, 0,   0, 0, 0},
                {0, 0, 0,   0, 0, 0,   0, 0, 0},
                {0, 0, 0,   0, 0, 0,   0, 0, 0},

                {0, 0, 0,   0, 0, 0,   0, 0, 0},
                {0, 0, 0,   0, 0, 0,   0, 0, 0},
                {0, 0, 0,   0, 0, 0,   0, 0, 0},
        };

    public void resetBoard ()
    {
        for (int row = 0; row < boardValues.length; row++) {
            for (int col = 0; col < boardValues[row].length; col++) {
                boardValues[row][col] = 0;
            }
        }
    }

    public void start () {
        //Create an initial check to see if board is valid
        logic.accessArray();
        if (logic.check_valid())
        {
            solved = logic.solve(0,0);
        }
        else
        {
            solved = false;
        }
        if (solved) {
            System.out.println("Done!");
        }
        else
        {
            System.out.println("Invalid Board!");
        }
    }

    public int[][] returnBoard ()
    {
        return boardValues;
    }

    //Checks if the mouse position is inside a box
    public void changeValue (int mouseX, int mouseY)
    {
        int rowCoord = (mouseY - startingCoordRow);
        int columnCoord = (mouseX - startingCoordColumn);

        clickedRowCoord = (int)Math.floor((rowCoord + .0) / boxSize);
        clickedColumnCoord = (int)Math.floor((columnCoord + .0) / boxSize);

        //Works as intended don't change this mark!!
//        if (clickedRowCoord <= 8 && clickedColumnCoord <= 8 && clickedRowCoord >= 0 && clickedColumnCoord >= 0) {
//            System.out.println("Row: " + clickedRowCoord + " Column: " + clickedColumnCoord);
//        }
    }

    //Changed directly by keyHandler
    public void changeArrayVal (int num) {
        if (clickedRowCoord <= 8 && clickedRowCoord >= 0 && clickedColumnCoord <= 8 && clickedColumnCoord >= 0)
        {
            boardValues[clickedRowCoord][clickedColumnCoord] = num;
        }

//        for (int i = 0; i < boardValues.length; i++)
//        {
//            System.out.println(Arrays.toString(boardValues[i]));
//        }
    }

    public void update ()
    {
        startingCoordRow = (getHeight() / 2) - (board_Height / 2);
        startingCoordColumn = (getWidth() / 2) - (board_Width / 2);
    }

    public void drawBoard (Graphics2D g)
    {
        //Draws the small squares
        for (int i = 0; i < boardValues.length; i++)
        {
            float thickness = 4;
            g.setStroke(new BasicStroke(thickness));
            Stroke oldStroke = g.getStroke();
            //System.out.println("row: " + i);
            for (int j = 0; j < boardValues[i].length; j++)
            {
                g.setColor(Color.black);
                g.drawRect((boxSize * j) + startingCoordColumn, (boxSize * i) + startingCoordRow , boxSize, boxSize);
                g.setStroke(oldStroke);
            }
        }
        //Draws 9 x 9 square
        for (int l = 0; l < 3; l++)
        {
            float thickness = 6;
            g.setStroke(new BasicStroke(thickness));
            Stroke oldStrokeNine = g.getStroke();
            for (int k = 0; k < 3; k++)
            {
                g.setColor(new Color(199, 43, 43));
                g.drawRect((nine_by_nine * k) + startingCoordColumn, (nine_by_nine * l) + startingCoordRow , nine_by_nine, nine_by_nine);
                g.setStroke(oldStrokeNine);
            }
        }
        //Draws the green outline around selected box
        if (clickedRowCoord <= 8 && clickedColumnCoord <= 8 && clickedRowCoord >= 0 && clickedColumnCoord >= 0) {
            float thickness = 10;
            g.setStroke(new BasicStroke(thickness));
            Stroke oldStroke = g.getStroke();
            g.setColor(Color.green);
            g.drawRect((boxSize * clickedColumnCoord) + startingCoordColumn, (boxSize * clickedRowCoord) + startingCoordRow , boxSize, boxSize);
            g.setStroke(oldStroke);
        }
    }

    public void drawNum (Graphics2D g)
    {
        //draws the number inside the 2d array
        int fontSize = 40;
        Font f = new Font("SansSerif.plain", Font.BOLD, fontSize);
        g.setColor(Color.black);
        g.setFont(f);
        for (int i = 0; i < boardValues.length; i++)
        {
            for (int j = 0; j < boardValues[i].length; j++)
            {
                if (boardValues[i][j] != 0) {
                    g.drawString("" + boardValues[i][j], ((boxSize * j) + startingCoordColumn) + (boxSize / 2) - 12, ((boxSize * i) + startingCoordRow) + (boxSize / 2) + 15);
                }
            }
        }
    }

    public void paintComponent (Graphics g)
    {
        //Pass into parentClass JPanel
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        drawBoard(g2);
        drawNum(g2);
        //Recycle (draw)
        g2.dispose();
    }

    public void starGameThread ()
    {
        gameThread = new Thread(this);
        gameThread.start(); // Calls run method
    }

    @Override
    public void run ()
    {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null)
        {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                //System.out.println(drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }
}
