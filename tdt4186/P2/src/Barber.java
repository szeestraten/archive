/**
 * This class implements the barber's part of the
 * Barbershop thread synchronization example.
 */
public class Barber implements Runnable {

    private CustomerQueue queue;
    private Gui gui;
    private Thread thread;
    // Thread running flag
    private boolean threadRunning;
    // Position of barber chair
    private int pos;

    /**
     * Creates a new barber.
     *
     * @param queue The customer queue.
     * @param gui   The GUI.
     * @param pos   The position of this barber's chair
     */
    public Barber(CustomerQueue queue, Gui gui, int pos) {
        this.queue = queue;
        this.gui = gui;
        this.pos = pos;

        this.thread = new Thread(this);
        gui.println("Barber #" + pos + " has been created.");
    }

    /**
     * Starts the barber running as a separate thread.
     */
    public void startThread() {
        threadRunning = true;
        thread.start();
    }

    /**
     * Stops the barber thread.
     */
    public void stopThread() {
        threadRunning = false;
    }

    @Override
    public void run() {
        while (threadRunning) {
            barberSleep();
            cutCustomer();
        }
    }

    /**
     * Grabs a customer from the queue and cuts him for an amount of time specified by Globals.barberWork
     */
    private void cutCustomer() {
        Customer customer = queue.popCustomer();
        gui.println("Barber # " + pos + " was notified of a new customer.");
        gui.fillBarberChair(pos, customer);

        try {
            Thread.sleep(Globals.barberWork);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        gui.emptyBarberChair(pos);
    }

    /**
     * Makes the barber sleep for an amount of time set by Globals.barberSleep
     */
    private void barberSleep() {
        gui.barberIsSleeping(pos);

        try {
            Thread.sleep(Globals.barberSleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        gui.println("Barber #" + pos + " is waiting for customers...");
        gui.barberIsAwake(pos);
    }
}
