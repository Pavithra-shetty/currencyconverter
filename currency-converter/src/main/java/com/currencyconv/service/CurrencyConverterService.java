package com.currencyconv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.currencyconv.model.CurrencyConvRequest;
import com.currencyconv.model.CurrencyRate;

@Service
public class CurrencyConverterService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	public Double convertCurrency(CurrencyConvRequest request) {
		ResponseEntity<CurrencyRate> rateResp = restTemplate
				.getForEntity("https://api.exchangeratesapi.io/latest?symbols="+request.getFromCurrency()+","+request.getToCurrency()+"&base="+request.getFromCurrency(), CurrencyRate.class);
		return request.getAmount() * rateResp.getBody().getRates().get(request.getToCurrency());

	}

}
