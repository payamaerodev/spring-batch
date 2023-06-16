package com.example.demo.reader;

import com.example.demo.mapper.StudentFieldSetMapper;
import com.example.demo.mode.Student;
import org.springframework.batch.item.*;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Component
public class FirstReader  implements ItemReader<Student> {
    @Override
    public Student read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        FlatFileItemReader<Student> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("C:\\Users\\payam\\Downloads\\batch\\src\\main\\resources\\students.csv"));
        DefaultLineMapper<Student> lineMapper = new DefaultLineMapper<>();
//DelimitedLineTokenizer defaults to comma as its delimiter
        lineMapper.setLineTokenizer(new DelimitedLineTokenizer());
        lineMapper.setFieldSetMapper(new StudentFieldSetMapper());
        itemReader.setLineMapper(lineMapper);
        itemReader.open(new ExecutionContext());

        return null;
//        return itemReader.read();
    }
}
