/***************************************************************************
 *                   (C) Copyright 2013 - Faiumoni e. V.                   *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.entity.status;

import games.stendhal.common.Rand;
import games.stendhal.server.entity.RPEntity;

import org.apache.log4j.Logger;

/**
 * a status attacker
 *
 * @author hendrik
 */
public class StatusAttacker {
	/** The logger instance */
	private static final Logger logger = Logger.getLogger(StatusAttacker.class);
	
	private final double probability;
	private final Status status;

	/**
	 * a Status attacker
	 *
	 * @param status status to attack with
	 * @param probability probability of an attack in this turn
	 */
	public StatusAttacker(Status status, double probability) {
		this.probability = probability;
		this.status = status;
	}

	/**
	 * gets the probability
	 *
	 * @return probability
	 */
	public double getProbability() {
		return probability;
	}

	/**
	 * gets the status
	 *
	 * @return status
	 */
	protected Status getStatus() {
		return status;
	}

	/**
	 * an attempt to attack the target, it may succeed or not
	 *
	 * @param target   target   defender
	 * @param attacker attacker attacker
	 */
	@SuppressWarnings("unused")
	public void onAttackAttempt(RPEntity target, RPEntity attacker) {
		// stub
	}

	/**
	 * the target was hit, this may or may not have caused damage
	 *
	 * @param target   target   defender
	 * @param attacker attacker attacker
	 * @param damage   amount of damage
	 */
	public void onHit(RPEntity target, RPEntity attacker,
			@SuppressWarnings("unused") int damage) {
		Status inflictedStatus = (Status) status.clone();
		StatusType statusType = inflictedStatus.getStatusType();
		String resistAttribute = "resist_"
				+ statusType.toString().toLowerCase();
		
		System.out.println("\n!!! RESIST ATTRIBUTE: " + resistAttribute + " !!!\n");
		
		// Create a temporary instance to adjust without affecting entity's
		// built-in probability.
		Double actualProbability = probability;
		
		if (target.has(resistAttribute)) {
			Double probabilityAdjust = 1.0 - target.getDouble(resistAttribute);
			
			if (logger.isDebugEnabled()) {
				logger.debug("Adjusting " + statusType.toString()
						+ " status infliction resistance: "
						+ Double.toString(probability) + " * "
						+ Double.toString(probabilityAdjust) + " = "
						+ Double.toString(probability * probabilityAdjust));
			}
			
			actualProbability = probability * probabilityAdjust;
		}
		
		// Roll dice between 1-100
		int roll = Rand.randUniform(1, 100);
		if (roll <= actualProbability) {
			target.getStatusList().inflictStatus(inflictedStatus, attacker);
		}
	}
	
	/**
	 * 
	 * @return
	 *     Name of the status that this attacker can inflict
	 */
    public String getStatusName() {
        return status.getName();
    }

}
