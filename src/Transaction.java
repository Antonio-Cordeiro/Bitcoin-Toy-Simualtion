class Transaction {
    private Agent sender;
    private Agent receiver;
    private int amount;
    private int Id;

    public Transaction(Agent sender, Agent receiver, int amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.Id=World.queue.getId();
    }

    public int getId(){
        return Id;
    }

    public String getSenderId() {
        return sender.publicId;
    }

    public String getReceiverId() {
        return receiver.publicId;
    }

    public int getAmount() {
        return amount;
    }

    public Agent getSender() {
        return sender;
    }

    public Agent getReceiver() {
        return receiver;
    }
}
