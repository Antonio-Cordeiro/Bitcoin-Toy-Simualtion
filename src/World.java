import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class World {
    //parameters that can be set by the user
    public static int numOfAgents = 10;
    public static int numOfMiners = 10;
    public static int reward = 2500;
    public static String complexity = "000";

    public static SynchroQueue<Transaction> queue = new SynchroQueue<>();
    public static ArrayList<Integer> usedPublicIds = new ArrayList<Integer>();
    public static ArrayList<Agent> AgentsList = new ArrayList<Agent>();
    public static int blockId=1;
    public static String prevHash="";
    public static Blockchain blockchain = new Blockchain();
    public static int TotalBitcoins = 0;

        
    public static synchronized int getBlockId() {
        return blockId++;
    }

    public static synchronized String getPrevHash() {
        return prevHash;
    }
    
    public static synchronized void setPrevHash(String Hash) {
        World.prevHash = Hash;
    }

    //used to hash
    public static String HashGenerator(String inputString) throws NoSuchAlgorithmException{
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(inputString.getBytes(StandardCharsets.UTF_8));
        String hexHash = String.format("%064x", new BigInteger(1, hash));
        return hexHash;
    }

    public static synchronized void addBitcoins(){
        TotalBitcoins += reward;
    }

    //set user parameters
    public static void setParameters(int numOfAgents, int numOfMiners, int reward, int complexity){
        if (numOfAgents != 0){
            World.numOfAgents = numOfAgents;
        }

        if (numOfMiners != 0){
            World.numOfMiners = numOfMiners;
        }

        if (reward != 0) {
            World.reward = reward;
        }

        if (complexity != 0 && complexity != 3){
            World.complexity = "0".repeat(complexity);
        }
    }
}