package com.example.demo.mode;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Student {

@JsonProperty("ID")
    private String id;
    @JsonProperty("Firstname")

    private String firstname;
    @JsonProperty("Lastname")

    private String lastname;
    @JsonProperty("Email")

    private String email;


    public void setId(String id) {
        this.id = id;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
