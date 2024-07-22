package com.melihinci.skeleton.repository;

import com.melihinci.skeleton.entity.DispatchLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DispatchLogRepository extends JpaRepository<DispatchLog, Long> {
}
