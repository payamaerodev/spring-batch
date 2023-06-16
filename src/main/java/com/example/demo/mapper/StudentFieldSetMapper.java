package com.example.demo.mapper;

import com.example.demo.mode.Student;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;


public class StudentFieldSetMapper implements FieldSetMapper<Student> {
    @Override
    public Student mapFieldSet(FieldSet fieldSet) throws BindException {
        Student player = new Student();

        player.setId(fieldSet.readLong(0));
        player.setLastName(fieldSet.readString(1));
        player.setFirstName(fieldSet.readString(2));
        player.setFirstName(fieldSet.readString(3));

        return player;    }
}
