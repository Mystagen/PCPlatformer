package game;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
	
    AnchorPane root = new AnchorPane();
    boolean pressed = false;
    boolean spacePressed = false;
    double[] startPosition = {0.0, 0.0};
    public List<ImageView> tileList = new ArrayList<ImageView>();  
    PlayerThread playerThread;
    
    //Determines the stage
    int stage = 1;
    
    double heightOfTile;
    double widthOfTile;
    
    int xLength;
    int yLength;
    
    Image grassFloorLeft = new Image("assets/grassLeft.png", true);
    Image grassFloorMid = new Image("assets/grassMid.png", true);
    Image grassFloorRight = new Image("assets/grassRight.png", true);
    Image grassFloor = new Image("assets/grass.png", true);
    Image sandFloorLeft = new Image("assets/sandLeft.png", true);
    Image sandFloorMid = new Image("assets/sandMid.png", true);
    Image sandFloorRight = new Image("assets/sandRight.png", true);
    Image gemGreen = new Image("assets/gemGreen.png", true);
    Image gemYellow = new Image("assets/gemYellow.png", true);
    Image grassHalfLeft = new Image("assets/grassHalf_left.png", true);
    Image grassHalfMid = new Image("assets/grassHalf_mid.png", true);
    Image grassHalfRight = new Image("assets/grassHalf_right.png", true);
    Image grassHalf = new Image("assets/grassHalf.png", true);
    Image lockGreen = new Image("assets/lockGreen.png", true);
    Image lockYellow = new Image("assets/lockYellow.png", true);
    Image keyYellow = new Image("assets/keyYellow.png", true);
    Image switchGreen = new Image("assets/switchGreen.png", true);
    Image switchGreenPressed = new Image("assets/switchGreen_pressed.png", true);
    Image spikes = new Image("assets/spikes.png", true);
    Image spring = new Image("assets/spring.png", true);
    Image springSprung = new Image("assets/sprung.png", true);
    Image window = new Image("assets/window.png", true);
    Image doorTop = new Image("assets/doorOpen_top.png", true);
    Image doorBottom = new Image("assets/doorOpen_mid.png", true);
    Image lava = new Image("assets/lavaTop_high.png", true);
    Image lockRed = new Image("assets/lockRed.png", true);
    Image keyRed = new Image("assets/keyRed.png", true);
    Image gemRed = new Image("assets/gemRed.png", true);
    Image brownBrick = new Image("assets/brickBrown.png", true);
    Image snow = new Image("assets/snow.png", true);
    Image lockBlue = new Image("assets/lockBlue.png", true);
    Image keyBlue = new Image("assets/keyBlue.png", true);
    Image gemBlue = new Image("assets/gemBlue.png", true);
    BackgroundImage grassBackground = new BackgroundImage(new Image("assets/colored_grass.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    BackgroundImage caveBackground = new BackgroundImage(new Image("assets/colored_shroom.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    BackgroundImage sandBackground = new BackgroundImage(new Image("assets/colored_desert.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    BackgroundImage airBackground = new BackgroundImage(new Image("assets/blue_land.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    
    
    /*
     0 air
     -1 player
     1 grassLeft
     2 grassMid
     3 grassRight
     4 grassHalfLeft
     5 grassHalfMid
     6 grassHalfRight
     7 grassHalf
     8 lock green
     9 switch green
     -9 inverted switchgreen
     10 switch green pressed
     11 spikes
     12 spring
     13 spring sprung
     14 window
     15 gemGreen
     16 grass
     17 sandLeft
     18 sandMid
     19 sandRight
     20 lock yellow
     21 key yellow
     22 gemYellow
     23 brown brick
     24 lava
     25 red gem
     26 red lock
     27 red key
     28 door top
     29 door mid
     30 snow
     31 key blue
     32 lock blue
     33 gem blue
     */
    
    int[][] map1 = {
    	{2,2,2,2,2,2,2,2,2,2,2,2,2},
		{0,-9,0,0,0,0,0,8,0,0,0,0,0},
		{0,0,0,0,0,0,0,8,15,0,0,0,0},
		{0,7,0,0,0,4,5,5,5,6,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,12,0,11,0,0,0,14,0,0,0},
		{0,0,4,5,5,5,5,5,5,5,5,6,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,12,0,0,0,0,0,0,-1,0,0,0,0},
		{2,2,3,11,16,11,1,2,2,3,0,1,2}
    };
    
    int[][] map2 = {
		{0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0},
		{-1,0,21,20,22,0,14},
		{17,18,18,18,18,18,19}
    };
    
    int[][] map3 = {
		{0,0,0,0,0,0,0,0,28,0},
		{0,0,28,0,0,0,14,0,29,0},
		{-1,0,29,0,0,0,23,23,23,0},
		{23,23,23,0,0,0,0,0,28,0},
		{0,0,0,0,0,0,0,0,29,0},
		{28,0,0,27,0,0,0,0,23,0},
		{29,0,0,11,0,11,0,23,0,0},
		{23,23,23,23,23,23,23,0,0,0},
		{28,0,0,0,0,0,0,0,0,28},
		{29,0,0,0,0,26,25,0,0,29},
		{23,24,23,24,23,23,23,24,23,23}
    };
    
    int[][] map4 = {
		{0,0,0,0,0,0,0,0,0,28,0,0,0,0,0,0,0,33},
		{0,30,0,0,0,30,0,0,0,29,0,0,0,0,0,0,0,32},
		{0,0,30,0,0,0,0,30,30,0,0,0,0,0,0,30,30,30},
		{0,0,30,0,0,0,0,14,0,0,0,30,0,30,30,0,0,0},
		{0,0,0,0,0,0,0,30,28,0,0,0,30,0,0,30,0,0},
		{0,0,0,30,30,0,30,0,29,0,0,0,0,0,30,0,0,0},
		{0,30,0,0,0,0,0,0,30,0,0,0,0,0,0,0,0,0},
		{31,0,0,0,0,0,30,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,30,0,30,0,30,0,0,0,0,0,0,0,0,30,0},
		{30,30,0,0,0,0,0,0,0,0,30,0,30,0,30,0,0,28},
		{0,0,0,28,0,0,0,0,30,0,0,0,0,0,0,0,0,29},
		{0,0,0,29,0,0,0,0,0,0,0,0,0,0,0,0,0,30},
		{0,0,0,30,0,0,30,0,0,0,0,0,0,30,0,0,30,0},
		{0,0,0,0,0,0,0,0,0,30,30,0,30,0,30,30,0,0},
		{-1,0,0,0,0,0,0,30,30,0,0,0,0,30,0,0,0,0},
		{30,0,30,0,30,0,30,0,0,0,0,0,0,0,0,0,0,0}
    };
	
	//Player Thread
	
	public static void main(String[] args) {
        launch(args);
    }
	
	public ImageView createTile(String name, Image img, double widthOfTile, double heightOfTile, int x, int y, boolean invert) {

		tile tile = new tile(img, name);

		ImageView tileObject = tile.createTile();
		
		tileObject.setId(tile.getType());

		tileList.add(tileList.size(), tileObject);
		
		tileObject.setFitWidth(widthOfTile);
		tileObject.setFitHeight(heightOfTile);

        AnchorPane.setBottomAnchor(tileObject, heightOfTile * (xLength-x) - heightOfTile);
        AnchorPane.setLeftAnchor(tileObject, (widthOfTile * y));
        

        if (invert) {
            tileObject.setScaleY(-1);
        }
        
        return tileObject;
        
	}
	
	public void drawMap(int[][] mapToDraw) {
		xLength = mapToDraw.length;
		yLength = mapToDraw[0].length;
		double width = root.getWidth();
		double height = root.getHeight();
		widthOfTile = width / (yLength);
		heightOfTile = height / (xLength);
		for (int x = 0; x < xLength; x++) {
			for (int y = 0; y < yLength; y++) {
				int currentTile = mapToDraw[x][y];
				switch(currentTile) {
				case -9:
					root.getChildren().add(createTile("greenswitch", switchGreen, widthOfTile, heightOfTile, x, y, true));
					break;
				case -1:
					startPosition[0] = heightOfTile * (xLength-x) - heightOfTile;
					startPosition[1] = widthOfTile * y;
					break;
				case 0:
					//Air
					break;
				case 1:
					root.getChildren().add(createTile("floor", grassFloorLeft, widthOfTile, heightOfTile, x, y, false));
					break;
				case 2:
					root.getChildren().add(createTile("floor", grassFloorMid, widthOfTile, heightOfTile, x, y, false));
					break;
				case 3:
					root.getChildren().add(createTile("floor", grassFloorRight, widthOfTile, heightOfTile, x, y, false));
					break;
				case 4:
					root.getChildren().add(createTile("floor", grassHalfLeft, widthOfTile, heightOfTile, x, y, false));
					break;
				case 5:
					root.getChildren().add(createTile("floor", grassHalfMid, widthOfTile, heightOfTile, x, y, false));
					break;
				case 6:
					root.getChildren().add(createTile("floor", grassHalfRight, widthOfTile, heightOfTile, x, y, false));
					break;
				case 7:
					root.getChildren().add(createTile("floor", grassHalf, widthOfTile, heightOfTile, x, y, false));
					break;
				case 8:
					root.getChildren().add(createTile("lock", lockGreen, widthOfTile, heightOfTile, x, y, false));
					break;
				case 9:
					root.getChildren().add(createTile("greenswitch", switchGreen, widthOfTile, heightOfTile, x, y, false));
					break;
				case 10:
					root.getChildren().add(createTile("switch", switchGreenPressed, widthOfTile, heightOfTile, x, y, false));
					break;
				case 11:
					root.getChildren().add(createTile("spikes", spikes, widthOfTile, heightOfTile, x, y, false));
					break;
				case 12:
					root.getChildren().add(createTile("spring", spring, widthOfTile, heightOfTile, x, y, false));
					break;
				case 13:
					root.getChildren().add(createTile("spring", springSprung, widthOfTile, heightOfTile, x, y, false));
					break;
				case 14:
					root.getChildren().add(createTile("window", window, widthOfTile, heightOfTile, x, y, false));
					break;
				case 15:
					root.getChildren().add(createTile("gem", gemGreen, widthOfTile, heightOfTile, x, y, false));
					break;
				case 16:
					root.getChildren().add(createTile("floor", grassFloor, widthOfTile, heightOfTile, x, y, false));
					break;
				case 17:
					root.getChildren().add(createTile("floor", sandFloorLeft, widthOfTile, heightOfTile, x, y, false));
					break;
				case 18:
					root.getChildren().add(createTile("floor", sandFloorMid, widthOfTile, heightOfTile, x, y, false));
					break;
				case 19:
					root.getChildren().add(createTile("floor", sandFloorRight, widthOfTile, heightOfTile, x, y, false));
					break;
				case 20:
					root.getChildren().add(createTile("lock", lockYellow, widthOfTile, heightOfTile, x, y, false));
					break;
				case 21:
					root.getChildren().add(createTile("key", keyYellow, widthOfTile, heightOfTile, x, y, false));
					break;
				case 22:
					root.getChildren().add(createTile("gem", gemYellow, widthOfTile, heightOfTile, x, y, false));
					break;
				case 23:
					root.getChildren().add(createTile("floor", brownBrick, widthOfTile, heightOfTile, x, y, false));
					break;
				case 24:
					root.getChildren().add(createTile("lava", lava, widthOfTile, heightOfTile, x, y, false));
					break;
				case 25:
					root.getChildren().add(createTile("gem", gemRed, widthOfTile, heightOfTile, x, y, false));
					break;
				case 26:
					root.getChildren().add(createTile("lock", lockRed, widthOfTile, heightOfTile, x, y, false));
					break;
				case 27:
					root.getChildren().add(createTile("key", keyRed, widthOfTile, heightOfTile, x, y, false));
					break;
				case 28:
					root.getChildren().add(createTile("doorTop", doorTop, widthOfTile, heightOfTile, x, y, false));
					break;
				case 29:
					root.getChildren().add(createTile("doorBottom", doorBottom, widthOfTile, heightOfTile, x, y, false));
					break;
				case 30:
					root.getChildren().add(createTile("floor", snow, widthOfTile, heightOfTile, x, y, false));
					break;
				case 31:
					root.getChildren().add(createTile("key", keyBlue, widthOfTile, heightOfTile, x, y, false));
					break;
				case 32:
					root.getChildren().add(createTile("lock", lockBlue, widthOfTile, heightOfTile, x, y, false));
					break;
				case 33:
					root.getChildren().add(createTile("gem", gemBlue, widthOfTile, heightOfTile, x, y, false));
					break;
				default:
					break;
				}
			}
		}
	}
	
	public void removeTile(ImageView tile) {
		root.getChildren().remove(tile);
	}
	
	public void finished() {
		Platform.exit();
	}

	@Override
    public void start(Stage primaryStage) {

		primaryStage.setTitle("Question Master");
        
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
               Platform.exit();
               System.exit(0);
            }
         });
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        primaryStage.setScene(new Scene(root, (screenSize.width/2), (screenSize.height)));
        
        primaryStage.setFullScreen(true);

        PlayerThread playerThread = new PlayerThread(root.getWidth(), root);
        playerThread.start();
		playerThread.halt();
		
        root.getChildren().add(playerThread.getPlayer());
        
        root.setOnKeyPressed(new EventHandler<KeyEvent>() {
        	public void handle(KeyEvent ke) {
	            if (ke.getCode()==KeyCode.LEFT) {
	            	if (!pressed) {
		                playerThread.getPlayer().setScaleX(-1);
		                playerThread.startMoving();
		                playerThread.begin();
		                playerThread.setKeyPressed();
		                pressed = true;
	            	}
	                
	            } else if (ke.getCode()==KeyCode.RIGHT) {
	            	if (!pressed) {
		            	playerThread.getPlayer().setScaleX(1);
		                playerThread.startMoving();
		                playerThread.halt();
		                playerThread.setKeyPressed();
		                pressed = true;
	            	}
	                
	            } else if (ke.getCode()==KeyCode.SPACE) {
	            	if (!spacePressed) {
		                playerThread.startMoving();
		                playerThread.jump();
		                spacePressed = true;
	            	}
	            } else if (ke.getCode()==KeyCode.ESCAPE) {
	            	primaryStage.close();
	            	playerThread.stop();
	            }
        	}
        });
        
        root.setOnKeyReleased(new EventHandler<KeyEvent>() {
        	public void handle(KeyEvent ke) {
        		if (ke.getCode()==KeyCode.LEFT) {
	                playerThread.setKeyDepressed();
	                pressed = false;
	                
	            } else if (ke.getCode()==KeyCode.RIGHT) {
	                playerThread.setKeyDepressed();
	                pressed = false;
	            } else if (ke.getCode()==KeyCode.SPACE) {
	                spacePressed = false;
	            } else {
	            	if (!playerThread.isAlive()) {
	            		finished();
	            	}
	            }
        	}
        });
        root.requestFocus();
        primaryStage.show();
        
        if (stage == 1) {
            root.setBackground(new Background(grassBackground));
            drawMap(map1);
        	
        } else if (stage == 2) {
            root.setBackground(new Background(caveBackground));
            drawMap(map2);
        
        } else if (stage == 3 ) {
            root.setBackground(new Background(sandBackground));
            drawMap(map3);
        	
        } else if (stage == 4) {
            root.setBackground(new Background(airBackground));
            drawMap(map4);
        	
        }
		playerThread.setSize(heightOfTile*2, widthOfTile);
        playerThread.setTileList(tileList);
        playerThread.setPosition(startPosition[1], startPosition[0]);
		
	}

}
