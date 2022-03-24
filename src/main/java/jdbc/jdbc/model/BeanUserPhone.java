package jdbc.jdbc.model;

public class BeanUserPhone {
	
	private String name;
	private String numberPhone;
	private String email;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getNumberPhone() {
		return numberPhone;
	}
	
	public void setNumberPhone(String numberPhone) {
		this.numberPhone = numberPhone;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "BeanUserPhone [name=" + name + ", numberPhone=" + numberPhone + ", email=" + email + "]";
	}
	
	
}
