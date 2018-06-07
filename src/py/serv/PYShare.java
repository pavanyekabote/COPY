package py.serv;

import java.io.*;
import java.net.*;
import java.awt.*;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;

import py.util.Store;
import py.util.TransferUtils;

public class PYShare extends JFrame {

	private static final long serialVersionUID = 1L;
	TransferUtils tu = null;
	ServerSocket server = null;
	Socket socket = null;
	String location;
	DataInputStream in;
	int filesize, prevp = 0;
	JProgressBar jp;
	JFileChooser fileChooser;

	public PYShare() throws Exception {
		super("Transferer");
		jp = new JProgressBar();
		setFrame(this);
		in = new DataInputStream(System.in);
		server = new ServerSocket(Store.PORT);
		socket = server.accept();
		if (socket != null)
			System.out.println("Connection Established");

		tu = new TransferUtils(socket.getInputStream(), socket.getOutputStream()) {

			@Override
			public void transferProgress(int data) {
				int progress = (int) ((double) data / filesize * 100);
				if (progress != prevp)
					System.out.println("DataSent: " + progress + "%");
				jp.setValue(progress);
				if (progress >= 95)
					jp.setVisible(false);
				
				prevp = progress;
			}

			@Override
			public void fileSize(int bytes) {
				filesize = bytes;

			}
		};

		fileChooser = new JFileChooser("Select file to be transferred");
		int x = fileChooser.showOpenDialog(null);
		if (x == JFileChooser.APPROVE_OPTION)
			location = fileChooser.getSelectedFile().getAbsolutePath();
		if (x != JFileChooser.APPROVE_OPTION) {
			System.out.println("Enter FileLocation with filename to send:");
			location = in.readLine();
		}
		if (tu.sendFile(new File(location)))
			System.out.println("Transferred Successfully");
		else
			System.out.println("Error while transferring");
	}

	public static void main(String args[]) throws Exception {
		new PYShare();
	}

	void setFrame(PYShare frame) {
		frame.setLocationRelativeTo(null);
		frame.setPreferredSize(new Dimension(400, 100));
		frame.setLayout(new FlowLayout());
		frame.add(new JLabel("Receiving Progress"));
		frame.add(jp);
		frame.pack();
		frame.setVisible(true);
	}
}
