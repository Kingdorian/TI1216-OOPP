/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.model;

import java.util.ArrayList;
import java.util.Random;

import application.Main;

/**
 *
 * @author faris
 */
public class Market {

    private ArrayList<Players> playersForSale = new ArrayList<>();
    private ArrayList<Integer> price = new ArrayList<>();

    public ArrayList<Players> getPlayersForSale() {
        return playersForSale;
    }

    public void addPlayer(Players player, int price) {
        if (!playersForSale.contains(player)) {
            playersForSale.add(player);
            this.price.add(price);
        }
    }

    public void removePlayer(Players player) {
        for (int i = 0; i < playersForSale.size(); i++) {
            if (playersForSale.get(i).equals(player)) {
                playersForSale.remove(i);
                price.remove(i);
            }
        }
    }

    public int getPlayersPrice(Players player) {
        for (int i = 0; i < playersForSale.size(); i++) {
            if (playersForSale.get(i).equals(player)) {
                return price.get(i);
            }
        }
        return 0;
    }
    
    public void computerBuyPlayer(Competition competition){
    	computerBuyPlayer(competition, new Random().nextLong());
    }
    
    public void computerSellPlayer(Competition competition){
    	computerSellPlayer(competition, new Random().nextLong());
    }
    
    public void computerBuyPlayer(Competition competition, long seed){
    	ArrayList<Team> teams = new ArrayList<>();
        for (Team t : competition.getTeams()) {
            teams.add(t);
        }
        Random random = new Random(seed);
        for(int i = 0; i < teams.size(); i++){
        	if(teams.get(i) != competition.getTeamByName(Main.getChosenTeamName())){
        		double buyPlayer = random.nextDouble();
        		if(teams.get(i).getPlayers().size() >= 30){
        		}
        		else if(teams.get(i).getPlayers().size() >= 27){
        			if(buyPlayer > 0.95){
        				buyWhichPlayer(competition, teams.get(i), random);
        			}
        		}
        		else if(teams.get(i).getPlayers().size() >= 23){
        			if(buyPlayer > 0.80){
        				buyWhichPlayer(competition, teams.get(i), random);
        			}
        		}
        		else if(teams.get(i).getPlayers().size() >= 18){
        			if(buyPlayer > 0.50){
        				buyWhichPlayer(competition, teams.get(i), random);
        			}
        		}
        		else if(teams.get(i).getPlayers().size() >= 14){
        			buyWhichPlayer(competition, teams.get(i), random);
        		}
        	}
        }
    }
    
    public void computerSellPlayer(Competition competition, long seed){
    	ArrayList<Team> teams = new ArrayList<>();
        for (Team t : competition.getTeams()) {
            teams.add(t);
        }
        Random random = new Random(seed);
        for(int i = 0; i < teams.size(); i++){
        	if(teams.get(i) != competition.getTeamByName(Main.getChosenTeamName())){
        		double sellPlayer = random.nextDouble();
        		if(teams.get(i).getPlayers().size() == 14){
        		}
        		else if(teams.get(i).getPlayers().size() <= 18){
        			if(sellPlayer > 0.85){
        				sellWhichPlayer(competition, teams.get(i), random);
        			}
        		}
        		else if(teams.get(i).getPlayers().size() <= 23){
        			if(sellPlayer > 0.75){
        				sellWhichPlayer(competition, teams.get(i), random);
        			}
        		}
        		else if(teams.get(i).getPlayers().size() <= 27){
        			if(sellPlayer > 0.50){
        				sellWhichPlayer(competition, teams.get(i), random);
        			}
        		}
        		else if(teams.get(i).getPlayers().size() <= 30){
        			sellWhichPlayer(competition, teams.get(i), random);
        		}
        	}
        }
    }
    
