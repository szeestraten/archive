import java.io.*;

/**
 * The main class of the P3 exercise. This class is only partially complete.
 */
public class Simulator implements Constants
{
	/** The queue of events to come */
    private EventQueue eventQueue;
	/** Reference to the memory unit */
    private Memory memory;
	/** Reference to the CPU unit */
    private CPU cpu;
    /** Reference to the IO unit */
    private IO io;
	/** Reference to the GUI interface */
	private Gui gui;
	/** Reference to the statistics collector */
	private Statistics statistics;
	/** The global clock */
    private long clock;
	/** The length of the simulation */
	private long simulationLength;
	/** The average length between process arrivals */
	private long avgArrivalInterval;
    /** The average IO wait time */
    private long avgIoTime;

	/**
	 * Constructs a scheduling simulator with the given parameters.
	 * @param memoryQueue			The memory queue to be used.
	 * @param cpuQueue				The CPU queue to be used.
	 * @param ioQueue				The I/O queue to be used.
	 * @param memorySize			The size of the memory.
	 * @param maxCpuTime			The maximum time quant used by the RR algorithm.
	 * @param avgIoTime				The average length of an I/O operation.
	 * @param simulationLength		The length of the simulation.
	 * @param avgArrivalInterval	The average time between process arrivals.
	 * @param gui					Reference to the GUI interface.
	 */
	public Simulator(Queue memoryQueue, Queue cpuQueue, Queue ioQueue, long memorySize,
			long maxCpuTime, long avgIoTime, long simulationLength, long avgArrivalInterval, Gui gui) {
		this.simulationLength = simulationLength;
		this.avgArrivalInterval = avgArrivalInterval;
		this.gui = gui;
		statistics = new Statistics();
		eventQueue = new EventQueue();
		memory = new Memory(memoryQueue, memorySize, statistics);
		clock = 0;

		// Add code as needed
        cpu = new CPU(cpuQueue, statistics, maxCpuTime, gui);
        io = new IO(ioQueue, statistics, avgIoTime, gui);
        this.avgIoTime = avgIoTime;
    }

    /**
	 * Starts the simulation. Contains the main loop, processing events.
	 * This method is called when the "Start simulation" button in the
	 * GUI is clicked.
	 */
	public void simulate() {
		System.out.print("Simulating...");
		// Genererate the first process arrival event
		eventQueue.insertEvent(new Event(NEW_PROCESS, 0));

		// Process events until the simulation length is exceeded:
		while (clock < simulationLength && !eventQueue.isEmpty()) {
			// Find the next event
			Event event = eventQueue.getNextEvent();

			// Find out how much time that passed...
			long timeDifference = event.getTime()-clock;
			// ...and update the clock.
			clock = event.getTime();

			// Let everyone know that time has passed
			memory.timePassed(timeDifference);
            cpu.timePassed(timeDifference);
            io.timePassed(timeDifference);
			gui.timePassed(timeDifference);

			// Deal with the event
			if (clock < simulationLength) {
				processEvent(event);
			}

			// Note that the processing of most events should lead to new
			// events being added to the event queue!

		}
		System.out.println("..done.");
		// End the simulation by printing out the required statistics
		statistics.printReport(simulationLength);
	}

	/**
	 * Processes an event by inspecting its type and delegating
	 * the work to the appropriate method.
	 * @param event	The event to be processed.
	 */
	private void processEvent(Event event) {
		switch (event.getType()) {
			case NEW_PROCESS:
				createProcess();
				break;
			case SWITCH_PROCESS:
				switchProcess();
				break;
			case END_PROCESS:
				endProcess();
				break;
			case IO_REQUEST:
				processIoRequest();
				break;
			case END_IO:
				endIoOperation();
				break;
		}
	}

	/**
	 * Simulates a process arrival/creation.
	 */
	private void createProcess() {
		// Create a new process
		Process newProcess = new Process(memory.getMemorySize(), clock);
		memory.insertProcess(newProcess);
		flushMemoryQueue();
		// Add an event for the next process arrival
		long nextArrivalTime = clock + 1 + (long)(2*Math.random()*avgArrivalInterval);
		eventQueue.insertEvent(new Event(NEW_PROCESS, nextArrivalTime));
		// Update statistics
		statistics.nofCreatedProcesses++;
    }

	/**
	 * Transfers processes from the memory queue to the ready queue as long as there is enough
	 * memory for the processes.
	 */
	private void flushMemoryQueue() {
		Process p = memory.checkMemory(clock);
		// As long as there is enough memory, processes are moved from the memory queue to the cpu queue
		while(p != null) {
            // Add process to CPU queue
            cpu.addProcess(p);
            if (cpu.isCpuIdle()) {
                switchProcess();
            }
			// Try to use the freed memory:
			flushMemoryQueue();
			// Check for more free memory
			p = memory.checkMemory(clock);
		}
	}

	/**
	 * Simulates a process switch.
	 */
	private void switchProcess() {
        eventQueue.insertEvent(cpu.switchProcess(clock));
	}

	/**
	 * Ends the active process, and deallocates any resources allocated to it.
	 */
	private void endProcess() {
		Process endedProcess = cpu.popActiveProcess(clock);
		endedProcess.updateStatistics(statistics);
		memory.processCompleted(endedProcess);
		// Continue processing
		if (cpu.isCpuIdle())
			switchProcess();
	}

	/**
	 * Processes an event signifying that the active process needs to
	 * perform an I/O operation.
	 */
	private void processIoRequest() {
		Process activeProcess = cpu.popActiveProcess(clock);
		eventQueue.insertEvent(io.addIoRequest(activeProcess, clock));
		// Continue processing
		if (cpu.isCpuIdle())
			switchProcess();
	}

	/**
	 * Processes an event signifying that the process currently doing I/O
	 * is done with its I/O operation.
	 */
	private void endIoOperation() {
		Process process = io.endIoOperation(clock);
		cpu.addProcess(process);
		eventQueue.insertEvent(io.startIoOperation(clock));
		if (cpu.isCpuIdle())
			switchProcess();
	}

	/**
	 * Reads a number from the an input reader.
	 * @param reader	The input reader from which to read a number.
	 * @return			The number that was inputted.
	 */
	public static long readLong(BufferedReader reader) {
		try {
			return Long.parseLong(reader.readLine());
		} catch (IOException ioe) {
			return 100;
		} catch (NumberFormatException nfe) {
			return 0;
		}
	}

	/**
	 * The startup method. Reads relevant parameters from the standard input,
	 * and starts up the GUI. The GUI will then start the simulation when
	 * the user clicks the "Start simulation" button.
	 * @param args	Parameters from the command line, they are ignored.
	 */
	public static void main(String args[]) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("System parameters: ");

		long memorySize = 2048;
		System.out.println("Memory size (KB): " + memorySize);

		long maxCpuTime = 500;
		System.out.println("Maximum uninterrupted cpu time for a process (ms): " + maxCpuTime);

		long avgIoTime = 225;
		System.out.println("Average I/O operation time (ms): " + avgIoTime);

        long simulationLength = 250000;
		System.out.println("Simulation length (ms): " + simulationLength);

		long avgArrivalInterval = 5000;
		System.out.println("Average time between process arrivals (ms): " + avgArrivalInterval);

		SimulationGui gui = new SimulationGui(memorySize, maxCpuTime, avgIoTime, simulationLength, avgArrivalInterval);
	}

}
