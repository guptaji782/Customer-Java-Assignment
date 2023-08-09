package com.assignment.testcustomer.controller;

import java.net.MalformedURLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import jakarta.servlet.http.HttpServletRequest;
//REST = representational state transfer
@Controller
public class CustomerController {
	
	public JsonObject sendReq(String url, String data) {
		RestTemplate template = new RestTemplate();
		JsonObject json = new JsonObject();
		try {
			String s = template.postForEntity(url, data ,String.class).getBody();
			JsonParser parser = new JsonParser();   
			json = (JsonObject) parser.parse(s);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return json;
	}
	
	private static String accessToken = null;
	@GetMapping("/home")
	public RedirectView home(HttpServletRequest request) throws MalformedURLException {
		HashMap<String, String> body = new  HashMap<>();
		body.put("login_id", request.getParameter("uname"));
		body.put("password", request.getParameter("psw"));
		Gson gson = new Gson();
		String string = gson.toJson(body);
		JsonObject json = sendReq("https://qa2.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp", string);
//		ModelAndView mv = new ModelAndView("/home");
//		mv.addObject("at", accessToken);
//		mv.setViewName("home");
		RedirectView rv = new RedirectView("/getAll", true);
		if(!json.isEmpty()){
			accessToken = json.get("access_token").getAsString();
		}
		return rv;
		
	}
	
	
	@GetMapping("/nw")
	public ModelAndView nw(HttpServletRequest request) throws MalformedURLException {
		ModelAndView mv = new ModelAndView();
		mv.addObject("at", accessToken);
		mv.setViewName("NewCustomer");
		
		return mv;
		
	}
	
	public List<CustomerBean> sendReq2(String url, String data,String cmd) {
		RestTemplate template = new RestTemplate();
		Gson gson = new Gson();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		builder.queryParam("cmd", cmd);
		List<CustomerBean> cb = null;
		try {
			String s = template.exchange(builder.toUriString(), HttpMethod.GET ,entity , String.class).getBody();
			cb = gson.fromJson(s, new TypeToken<List<CustomerBean>>() {});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cb;
	}
	
	@GetMapping("/getAll")
	public ModelAndView getAll(HttpServletRequest request) throws MalformedURLException {
		List<CustomerBean> json = sendReq2("https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp", "","get_customer_list"
				+ "");
		ModelAndView mv = new ModelAndView();
		mv.addObject("cb", json);
		mv.setViewName("list");
		
		return mv;
		
	}
	
	public String delM(String url, String uuid) {
		RestTemplate template = new RestTemplate();
		Gson gson = new Gson();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		builder.queryParam("cmd", "delete");
		builder.queryParam("uuid", "uuid");
		String cb = null;
		try {
			ResponseEntity<String> s = template.exchange(builder.toUriString(), HttpMethod.POST ,entity , String.class);
			cb = gson.fromJson(s.getBody(), new TypeToken<String>() {});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cb;
	}
	
	
	
	
	@GetMapping("/del")
	public RedirectView del(HttpServletRequest request) throws MalformedURLException {
		String uuid = request.getParameter("uuid");
		String json = delM("https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp", uuid);
		RedirectView rv = new RedirectView("/getAll", true);
//		ModelAndView mv = new ModelAndView();
//		mv.addObject("at", json);
//		mv.setViewName("list");
		return rv;
		
	}
	
	public String create(String url, CustomerBean bean,String cmd) {
		RestTemplate template = new RestTemplate();
		Gson gson = new Gson();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);
		HttpEntity<String> entity = new HttpEntity<>(gson.toJson(bean),  headers);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		builder.queryParam("cmd", cmd);
		
		String cb = null;
		try {
			ResponseEntity<String> s = template.exchange(builder.toUriString(), HttpMethod.POST ,entity , String.class);
			cb = s.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cb;
	}
	
	@PostMapping("/add")
	public RedirectView add(CustomerBean bean) throws MalformedURLException {
		String json = create("https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp", bean,"create");
		RedirectView rv = new RedirectView("/getAll", true);
		
		return rv;
		
	}
	
	public String create(String url, CustomerBean bean,String cmd,String uuid) {
		RestTemplate template = new RestTemplate();
		Gson gson = new Gson();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);
		HttpEntity<String> entity = new HttpEntity<>(gson.toJson(bean),  headers);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		builder.queryParam("cmd", cmd);
		builder.queryParam("uuid", uuid);
		String cb = null;
		try {
			ResponseEntity<String> s = template.exchange(builder.toUriString(), HttpMethod.POST ,entity , String.class);
			cb = s.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cb;
	}
	
	@GetMapping("/upd")
	public RedirectView upd(CustomerBean bean) throws MalformedURLException{
		HashMap<String, String> body = new  HashMap<>();
		String json = create("https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp",bean,"update","");
		
		
		RedirectView rv = new RedirectView("/getAll", true);
		
		return rv;
		
	}
}
