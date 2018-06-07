package py.client;

import java.util.*;

import javax.swing.JFrame;

import py.util.Store;
import py.util.TransferUtils;

import java.io.IOException;
import java.net.*;

public class PYShare extends JFrame {
	private static final long serialVersionUID = 1L;
	int filesize;
	Socket socket;
	Scanner sc;
	String IP;
	TransferUtils utils;

	public PYShare() throws IOException {

		sc = new Scanner(System.in);
		System.out.println("Enter IP Address: ");
		IP = sc.nextLine();
		socket = new Socket(IP, Store.PORT);
		boolean isReceived = false;
		if (socket.isConnected()) {
			System.out.println("Connnection Established");
			utils = new TransferUtils(socket.getInputStream(), socket.getOutputStream()) {

				@Override
				public void transferProgress(int data) {
					System.out.println("DataReceived: " + (((double) (data / filesize)) * 100));
				}

				@Override
				public void fileSize(int bytes) {
					filesize = bytes;

				}
			};
			isReceived = utils.receiveFile(Store.DEFAULT_LOCATION);
			//System.out.println("Receive " + (isReceived ? "Success" : "Failed"));
		}

	}

	public static void main(String args[]) throws IOException {
		new PYShare();
	}

}
