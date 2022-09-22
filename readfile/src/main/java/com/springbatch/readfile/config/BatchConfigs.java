package com.springbatch.readfile.config;

//import java.util.List;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import com.springbatch.readfile.model.CardDetails;

@Configuration
@EnableBatchProcessing
public class BatchConfigs {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	CardDetailsProcessor cardDetailsProcessor;

	@Bean
	public Job readCSVFilesJob() {
		return jobBuilderFactory.get("readCSVFilesJob").incrementer(new RunIdIncrementer()).start(step1()).build();
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1").<CardDetails, CardDetails>chunk(5).reader(reader())
				.processor(processor()).writer(writer()).build();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public FlatFileItemReader<CardDetails> reader() {

		// Create reader instance
		FlatFileItemReader<CardDetails> reader = new FlatFileItemReader<CardDetails>();

		// Set input file location
		reader.setResource(new FileSystemResource("input/data.csv"));

		// Set number of lines to skips. Use it if file has header rows.
		reader.setLinesToSkip(1);

		// Configure how each line will be parsed and mapped to different values
		reader.setLineMapper(new DefaultLineMapper() {

			{
				// 5 columns in each row
				setLineTokenizer(new DelimitedLineTokenizer() {

					{
						setNames(new String[] { "customerName", "cardNumber", "expiryDate", "mobileNumber", "email" });
					}
				});

				// Set values in CardDetails class

				setFieldSetMapper(new BeanWrapperFieldSetMapper<CardDetails>() {

					{
						setTargetType(CardDetails.class);
					}
				});
			}
		});

		ExecutionContext value = new ExecutionContext();
		value.put("", "data.csv");
		return reader;
	}

	@Bean
	public FlatFileItemWriter<CardDetails> writer() {
		// Create writer instance
		FlatFileItemWriter<CardDetails> writer = new FlatFileItemWriter<>();

		// Set output file location
		writer.setResource(new FileSystemResource("output/output.csv"));

		// All job repetitions should "append" to same output file
		writer.setAppendAllowed(true);

		// Name field values sequence based on object properties
		writer.setLineAggregator(new DelimitedLineAggregator<CardDetails>() {
			{
				setDelimiter(",");
				setFieldExtractor(new BeanWrapperFieldExtractor<CardDetails>() {
					{
						setNames(new String[] { "customerName", "cardNumber", "expiryDate", "mobileNumber", "email" });
					}
				});
			}

//  @Bean
//  public ConsoleItemWriter<CardDetails> writer() 
//  {
//    return new ConsoleItemWriter<CardDetails>();
//  }
		});

		return writer;
	}

	@Bean
	@StepScope
	public CardDetailsProcessor processor() {

		// cardDetailsProcessor.setFileName(fileNames);

		return cardDetailsProcessor;
	}
}
