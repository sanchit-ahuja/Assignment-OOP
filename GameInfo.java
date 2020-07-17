import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GameInfo {
    
    private int announcedNumber;
    private int numPlayers;	 
	private boolean gameCompleteFlag;
	private boolean noAnnouncedFlag;
	private boolean[] playerSuccessFlag;
    private boolean[] playerChanceFlag;
	private Object lock1;
    private int numTickets;
    private static GameInfo  gameInfo= null;

    private GameInfo() throws IOException{
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader bfr = new BufferedReader(isr);
        boolean valid = false;
        while(!valid) {
            try {
                System.out.println("Enter the number of Players ");
                numPlayers = Integer.parseInt(bfr.readLine());
                valid = true;
            }
            catch (NumberFormatException e) {
                System.out.println("Enter a valid number!");
            }
        }

        
        numTickets =  3;
        playerChanceFlag = new boolean[numPlayers];
        playerSuccessFlag = new boolean[numPlayers];
        announcedNumber = 0;
        gameCompleteFlag = false;
        noAnnouncedFlag = false;
        lock1 = new Object();
    }

    public static GameInfo getInstance() {
        synchronized(GameInfo.class) {
            if(gameInfo == null) {
                try {
                    gameInfo = new GameInfo();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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

    public void setPlayerNums(int n) {
        this.numPlayers = n;
    }
    public int getPlayerNums() {
        return this.numPlayers;
    }
    
    public int getTicketNums() {
        return this.numTickets;
    }
}