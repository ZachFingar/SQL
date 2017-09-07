package managers;

import java.sql.Connection;


public interface DataManager {
    
    static final String MYSQLDRIVERNAME = "com.mysql.jdbc.Driver";
    static final String MYSQLSERVER = "jdbc:mysql://localhost:3306/";
    
    public void makeConnection();

    public boolean validateUser(String username, String password);
    
   
    public Connection getConn();
    
    public void setConn(Connection conn);
    
    public Object runQuery(String query, int outputType);
    public String runUpdate(String update);

}