package webscraping;

import java.sql.Connection;

/**
 * Interface IDataBase to create any data base that
 * implements DB creation method,
 * tale creation method,
 * insert and select methods
 * This task download an image from a given url
 * into a given directory location
 * and uses insert method from a given database
 * @author Diego Berroterán
 */
public interface IDataBase {
    void createNewDatabase(String dir);
    void createNewTable(Connection conn);
    boolean insert(Object url, String imgDirLocal);
    Object select(String dirLocal);
    boolean ifNotExist(String dirLocal);
}
