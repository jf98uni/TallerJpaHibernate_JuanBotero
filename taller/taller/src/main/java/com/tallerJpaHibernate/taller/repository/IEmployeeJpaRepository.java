package com.tallerJpaHibernate.taller.repository;

import com.tallerJpaHibernate.taller.model.Employee;
import com.tallerJpaHibernate.taller.model.Project;
import com.tallerJpaHibernate.taller.model.Role;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IEmployeeJpaRepository extends JpaRepository<Employee,Long> {
    //select fields from employee where employeeid = "[param]"
    Employee findByEmployeeid(String employeeid);
    List<Employee> findByLastName(String lastName);
    List<Employee> findByRole(Role role);
}
