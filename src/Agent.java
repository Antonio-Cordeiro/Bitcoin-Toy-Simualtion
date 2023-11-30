import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Agent extends Thread{

    public String publicId;
    private int privateId;
    private Random rand = new Random();
    private int balance = 0;

    public Agent() throws NoSuchAlgorithmException {
        //get a random private id
        int newPrivateId = rand.nextInt(World.numOfAgents+World.numOfMiners) + 1;
        while (World.usedPublicIds.contains(newPrivateId)) {
            newPrivateId = rand.nextInt(World.numOfAgents+World.numOfMiners) + 1;
        }
        World.usedPublicIds.add(newPrivateId);
        
        this.privateId = newPrivateId;
        this.publicId = World.HashGenerator(String.valueOf(privateId));
        this.balance = 50;
    }

    public void run() {
        
        while(World.TotalBitcoins < 21000000) {
            //wait if the balance is not enough to make transactions
            while(this.balance <= 0){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {}
            }
            
            int send = rand.nextInt(2); //randomly be the the sender or the receiver of the transaction
            try{
                //send == 1 means that the current agent is the sender
                if (send == 1) {
                    //randomly select a receiver
                    int rec_id = rand.nextInt(World.usedPublicIds.size());
                    while (this.publicId == World.AgentsList.get(rec_id).publicId){
                        rec_id = rand.nextInt(World.usedPublicIds.size());
                    }
                    Agent receiver = World.AgentsList.get(rec_id);
                    
                    int amount = rand.nextInt(balance)+1;
                    Transaction transaction = new Transaction(this, receiver, amount);
                    this.balance -= amount;
                    receiver.updateBalance(amount);
                    World.queue.insert(transaction);
                }

                else {
                    //send == 0 means that the current agent is the receiver
                    int send_id = rand.nextInt(World.usedPublicIds.size());
                    while (this.publicId == World.AgentsList.get(send_id).publicId){
                        send_id = rand.nextInt(World.usedPublicIds.size());
                    }
                    Agent sender = World.AgentsList.get(send_id);
                    
                    int amount = rand.nextInt(balance)+1;
                    
                    //get the transaction approved by the sender
                    if (sender.giveSignature(amount)){
                        Transaction transaction = new Transaction( World.AgentsList.get(send_id),this, amount);
                        this.balance += amount;
                        World.queue.insert(transaction);
                    }
                }
            }
            catch (IndexOutOfBoundsException e) {} catch (IllegalArgumentException f){}
        }
    }
    


    public String getPublicId() {
        return publicId;
    }

    public void updateBalance(int amount) {
        this.balance += amount;
    }

    public int getBalance() {
        return balance;
    }
    
    public Boolean giveSignature(int amount) {
        if (rand.nextInt(2) == 1 && this.balance >= amount){
            this.balance -= amount;
            return true;
        }
        return false;
    }

    public synchronized void printBalance(){
        System.out.println("My balance is " + this.balance);;
    }
}
