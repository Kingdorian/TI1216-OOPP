package application.controller;

import application.model.Goalkeeper;
import application.model.Match;
import application.model.Player;
import application.model.Players;
import application.model.Reason;
import application.model.Status;
import application.model.Team;

import java.util.ArrayList;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author faris
 */
public class GenerateMatch {

	/**
	 * generates match results (only scores) based of player strength
	 *
	 * @param homeTeam
	 *            Team: home
	 * @param visitorTeam
	 *            Team: visitor
	 * @return Match: result of the match
	 */
	public static Match generateMatch(Team homeTeam, Team visitorTeam) {
		ArrayList<Players> homePlayers = homeTeam.getPlayers();
		ArrayList<Players> visitingPlayers = visitorTeam.getPlayers();
		return generateMatch(homeTeam, visitorTeam, new Random().nextLong(),
				new Random().nextLong());

	}

	/**
	 * this method is for testing
	 *
	 * @param homeTeam
	 * @param visitorTeam
	 * @param seed1
	 * @param seed2
	 * @return
	 */
	public static Match generateMatch(Team homeTeam, Team visitorTeam,
			long seed1, long seed2) {
		int homePower = 0, visitorPower = 0;
		ArrayList<Players> playerList = homeTeam.getPlayers();

		for (int i = 0; i < playerList.size(); i++) {
			Players pl = playerList.get(i);
			// for(Players pl : playerList){
			if (!(pl instanceof Player)) {
				homePower += 2 * (((Goalkeeper) pl).getStopPower() + ((Goalkeeper) pl)
						.getPenaltyStopPower() / 2);
			} else {
				homePower += ((Player) pl).getAttack()
						+ ((Player) pl).getDefence()
						+ ((Player) pl).getStamina();
			}
		}
		playerList = visitorTeam.getPlayers();
		for (Players pl : playerList) {
			if (!(pl instanceof Player)) {
				visitorPower += 2 * (((Goalkeeper) pl).getStopPower() + ((Goalkeeper) pl)
						.getPenaltyStopPower() / 2);
			} else {
				visitorPower += ((Player) pl).getAttack()
						+ ((Player) pl).getDefence()
						+ ((Player) pl).getStamina();
			}
		}

		if ((homeTeam.hasArtificialGrass() && !visitorTeam.hasArtificialGrass())
				|| (!homeTeam.hasArtificialGrass() && visitorTeam
						.hasArtificialGrass())) {
			homePower *= 1.05;
		}

		homePower *= 4;
		visitorPower *= 4;

		double ratio = (double) homePower / visitorPower;

		int homeGoals = 0;
		int visitorGoals = 0;

		// ArrayList<Players> players = new ArrayList<Players>();
		// ArrayList<Status> status = new ArrayList<Status>();
		// ArrayList<Reason> reason = new ArrayList<Reason>();

		for (int i = 0; i <= 90; i++) {

			double randomInjury = Math.random();// new
												// Random(seed1).nextDouble();
			double randomRedCard = Math.random();// new
													// Random(seed2).nextDouble();
			double randomYellowCard = Math.random();// new
													// Random(seed2).nextDouble();
			int side = (int) (Math.random()/* new Random(seed2).nextDouble() */* 2);
			int randomPerson = (int) ((Math.random()/*
													 * new
													 * Random(seed1).nextDouble
													 * ()
													 */* 11));

			if (randomInjury > 0.95) {

				// Hometeam
				if (side == 0) {

					Reason injury = Reason.values()[((int) ((new Random(seed1)
							.nextDouble() * 4) + 1))];

					Players player = homeTeam.getPlayers().get(randomPerson);
					homeTeam.getPlayers().get(randomPerson).setReason(injury);

					if (!(player instanceof Player)) {
						homePower -= 2 * (((Goalkeeper) player).getStopPower() + ((Goalkeeper) player)
								.getPenaltyStopPower() / 2);
					}

					else {
						homePower -= ((Player) player).getAttack()
								+ ((Player) player).getDefence()
								+ ((Player) player).getStamina();
					}

				}

				// VisitingTeam
				else {

					Reason injury = Reason.values()[((int) ((new Random(seed1)
							.nextDouble() * 4) + 1))];

					Players player = homeTeam.getPlayers().get(randomPerson);
					homeTeam.getPlayers().get(randomPerson).setReason(injury);

					if (!(player instanceof Player)) {
						visitorPower -= 2 * (((Goalkeeper) player)
								.getStopPower() + ((Goalkeeper) player)
								.getPenaltyStopPower() / 2);
					} else {
						visitorPower -= ((Player) player).getAttack()
								+ ((Player) player).getDefence()
								+ ((Player) player).getStamina();
					}

				}

			}

			if (randomYellowCard > 0.90) {

				// Hometeam
				if (side == 0) {

					Players player = homeTeam.getPlayers().get(randomPerson);

					if (homeTeam.getPlayers().get(randomPerson).getStatus() == Status.YELLOW) {
						homeTeam.getPlayers().get(randomPerson)
								.setStatus(Status.RED);

						if (!(player instanceof Player)) {
							homePower -= 2 * (((Goalkeeper) player)
									.getStopPower() + ((Goalkeeper) player)
									.getPenaltyStopPower() / 2);
						}

						else {
							homePower -= ((Player) player).getAttack()
									+ ((Player) player).getDefence()
									+ ((Player) player).getStamina();
						}

					}

					else
						homeTeam.getPlayers().get(randomPerson)
								.setStatus(Status.YELLOW);

				}

				else {

					Players player = visitorTeam.getPlayers().get(randomPerson);

					if (visitorTeam.getPlayers().get(randomPerson).getStatus() == Status.YELLOW) {
						visitorTeam.getPlayers().get(randomPerson)
								.setStatus(Status.RED);

						if (!(player instanceof Player)) {
							visitorPower -= 2 * (((Goalkeeper) player)
									.getStopPower() + ((Goalkeeper) player)
									.getPenaltyStopPower() / 2);
						} else {
							visitorPower -= ((Player) player).getAttack()
									+ ((Player) player).getDefence()
									+ ((Player) player).getStamina();
						}
					}

					else
						visitorTeam.getPlayers().get(randomPerson)
								.setStatus(Status.YELLOW);
				}

			}

			if (randomRedCard > 0.95) {

				// Hometeam card
				if (side == 0) {

					Status status = Status.values()[2];

					Players player = homeTeam.getPlayers().get(randomPerson);
					homeTeam.getPlayers().get(randomPerson).setStatus(status);

					if (!(player instanceof Player)) {
						homePower -= 2 * (((Goalkeeper) player).getStopPower() + ((Goalkeeper) player)
								.getPenaltyStopPower() / 2);
					}

					else {
						homePower -= ((Player) player).getAttack()
								+ ((Player) player).getDefence()
								+ ((Player) player).getStamina();
					}

				}

				// VisitingTeam card
				else {

					Reason status = Reason.values()[2];

					Players player = visitorTeam.getPlayers().get(randomPerson);
					visitorTeam.getPlayers().get(randomPerson)
							.setReason(status);

					if (!(player instanceof Player)) {
						visitorPower -= 2 * (((Goalkeeper) player)
								.getStopPower() + ((Goalkeeper) player)
								.getPenaltyStopPower() / 2);
					} else {
						visitorPower -= ((Player) player).getAttack()
								+ ((Player) player).getDefence()
								+ ((Player) player).getStamina();
					}

				}

				homeGoals += (int) ((new Random(seed1).nextDouble() * ratio * 1.1));
				visitorGoals += (int) ((new Random(seed2).nextDouble() / ratio * 1.1));

			}

		}
		return new Match(homeTeam, visitorTeam, homeGoals, visitorGoals);
	}
}
