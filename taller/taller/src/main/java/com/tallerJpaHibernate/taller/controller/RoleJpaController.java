package com.tallerJpaHibernate.taller.controller;

import com.tallerJpaHibernate.taller.model.Role;
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
    public class RoleJpaController {
        @Autowired
        private IRoleJpaRepository repoRole;

        @GetMapping("/role")
        public ResponseEntity<List<Role>> getAllRoles(@RequestParam(required = false) String name) {
            try {
                List<Role> roles = new ArrayList<Role>();
                repoRole.findAll().forEach(roles::add);

                if (roles.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }

                return new ResponseEntity(roles, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @PostMapping("/role")
        public ResponseEntity<Role> createRole(@RequestBody Role role){

            try {
                Role _role1 = new Role(role.getName());
                _role1 = repoRole.save(_role1);
                repoRole.flush();
                return new ResponseEntity<>(_role1, HttpStatus.CREATED);
            }catch (Exception e){
                return new ResponseEntity<>(null,HttpStatus.EXPECTATION_FAILED);
            }
        }

        @DeleteMapping("/role/delete/{id}")
        public ResponseEntity<String> deleteRole(@PathVariable("id") Long id){
            try {
                repoRole.deleteById(id);
                    return new ResponseEntity<>("Tutorials DELETE!! ",HttpStatus.NO_CONTENT);
            }catch (Exception e){
                return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
            }
        }



}
