package footballBuzz.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import footballBuzz.configuration.BasicConfiguration;
import footballBuzz.model.Country;
import footballBuzz.model.League;
import footballBuzz.model.Standing;
import footballBuzz.model.Team;
import footballBuzz.service.FootballService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

@Service
public class FootballServiceImpl implements FootballService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FootballServiceImpl.class);

	private static final String COUNTRY_URI = "https://apiv2.apifootball.com/?action=get_countries&APIkey=";

	private static final String LEAGUES_URI = "https://apiv2.apifootball.com/?action=get_leagues&country_id=";

	private static final String STANDINGS_URI = "https://apiv2.apifootball.com/?action=get_standings&league_id=";

	private static final String TEAMS_URI = "https://apiv2.apifootball.com/?action=get_standings&league_id=";

	ConnectionProvider fixedPool = ConnectionProvider.fixed("fixedPool", 5, 5000);
	HttpClient httpClient = HttpClient.create(fixedPool);
	WebClient webClient = WebClient.builder().clientConnector(new ReactorClientHttpConnector(httpClient)).build();

	@Autowired
	private BasicConfiguration configuration;

	@Override
	public Flux<Country> findCountry(String name) {

		Flux<Country> flux = webClient.get().uri(COUNTRY_URI + configuration.getApikey())
				.accept(MediaType.APPLICATION_JSON).retrieve()

				.bodyToFlux(Country.class);

		return flux;

	}

	private Mono<String> getCountryIdfromCountryName(Flux<Country> filteredCountryDetails) {
		return filteredCountryDetails.map(x -> x.getCountry_id()).next();

	}

	@Override
	public Flux<League> findLeaguesByCountryId(String countryid) {

		

		Flux<Country> countryDetails = webClient.get().uri(COUNTRY_URI + configuration.getApikey())
				.accept(MediaType.APPLICATION_JSON).retrieve().onStatus(HttpStatus::isError, clientResponse -> {
					LOGGER.error("Error while calling endpoint {} with status code {}", clientResponse.statusCode());
					throw new RuntimeException("Error while calling  Country endpoint");
				}).bodyToFlux(Country.class);

		Mono<Map<String, String>> countryMap = countryDetails.collectMap(x -> {
			return x.getCountry_id();
		}, x -> {
			return x.getCountry_name();
		}

		);

		Mono<Map<String, String>> localCountryMap = countryMap.filter(entry -> entry.containsKey(countryid));
		Flux<League> leagueObject = localCountryMap.flatMapMany(entry -> getLeaguesForCountry(countryid));
		return leagueObject;
	}

	@Override
	public Flux<League> getLeaguesForCountry(String id) {
		Flux<League> flux = webClient.get().uri(LEAGUES_URI + id + "&APIkey=" + configuration.getApikey())
				.accept(MediaType.APPLICATION_JSON).retrieve()

				.bodyToFlux(League.class);

		return flux;
	}

	@Override
	public Flux<Team> findTeamsByLeagueName(String LeagueName, String countryid) {

		Flux<League> leagueDeatils = findLeaguesByCountryId(countryid);
		Mono<Map<String, String>> leagueMap = leagueDeatils.collectMap(x -> {
			return x.getLeague_id();
		}, x -> {
			return x.getLeague_name();
		}

		);

		Mono<Map<String, String>> localCountryMap = leagueMap.filter(entry -> entry.containsValue(LeagueName));
		Flux<Team> teamDetails = localCountryMap
				.flatMapMany(entry -> getTeamsByLeagueId(entry.keySet().iterator().next()));

		return teamDetails;
	}

	@Override
	public Flux<Team> getTeamsByLeagueId(String LeagueId) {

		Flux<Team> teamDetails = webClient.get().uri(TEAMS_URI + LeagueId + "&APIkey=" + configuration.getApikey())
				.accept(MediaType.APPLICATION_JSON).retrieve()

				.bodyToFlux(Team.class);

		return teamDetails;
	}

	@Override
	public Flux<Standing> getStandingsByLeagueId(String LeagueId) {

		Flux<Standing> standingDetails = webClient.get()
				.uri(STANDINGS_URI + LeagueId + "&APIkey=" + configuration.getApikey())
				.accept(MediaType.APPLICATION_JSON).retrieve().onStatus(HttpStatus::isError, clientResponse -> {
					LOGGER.error("Error while calling endpoint {} with status code {}", clientResponse.statusCode());
					throw new RuntimeException("Error while calling  standings endpoint");
				})

				.bodyToFlux(Standing.class);

		return standingDetails;
	}

	@Override
	public Flux<Standing> findStandingsByCLNames(String countryName, String leagueName) {
		Flux<Country> countryDetails = webClient.get().uri(COUNTRY_URI + configuration.getApikey())
				.accept(MediaType.APPLICATION_JSON).retrieve().onStatus(HttpStatus::isError, clientResponse -> {
					LOGGER.error("Error while calling endpoint {} with status code {}", clientResponse.statusCode());
					throw new RuntimeException("Error while calling  countries endpoint");
				}).bodyToFlux(Country.class);

		// filter by country name

		Flux<Country> filteredCountryDetails = countryDetails.filter(x -> x.getCountry_name().equals(countryName));
		Flux<League> leagueDetails = filteredCountryDetails.flatMap(x -> findLeaguesByCountryId(x.getCountry_id()));

		// filter by leagueName

		Flux<League> filteredLeagueDetails = leagueDetails.filter(x -> x.getLeague_name().equals(leagueName));
		Flux<Standing> standingDetails = filteredLeagueDetails
				.flatMap(entry -> getStandingsByLeagueId(entry.getLeague_id()));
		Mono<String> countryID = getCountryIdfromCountryName(filteredCountryDetails);

		Flux<Standing> standingDetailswithCid = standingDetails.flatMap(obj -> {
			return countryID.map(x -> {
				obj.setCountry_id(x);
				return obj;
			});
		});

		return standingDetailswithCid;
	}

	@Override
	public Flux<Standing> findStandingsByCLTNames(String countryName, String leagueName, String teamName) {
		Flux<Standing> standingDetCL = findStandingsByCLNames(countryName, leagueName);
		Flux<Standing> standingDetailsfilteredByTeam = standingDetCL.filter(x -> x.getTeam_name().equals(teamName));
		return standingDetailsfilteredByTeam;

	}

}
