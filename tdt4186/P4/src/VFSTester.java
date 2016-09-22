import java.io.*;
import java.util.*;

/**
 * This class contains methods for testing a Virtual File System
 * in various ways.
 */
public class VFSTester {
	/** The default page size to be used in this test */
	private static int pageSize = 50;
	/** The default number of pages in the file to be used in this test */
	private static int fileSize = 15;
	/** The default number of frames in the frame table for this test */
	private static int nofFrames = 5;
	/** The default number of accesses to do during this test */
	private static int nofIterations = 10000;
	/** The default minimum number of bytes to be accessed for each access in the test */
	private static int minAccessLength = 30;
	/** The default maximum number of bytes to be accessed for each access in the test */
	private static int maxAccessLength = 150;
	/** The default file name to use in the test */
	private static String filename = "test.dat";
	/** Parameter used in the distributed systems extension of this exercise */
	private static String servername = null;

	/**
	 * Starts a new test. The various test parameters may be set to non-default values
	 * by specifying command line parameters as explained in the usage string.
	 */
	public static void main (String[] args) {
		String usage =
			"Usage: java VFSTester [-options]\n\n"+

			"where options include:\n"+
			"-h		To display this text\n"+
			"-b		To run a test generating the same page faults as in the book\n"+
			"-p <size>	To set the page size in bytes\n"+
			"-n <frames>	To set the number of page frames\n"+
			"-l <pages>	To set the number of pages in the file\n"+
			"-f <file>	To specify a file name (test.dat is default)\n"+
			"-i <number>	To specify the number of iterations to use.\n"+
			"-min <number>	To specify the minimum number of bytes accessed.\n"+
			"-max <number>	To specify the maximum number of bytes accessed.\n"+
			"-r <FIFO|LRU|CLOCK> To specify what page replacement algorithm should be used.\n"+
			"-s <servername>	To test a distributed file system registered at the specified server.";

		String replacementAlgorithm = "FIFO";
		boolean runBookTest = false;

		if(args.length == 0)
			System.out.println("Run \"java VFSTester -h\" to see nondefault testing options.");
		if(args.length == 1 && args[0].equals("-h")) {
				System.out.println(usage);
				System.exit(0);
		}
		// Alter parameters specified by command line arguments
		for(int i = 0; i < args.length; i++) {
			if(args[i].equals("-b"))
				runBookTest = true;
			else if(args[i].equals("-p"))
				pageSize = new Integer(args[++i]).intValue();
			else if(args[i].equals("-n"))
				nofFrames = new Integer(args[++i]).intValue();
			else if(args[i].equals("-i"))
				nofIterations = new Integer(args[++i]).intValue();
			else if(args[i].equals("-min"))
				minAccessLength = new Integer(args[++i]).intValue();
			else if(args[i].equals("-max"))
				maxAccessLength = new Integer(args[++i]).intValue();
			else if(args[i].equals("-l"))
				fileSize = new Integer(args[++i]).intValue();
			else if(args[i].equals("-r"))
				replacementAlgorithm = args[++i];
			else if(args[i].equals("-f")) 
				filename = args[++i];
			else if(args[i].equals("-s")) 
				servername = args[++i];
		}
		if(runBookTest) {
			// Use the same key parameters as in the test on page 357 in the book:
			nofFrames = 3;
			fileSize = 5;
		}

		// Create virtual file system:
		VirtualFileSystem vfs = null;
		if(servername == null) {
			// Use a local file system
			vfs = new VirtualFileSystemImpl();
		}
		else {
			// Use a distributed file system, not implemented
			System.exit(1);
		}
		vfs.init(pageSize, nofFrames);

		// Run test:
		System.out.println();
		System.out.println("VIRTUAL FILE MANAGEMENT TEST");
		System.out.println("Test parameters:");
		System.out.println("File name: "+filename);
		System.out.println("File size: "+fileSize+" pages.");
		System.out.println("Page size: "+pageSize+" bytes.");
		System.out.println("# of page frames: "+nofFrames);
		if(runBookTest) {
			// Create frame chooser
			FrameChooser frameChooser;
			if(replacementAlgorithm.equalsIgnoreCase("FIFO")) {
				frameChooser = new FifoChooser();
			}
			else if(replacementAlgorithm.equalsIgnoreCase("LRU")) {
				frameChooser = new LruChooser();
			}
			else if(replacementAlgorithm.equalsIgnoreCase("CLOCK")) {
				frameChooser = new ClockChooser();
			}
			else {
				frameChooser = new FifoChooser();
			}
			runFixedAccessTest(vfs, frameChooser);
		}
		else {
			runRandomAccessTest(vfs);
		}
	}

