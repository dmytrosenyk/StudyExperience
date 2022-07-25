package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(StudentRepository repository, MongoTemplate mongoTemplate){
		return args->{
			Address address=new Address("England","London","NE9");
			String email="jahmed@gmail.com";
			Student student =new Student(
					"Jamila",
					"Ahmed",
					email,
					Gender.FEMALE,
					address,
					List.of("Computer"),
					BigDecimal.TEN,
					LocalDateTime.now()
			);
			//usingMongoTemplateAndQuery(repository, mongoTemplate, email, student);
			repository.findStudentByEmail(email).
					ifPresentOrElse(student1 -> {
						System.out.println(student +" already exits");
					},
					()-> {
						System.out.println("insert student "+ student);
						repository.insert(student);
					}
			);
		};
	}

	private void usingMongoTemplateAndQuery(StudentRepository repository, MongoTemplate mongoTemplate, String email, Student student) {
		Query query=new Query();
		query.addCriteria(Criteria.where("email").is(email));
		List<Student> students= mongoTemplate.find(query,Student.class);
		if (students.size()>1){
			throw new IllegalStateException("found many students with email "+ email);
		}
		if(students.isEmpty()){

		}else {

		}
	}
}
