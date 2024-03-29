import java.util.List;
import java.util.Random;

public class MessageGenerator implements Runnable {

    List<ThreadState> threadsState_List;
    int threadsNumber;
    int messageNumber;

    MessageGenerator(List<ThreadState> threadsState_List, int threadsNumber, int messageNumber) {
        this.threadsState_List = threadsState_List;
        this.threadsNumber = threadsNumber;
        this.messageNumber = messageNumber;

    }

    @Override
    public void run() {
        Random random = new Random();
        // generate messages
        for (int i = 0; i < messageNumber; i++) {
            int randnum = random.nextInt(messageNumber * 9999);
            threadsState_List.get(random.nextInt(threadsNumber)).inbox.add(randnum);
        }

        for (ThreadState thrState : threadsState_List) {
            thrState.messageGeneratorClosing = true;
        }
        System.out.println("Total generated Messages: " + messageNumber);

        int i = 1;
        for (ThreadState thrState : threadsState_List) {
            System.out.println(
                    "Thread-" + i + " got " + thrState.inbox.size() + " external messages from MessageGenerator");
            i++;
        }

    }

}
