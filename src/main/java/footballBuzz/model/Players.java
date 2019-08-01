package footballBuzz.model;

import org.springframework.stereotype.Component;

@Component
public class Players {
	
	String player_name;
	String player_key;
	String player_number;
	String player_country;
	String player_type;
	String player_age;
	
	public Players() {
		super();
		
	}
	
	
	public Players(String player_name, String player_key, String player_number, String player_country,
			String player_type, String player_age, String player_match_played, String player_goals,
			String player_yellow_cards, String player_red_cards, String team_name, String team_key) {
		super();
		this.player_name = player_name;
		this.player_key = player_key;
		this.player_number = player_number;
		this.player_country = player_country;
		this.player_type = player_type;
		this.player_age = player_age;
		this.player_match_played = player_match_played;
		this.player_goals = player_goals;
		this.player_yellow_cards = player_yellow_cards;
		this.player_red_cards = player_red_cards;
		this.team_name = team_name;
		this.team_key = team_key;
	}


	public String getPlayer_name() {
		return player_name;
	}
	public void setPlayer_name(String player_name) {
		this.player_name = player_name;
	}
	public String getPlayer_key() {
		return player_key;
	}
	public void setPlayer_key(String player_key) {
		this.player_key = player_key;
	}
	public String getPlayer_number() {
		return player_number;
	}
	public void setPlayer_number(String player_number) {
		this.player_number = player_number;
	}
	public String getPlayer_country() {
		return player_country;
	}
	public void setPlayer_country(String player_country) {
		this.player_country = player_country;
	}
	public String getPlayer_type() {
		return player_type;
	}
	public void setPlayer_type(String player_type) {
		this.player_type = player_type;
	}
	public String getPlayer_age() {
		return player_age;
	}
	public void setPlayer_age(String player_age) {
		this.player_age = player_age;
	}
	public String getPlayer_match_played() {
		return player_match_played;
	}
	public void setPlayer_match_played(String player_match_played) {
		this.player_match_played = player_match_played;
	}
	public String getPlayer_goals() {
		return player_goals;
	}
	public void setPlayer_goals(String player_goals) {
		this.player_goals = player_goals;
	}
	public String getPlayer_yellow_cards() {
		return player_yellow_cards;
	}
	public void setPlayer_yellow_cards(String player_yellow_cards) {
		this.player_yellow_cards = player_yellow_cards;
	}
	public String getPlayer_red_cards() {
		return player_red_cards;
	}
	public void setPlayer_red_cards(String player_red_cards) {
		this.player_red_cards = player_red_cards;
	}
	public String getTeam_name() {
		return team_name;
	}
	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}
	public String getTeam_key() {
		return team_key;
	}
	public void setTeam_key(String team_key) {
		this.team_key = team_key;
	}
	String player_match_played;
	String player_goals;
	String player_yellow_cards;
	String player_red_cards;
	String team_name;
	String team_key;

}
