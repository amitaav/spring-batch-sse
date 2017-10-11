package com.example.demobatch.config;
 
import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.example.demobatch.listener.CustomerReadListener;
import com.example.demobatch.listener.CustomerWriteListener;
import com.example.demobatch.listener.JobCompletionNotificationListener;
import com.example.demobatch.listener.ProductReadListener;
import com.example.demobatch.listener.ProductWriteListener;
import com.example.demobatch.model.Customer;
import com.example.demobatch.model.Product;

@Configuration
@EnableBatchProcessing
public class BatchConfig extends DefaultBatchConfigurer {
 
    @Autowired
    public JobBuilderFactory jobBuilderFactory;
 
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
 
    @Autowired
    CustomerReadListener customerReadListener;    
    
    @Autowired
    CustomerWriteListener customerWriteListener;  
      
    @Autowired
    ProductReadListener productReadListener;    
    
    @Autowired
    ProductWriteListener productWriteListener;  

    @Autowired
    FilterCustomerProcessor customerProcessor;

    @Autowired
    FilterProductProcessor productProcessor;

    @Autowired
    MongoTemplate mongoTemplate;
      
    @Override
    public void setDataSource(DataSource dataSource) {
        //This BatchConfigurer ignores any DataSource and uses in-memory one
    }
    
    @Bean
    public ItemReader<Customer> customerReader() {
        FlatFileItemReader<Customer> reader = new FlatFileItemReader<Customer>();
        reader.setResource(new ClassPathResource("customer.csv"));
        reader.setLinesToSkip(1);
        reader.setLineMapper(new DefaultLineMapper<Customer>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] {"email", "firstname", "lastname" });
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Customer>() {{
                setTargetType(Customer.class);
            }});
        }});
        return reader;
    }
    
    @Bean
    public ItemWriter<Customer> customerWriter() {
    	MongoItemWriter<Customer> writer = new MongoItemWriter<Customer>();
    	writer.setTemplate(mongoTemplate);
    	writer.setCollection("customer");
        return writer;
    }
    
    @Bean
    public ItemReader<Product> productReader() {
        FlatFileItemReader<Product> reader = new FlatFileItemReader<Product>();
        reader.setResource(new ClassPathResource("product.csv"));
        reader.setLinesToSkip(1);
        reader.setLineMapper(new DefaultLineMapper<Product>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] {"name", "description"});
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Product>() {{
                setTargetType(Product.class);
            }});
        }});
        return reader;
    }
    
    @Bean
    public ItemWriter<Product> productWriter() {
    	MongoItemWriter<Product> writer = new MongoItemWriter<Product>();
    	writer.setTemplate(mongoTemplate);
    	writer.setCollection("product");
        return writer;
    }
    
    public Job importUserJob(JobCompletionNotificationListener listener, String dataType) {
        return jobBuilderFactory.get(dataType+"job")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1(dataType))
                .end()
                .build();
    }
 
    public Step step1(String dataType) {
    	if(dataType.equals("customer")) {
    		
            return stepBuilderFactory.get("step1")
                    .<Customer, Customer> chunk(1)
                    .reader(customerReader())
                    .processor(customerProcessor)                
                    .writer(customerWriter())
            		.listener(customerReadListener)
            		.listener(customerWriteListener)
                    .build();    		
    	}

    	if(dataType.equals("product")) {
    		
            return stepBuilderFactory.get("step1")
                    .<Product, Product> chunk(1)
                    .reader(productReader())
                    .processor(productProcessor)                
                    .writer(productWriter())
            		.listener(productReadListener)
            		.listener(productWriteListener)
                    .build();    		
    	}
    	
    	return null;
    }

}
