import java.io.*;

/**
 * This class supplies functionality for performing
 * blockwise disk input/output operations.
 */
public class DiskManager
{
	/** The size of disk blocks */
	private int blockSize;
	
	/**
	 * Creates a new DiskManager operating
	 * on blocks of the given size.
	 * @param blockSize	The size of file blocks.
	 */
	public DiskManager(int blockSize) {
		this.blockSize = blockSize;
	}

	/**
	 * Create a physical file consisting of the given number
	 * of blocks/pages.
	 * @param filename	The name of the physical file to create.
	 * @param nofPages	The number of pages/blocks that this file should consist of.
	 */
	public void createFile(String filename, int nofPages) {
		RandomAccessFile newFile;
		try {
			newFile = new RandomAccessFile(filename, "rw"); 
			// Write the first two bytes, containing the # of pages in the file:
			newFile.write(nofPages / 256);
			newFile.write(nofPages % 256);

			// Fill the file with '.', just to initialize the content to something.
			for (int i = 0; i < nofPages; i++) {
				for(int j = 0; j < blockSize; j++) {
					newFile.write((byte)'.');
				}
			}
			newFile.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Gets the number of blocks in a physical file created by a previous
	 * call to createFile().
	 * @param filename	The name of the physical file.
	 * @return			The number of blocks in the file.
	 */
	public int getFileSize(String filename) {
		int fileSize = -1;
		try {
			// Read the number of pages from the first two bytes of the file
			RandomAccessFile file = new RandomAccessFile(filename, "rw");
			fileSize = (int)file.readByte()*256 + (int)file.readByte();
			file.close();
		}
		catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return fileSize;
	}

	/**
	 * Writes a block of data to a given block in a physical file.
	 * @param filename	The name of the physical file to write to.
	 * @param block		The index of the block to write.
	 * @param content	The data to write to that block.
	 */
	public void writeBlock(String filename, int block, byte[] content) {
		try {
			RandomAccessFile file = new RandomAccessFile(filename, "rw");
			file.seek(block*blockSize+2);
			file.write(content, 0, blockSize);
			file.close();
		}
		catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Reads a given block of data from a physical file.
	 * @param filename	The name of the physical file to read from.
	 * @param block		The index of the block to read.
	 * @param buffer	The buffer in which to place the result.
	 */
	public void readBlock(String filename, int block, byte[] buffer) {
		try {
			RandomAccessFile file = new RandomAccessFile(filename, "r");
			file.seek(block*blockSize+2);
			file.read(buffer, 0, blockSize);
			file.close();
		}
		catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}