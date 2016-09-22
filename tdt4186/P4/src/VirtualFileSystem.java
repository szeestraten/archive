public interface VirtualFileSystem
{
	/**
	 * Initializes the virtual file system.
	 *
	 * @param pageSize	The size of pages (file blocks).
	 * @param nofFrames	The number of page frames in the system.
	 */
	public void init(int pageSize, int nofFrames);

	/**
	 * Creates a new file with the given name and size.
	 *
	 * @param filename   A unique name identifying the file.
	 * @param nofPages   The size of the file, in pages.
	 */
	public void create(String filename, int nofPages);

	/**
	 * Opens a file created by the create() method.
	 *
	 * @param filename   The name of the file to be opened.
	 */
	public void open(String filename);

	/**
	 * Reads a sequence of bytes from the open file.
	 *
	 * @param address	The position of the first byte to be read, relative
	 *                  to the first byte in the file.
	 * @param nofBytes  The size number of bytes to be read.
	 * @param data      The buffer in which to place the read bytes. 
	 */
	public void read(int address, int nofBytes, byte[] data);

	/**
	 * Writes a sequence of bytes to the open file.
	 *
	 * @param address	The position of the first byte to be written, relative
	 *                  to the first byte in the file.
	 * @param data      A buffer of bytes to be written to the file.
	 */
	public void write(int address, byte[] data);

	/**
	 * Closes the file opened by the open() method.
	 */
	public void close();

	/**
	 * Specifies which page replacement algorithm to be used.
	 *
	 * @param chooser   An object, implementing the FrameChooser interface,
	 *                  specifying the page replacement algorithm.
	 */
	public void setFrameChooser(FrameChooser chooser);

	/**
	 * Prints the contents of the frame table in a compact but readable form
	 * to System.out. It suffices to list the indexes of the pages currently
	 * in the table, and the corresponding clock bits.
	 */
	public void printFrameTable();
}