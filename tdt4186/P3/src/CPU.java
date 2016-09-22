public class CPU implements Constants {
    // The queue of processes being processed in the cpu
    private Queue cpuQueue;
    // Reference to the statistics collector
    private Statistics statistics;
    // Reference to the active process
    private Process activeProcess;
    // Max CPU time allowed
    private long maxCpuTime;
    // Reference to the GUI
    private Gui gui;

    // Creates a new CPU device
    public CPU(Queue cpuQueue, Statistics statistics, long maxCpuTime, Gui gui) {
        this.cpuQueue = cpuQueue;
        this.statistics = statistics;
        this.maxCpuTime = maxCpuTime;
        this.gui = gui;
    }

    public Event switchProcess(long clock) {
        // The CPU is busy with an process
        if (!isCpuIdle()) {
            // Clock out the active process
            Process prev = popActiveProcess(clock);
            // Update forced switch stats
            statistics.nofForcedSwitches++;
            // Add process to the back of the queue
            addProcess(prev);
            // Start next process
            return startNextProcess(clock);
        }
        // CPU is idle
        else {
            // Switch to next process in queue
            if (!isQueueEmpty()) {
                // Start next process
                return startNextProcess(clock);
            }
            // Ready queue is empty so setting to null
            else {
                activeProcess = null;
                gui.setCpuActive(null);
                return null;
            }
        }
    }

    private Event startNextProcess(long clock) {
        activeProcess = getNextProcess();
        activeProcess.enteredCpu(clock);
        gui.setCpuActive(activeProcess);

        // CPU max time before process or IO request
        if (activeProcess.getTimeToNextIoOperation() > maxCpuTime && activeProcess.getCpuTimeNeeded() > maxCpuTime) {
            return new Event(SWITCH_PROCESS, clock + maxCpuTime);
        }
        // Process ends before max time and IO
        else if (activeProcess.getTimeToNextIoOperation() > activeProcess.getCpuTimeNeeded()) {
            return new Event(END_PROCESS, clock + activeProcess.getCpuTimeNeeded());
        }
        // IO request incoming
        else {
            return new Event(IO_REQUEST, clock + activeProcess.getTimeToNextIoOperation());
        }
    }

    public Process popActiveProcess(long clock) {
        Process process = activeProcess;
        activeProcess.leftCpu(clock);
        activeProcess = null;
        gui.setCpuActive(null);
        return process;
    }

    public void addProcess(Process process) {
        cpuQueue.insert(process);
    }

    private Process getNextProcess() {
        return (Process) cpuQueue.removeNext();
    }

    public boolean isCpuIdle() {
        return activeProcess == null;
    }

    private boolean isQueueEmpty() {
        return cpuQueue.isEmpty();
    }

    public void timePassed(long timePassed) {
        statistics.cpuQueueLengthTime += cpuQueue.getQueueLength()*timePassed;
        if (cpuQueue.getQueueLength() > statistics.cpuQueueLargestLength) {
            statistics.cpuQueueLargestLength = cpuQueue.getQueueLength();
        }
    }
}