    private void buyWhichPlayer(Competition competition, Team team, Random random){
    	double buyGK = random.nextDouble();
    	if(team.getAmountGoalkeepers() == 3){
    		if(buyGK > 0.95){
    			buyGoalkeeper(competition, team, random);
    		}
    	}
    	else if(team.getAmountGoalkeepers() >= 1){
    		buyGoalkeeper(competition, team, random);
    	}
    	double buyDef = random.nextDouble();
    	if(team.getAmountDefenders() == 12){
    		if(buyDef > 0.99){
    			buyPlayers(competition, team, "Defender", random);
    		}
    	}
    	else if(team.getAmountDefenders() >= 10){
    		if(buyDef > 0.90){
    			buyPlayers(competition, team, "Defender", random);
    		}
    	}
    	else if(team.getAmountDefenders() >= 8){
    		if(buyDef > 0.70){
    			buyPlayers(competition, team, "Defender", random);
    		}
    	}
    	else if(team.getAmountDefenders() >= 6){
    		if(buyDef > 0.35){
    			buyPlayers(competition, team, "Defender", random);
    		}
    	}
    	else if(team.getAmountDefenders() >= 4){
    		buyPlayers(competition, team, "Defender", random);
    	}
    	double buyMid = random.nextDouble();
    	if(team.getAmountMidfielders() == 12){
    		if(buyMid > 0.98){
    			buyPlayers(competition, team, "Midfielder", random);
    		}
    	}
    	else if(team.getAmountMidfielders() >= 10){
    		if(buyMid > 0.90){
    			buyPlayers(competition, team, "Midfielder", random);
    		}
    	}
    	else if(team.getAmountMidfielders() >= 8){
    		if(buyMid > 0.75){
    			buyPlayers(competition, team, "Midfielder", random);
    		}
    	}
    	else if(team.getAmountMidfielders() >= 6){
    		if(buyMid > 0.40){
    			buyPlayers(competition, team, "Midfielder", random);
    		}
    	}
    	else if(team.getAmountMidfielders() >= 3){
    		buyPlayers(competition, team, "Midfielder", random);
    	}
    	double buyFor = random.nextDouble();
    	if(team.getAmountForwards() == 9){
    		if(buyFor > 0.99){
    			buyPlayers(competition, team, "Forward", random);
    		}
    	}
    	else if(team.getAmountForwards() >= 7){
    		if(buyFor > 0.90){
    			buyPlayers(competition, team, "Forward", random);
    		}
    	}
    	else if(team.getAmountForwards() >= 4){
    		if(buyFor > 0.65){
    			buyPlayers(competition, team, "Forward", random);
    		}
    	}
    	else if(team.getAmountForwards() >= 2){
    		buyPlayers(competition, team, "Forward", random);
    	}
    }
    
