import java.security.NoSuchAlgorithmException;
import java.util.List;

public class Block {
    public String prevHash;
    public String Hash;
    public int nonce;
    public int blockId;
    public List<Transaction> TransList;

    public Block(String prevHash, String Hash, int nonce, int blockId, List<Transaction> TransList) {
        this.prevHash = prevHash;
        this.blockId = blockId;
        this.Hash = Hash;
        this.nonce = nonce;
        this.TransList = TransList;
    }
    
    public void setPrevHash(String prevHash) {
        this.prevHash = prevHash;
    }

    public String getPrevHash() {
        return prevHash;
    }

    public void setHash(String Hash) {
        this.Hash = Hash;
    }

    public String getHash() {
        return Hash;
    }

    public String calculateBlockHash() throws NoSuchAlgorithmException {
        //put everything in a string and then hash it
        String to_hash = "";
        String hashed = "";
        for (Transaction t : TransList) {
            to_hash += t.getSenderId() + ";" + t.getReceiverId() + ";" + t.getAmount() + "\n"; // add number of transaction
        }
            to_hash += Integer.toString(this.nonce);
            hashed = World.HashGenerator(to_hash);
            return hashed;
    }

    //just to print a block
    public synchronized void printBlock() {
        System.out.println("Block: " + blockId);
        System.out.println("Previous Hash: " + prevHash);
        System.out.println("ProofOfWork (Hash): " + Hash);
        System.out.println("Nonce: " + nonce);
        System.out.println("Transactions: ");
        for (Transaction t : TransList) {
            System.out.print("\tSender: " + t.getSenderId());
            System.out.print("  Receiver: " + t.getReceiverId());
            System.out.println("  Amount: " + t.getAmount());
        }
        System.out.println(World.blockchain.size());
        System.out.println("-----------------------------");
    }
}