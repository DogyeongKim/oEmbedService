package com.kdk.embed;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class EmbedService {
	
	@Value("#{config['instagramUrl']}")
	private String instagramURL;
	@Value("#{config['twitterUrl']}")
	private String twitterURL;
	@Value("#{config['tiktokUrl']}")
    private String tiktokURL;
	
	/*
	 * 파라미터로 받아온 URL이 어떤 sns의 URL인지 리턴
	 */
	private static String getDomain(String url) throws URISyntaxException{
		URI uri = new URI(url);
		String domain = uri.getHost();
		
		if(domain.startsWith("www.")) {
			domain = domain.substring(4);
		}
		
		if(domain.endsWith(".com")) {
			domain = domain.substring(0,domain.length()-4);
		}
		
		return domain;
	}
	
		
	
	/*
	 * 리턴된 sns에 맞게 embed 처리할 메서드 호출
	 */
	public HttpEntity<Map<String,Object>> callEmbedProcess(String url){
		Map<String,Object> response = new HashMap<>();
		String domain = "";
		
		try {
			domain = getDomain(url);
		}catch(URISyntaxException e) {
			e.printStackTrace();
			response.put("result", "Fail");
			response.put("response", "잘못된 URL입니다. 다시 입력바랍니다.");
			response.put("error",e);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		return getSnsHtml(url,domain);
	}
	
	
	private HttpEntity<Map<String,Object>> getSnsHtml(String url,String domain){
		Map<String,Object> resultEmbed = new HashMap<>();
		Map<String,Object> response = new HashMap<>();
		
		// embed를 지원하는 url인지 검증
		boolean isValidUrl = isValidUrl(url, domain);

		if(isValidUrl == false) {
			response.put("result", "Fail");
			response.put("response", "지원하지 않는 URL 형식입니다. 다시 입력바랍니다.");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		
		// 지원하는 url이면 api를 통해 html 받아오기
		RestTemplate template = new RestTemplate();
		
		if(domain.equals("instagram")) {
			resultEmbed = template.getForObject(instagramURL + url, Map.class);
		} else if(domain.equals("twitter")) {
			resultEmbed = template.getForObject(twitterURL + url, Map.class);
		} else{
			resultEmbed = template.getForObject(tiktokURL + url, Map.class);
		}

		response.put("result", "Success");
		response.put("response", resultEmbed.get("html"));
		
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	private boolean isValidUrl(String url,String domain) {
		boolean isValid = true;
		
		if(domain.equals("instagram")) {
			isValid = Pattern.compile("(https://www.instagram.com/p/.*?)").matcher(url).find();
		} else if(domain.equals("twitter")) {
			isValid = Pattern.compile("(https://twitter.com/.*/status/.*?)").matcher(url).find();
		} else if(domain.equals("tiktok")) {
			isValid = Pattern.compile("(https://www.tiktok.com/.*/video/.*?)").matcher(url).find();
		} else {
			isValid = false;
		}
		
		return isValid;
	}

	
}
