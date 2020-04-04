package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;

public class Player {
    private final  Integer stopCondition=10;
    private String playerId;
    private Integer counter=1;
    private List<Message> receivedMessages = new ArrayList<>();
    private BlockingQueue<Message> queue;
    private boolean lock=false;


    private Player(){}

    public void send(String receiverID) throws AllMessagesUsedException {
        if(queue!=null && !isLock()) {
            queue.add(getMessage(receiverID));
            increaseMessageCounter();
        }else{
            throw new AllMessagesUsedException();
        }
    }

    public boolean isLock() {
        if(counter>stopCondition
                && receivedMessages.size()>stopCondition){
            lock=true;
        }
        return lock;
    }

    public void receive(Message message) throws AllMessagesUsedException {
        receivedMessages.add(message);
        System.out.println("Message Received: "+message.getMessage());
        send(message.getSenderID());
    }

    public static Player getPlayer(String id){
        Player player = new Player();
        player.setPlayerId(id);
        System.out.println("Player "+player.getPlayerId()+" Created");
        return player;
    }

    public Message getMessage(String receiverID){
        return new Message(this.playerId,counter,receiverID);
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }
    public void increaseMessageCounter(){
        this.counter++;
    }

    public void setQueue(BlockingQueue<Message> queue) {
        this.queue = queue;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null ) return false;
        return Objects.equals(playerId, o.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId);
    }
}
