package tekkies.Model;

public class User {
	private int id = 0;
	private String email = "";
	private String psw = "";
	private String username = "";
	private Boolean isAdmin = false;
	
	public int getId()
	{
		return id;
	}
	public void setId(int id) 
	{
		this.id = id;
	}
	
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email) 
	{
		this.email = email;
	}
	
	public String getPsw() 
	{
		return psw;
	}
	public void setPw(String psw)
	{
		this.psw = psw;
	}
	
	public String getUsername() 
	{
		return username;
	}
	public void setUsername(String username) 
	{
		this.username = username;
	}
	
	public boolean getAdmin() 
	{
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin)
	{
		this.isAdmin = isAdmin;
	}
	
	@Override
	public String toString() 
	{
		return "User [id=" + id + ", email=" + email + ", psw=" + psw + ", username=" +
				 username + ", admin=" + isAdmin + "]";
	}
}
