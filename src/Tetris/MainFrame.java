package Tetris;
//Tetris game created by musa Guhad
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.*;
//Main class, initiates the game and controls the core functionality together with the playing screen/panel
public class MainFrame extends JFrame {
	//options
	JMenu mGame;
	JMenu mMain;
	JMenuBar mbMain;
	JMenuItem miExit;
	JMenuItem miNew;
	JMenuItem miNewSquared;

	//The board
	GameState gameState;
	GameField gameField;
	double lastTimeUpdated = 0;
	int score;
	Timer timer;
	boolean hardDrop;
	boolean shouldRedraw;

	public MainFrame()
	{

		mbMain = new JMenuBar();
		mMain = new JMenu(); //The Menu 'File' tab/bar Menu option
		miExit = new JMenuItem(); //The 'Exit' menu item found under 'File' tab menu option
		mGame = new JMenu(); //'Game' bar/tab menu option
		miNew = new JMenuItem();//''New game' option found under 'Game'/'mGAME' TAB
		miNewSquared = new JMenuItem();//The other option found 'Game' with the black and white background game Mode

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Musa Guhad 1502029");//displays my name and Reg Number on the top left of panel

		// for Mouse events
		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent evt){
				formMousePressed(evt);
			}
		});
		addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent evt){
				formKeyPressed(evt);
			}
		});
		getContentPane().setLayout(null);

		mMain.setText("File");

		miExit.setText("Exit");//The option under 'File'
		miExit.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent evt){
				System.exit(0);
			}
		});
		mMain.add(miExit);//link the 'File'>'Exit' options
		mbMain.add(mMain);

		mGame.setText("Game");

		miNew.setText("New Game");//Normal game mode
		miNew.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				miNewActionPerformed(evt);
			}
		});

		mGame.add(miNew);

		miNewSquared.setText("New Game (Squared Background)");//Other game mode with squared background
		miNewSquared.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				miNewActionPerformed(evt);
			}
		});
		mGame.add(miNewSquared);

		mbMain.add(mGame);

		setJMenuBar(mbMain);

		setSize(new java.awt.Dimension(678, 761));
		setLocationRelativeTo(null);

		gameField = new GameField();//area
		gameState = GameState.NOT_PLAYING;
		shouldRedraw = true;
		super.getContentPane().setBackground(Color.WHITE);//For the normal game mode

	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);

		// use a buffered image for double buffering to reduce flickering each time the game is redrawn
		BufferedImage bufferedImage = new BufferedImage(660, 720, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D)g;//bufferedImage.createGraphics();

		g2d.setColor(Color.BLUE);

		// game borders
		g2d.fillRect(Dimensions.X0, Dimensions.Y0 + 2 * Dimensions.SQUARE_WIDTH, Dimensions.WALL_WIDTH, Dimensions.ROWS * Dimensions.SQUARE_WIDTH + Dimensions.WALL_WIDTH - 2 * Dimensions.SQUARE_WIDTH);
		g2d.fillRect(Dimensions.X0 + Dimensions.WALL_WIDTH, Dimensions.Y0 + Dimensions.ROWS * Dimensions.SQUARE_WIDTH, Dimensions.COLS * Dimensions.SQUARE_WIDTH, Dimensions.WALL_WIDTH);
		g2d.fillRect(Dimensions.X0 + Dimensions.WALL_WIDTH + Dimensions.COLS * Dimensions.SQUARE_WIDTH, Dimensions.Y0 + 2 * Dimensions.SQUARE_WIDTH, Dimensions.WALL_WIDTH, Dimensions.ROWS * Dimensions.SQUARE_WIDTH + Dimensions.WALL_WIDTH - 2 * Dimensions.SQUARE_WIDTH);

		g2d.setColor(Color.BLACK);

		// game status
		g2d.setFont(new Font("Tahoma", Font.PLAIN, 30));
		g2d.drawChars("Next piece:".toCharArray(), 0, "Next piece:".length(), Dimensions.X0 + 2 * Dimensions.WALL_WIDTH + Dimensions.COLS * Dimensions.SQUARE_WIDTH + 50, Dimensions.Y0);
		g2d.drawChars(gameField.txLevel.toCharArray(), 0, gameField.txLevel.length(), Dimensions.X0 + 2 * Dimensions.WALL_WIDTH + Dimensions.COLS * Dimensions.SQUARE_WIDTH + 50, Dimensions.Y0 + 200);
		g2d.drawChars(gameField.txScore.toCharArray(), 0, gameField.txScore.length(), Dimensions.X0 + 2 * Dimensions.WALL_WIDTH + Dimensions.COLS * Dimensions.SQUARE_WIDTH + 50, Dimensions.Y0 + 250);
		g2d.drawChars(gameField.txStatus.toCharArray(), 0, gameField.txStatus.length(), Dimensions.X0 + 2 * Dimensions.WALL_WIDTH + Dimensions.COLS * Dimensions.SQUARE_WIDTH + 50, Dimensions.Y0 + 300);

		// the game 'legend'/Options
		String legend1 = "INSERT    - Rotate Left";
		String legend2 = "PG_UP  - Rotate Right";
		String legend3 = "DELETE    - Move Left";
		String legend4 = "PG_DN  - Move Right";
		String legend5 = "END    - Move Down";
		String legend6 = "SPACE  - Drop";
		String legend7 = "LMouse - Move Left";
		String legend8 = "RMouse - Move Right";
		String legend9 = "MMouse - Rotate";

		g2d.setFont(new Font("Courier new", Font.PLAIN, 15));
		g2d.drawChars(legend1.toCharArray(), 0, legend1.length(), Dimensions.X0 + 2 * Dimensions.WALL_WIDTH + Dimensions.COLS * Dimensions.SQUARE_WIDTH + 50, Dimensions.Y0 + 420);
		g2d.drawChars(legend2.toCharArray(), 0, legend2.length(), Dimensions.X0 + 2 * Dimensions.WALL_WIDTH + Dimensions.COLS * Dimensions.SQUARE_WIDTH + 50, Dimensions.Y0 + 435);
		g2d.drawChars(legend3.toCharArray(), 0, legend3.length(), Dimensions.X0 + 2 * Dimensions.WALL_WIDTH + Dimensions.COLS * Dimensions.SQUARE_WIDTH + 50, Dimensions.Y0 + 450);
		g2d.drawChars(legend4.toCharArray(), 0, legend4.length(), Dimensions.X0 + 2 * Dimensions.WALL_WIDTH + Dimensions.COLS * Dimensions.SQUARE_WIDTH + 50, Dimensions.Y0 + 465);
		g2d.drawChars(legend5.toCharArray(), 0, legend5.length(), Dimensions.X0 + 2 * Dimensions.WALL_WIDTH + Dimensions.COLS * Dimensions.SQUARE_WIDTH + 50, Dimensions.Y0 + 480);
		g2d.drawChars(legend6.toCharArray(), 0, legend6.length(), Dimensions.X0 + 2 * Dimensions.WALL_WIDTH + Dimensions.COLS * Dimensions.SQUARE_WIDTH + 50, Dimensions.Y0 + 495);
		g2d.drawChars(legend7.toCharArray(), 0, legend7.length(), Dimensions.X0 + 2 * Dimensions.WALL_WIDTH + Dimensions.COLS * Dimensions.SQUARE_WIDTH + 50, Dimensions.Y0 + 520);
		g2d.drawChars(legend8.toCharArray(), 0, legend8.length(), Dimensions.X0 + 2 * Dimensions.WALL_WIDTH + Dimensions.COLS * Dimensions.SQUARE_WIDTH + 50, Dimensions.Y0 + 535);
		g2d.drawChars(legend9.toCharArray(), 0, legend9.length(), Dimensions.X0 + 2 * Dimensions.WALL_WIDTH + Dimensions.COLS * Dimensions.SQUARE_WIDTH + 50, Dimensions.Y0 + 550);

		// next block to come
		for (int r = 0; r < gameField.nextSquares.length; ++r)

			for (int c = 0; c < gameField.nextSquares[r].length; ++c){
				g2d.setColor(gameField.nextSquares[r][c].fill);

				g2d.fillRect(
						Dimensions.X0 + 2 * Dimensions.WALL_WIDTH + (Dimensions.COLS + c) * Dimensions.SQUARE_WIDTH + 50,
						Dimensions.Y0 + (2 - r) * Dimensions.SQUARE_WIDTH + 50,
						Dimensions.SQUARE_WIDTH,
						Dimensions.SQUARE_WIDTH);

				g2d.setColor(gameField.nextSquares[r][c].stroke);
				g2d.drawRect(
						Dimensions.X0 + 2 * Dimensions.WALL_WIDTH + (Dimensions.COLS + c) * Dimensions.SQUARE_WIDTH + 50,
						Dimensions.Y0 + (2 - r) * Dimensions.SQUARE_WIDTH + 50,
						Dimensions.SQUARE_WIDTH - 1,
						Dimensions.SQUARE_WIDTH - 1);
			}

		// all the pieces/blocks
		for (int r = 0; r < gameField.gameSquares.length; ++r)
			for (int c = 0; c < gameField.gameSquares[r].length; ++c){
				g2d.setColor(gameField.gameSquares[r][c].fill);
				g2d.fillRect(
						Dimensions.X0 + Dimensions.WALL_WIDTH + c * Dimensions.SQUARE_WIDTH,
						Dimensions.Y0 + (Dimensions.ROWS - 1 - r) * Dimensions.SQUARE_WIDTH,
						Dimensions.SQUARE_WIDTH,
						Dimensions.SQUARE_WIDTH);
				g2d.setColor(gameField.gameSquares[r][c].stroke);
				g2d.drawRect(
						Dimensions.X0 + Dimensions.WALL_WIDTH + c * Dimensions.SQUARE_WIDTH,
						Dimensions.Y0 + (Dimensions.ROWS - 1 - r) * Dimensions.SQUARE_WIDTH,
						Dimensions.SQUARE_WIDTH - 1,
						Dimensions.SQUARE_WIDTH - 1);
			}

		((Graphics2D) g).drawImage(bufferedImage, null, 0, 0);

		shouldRedraw = false;
	}

	// as the score increases, so does the level and the speed
	private int scoreToLevel(int score)
	{
		return 1 + score / 200;
	}

	// if dropped instantly with the space key, then double points will be recorded if the the space key is used to fill the last piece of the row to make the line clear below
	private void addToScore()
	{
		score += hardDrop ? 20 : 10;
	}

	// when the Buttons options are pressed
	private void processKeyPress(int keyPress)
	{
		switch (keyPress)
		{
			case KeyEvent.VK_INSERT:
				if (gameField.currentBlock.canRotateLeft())
				{
					gameField.currentBlock.rotateLeft();
					shouldRedraw = true;
				}
				break;

			case KeyEvent.VK_PAGE_UP:
				if (gameField.currentBlock.canRotateRight())
				{
					gameField.currentBlock.rotateRight();
					shouldRedraw = true;
				}
				break;

			case KeyEvent.VK_END:
				if (gameField.currentBlock.canMoveDown())
				{
					gameField.currentBlock.moveDown();
					shouldRedraw = true;
				}
				break;

			case KeyEvent.VK_DELETE:
				if (gameField.currentBlock.canMoveLeft())
				{
					gameField.currentBlock.moveLeft();
					shouldRedraw = true;
				}
				break;

			case KeyEvent.VK_PAGE_DOWN:
				if (gameField.currentBlock.canMoveRight())
				{
					gameField.currentBlock.moveRight();
					shouldRedraw = true;
				}
				break;

			case KeyEvent.VK_SPACE:
				while (gameField.currentBlock.canMoveDown())
					gameField.currentBlock.moveDown();
				hardDrop = true;
				shouldRedraw = true;
				break;

			case KeyEvent.VK_ESCAPE:
				System.exit(0);
		}
	}
