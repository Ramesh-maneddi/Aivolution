package com.aivolution.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class AivolutionController {
	
	
	
	private String res = "success";
	private String msg = "message";
	private String defaultMsg = "Oops! Something went wrong.";
	
	
	@PostMapping("/api/sendSMS/{number}/{otp}")
	public Map<String, Object> sendSMS(@PathVariable String number, @PathVariable String otp)  {
		log.info("Entered into sendSMS method of TextLocalSMS");

		Map<String, Object> response = new HashMap<>();
		response.put(res, false);
		response.put(msg, defaultMsg);
		try {

			// Construct data
			String apiKey = "apikey=" + "NDU0NzZjMzM3OTQ0NTk3NzRlNjE1ODUwNDYzMjc4NGM=";
			String message = "&message=Hi there, thank you for sending your first test message from Textlocal. Get 20% off today with our code: "+otp+".";
			String sender = "&sender=" + "600010";
			String numbers = "&numbers=" + number;

			// Send data https://api.txtlocal.com/send/?
			// ==>https://api.textlocal.in/Dynamic/?
			HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
			String data = apiKey + numbers + message + sender;
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
			conn.getOutputStream().write(data.getBytes("UTF-8"));

			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null) {
				stringBuffer.append(line).append("\n");
			}

			ObjectMapper objectMapper = new ObjectMapper();

			response = objectMapper.readValue(stringBuffer.toString(), new TypeReference<Map<String, Object>>() {
			});
			response.put(res, true);
			rd.close();

		} catch (Exception e) {
			log.error("Error has occured in TextLocalSMS {}", e.getMessage());
		}
		log.info("TextLocalSMS method ended");
		return response;
	}
	
	

}
