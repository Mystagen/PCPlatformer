package game;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class PlayerThread extends Thread {
	
	boolean startMoving;
	double left;
	double bottom;
	boolean run;
	boolean keyPressed;
	boolean jump;
	boolean springJump;
	boolean switchPressed;
	boolean gemCollected;
	boolean contact;
	boolean running;
	double screenWidth;
	int imageTrack = 1;
	int jumpCount = 0;
	int animateCounter = 0;
	ImageView activeSpring;
    public List<ImageView> tileList;  
    AnchorPane root;
	ImageView player = new ImageView();
    Image img = new Image("assets/alienBlue_stand.png", true);
    Image walk1 = new Image("assets/alienBlue_walk1.png", true);
    Image walk2 = new Image("assets/alienBlue_walk2.png", true);
    Image jump1 = new Image("assets/alienBlue_jump.png", true);
    Image springSprung = new Image("assets/sprung.png", true);
    Image spring = new Image("assets/spring.png", true);
    Image switchGreenPressed = new Image("assets/switchGreen_pressed.png", true);

    
	
	public PlayerThread(double screenWidth, AnchorPane root) {
		this.run = false;
		this.left = 0.0;
		this.bottom = 0.0;
		this.screenWidth = screenWidth;
		this.keyPressed = false;
		this.jump = false;
		this.springJump = false;
		this.startMoving = false;
		this.root = root;
		this.gemCollected = false;
		this.running = true;
	    player.setImage(img);
	}

    public void run(){
    	while (running) {
    		System.out.println("");
    		if (startMoving) {
	    		if (keyPressed == true) {
			    	if (this.run) {
			    		//Move left
			    		if (this.left > 0) {
			    			if (tileList != null) {
			    				contact = false;
				    			for (ImageView tile : tileList) {
					                if ( 
					                		(
						                		((tile.getBoundsInParent().getMaxY() < (player.getBoundsInParent().getMaxY() - 5))  
						                			&&
						                		(tile.getBoundsInParent().getMaxY() > (player.getBoundsInParent().getMinY())))
					                			||
					                			((tile.getBoundsInParent().getMinY() < (player.getBoundsInParent().getMaxY() - 5))  
					                				&&
					                			(tile.getBoundsInParent().getMinY() > (player.getBoundsInParent().getMinY())))
					                		)
					                	){
					                	if (
					                			(tile.getBoundsInParent().getMaxX() < (player.getBoundsInParent().getMinX() - 3))
					                				&&
					                			(tile.getBoundsInParent().getMaxX() > player.getBoundsInParent().getMinX())
					                		
					                		) {
						                    contact = true;
						                    
					                	}
					                }
				    			}
				    			if (!contact) {
					                this.left --;
						            AnchorPane.setLeftAnchor(player, this.left);  
				    			}
			    			}
			    		}
		                AnchorPane.setLeftAnchor(player, this.left);
			    	} else {
			    		//Move right
			    		if (this.left < (screenWidth - img.getWidth())) {
				    		if (tileList != null) {
			    				contact = false;
				    			for (ImageView tile : tileList) {
					                if ( 
					                		(
						                		((tile.getBoundsInParent().getMaxY() < (player.getBoundsInParent().getMaxY() - 5))  
						                			&&
						                		(tile.getBoundsInParent().getMaxY() > (player.getBoundsInParent().getMinY())))
					                			||
					                			((tile.getBoundsInParent().getMinY() < (player.getBoundsInParent().getMaxY() - 5))  
					                				&&
					                			(tile.getBoundsInParent().getMinY() > (player.getBoundsInParent().getMinY())))
					                		)
					                	){
					                	if (
					                			(tile.getBoundsInParent().getMinX() < (player.getBoundsInParent().getMaxX()))
					                				&&
					                			(tile.getBoundsInParent().getMinX() > player.getBoundsInParent().getMaxX())
					                		
					                		) {
						                    contact = true;
						                    
					                	}
					                }
				    			}    
				    			if (!contact) {
					                this.left ++;
						            AnchorPane.setLeftAnchor(player, this.left);  
				    			}
			    			}
			    		}
			    	}
		
			    	if (animateCounter == 50) {
				    	if (imageTrack == 1) {
				    	    player.setImage(walk2);
				    	    imageTrack = 2;
				    	} else {
				    		player.setImage(walk1);
				    		imageTrack = 1;
				    	}
				    	animateCounter = 0;
			    	}
			    	animateCounter++;
	    		} else {
	    			player.setImage(img);
	    		}
	    		
	    		if (springJump) {
	    			player.setImage(jump1);
	    			jump = true;
	    			if (jumpCount < 100) { 
	    				if (tileList != null) {
	        				boolean test = contactAbove();
	        				if (!test) {
	            				bottom += 3;
	            				AnchorPane.setBottomAnchor(player, this.bottom);
	        				}
	    				}
	    				jumpCount++;
	    			} else {
	    				activeSpring.setImage(spring);
	    				springJump = false;
	    				jumpCount = 0;
	    			}
	    		}
	    		
	    		if (!springJump && jump) {
	    			player.setImage(jump1);
	    			if (jumpCount < 100) {
	    				if (tileList != null) {
	        				if (!contactAbove()) {
	            				bottom++;
	                            AnchorPane.setBottomAnchor(player, this.bottom);
	        				}
	    				}
	    				jumpCount++;
	    			} else {
						jump = false;
						jumpCount = 0;
	    			}
	    		} else {
	    			if (tileList != null) {
	    				contact = false;
		    			for (ImageView tile : tileList)
			                if (player.getBoundsInParent().intersects(tile.getBoundsInParent())) {
			                    contact = true;
			                }
		    			if (!contact) {
		                    if (bottom > 0) {
			                	bottom--;
			                    AnchorPane.setBottomAnchor(player, this.bottom);  
		                	} 
		    			}
	    			}
	    		}
	
		    	try {
					this.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		    	if (tileList != null) {
			    	checkBoundaries();
		    	}
    		}
    	}
    }
    
    private boolean contactAbove() {
    	for (ImageView tile : tileList) {
            if ( 
            		(
                		((tile.getBoundsInParent().getMaxY() < (player.getBoundsInParent().getMinY()))  
                			&&
                		(tile.getBoundsInParent().getMaxY() > (player.getBoundsInParent().getMinY() - 5)))
            		)
            	){
            	if (
            			(
            				((tile.getBoundsInParent().getMinX() < (player.getBoundsInParent().getMaxX()))
            				&&
            				(tile.getBoundsInParent().getMinX() > player.getBoundsInParent().getMinX())
            					)||(
        					(tile.getBoundsInParent().getMaxX() < (player.getBoundsInParent().getMaxX()))
            				&&
            				(tile.getBoundsInParent().getMaxX() > player.getBoundsInParent().getMinX()))
            			)
            		
            		) {
                    return true;
            	}
            }
		}
    	return false;
    }
    
    //Checks to see if against any interactive objects such as springs or spikes
    private void checkBoundaries() {
    	for (ImageView tile : tileList) {
    		if (tile.getId().equals("spring")) {
    			if (player.getBoundsInParent().intersects(tile.getBoundsInParent())) {
                    tile.setImage(springSprung);
                    springJump = true;
                    activeSpring = tile;
                }
    		} else if (tile.getId().equals("greenswitch")) {
    			if (player.getBoundsInParent().intersects(tile.getBoundsInParent())) {
    				tile.setImage(switchGreenPressed);
    				switchPressed = true;
    			}
    		} else if (tile.getId().equals("key")) {
    			if (player.getBoundsInParent().intersects(tile.getBoundsInParent())) {
    				double move = tile.getFitHeight()*-15;
                    AnchorPane.setTopAnchor(tile, move);  
    				switchPressed = true;
    			}
    		} else if (tile.getId().equals("lock") && switchPressed) {
				double move = tile.getFitHeight()*-15;
                AnchorPane.setTopAnchor(tile, move);  
    		} else if (tile.getId().equals("gem")) {
    			if (player.getBoundsInParent().intersects(tile.getBoundsInParent())) {
    				double move = tile.getFitHeight()*-15;
	    			AnchorPane.setTopAnchor(tile, move);
	    			gemCollected = true;
    			}
    		} else if (tile.getId().equals("window") && gemCollected) {
    			if (player.getBoundsInParent().intersects(tile.getBoundsInParent())) {
    				running = false;
    			}
    		}
    	}
    }
    
    public void setTileList(List<ImageView> list) {
    	this.tileList = list;
    }
    
    public void setSize(double height, double width) {
		player.setFitWidth(width);
		player.setFitHeight(height);
    }
    
    public void halt() {
    	this.run = false;
    }
    
    public void setPosition(double leftPosition, double bottomPosition) {
    	this.left = leftPosition;
    	this.bottom = bottomPosition;
        AnchorPane.setLeftAnchor(player, this.left);
        AnchorPane.setBottomAnchor(player, this.bottom);
    }
    
    public void begin() {
    	this.run = true;
    }
    
    public ImageView getPlayer() {
    	return player;
    }
    
    public boolean getKeyPressed() {
    	return keyPressed;
    }
    
    public void setKeyPressed() {
    	if (keyPressed == false) {
    		keyPressed = true;
    	}
    }
    
    public void setKeyDepressed() {
    	if (keyPressed == true) {
    		keyPressed = false;
    	}
    }
    
    public void jump() {
    	jump = true;
    }
    
    public void startMoving() {
    	startMoving = true;
    }
}