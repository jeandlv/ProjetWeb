/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
/**
 *
 * @author delevoyj
 */
public class AccompagnateurDAO extends AbstractDataBaseDAO {
    
    public AccompagnateurDAO(DataSource ds) {
        super(ds);
    }
    
    public List<String> getListEmail() {
        List<String> result = new ArrayList<String>();
        try (
	     Connection conn = getConn();
	     Statement st = conn.createStatement();
	     ) {
            ResultSet rs = st.executeQuery("SELECT mail FROM accompagnateur");
            while (rs.next()) {
                result.add(rs.getString("mail"));
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
	}
	return result;
    }
}
