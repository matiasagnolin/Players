package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
/**
 * Hello world!
 *
 */
public class App {
    private final static Integer playerQuantity=50;
    static  BlockingQueue<Message>  queue = new LinkedBlockingQueue<>();
    private static boolean finishDueToLockedPlayer=false;

    public static void main( String[] args ) throws AllMessagesUsedException {
    List<Player> players = new ArrayList<>();
    Random random = new Random();
    configuration(players);

    Integer firstRandomSender= random.nextInt(players.size());
    Integer firstRandomReceiver= random.nextInt(players.size());

    players.get(firstRandomSender)
            .send(players.get(firstRandomReceiver).getPlayerId());

    while (!finishDueToLockedPlayer) {
        if(queue.size()>0){
            try {
                Message msg = queue.take();
                 players.stream()
                        .filter(p->p.equals(msg.getReceiverID()))
                        .findFirst()
                        .ifPresent(player -> {
                            try {
                                player.receive(msg);
                            } catch (AllMessagesUsedException e) {
                                System.out.println("locked player "+ msg.getReceiverID());
                                finishDueToLockedPlayer=true;
                                return;
                            }
                        });
            } catch ( InterruptedException  e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void configuration(List<Player> players){
        for(int i=0;i<=playerQuantity;i++) {
            Player p = Player.getPlayer(UUID.randomUUID().toString());
            p.setQueue(queue);
            players.add(p);
        }
    }

}
