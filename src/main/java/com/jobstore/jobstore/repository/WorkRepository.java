package com.jobstore.jobstore.repository;

import com.jobstore.jobstore.entity.Work;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkRepository extends JpaRepository<Work,Long> {
}
