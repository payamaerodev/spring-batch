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
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.awt.dnd.DragSource;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

@Configuration
@AllArgsConstructor

public class Config {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private ItemWriter writer;


//    @Autowired
//    private DataSource dataSource;


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
                .<Student, Long>chunk(1).reader(jsonItemReader()).writer(writer).build();
    }
//    @Bean
//    public Step firstChunk() throws Exception {
//        System.out.println("first job is running...");
//        return stepBuilderFactory.get("firstChunk")
//                .<Student, Long>chunk(1).reader(jdbcCursorItemReader()).writer(writer).build();
//    }

//    @Bean
//    public JdbcCursorItemReader<Student>  jdbcCursorItemReader() {
//        return new JdbcCursorItemReaderBuilder<Student>()
//                .dataSource(this.dataSource)
//                .name("creditReader")
//                .sql("select id, first_name as firstname , last_name as lastname,email from students")
//                .rowMapper(new RowMapper<Student>() {
//
//                    @Override
//                    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
//                        Student student = new Student();
//                        student.setId(rs.getString("id"));
//                        student.setFirstname(rs.getString("firstname"));
//                        student.setLastname(rs.getString("lastname"));
//                        student.setEmail(rs.getString("email"));
//                        return student;
//                    }
//                })
//                .build();
//
//    }
    @Bean
    public JsonItemReader<Student> jsonItemReader() {
        return new JsonItemReaderBuilder<Student>()
                .jsonObjectReader(new JacksonJsonObjectReader<>(Student.class))
                .resource(new FileSystemResource(new File("C:\\Users\\payam\\Downloads\\batch\\src\\main\\resources\\students.json")))
                .name("tradeJsonItemReader")
                .build();
    }
//
//    @Bean
//    public JsonItemReader<Student> jsonItemReader() {
//        return new JsonItemReaderBuilder<Student>()
//                .jsonObjectReader(new JacksonJsonObjectReader<>(Student.class))
//                .resource(new ClassPathResource("students.json"))
//                .name("studentJsonItemReader")
//                .build();
//    }



    //    @Bean
//    public FlatFileItemReader<Student> reader() {
//
//        FlatFileItemReader<Student> flatFileItemReader = new FlatFileItemReader();
//        //1-location of csv file
//        flatFileItemReader.setResource(new FileSystemResource(new File("C:\\Users\\payam\\Downloads\\batch\\src\\main\\resources\\students.csv")));
//
//        //2-line Mapper
//        flatFileItemReader.setLineMapper(new DefaultLineMapper<Student>() {
//            {
//                setLineTokenizer(new DelimitedLineTokenizer() {
//                    {
//                        // setDelimiter("|");
//                        setNames("ID", "Firstname", "Lastname", "Email");
//                    }
//                });
//                setFieldSetMapper(new BeanWrapperFieldSetMapper<Student>() {
//                    {
//                        setTargetType(Student.class);
//                    }
//                });
//            }
//        });
//
//        flatFileItemReader.setLinesToSkip(1);
//        return flatFileItemReader;
//    }
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

