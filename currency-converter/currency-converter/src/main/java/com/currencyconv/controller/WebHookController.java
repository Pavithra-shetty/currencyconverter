package com.currencyconv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.currencyconv.model.CurrencyConvRequest;
import com.currencyconv.service.CurrencyConverterService;

@RestController
@RequestMapping(value = "/webhook")
public class WebHookController {
	
	@Value("${verify.token}")
	private String VERIFY_TOKEN;
	
	@Value("${mode}")
	private String MODE;
	
	@Autowired
	private CurrencyConverterService currConvService;
	
	@GetMapping
	public ResponseEntity<String> verifyWebhook(@RequestParam("hub.mode") final String mode,
	                                        @RequestParam("hub.verify_token") final String verifyToken,
	                                        @RequestParam("hub.challenge") final String challenge) {
		if(mode.equals(MODE) && VERIFY_TOKEN.equals(verifyToken)) {
			return ResponseEntity.ok(challenge);
		}
		else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error");
		}
	    
	}
	
	@PostMapping
	public Double convertCurrency(@RequestBody CurrencyConvRequest request) {
		return currConvService.convertCurrency(request);
	}
	
	
}
