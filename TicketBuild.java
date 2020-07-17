import java.util.Random;

public class TicketBuild implements Ticket {


    private int ticketNums;
    private int ticket[];
    public TicketBuild(int ticketNums) {
            this.ticketNums = ticketNums;
            ticket = new int[ticketNums];
            this.generateTicket();
    }

    @Override
    public int[] getTicket() {
        return ticket;
    }

    public static int generateTicketnum(int max, int min) {
        Random rand = new Random();
        int num = rand.nextInt((max - min) + 1) + min;
        return num;
    }

    @Override
    public void generateTicket() {
        for (int i = 0; i < this.ticketNums; i++) {
            int num = generateTicketnum(50, 1);
            ticket[i] = num;
        }
    }

  
}


class Test {

    public static void main(String[] args) {
        TicketBuild temp = new TicketBuild(3);
        int[] ticket = temp.getTicket();
        for(int i = 0;i<ticket.length;i++) {
            System.out.println(ticket[i]);
        }
    }
}