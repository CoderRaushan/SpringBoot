package com.Raushan.SpringBootCrud.dto;

import jakarta.validation.constraints.*;

public class CreateStudentRequestDto {
    @NotBlank(message = "Name cannot be null!")
    @Size(min = 2,max = 50, message = "Name length cannot be less than 2")
    private String name;
    @NotBlank(message = "Email cannot be blank")
    @Email( message = "Please Enter valid email")
    private String email;
    @Positive(message = "age cannot be negative")
    @NotNull(message = "age cannot be null")
    @Min(value = 18, message = "age must be greater than 18")
    private Integer age;
    @Positive(message = "rollNo cannot be negative")
    @NotNull(message = "rollNo cannot be null")
    private Integer rollNo;
    @NotBlank(message = "subject cannot be blank")
    private String subject;
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }


}
