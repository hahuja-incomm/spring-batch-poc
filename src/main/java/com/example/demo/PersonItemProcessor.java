package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

public class PersonItemProcessor implements ItemProcessor<Person, Person> {

	private static final Logger log = LoggerFactory.getLogger(PersonItemProcessor.class);

	@Override
	public Person process(final Person person) throws Exception {
		String status ="";
		if(person.getBalance()> 499){
			 status = "yes";
		}else{
			 status = "no";
		}
		

		final Person transformedPerson = new Person(person.getCcNumber(),person.getFirstName(), person.getLastName(), 
													person.getBalance(), 
													person.getDueDate(), status);

		log.info("Converting (" + person + ") into (" + transformedPerson + ")");

		return transformedPerson;
	}

}