//Update the game based on the key events
	private void updateGameState()
	{
		if (gameState == GameState.PLAYING)
		{
			double timeNow = System.currentTimeMillis() / 1000.0;
			if (hardDrop || lastTimeUpdated + gameState.getDropDownTime(scoreToLevel(score)) < timeNow)
			{
				if (gameField.currentBlock.canMoveDown())
					gameField.currentBlock.moveDown();
				else
				{
					int reduced = gameField.reduceRows();//clear rows

					gameField.txScore = "Score: " + score;//display score
					gameField.txLevel = "Level: " + scoreToLevel(score);//level displayed
					gameField.currentBlock = null;

					//Populate board/field  if free
					if (gameField.canPut(gameField.nextBlock.getFigure(), gameField.nextBlock.row, gameField.nextBlock.col)){

						gameField.currentBlock = gameField.nextBlock;
						gameField.currentBlock.writeToField();
						gameField.nextBlock.eraseFromNext();
						gameField.nextBlock = new Block(gameField);
						gameField.nextBlock.writeToNext();
					}
					else // game over
					{
						gameState = GameState.NOT_PLAYING;
						gameField.txStatus = "GAME OVER!";
						timer.stop();
					}
				}

				if (lastTimeUpdated == 0 || hardDrop)
					lastTimeUpdated = timeNow;
				else
					lastTimeUpdated += gameState.getDropDownTime(scoreToLevel(score));

				hardDrop = false;
				shouldRedraw = true;
			}
			if (shouldRedraw)
				repaint();
		}
	}

	private void miNewActionPerformed(ActionEvent evt) {
		if (evt.getSource() == miNewSquared)
			Figure.STROKE_BLANK = Color.BLACK;
		else
			Figure.STROKE_BLANK = Color.WHITE;

		gameField.clearField();
		gameField.currentBlock = new Block(gameField);
		gameField.nextBlock = new Block(gameField);
		gameField.nextBlock.writeToNext();
		score = 0;
		gameField.txScore = "Score: " + score;//Score
		gameField.txLevel = "Level: " + scoreToLevel(score);//Level
		gameState = GameState.PLAYING;
		gameField.txStatus = "Playing";//Status message
		lastTimeUpdated = 0;
		hardDrop = false;
		timer = new Timer(100, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				updateGameState();
			}
		});
		timer.start();
	}

	private void formKeyPressed(KeyEvent evt)
	{
		if (gameState == GameState.PLAYING)
			switch (evt.getKeyCode())
			{
				case KeyEvent.VK_INSERT:
				case KeyEvent.VK_PAGE_DOWN:
				case KeyEvent.VK_PAGE_UP:
				case KeyEvent.VK_DELETE:
				case KeyEvent.VK_END:
				case KeyEvent.VK_SPACE:
					processKeyPress(evt.getKeyCode());
					updateGameState();
					break;
			}

	}

	//Assign the button fucntionality to the Mouse buttons to achieve same result
	private void formMousePressed(MouseEvent evt){
		if (gameState == GameState.PLAYING)
			switch (evt.getButton())
			{
				case MouseEvent.BUTTON1: //Move left
					processKeyPress(KeyEvent.VK_DELETE);
					updateGameState();
					break;

				case MouseEvent.BUTTON2:
					processKeyPress(KeyEvent.VK_PAGE_UP);//Rotate right (Middle button!)
					updateGameState();
					break;

				case MouseEvent.BUTTON3:
					processKeyPress(KeyEvent.VK_PAGE_DOWN);//Move right
					updateGameState();
					break;
			}
	}

	public static void main(String args[]){

		new MainFrame().setVisible(true);
	}//Main method start the system/Game by simply implementing the above class

}
