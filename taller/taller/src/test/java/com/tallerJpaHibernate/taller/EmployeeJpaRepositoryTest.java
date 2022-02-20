package com.tallerJpaHibernate.taller;

import com.tallerJpaHibernate.taller.model.Employee;
import com.tallerJpaHibernate.taller.model.Project;
import com.tallerJpaHibernate.taller.model.Role;
import com.tallerJpaHibernate.taller.repository.IEmployeeJpaRepository;
import com.tallerJpaHibernate.taller.repository.IProjectJpaRepository;
import com.tallerJpaHibernate.taller.repository.IRoleJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeJpaRepositoryTest {

    @Autowired
    private IEmployeeJpaRepository repo;
    @Autowired
    private IRoleJpaRepository repoRole;
    @Autowired
    private IProjectJpaRepository repoProj;

    @Test
    // prueba que guardar empleados en la Base de datos funcione
    public void saveEmployee(){

        Role admin = new Role("ROLE_ADMIN");
        Role dev = new Role("ROLE_DEV");

        admin = repoRole.save(admin);
        dev = repoRole.save(dev);

        Project proj1 = new Project("proj1");
        Project proj2 = new Project("proj2");
        Project proj3 = new Project("proj3");

        proj1 = repoProj.save(proj1);
        proj2 = repoProj.save(proj2);
        proj3 = repoProj.save(proj3);

        Employee john = new Employee("John","Smith","empl123",dev);
        Employee claire = new Employee("Claire","Simpson","empl124",admin);

        john.getProjects().add(proj1);
        john.getProjects().add(proj2);

        claire.getProjects().add(proj1);
        claire.getProjects().add(proj2);
        claire.getProjects().add(proj3);


        repo.save(john);
        repo.save(claire);

        // hace que los inserte de inmediato
        repo.flush();

        Employee empl124 = repo.findByEmployeeid("empl124");
        assertEquals("Claire",empl124.getFirstName());
        assertEquals(2,repo.findAll().size());
        assertEquals(admin,empl124.getRole());


    }

}
