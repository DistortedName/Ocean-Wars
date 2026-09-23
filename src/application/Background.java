/********************************
*  			Background
*********************************
* Background()
*
* + getImage(): ImageView
* + getWidth(): double
* +	getHeight(): double
* + getHouseBounds(): Bounds
* + getHouseMask(): Rectangle
* + swap(background: int): void
********************************/

package application;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class Background {
	
	//Fields
	private Image[] imgBack;
	private ImageView ivBack;
	private double width, height;
	public final int main = 0, parkour = 1;
	private Rectangle house, water, river;
	
	//Constructor
	public Background()
	{
		imgBack = new Image[2];
		imgBack[main] = new Image("file:BackgroundsCPT/River.png");
		imgBack[parkour] = new Image("file:BackgroundsCPT/background.png");
		ivBack = new ImageView(imgBack[0]);
		width = imgBack[0].getWidth();
		height = imgBack[0].getHeight();
		house = new Rectangle(70, 10, 300, 200);
		water = new Rectangle(110, 376, 1200, 500);
		river = new Rectangle(780, 0, 300, 250);
	}
	
	//Methods
	public ImageView getImage()
	{
		return ivBack;
	}
	public double getWidth()
	{
		return width;
	}
	public double getHeight()
	{
		return height;
	}
	public Bounds getHouseBounds()
	{
		return house.getBoundsInParent();
	}
	public Bounds getWaterBounds()
	{
		return water.getBoundsInParent();
	}
	public Bounds getRiverBounds()
	{
		return river.getBoundsInParent();
	}
	public void swap(int background) //Swapping Maps
	{
		if (background == 0) { //The First Map 
			ivBack.setImage(imgBack[main]);
			house.setX(70);
			house.setY(10);
			width = imgBack[main].getWidth();
			height = imgBack[main].getHeight();

		} else if(background == 1) { //Battle Map
			ivBack.setImage(imgBack[parkour]);
			ivBack.setLayoutX(-120);
			house.setX(-100);
			house.setY(-100);
			width = imgBack[parkour].getWidth();
			height = imgBack[parkour].getHeight();
		}

	}
}