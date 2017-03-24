package init;

import javax.servlet.http.Part;

public class ApplicationService {
	public static final int RECORDS_PER_PAGE = 3;
	public static final String host = "127.0.0.1";
	public static final String USER = "sa";
	public static final String PASSWORD = "passw0rd";
	public static final String SYSTEM_NAME = "神鬼論壇";
	public static final String JNDI_DB_NAME = "java:comp/env/jdbc/BookDataSQLver";
	public static final int IMAGE_FILENAME_LENGTH = 10;
	public static final String DB_URL = "jdbc:sqlserver://" + ApplicationService.host + ":1433;databaseName=JSPDB";
	public static final String KEY = "KittySnoopyMicky"; // 16, 24, 32
	public static final String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

	public static String AdjustFileName(String FileName) {
		String fName = FileName.trim();
		if (fName.trim().length() > IMAGE_FILENAME_LENGTH) {
			String[] part = fName.split("\\.");
			int length = IMAGE_FILENAME_LENGTH - part[1].length() - 1;
			fName = part[0].substring(0, length) + "." + part[1];
		}
		return fName;
	}

	public static String getFileName(Part p) {
		for (String content : p.getHeader("Content-Disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				//return (content.split("=")[1]).replace("\"", "");
				return content.substring(content.indexOf("=")+1).trim().replace("\"", "");
			}
		}
		return null;
	}

	public static void main(String[] args) {
     
	}
}
