//package com.example.demo.reader;
//
//import com.example.demo.mode.Student;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
//import org.springframework.batch.item.file.mapping.DefaultLineMapper;
//import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.stereotype.Component;
//
//@Component
//public class FirstReader extends FlatFileItemReader<Student> {
//
//@Override
//public Student read() throws Exception {
//        DefaultLineMapper<Student> lineMapper = new DefaultLineMapper<>();
//
//        BeanWrapperFieldSetMapper<Student> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
//        fieldSetMapper.setTargetType(Student.class);
//
//        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
//        lineTokenizer.setDelimiter(",");
//        lineTokenizer.setNames(new String[]{"ID", "Firstname", "Lastname", "Email"});
//
//        lineMapper.setFieldSetMapper(fieldSetMapper);
//        lineMapper.setLineTokenizer(lineTokenizer);
//
//        FlatFileItemReader<Student> flatFileItemReader = new FlatFileItemReader<Student>();
//        flatFileItemReader.setName("personItemReader");
//        flatFileItemReader.setResource(new ClassPathResource("students.csv"));
//        flatFileItemReader.setLineMapper(lineMapper);
//        flatFileItemReader.setLinesToSkip(1);
//
//        return flatFileItemReader.read();
//    }
//
//}
