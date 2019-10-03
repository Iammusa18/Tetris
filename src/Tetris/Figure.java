package Tetris;
import java.awt.Color;


// A block figure/piece is represented in a 4x4 matrix
// Each block figure has 4 such matrices, each matrice representing one rotation
public class Figure
{
	public static final Color COLOR_BLANK = Color.WHITE;
	public static Color STROKE_BLANK = Color.WHITE;
	//
	public static final Figure[][] figures = new Figure[7][4];
	//
	public boolean[][] squares;
	public Color color;
	public Color stroke;
	public int mostLeftColumn; // needed to know how far to the left can be moved
	public int mostRightColumn; // needed to know how far to the right can be moved

	static
	{
		Color[] colors = new Color[]
		{
			Color.CYAN, Color.BLUE, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.MAGENTA, Color.RED
		};

		Color[] strokes = new Color[]
		{
			Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK
		};

		int[][] leftOverreaches = new int[][]
		{
			{
				0, 2, 0, 1
			},
			{
				0, 1, 0, 0
			},
			{
				0, 1, 0, 0
			},
			{
				1, 1, 1, 1
			},
			{
				0, 1, 0, 0
			},
			{
				0, 1, 0, 0
			},
			{
				0, 1, 0, 0
			}
		};

		int[][] rightOverreaches = new int[][]
		{
			{
				0, 1, 0, 2
			},
			{
				1, 1, 1, 2
			},
			{
				1, 1, 1, 2
			},
			{
				1, 1, 1, 1
			},
			{
				1, 1, 1, 2
			},
			{
				1, 1, 1, 2
			},
			{
				1, 1, 1, 2
			}
		};

		String[][][] strFigures = new String[7][4][4];
//Each shape drawn below and the adjacent comments offer visualisation of the pieces at every rotation

		// Block I
		strFigures[0][0][0] = "    ";
		strFigures[0][0][1] = "OOOO";
		strFigures[0][0][2] = "    ";
		strFigures[0][0][3] = "    ";

		strFigures[0][1][0] = "  O ";
		strFigures[0][1][1] = "  O ";
		strFigures[0][1][2] = "  O ";
		strFigures[0][1][3] = "  O ";

		strFigures[0][2][0] = "    ";
		strFigures[0][2][1] = "    ";
		strFigures[0][2][2] = "OOOO";
		strFigures[0][2][3] = "    ";

		strFigures[0][3][0] = " O  ";
		strFigures[0][3][1] = " O  ";
		strFigures[0][3][2] = " O  ";
		strFigures[0][3][3] = " O  ";


		// Block J
		strFigures[1][0][0] = "O   ";
		strFigures[1][0][1] = "OOO ";
		strFigures[1][0][2] = "    ";
		strFigures[1][0][3] = "    ";

		strFigures[1][1][0] = " OO ";
		strFigures[1][1][1] = " O  ";
		strFigures[1][1][2] = " O  ";
		strFigures[1][1][3] = "    ";

		strFigures[1][2][0] = "    ";
		strFigures[1][2][1] = "OOO ";
		strFigures[1][2][2] = "  O ";
		strFigures[1][2][3] = "    ";

		strFigures[1][3][0] = " O  ";
		strFigures[1][3][1] = " O  ";
		strFigures[1][3][2] = "OO  ";
		strFigures[1][3][3] = "    ";


		// Block L
		strFigures[2][0][0] = "  O ";
		strFigures[2][0][1] = "OOO ";
		strFigures[2][0][2] = "    ";
		strFigures[2][0][3] = "    ";

		strFigures[2][1][0] = " O  ";
		strFigures[2][1][1] = " O  ";
		strFigures[2][1][2] = " OO ";
		strFigures[2][1][3] = "    ";

		strFigures[2][2][0] = "    ";
		strFigures[2][2][1] = "OOO ";
		strFigures[2][2][2] = "O   ";
		strFigures[2][2][3] = "    ";

		strFigures[2][3][0] = "OO  ";
		strFigures[2][3][1] = " O  ";
		strFigures[2][3][2] = " O   ";
		strFigures[2][3][3] = "    ";


		// Block O
		strFigures[3][0][0] = " OO ";
		strFigures[3][0][1] = " OO ";
		strFigures[3][0][2] = "    ";
		strFigures[3][0][3] = "    ";

		strFigures[3][1][0] = " OO ";
		strFigures[3][1][1] = " OO ";
		strFigures[3][1][2] = "    ";
		strFigures[3][1][3] = "    ";

		strFigures[3][2][0] = " OO ";
		strFigures[3][2][1] = " OO ";
		strFigures[3][2][2] = "    ";
		strFigures[3][2][3] = "    ";

		strFigures[3][3][0] = " OO ";
		strFigures[3][3][1] = " OO ";
		strFigures[3][3][2] = "    ";
		strFigures[3][3][3] = "    ";


		// Block S
		strFigures[4][0][0] = " OO ";
		strFigures[4][0][1] = "OO  ";
		strFigures[4][0][2] = "    ";
		strFigures[4][0][3] = "    ";

		strFigures[4][1][0] = " O  ";
		strFigures[4][1][1] = " OO ";
		strFigures[4][1][2] = "  O ";
		strFigures[4][1][3] = "    ";

		strFigures[4][2][0] = "    ";
		strFigures[4][2][1] = " OO ";
		strFigures[4][2][2] = "OO  ";
		strFigures[4][2][3] = "    ";

		strFigures[4][3][0] = "O   ";
		strFigures[4][3][1] = "OO  ";
		strFigures[4][3][2] = " O  ";
		strFigures[4][3][3] = "    ";


		// Block T
		strFigures[5][0][0] = " O  ";
		strFigures[5][0][1] = "OOO ";
		strFigures[5][0][2] = "    ";
		strFigures[5][0][3] = "    ";

		strFigures[5][1][0] = " O  ";
		strFigures[5][1][1] = " OO ";
		strFigures[5][1][2] = " O  ";
		strFigures[5][1][3] = "    ";

		strFigures[5][2][0] = "    ";
		strFigures[5][2][1] = "OOO ";
		strFigures[5][2][2] = " O  ";
		strFigures[5][2][3] = "    ";

		strFigures[5][3][0] = " O  ";
		strFigures[5][3][1] = "OO  ";
		strFigures[5][3][2] = " O  ";
		strFigures[5][3][3] = "    ";


		// Block Z
		strFigures[6][0][0] = "OO  ";
		strFigures[6][0][1] = " OO ";
		strFigures[6][0][2] = "    ";
		strFigures[6][0][3] = "    ";

		strFigures[6][1][0] = "  O ";
		strFigures[6][1][1] = " OO ";
		strFigures[6][1][2] = " O  ";
		strFigures[6][1][3] = "    ";

		strFigures[6][2][0] = "    ";
		strFigures[6][2][1] = "OO  ";
		strFigures[6][2][2] = " OO ";
		strFigures[6][2][3] = "    ";

		strFigures[6][3][0] = " O  ";
		strFigures[6][3][1] = "OO  ";
		strFigures[6][3][2] = "O   ";
		strFigures[6][3][3] = "    ";


		for (int i = 0; i < 7; ++i)
			for (int direction = 0; direction < BlockDirection.values().length; ++direction){
				figures[i][direction] = new Figure(
						colors[i], strokes[i], strFigures[i][direction],
						0-leftOverreaches[i][direction],
						Dimensions.COLS - 4 + rightOverreaches[i][direction]);
			}
	}

	public static boolean[][] generateFromStrings(String[] strs){
		boolean[][] squares = new boolean[4][4];
		for (int r = 0; r < 4; ++r)
			for (int c = 0; c < 4; ++c)
				squares[r][c] = strs[r].charAt(c) == 'O';
		return squares;
	}

//The colouring
	public Figure(Color color, Color stroke, String[] strs, int mostLeftColumn, int mostRightColumn)
	{
		this.color = color;
		this.stroke = stroke;
		this.squares = generateFromStrings(strs);
		this.mostLeftColumn = mostLeftColumn;
		this.mostRightColumn = mostRightColumn;
	}
//Colouring
	public Color fillOf(int row, int col)
	{
		if (squares[row][col])
			return color;
		else
			return COLOR_BLANK;
	}
//colouring
	public Color strokeOf(int row, int col)
	{
		if (squares[row][col])
			return stroke;
		else
			return STROKE_BLANK;
	}

}
