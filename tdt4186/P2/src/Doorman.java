/**
 * This class implements the doorman's part of the
 * Barbershop thread synchronization example.
 */
public class Doorman implements Runnable {

    private CustomerQueue queue;
    private Gui gui;
    private Thread thread;
    // Thread running flag
    private boolean threadRunning;

    /**
     * Creates a new doorman.
     *
     * @param queue The customer queue.
     * @param gui   A reference to the GUI interface.
     */
    public Doorman(CustomerQueue queue, Gui gui) {
        this.queue = queue;
        this.gui = gui;

        this.thread = new Thread(this);
        gui.println("Doorman has been created.");
    }

    /**
     * Starts the doorman running as a separate thread.
     */
    public void startThread() {
        threadRunning = true;
        thread.start();
    }

    /**
     * Stops the doorman thread.
     */
    public void stopThread() {
        threadRunning = false;
    }

    @Override
    public void run() {
        while (threadRunning) {
            doormanSleep();
            addCustomer();
        }
    }

    /**
     * Adds a new customer to the queue.
     */
    private void addCustomer() {
        gui.println("Adding customer to queue.");
        queue.addCustomer(new Customer());
    }

    /**
     * Makes the doorman sleep for an amount set by Globals.doormanSleep
     */
    private void doormanSleep() {
        gui.println("Doorman is sleeping for " + Globals.doormanSleep + ".");

        try {
            Thread.sleep(Globals.doormanSleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
