package tekkies.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class UserDAO {
	
	private static final String TABLE_NAME = "siteuser";
	private static final String SELECT_ALL = "SELECT * FROM siteuser";
	
	private static DataSource ds;
	static Logger logger = Logger.getLogger(UserDAO.class.getName());

	//Inizializzazione DataSource
	static {
		try {
			//Contesto iniziale JNDI
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");

			//LookUp DataSource
			ds = (DataSource) envCtx.lookup("jdbc/tekkies");

		} catch (NamingException e) {
			UserDAO.logger.log(Level.WARNING, "Errore DataSource");
		}
	}
	
	private void terminateQuery(PreparedStatement ps, Connection connection) throws SQLException {
		try {
			if(ps != null) {
				ps.close();
			}
		}
		finally {
			if(connection != null) {
				connection.close();
			}
		}
	}
	
	private User getUserFromRS(ResultSet rs) throws SQLException {
		User bean = new User();
		bean.setId(rs.getInt("id"));
		bean.setPsw(rs.getString("psw"));
		bean.setEmail(rs.getString("email"));
		bean.setUsername(rs.getString("username"));
		bean.setAdmin(rs.getBoolean("is_admin"));
		return bean;
	}
	
	public synchronized User doRetrieveByKey(int id) throws SQLException {
		User bean = new User();
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = SELECT_ALL + " WHERE id = ?";
		ResultSet rs = null;
		
		try {
			connection = ds.getConnection(); 
			
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				bean = getUserFromRS(rs);	
			}		
		}
		finally {
			terminateQuery(ps, connection);
		}	
		return bean;
	}
	
	public synchronized User doRetrieveByEmail(String email) throws SQLException {
		User bean = new User();
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = SELECT_ALL + " WHERE email = ?";
		ResultSet rs = null;
		
		try {
			connection = ds.getConnection(); 
			
			ps = connection.prepareStatement(sql);
			ps.setString(1, email);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				bean = getUserFromRS(rs);
				
			}		
		}
		finally {
			terminateQuery(ps, connection);
		}
		
		return bean;
	
		
	}
	
	public synchronized Collection<User> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection<User> collection = new ArrayList<>(); 
		
		String sql = SELECT_ALL;
		if(order!=null && !order.equals("")) {
			sql = sql + " ORDER BY " + order;
		}
		
		try {
			connection = ds.getConnection(); 
			ps = connection.prepareStatement(sql);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				User bean = getUserFromRS(rs);
				collection.add(bean);
			}		
		}
		finally {
			terminateQuery(ps, connection);
		}
		
		return collection;
	}
	
	public synchronized void doSave(User bean) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "INSERT INTO " + UserDAO.TABLE_NAME + " (email, psw, username, is_admin)"
				+ " VALUES (?, ?, ?, ?) ";
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);

			ps = connection.prepareStatement(sql);
			ps.setString(1, bean.getEmail());
			ps.setString(2, bean.getPsw());
			ps.setString(3, bean.getUsername());
			ps.setBoolean(4, bean.getAdmin());
			
			ps.executeUpdate();
			connection.commit();		
		}
		finally {
			terminateQuery(ps, connection);
		}
		
		
	}
	
	public synchronized boolean doDelete(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "DELETE FROM " + UserDAO.TABLE_NAME + " WHERE id = ?";
		int result = 0;
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			
			result = ps.executeUpdate();
			connection.commit();		
		}
		finally {
			terminateQuery(ps, connection);
		}
		
		return (result!=0);
	}
	
public synchronized void doUpdate(User bean) throws SQLException {
		
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "UPDATE "+ UserDAO.TABLE_NAME + " SET username = ?, email= ? , pw = ? WHERE id = ?";
		try {
			connection = ds.getConnection(); 
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(sql);
			ps.setString(1, bean.getUsername());
			ps.setString(2, bean.getEmail());
			ps.setString(3, bean.getPsw());
			ps.setInt(4, bean.getId());
			
			ps.executeUpdate();
			connection.commit();		
		}
		finally {
			try {
				if(ps != null) {
					ps.close();
				}
			}
			finally {
				if(connection != null) {
					connection.close();
				}
			}
		}
	}
}
