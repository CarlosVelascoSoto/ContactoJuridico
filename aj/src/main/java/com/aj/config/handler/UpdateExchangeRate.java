package com.aj.config.handler;

import java.util.Date;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Configuration
@EnableScheduling
public class UpdateExchangeRate {
	
	/*
	@Autowired
	public AccessDbJAService daoJaService;
	*/
	
	//@Scheduled(cron = "5 * * * * ?")
	@Scheduled(cron = "0 0 5 * * ?")	
	public void demoServiceMethod() {
		
		/*
		List<Currency> lmonedas = daoJaService.		
		Banxico http = new Banxico();
		String responseBody = http.sendPost();
		String tipocambio = http.procesarTexto(responseBody, "USD");
		Exchangerate entity = new Exchangerate();
		int rowid = daoJaService.save(entity);
		*/
		System.out.println("Method executed at every 5 seconds. Current time is :: " + new Date());
	}
	
	
}