    private void buyPlayers(Competition competition, Team team, String kind, Random random){
    	boolean transfered = false;
    	for(int i = 0; i < playersForSale.size() && transfered == false; i++){
    		if(team.getBudget() >= getPlayersPrice(playersForSale.get(i)) && (playersForSale.get(i).getKind().equals(kind)) || (playersForSale.get(i).getKind().equals("Allrounder"))){
    			double buyPlayer = random.nextDouble();
    			if(competition.getRank(team) <= 4){
    				if(playersForSale.get(i).getAbility() >= 4.00){
    					competition.getPlayersTeam(playersForSale.get(i)).transferTo(playersForSale.get(i), team, getPlayersPrice(playersForSale.get(i)));
    					transfered = true;
    					removePlayer(playersForSale.get(i));
    				}
    				else if(playersForSale.get(i).getAbility() >= 3.50){
    					if(buyPlayer > 0.35){
    						competition.getPlayersTeam(playersForSale.get(i)).transferTo(playersForSale.get(i), team, getPlayersPrice(playersForSale.get(i)));
    						transfered = true;
        					removePlayer(playersForSale.get(i));
    					}
    				}
    				else if(playersForSale.get(i).getAbility() >= 3.00){
    					if(buyPlayer > 0.65){
    						competition.getPlayersTeam(playersForSale.get(i)).transferTo(playersForSale.get(i), team, getPlayersPrice(playersForSale.get(i)));
    						transfered = true;
        					removePlayer(playersForSale.get(i));
    					}
    				}
    				else if(playersForSale.get(i).getAbility() >= 2.50){
    					if(buyPlayer > 0.90){
    						competition.getPlayersTeam(playersForSale.get(i)).transferTo(playersForSale.get(i), team, getPlayersPrice(playersForSale.get(i)));
    						transfered = true;
        					removePlayer(playersForSale.get(i));
    					}
    				}
    				else if(playersForSale.get(i).getAbility() >= 2.00){
    					if(buyPlayer > 0.98){
    						competition.getPlayersTeam(playersForSale.get(i)).transferTo(playersForSale.get(i), team, getPlayersPrice(playersForSale.get(i)));
    						transfered = true;
        					removePlayer(playersForSale.get(i));
    					}
    				}
    			}
    			else if(competition.getRank(team) <= 8){
    				if(playersForSale.get(i).getAbility() >= 4.00){
    					competition.getPlayersTeam(playersForSale.get(i)).transferTo(playersForSale.get(i), team, getPlayersPrice(playersForSale.get(i)));
    					transfered = true;
    					removePlayer(playersForSale.get(i));
    				}
    				else if(playersForSale.get(i).getAbility() >= 3.50){
    					if(buyPlayer > 0.30){
    						competition.getPlayersTeam(playersForSale.get(i)).transferTo(playersForSale.get(i), team, getPlayersPrice(playersForSale.get(i)));
    						transfered = true;
        					removePlayer(playersForSale.get(i));
    					}	
    				}
    				else if(playersForSale.get(i).getAbility() >= 3.00){
    					if(buyPlayer > 0.60){
    						competition.getPlayersTeam(playersForSale.get(i)).transferTo(playersForSale.get(i), team, getPlayersPrice(playersForSale.get(i)));
    						transfered = true;
        					removePlayer(playersForSale.get(i));
    					}    					
    				}
    				else if(playersForSale.get(i).getAbility() >= 2.50){
    					if(buyPlayer > 0.85){
    						competition.getPlayersTeam(playersForSale.get(i)).transferTo(playersForSale.get(i), team, getPlayersPrice(playersForSale.get(i)));
    						transfered = true;
        					removePlayer(playersForSale.get(i));
    					}
    				}
    				else if(playersForSale.get(i).getAbility() >= 2.00){
    					if(buyPlayer > 0.95){
    						competition.getPlayersTeam(playersForSale.get(i)).transferTo(playersForSale.get(i), team, getPlayersPrice(playersForSale.get(i)));
    						transfered = true;
        					removePlayer(playersForSale.get(i));
    					}
    				}
    			}
    			else if(competition.getRank(team) <= 13){
    				if(playersForSale.get(i).getAbility() >= 4.00){
    						competition.getPlayersTeam(playersForSale.get(i)).transferTo(playersForSale.get(i), team, getPlayersPrice(playersForSale.get(i)));
    						transfered = true;
        					removePlayer(playersForSale.get(i));
    				}
    				else if(playersForSale.get(i).getAbility() >= 3.50){
    					if(buyPlayer > 0.20){
    						competition.getPlayersTeam(playersForSale.get(i)).transferTo(playersForSale.get(i), team, getPlayersPrice(playersForSale.get(i)));
    						transfered = true;
        					removePlayer(playersForSale.get(i));
    					}
    				}
    				else if(playersForSale.get(i).getAbility() >= 3.00){
    					if(buyPlayer > 0.50){
    						competition.getPlayersTeam(playersForSale.get(i)).transferTo(playersForSale.get(i), team, getPlayersPrice(playersForSale.get(i)));
    						transfered = true;
        					removePlayer(playersForSale.get(i));
    					}
    				}
    				else if(playersForSale.get(i).getAbility() >= 2.50){
    					if(buyPlayer > 0.75){
    						competition.getPlayersTeam(playersForSale.get(i)).transferTo(playersForSale.get(i), team, getPlayersPrice(playersForSale.get(i)));
    						transfered = true;
        					removePlayer(playersForSale.get(i));
    					}
    				}
    				else if(playersForSale.get(i).getAbility() >= 2.00){
    					if(buyPlayer > 0.90){
    						competition.getPlayersTeam(playersForSale.get(i)).transferTo(playersForSale.get(i), team, getPlayersPrice(playersForSale.get(i)));
    						transfered = true;
        					removePlayer(playersForSale.get(i));
    					}
    				}
    			}
    			else if(competition.getRank(team) <= 18){
    				if(playersForSale.get(i).getAbility() >= 4.00){
    						competition.getPlayersTeam(playersForSale.get(i)).transferTo(playersForSale.get(i), team, getPlayersPrice(playersForSale.get(i)));
    						transfered = true;
        					removePlayer(playersForSale.get(i));
    				}
    				else if(playersForSale.get(i).getAbility() >= 3.50){
    					if(buyPlayer > 0.05){
    						competition.getPlayersTeam(playersForSale.get(i)).transferTo(playersForSale.get(i), team, getPlayersPrice(playersForSale.get(i)));
    						transfered = true;
        					removePlayer(playersForSale.get(i));
    					}
    				}
    				else if(playersForSale.get(i).getAbility() >= 3.00){
    					if(buyPlayer > 0.35){
    						competition.getPlayersTeam(playersForSale.get(i)).transferTo(playersForSale.get(i), team, getPlayersPrice(playersForSale.get(i)));
    						transfered = true;
        					removePlayer(playersForSale.get(i));
    					}
    				}
    				else if(playersForSale.get(i).getAbility() >= 2.50){
    					if(buyPlayer > 0.55){
    						competition.getPlayersTeam(playersForSale.get(i)).transferTo(playersForSale.get(i), team, getPlayersPrice(playersForSale.get(i)));
    						transfered = true;
        					removePlayer(playersForSale.get(i));
    					}
    				}
    				else if(playersForSale.get(i).getAbility() >= 2.00){
    					if(buyPlayer > 0.80){
    						competition.getPlayersTeam(playersForSale.get(i)).transferTo(playersForSale.get(i), team, getPlayersPrice(playersForSale.get(i)));
    						transfered = true;
        					removePlayer(playersForSale.get(i));
    					}
    				}
    			}
    		}
    	}
    }
    
