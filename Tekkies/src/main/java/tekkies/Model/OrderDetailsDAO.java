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

public class OrderDetailsDAO {

private static final String TABLE_NAME = "orderdetails";
	
	private static DataSource ds;
	static Logger logger = Logger.getLogger(OrderDetailsDAO.class.getName());
	
	//Inizializzazione DataSource
	static {
		try {
			//Contesto iniziale JNDI
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");

			//LookUp DataSource
			ds = (DataSource) envCtx.lookup("jdbc/tekkies");

		} catch (NamingException e) {
			OrderDetailsDAO.logger.log(Level.WARNING, "Errore DataSource");
		}
	}
	
	public synchronized Collection<OrderDetails> doRetrieveByOrder(int orderId) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection<OrderDetails> collection = new ArrayList<>(); 
		
		String sql = "SELECT * FROM " + OrderDetailsDAO.TABLE_NAME + " WHERE order_id = ?";
		
		
		try {
			connection = ds.getConnection(); 
			ps = connection.prepareStatement(sql);
			ps.setInt(1, orderId);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				OrderDetails bean = new OrderDetails();
				
				bean.setOrderID(rs.getInt("order_id"));
				bean.setSneakerID(rs.getInt("sneaker_id"));
				bean.setQty(rs.getInt("qty"));
				bean.setPrice(rs.getBigDecimal("price"));
				
				collection.add(bean);
			}		
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
		
		return collection;
	}
	
	public synchronized void doSave(OrderDetails bean) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "INSERT INTO " + OrderDetailsDAO.TABLE_NAME + "(order_id, sneaker_id, qty, price) VALUES (?, ?, ?, ?) ";
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			
			ps = connection.prepareStatement(sql);
			ps.setInt(1, bean.getOrderID());
			ps.setInt(2, bean.getSneakerID());
			ps.setInt(3, bean.getQty());
			ps.setBigDecimal(4, bean.getPrice());
			
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
