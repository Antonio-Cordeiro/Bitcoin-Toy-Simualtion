## Introduction

We developed a Java-based blockchain simulation, similar to Bitcoins, which provides insights into the fundamental concepts of blockchain networks, decentralized transactions, and the mining process. \
The paper discusses the key components of the simulation, including agents, miners, transactions and the blockchain structure. Additionally, it delves into the synchronization mechanisms used to ensure the integrity of the simulation in a multi-threaded environment.

## Getting Started

To try running the project you just have to run the main class. You will be able to set some parameters and then, when the program starts running, something will be printed out when a block is added to the blockchain. \
It is possible to modify the code to your liking and print something different, like a block structure or when invalid blocks are not added to the blockchain.

## Implementation details

- As already mentioned above, we decided to model agents and miners as threads. More specifically, the Agent class extends the Thread class, and miners are a subclass of agents. All agents have both a public and a private ID, and for simplicity they start with an initial balance of 50 bitcoins. After they start running, agents randomly create transactions and put them in a pool.
- In these transactions, they can either be the senders or the receivers (like asking someone for money in the real world), in which case they need to get the validation from the sender they are asking money from. The role of miners is to select transactions and place them into a block. 
- They then begin the mining process to find a nonce that results in a block hash with a specified number of leading zeros (the difficulty level). While the real Bitcoin adjusts this difficulty periodically, in our project we have made it a user-settable constant. 
- The Blockchain is implemented using a synchronized queue, and we have defined a method for adding blocks. This method checks the block’s validity by recomputing its hash and verifying that the previous block’s hash is correct. This is necessary because it’s possible for two miners to mine a block simultaneously, but the one added to the chain first determines the correct previous block’s hash. 
- Finally, the World class is used to define all global variables and methods required to run the simulation. Users can configure several parameters, such as the number of agents and miners, the miner’s reward amount, and the block mining difficulty level (i.e., the number of leading zeros in the block’s hash). Additionally, there is a list to keep track of agents and a synchronized queue to manage the pool of transactions.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

