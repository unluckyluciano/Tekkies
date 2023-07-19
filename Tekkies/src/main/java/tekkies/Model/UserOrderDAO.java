package tekkies.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class UserOrderDAO {

	private static final String TABLE_NAME = "useroder";
	
	private static DataSource ds;
	static Logger logger = Logger.getLogger(UserOrderDAO.class.getName());
	static final String SELECT_ALL = "SELECT * FROM ";
	static final String USER_ID = "order_user_id";
	static final String SHIPPING_ADDRESS = "shipping_address";
	static final String ORDER_STATUS = "order_status";
	static final String TOTAL = "total";
	static final String ORDER_DATE = "order_date";
	
	//Inizializzazione DataSource
		static {
			try {
				//Contesto iniziale JNDI
				Context initCtx = new InitialContext();
				Context envCtx = (Context) initCtx.lookup("java:comp/env");

				//LookUp DataSource
				ds = (DataSource) envCtx.lookup("jdbc/tekkies");

			} catch (NamingException e) {
				UserOrderDAO.logger.log(Level.WARNING, "Errore DataSource");
			}
		}
		
		public synchronized UserOrder doRetrieveByKey(int order_id) throws SQLException {
			UserOrder bean = new UserOrder();
			Connection connection = null;
			PreparedStatement ps = null;
			String sql = SELECT_ALL + UserOrderDAO.TABLE_NAME + " WHERE order_id = ?";
			ResultSet rs = null;
			
			try {
				connection = ds.getConnection(); 
				
				ps = connection.prepareStatement(sql);
				ps.setInt(1, order_id);
				
				rs = ps.executeQuery();
				while(rs.next()) {
					bean.setOrderID(rs.getInt("order_id"));
					bean.setOrderUserID(rs.getInt(USER_ID));
					bean.setShippingAddress(rs.getString(SHIPPING_ADDRESS));
					bean.setOrderStatus(rs.getString(ORDER_STATUS));
					bean.setTotal(rs.getBigDecimal(TOTAL));
					bean.setOrderDate(rs.getDate(ORDER_DATE));
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
			
			return bean;
		}
		
		public synchronized Collection<UserOrder> doRetrieveAll(String order) throws SQLException {
			Connection connection = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			Collection<UserOrder> collection = new ArrayList<>(); 
			
			String sql = SELECT_ALL + UserOrderDAO.TABLE_NAME;
			if(order!=null && !order.equals("")) {
				sql = sql + " ORDER BY " + order;
			}
			
			try {
				connection = ds.getConnection(); 
				ps = connection.prepareStatement(sql);
				
				rs = ps.executeQuery();
				while(rs.next()) {
					UserOrder bean = new UserOrder();
					
					bean.setOrderID(rs.getInt("order_id"));
					bean.setOrderUserID(rs.getInt(USER_ID));
					bean.setShippingAddress(rs.getString(SHIPPING_ADDRESS));
					bean.setOrderStatus(rs.getString(ORDER_STATUS));
					bean.setTotal(rs.getBigDecimal(TOTAL));
					bean.setOrderDate(rs.getDate(ORDER_DATE));
					
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
		
		public synchronized Collection<UserOrder> doRetrieveByUser(int id) throws SQLException {
			Connection connection = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			Collection<UserOrder> collection = new ArrayList<>(); 
			
			String sql = SELECT_ALL + UserOrderDAO.TABLE_NAME + " WHERE order_user_id = ? ";
			
			
			try {
				connection = ds.getConnection(); 
				ps = connection.prepareStatement(sql);
				ps.setInt(1, id);
				rs = ps.executeQuery();
				while(rs.next()) {
					UserOrder bean = new UserOrder();
					
					bean.setOrderID(rs.getInt("order_id"));
					bean.setOrderUserID(rs.getInt(USER_ID));
					bean.setShippingAddress(rs.getString(SHIPPING_ADDRESS));
					bean.setOrderStatus(rs.getString(ORDER_STATUS));
					bean.setTotal(rs.getBigDecimal(TOTAL));
					bean.setOrderDate(rs.getDate(ORDER_DATE));
					
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
		
		public synchronized Collection<UserOrder> doRetrieveByDates(Date fromDate, Date toDate) throws SQLException {
			Connection connection = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			Collection<UserOrder> collection = new ArrayList<>(); 
			
			String sql = SELECT_ALL + UserOrderDAO.TABLE_NAME + " WHERE order_date>=? AND order_date<=?";
			
			
			try {
				connection = ds.getConnection(); 
				ps = connection.prepareStatement(sql);
				ps.setDate(1, fromDate);
				ps.setDate(2, toDate);
				rs = ps.executeQuery();
				while(rs.next()) {
					UserOrder bean = new UserOrder();
					
					bean.setOrderID(rs.getInt("order_id"));
					bean.setOrderUserID(rs.getInt(USER_ID));
					bean.setShippingAddress(rs.getString(SHIPPING_ADDRESS));
					bean.setOrderStatus(rs.getString(ORDER_STATUS));
					bean.setTotal(rs.getBigDecimal(TOTAL));
					bean.setOrderDate(rs.getDate(ORDER_DATE));
					
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
		
		public synchronized int doSaveReturnKey(UserOrder bean) throws SQLException{
			Connection connection = null;
			PreparedStatement ps = null;
			String sql = "INSERT INTO " + UserOrderDAO.TABLE_NAME + " (order_user_id, shipping_address, order_status, total, order_date) VALUES (?, ?, ?, ?, ?) ";
			int key = 0;
			
			try {
				connection = ds.getConnection(); 
				connection.setAutoCommit(false);
				
				ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, bean.getOrderUserID());
				ps.setString(2, bean.getShippingAddress());
				ps.setString(3, bean.getOrderStatus());
				ps.setBigDecimal(4, bean.getTotal());
				ps.setDate(5, bean.getOrderDate());
				
				ps.executeUpdate();
				connection.commit();
				
				ResultSet rs = ps.getGeneratedKeys();
				rs.next();
				key = rs.getInt(1);
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
			
			return key;
			
		}
}
