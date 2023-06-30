package com.example.demo.configuration;

import com.example.demo.listener.OnException;
import com.example.demo.mode.Student;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import javax.sql.DataSource;
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
    @Qualifier("dataSource")
    private DataSource dataSource;


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

    @Autowired
    private OnException onException;

    @Bean
    public Step firstChunk() throws Exception {
        System.out.println("first job is running...");
        return stepBuilderFactory.get("firstChunk")
                .<Student, Student>chunk(1).reader(reader()).writer(jsonFileItemWriter())
                .faultTolerant()
                .skipPolicy(new AlwaysSkipItemSkipPolicy())
                .skip(FlatFileParseException.class)
//                .skipLimit(Integer.MAX_VALUE)
                .listener(onException)
                .build();
    }

//    @Bean
//    public ItemReaderAdapter<Student> itemReader() {
//        ItemReaderAdapter<Student> reader = new ItemReaderAdapter<Student>();
//
//        reader.setTargetObject(jobService1());
//        reader.setTargetMethod("getTest");
//
//        return reader;
//    }
//@Bean
//    public  JobService1 jobService1(){
//        return new JobService1();
//}
//    @Bean
//    public JobService jobService() {
//        return new JobService();
//    }

//    @Bean
//    public JsonItemReader<Student> jsonItemReader() {
//        return new JsonItemReaderBuilder<Student>()
//                .jsonObjectReader(new JacksonJsonObjectReader<>(Student.class))
//                .resource(new FileSystemResource(new File("C:\\Users\\payam\\Downloads\\batch\\src\\main\\resources\\students.json")))
//                .name("tradeJsonItemReader")
//                .build();
//    }
//
//    @Bean
//    public JsonItemReader<Student> jsonItemReader() {
//        return new JsonItemReaderBuilder<Student>()
//                .jsonObjectReader(new JacksonJsonObjectReader<>(Student.class))
//                .resource(new ClassPathResource("students.json"))
//                .name("studentJsonItemReader")
//                .build();
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

    @Bean
    public JsonFileItemWriter<Student> jsonFileItemWriter() {
        System.out.println("adf");
        return new JsonFileItemWriterBuilder<Student>()
                .jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
                .resource(new FileSystemResource(new File("E:\\students1.json")))
                .name("studentsFileItemWriter")
                .build();
    }


//    @Bean
//    public FlatFileItemReader<Student> reader() {
//        BeanWrapperFieldSetMapper<Student> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
//        fieldSetMapper.setTargetType(Student.class);
//
//        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
//        lineTokenizer.setDelimiter(",");
//        lineTokenizer.setNames(new String[]{"ID", "Firstname", "Lastname", "Email"});
//
//        DefaultLineMapper<Student> lineMapper = new DefaultLineMapper<>();
//        lineMapper.setFieldSetMapper(fieldSetMapper);
//        lineMapper.setLineTokenizer(lineTokenizer);
//
//        FlatFileItemReader<Student> flatFileItemReader = new FlatFileItemReader<Student>();
//        flatFileItemReader.setName("personItemReader");
//        flatFileItemReader.setResource(new ClassPathResource("students.csv"));
//        flatFileItemReader.setLineMapper(lineMapper);
//        flatFileItemReader.setLinesToSkip(1);
//
//        return flatFileItemReader;
//    }
}

