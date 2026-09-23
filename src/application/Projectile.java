/*********************************************************
 * 						Projectile
 * *******************************************************
 * Projectile()
 * 
 * + getX(): double
 * + getY(): double
 * + getImage(): ImageView
 * + getWidth(): double
 * + getHeight(): double
 * + setX(x: double): void
 * + setY(y: double): void
 * + setDirection(dir: int): void
 * + setLocation(x: double, y: double, dir: int): void
 * + move(): void
 * + getShoot(): boolean
 * + stopShoot(): void
 * + offScreen(sceneWidth: double): boolean
 *******************************************************/
package application;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class Projectile {
	
	//Fields
	private double xPos, yPos, width, height;
	private Image[] imgSplash;
	private ImageView ivSplash;
	private int dir, speed;
	public final int RIGHT = 0, LEFT = 1;
	private boolean shot;
	private Rectangle mask;
	
	//Constructor
	public Projectile()
	{
		speed = 12;
		xPos = -2000;
		yPos = -2000;
		dir = RIGHT;
		shot = false;
		imgSplash = new Image[2];
		imgSplash[RIGHT] = new Image("file:images/waterProjectileR.gif");
		imgSplash[LEFT] = new Image("file:images/waterProjectileL.gif");
		ivSplash = new ImageView(imgSplash[RIGHT]);
		width = imgSplash[RIGHT].getWidth();
		height = imgSplash[RIGHT].getHeight();
		mask = new Rectangle(xPos + 60, yPos + 30, 30, 30);

	}
	
	//Methods
	public double getX()
	{
		return xPos;
	}
	public double getY()
	{
		return yPos;
	}
	public ImageView getImage()
	{
		if (dir == RIGHT)
		{
			ivSplash.setImage(imgSplash[RIGHT]);
		}
		else if (dir == LEFT)
		{
			ivSplash.setImage(imgSplash[LEFT]);
		}
		return ivSplash;
	}
	public double getWidth()
	{
		return width;
	}
	public double getHeight()
	{
		return height;
	}
	public void setX(double x)
	{
		xPos = x;
		ivSplash.setX(xPos);
	}
	public void setY(double y)
	{
		yPos = y;
		ivSplash.setY(yPos);
	}
	public void setDirection(int dir)
	{
		this.dir = dir;
	}
	public void setLocation(double x, double y, int dir)
	{
		this.dir = dir; //Assign the current direction
		if (this.dir == RIGHT)//Bullet faces east;
		{
			xPos = x + 75;
		}
		else
		{
			xPos = x - 75;
		}
		yPos = y;
		shot = true; //bullet is fired

		//Update Image Position
		ivSplash.setX(xPos);
		ivSplash.setY(yPos);
		mask.setX(xPos + 60);
		mask.setY(yPos + 30);

	}
	public void move()
	{				
		if (dir == RIGHT)
		{
			xPos += speed;
		}
		else if (dir == LEFT)
		{
			xPos -= speed;
		}

		//Update Position
		ivSplash.setX(xPos);
		ivSplash.setY(yPos);
		mask.setX(xPos + 60);
		mask.setY(yPos + 30);

	}
	public Bounds getMask()
	{
		return mask.getBoundsInParent();
	}
	public boolean getShoot()
	{
		return shot;
	}
	public void stopShoot()
	{
		shot = false;
	}
	public boolean offScreen(double sceneWidth)
	{
		boolean off = false;
		if (xPos >= sceneWidth || xPos + width <= 0)
		{
			off = true;
			shot  = false;
		}
		else
		{
			off = false;
		}
		
		return off;
	}
}