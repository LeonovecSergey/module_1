package com.learn.javaadvanced.configuration;

import com.learn.javaadvanced.entity.EmployeeEntity;
import com.learn.javaadvanced.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CustomDataSourceConfigurationTest {

    @Autowired
    EmployeeRepository repository;

    @Test
    void saveUser() {

        var employee = new EmployeeEntity();
        employee.setName("John Doe");

        repository.save(employee);

        var savedEmployees = repository.findAll();

        assertThat(savedEmployees).hasSize(1);
        assertThat(savedEmployees.get(0).getName()).isEqualTo("John Doe");
    }
}
