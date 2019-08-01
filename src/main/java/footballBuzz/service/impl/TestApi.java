package footballBuzz.service.impl;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import footballBuzz.model.Country;
import reactor.core.publisher.Flux;

public class TestApi {

	public static void main(String[] args) {
		WebClient webClient = WebClient.create("https://apiv2.apifootball.com/");
		
		Flux<Country> flux= webClient.get()
	            .uri("?action=get_countries&APIkey=9bb66184e0c8145384fd2cc0f7b914ada57b4e8fd2e4d6d586adcc27c257a978")
	            .accept(MediaType.APPLICATION_JSON)
	            .retrieve()
	            
	            .bodyToFlux(Country.class);
		
		flux.subscribe(x ->System.out.println(x.getCountry_id()));
	}

}
