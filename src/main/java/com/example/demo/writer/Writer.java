package com.example.demo.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class Writer implements ItemWriter<Long> {
    @Override
    public void write(List items) throws Exception {
        items.stream().forEach(System.out::println);
    }
}
