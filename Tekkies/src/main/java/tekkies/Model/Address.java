package tekkies.Model;

public class Address {
	
	private int address_id = 0;
	private int user_id = 0;
	private String cap = "";
	private String street = "";
	private String city = "";
	private String tel = "";
	private String country = "";
	
	public int getAddressID()
	{
		return address_id;
	}
	
	
	public void setAddressID(int address_id)
	{
		this.address_id = address_id;
	}
	
	public int getUserID()
	{
		return user_id;
	}
	
	
	public void setUserID(int user_id)
	{
		this.user_id = user_id;
	}
	
	public String getCap()
	{
		return cap;
	}
	
	
	public void setCap(String cap)
	{
		this.cap = cap;
	}
	
	public String getStreet()
	{
		return street;
	}
	
	
	public void setStreet(String street)
	{
		this.street = street;
	}
	
	public String getCity()
	{
		return city;
	}
	
	
	public void setCity(String city)
	{
		this.city = city;
	}
	
	public String getTel()
	{
		return tel;
	}
	
	
	public void setTel(String tel)
	{
		this.tel = tel;
	}
	
	public String getCountry()
	{
		return country;
	}
	
	
	public void setCountry(String country)
	{
		this.country = country;
	}
	
	@Override
	public String toString()
	{
		return  street + "," + cap + ", " + city + ", " + country
				+ ", Tel: " + tel;
	}
}
