package utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;


public class Viewer {
    
    public static void viewDatabaseTable(Connection conn, String tableName, JTable target) throws SQLException{
        PreparedStatement query = conn.prepareStatement("select * from " + tableName);        
        ResultSet rs = query.executeQuery();
        target.setModel(RSTableModel.rsToTableModel(rs));        
    }
    
}
