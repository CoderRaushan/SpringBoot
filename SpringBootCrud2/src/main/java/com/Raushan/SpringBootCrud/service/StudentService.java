package com.Raushan.SpringBootCrud.service;

import com.Raushan.SpringBootCrud.entity.Student;
import com.Raushan.SpringBootCrud.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public  StudentService(StudentRepository studentRepository)
    {
        this.studentRepository=studentRepository;
    }
    public Student createStudent(Student student)
    {
         student.setActive(true);
         Student studentResponse = studentRepository.save(student);
         return studentResponse;
    }

    public Student getStudent(Long id)
    {
      Optional<Student>studentRes = studentRepository.findByIdAndIsActiveIsTrue(id);
      if(studentRes.isPresent())
      {
          return studentRes.get();
      }else
      {
          return null;
      }
    }
    public List<Student> getAllStudent()
    {
        List<Student> studentRes = studentRepository.findByIsActiveIsTrue();
        return studentRes;
    }
    public List<Student> getAlInActiveStudent()
    {
        List<Student> studentRes = studentRepository.findByIsActiveIsFalse();
        return studentRes;
    }
    public Student updateStudent(Long id, Student student)
    {
        Optional<Student>studentRes = studentRepository.findByIdAndIsActiveIsTrue(id);
        if(studentRes.isEmpty())
        {
            return null;
        }
        Student studentToSave=studentRes.get();
        studentToSave.setName(student.getName());
        studentToSave.setEmail(student.getEmail());
        studentToSave.setSubject(student.getSubject());
        studentToSave.setRollNo(student.getRollNo());
        studentToSave.setAge(student.getAge());

       return  studentRepository.save(studentToSave);
    }

    public Boolean deleteStudent(Long id)
    {
        Boolean studentRes = studentRepository.existsById(id);
        if(!studentRes)
        {
            return false;
        }
         studentRepository.deleteById(id);
        return true;
    }
    public Boolean softDeleteStudent(Long id)
    {
        Optional<Student>studentRes = studentRepository.findByIdAndIsActiveIsTrue(id);
        if(studentRes.isPresent())
        {
           Student studentToDel = studentRes.get();
           studentToDel.setActive(false);
           studentRepository.save(studentToDel);
           return true;
        }
        return false;
    }
    public Boolean activeStudent(Long id)
    {
        Optional<Student>studentRes = studentRepository.findByIdAndIsActiveIsFalse(id);
        if(studentRes.isPresent())
        {
            Student studentToDel = studentRes.get();
            studentToDel.setActive(true);
            studentRepository.save(studentToDel);
            return true;
        }
        return false;
    }
}
