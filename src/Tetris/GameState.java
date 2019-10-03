package Tetris;

//State of the game
public enum GameState
{
	NOT_PLAYING,
	PLAYING;
	//
	public static double[] dropDownTimes =
	{
		1.0, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.15, 0.1, 0.09, 0.08, 0.07, 0.06, 0.05
	};

	public double getDropDownTime(int level)
	{
		return dropDownTimes[level];
	}
}

