package com.tallerJpaHibernate.taller.controller;

import com.tallerJpaHibernate.taller.model.Employee;
import com.tallerJpaHibernate.taller.model.Project;
import com.tallerJpaHibernate.taller.model.Role;
import com.tallerJpaHibernate.taller.repository.IEmployeeJpaRepository;
import com.tallerJpaHibernate.taller.repository.IProjectJpaRepository;
import com.tallerJpaHibernate.taller.repository.IRoleJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")

public class EmployeeJpaController {
    @Autowired
    private IEmployeeJpaRepository repo;
    @Autowired
    private IRoleJpaRepository repoRole;
    @Autowired
    private IProjectJpaRepository repoProj;

    @GetMapping("/employee")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        try {
            List<Employee> employee = new ArrayList<Employee>();
            repo.findAll().forEach(employee :: add);

            if (employee.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity(employee, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // agrega un empleado a la base de datos
    @PostMapping("/employee")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee){

        try {
            Employee _employee1 = repo.save(new Employee(employee.getFirstName(), employee.getLastName(),
                    employee.getEmployeeid(),repoRole.save(new Role(employee.getRole().getName()))));




            return new ResponseEntity<>(_employee1, HttpStatus.CREATED);

        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/edit/employee/{id}")
    public ResponseEntity<Employee> updateEmployeeInfo(@PathVariable("id") long id, @RequestBody Employee employee) {
        Optional<Employee> employeeData = repo.findById(id);
// permite editar al empleado
        if (employeeData.isPresent()) {
            Employee _employee = employeeData.get();
            _employee.setFirstName(employee.getFirstName());
            _employee.setLastName(employee.getLastName());
            _employee.setRole(employee.getRole());
            _employee.setProjects(employee.getProjects());
            _employee.setEmployeeid(employee.getEmployeeid());
            return new ResponseEntity<>(repo.save(_employee), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/edit/employee/projects/{id}")
    public ResponseEntity<Employee> updateEmployeeProjects(@PathVariable("id") long id, @RequestBody Employee employee) {
        Optional<Employee> employeeData = repo.findById(id);


        // pone proyectos en el perfin del empleado y los guarda en la base de datos de proyectos

        if (employeeData.isPresent()) {

            Employee _employee = employeeData.get();

            _employee.setProjects(employee.getProjects());

            // comprueba si el proyecto ya esta en la base de proyectos

            if((repoProj.findAll().contains(_employee.getProjects()))==false) {

                _employee.getProjects().forEach(project -> repoProj.save(project));
            }

            return new ResponseEntity<>(repo.save(_employee), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //obtiene al empleado por id

    @GetMapping("/tutorials/{id}")
    public ResponseEntity<Employee> getTutorialById(@PathVariable("id") long id) {
        Optional<Employee> employeeData = repo.findById(id);

        if (employeeData.isPresent()) {
            return new ResponseEntity<>(employeeData.get(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @DeleteMapping("/employee/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") Long id){
        try {
            repo.deleteById(id);
            return new ResponseEntity<>("Tutorials DELETE!! ",HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}
