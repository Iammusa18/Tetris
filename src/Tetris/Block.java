package Tetris;
import java.util.Random;


// A block represents a figure along with the current location and orientation
public class Block{
	private static final Random rand = new Random();
	//
	public int row, col;
	private final int figure;
	private BlockDirection direction;
	private final GameField gameField;

	public Block(GameField graphicsState){
		figure = rand.nextInt(Figure.figures.length);
		direction = BlockDirection.UP;
		row = Dimensions.ROWS - 4;
		col = Dimensions.COLS / 2 - 2;
		this.gameField = graphicsState;
	}

	public Figure getFigure()
	{
		return Figure.figures[figure][direction.ordinal()];
	}

	public boolean canMoveLeft()
	{
		return gameField.canPut(getFigure(), row, col - 1);
	}

	public boolean canMoveRight()
	{
		return gameField.canPut(getFigure(), row, col + 1);
	}

	public boolean canMoveDown()
	{
		return gameField.canPut(getFigure(), row - 1, col);
	}

	public boolean canRotateLeft(){
		int d = direction.ordinal();
		d = (d + BlockDirection.values().length - 1) % BlockDirection.values().length;
		Figure fig = Figure.figures[figure][d];

		if (col < fig.mostLeftColumn)
			return gameField.canPut(fig, row, fig.mostLeftColumn);
		else if (col > fig.mostRightColumn)
			return gameField.canPut(fig, row, fig.mostRightColumn);
		else
			return gameField.canPut(fig, row, col);
	}
//check right rotation possible
	public boolean canRotateRight(){
		int d = direction.ordinal();
		d = (d + 1) % BlockDirection.values().length;
		Figure fig = Figure.figures[figure][d];

		if (col < fig.mostLeftColumn)
			return gameField.canPut(fig, row, fig.mostLeftColumn);
		else if (col > fig.mostRightColumn)
			return gameField.canPut(fig, row, fig.mostRightColumn);
		else
			return gameField.canPut(fig, row, col);
	}
//Move left
	public void moveLeft()
	{
		eraseFromField();
		--col;
		writeToField();
	}
//move right
	public void moveRight()
	{
		eraseFromField();
		++col;
		writeToField();
	}
//Move down
	public void moveDown()
	{
		eraseFromField();
		--row;
		writeToField();
	}
//Rotate
	public void rotateLeft()
	{
		eraseFromField();
		int d = direction.ordinal();
		d = (d + 1) % BlockDirection.values().length;
		direction = BlockDirection.values()[d];

		if (col < getFigure().mostLeftColumn)
			col = getFigure().mostLeftColumn;
		else if (col > getFigure().mostRightColumn)
			col = getFigure().mostRightColumn;

		writeToField();
	}
//opposite rotation
	public void rotateRight()
	{
		eraseFromField();
		int d = direction.ordinal();
		d = (d + BlockDirection.values().length - 1) % BlockDirection.values().length;
		direction = BlockDirection.values()[d];

		if (col < getFigure().mostLeftColumn)
			col = getFigure().mostLeftColumn;
		else if (col > getFigure().mostRightColumn)
			col = getFigure().mostRightColumn;

		writeToField();
	}

	public boolean occupiesLocation(int locRow, int locCol)
	{
		if (locRow < row || row + 4 <= locRow || locCol < col || col + 4 <= locCol)
			return false;

		int r = locRow - row;
		int c = locCol - col;

		return getFigure().squares[r][c];
	}

	//Remove/clear field/row
	public void eraseFromField()
	{
		for (int r = 0; r < getFigure().squares.length; ++r)
			for (int c = 0; c < getFigure().squares[r].length; ++c)
				if (getFigure().squares[r][c])
				{
					int locRow = row + r;
					int locCol = col + c;

					gameField.gameSquares[locRow][locCol].fill = Figure.COLOR_BLANK;
					gameField.gameSquares[locRow][locCol].stroke = Figure.STROKE_BLANK;
				}
	}
//Populate field
	public void writeToField()
	{
		for (int r = 0; r < getFigure().squares.length; ++r)
			for (int c = 0; c < getFigure().squares[r].length; ++c)
				if (getFigure().squares[r][c])
				{
					int locRow = row + r;
					int locCol = col + c;

					if (locRow < 20)
					{
						gameField.gameSquares[locRow][locCol].fill = getFigure().fillOf(r, c);
						gameField.gameSquares[locRow][locCol].stroke = getFigure().strokeOf(r, c);
					}
				}
	}

	public void eraseFromNext()
	{
		for (int r = 0; r < getFigure().squares.length; ++r)
			for (int c = 0; c < getFigure().squares[r].length; ++c)
				if (getFigure().squares[r][c])
				{
					gameField.nextSquares[r][c].fill = Figure.COLOR_BLANK;
					gameField.nextSquares[r][c].stroke = Figure.STROKE_BLANK;
				}
	}

	public void writeToNext()
	{
		for (int r = 0; r < getFigure().squares.length; ++r)
			for (int c = 0; c < getFigure().squares[r].length; ++c)
				if (getFigure().squares[r][c])
				{
					gameField.nextSquares[r][c].fill = getFigure().fillOf(r, c);
					gameField.nextSquares[r][c].stroke = getFigure().strokeOf(r, c);
				}
	}

}