	/**
	 * Runs a test of the virtual file system using randomly generated access patterns.
	 * Tests all the different page replacement algorithms and compares the results.
	 * @param vfs	The VirtualFileSystem object to test.
	 */
	public static void runRandomAccessTest(VirtualFileSystem vfs) {
		FrameChooser fc;
		System.out.println("Test type: Random access comparative testing");
		System.out.println("# of random accesses: "+nofIterations);
		System.out.println("Access size: "+minAccessLength+"-"+maxAccessLength+" bytes.");
		System.out.println();
		System.out.println("Running comparative test between different replacement algorithms:\n");
		fc = new FifoChooser();
		testFrameChooser(vfs, fc);
		fc = new LruChooser();
		testFrameChooser(vfs, fc);
		fc = new ClockChooser();
		testFrameChooser(vfs, fc);
		System.out.println("Comparative test done.");
	}

	/**
	 * Runs a test of the virtual file system using randomly generated access patterns,
	 * with the page replacement algorithm specified. The test uses random accesses, but
	 * keeps accesses clustered 90% of the time. So on average, a new cluster of accesses
	 * is started every tenth access. The relative performance of various page replacement
	 * policies will nonetheless depend heavily on the chosen test parameters. With default
	 * test parameters the relative performance of the different algorithms should not be
	 * too unrealistic, even though hotspots and repetitive access patterns are not
	 * implemented.
	 *
	 * @param vfs	The VirtualFileSystem object to test.
	 * @param fc	The FrameChooser object dictating the page replacement policy.
	 */
	private static void testFrameChooser(VirtualFileSystem vfs, FrameChooser fc) {
		System.out.println("Testing replacement algorithm "+fc.getAlgorithm()+"...");
		vfs.setFrameChooser(fc);
		vfs.create(filename, fileSize);
		vfs.open(filename);
		int byteSize = fileSize*pageSize;
		byte[] buffer = new byte[maxAccessLength];
		int lastAddress = -1;
		int address;
		for(int i = 0; i < nofIterations; i++) {
			int nofBytes = (int)(Math.random()*(maxAccessLength-minAccessLength+1))+minAccessLength;
			if(lastAddress > -1 && Math.random() < 0.9) {
				// In 90% of cases, access an address close to the last address accessed
				address = lastAddress+(int)((0.2*Math.random()-0.1)*byteSize);
				if(address < 0)	address = 0;
				if(address > byteSize-nofBytes) address = byteSize-nofBytes;
			}
			else {
				// In the remaining 10% of cases, start a new "cluster" of accesses by choosing
				// a completely random address.
				address = (int)(Math.random()*(byteSize-nofBytes+1));
			}
			vfs.read(address, nofBytes, buffer);
			lastAddress = address;
		}
		vfs.close();
		System.out.println("Number of page faults in "+nofIterations+" random accesses: "+fc.getNofPageFaultsProcessed());
		System.out.println();
	}

	/**
	 * Runs a test of the virtual file system using the same accesses used
	 * on page 357 of the book.
	 *
	 * @param vfs	The VirtualFileSystem object to test.
	 * @param fc	The FrameChooser object dictating the page replacement policy.
	 */
	public static void runFixedAccessTest(VirtualFileSystem vfs, FrameChooser fc) {
		System.out.println();
		System.out.println("Testing replacement algorithm "+fc.getAlgorithm()+" using accesses specified on page 357 in the book:");
		vfs.setFrameChooser(fc);
		vfs.create(filename, fileSize);
		vfs.open(filename);
		int byteSize = fileSize*pageSize;
		// Access the same pages as in the book, page 357:
		int[] pageAccesses = {2, 3, 2, 1, 5, 2, 4, 5, 3, 2, 5, 2};
		byte[] buffer = new byte[1];

		for(int i = 0; i < pageAccesses.length; i++) {
			int address = pageAccesses[i]*pageSize-pageSize/2-1;
			// Read one byte from the middle of the page to be accessed:
			vfs.read(address, 1, buffer);
			System.out.println("Frame table after accessing page "+pageAccesses[i]+":");
			vfs.printFrameTable();
		}
		vfs.close();
		System.out.println("Number of page faults generated: "+fc.getNofPageFaultsProcessed());
	}
}