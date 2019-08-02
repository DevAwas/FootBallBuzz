package footballBuzz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import footballBuzz.errorhandling.ErrorResponse;
import footballBuzz.model.League;
import footballBuzz.model.Standing;
import footballBuzz.service.FootballService;
import reactor.core.publisher.Flux;


@RestController
@RequestMapping("/football")
public class FootballController {
	
	
    @Autowired
	private final FootballService FootballService;

	@Autowired
	public FootballController(FootballService FootballService) {
		this.FootballService = FootballService;
	}

	@GetMapping(path = "/{countryId}")
	public Flux<League> getCountry(@PathVariable String countryId) {
		return FootballService.findLeaguesByCountryId(countryId);
	}

	@GetMapping(path =  "/{countryName}/{leagueName}")
	public Flux<Standing> findStandingsByCountryandLeague(@PathVariable String countryName, @PathVariable String leagueName) {
		return FootballService.findStandingsByCLNames(countryName, leagueName);
	}
	
	@GetMapping(path= "/{countryName}/{leagueName}/{teamName}")
	public Flux<Standing> findStandingsByCountryLeagueandTeam(@PathVariable String countryName, @PathVariable String leagueName,
			@PathVariable(name = "teamName", required = false) String teamName) {
		return FootballService.findStandingsByCLTNames(countryName, leagueName, teamName);
	}
	
	
	// more exceptions can be added per use case in similar fashion
	@ExceptionHandler(Exception.class)
	  ResponseEntity<ErrorResponse> handleIllegalAccessException() {
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
	    return new ResponseEntity<ErrorResponse>((new ErrorResponse("Sorry for the inconvienince , Please try again later!")), headers, HttpStatus.NOT_FOUND);
	  }
	
	

	@GetMapping(path = "/leagues/{id}")
	public Flux<League> getLeagues(@PathVariable String id) {
		return FootballService.getLeaguesForCountry(id);
	}

}
