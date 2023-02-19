package webscraping;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class DataBaseSQLite
 * implements IDataBase
 * This creates a SQLite data base in
 * a given directory location
 * to store collected resources from
 * a given web page url
 * @author Diego Berroterán
 */
public class DataBaseSQLite implements IDataBase{
	
	private static final String APOSTROPHE = "'";
	private static final String REPLACEMENT = "/";
	private static final String SQLITE_DB_LOOT_DB = "/sqlite-db/warehouse.db";
	private static final String JDBC_SQLITE = "jdbc:sqlite:";
	
	public void createNewDatabase(String dir) {
		String url = JDBC_SQLITE + dir.replaceAll(AutoWebConstat.SLASH, REPLACEMENT) +
        		SQLITE_DB_LOOT_DB;
		
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
            	createNewTable(conn);
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
	public void createNewTable(Connection conn) {
        
        String sql = "CREATE TABLE IF NOT EXISTS T0001_IMAGE_LOOT (\n"
                + "	ID INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "	URL TEXT NOT NULL UNIQUE,\n"
                + "	DIRECTORY TEXT NOT NULL\n"
                + ");";
        
        try {
        	Statement stmt = conn.createStatement();
        	stmt.execute(sql);            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

	public boolean insert(Object urlImage,String imgDirLocal) {
		String url = JDBC_SQLITE +
				imgDirLocal.substring(0,25).replaceAll(AutoWebConstat.SLASH, REPLACEMENT)+
				SQLITE_DB_LOOT_DB;
		
		String sql = "INSERT INTO T0001_IMAGE_LOOT (URL,DIRECTORY) "
				+ "VALUES ( '"
				+urlImage+"','"
				+imgDirLocal
				+ "' )";
		
		try (Connection conn = DriverManager.getConnection(url)){
			PreparedStatement stmt = conn.prepareStatement(sql);
        	stmt.execute();
        	return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
	}

	public Object select(String dirLocal) {
		String url = JDBC_SQLITE +
				dirLocal.substring(0,25).replaceAll(AutoWebConstat.SLASH, REPLACEMENT)+
				SQLITE_DB_LOOT_DB;
		
		String sql = "SELECT " +
						"ID," +
						"URL," +
						"DIRECTORY" +
					" FROM T0001_IMAGE_LOOT";
        
		List<Image> imgList = new ArrayList<Image>();
		
        try (Connection conn = DriverManager.getConnection(url)){
        	PreparedStatement stmt = conn.prepareStatement(sql);
        	ResultSet rs = stmt.executeQuery();
        	
        	while (rs.next()) {
        		Image img = new Image();
        		img.setId(rs.getInt("ID"));
        		img.setUrlImg(rs.getString("URL"));
                img.setDirImg(rs.getString("DIRECTORY"));
                imgList.add(img);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }		
		return imgList;
	}
	
	public boolean ifNotExist(String imgDirLocal) {
		String url = JDBC_SQLITE +
				imgDirLocal.substring(0,25).replaceAll(AutoWebConstat.SLASH, REPLACEMENT)+
				SQLITE_DB_LOOT_DB;
		
		String sql = "SELECT " +
						"DIRECTORY" +
					" FROM T0001_IMAGE_LOOT"+
					" WHERE "+
					" DIRECTORY="+
					APOSTROPHE+imgDirLocal+APOSTROPHE;
        
		
		
        try (Connection conn = DriverManager.getConnection(url)){
        	PreparedStatement stmt = conn.prepareStatement(sql);
        	ResultSet rs = stmt.executeQuery();
        	return Objects.isNull(rs.getString("DIRECTORY"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return true;
        }
	}
}
