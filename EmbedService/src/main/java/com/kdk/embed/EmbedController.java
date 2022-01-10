package com.kdk.embed;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmbedController {
	
	@Autowired
	EmbedService embedService;

	
	@GetMapping("/api/socialEmbed")
	public HttpEntity<Map<String,Object>> socialEmbed(@RequestParam(value = "url") String url){
		return embedService.callEmbedProcess(url);
	}
}
