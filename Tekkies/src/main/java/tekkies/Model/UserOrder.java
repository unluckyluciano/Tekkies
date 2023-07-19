package tekkies.Model;

import java.math.BigDecimal;
import java.sql.Date;

public class UserOrder {
	
	private int order_id = 0;
	private int order_user_id = 0;
	private BigDecimal total = new BigDecimal(0);
	private Date order_date = new Date(0);
	private String shipping_address = "";
	private String order_status = "";
	
	public int getOrderID()
	{
		return order_id;
	}
	
	public void setOrderID(int order_id)
	{
		this.order_id = order_id;
	}
	
	public int getOrderUserID()
	{
		return order_user_id;
	}
	
	public void setOrderUserID(int order_user_id)
	{
		this.order_user_id = order_user_id;
	}
	
	public BigDecimal getTotal()
	{
		return total;
	}
	
	public void setTotal(BigDecimal total)
	{
		this.total = total;
	}
	
	public Date getOrderDate(){
		return order_date;
	}
	
	public void setOrderDate(Date order_date)
	{
		this.order_date = order_date;
	}
	
	public String getShippingAddress()
	{
		return shipping_address;
	}
	
	public void setShippingAddress(String shipping_address)
	{
		this.shipping_address = shipping_address;
	}
	
	public String getOrderStatus()
	{
		return order_status;
	}
	
	public void setOrderStatus(String order_status)
	{
		this.order_status = order_status;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserOrder other = (UserOrder) obj;
		if (order_id != other.order_id)
			return false;
		return order_user_id == other.order_user_id;
	}
	
	@Override
	public String toString() {
		return "Order [id=" + order_id + ", user ID=" + order_user_id + ", date=" + order_date
				+ "]";
	}
}
