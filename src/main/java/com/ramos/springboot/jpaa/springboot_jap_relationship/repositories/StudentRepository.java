package com.ramos.springboot.jpaa.springboot_jap_relationship.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ramos.springboot.jpaa.springboot_jap_relationship.entities.Student;

public interface StudentRepository extends CrudRepository<Student, Long>{
    
    @Query("select s from Student s left join fetch s.courses where s.id=?1")
    Optional<Student> findOneWithCourse(Long id);
}
