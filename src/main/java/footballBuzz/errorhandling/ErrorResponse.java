package footballBuzz.errorhandling;


import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement(name = "error")
public class ErrorResponse
{
    public ErrorResponse(String message) {
        super();
        this.message = message;
       // this.details = details;
    }
 
    //General error message about nature of error
    private String message;
 
    //Specific errors in API request processing
  

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
 
   
}