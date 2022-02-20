package com.tallerJpaHibernate.taller.repository;

import com.tallerJpaHibernate.taller.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProjectJpaRepository extends JpaRepository<Project,Long> {
}
