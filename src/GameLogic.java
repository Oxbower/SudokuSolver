public class GameLogic {
    BoardPanel panel;

    int repeat = 0;
    int [][] boardCopy;

    public GameLogic (BoardPanel panel)
    {
        this.panel = panel;
    }

    public void accessArray ()
    {
        boardCopy = panel.returnBoard();
    }

    public boolean check_valid ()
    {
        return validity_check();
    }

    public boolean validity_check ()
    {
        for (int i = 0; i < boardCopy.length; i++)
        {
            for (int j = 0; j < boardCopy[0].length; j++)
            {
                if (boardCopy[i][j] == 0)
                    continue;
                if(!check_cross(i, j, boardCopy[i][j]))
                {
                    System.out.println(String.format("Found Error at row:%d col:%d", i+1, j+1));
                    return false;
                }
            }
        }
        return true;
    }

    public boolean check_cross (int row, int col, int value)
    {
        for (int i = 0; i < 9; i++) {
            if (row != i && boardCopy[i][col] == value)
                return false;
        }
        for (int i = 0; i < 9; i++) {
            if (col != i && boardCopy[row][i] == value)
                return false;
        }
        int startingCol = col - (col % 3);
        //Takes current row# and subtracts the remainder of row# / 3
        int startingRow = row - (row % 3);
        //Figures out which 9x9 square the index is currently in
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if ((i + startingRow) != row && (j + startingCol) != col && boardCopy[i + startingRow][j + startingCol] == value)
                {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean solve (int row, int col)
    {
        //Do checks first for row and columns then the 9 x 9 squares
        //show all possible valid answers for each tile
        //Using recursion
        //Set flags for initial values set!!
        //Skip changing value if it isn't 0 in the original
        //If current val at curr row and col is equal 0
        //If row equals maxrows and col exceeds maxcols end algo
        if (row == 8 && col > 8)
        {
            return true;
        }
        //If col exceeds maxcols move onto next row
        if (col > 8)
        {
            row++;
            col = 0;
        }
        //Check if current arr index has a value greater than 0 if true
        //move onto next column using recursion which we return to if our
        //current solution is not possible
        if (boardCopy[row][col] > 0)
        {
            return solve(row, col + 1);
        }

        //If current arr index is == 0
        if (boardCopy[row][col] == 0)
        {
            //up nums see if it fits
            for (int num = 1; num <= 9; num++)
            {
                //perform checks to see what number is possible
                if (checkRow(row, num) && checkCol(col, num) && checkSquare(row, col, num))
                {
                    boardCopy[row][col] = num;
                    //moves unto next column recursively to backtrack if a number doesn't fit
                    if (solve(row, col + 1))
                    {
                        return true;
                    }
                }
            }
            boardCopy[row][col] = 0;
        }
        return false;
    }

    public boolean checkRow (int row, int num)
    {
        for (int col = 0; col < 9; col++)
        {
            if (boardCopy[row][col] == num)
            {
                return false;
            }
        }
        return true;
    }

    public boolean checkCol (int col, int num)
    {
        for (int row = 0; row < 9; row++)
        {
            if (boardCopy[row][col] == num)
            {
                return false;
            }
        }
        return true;
    }

    public boolean checkSquare (int row, int col, int num)
    {
        //Takes current col# and subtracts the remainder of col# / 3
        int startingCol = col - (col % 3);
        //Takes current row# and subtracts the remainder of row# / 3
        int startingRow = row - (row % 3);
        //Figures out which 9x9 square the index is currently in
        for (int y = 0; y < 3; y++)
        {
            for (int x = 0; x < 3; x++)
            {
                if (boardCopy[y + startingRow][x + startingCol] == num)
                {
                    return false;
                }
            }
        }
        return true;
    }
}
