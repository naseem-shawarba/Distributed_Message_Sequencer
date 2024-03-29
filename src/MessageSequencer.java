import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageSequencer implements Runnable {

    List<ThreadState> threadsState_List;
    BlockingQueue<Integer> queue;

    private static MessageSequencer instance = null;

    MessageSequencer() {
        threadsState_List = new ArrayList<ThreadState>();
        queue = new LinkedBlockingQueue<Integer>();
    }

    @Override
    public void run() {

        int i = 0; // to count total messages recieved from MessageGenerator
        while (true) {

            while (!queue.isEmpty()) {

                int message = queue.poll();
                for (ThreadState thrState : threadsState_List) {
                    // System.out.println(i);
                    thrState.logMessages.add(message);
                }

                i++;

            }

            // number of terminated threads
            int gg = 0;
            for (ThreadState thrState : threadsState_List) {
                if (thrState.thrClosing) {
                    gg++;
                }
            }

            if (queue.isEmpty() && gg == threadsState_List.size()) {
                System.out.println("internal messages from MessageSequencer :" + i);
                for (ThreadState thrState : threadsState_List) {
                    thrState.messageSequencerClosing = true;
                }
                break;
            }

        }
    }

    public static synchronized MessageSequencer getInstance() {
        if (instance == null) {
            instance = new MessageSequencer();
        }
        return instance;
    }
}
