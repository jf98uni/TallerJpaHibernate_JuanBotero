package com.tallerJpaHibernate.taller.repository;


import com.tallerJpaHibernate.taller.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleJpaRepository extends JpaRepository<Role,Long> {
}
