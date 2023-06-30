package com.example.demo.listener;

import com.example.demo.mode.Student;
import org.springframework.batch.core.annotation.OnSkipInRead;
import org.springframework.batch.core.annotation.OnSkipInWrite;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import javax.websocket.OnError;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class OnException {

    @OnSkipInRead
    public void onException(Throwable throwable) throws IOException {
        System.out.println("student = ");
        FileWriter fileWriter = new FileWriter(new File("C:\\Users\\payam\\Downloads\\batch\\src\\main\\resources\\error.txt"));
        if (throwable instanceof FlatFileParseException) {
            fileWriter.write(((FlatFileParseException) throwable).getInput());
        }

    }
}
