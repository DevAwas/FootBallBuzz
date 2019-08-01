package footballBuzz.model;


import org.springframework.stereotype.Component;

@Component
public class Country {
	
	private  String country_id;
	private  String country_name;
	
	
	
	
	public Country() {
		super();
	}




	public String getCountry_id() {
		return country_id;
	}




	public void setCountry_id(String country_id) {
		this.country_id = country_id;
	}




	public String getCountry_name() {
		return country_name;
	}




	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}
	
	
	

}
