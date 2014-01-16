package eu.kuenzli.jeopardy.input;

public interface IQuizController {	
	void addPlayerInputListener(int playerID, IPlayerInputListener listener);
	void removePlayerInputListener(int playerID, IPlayerInputListener listener);
	
	void addGenericInputListener(IGenericInputListener listener);
	void removeGenericInputListener(IGenericInputListener listener);
	
	void enablePlayerNotification(int playerID);
	void disablePlayerNotification(int playerID);
}