    private void buyGoalkeeper(Competition competition, Team team, Random random){
    	boolean transfered = false;
    	for(int i = 0; i < playersForSale.size() && transfered == false; i++){
    		if(team.getBudget() >= getPlayersPrice(playersForSale.get(i)) && (playersForSale.get(i).getKind().equals("Goalkeeper"))){
    			double buyPlayer = random.nextDouble();
    			if(competition.getRank(team) <= 4){
    				if(playersForSale.get(i).getAbility() >= 4.00){
    					competition.getPlayersTeam(playersForSale.get(i)).transferTo(playersForSale.get(i), team, getPlayersPrice(playersForSale.get(i)));
    					transfered = true;
    					removePlayer(playersForSale.get(i));
    				}
    				else if(playersForSale.get(i).getAbility() >= 3.50){
    					if(buyPlayer > 0.40){
    						competition.getPlayersTeam(playersForSale.get(i)).transferTo(playersForSale.get(i), team, getPlayersPrice(playersForSale.get(i)));
    						transfered = true;
        					removePlayer(playersForSale.get(i));
    					}
    				}
    				else if(playersForSale.get(i).getAbility() >= 3.00){
    					if(buyPlayer > 0.75){
    						competition.getPlayersTeam(playersForSale.get(i)).transferTo(playersForSale.get(i), team, getPlayersPrice(playersForSale.get(i)));
    						transfered = true;
        					removePlayer(playersForSale.get(i));
    					}
    				}
    				else if(playersForSale.get(i).getAbility() >= 2.50){
    					if(buyPlayer > 0.90){
    						competition.getPlayersTeam(playersForSale.get(i)).transferTo(playersForSale.get(i), team, getPlayersPrice(playersForSale.get(i)));
    						transfered = true;
        					removePlayer(playersForSale.get(i));
    					}
    				}
    				else if(playersForSale.get(i).getAbility() >= 2.00){
    					if(buyPlayer > 0.999){
    						competition.getPlayersTeam(playersForSale.get(i)).transferTo(playersForSale.get(i), team, getPlayersPrice(playersForSale.get(i)));
    						transfered = true;
        					removePlayer(playersForSale.get(i));
    					}
    				}
    			}
    			else if(competition.getRank(team) <= 8){
    				if(playersForSale.get(i).getAbility() >= 4.00){
    						competition.getPlayersTeam(playersForSale.get(i)).transferTo(playersForSale.get(i), team, getPlayersPrice(playersForSale.get(i)));
    						transfered = true;
        					removePlayer(playersForSale.get(i));
    				}
    				else if(playersForSale.get(i).getAbility() >= 3.50){
    					if(buyPlayer > 0.35){
    						competition.getPlayersTeam(playersForSale.get(i)).transferTo(playersForSale.get(i), team, getPlayersPrice(playersForSale.get(i)));
    						transfered = true;
        					removePlayer(playersForSale.get(i));
    					}
    				}
    				else if(playersForSale.get(i).getAbility() >= 3.00){
    					if(buyPlayer > 0.65){
    						competition.getPlayersTeam(playersForSale.get(i)).transferTo(playersForSale.get(i), team, getPlayersPrice(playersForSale.get(i)));
    						transfered = true;
        					removePlayer(playersForSale.get(i));
    					}
    				}
    				else if(playersForSale.get(i).getAbility() >= 2.50){
    					if(buyPlayer > 0.80){
    						competition.getPlayersTeam(playersForSale.get(i)).transferTo(playersForSale.get(i), team, getPlayersPrice(playersForSale.get(i)));
    						transfered = true;
        					removePlayer(playersForSale.get(i));
    					}
    				}
    				else if(playersForSale.get(i).getAbility() >= 2.00){
    					if(buyPlayer > 0.90){
    						competition.getPlayersTeam(playersForSale.get(i)).transferTo(playersForSale.get(i), team, getPlayersPrice(playersForSale.get(i)));
    						transfered = true;
        					removePlayer(playersForSale.get(i));
    					}
    				}
    			}
    			else if(competition.getRank(team) <= 13){
    				if(playersForSale.get(i).getAbility() >= 4.00){
    						competition.getPlayersTeam(playersForSale.get(i)).transferTo(playersForSale.get(i), team, getPlayersPrice(playersForSale.get(i)));
    						transfered = true;
        					removePlayer(playersForSale.get(i));
    				}
    				else if(playersForSale.get(i).getAbility() >= 3.50){
    					if(buyPlayer > 0.25){
    						competition.getPlayersTeam(playersForSale.get(i)).transferTo(playersForSale.get(i), team, getPlayersPrice(playersForSale.get(i)));
    						transfered = true;
        					removePlayer(playersForSale.get(i));
    					}
    				}
    				else if(playersForSale.get(i).getAbility() >= 3.00){
    					if(buyPlayer > 0.55){
    						competition.getPlayersTeam(playersForSale.get(i)).transferTo(playersForSale.get(i), team, getPlayersPrice(playersForSale.get(i)));
    						transfered = true;
        					removePlayer(playersForSale.get(i));
    					}
    				}
    				else if(playersForSale.get(i).getAbility() >= 2.50){
    					if(buyPlayer > 0.70){
    						competition.getPlayersTeam(playersForSale.get(i)).transferTo(playersForSale.get(i), team, getPlayersPrice(playersForSale.get(i)));
    						transfered = true;
        					removePlayer(playersForSale.get(i));
    					}
    				}
    				else if(playersForSale.get(i).getAbility() >= 2.00){
    					if(buyPlayer > 0.90){
    						competition.getPlayersTeam(playersForSale.get(i)).transferTo(playersForSale.get(i), team, getPlayersPrice(playersForSale.get(i)));
    						transfered = true;
        					removePlayer(playersForSale.get(i));
    					}
    				}
    			}
    			else if(competition.getRank(team) <= 18){
    				if(playersForSale.get(i).getAbility() >= 4.00){
    						competition.getPlayersTeam(playersForSale.get(i)).transferTo(playersForSale.get(i), team, getPlayersPrice(playersForSale.get(i)));
    						transfered = true;
        					removePlayer(playersForSale.get(i));
    				}
    				else if(playersForSale.get(i).getAbility() >= 3.50){
    					if(buyPlayer > 0.05){
    						competition.getPlayersTeam(playersForSale.get(i)).transferTo(playersForSale.get(i), team, getPlayersPrice(playersForSale.get(i)));
    						transfered = true;
        					removePlayer(playersForSale.get(i));
    					}
    				}
    				else if(playersForSale.get(i).getAbility() >= 3.00){
    					if(buyPlayer > 0.35){
    						competition.getPlayersTeam(playersForSale.get(i)).transferTo(playersForSale.get(i), team, getPlayersPrice(playersForSale.get(i)));
    						transfered = true;
        					removePlayer(playersForSale.get(i));
    					}
    				}
    				else if(playersForSale.get(i).getAbility() >= 2.50){
    					if(buyPlayer > 0.60){
    						competition.getPlayersTeam(playersForSale.get(i)).transferTo(playersForSale.get(i), team, getPlayersPrice(playersForSale.get(i)));
    						transfered = true;
        					removePlayer(playersForSale.get(i));
    					}
    				}
    				else if(playersForSale.get(i).getAbility() >= 2.00){
    					if(buyPlayer > 0.75){
    						competition.getPlayersTeam(playersForSale.get(i)).transferTo(playersForSale.get(i), team, getPlayersPrice(playersForSale.get(i)));
    						transfered = true;
        					removePlayer(playersForSale.get(i));
    					}
    				}
    			}
    		}
    	}
    }
    
