/*******************************************************************
 * 								Enemy
 * *****************************************************************
 * - x, y, width, height: double
 * - speed = 5: final int
 * - imgEnemy: Image
 * - ivEnemy: ImageView
 * - eMask: Rectangle
 * - rnd: Random
 * - dir: int
 *******************************************************************
 * Enemy()
 * 
 * + getX(): double
 * + getY(): double
 * + setX(x: double): void
 * + setY(y: double): void
 * + getWidth(): double
 * + getHeight(): double
 * + getNode(): ImageView
 * + getMask(): Bounds
 * + getDirection(): int
 * + randomizeDirection(): void
 * + setLocation(sceneWidth: double, sceneHeight: double): void
 * + move(): void
 *****************************************************************/
package application;
import java.util.Random;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class Enemy {

	//Fields
	private double x, y, width, height;
	private final int speed = 14, RIGHT = 0, LEFT = 1;
	private Image[] imgEnemy;
	private ImageView ivEnemy;
	private Rectangle eMask;
	private Random rnd;
	private int dir;

	//Constructor
	public Enemy()
	{
		x = -100;
		y = -100;
		imgEnemy = new Image[2];
		imgEnemy[RIGHT] = new Image("file:EnemyCPT/enemyAttackLEFT.gif");
		imgEnemy[LEFT] = new Image("file:EnemyCPT/enemyAttackRIGHT.gif");
		ivEnemy = new ImageView(imgEnemy[RIGHT]);
		width = imgEnemy[RIGHT].getWidth();
		height = imgEnemy[RIGHT].getHeight();
		rnd = new Random();
		dir = RIGHT;
		eMask = new Rectangle(x, y, 100, height);
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
		ivEnemy.setX(this.x);
	}
	public void setY(double y)
	{
		this.y = y;
		ivEnemy.setY(this.y);
	}
	public double getWidth()
	{
		return width;
	}
	public double getHeight()
	{
		return height;
	}
	public ImageView getNode()
	{
		return ivEnemy;
	}
	public Bounds getMask()
	{
		return eMask.getBoundsInParent();
	}
	public void randomizeDirection()
	{
		//Randomize the direction of the squid, either coming from the left or right
		dir = rnd.nextInt(0, 2);
		if (dir == RIGHT)
		{
			ivEnemy.setImage(imgEnemy[RIGHT]);
		}
		else
		{
			ivEnemy.setImage(imgEnemy[LEFT]);
		}
	}
	public void setLocation(double sceneWidth, double sceneHeight)
	{
		//Sets location of the squid off screen based on which direction it comes from
		if (dir == RIGHT)
		{
			x = 0 - width;
			y = rnd.nextInt((int)(sceneHeight - height));
		}
		else
		{
			x = sceneHeight + width;
			y = rnd.nextInt((int)(sceneHeight - height));
		}

		//Update Position
		ivEnemy.setX(x);
		ivEnemy.setY(y);
		eMask = new Rectangle(x + 10, y + (height / 2 - 20), width - 60, height / 2 - 50);

	}
	public int getDirection()
	{
		return dir;
	}
	public void move() {

		//Move squid
		if (dir == RIGHT)
		{
			x += speed;
		}
		else
		{
			x -= speed;
		}
		ivEnemy.setX(x);
		eMask.setX(x + 10);
	}

}
