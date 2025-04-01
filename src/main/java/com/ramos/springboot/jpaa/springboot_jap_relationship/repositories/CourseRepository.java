package com.ramos.springboot.jpaa.springboot_jap_relationship.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ramos.springboot.jpaa.springboot_jap_relationship.entities.Course;

public interface CourseRepository extends CrudRepository<Course, Long>{
    @Query("select c from Course c left join fetch c.students where c.id=?1")
    Optional<Course> findOneWithStudents(Long id);
}
