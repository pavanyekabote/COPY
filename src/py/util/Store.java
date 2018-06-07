package py.util;

import java.io.File;

public class Store {

	public final static int SND_SUCCESS = 100;

	public final static int SND_FAIL = 120;

	public final static int MAX_DATA_LENGTH = 60000;

	public final static int F_REC_SUCCESS = 101;

	// Default PORT Address;
	public final static int PORT = 45286;

	// OS determine
	final static String OS = System.getProperty("os.name").toLowerCase();

	// Get Default location to store on Particular OS
	public final static String DEFAULT_LOCATION = getLocation();

	static String getLocation() {
		if (isUnix(OS)) {
			String dir = "/home/codemantra/" + "pavan" + "/PYShare";
			File f = new File(dir);
			if (!f.exists())
				f.mkdirs();
			return dir;
		}
		return "win";
	}

	private static boolean isUnix(String OS) {
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
	}

}
