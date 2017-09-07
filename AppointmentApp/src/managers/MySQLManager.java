package managers;

/**
 *
 * @author Zach
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import utility.Printer;

public class MySQLManager implements DataManager {

    private final String username = "root";
    private final String password = "";
    private Connection conn = null;
    
    @Override
    public void makeConnection() {
        try {
            Class.forName(MYSQLDRIVERNAME);
            this.conn = DriverManager.getConnection(MYSQLSERVER + "counselor", username, password);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MySQLManager.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Connection or Driver load failure", null, 0);
        }

    }//End of makeConnection

    @Override
    public boolean validateUser(String username, String password) {
        
        boolean status = false;
        String state = "SELECT `username`, `password`\n"
                + "FROM login";
        try {
            PreparedStatement query;
            query = this.conn.prepareStatement(state);
            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                String u = rs.getString(1);
                String p = rs.getString(2);
                if ((u.equals(username)) && (p.equals(password))) {
                    status = true;
                    return status;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySQLManager.class.getName()).log(Level.SEVERE, null, ex);
            return status;
        }
        return status;
    }

    @Override
    public Connection getConn() {
        return conn;
    }

    @Override
    public void setConn(Connection conn) {
        this.conn = conn;
    }
    
    @Override
    public Object runQuery(String query, int outputType) {
        // out: 1-return String; others-return ResultSet
        String result = "";
        ResultSet rs = null;

        if (this.conn == null) {
            JOptionPane.showMessageDialog(null, "Make a connection ...");
        } else {
            try {
                PreparedStatement sql = this.conn.prepareStatement(query);
                rs = sql.executeQuery();
                if (outputType != 1) {
                    return rs;
                } else {
                    result = Printer.resultSetToString(rs);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex, null, JOptionPane.ERROR_MESSAGE);
            }
        }//End of IF-ELSE

        return result;
    }// End of runQuery
    
    @Override
    public String runUpdate(String update) {

        String result = null;
        int updateCount = 0;

        if (this.conn == null) {
            JOptionPane.showMessageDialog(null, "Make a connection ...");
        } else {
            try {
                PreparedStatement sql = this.conn.prepareStatement(update);
                updateCount = sql.executeUpdate();
                result = "\n" + updateCount + " rows have been updated.\n";
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex, null, JOptionPane.ERROR_MESSAGE);
            }
        }//End of IF-ELSE

        return result;
    }// End of runUpdate
}
