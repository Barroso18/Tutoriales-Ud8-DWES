package goya.daw2.ud8;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
@Configuration
@SpringBootApplication
public class ResTfulWebServiceApplication {
	
	private static final Logger log = LoggerFactory.getLogger(ResTfulWebServiceApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(ResTfulWebServiceApplication.class, args);
	}
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	/*
	Quote quote = restTemplate.getForObject(
			"http://localhost:8080/api/random", Quote.class);
	log.info(quote.toString());
			*/
	

	public void postMascotas(RestTemplate restTemplate) {
		HttpEntity<Pet> request = new HttpEntity<>(new Pet(6,"bird",6.99));
		Pet pet = restTemplate.postForObject("http://petstore-demo-endpoint.execute-api.com/petstore/pets", request, Pet.class);
		/*Assertions.assertNotNull(pet);
		Assertions.assertEquals(pet.type(), "bird");*/
		List<Pet> pets = restTemplate.getForObject(
				"http://petstore-demo-endpoint.execute-api.com/petstore/pets", List.class);
		
		
		log.info(pets.toString());
	}
	
	public void getDigimon(RestTemplate restTemplate) {
		Digimon digimon = restTemplate.getForObject(
				"https://digi-api.com/api/v1/digimon/1", Digimon.class);
		
		
		log.info(digimon.toString());	
	}
	public void getBromaAleatoria(RestTemplate restTemplate) {
		Joke joke = restTemplate.getForObject(
				"http://official-joke-api.appspot.com/random_joke", Joke.class);
		
		
		log.info(joke.toString());	
	}
	public void getBromaDiezAleatoria(RestTemplate restTemplate) {
		List<Joke> joke = restTemplate.getForObject(
				"http://official-joke-api.appspot.com/random_ten", List.class);
		
		
		log.info(joke.toString());	
	}
	
	// Crear una instancia de WebClient
	WebClient webClient = WebClient.create("https://api.example.com");

	// Realizar una solicitud GET y obtener una respuesta en forma de String
	String respuesta = webClient.get() //Prepara una solicitud GET.
	    .uri("/resource") //Especifica la URI relativa a la base URL.
	    .retrieve() //Inicia la ejecución de la solicitud y obtiene la respuesta.
	    .bodyToMono(String.class)  //Convierte el cuerpo de la respuesta a un tipo Mono (un contenedor reactiva) de tipo String
	    .block(); //Bloquea la ejecución hasta que se completa la operación reactiva y obtiene el resultado.

	// Realizar una solicitud POST con datos y obtener una respuesta en forma de objeto
	/*RespuestaDTO resultado = webClient.post() //Prepara una solicitud POST.
	    .uri("/resource")
	    .bodyValue(datos) //Adjunta datos al cuerpo de la solicitud.
	    .retrieve() 
	    .bodyToMono(RespuestaDTO.class)
	    .block();*/
	@Bean
	@Profile("!test")
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			postMascotas(restTemplate);
			getDigimon(restTemplate);
			getBromaAleatoria(restTemplate);
			getBromaDiezAleatoria(restTemplate);
		};
	}

}
