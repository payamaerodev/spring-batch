package com.example.demo.processor;

import com.example.demo.mode.Student;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class Processor implements ItemProcessor<Student,String> {
    @Override
    public String process(Student item) throws Exception {
        return item.getId();
    }
}
