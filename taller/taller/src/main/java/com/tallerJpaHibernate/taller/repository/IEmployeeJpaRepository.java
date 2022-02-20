package com.tallerJpaHibernate.taller.repository;

import com.tallerJpaHibernate.taller.model.Employee;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeJpaRepository extends JpaRepository<Employee,Long> {
    //select fields from employee where employeeid = "[param]"
    Employee findByEmployeeid(String employeeid);
    
}
