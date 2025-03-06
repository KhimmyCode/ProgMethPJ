package scenes;

import java.util.ArrayList;

import application.SceneManager;
import entity.enemies.Jail;
import entity.enemies.Orc;
import entity.enemies.Slime;
import entity.enemies.Werebear;
import entity.heros.Archer;
import entity.heros.Knight;
import entity.heros.Lancer;
import entity.heros.Priest;
import entity.heros.Wizard;
import entity.interfaces.Action;
import entity.interfaces.Unit;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import logic.GameLogic;

public class FieldCanvas extends Canvas {
	GameScene gameScene;
	public FieldCanvas(double width, double height,GameScene gameScene) {
		super(width, height);
		updateCanvas(getGraphicsContext2D());
		this.gameScene=gameScene;
	}

	public void updateCanvas(GraphicsContext gc) {
		Thread t = new Thread(() -> {
			while (!GameLogic.isEnd()) {
				ArrayList<Unit> toremove = new ArrayList<Unit>();
				ArrayList<Unit> toremoveHero = new ArrayList<Unit>();

				try {
//	            	System.out.println("updating...");
					Platform.runLater(() -> {
						// Clear the canvas
						gc.clearRect(0, 0, getWidth(), getHeight());

						// Draw background placeholder
						Image background = new Image("background/level.png");
						gc.drawImage(background, 0, 0, 800, 400);

						// Draw left base

						Image ci = new Image("background/castle.png");
						Image ji = new Image("background/cage.png");

						gc.drawImage(ci, 0, 200, 185.3 * 0.8, 152.6 * 0.8);
						gc.drawImage(ji, 700, 170, 209 * 0.5, 309 * 0.5);
						
						gc.setFill(Color.RED);
						SceneManager smanager = new SceneManager();
						Font pixelFont = smanager.loadFont("/fonts/pixel2.ttf", 25);
						gc.setFont(pixelFont);
						
						
						int castleHp = GameLogic.getInstance().getOurTeamUnits().get(0).getHealth();
						int jailHp = GameLogic.getInstance().getEnemyTeamUnits().get(0).getHealth();
						// แสดง HP ของ Castle (ฐานของเรา)
						gc.fillText("HP: " +castleHp , 23, 190); // ตำแหน่ง X = 20, Y = 190

						// แสดง HP ของ Jail (ฐานศัตรู)
						gc.fillText("HP: " + jailHp, 690, 170); // ตำแหน่ง X = 710, Y = 160
						
						
						

						// Draw and update our team's units
						Knight k = null;
						Archer a = null;
						Lancer l = null;
						Priest p = null;
						Wizard w = null;
						Slime s = null;
						Orc o = null;
						Werebear wb = null;
						final int jailpos = 650;
						final int castlepos = 0;
						
						if(GameLogic.getInstance().getEnemyTeamUnits().get(0).getStatus()==Action.DEATH) {
							System.out.println("Winn!!! Jail daed");
							GameLogic.setEnd(true) ;
							SceneManager.setScene("win");
						}
						else if(GameLogic.getInstance().getOurTeamUnits().get(0).getStatus()==Action.DEATH) {
							System.out.println("we lost NOoo");
							GameLogic.setEnd(true) ;
							SceneManager.setScene("lost");
						}
						

						for (Unit u : GameLogic.getInstance().getOurTeamUnits()) {
						    if (u instanceof Knight) {
						        k = (Knight) u;
						        if (k.getStatus() == Action.DEATH) {
						            toremove.add(u);
						        } else if (k.isEnemyInRange(GameLogic.getInstance().getUnitInFiled())||k.getPos()+k.getRange()>=jailpos) {
						            k.renderAttacking(gc);
						        } else {
						            k.walk();
						            k.renderWalk(gc);
						        }
						    } else if (u instanceof Archer) {
						        a = (Archer) u;
						        if (u.getStatus() == Action.DEATH) {
						            toremove.add(u);
						        } else if (a.isEnemyInRange(GameLogic.getInstance().getUnitInFiled())||u.getPos()+u.getRange()>=jailpos) {
						            a.renderAttacking(gc);
						        } else {
						            a.walk();
						            a.renderWalk(gc);
						        }
						    } else if (u instanceof Lancer) {
						        l = (Lancer) u;
						        if (u.getStatus() == Action.DEATH) {
						            toremove.add(u);
						        } else if (l.isEnemyInRange(GameLogic.getInstance().getUnitInFiled())||l.getPos()+l.getRange()>=jailpos) {
						            l.renderAttacking(gc);
						        } else {
						            l.walk();
						            l.renderWalk(gc);
						        }
						    } else if (u instanceof Priest) {
						        p = (Priest) u;
//						        System.out.println(GameLogic.getInstance().getUnitInFiled());
						        if (p.getStatus() == Action.DEATH) {
						            toremove.add(u);
						        }else if (p.isAllyInRange(GameLogic.getInstance().getUnitInFiled())) {
//						        	System.out.println("found ally");
						            p.renderBuff(gc);
						        }else {
						            p.walk();
						            p.renderWalk(gc);
						        }
						    } else if (u instanceof Wizard) {
						        w = (Wizard) u;
						        if (w.getStatus() == Action.DEATH) {
						            toremove.add(u);
						        } else if (w.isEnemyInRange(GameLogic.getInstance().getUnitInFiled())||u.getPos()+u.getRange()>=jailpos) {
						            w.renderAttacking(gc);
						        } else {
						            w.walk();
						            w.renderWalk(gc);
						        }
						    }
						}


						// Draw and update enemy team's units
						for (Unit e : GameLogic.getInstance().getEnemyTeamUnits()) {
//	                    	
							if (e instanceof Werebear) {
								wb = (Werebear) e;
								if(wb.getStatus()==Action.DEATH) {
									toremove.add(e);
								}
								else if (wb.isEnemyInRange(GameLogic.getInstance().getUnitInFiled())||e.getPos()-e.getRange()<=castlepos) {
									wb.renderAttacking(gc);
								} else {
									wb.walk();
									wb.render(gc);
								}
							} else if (e instanceof Orc) {
								o = (Orc) e;
								if(o.getStatus()==Action.DEATH) {
									toremove.add(e);
									
								}
								else if(o.isEnemyInRange(GameLogic.getInstance().getUnitInFiled())||e.getPos()-e.getRange()<=castlepos){
									o.renderAttacking(gc);
								}
								else {
									o.walk();
									o.render(gc);
								}
							} else if (e instanceof Slime) {
								s = (Slime) e;
								if(s.getStatus()==Action.DEATH) {
									toremove.add(e);
									System.out.println(toremove.toString());
									
								}
								else if(s.isEnemyInRange(GameLogic.getInstance().getUnitInFiled())||e.getPos()-e.getRange()<=castlepos) {
									s.renderAttacking(gc);
								}
								else {
									s.walk();
									s.render(gc);
								}
							}
						}
						
						for(Unit r: toremove) {
							GameLogic i = GameLogic.getInstance();
							i.getEnemyTeamUnits().remove(r);
							i.getUnitInFiled().remove(r);
//							System.out.println("removing uunit");
							
						}
						for(Unit r:toremoveHero) {
							GameLogic i = GameLogic.getInstance();
							i.getOurTeamUnits().remove(r);
							i.getUnitInFiled().remove(r);
						}

					});
					
					
					

					Thread.sleep(50); 
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		t.start();
	}
}
