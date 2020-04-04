package org.example;

public class Message {
    private  String messageID;
    private String message;
    private String receiverID;
    private String senderID;

    public Message(String sender, Integer counter,String receiverID){
        this.senderID=sender;
        this.messageID=sender+counter;
        this.receiverID=receiverID;
        createMessage();
    }

    public void createMessage() {
        StringBuilder sb = new StringBuilder();
        this.message=sb//.append(this.senderID)
                .append("- THIS IS A MESSAGE -")
                .append(" message# ")
                .append(messageID.substring(messageID.length() - 1))
                //.append(" - ")
                //.append(messageID)
                .toString();
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getReceiverID() {
        return receiverID;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }
}
