/**
 * This class implements a queue of customers as a circular buffer.
 */
public class CustomerQueue {

    private Gui gui;
    // Array of the customers in the queue
    private Customer[] queue;
    // Pointer for the circular buffer
    private int bufferPointer;
    // The amount of queued customers
    private int queuedCustomers;

    /**
     * Creates a new customer queue.
     *
     * @param queueLength The maximum length of the queue.
     * @param gui         A reference to the GUI interface.
     */
    public CustomerQueue(int queueLength, Gui gui) {
        this.gui = gui;
        queue = new Customer[queueLength];
        bufferPointer = 0;
        queuedCustomers = 0;
    }

    /**
     * Adds a customer to the queue. If queue is full, we'll wait.
     *
     * @param customer
     */
    public synchronized void addCustomer(Customer customer) {
        // While full
        while (queuedCustomers == queue.length) {
            gui.println("Doorman is waiting for free chairs...");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Awakened from wait()
            gui.println("Doorman was notified of a free chair.");
        }

        // Add customer to queue
        queue[bufferPointer] = customer;
        // Update lounge with new customer and position
        gui.fillLoungeChair(bufferPointer, customer);
        // Move pointer forward
        bufferPointer = (bufferPointer + 1) % queue.length;
        // Increase customers in queue
        queuedCustomers++;
        // Notify All
        notifyAll();
    }

    /**
     * Pops a customer from the queue. If no customers in the queue, we'll wait.
     *
     * @return customer
     */
    public synchronized Customer popCustomer() {
        // While no customers
        while (queuedCustomers == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Calculate position of first customer based on queued customer, current pointer and queue length
        int position = (bufferPointer + (queue.length - queuedCustomers)) % queue.length;
        Customer customer = queue[position];
        // Update lounge with removal of customer
        gui.emptyLoungeChair(position);
        // Decrease customers in queue
        queuedCustomers--;
        // Notify All
        notifyAll();

        // Return customer
        return customer;
    }
}
