/*******************************************
* 				Player
*******************************************
* - x, y, width, height: double
* - dir, speed: int
* - imgPlayer: Image[]
* - ivPlayer: ImageView
* - pMask: Rectangle
* + RIGHT = 0, LEFT = 1, IDLE = 2: final int
* *******************************************
* Player()
* 
* + getX(): double
* + getY(): double
* + setX(): void
* + setY(): void
* + getWidth(): double
* + getHeight(): double
* + getNode(): ImageView
* + setDirection(dir: int): void
* + getPBounds(): Bounds
* + setLocation(x: double, y: double): void
* + getDirection(): int
* + update(idle: boolean): void
* + moveRight(): void
* + moveLeft(): void
* + moveUp(): void
* + moveDown(): void
* *******************************************/

package application;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player {
	
	//Fields
	private double x, y, width, height;
	private int dir, speed;
	private Image[] imgPlayer;
	private ImageView ivPlayer;
	private Rectangle pMask;
	public final int RIGHT = 0, LEFT = 1, IDLE = 2, HURTL = 3, HURTR = 4;
		
	public Player()
	{
		x = 0;
		y = 0;
		speed = 7;
		imgPlayer = new Image[5];
		imgPlayer[RIGHT] = new Image("file:PlayerAnimationCPT/PlayerWalkR.gif");
		imgPlayer[LEFT] = new Image("file:PlayerAnimationCPT/PlayerWalkL.gif");
		imgPlayer[IDLE] = new Image("file:PlayerAnimationCPT/PlayerIdle.gif");
		imgPlayer[HURTL] = new Image("file:PlayerAnimationCPT/PlayerHurtL.gif");
		imgPlayer[HURTR] = new Image("file:PlayerAnimationCPT/PlayerHurtR.gif");

		ivPlayer = new ImageView (imgPlayer[RIGHT]);
		width = imgPlayer[0].getWidth();
		height = imgPlayer[0].getHeight();
		dir = RIGHT;
		pMask = new Rectangle(getX(), getY(), 30, 50);
		pMask.setFill(Color.RED);
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
		ivPlayer.setX(this.x);
	}
	public void setY(double y)
	{
		this.y = y;
		ivPlayer.setY(this.y);
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
		return ivPlayer;
	}
	public void setDirection(int dir)
	{
		this.dir = dir;
	}
	public Bounds getPBounds()
	{
		return pMask.getBoundsInParent();
	}
	public void setLocation(double x, double y)
	{
		//Set the location of the player
		this.x = x;
		this.y = y;
		ivPlayer.setX(this.x);
		ivPlayer.setY(this.y);
		pMask.setX(this.x + width / 2 - 10);
		pMask.setY(this.y + height / 2 - 20);
	}
	public int getDirection()
	{
		return dir;
	}
	public void update(boolean idle, boolean playerHurt)
	{
		if (playerHurt) //Update the animation of the player if hurt
		{
			if (dir == RIGHT)
			{
				ivPlayer.setImage(imgPlayer[HURTR]);
			}
			if (dir == LEFT)
			{
				ivPlayer.setImage(imgPlayer[HURTL]);
			}
		}
		else if (!idle) // The boolean checks the player movement, if false, player is not moving
		{
			ivPlayer.setImage(imgPlayer[IDLE]);
		}
		else if (dir == RIGHT)
		{
			ivPlayer.setImage(imgPlayer[RIGHT]);
		}
		else if (dir == LEFT)
		{
			ivPlayer.setImage(imgPlayer[LEFT]);
		}
	}
	//Player Movement
	public void moveRight()
	{
		dir = RIGHT;
		x += speed;
		ivPlayer.setX(x);
		pMask.setX(x + width / 2 - 10);
	}
	public void moveLeft()
	{
		dir = LEFT;
		x -= speed;
		ivPlayer.setX(x);
		pMask.setX(x + width / 2 - 10);
	}
	public void moveUp()
	{
		y -= speed;
		ivPlayer.setY(y);
		pMask.setY(y + height / 2 - 20);

	}
	public void moveDown()
	{
		y += speed;
		ivPlayer.setY(y);
		pMask.setY(y + height / 2 - 20);
	}
}