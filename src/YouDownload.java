
import java.net.*;
import py.util.Store;
import java.io.*;

public class YouDownload {
	
	
	public YouDownload(String link) throws Exception
	{
		FileOutputStream fout ;
		URL url = new URL(link);
		int c,t=0;
		byte[] buf = new byte[Store.MAX_DATA_LENGTH];
		HttpURLConnection ucon = (HttpURLConnection)url.openConnection();
		ucon.setRequestMethod("GET");
		ucon.connect();
		InputStream in = ucon.getInputStream();
		fout = new FileOutputStream(new File("/root/Desktop/data.swf"));
		while((c = in.read(buf,0,Store.MAX_DATA_LENGTH))!= -1)
		{
			fout.write(buf, 0, c);
			t += c;
			System.out.println("Data:"+t);
		}
		fout.close();
	}

	public static void main(String args[])throws Exception
	{
		//new YouDownload("https://www.youtube.com/v/xLjsZr9KDuw?version=3&amp;autohide=1");
		new YouDownload("https://youtube.com/embed/wTphObvEH9w");
	}
}
