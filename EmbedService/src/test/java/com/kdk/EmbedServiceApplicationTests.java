package com.kdk;

import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class EmbedServiceApplicationTests {
	
	@Autowired
	private TestRestTemplate template;
	
//	private final String testUrl = "https://www.tiktok.com/@tiktok_kr/video/7040001410276068610/";
//	private final String testUrl = "https://twitter.com/TwitterDev/status/1460323737035677698";
	private final String testUrl = "https://www.instagram.com/p/CYara9ephNJ/";

	@Test
    public void embedProcessTest() {
        ResponseEntity<Map> response = template.getForEntity("/api/socialEmbed?url=" + testUrl, Map.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().get("result")).isEqualTo("Success");
    }

}