    private void sellWhichPlayer(Competition competition, Team team, Random random){
    	double sellGK = random.nextDouble();
    	if(team.getAmountGoalkeepers() <= 2){
    	}
    	else if(team.getAmountGoalkeepers() >= 3){
    		if(sellGK > 0.65){
    		sellGoalkeeper(competition, team, random);
    		}
    	}
    	double sellDef = random.nextDouble();
    	if(team.getAmountDefenders() == 4){
    		if(sellDef > 0.99){
    			sellPlayers(competition, team, "Defender", random);
    		}
    	}
    	else if(team.getAmountDefenders() <= 6){
    		if(sellDef > 0.90){
    			sellPlayers(competition, team, "Defender", random);
    		}
    	}
    	else if(team.getAmountDefenders() <= 8){
    		if(sellDef > 0.75){
    			sellPlayers(competition, team, "Defender", random);
    		}
    	}
    	else if(team.getAmountDefenders() <= 10){
    		if(sellDef > 0.50){
    			sellPlayers(competition, team, "Defender", random);
    		}
    	}
    	else if(team.getAmountDefenders() >= 11){
    		sellPlayers(competition, team, "Defender", random);
    	}
    	double sellMid = random.nextDouble();
    	if(team.getAmountMidfielders() == 3){
    		if(sellMid > 0.99){
    			sellPlayers(competition, team, "Midfielder", random);
    		}
    	}
    	else if(team.getAmountMidfielders() <= 6){
    		if(sellMid > 0.90){
    			sellPlayers(competition, team, "Midfielder", random);
    		}
    	}
    	else if(team.getAmountMidfielders() <= 8){
    		if(sellMid > 0.75){
    			sellPlayers(competition, team, "Midfielder", random);
    		}
    	}
    	else if(team.getAmountMidfielders() <= 10){
    		if(sellMid > 0.50){
    			sellPlayers(competition, team, "Midfielder", random);
    		}
    	}
    	else if(team.getAmountMidfielders() >= 11){
    		sellPlayers(competition, team, "Midfielder", random);
    	}
    	double sellFor = random.nextDouble();
    	if(team.getAmountForwards() == 2){
    		if(sellFor > 0.99){
    			sellPlayers(competition, team, "Forward", random);
    		}
    	}
    	else if(team.getAmountForwards() <= 4){
    		if(sellFor > 0.90){
    			sellPlayers(competition, team, "Forward", random);
    		}
    	}
    	else if(team.getAmountForwards() <= 7){
    		if(sellFor > 0.50){
    			sellPlayers(competition, team, "Forward", random);
    		}
    	}
    	else if(team.getAmountForwards() >= 8){
    		sellPlayers(competition, team, "Forward", random);
    	}
    }
    
