import java.util.*;

public class Player implements Runnable {
    private int id; // Player
    private GameInfo gameInfo;
    private int totalNosFound;
    private static final int  NUM_TICKETS = 10;
    private static final int WIN_TICKETS = 3;
    private HashSet<Integer> numsDone;
    private int ticket[];

    public Player(GameInfo gameInfo, int id) {
        this.id = id;
        this.gameInfo = gameInfo;
        this.totalNosFound = 0;
        Ticket ticketBuild = new TicketBuild(NUM_TICKETS);
        ticket = ticketBuild.getTicket();      
        printPlayerTickets();    
        numsDone = new HashSet<>();
    }

    private void printPlayerTickets() {
        System.out.println("----- Player " + (this.id + 1) + " tickets ----- ");
        for (int i = 0; i < NUM_TICKETS; i++) {
            System.out.print(" " + ticket[i] + " ");
        }
        System.out.println();
    }

    public HashSet<Integer> getMarkedIndicies() {
        return this.numsDone;
    }

    public int[] getTickets() {
        return this.ticket;
    }
    public int getId() {
        return this.id;
    }

    @Override
    public void run() {

        synchronized (gameInfo.getLock1()) {
            boolean[] getPlayerChanceFlag = gameInfo.getplayerChanceFlag();
            boolean[] getPlayerSuccessFlag = gameInfo.getplayerSuccessFlag();
            while (!gameInfo.getGameCompleteFlag()) {

                while (!gameInfo.getNoAnnouncedFlag() || getPlayerChanceFlag[this.id] &&!gameInfo.getGameCompleteFlag()) {
                    try {
                        gameInfo.getLock1().wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (!gameInfo.getGameCompleteFlag()) {

                    for (int i = 0; i < NUM_TICKETS; i++) {
                        if (gameInfo.getAnnouncedNumber() == ticket[i] && !numsDone.contains(i)) {
                            this.totalNosFound++;
                            numsDone.add(i);
                            break;
                        }
                    }
                }

                if (this.totalNosFound == WIN_TICKETS) {
                    getPlayerSuccessFlag[this.id] = true;
                    gameInfo.setplayerSuccessFlag(getPlayerSuccessFlag);
                }
                getPlayerChanceFlag[this.id] = true;
                gameInfo.setplayerChanceFlag(getPlayerChanceFlag);
                gameInfo.getLock1().notifyAll();
            }
        }
    }

}
