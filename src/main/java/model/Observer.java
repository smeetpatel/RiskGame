package main.java.model;

/**
 * Updates the observing classes on getting notification from Observable.
 */
public interface Observer {
    /**
     * Function carrying out update work in response to some observed action.
     * @param o Observable object
     */
    public void update(Observable o);

    public void update(String message);

   // public void update(Observable o, GameData game, String message);
}
