package com.ramos.springboot.jpaa.springboot_jap_relationship.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ramos.springboot.jpaa.springboot_jap_relationship.entities.Course;

public interface CourseRepository extends CrudRepository<Course, Long>{
    
}
