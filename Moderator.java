import java.util.*;

public class Moderator implements Runnable {

    private GameInfo gameInfo;
    private static final int GEN_TICKETS = 70;
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
                // boolean playerSuccessFlag[] = gameInfo.getPlayerSuccessFlag();
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
                        Thread.sleep(100);
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
                    this.finalTicketsMarked();
                    System.exit(0);
                    break;
                }
            }

            System.out.println();
            System.out.println("------FINAL STATE OF CROSSED OUT TICKETS------");
            this.finalTicketsMarked();

            if(!playerWon) {
                System.out.println("No one won this round ");
                System.exit(0);
            }
        }
    }
    
}




// synchronized (gameInfo.getLock1()) { // Synchronizing gameInfo object
//     while(this.totalNumsAnnounced<GEN_TICKETS) {
//         boolean playerChanceFlag[] = gameInfo.getplayerChanceFlag();
        
//         gameInfo.getLock1().notifyAll();
//         boolean playerWon = false;
//         for (int pl = 0; pl < playerSuccessFlag.length; pl++) {
//             if (playerSuccessFlag[pl]) { // Any player who has won then break
//                 whoWon = pl;
//                 playerWon = true;
//                 break;
//             }
//         }
//         if (!playerWon) {
//             gameInfo.setNoAnnouncedFlag(false);
//             // Checking number flag
//             for (int pl = 0; pl < playerChanceFlag.length; pl++) {
//                 playerChanceFlag[pl] = false;
//             }
//             gameInfo.setplayerChanceFlag((playerChanceFlag));
//             int num = generateTicketnum(50, 0);
//             this.setAnnouncedNum(num);
//             System.out.println("Moderator announced the number " + num);
//             this.totalNumsAnnounced++;

//             try {
//                 Thread.sleep(1000); //Wait for the next task
//             }
//             catch (Exception e) {
//                 System.out.println("Except " + e);
//             }


//             gameInfo.setAnnouncedNumber(this.noAnnounced); //Set the num of gamedata to the generated one
//             this.setAnnouncedNum(0); //Reset the num
//             gameInfo.setNoAnnouncedFlag(true);
//             gameInfo.getLock1().notifyAll();

//             for(int pl = 0;pl<playerChanceFlag.length;pl++) {
//                 while(!playerChanceFlag[pl]) {
//                     try {
//                         gameInfo.getLock1().wait();
//                     }
//                     catch (Exception e) {
//                         System.err.println(e);
//                     }
//                 }
//             }

//             }
//             else {
//                 System.out.println("Player: " + (whoWon+1) + "has won!" );
//                 gameInfo.setGameCompleteFlag(true);
//                 break; //Stop generating all the numbers if the player has already won?
//             }
//     }

    
   
// //playerChanceflag
    
// }
