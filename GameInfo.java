public class GameInfo {
    
    private int announcedNumber;
    public int n;	 
	private boolean gameCompleteFlag;
	private boolean noAnnouncedFlag;
	private boolean[] playerSuccessFlag;
    private boolean[] playerChanceFlag;
	private Object lock1;
    
    private static GameInfo  gameInfo= new GameInfo();

    private GameInfo() {
        announcedNumber = 0;
        n = 3;
        gameCompleteFlag = false;
        noAnnouncedFlag = false;
        playerChanceFlag = new boolean[n];
        playerSuccessFlag = new boolean[n];
        lock1 = new Object();
    
    }

    public static GameInfo getInstance() {
        return gameInfo;
    }

    
    public int getAnnouncedNumber() {
        return announcedNumber;
    }
    public void setAnnouncedNumber(int newNumber) {
        announcedNumber = newNumber;
    }

    public boolean getNoAnnouncedFlag() {
        return noAnnouncedFlag;
    }

    public void setNoAnnouncedFlag(boolean NewNoAnnouncedFlag) {
        noAnnouncedFlag = NewNoAnnouncedFlag;
    }

    public Object getLock1() {
        return lock1;
    }

    public void setLock1(Object lock1) {
        this.lock1 = lock1;
    }

    public boolean[] getplayerSuccessFlag() {
        return playerSuccessFlag;
    }

    public void setplayerSuccessFlag(boolean[] playerSuccessFlag) {
        this.playerSuccessFlag = playerSuccessFlag;
    }

    public boolean[] getplayerChanceFlag() {
        return playerChanceFlag;
    }


    public void setplayerChanceFlag(boolean[] playerChanceFlag) {
        this.playerChanceFlag = playerChanceFlag;
    }

    public boolean getGameCompleteFlag() {
        return gameCompleteFlag;
    }
    public void setGameCompleteFlag(boolean gameCompleteFlag) {
        this.gameCompleteFlag = gameCompleteFlag;
    }

    // public ArrayList<Integer> getModeratorTickets() {
    //     return moderatorTickets;
    // }
}