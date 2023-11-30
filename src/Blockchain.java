
import java.security.NoSuchAlgorithmException;

public class Blockchain {
    public SynchroQueue<Block> chain= new SynchroQueue<>() ;

    public synchronized boolean addBlock(Block block) throws NoSuchAlgorithmException, InterruptedException {
        if (chain.isEmpty()) {
            // This is the first block in the chain
            chain.insert(block);
            return true;
        } 
        
        else if (World.TotalBitcoins < 21000000){
            chain.insert(block);
            if(!isChainValid(block)) { //block not added
                chain.extract();
                //System.out.println("Invalid block, the size didn't change "+this.size());
                return false;
            }

            else { //block added
                World.setPrevHash(block.getHash());
                System.out.println("Valid block, the size is:  "+this.size() + " and there are " + World.TotalBitcoins + " Bitcoins");
            return true;}
        }
        return false;
    }

    public synchronized boolean isChainValid(Block currentBlock) throws NoSuchAlgorithmException {
        //check if the block we are inserting is valid
        if ((currentBlock.getHash().equals(currentBlock.calculateBlockHash())) && (World.getPrevHash()==(currentBlock.prevHash))) {
            return true;
        }
        return false;
        }

    public int size() {
        return chain.getLen();
    }
}