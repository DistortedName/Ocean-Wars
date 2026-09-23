/*
 * JAIDEN & AARAV
 * JUNE, 4, 2026
 * ICS4U1
 * Coding a user friendly game, where the player has to defeat a Boss. The game first begins
 * with a home screen where the player has the option to start the game, quit the game, or understand
 * the controls of the game. Once the player clicks start, they are brought to the first map, where they
 * encounter a wizard that encourages them to defeat the boss. Once the player crosses the bridge they
 * are brought to the first level, where they have to defeat the boss. The boss fight is made up of two levels.
 * The first one where octopuses are spawned from random directions and the player has to dodge them.
 * The next level is a 1v1 battle with the boss. During this, the boss will teleport to random locations
 * and attempt to hit you with his own projectile. You must dodge his attack and hit him back. You can only win
 * once boss has no more health. If you fail to do this and are killed, you are brought to the losing screen.
 */

package application;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

	//Fields
	private Scene scene;
	private Pane root;
	private Label lblBoss, lblDialog, fight;
	private Player p;
	private ArrayList<Enemy> enemy;
	private int enemyCounter, lives, waveCounter;
	private Projectile splash;
	private Boss boss;
	private Image imgHeart;
	private ArrayList<ImageView> ivHeart;
	private Background back;
	private AnimationTimer anim;
	private boolean up, down, left, right, idle, hurt, nextMap, 
	checkBounds, canHit, bossFight, win, loss;
	private final int Map2 = 1;
	private Timeline timer, eTimer, cutscene, hurtTimer, bossDialogue, wave1, wave2, mudTimer;
	private int xImg = 300, yImg = 5;
	private File file, backgroundSound;
	private FileReader fr;
	private BufferedReader br;
	private Button btnWizard;
	private ArrayList<String> speech;
	private int lineCount, lineCounter;
	private Font bigFont, dialogFont, buttonFont;
	private Image imgStart;
	private ImageView ivStart;
	private AudioClip[] clips;
	private Media media;
	private MediaPlayer mPlayer;
	private MenuScreen menu;

	public void start(Stage primaryStage) throws IOException {
		try {	
			//Main pane layout
			root = new Pane();
			imgStart = new Image("file:images/start.png");
			ivStart = new ImageView(imgStart);
			ivStart.setLayoutY(-200);
			scene = new Scene(root,1155, 800); //Setting the size of the scene

			//Background Music
			backgroundSound = new File("Sounds/Background.mp3");
			media = new Media(backgroundSound.toURI().toString());
			mPlayer = new MediaPlayer(media);
			mPlayer.play();
			mPlayer.setOnEndOfMedia(new Runnable() { //looping the background music to run forever until stopped
				public void run() {
					mPlayer.seek(Duration.ZERO);
				}
			});

			//Fonts
			bigFont = Font.loadFont("file:THICK.ttf", 80);
			dialogFont = Font.loadFont("file:Jersey10.ttf", 50);
			buttonFont = Font.loadFont("file:Kanit.ttf", 50);

			//Calling the menu class which contain all the buttons and titles for our first intro screen
			menu = new MenuScreen();
			//when buttons are clicked or hovered over
			menu.getStart().setOnMouseClicked(new EventHandler<MouseEvent> ()  //When the start button is clicked
					{
				public void handle(MouseEvent e) 
				{
					//Remove all nodes from the screen
					root.getChildren().removeAll(ivStart, menu.getGameTitle(), 
							menu.getNameTitle(), menu.getCPTTitle(), menu.getVBX(), menu.getStartPlayer());
					try {
						//Calling the method to start the first level of the game
						game();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

					});
			menu.getStart().setOnMouseEntered(new EventHandler<MouseEvent> () //When the mouse enters the button node
					{
				public void handle(MouseEvent e) {
					menu.switchStartColor(Color.WHITE); //Changing the color of the label text
				}
					});
			menu.getStart().setOnMouseExited(new EventHandler<MouseEvent> () //when the mouse exits the button node
					{
				public void handle(MouseEvent e) {
					menu.switchStartColor(Color.RED); //Changing the color back to red

				}
					});
			menu.getQuit().setOnMouseEntered(new EventHandler<MouseEvent> () //When the mouse enters the button node
					{
				public void handle(MouseEvent e) {
					menu.switchQuitColor(Color.WHITE);//Changing the color of the label text
				}
					});
			menu.getQuit().setOnMouseExited(new EventHandler<MouseEvent> () //When the mouse exits the button node
					{
				public void handle(MouseEvent e) {
					menu.switchQuitColor(Color.RED);//Changing the color back to red
				}
					});
			menu.getQuit().setOnMouseClicked(new EventHandler<MouseEvent> () //When the quit button is clicked
					{
				public void handle(MouseEvent e) 
				{
					//Display the alert to ask the user if they really want to quit
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setHeaderText(null);
					alert.setContentText("Are you sure you want to exit?");
					alert.setTitle("Exit Game");
					alert.getButtonTypes().clear();
					alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.YES)
					{
						Platform.exit();
						System.exit(0);
					}
				}

					});
			menu.getControls().setOnMouseEntered(new EventHandler<MouseEvent> () //When the mouse enters the button node
					{
				public void handle(MouseEvent e) {
					menu.switchControlsColor(Color.WHITE);//Changing the color of the label text
				}
					});
			menu.getControls().setOnMouseExited(new EventHandler<MouseEvent> () //When the mouse entersexits the button node
					{
				public void handle(MouseEvent e) {
					menu.switchControlsColor(Color.RED);//Changing the color back to red
				}
					});
			menu.getControls().setOnMouseClicked(new EventHandler<MouseEvent> () //When the controls button is clicked
					{
				public void handle(MouseEvent e) 
				{
					//Displaying the control screen where the buttons to control the player are explained
					Rectangle rect = new Rectangle(0, 0, 
							scene.getWidth(), scene.getHeight());
					Label lblControls = new Label("CONTROLS");
					lblControls.setFont(bigFont);
					lblControls.setPrefSize(1000, 400);
					lblControls.setTextFill(Color.RED);
					lblControls.setLayoutX(scene.getWidth() / 2 - lblControls.getPrefWidth() / 2 + 200);
					lblControls.setLayoutY(-100);

					//Setting a VBox to store all the text and graphics
					VBox controls = new VBox();
					Label lblMove = new Label();
					lblMove.setText("Use Arrow Keys to Move");
					lblMove.setFont(dialogFont);
					lblMove.setTextFill(Color.RED);
					lblMove.setGraphic(new ImageView(new Image("file:PlayerAnimationCPT/PlayerWalkR.gif")));
					lblMove.setContentDisplay(ContentDisplay.LEFT);
					Label lblShoot = new Label();
					lblShoot.setText("Use SPACE to shoot projectile");
					lblShoot.setFont(dialogFont);
					lblShoot.setTextFill(Color.RED);
					lblShoot.setGraphic(new ImageView(new Image("file:images/waterProjectileR.gif")));
					lblShoot.setContentDisplay(ContentDisplay.RIGHT);
					controls.setSpacing(10);
					controls.setLayoutX(300);
					controls.setLayoutY(300);
					controls.getChildren().addAll(lblMove, lblShoot);

					//Return button
					Button btnContinue = new Button("Continue");
					btnContinue.setStyle("-fx-background-color: black; -fx-text-fill: red");
					btnContinue.setFont(buttonFont);
					btnContinue.setPrefSize(300, 50);
					btnContinue.setLayoutX(scene.getWidth() / 2 - btnContinue.getPrefWidth() / 2);
					btnContinue.setLayoutY(scene.getHeight() - btnContinue.getHeight() - 120);
					btnContinue.setOnAction(new EventHandler<ActionEvent>() 
					{
						public void handle(ActionEvent e) { //When the use clicks the return button
							root.getChildren().removeAll(rect, btnContinue, lblControls, controls);
						}
					});
					root.getChildren().addAll(rect, btnContinue, lblControls, controls);
				}
					});

			//Add start screen
			root.getChildren().addAll(ivStart, menu.getGameTitle(), menu.getNameTitle(), 
					menu.getCPTTitle(), menu.getVBX(), menu.getStartPlayer());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false); //Pane is not resizable
			primaryStage.setTitle("CPT");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void clicked() //Continue button
	{
		if(lineCounter < lineCount) //Go through each line in the text file
		{
			lblDialog.setText(speech.get(lineCounter));
			lineCounter++;
			if(lineCounter == lineCount)//Final Line
			{
				btnWizard.setText("Finish");
			}
			else {
				btnWizard.setText("Continue");
			}
		}
		else
		{
			// Dialogue finished
			root.getChildren().removeAll(btnWizard, lblDialog);
		}	
	}


	public void game() throws IOException {
		//Sounds for different game events
		clips = new AudioClip[] {
				new AudioClip("file:Sounds/PlayerShoot.wav"),
				new AudioClip("file:Sounds/Win.mp3"),
				new AudioClip("file:Sounds/Buzzer.wav"),
				new AudioClip("file:Sounds/lose.mp3")
		};

		//Fonts
		bigFont = Font.loadFont("file:THICK.ttf", 200);
		dialogFont = Font.loadFont("file:Jersey10.ttf", 40);

		//Initializing the booleans
		nextMap = false;
		checkBounds = true;
		canHit = true;
		bossFight = false;
		waveCounter = 0;

		//Calling the background class
		back = new Background();
		root.getChildren().addAll(back.getImage()); //Adding a new background to the screen

		//Player Class
		p = new Player();
		p.setLocation(400, 200); //Starting position of the player
		root.getChildren().addAll(p.getNode()); //Adding the player to the screen

		//Initializing the booleans to control the player
		up = false;
		down = false;
		right = false;
		left = false;
		hurt = false;

		//Enemy Class and Spawn
		enemy = new ArrayList<Enemy>();
		enemyCounter = -1;
		KeyFrame kfEnemy = new KeyFrame(Duration.millis(500), new EventHandler<ActionEvent>() //A keyframe to spawn the enemy in the first level 
				{
			public void handle(ActionEvent e) 
			{
				enemyCounter++;
				enemy.add(enemyCounter, new Enemy()); //Adding a new enemy to the arraylist
				enemy.get(enemyCounter).randomizeDirection(); //Calling the method to randomize the direction of the enemy
				enemy.get(enemyCounter).setLocation(scene.getWidth(), scene.getHeight());
				root.getChildren().addAll(enemy.get(enemyCounter).getNode());
			}

				});
		eTimer = new Timeline(kfEnemy);
		eTimer.setCycleCount(Timeline.INDEFINITE);

		//Wave 1 Label
		lblBoss = new Label();
		lblBoss.setPrefWidth(500);
		lblBoss.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
		lblBoss.setFont(dialogFont);
		lblBoss.setVisible(false);
		root.getChildren().add(lblBoss);

		//File Reader
		file = new File("text.txt");
		fr = new FileReader(file);
		br = new BufferedReader(fr);
		String line;
		btnWizard = new Button("Hint");
		btnWizard.setPrefSize(300, 30);
		btnWizard.setFont(dialogFont);
		btnWizard.setLayoutX(scene.getWidth() / 2 - btnWizard.getPrefWidth() / 2);
		btnWizard.setLayoutY(700);
		btnWizard.setStyle("-fx-background-color: black; -fx-text-fill: red");
		lblDialog = new Label();
		lblDialog.setPrefSize(800, 100);
		lblDialog.setFont(dialogFont);
		lblDialog.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
		lblDialog.setWrapText(true);
		lblDialog.setLayoutX(scene.getWidth() / 2 - lblDialog.getPrefWidth() / 2 - 30);
		lblDialog.setLayoutY(scene.getHeight() - lblDialog.getPrefHeight() - 100);
		lblDialog.setGraphic(new ImageView(new Image("file:images/wizard.gif")));
		lblDialog.setContentDisplay(ContentDisplay.LEFT);
		lineCount = 0;
		lineCounter = 0;
		speech = new ArrayList<String>();

		//Introduces the game
		while ((line = br.readLine()) != null)
		{
			speech.add(lineCount, line); //Store each line of text, in a different index to dislpay later
			lineCount++; //Increasing the count to store the number of line stored in the arraylisrt
		}
		br.close(); //Closing the file
		btnWizard.setOnAction(e -> clicked()); //When the hint button is clicked
		root.getChildren().addAll(lblDialog, btnWizard);

		//Hearts
		lives = 10; //Setting up the lives of the player
		imgHeart = new Image("file:images/Heart.png"); //Loading the image
		ivHeart = new ArrayList<ImageView>(); //Setting up an arraylist to store and display multiple hearts
		for (int i = 0; i < lives; i++)
		{
			//Add the lives (as multiple hearts) to the screen
			ivHeart.add(i, new ImageView(imgHeart));
			ivHeart.get(i).setLayoutX(xImg);
			ivHeart.get(i).setLayoutY(yImg);
			root.getChildren().add(ivHeart.get(i));
			xImg += 50;
		}

		//Hurt Animation
		KeyFrame kfHurt = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() { //Using a keyframe to run the hurt animation whenever the player is hit

			public void handle(ActionEvent e)
			{
				hurt = false;
				hurtTimer.stop(); //hurt animation lasts 1 second
			}

		});
		hurtTimer = new Timeline(kfHurt);
		hurtTimer.setCycleCount(1);

		//Calling the projectile class
		splash = new Projectile();
		KeyFrame kfSplash = new KeyFrame(Duration.millis(30), new EventHandler<ActionEvent> () { //A keyframe to move the player shot

			public void handle(ActionEvent e) 
			{
				splash.move(); //Calling the method to move the player shot
				if (splash.offScreen(scene.getWidth()))//Boundary Check
				{
					//Removing and stopping the projectile when offscreen
					splash.stopShoot();
					root.getChildren().remove(splash.getImage());
					timer.stop();
				}
			}
		});
		timer = new Timeline(kfSplash);
		timer.setCycleCount(Timeline.INDEFINITE);

		//Fight Title
		fight = new Label("FIGHT!");
		fight.setFont(bigFont);
		fight.setPrefSize(800, 100);
		fight.setTextFill(Color.WHITE);
		fight.setLayoutX(scene.getWidth() / 2 - fight.getWidth() / 2 - 350);
		fight.setLayoutY(180);

		//Boss Class
		boss = new Boss();

		//Mud Timer for boss to shoot the mud
		KeyFrame kfMud =new KeyFrame(Duration.millis(20), new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e)
			{
				boss.shootMove();
			}
		});

		mudTimer = new Timeline(kfMud);
		mudTimer.setCycleCount(Timeline.INDEFINITE);


		//Cutscene Timeline
		KeyFrame kfScene = new KeyFrame(Duration.millis(50), new EventHandler<ActionEvent> () {//A keyframe to run the cutscene before the first level

			public void handle(ActionEvent e) 
			{
				//Float the player down
				p.moveDown();
				if (p.getY() >= scene.getHeight() / 2 - 50) //Once the player reaches center of battlefield
				{
					root.getChildren().remove(fight);
					cutscene.stop();
					//Add the boss and the boss projectile
					boss.setLocation(scene.getWidth()/2+100, scene.getHeight()/2 - boss.getHeight()/2);
					root.getChildren().addAll(boss.getNode(), boss.getHealthBar());
					lblBoss.setText("WAVE 1: OCTOPUSES, ATTACK!");
					lblBoss.setLayoutX(scene.getWidth()/2 - 200);
					lblBoss.setLayoutY(100);
					lblBoss.setVisible(true);
					bossDialogue.play();

				}
			}
		});
		cutscene = new Timeline(kfScene);
		cutscene.setCycleCount(Timeline.INDEFINITE);

		//Wave Dialogs
		KeyFrame kfBossDialog = new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent> () 
		{
			public void handle(ActionEvent e) //Go through each wave dialog
			{
				if (waveCounter == 0)
				{
					waveCounter++;
					lblBoss.setVisible(false);

					//Start hte first wave
					eTimer.play();
					wave1.play();
					anim.start();
				}
				else if (waveCounter == 1)
				{
					bossFight = true;
					wave1.stop();
					lblBoss.setVisible(true);
					lblBoss.setText("WAVE 2: TRY YOUR LUCK AGAINST ME NOW!!");
					bossDialogue.stop();
					anim.start();
					wave2.play(); //Start the second wave
				}

			}
		});
		bossDialogue = new Timeline(kfBossDialog);
		bossDialogue.setCycleCount(1);

		//Waves
		KeyFrame kfWave1 = new KeyFrame(Duration.seconds(12), new EventHandler<ActionEvent> () 
		{
			public void handle(ActionEvent e) 
			{
				//Remove all enemies at the end of the keyframe
				for(int i = 0; i < enemy.size(); i++) 
				{
					root.getChildren().remove(enemy.get(i).getNode());
				}
				lblBoss.setVisible(false);
				anim.stop();
				eTimer.stop();
				bossDialogue.play();

			}
		});
		wave1 = new Timeline(kfWave1);
		wave1.setCycleCount(1);

		//Wave 2
		KeyFrame kfWave2 = new KeyFrame(Duration.millis(3000), new EventHandler<ActionEvent> () 
		{
			public void handle(ActionEvent e) 
			{
				lblBoss.setVisible(false);
				boss.teleport(scene.getWidth(), scene.getHeight(),
						p.getX(), p.getY()); //Teleporting the boss
				boss.shoot(p.getX(), p.getY());
				if (!root.getChildren().contains(boss.getMud())) //If the other projectile is offscreen
				{
					root.getChildren().add(boss.getMud());
				}
				mudTimer.play();

			}
		});
		wave2 = new Timeline(kfWave2);
		wave2.setCycleCount(Timeline.INDEFINITE);

		//Animation
		anim = new AnimationTimer() {

			public void handle(long val) 
			{
				//Store previous position for river boundaries, so the player does go across the boundaries
				double oldX = p.getX();
				double oldY = p.getY();

				//Player Movement and Boundary Checking
				if (up) //Calls the getUP method in the KeyMovements class
				{
					p.moveUp();
					if (p.getY() <= 0)
					{
						p.setY(0);
					}
				}
				if (down)//Calls the getDOWN method in the KeyMovements class
				{
					p.moveDown();
					if (p.getY() + p.getHeight() >= scene.getHeight())
					{
						p.setY(scene.getHeight() - p.getHeight());
					}
				}
				if (left)//Calls the getLEFT method in the KeyMovements class
				{
					p.moveLeft();
					if (p.getX() <= 0)
					{
						p.setX(0);
					}
				}
				if (right)//Calls the getRIGHT method in the KeyMovements class
				{
					p.moveRight();
					if (p.getX() + p.getWidth() >= scene.getWidth() 
							&& nextMap == false) //Next Map
					{
						nextMap = true; //Battle Map
						checkBounds = false; //Done to stop boundary checking
						p.setX(100);
						p.setY(0 - p.getWidth());
						back.swap(Map2); //Swap to battle map, start the cutscene
						anim.stop();
						cutscene.play();
						root.getChildren().add(fight);
					}
					else if (p.getX() + p.getWidth() >= scene.getWidth())
					{
						p.setX(scene.getWidth() - p.getWidth());
					}
				}
				idle = right || left || up || down; //Checks if player is idle or not
				p.update(idle, hurt); //Update Animation

				//Boundary Check
				if (checkBounds) //Disable bounds
				{
					if (p.getPBounds().intersects(back.getHouseBounds()) //All bounds in first map
							|| p.getPBounds().intersects(back.getWaterBounds()) 
							||p.getPBounds().intersects(back.getRiverBounds()))
					{
						p.setLocation(oldX, oldY); //Sets the location back to old one
					}
				}

				//Attacks & Damage
				if (bossFight && canHit && splash.getMask().intersects
						(boss.getNode().getBoundsInParent()))
				{
					canHit = false;
					boss.updateHealth(); //Decrease health
					splash.stopShoot();
					root.getChildren().remove(splash.getImage());
					timer.stop();
				}
				else if (bossFight && p.getPBounds().intersects(boss.getMud().getBoundsInParent()))
				{
					boss.resetMud(); 
					clips[2].play();
					mudTimer.stop();
					//Remove lives
					hurt = true;
					hurtTimer.playFromStart();
					root.getChildren().remove(boss.getMud());
					lives--;
					root.getChildren().remove(ivHeart.get(lives));
				}

				//Enemy Movement
				for (int i = 0; i < enemy.size(); i++)
				{
					enemy.get(i).move();

					//Collision Checking
					if (enemy.get(i).getMask().intersects(p.getPBounds()))
					{
						clips[2].play();
						hurt = true;
						hurtTimer.play();
						//Player took damages, lives decreased
						root.getChildren().remove(enemy.get(i).getNode());
						enemy.remove(i);
						lives--;
						root.getChildren().remove(ivHeart.get(lives));
						enemyCounter--;
					}
					else if (enemy.get(i).getX() >= scene.getWidth() 
							&& enemy.get(i).getDirection() == 0) //Off Screen to the right
					{
						root.getChildren().remove(enemy.get(i).getNode());
						enemy.remove(i);
						enemyCounter--;
					}
					else if (enemy.get(i).getX() + enemy.get(i).getWidth() <= 0
							&& enemy.get(i).getDirection() == 1) //Off Screen to the left
					{
						root.getChildren().remove(enemy.get(i).getNode());
						enemy.remove(i);
						enemyCounter--;
					}
				}
				boss.updateDirection(p.getX()); //Update which direction boss faces

				//Death or Win
				if (boss.getHealthWidth() ==  0) //Player wins
				{
					win = true;
					root.getChildren().removeAll(boss.getNode(), boss.getHealthBar());
				}
				else if (lives == 0) //Boss Win
				{
					loss = true;
					root.getChildren().remove(p.getNode());
				}

				//Runnable
				Platform.runLater(new Runnable() {

					public void run() 
					{
						if (win) //Player wins the game
						{
							nextMap = false;
							//Stop all timers
							clips[1].play();
							mPlayer.stop();
							anim.stop();
							eTimer.stop();
							wave1.stop();
							wave2.stop();
							root.getChildren().clear();
							Button btnFinish = new Button("Finish");
							btnFinish.setFont(dialogFont);
							btnFinish.setStyle("-fx-background-color: black; -fx-text-fill: red");
							btnFinish.setPrefSize(300, 50);
							btnFinish.setLayoutX(scene.getWidth() / 2 - btnFinish.getPrefWidth() / 2 + 20);
							btnFinish.setLayoutY(scene.getHeight() - btnFinish.getPrefHeight());
							btnFinish.setFocusTraversable(false);
							btnFinish.setOnAction(e -> //End Game
							{
								Platform.exit();
								System.exit(0);
							});
							Image imgWin = new Image("file:images/win.png");
							ImageView ivWin = new ImageView(imgWin);
							ivWin.setFitWidth(1155);
							ivWin.setPreserveRatio(true);
							root.getChildren().addAll(ivWin, btnFinish);

						}
						else if (loss) //Player losses game
						{
							nextMap = false;
							//Stop all timers
							clips[3].play();
							mPlayer.stop();
							anim.stop();
							eTimer.stop();
							wave1.stop();
							wave2.stop();
							root.getChildren().clear();
							Button btnFinish = new Button("Finish");
							btnFinish.setFont(dialogFont);
							btnFinish.setStyle("-fx-background-color: black; -fx-text-fill: red");
							btnFinish.setPrefSize(300, 50);
							btnFinish.setLayoutX(scene.getWidth() / 2 - btnFinish.getPrefWidth() / 2 + 20);
							btnFinish.setLayoutY(scene.getHeight() - btnFinish.getPrefHeight());
							btnFinish.setFocusTraversable(false);
							btnFinish.setOnAction(e -> //End Game
							{
								Platform.exit();
								System.exit(0);
							});
							Image imgWin = new Image("file:images/loss.png"); 
							ImageView ivWin = new ImageView(imgWin);
							ivWin.setFitWidth(1155);
							ivWin.setPreserveRatio(true);
							root.getChildren().addAll(ivWin, btnFinish);

						}
					}
				});
			}
		};
		anim.start();

		//KeyMovement
		scene.setOnKeyPressed(new EventHandler<KeyEvent> () 
		{
			public void handle(KeyEvent e) 
			{
				if (e.getCode() == KeyCode.UP)
				{
					up = true;
				}
				if (e.getCode() == KeyCode.DOWN)
				{
					down = true;
				}
				if (e.getCode() == KeyCode.LEFT)
				{
					left = true;
					p.setDirection(p.LEFT);
				}
				if (e.getCode() == KeyCode.RIGHT)
				{
					right = true;
					p.setDirection(p.RIGHT);
				}
				if (e.getCode() == KeyCode.SPACE)
				{
					if (splash.getShoot() == false && nextMap) //Can only shoot projectiles in fight
					{
						clips[0].play();
						canHit = true; //Means the player can shoot the boss
						splash.setDirection(p.getDirection());
						splash.setLocation(p.getX(), p.getY(), p.getDirection());
						root.getChildren().addAll(splash.getImage());
						timer.play();
					}
				}
			}
		});
		scene.setOnKeyReleased(new EventHandler<KeyEvent> () 
		{
			public void handle(KeyEvent e) 
			{
				if (e.getCode() == KeyCode.UP)
				{
					up = false;
				}
				if (e.getCode() == KeyCode.DOWN)
				{
					down = false;
				}
				if (e.getCode() == KeyCode.LEFT)
				{
					left = false;
				}
				if (e.getCode() == KeyCode.RIGHT)
				{
					right = false;
				}
			}
		});

	}

	public static void main(String[] args) {
		launch(args);
	}
}