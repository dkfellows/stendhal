package games.stendhal.client.actions;

import games.stendhal.client.ClientSingletonRepository;
import games.stendhal.common.StringHelper;
import marauroa.common.game.RPAction;

/**
 * Causes the entity to begin walking in the direction facing.
 * 
 * @author
 * 		AntumDeluge
 */
public class WalkAction implements SlashAction {
	
	/**
	 * Execute a chat command.
	 * 
	 * @param params
	 * 		The formal parameters.
	 * @param remainder
	 * 		Line content after parameters.
	 * @return
	 * 		<code>true</code> if command was handled.
	 */
	@Override
	public boolean execute(String[] params, String remainder) {
		final RPAction walk = new RPAction();
		
		walk.put("type", "walk");
		walk.put("target", StringHelper.unquote(remainder));

		ClientSingletonRepository.getClientFramework().send(walk);
		
		return true;
	}
	
	/**
	 * Get the maximum number of formal parameters.
	 * 
	 * @return
	 * 		Parameter count.
	 */
	@Override
	public int getMaximumParameters() {
		return 0;
	}
	
	/**
	 * Get the minimum number of formal parameters.
	 * 
	 * @return
	 * 		Parameter count.
	 */
	@Override
	public int getMinimumParameters() {
		return 0;
	}

}
