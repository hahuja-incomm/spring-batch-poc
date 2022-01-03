package com.example.demo;

import java.net.MalformedURLException;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableBatchProcessing
public class BootifulJobApplication {

	public static void main(String[] args) {
		System.exit(
				SpringApplication.exit(
						SpringApplication.run(BootifulJobApplication.class, args)
				)
		);
	}
	
	@Bean
	@StepScope
	public Resource resource(@Value("#{jobParameters['fileName']}") String fileName) throws MalformedURLException {
		return new UrlResource(fileName);
	}

	@Bean
	public PersonItemProcessor processor() {
		return new PersonItemProcessor();
	}
	
	@Bean
	public TaskExecutor taskExecutor() {
		return new SimpleAsyncTaskExecutor("spring_batch");
	}

	@Bean
	public FlatFileItemReader<Person> itemReader(Resource resource)  {
		return new FlatFileItemReaderBuilder<Person>()
				.name("personItemReader")
				.resource(resource)
				.delimited()
				.delimiter("|")
				.names(new String[] { "ccNumber", "firstName", "lastName", "balance", "dueDate" })
				.fieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {
						{
							setTargetType(Person.class);
						}
					})
				
				.build();
	}

	@Bean
	public JdbcBatchItemWriter<Person> itemWriter(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<Person>()
				.dataSource(dataSource)
				.sql("INSERT INTO PEOPLE (cc_number,first_name, last_name,balance,due_date,card_status) VALUES (:ccNumber,:firstName, :lastName,:balance,:dueDate,:status)")
				.beanMapped()
				.build();
	}

	@Bean
	public Job job(JobBuilderFactory jobs, StepBuilderFactory steps,
				   DataSource dataSource, Resource resource) {
		return jobs.get("job")
				.start(steps.get("step")
						.<Person, Person>chunk(3)
						.reader(itemReader(resource))
						.processor(processor())
						.writer(itemWriter(dataSource))
						.taskExecutor(taskExecutor())
						.build())
				.build();
	}

}
