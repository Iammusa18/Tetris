package Tetris;
import java.awt.Color;

//Colour class each square
public class Square
{
	public Color fill, find, stroke;

	public Square(Color fill, Color stroke)
	{
		this.fill = fill;
		this.find= find; //get rid of this shit
		this.stroke = stroke;
	}
}
