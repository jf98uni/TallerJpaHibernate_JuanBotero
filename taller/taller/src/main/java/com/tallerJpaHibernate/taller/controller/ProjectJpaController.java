package com.tallerJpaHibernate.taller.controller;


import com.tallerJpaHibernate.taller.model.Project;
import com.tallerJpaHibernate.taller.model.Role;
import com.tallerJpaHibernate.taller.repository.IProjectJpaRepository;
import com.tallerJpaHibernate.taller.repository.IRoleJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")

public class ProjectJpaController {

    @Autowired
    private IProjectJpaRepository repoProj;

    @GetMapping("/Project")
    public ResponseEntity<List<Project>> getAllProjects(@RequestParam(required = false) String name) {
        try {
            List<Project> projects = new ArrayList<Project>();
            repoProj.findAll().forEach(projects::add);

            if (projects.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity(projects, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/project")
    public ResponseEntity<Project> createProject(@RequestBody Project project){

        try {
            Project _project1 = new Project(project.getName());
            _project1 = repoProj.save(_project1);
            repoProj.flush();
            return new ResponseEntity<>(_project1, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping("/project/delete/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable("id") Long id){
        try {
            repoProj.deleteById(id);
            return new ResponseEntity<>("Tutorials DELETE!! ",HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}
