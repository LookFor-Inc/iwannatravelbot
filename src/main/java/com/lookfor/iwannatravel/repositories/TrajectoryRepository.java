package com.lookfor.iwannatravel.repositories;

import com.lookfor.iwannatravel.models.Trajectory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrajectoryRepository extends JpaRepository<Trajectory, Long> {
}
