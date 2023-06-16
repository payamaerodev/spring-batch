package com.example.demo.configuration;

import com.example.demo.mode.Student;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.annotation.EnableAsync;

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
    private ItemReader reader;
    @Autowired
    private ItemProcessor processor;

//    @Bean
//    public Job secondJob() {
//        return jobBuilderFactory.get("second Job")
//                .incrementer(new RunIdIncrementer())
////                .start(firstChunk())
//                .start(step1())
//                .build();
//    }


    @Bean
    public Step step1() {

        return stepBuilderFactory.get("step1").tasklet(firstTasklet()).build();
    }

    @Bean
    public Tasklet firstTasklet() {
        return (contribution, sessionStatus) -> {
            System.out.println("first tasklet  is ...");
            return RepeatStatus.FINISHED;
        };
    }

//
    @Bean
    public Job firstJob() {
        return jobBuilderFactory.get("firstJob")
               .incrementer(new RunIdIncrementer())
//                .start(firstChunk())
                .start(firstChunk())
                .build();
    }

    @Bean
    public Step firstChunk() {
        System.out.println("first job is running...");
        return stepBuilderFactory.get("firstChunk")
                .<Integer, Long>chunk(4).reader(reader).processor(processor).writer(writer).build();
    }


    @Bean
    public FlatFileItemReader<Student> flatFileItemReader()
    {

        FlatFileItemReader<Student> flatFileItemReader=new FlatFileItemReader();
        //1-location of csv file
        flatFileItemReader.setResource(new FileSystemResource(new File("C:\\Users\\Admin\\IdeaProjects\\spring\\5\\session21\\_03readers\\inputs\\students.csv")));

        //2-line Mapper
        flatFileItemReader.setLineMapper(new DefaultLineMapper<Student>(){
            {
                setLineTokenizer(new DelimitedLineTokenizer(){
                    {
                        // setDelimiter("|");
                        setNames("ID","First Name" ,"Last Name" , "Email");
                    }
                });
                setFieldSetMapper(new BeanWrapperFieldSetMapper<Student>(){
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
