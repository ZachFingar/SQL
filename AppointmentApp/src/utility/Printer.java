package utility;

import com.sun.javafx.webkit.Accessor;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Printer {

    public static String saveToFile(String data, String title) {
        // Save a string to a file
        String filename = null;
        String feedback = "";
        // Create a File Chooser component
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            filename = fileToSave.getAbsolutePath();
        }

        try {
            // Assume default encoding.
            FileWriter fw = new FileWriter(filename);

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bw = new BufferedWriter(fw);

            // Note that write() does not automatically
            // append a newline character.
            bw.write(title + "\n" + data);
            bw.newLine();

            // Always close files.
            bw.close();
            feedback = "Save as file: " + filename;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error writing to file '" + filename + "'");
            feedback = "Error writing to file '" + filename + "'";
        }
        return feedback;
    }

    public static String resultSetToString(ResultSet rs) {
        // Parse a resultset into a string
        String result = "";
        try {
            int ncol = rs.getMetaData().getColumnCount();// get table column count
            for (int i = 1; i <= ncol; i++) {// iterate from 1 to column count
                result += rs.getMetaData().getColumnName(i) + " ";// read each table column name
            }

            result += "\n";// append a new line to the result string

            while (rs.next()) {// when there is a row next, do the loop
                for (int i = 1; i <= ncol; i++) {
                    String currCol = rs.getString(i) + " ";// read the cell at position i and append a space to it.
                    result += currCol; // append the cell to the result string
                }
                result += "\n"; // create a new line
            }

            rs.last();// move the cursor to the last row.
            int nrow = rs.getRow();  // get row count
            result += "The total number of rows is " + nrow + "\n";// write row size into result
        } catch (SQLException ex) {
            Logger.getLogger(Accessor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}