    private void sellPlayers(Competition competition, Team team, String kind, Random random){
    	boolean transfered = false;
    	for(int i = 0; i < playersForSale.size() && transfered == false; i++){
    		if(playersForSale.get(i).getKind().equals(kind) || playersForSale.get(i).getKind().equals("Allrounder")){
    			double sellPlayer = random.nextDouble();
    			if(playersForSale.get(i).getAbility() >= 4.00){
    				if(sellPlayer > 0.97){
    					addPlayer(playersForSale.get(i), playersForSale.get(i).getPrice());
    				}
    			}
    			else if(playersForSale.get(i).getAbility() >= 3.50){
    				if(sellPlayer > 0.90){
    					addPlayer(playersForSale.get(i), playersForSale.get(i).getPrice());
    				}
    			}
    			else if(playersForSale.get(i).getAbility() >= 3.00){
    				if(sellPlayer > 0.75){
    					addPlayer(playersForSale.get(i), playersForSale.get(i).getPrice());
    				}
    			}
    			else if(playersForSale.get(i).getAbility() >= 2.50){
    				if(sellPlayer > 0.55){
    					addPlayer(playersForSale.get(i), playersForSale.get(i).getPrice());
    				}
    			}
    			else if(playersForSale.get(i).getAbility() >= 2.00){
    				if(sellPlayer > 0.30){
    					addPlayer(playersForSale.get(i), playersForSale.get(i).getPrice());
    				}
    			}
    		}
    	}
    }
    
