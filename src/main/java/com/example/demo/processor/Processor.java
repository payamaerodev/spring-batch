package com.example.demo.processor;

import com.example.demo.mode.Student;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class Processor implements ItemProcessor<Student, Student> {
    @Override
    public Student process(Student item) throws Exception {
        item.setLastname("payammesgarha");
        return item;
    }
}
