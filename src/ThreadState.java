import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.*;

public class ThreadState implements Runnable {
    private Logger logr;
    MessageSequencer messageSequencer;
    BlockingQueue<Integer> inbox = new LinkedBlockingQueue<Integer>(); // for external messages
    BlockingQueue<Integer> logMessages = new LinkedBlockingQueue<Integer>();
    boolean messageGeneratorClosing = false;
    boolean messageSequencerClosing = false;
    boolean thrClosing = false;
    int nodeID;

    public ThreadState(int nodeID, MessageSequencer messageSequencer) {
        this.messageSequencer = messageSequencer;
        this.nodeID = nodeID;
    }

    @Override
    public void run() {
        // creat a logger
        logr = Logger.getLogger(Thread.currentThread().getName());
        LogManager.getLogManager().reset();
        logr.setLevel(Level.ALL);
        String loggerfilename = String.format("Thread_%d.log", nodeID);
        FileHandler fh = null;
        try {
            fh = new FileHandler(loggerfilename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s"); // log message=(5$) --> to print only
                                                                                // the log message without timestamp
        fh.setFormatter(new SimpleFormatter());
        fh.setLevel(Level.INFO);

        logr.addHandler(fh);
        logr.setUseParentHandlers(false);

        int i = 0;
        while (true) {

            while (!inbox.isEmpty()) {

                messageSequencer.queue.add(inbox.poll());

            }

            while (!logMessages.isEmpty()) {
                String logMessage = String.format("%d\n", logMessages.poll());
                this.logr.info(logMessage);

                i++;

            }

            // terminating the thread in case it is done
            if (messageGeneratorClosing && inbox.isEmpty()) {
                thrClosing = true;
            }
            if (messageGeneratorClosing && inbox.isEmpty() && logMessages.isEmpty() && messageSequencerClosing) {
                System.out.println("logged Messages " + Thread.currentThread().getName() + " :" + i);
                break;
            }

        }

    }
}
