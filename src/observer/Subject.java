package observer;

public interface Subject {

	void addObserver(Observer observer);
	void removeObserver(Observer observer);
	void notifyAllObservers();
	
	void addObserverRedoUndo(Observer observer);
	void removeObserverRedoUndo(Observer observer);
	void notifyAllObserversRedoUndo();
}
