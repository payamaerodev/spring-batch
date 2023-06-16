package com.example.demo.mode;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Student {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;
}
