package application;
import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Boss {
	
	//Fields
	private double x, y, width, height, xMud, yMud;
	private final int shotSpeed = 8;
	private Image[] imgBoss;
	private Image imgShot;
	private ImageView ivBoss, ivShot;
	private int dir;
	private final int RIGHT = 0, LEFT = 1;
	private boolean shot;
	private Rectangle healthbar;
	private double health;
	private Random rnd;
	private double dx, dy;

	//Constructor
	public Boss()
	{
		dir = LEFT;
		x = -100;
		y = -100;
		xMud = -100;
		yMud = -100;
		imgBoss = new Image[2];
		imgBoss[RIGHT] = new Image("file:EnemyCPT/bossWalkRIGHT.gif");
		imgBoss[LEFT] = new Image("file:EnemyCPT/bossWalkLEFT.gif");
		ivBoss = new ImageView(imgBoss[LEFT]);
		imgShot = new Image("file:EnemyCPT/mud.png");
		ivShot = new ImageView(imgShot);
		width = imgBoss[RIGHT].getWidth();
		height = imgBoss[RIGHT].getHeight();
		rnd = new Random();
		health = 100;
		healthbar = new Rectangle(health, 15);
		healthbar.setX(50);
		healthbar.setY(50);
		healthbar.setFill(Color.LIGHTGREEN);
		healthbar.setStroke(Color.BLACK);
		shot = false;
	}

	//Methods
	public double getX()
	{
		return x;
	}
	public double getY()
	{
		return y;
	}
	public void setX(double x)
	{
		this.x = x;
		ivBoss.setX(this.x);
	}
	public void setY(double y)
	{
		this.y = y;
		ivBoss.setY(this.y);
	}
	public double getWidth()
	{
		return width;
	}
	public double getHeight()
	{
		return height;
	}
	public double getHealthWidth()
	{
		return health;
	}
	public Rectangle getHealthBar()
	{
		return healthbar;
	}
	public void updateDirection(double pX)//Boss faces player
	{
		if (x > pX) //If the boss is to the right of the player
		{
			dir = RIGHT;
			ivBoss.setImage(imgBoss[RIGHT]);
		}
		else //If the boss is to the left of the player
		{
			dir = LEFT;
			ivBoss.setImage(imgBoss[LEFT]);
		}
	}
	public void shoot(double pX, double pY)
	{
		shot = true;
		//sets the initial image of the mud to the centre of the boss
		xMud = x + width/2; 
		yMud = y + height/2;

		//Use pythagorean theorem and the distance between the boss and the player (in it's x and y vectors) to calculate the distance of the player from the mud projectile
		double distance = Math.sqrt(Math.pow(pX - xMud, 2) + Math.pow(pY - yMud, 2));

		//Use the unit vectors in the x and y axis to move the mud towards the player in a straight line
		dx = ((pX - xMud) / distance) * shotSpeed;
		dy = ((pY - yMud) / distance) * shotSpeed;

		ivShot.setX(xMud);
		ivShot.setY(yMud);
	}
	public void shootMove()
	{
		//Update the mud's x and y coordinate to move it towards the player in a straight line
	    xMud += dx;
	    yMud += dy;

	    ivShot.setX(xMud);
	    ivShot.setY(yMud);
	}
	public void resetMud()
	{
		//Resetting the mud off screen where it cannot collide with the player
	    xMud = -100;
	    yMud = -100;
	    ivShot.setX(xMud);
	    ivShot.setY(yMud);
	    dx = 0;
	    dy = 0;
	}
	public void updateHealth() //Boss takes damage
	{
		health -= 20;
		if (health < 0)//if boss is dead
			health = 0;

		healthbar.setWidth(health);
	}
	public ImageView getNode()
	{
		return ivBoss;
	}
	public ImageView getMud()
	{
		return ivShot;
	}
	public void teleport(double sceneWidth, double sceneHeight, double pX, double pY)
	{
		//using the random class to generate a random x and y coordinate for the boss location
		this.x = rnd.nextInt((int) (sceneWidth - width));
		this.y = rnd.nextInt((int) (sceneHeight - height));

		//Update Position
		ivBoss.setX(x);
		ivBoss.setY(y);
		healthbar.setX(this.x + 40);
		healthbar.setY(this.y - 15);
	}
	public void setLocation(double x, double y)
	{
		this.x = x;
		this.y = y;

		//Update Position
		ivBoss.setX(x);
		ivBoss.setY(y);
		healthbar.setX(this.x + 40);
		healthbar.setY(this.y - 15);
	}
	public double getMudX()
	{
		return xMud;
	}
	public double getMudY()
	{
		return yMud;
	}
}