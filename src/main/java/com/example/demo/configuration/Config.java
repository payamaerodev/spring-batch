package com.example.demo.configuration;
//

import com.example.demo.mode.Student;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import java.io.File;

@Configuration
@AllArgsConstructor

public class Config {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private ItemWriter writer;


    @Autowired
    private ItemProcessor processor;

    //
    @Bean

    public Job secondJob() throws Exception {
        return jobBuilderFactory.get("firstJob")
                .incrementer(new RunIdIncrementer())
                .start(firstChunk())
                .build();
    }

    @Bean
    public Step firstChunk() throws Exception {
        System.out.println("first job is running...");
        return stepBuilderFactory.get("firstChunk")
                .<Student, Long>chunk(1).reader(reader()).writer(writer).build();
    }
//    @Bean
//    public FlatFileItemReader<Student> reader() {
//        BeanWrapperFieldSetMapper<Student> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
//        fieldSetMapper.setTargetType(Student.class);
//
//        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
//        lineTokenizer.setDelimiter(",");
//        lineTokenizer.setNames(new String[]{"firstName", "lastName"});
//
//        DefaultLineMapper<Student> lineMapper = new DefaultLineMapper<>();
//        lineMapper.setFieldSetMapper(fieldSetMapper);
//        lineMapper.setLineTokenizer(lineTokenizer);
//
//        FlatFileItemReader<Student> flatFileItemReader = new FlatFileItemReader<Student>();
//        flatFileItemReader.setName("personItemReader");
//        flatFileItemReader.setResource(new ClassPathResource("csv/persons.csv"));
//        flatFileItemReader.setLineMapper(lineMapper);
//        flatFileItemReader.setLinesToSkip(1);
//
//        return flatFileItemReader;
//    }


    @Bean
    public FlatFileItemReader<Student> reader() {

        FlatFileItemReader<Student> flatFileItemReader = new FlatFileItemReader();
        //1-location of csv file
        flatFileItemReader.setResource(new FileSystemResource(new File("C:\\Users\\payam\\Downloads\\batch\\src\\main\\resources\\students.csv")));

        //2-line Mapper
        flatFileItemReader.setLineMapper(new DefaultLineMapper<Student>() {
            {
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        // setDelimiter("|");
                        setNames("ID", "Firstname", "Lastname", "Email");
                    }
                });
                setFieldSetMapper(new BeanWrapperFieldSetMapper<Student>() {
                    {
                        setTargetType(Student.class);
                    }
                });
            }
        });

        flatFileItemReader.setLinesToSkip(1);
        return flatFileItemReader;
    }

}

