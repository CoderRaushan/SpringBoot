package com.Raushan.SpringBootCrud.repository;

import com.Raushan.SpringBootCrud.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByIdAndIsActiveIsTrue(Long id);//
    Optional<Student> findByIdAndIsActiveIsFalse(Long id);
    List<Student> findByIsActiveIsTrue();
    List<Student> findByIsActiveIsFalse();
}
