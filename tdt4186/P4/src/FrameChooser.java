/**
 * An interface specifying methods for selecting which frame in
 * a frame table should be chosen when the need to replace a
 * page arises.
 */
public interface FrameChooser
{
	/**
	 * Selects a replaceable frame from a full frame table.
	 *
	 * @param frameTable  The frame table.
	 * @return            The frame whose page can be replaced.
	 */
	public Frame chooseFrame(FrameTable frameTable);

	/**
	 * Returns the number of page faults processed.
	 *
	 * @return  The number of times the chooseFrame() method has been invoked.
	 */
	public int getNofPageFaultsProcessed();

	/**
	 * Returns a string describing the algorithm used by this FrameChooser.
	 *
	 * @return  The name of the page replacement algorithm used.
	 */
	public String getAlgorithm();
}
