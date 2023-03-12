package ru.kata.spring.boot_security.demo;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import ru.kata.spring.boot_security.demo.model.Us;

import java.lang.reflect.Method;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication

public class SpringBootSecurityDemoApplication {
    private static final String GET_US_ENDPOINT_URL = "http://94.198.50.185:7081/api/users";
    private static final String POST_US_ENDPOINT_URL = "http://94.198.50.185:7081/api/users";
    private static final String PUT_US_ENDPOINT_URL = "http://localhost:8080/api/v1/employees";
    private static final String DELETE_US_ENDPOINT_URL = "http://localhost:8080/api/v1/employees/{id}";

    public static void main(String[] args) {

        SpringApplication.run(SpringBootSecurityDemoApplication.class, args);


        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        String sessionId = getSessionId(restTemplate);
        httpHeaders.add("cookie", sessionId);
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        Us us = new Us(3, "James", "Brown", 33);
        System.out.println(sessionId + " this SessionID");
        System.out.println("GET=======================");
        getUs(restTemplate, httpHeaders);
        System.out.println("POST=======================");
        createUs(us, restTemplate, httpHeaders);
        System.out.println("PUT=======================");
        us.setName("Thomas");
        us.setLastName("Shelby");
        updateUs(us, restTemplate, httpHeaders);
        System.out.println("DELETE=======================");
        deleteUs(us, restTemplate, httpHeaders);
    }

    /*public static ResponseEntity<?> setCookie(HttpServletResponse response) throws IOException {
        //в конструкторе указываем значения для name и value
        cookie.setPath("/");//устанавливаем путь
        cookie.setMaxAge(86400);//здесь устанавливается время жизни куки
        response.addCookie(cookie);//добавляем Cookie в запрос
        response.setContentType("text/plain");//устанавливаем контекст
        return ResponseEntity.ok().body(HttpStatus.OK);//получилось как бы два раза статус ответа установили, выбирайте какой вариант лучше
    }*/
    public static String getSessionId(RestTemplate restTemplate) {
        HttpHeaders httpHeaders = restTemplate.headForHeaders(GET_US_ENDPOINT_URL);
        return httpHeaders.get("Set-Cookie").get(0);
    }

    public static void getUs(RestTemplate restTemplate, HttpHeaders httpHeaders) {
        HttpEntity<String> httpEntity = new HttpEntity<>("parameters", httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(GET_US_ENDPOINT_URL, HttpMethod.GET, httpEntity, String.class);
        System.out.println(response.getBody());
    }

    private static void createUs(Us us, RestTemplate restTemplate, HttpHeaders httpHeaders) {
        HttpEntity<Us> httpEntity = new HttpEntity<>(us, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(POST_US_ENDPOINT_URL, HttpMethod.POST, httpEntity, String.class);
        ResponseEntity<String> response1 = restTemplate.exchange(POST_US_ENDPOINT_URL, HttpMethod.GET, httpEntity, String.class);
        System.out.println(response1.getBody());
    }

    private static void updateUs(Us us, RestTemplate restTemplate, HttpHeaders httpHeaders) {
        HttpEntity<Us> httpEntity = new HttpEntity<>(us, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(POST_US_ENDPOINT_URL, HttpMethod.PUT, httpEntity, String.class);
        ResponseEntity<String> response1 = restTemplate.exchange(GET_US_ENDPOINT_URL, HttpMethod.GET, httpEntity, String.class);
        System.out.println(response1.getBody());
    }

    private static void deleteUs(Us us, RestTemplate restTemplate, HttpHeaders httpHeaders) {
        HttpEntity<Us> httpEntity = new HttpEntity<>(us, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(POST_US_ENDPOINT_URL + "/" + us.getId(), HttpMethod.DELETE, httpEntity, String.class);
        ResponseEntity<String> response1 = restTemplate.exchange(POST_US_ENDPOINT_URL, HttpMethod.GET, httpEntity, String.class);
        System.out.println(response1.getBody());
    }
}
