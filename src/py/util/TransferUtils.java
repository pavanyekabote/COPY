package py.util;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public abstract class TransferUtils extends Thread {

	private final static int SEND = 2, RECEIVE = 3;
	OutputStream out;
	InputStream in;
	PrintStream ps;
	Scanner sc;
	File f;
	int flag;
	String location;

	// Abstract Methods
	// Method to send transfer progress bytes
	/**
	 * Sends no.of bytes that have been transferred as input parameter
	 * 
	 * @param bytes
	 */
	public abstract void transferProgress(int bytes);

	// Send total filesize
	public abstract void fileSize(int bytes);

	public TransferUtils(InputStream in, OutputStream out) {
		this.in = in;
		this.out = out;
		ps = new PrintStream(out);
		sc = new Scanner(in);
	}

	@Override
	public void run() {
		try {
			if (flag == SEND)
				startSend(this.f);
			else if (flag == RECEIVE)
				startReceive(location);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean sendFile(File f) {

		this.flag = SEND;
		this.f = f;
		this.start();
		return false;

	}

	private boolean startSend(File f) throws Exception {
		sendMessage(f.getName());
		int c, totalsize = 0;
		byte[] buf = new byte[Store.MAX_DATA_LENGTH];
		FileInputStream fin = new FileInputStream(f);
		fileSize(fin.available());
		sendMessage(fin.available() + ""); // Send Size of file
		while ((c = fin.read(buf, 0, Store.MAX_DATA_LENGTH)) != -1) {
			out.write(buf, 0, c);
			totalsize += c;
			transferProgress(totalsize);
		}
		fin.close();
		return false;
	}

	public boolean receiveFile(String location) {
		flag = RECEIVE;
		this.location = location;
		this.start();
		return false;
	}

	private boolean startReceive(String location) throws IOException {
		String filename = sc.nextLine();
		int c, totalsize = 0, filesize = 0;
		byte[] buf = new byte[Store.MAX_DATA_LENGTH];
		System.out.println("File: " + filename);
		filesize = sc.nextInt();
		fileSize(filesize);

		FileOutputStream fout = new FileOutputStream(new File(location + "/" + filename));
		while ((c = in.read(buf, 0, Store.MAX_DATA_LENGTH)) != -1) {
			fout.write(buf, 0, c);
			totalsize += c;
			transferProgress(totalsize);
		}
		System.out.println("Completed Receiving");
		System.out.println("File: " + filename);
		fout.close();
		return false;
	}

	// Message sender
	public void sendMessage(String msg) {
		ps.println(msg);
	}

}
