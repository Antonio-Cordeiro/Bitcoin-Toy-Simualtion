import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


class Miner extends Agent{
    public List<Transaction> TransList = new ArrayList<>();

    public Miner() throws NoSuchAlgorithmException {
        super();
    }

    @Override
    public void run(){
        Boolean flag = false;
        int i=0;
        while(!flag && World.TotalBitcoins < 21000000) {
            if (TransList.size()==0){
                //take 4 transactions
                while (i < 4){
                    try {
                    Transaction trans = World.queue.extract();
                    TransList.add(trans);
                    i++;
                    }
                    catch (InterruptedException e) {}
                }
            }
            
            try {
                ArrayList<Object> result = findHash();
                String hash = result.get(0).toString();
                int nonce = (int) result.get(1);
                
                String prevHash = World.getPrevHash();
                int blockId=World.getBlockId();

                Block block = new Block(prevHash, hash, nonce, blockId, TransList);
                    
                flag= World.blockchain.addBlock(block);
                if(flag){
                    //the block is added to the blockchain and the reward bitcoins are put in the network
                    World.addBitcoins();
                    this.updateBalance(World.reward);
                    makeTrans();
                    TransList.clear();
                    flag = false;
                }
            } catch (NoSuchAlgorithmException e) {} catch (InterruptedException e) {}
        }
    }


    public void makeTrans(){
        //randomly select a receiver
        int rec_id = new Random().nextInt(World.usedPublicIds.size());
        while (this.publicId == World.AgentsList.get(rec_id).publicId){
            rec_id = new Random().nextInt(World.usedPublicIds.size());
        }
        Agent receiver = World.AgentsList.get(rec_id);
        
        World.queue.insert(new Transaction(this, receiver, this.getBalance()));
        receiver.updateBalance(this.getBalance());
        this.updateBalance(-this.getBalance());
    }

    private ArrayList<Object> findHash() throws NoSuchAlgorithmException {  //return (hashed, nonce)
        //put everything in a string
        String to_hash = "";
        String hashed = "";
        for (Transaction t : TransList) {
            to_hash += t.getSenderId() + ";" + t.getReceiverId() + ";" + t.getAmount() + "\n"; // add number of transaction
        }

        //mine the block
        int nonce = 1;
        while (true) {
            hashed = World.HashGenerator(to_hash+Integer.toString(nonce));
            if (hashed.startsWith(World.complexity)) {
                ArrayList<Object> result = new ArrayList<>();
                result.add(hashed);
                result.add(nonce);
                return result;
            }
            nonce++;
        }
    }

    public void printTransaction(){
        //just to print a transaction     
        System.out.println("----------------------------");
        System.out.println("I am the miner: " + getName()+ " and I took the following transactions: ");
        for (Transaction t : TransList){
        System.out.println("sender: " + t.getSenderId() + " receiver: " + t.getReceiverId() + " amount: " + t.getAmount() +" id: "+ t.getId());
        System.out.println();
        }
        System.out.println("----------------------------");
    }

}
