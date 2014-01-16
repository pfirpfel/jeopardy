package eu.kuenzli.jeopardy.input;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class AbstractQuizController implements IQuizController, Runnable {
	
	private Map<Integer, Set<IPlayerInputListener>> playerInputListeners = new HashMap<Integer, Set<IPlayerInputListener>>();
	private Set<IGenericInputListener> genericInputListeners = new HashSet<IGenericInputListener>();

	@Override
	public void addPlayerInputListener(int playerId, IPlayerInputListener listener) {
		synchronized(playerInputListeners){
			if(!playerInputListeners.containsKey(playerId)){
				playerInputListeners.put(playerId, new HashSet<IPlayerInputListener>());			
			}
			playerInputListeners.get(playerId).add(listener);
		}
	}

	@Override
	public void removePlayerInputListener(int playerId, IPlayerInputListener listener) {
		synchronized(playerInputListeners){
			if(playerInputListeners.containsKey(playerId)){
				Set<IPlayerInputListener> playerListeners = playerInputListeners.get(playerId);
				playerListeners.remove(listener);
				if(playerListeners.isEmpty()){
					playerInputListeners.remove(playerListeners);
				}
			}
		}
	}
	
	protected void notifyPlayerInputListeners(int playerId, Button input){
		HashSet<IPlayerInputListener> copy = null;
		synchronized(playerInputListeners){
			if(playerInputListeners.containsKey(playerId)){
				copy = new HashSet<IPlayerInputListener>(playerInputListeners.get(playerId));	
			}		
		}
		for(IPlayerInputListener listener : copy){ // problem if copy == null?
			listener.onPlayerInput(input);;			
		}
	}

	@Override
	public void addGenericInputListener(IGenericInputListener listener) {
		synchronized(genericInputListeners){
			genericInputListeners.add(listener);
		}
	}

	@Override
	public void removeGenericInputListener(IGenericInputListener listener) {
		synchronized(genericInputListeners){
			genericInputListeners.remove(listener);
		}
	}
	
	protected void notifyGenericInputListeners(int playerId, Button input){
		HashSet<IGenericInputListener> copy;
		synchronized(genericInputListeners){;
			copy = new HashSet<IGenericInputListener>(genericInputListeners);			
		}
		for(IGenericInputListener listener : copy){
			listener.onGenericInput(playerId, input);		
		}
	}
}
