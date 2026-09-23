package application;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MenuScreen {
	
	//Fields
	private Font bigFont, dialogFont, buttonFont;
	private Label title1, title2, title3, start, quit, controls;
	private Image imgStartPlayer;
	private ImageView ivStartPlayer;
	private VBox vbox;
	
	//Constructor
	public MenuScreen()
	{
		//Fonts
		bigFont = Font.loadFont("file:THICK.ttf", 80);
		dialogFont = Font.loadFont("file:Jersey10.ttf", 50);
		buttonFont = Font.loadFont("file:Kanit.ttf", 50);

		//Labels for titles
		title1 = new Label("OCEAN WARS");
		title1.setPrefWidth(1000);
		title1.setLayoutX(250);
		title1.setLayoutY(80);
		title1.setTextFill(Color.RED);
		title1.setFont(bigFont);
		title3 = new Label("ICS4U1 CPT");
		title3.setLayoutX(10);
		title3.setFont(dialogFont);
		title3.setTextFill(Color.WHITE);
		title2 = new Label("Aarav Dhill & Jaiden Thomas");
		title2.setPrefWidth(600);
		title2.setLayoutX(330);
		title2.setLayoutY(220);
		title2.setTextFill(Color.WHITE);
		title2.setFont(dialogFont);

		//Labels for buttons
		start = new Label("START");
		start.setFont(buttonFont);
		start.setTextFill(Color.RED);
		quit = new Label("QUIT");
		quit.setFont(buttonFont);
		quit.setTextFill(Color.RED);
		controls = new Label("CONTROLS");
		controls.setFont(buttonFont);
		controls.setTextFill(Color.RED);
		imgStartPlayer = new Image("file:PlayerAnimationCPT/PlayerScreen.gif");
		ivStartPlayer = new ImageView(imgStartPlayer);
		ivStartPlayer.setLayoutX(300);
		ivStartPlayer.setLayoutY(400);
		vbox = new VBox();
		vbox.setLayoutX(700);
		vbox.setLayoutY(400);
		VBox.setMargin(start, new Insets(0, 0, 10, 0));
		VBox.setMargin(quit, new Insets(0, 0, 10, 0));
		vbox.getChildren().addAll(start, quit, controls);

	}
	//Methods
	public Label getStart()
	{
		return start;
	}
	public void switchStartColor(Color c)
	{
		start.setTextFill(c);
	}
	public Label getQuit()
	{
		return quit;
	}
	public void switchQuitColor(Color c)
	{
		quit.setTextFill(c);
	}
	public Label getControls()
	{
		return controls;
	}
	public void switchControlsColor(Color c)
	{
		controls.setTextFill(c);
	}
	public Label getGameTitle()
	{
		return title1;
	}
	public Label getCPTTitle()
	{
		return title3;
	}
	public Label getNameTitle()
	{
		return title2;
	}
	public VBox getVBX()
	{
		return vbox;
	}
	public ImageView getStartPlayer()
	{
		return ivStartPlayer;
	}

}
