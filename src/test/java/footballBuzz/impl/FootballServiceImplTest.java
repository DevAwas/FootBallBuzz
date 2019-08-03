package footballBuzz.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import footballBuzz.configuration.BasicConfiguration;
import footballBuzz.controller.FootballController;
import footballBuzz.model.Standing;
import footballBuzz.service.impl.FootballServiceImpl;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient

@SpringBootTest(classes = { FootballController.class, FootballServiceImpl.class, BasicConfiguration.class })
public class FootballServiceImplTest {

	@Autowired
	private FootballController fbController;

	@Test
	public void testFindStandingsByCountryandLeague() {

		String countryName = "Scotland";
		String leagueName = "Premiership";
		String teamName = "Hearts";

		Flux<Standing> standings = fbController.findStandingsByCountryLeagueandTeam(countryName, leagueName, teamName);

		StepVerifier.create(standings)
				.expectNextMatches(standing -> standing.getOverall_league_position().equalsIgnoreCase("1"))
				.expectComplete().verify();

	}

}
