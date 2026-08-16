package com.Raushan.SpringBootCrud.service;

import com.Raushan.SpringBootCrud.ExceptionHandler.DuplicateResourceException;
import com.Raushan.SpringBootCrud.ExceptionHandler.ResourceNotFoundException;
import com.Raushan.SpringBootCrud.dto.CreateStudentRequestDto;
import com.Raushan.SpringBootCrud.dto.CreateStudentResponseDto;
import com.Raushan.SpringBootCrud.dto.UpdateStudentRequestDto;
import com.Raushan.SpringBootCrud.dto.UpdateStudentResponseDto;
import com.Raushan.SpringBootCrud.entity.Student;
import com.Raushan.SpringBootCrud.repository.StudentRepository;
import org.hibernate.ResourceClosedException;
import org.hibernate.sql.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public  StudentService(StudentRepository studentRepository)
    {
        this.studentRepository=studentRepository;
    }
    public CreateStudentResponseDto createStudent(CreateStudentRequestDto studentReqDto)
    {
         Student student=createMapToEntity(studentReqDto);
         if(emailExists(student))
         {
             throw new DuplicateResourceException("Student with email "+student.getEmail()+" already exists.");
         }
         Student studentResponse = studentRepository.save(student);
         return createMapToDto(studentResponse);
    }

    public Student getStudent(Long id)
    {
       return studentRepository.findByIdAndIsActiveIsTrue(id)
                .orElseThrow(
                        ()->new ResourceNotFoundException("Student with id "+id+" Not found"));
    }
    public List<Student> getAllStudent()
    {
        List<Student> studentRes = studentRepository.findByIsActiveIsTrue();
        return studentRes;
    }
    public UpdateStudentResponseDto updateStudent(Long id, UpdateStudentRequestDto stuReqDto)
    {
        Student studentRes =
                studentRepository.
                        findByIdAndIsActiveIsTrue(id).
                        orElseThrow(()->new ResourceNotFoundException("Student with id "+id+" Not found"));

        studentRes.setName(stuReqDto.getName());
        studentRes.setSubject(stuReqDto.getSubject());
        studentRes.setRollNo(stuReqDto.getRollNo());
        studentRes.setAge(stuReqDto.getAge());
        studentRes.setUpdatedAt(LocalDateTime.now());

       Student updatedStu=studentRepository.save(studentRes);
       return updateMapToDto(updatedStu);
    }

    public void deleteStudent(Long id)
    {
        Student studentToBeDeleted =  studentRepository.findById(id)
                .orElseThrow(()->
                        new ResourceNotFoundException("Student with id "+id+" Not found"));
         studentRepository.delete(studentToBeDeleted);
    }
    public void softDeleteStudent(Long id)
    {
        Student studentRes = studentRepository
                .findByIdAndIsActiveIsTrue(id).
                orElseThrow(()->new ResourceNotFoundException("Student with id "+id+" Not found"));

           studentRes.setActive(false);
           studentRepository.save(studentRes);
    }
    private Student createMapToEntity(CreateStudentRequestDto stuReqDto)
    {
        Student student = new Student();
        student.setName(stuReqDto.getName());
        student.setEmail(stuReqDto.getEmail());
        student.setAge(stuReqDto.getAge());
        student.setRollNo(stuReqDto.getRollNo());
        student.setSubject(stuReqDto.getSubject());
        student.setActive(true);
        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());
        student.setActive(true);
        return student;
    }
    private CreateStudentResponseDto createMapToDto(Student student)
    {
        CreateStudentResponseDto stuResdto=new CreateStudentResponseDto();
        stuResdto.setName(student.getName());
        stuResdto.setEmail(student.getEmail());
        stuResdto.setAge(student.getAge());
        stuResdto.setSubject(student.getSubject());
        stuResdto.setRollNo(student.getRollNo());
        stuResdto.setId(student.getId());
        stuResdto.setCreatedAt(student.getCreatedAt());
        stuResdto.setUpdatedAt(student.getUpdatedAt());
        stuResdto.setMessage("Student Created Successfully!");

        return stuResdto;
    }
    private UpdateStudentResponseDto updateMapToDto(Student student)
    {
        UpdateStudentResponseDto stuResdto=new UpdateStudentResponseDto();
        stuResdto.setName(student.getName());
        stuResdto.setEmail(student.getEmail());
        stuResdto.setAge(student.getAge());
        stuResdto.setSubject(student.getSubject());
        stuResdto.setRollNo(student.getRollNo());
        stuResdto.setId(student.getId());
        stuResdto.setUpdatedAt(student.getUpdatedAt());
        stuResdto.setMessage("Student Updated Successfully!");
        return stuResdto;
    }
    private  Boolean emailExists(Student student)
    {
        return studentRepository.existsByEmail(student.getEmail());
    }
}
