package Tetris;

//The visuals of the game, what you see and the position/rotation of the current/next piece
public class GameField
{
	public Square[][] gameSquares; // a matrix of 10x20 squares
	public Square[][] nextSquares; // the next block that will be dropped
	public Block currentBlock, nextBlock;
	public String txLevel, txScore, txStatus;

	public GameField()
	{
		txLevel = "Level: 1";
		txScore = "Score: 0";
		txStatus = "Playing";
		
		gameSquares = new Square[Dimensions.ROWS][Dimensions.COLS];
		for (int r = 0; r < gameSquares.length; ++r)
			for (int c = 0; c < gameSquares[r].length; ++c)
				gameSquares[r][c] = new Square(Figure.COLOR_BLANK, Figure.STROKE_BLANK);

		nextSquares = new Square[4][4];
		for (int r = 0; r < nextSquares.length; ++r)
			for (int c = 0; c < nextSquares[r].length; ++c)
				nextSquares[r][c] = new Square(Figure.COLOR_BLANK, Figure.STROKE_BLANK);
	}

	public void clearField()
	{
		for (int r = 0; r < gameSquares.length; ++r)
			for (int c = 0; c < gameSquares[r].length; ++c)
			{
				gameSquares[r][c].fill = Figure.COLOR_BLANK;
				gameSquares[r][c].stroke = Figure.STROKE_BLANK;
			}

		for (int r = 0; r < nextSquares.length; ++r)
			for (int c = 0; c < nextSquares[r].length; ++c)
			{
				nextSquares[r][c].fill = Figure.COLOR_BLANK;
				nextSquares[r][c].stroke = Figure.STROKE_BLANK;
			}
	}

	public int reduceRows()
	{
		int reducedRows = 0;
		for (int row = 0; row < Dimensions.ROWS; ++row)
		{
			boolean hasEmptySquares = false;
			do
			{
				for (int col = 0; col < Dimensions.COLS; ++col)
					if (gameSquares[row][col].fill == Figure.COLOR_BLANK)
						hasEmptySquares = true;

				if (!hasEmptySquares)
				{
					++reducedRows;
					for (int r = row + 1; r < Dimensions.ROWS; ++r)
						for (int c = 0; c < Dimensions.COLS; ++c)
						{
							gameSquares[r - 1][c].fill = gameSquares[r][c].fill;
							gameSquares[r - 1][c].stroke = gameSquares[r][c].stroke;
						}
				}
			}
			while (!hasEmptySquares);
		}
		return reducedRows;
	}

	public boolean canPut(Figure tetFigure, int row, int col)
	{
		if (col < tetFigure.mostLeftColumn || tetFigure.mostRightColumn < col)
			return false;

		for (int r = 0; r < 4; ++r)
			for (int c = 0; c < 4; ++c)
				if (tetFigure.squares[r][c])
				{
					int locRow = row + r;
					int locCol = col + c;

					// at bottom
					if (locRow < 0)
						return false;

					// square is already taken
					if (!isLocationFree(locRow, locCol))
						return false;
				}
		return true;
	}

	public boolean isLocationFree(int locRow, int locCol)
	{
		if (gameSquares[locRow][locCol].fill == Figure.COLOR_BLANK)
			return true;

		if (currentBlock == null)
			return false;
		else
			return currentBlock.occupiesLocation(locRow, locCol);
	}

}