    private void sellGoalkeeper(Competition competition, Team team, Random random){
    	boolean transfered = false;
    	for(int i = 0; i < playersForSale.size() && transfered == false; i++){
    		if(playersForSale.get(i).getKind().equals("Goalkeeper")){
    			double sellGoalkeeper = random.nextDouble();
    			if(playersForSale.get(i).getAbility() >= 4.00){
    				if(sellGoalkeeper > 0.97){
    					addPlayer(playersForSale.get(i), playersForSale.get(i).getPrice());
    				}
    			}
    			else if(playersForSale.get(i).getAbility() >= 3.50){
    				if(sellGoalkeeper > 0.85){
    					addPlayer(playersForSale.get(i), playersForSale.get(i).getPrice());
    				}
    			}
    			else if(playersForSale.get(i).getAbility() >= 3.00){
    				if(sellGoalkeeper > 0.70){
    					addPlayer(playersForSale.get(i), playersForSale.get(i).getPrice());
    				}
    			}
    			else if(playersForSale.get(i).getAbility() >= 2.50){
    				if(sellGoalkeeper > 0.50){
    					addPlayer(playersForSale.get(i), playersForSale.get(i).getPrice());
    				}
    			}
    			else if(playersForSale.get(i).getAbility() >= 2.00){
    				if(sellGoalkeeper > 0.25){
    					addPlayer(playersForSale.get(i), playersForSale.get(i).getPrice());
    				}
    			}
    		}
    	}
    }
}
