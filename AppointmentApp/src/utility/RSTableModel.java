package utility;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class RSTableModel {
    public static TableModel rsToTableModel(ResultSet rs) {

        String[][] tableData = null;
        String[] colNames = null;
        
        try {
            ResultSetMetaData meta = rs.getMetaData();
            int ncol = meta.getColumnCount();

            // instantiate the String array colNames
            colNames = new String[ncol];

            // Store the column names in the colNames array
            for (int i = 0; i < ncol; i++) {
                //get a column name
                colNames[i] = meta.getColumnLabel(i + 1);
            }

            // Get number of rows
            rs.last();
            int nrow = rs.getRow();
            rs.first();
            //JOptionPane.showMessageDialog(null,nrow);

            // Create a 2D String array for the JTable data
            tableData = new String[nrow][ncol];
            // Store each record into the table data
            for (int row = 0; row < nrow; row++) {
                for (int col = 0; col < ncol; col++) {
                    tableData[row][col] = rs.getString(col + 1);
                }
                rs.next();
            }
            // Close the statement
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(RSTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return new DefaultTableModel(tableData, colNames);
    }     
    
    public static String tableToString(JTable table) {
        
        String result ="";
	
	for (int i=0;i<table.getColumnCount();i++){
	    result += table.getColumnName(i);
	    result += ",";
	}
	
        for (int i=0; i<table.getRowCount(); i++){
            result += "\n";
	    for (int j=0; j<table.getColumnCount(); j++){
		result +=  table.getModel().getValueAt(i, j);
                result += ",";                
            }
        }
        
        return result;
    }      
}
