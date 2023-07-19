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


public class AddressDAO{
	
	private static final String TABLE_NAME = "address";
	private static final String SELECT_ALL = "SELECT * FROM address";
	
	private static DataSource ds;
	static Logger logger = Logger.getLogger(AddressDAO.class.getName());
	
	
	
	//DataSource
	static {
		try {
			//Contesto iniziale JNDI
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");

			//LookUp DataSource
			ds = (DataSource) envCtx.lookup("jdbc/tekkies");

		} catch (NamingException e) {
			AddressDAO.logger.log(Level.WARNING, "DataSource Error");
		}
	}
	
	//Funzione per recuperare i dati dal result set
	private Address getAddressFromRS(ResultSet rs) throws SQLException 
	{
		Address bean = new Address();
		bean.setAddressID(rs.getInt("address_id"));
		bean.setUserID(rs.getInt("user_id"));
		bean.setStreet(rs.getString("street"));
		bean.setCap(rs.getString("cap"));
		bean.setCity(rs.getString("city"));
		bean.setCountry(rs.getString("country"));
		bean.setTel(rs.getString("tel"));
		return bean;
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
	
	public Address doRetrieveByKey(int address_id) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = SELECT_ALL + " WHERE address_id = ?";
		ResultSet rs = null;
		Address bean = new Address();
		
		try 
		{
			connection = ds.getConnection(); 
			
			ps = connection.prepareStatement(sql);
			ps.setInt(1, address_id);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				bean = getAddressFromRS(rs);	
			}		
		}
		finally 
		{
			terminateQuery(ps, connection);
		}
		
		return bean;
	}
	
	public Collection<Address> doRetrieveAll(String order) throws SQLException {
		
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection<Address> collection = new ArrayList<>(); 
		
		String sql = SELECT_ALL;
		if(order!=null && !order.equals("")) {
			sql = sql + " ORDER BY " + order;
		}
		
		try {
			connection = ds.getConnection(); 
			ps = connection.prepareStatement(sql);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				Address bean = getAddressFromRS(rs);
				
				collection.add(bean);
			}		
		}
		finally {
			terminateQuery(ps, connection);
		}
		
		return collection;
	}
	
	public Collection<Address> doRetrieveByUser(int user_id) throws SQLException {
		
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection<Address> collection = new ArrayList<>(); 
		
		String sql = SELECT_ALL + " WHERE (user_id=?)";
		
		try {
			connection = ds.getConnection(); 
			ps = connection.prepareStatement(sql);
			ps.setInt(1, user_id);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				Address bean = getAddressFromRS(rs);
				
				collection.add(bean);
			}		
		}
		finally {
			terminateQuery(ps, connection);
		}
		
		return collection;
	}
	
	public void doSave(Address bean) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "INSERT INTO " + AddressDAO.TABLE_NAME + " (user_id, street, cap, city, country, tel)"
				+ " VALUES (?, ?, ?, ?, ?, ?)";
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);

			ps = connection.prepareStatement(sql);
			
			ps.setInt(1, bean.getUserID());
			ps.setString(2, bean.getStreet());
			ps.setString(3, bean.getCap());
			ps.setString(4, bean.getCity());
			ps.setString(5, bean.getCountry());
			ps.setString(6, bean.getTel());
			
			ps.executeUpdate();
			connection.commit();		
		}
		finally {
			terminateQuery(ps, connection);
		}
	}
	
	public boolean doDelete(int address_id) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "DELETE FROM " + AddressDAO.TABLE_NAME + " WHERE address_id = ?";
		int result = 0;
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			
			ps = connection.prepareStatement(sql);
			ps.setInt(1, address_id);
			
			result = ps.executeUpdate();
			connection.commit();		
		}
		finally {
			terminateQuery(ps, connection);
		}
		
		return (result!=0);
	}
	
}