package tekkies.Model;

import java.sql.Date;

public class Payment {
	private int payment_id = 0;
	private int pay_user_id = 0;
	private String cvv = "";
	private String card_num = "";
	private String card_own = "";
	private Date exp_date = new Date(0);
	
	public int getPayID()
	{
		return payment_id;
	}
	
	
	public void setPayID(int payment_id)
	{
		this.payment_id = payment_id;
	}
	
	public int getPayUserID()
	{
		return pay_user_id;
	}
	
	
	public void setPayUserID(int pay_user_id)
	{
		this.pay_user_id = pay_user_id;
	}
	
	public String getCvv()
	{
		return cvv;
	}
	
	
	public void setCvv(String cvv)
	{
		this.cvv = cvv;
	}
	
	public String getNumber(boolean visible)
	{
		if(visible) 
		{
			return card_num;
		}
		else 
		{
			String lastFourChars = "";  
			if (card_num.length() > 4) 
			{
				lastFourChars = card_num.substring(card_num.length() - 4);
			} else {
				lastFourChars = card_num;
			}
			return "xxxx xxxx xxxx " + lastFourChars;
		}
	}
	
	public void setNumber(String card_num)
	{
		this.card_num = card_num;
	}
	
	public String getCardOwn()
	{
		return card_own;
	}
	
	
	public void setCardOwn(String card_own)
	{
		this.card_own = card_own;
	}
	
	public Date getExpDate(){
		return exp_date;
	}
	
	public void setExpDate(Date exp_date)
	{
		this.exp_date = exp_date;
	}
	
	@Override
	public String toString() 
	{
		
		return "Card Number: " + this.getNumber(false) + ", Owner: " + this.getCardOwn();
		
	}
}
