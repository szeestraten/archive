import java.awt.*;

public class IO implements Constants {
    // The queue of processes in the IO queue
    private Queue ioQueue;
    // Reference to the statistics collector
    private Statistics statistics;
    // Reference to the active process
    private Process activeProcess;
    // IO wait time
    private long avgIoTime;
    // Reference to the GUI
    private Gui gui;

    public IO(Queue ioQueue, Statistics statistics, long avgIoTime, Gui gui) {
        this.ioQueue = ioQueue;
        this.statistics = statistics;
        this.avgIoTime = avgIoTime;
        this.gui = gui;
        this.activeProcess = null;
    }

    public Event addIoRequest(Process process, long clock) {
        ioQueue.insert(process);
        // Make sure device is free
        if (isIoIdle())
            return startIoOperation(clock);

        // Another process is already doing IO
        return null;
    }

    public Event startIoOperation(long clock) {
        // Is there something to process
        if (!ioQueue.isEmpty()) {
            // Let the first process in the queue start IO
            activeProcess = getNextProcess();
            activeProcess.enteredIo(clock);
            gui.setIoActive(activeProcess);
            // Update statistics
            statistics.nofIoOperations++;

            return new Event(Constants.END_IO, clock + activeProcess.getTimeToNextIoOperation());
        }

        return null;
    }

    public Process endIoOperation(long clock) {
        Process process = activeProcess;
        process.leftIo(clock);
        activeProcess = null;
        gui.setIoActive(null);
        return process;
    }

    private Process getNextProcess() {
        return (Process) ioQueue.removeNext();
    }

    private boolean isQueueEmpty() {
        return ioQueue.isEmpty();
    }

    public boolean isIoIdle() {
        return activeProcess == null;
    }

    public void timePassed(long timePassed) {
        statistics.ioQueueLengthTime += ioQueue.getQueueLength()*timePassed;
        if (ioQueue.getQueueLength() > statistics.ioQueueLargestLength) {
            statistics.ioQueueLargestLength = ioQueue.getQueueLength();
        }
    }
}
