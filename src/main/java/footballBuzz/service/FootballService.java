package footballBuzz.service;




import org.springframework.stereotype.Component;

import footballBuzz.model.Country;
import footballBuzz.model.League;
import footballBuzz.model.Standing;
import footballBuzz.model.Team;
import reactor.core.publisher.Flux;

@Component
public interface FootballService {

	public Flux<Country> findCountry(String name) ;

	public Flux<League> getLeaguesForCountry(String id);

	public Flux<League> findLeaguesByCountryId(String id);
	
	public Flux<Team> findTeamsByLeagueName(String LeagueName, String countryid);

	public Flux<Team> getTeamsByLeagueId(String LeagueId);

	public Flux<Standing> getStandingsByLeagueId(String LeagueId);

	public Flux<Standing> findStandingsByCLNames(String countryName, String leagueName);

	public Flux<Standing> findStandingsByCLTNames(String countryName, String leagueName, String teamName);
}
