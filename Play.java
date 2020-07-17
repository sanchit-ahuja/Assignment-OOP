public class Play {

    public static void main(String[] args) {

       

        GameInfo gameInfo = GameInfo.getInstance();

        int numPlayers = gameInfo.getPlayerNums();
        Player[] players = new Player[numPlayers];

        for(int i = 0;i<players.length;i++) {
            players[i] = new Player(gameInfo,i);
        }


        Moderator moderator = new Moderator(gameInfo,players);
        Thread moderatorThread = new Thread(moderator);
        Thread[] playerThreads = new Thread[numPlayers];

        for(int i = 0;i<numPlayers;i++) {
            playerThreads[i] = new Thread(players[i]);
        }

        moderatorThread.start();
        for(int i = 0;i<numPlayers;i++) {
            playerThreads[i].start();
        }

    }
}