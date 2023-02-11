package ejercicio;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BaseDatos {
	
	private static final String REPLACEMENT = "/";
	private static final String REGEX = "\\\\";
	private static final String SQLITE_DB_LOOT_DB = "/sqlite-db/loot.db";
	private static final String JDBC_SQLITE = "jdbc:sqlite:";
	
	public void createNewDatabase(String dir) {

        String url = JDBC_SQLITE + dir.replaceAll(REGEX, REPLACEMENT) +
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
        
        String sql = "CREATE TABLE IF NOT EXISTS warehouse (\n"
                + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
                + "	url text NOT NULL,\n"
                + "	directory text NOT NULL\n"
                + ");";
        
        try {
        	Statement stmt = conn.createStatement();
                
            stmt.execute(sql);
            
            conn.close();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

	public void insert(URL imgUrl,String imgDirLocal) {
		
		String url = JDBC_SQLITE +
				imgDirLocal.substring(0,23).replaceAll(REGEX, REPLACEMENT)+
				SQLITE_DB_LOOT_DB;
		
		String sql = "INSERT INTO warehouse (url,directory) "
				+ "VALUES ( '"
				+imgUrl+"','"
				+imgDirLocal
				+ "' )";
        
        try {
        	Connection conn = DriverManager.getConnection(url);
        	
        	PreparedStatement stmt = conn.prepareStatement(sql);
        	
        	stmt.execute();
            
            conn.close();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}

	public List<Image> select(String dirLocal) {
		
		String url = JDBC_SQLITE +
				dirLocal.substring(0,23).replaceAll(REGEX, REPLACEMENT)+
				SQLITE_DB_LOOT_DB;
		
		String sql = "SELECT id,url,directory FROM warehouse";
        
		List<Image> imgList = new ArrayList<Image>();
		
        try {
        	Connection conn = DriverManager.getConnection(url);
        	
        	PreparedStatement stmt = conn.prepareStatement(sql);

        	ResultSet rs = stmt.executeQuery();
        	
        	while (rs.next()) {
        		Image img = new Image();
        		img.setId(rs.getInt("id"));
        		img.setUrlImg(rs.getString("url"));
                img.setDirImg(rs.getString("directory"));
                imgList.add(img);
            }
        	
        	conn.close();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		
		return imgList;
	}

}
