import java.util.*;

public class Moderator implements Runnable {

    private GameInfo gameInfo;
    private static final int GEN_TICKETS = 10;
    private Player[] players;
    private int noAnnounced = 0;
    public Moderator(GameInfo gameInfo, Player[] players) {
        this.gameInfo = gameInfo;
        this.players = players;
    }

    private int totalNumsAnnounced = 0; 
    
    public static int generateTicketnum(int max, int min) {
        Random rand = new Random();
        int num = rand.nextInt((max - min) + 1) + min;
        return num;
    }

    public void setAnnouncedNum(int num) {
        this.noAnnounced = num;
    }

    private void finalTicketsMarked() {
        for(int i = 0;i<players.length;i++) {
            HashSet<Integer>getMarked = players[i].getMarkedIndicies();
            int[] tickets = players[i].getTickets();
            System.out.println("----Player " + (i+1)+ " marked tickets----");
            for(int idx : getMarked) {
                System.out.print(" " + tickets[idx] + " ");
            }
            System.out.println();
        }
    }

    @Override
    public void run() {
        synchronized (gameInfo.getLock1()) {
            boolean playerWon = false;
            while (this.totalNumsAnnounced < GEN_TICKETS) {
                boolean playerSuccessFlag[] = gameInfo.getplayerSuccessFlag();
                boolean playerChanceFlag[] = gameInfo.getplayerChanceFlag();
                int playerIndexWon = 0;
                for (int pl = 0; pl < playerSuccessFlag.length; pl++) {
                    if (playerSuccessFlag[pl]) {
                        playerWon = true;
                        playerIndexWon = pl;
                        break;
                    }
                }

                if (!playerWon) { // if no one has won yet
                    gameInfo.setNoAnnouncedFlag(false); // set number announced flag to false before announcing
                    // Check for player chances and reset all of them to false
                    for (int pl = 0; pl < playerChanceFlag.length; pl++) {
                        playerChanceFlag[pl] = false;
                    }
                    gameInfo.setplayerChanceFlag(playerChanceFlag);

                    int ticketNum = generateTicketnum(50, 1);
                    this.setAnnouncedNum(ticketNum);
                    System.out.println("Moderator announced Number:  " + ticketNum);
                    this.totalNumsAnnounced++;

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    gameInfo.setAnnouncedNumber(ticketNum); // send it to the GameInfo class
                    this.setAnnouncedNum(0); // reset the num
                    gameInfo.setNoAnnouncedFlag(true);
                    gameInfo.getLock1().notifyAll();

                    for (int pl = 0; pl < playerChanceFlag.length; pl++) {
                        while (!playerChanceFlag[pl]) { //Wait for all the players to mark their tickets
                            try {
                                gameInfo.getLock1().wait();
                            } catch (InterruptedException e) {

                                e.printStackTrace();
                            }
                        }
                    }
                }

                else {
                    System.out.println("Player " + (playerIndexWon+1) + " won! " );
                    System.out.println();
                    System.out.println("Total tickets generated " + (this.totalNumsAnnounced));
                    gameInfo.setGameCompleteFlag(true);
                    break;
                }
            }

            System.out.println();
            System.out.println("------FINAL STATE OF CROSSED OUT TICKETS------");
            this.finalTicketsMarked();

            if(!playerWon) {
                gameInfo.setGameCompleteFlag(true);
                System.out.println("No one won this round ");
            }
        }
    }
    
}