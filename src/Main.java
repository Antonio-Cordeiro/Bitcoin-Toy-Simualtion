
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException, NoSuchAlgorithmException{
    try (
        //ask user for parameters
        Scanner input = new Scanner(System.in)) {
            int numOfAgents = -1;
            while(numOfAgents < 10){
                if (numOfAgents == 0){
                    break;
                }
                System.out.println("Type the desired number of Agents (at least 10 or type 0 for default setting): ");
                numOfAgents = input.nextInt();
            }

            int numOfMiners = -1;
            while(numOfMiners < 5){
                if (numOfMiners == 0){
                    break;
                }
                System.out.println("Type the desired number of Miners (at least 5 or type 0 for default setting): ");
                numOfMiners = input.nextInt();
            }

            int reward = -1;
            while(reward <= 0){
                if (reward == 0){
                    break;
                }
                System.out.println("Type the desired amount of bitcoins for the reward of mining a block (or type 0 for default setting): ");
                reward = input.nextInt();
            }

            int complexity = -1;
            while(complexity < 0 | complexity > 5){
                if (complexity == 0){
                    break;
                }
                System.out.println("Type the desired complexity to mine a block in a scale of 1 to 5 (or type 0 for default setting): ");
                complexity = input.nextInt();
            }

            World.setParameters(numOfAgents, numOfMiners, reward, complexity);
        }

    //create and run agents
    for (int i = 0; i < World.numOfAgents; i++) {
        Agent a = new Agent();
        World.AgentsList.add(a);
        a.start();
    }

    //create and run miners
    for (int i = 0; i < World.numOfMiners; i++) {
        Miner m = new Miner();
        World.AgentsList.add(m);
        m.start();
    }

    
    Thread.currentThread();
    while (World.TotalBitcoins < 21000000){
        Thread.sleep(1);
    }
    
    System.out.println("there are " + World.TotalBitcoins + " bitcoins");
    System.out.println("the blockchain size is " + World.blockchain.size());
    System.exit(0);
    }
}