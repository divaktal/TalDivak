public class Queen {// Color -> Human - W, Bot - B, None - G
    int row;
    int col;
    int id;
    String color;

    public Queen(int row, int col, String Color, int id) {
        this.row = row;
        this.col = col;
        this.id = id;
        this.color = Color;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public String getColor() {
        return color;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setRow(int row) { this.row = row; }

    public void setColor(String color) { this.color = color; }

    /**
     * switches the color of the object and other object
     */
    public void switchQueen(Queen q) {
        q.color = this.color;
        this.color = "G";
    }

    /**
     * a boolean function that gets the board that is played on and one of the game pieces
     * uses 2 dimensional vector to determine if the move is valid or not
     */
    public boolean validQueenMove(Queen[][] gameBoard, Queen q) {
        if (q.color.equals("W") || q.color.equals("B") ||
                (this.row == q.row && this.col == q.col) ||
                (q.row - this.row != 0 && q.col - this.col != 0 && Math.abs(q.row - this.row) != Math.abs(q.col - this.col))) // look if the space is even empty OR look if it even moves OR look if the move even legal
        {
            return false;
        }

        int moveHorizontal = 0; //determine the vector
        int moveVertical = 0;

        if(q.row - this.row != 0)
            moveHorizontal = (q.row - this.row) / (Math.abs(q.row - this.row));

        if(q.col - this.col != 0)
            moveVertical = (q.col - this.col) / (Math.abs(q.col - this.col));

        for(int i = 1; this.row + moveHorizontal * i < q.row || this.col + moveVertical * i < q.col || this.row + moveHorizontal * i > q.row || this.col + moveVertical * i > q.col; i++)
            if(gameBoard[this.row + (moveHorizontal * i)][this.col + (moveVertical * i)].color.equals("B") || gameBoard[this.row + (moveHorizontal * i)][this.col + (moveVertical * i)].color.equals("W"))
                return false;

        return true;
    }
}