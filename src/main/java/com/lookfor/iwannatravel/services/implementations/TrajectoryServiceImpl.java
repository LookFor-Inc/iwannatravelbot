package com.lookfor.iwannatravel.services.implementations;

import com.lookfor.iwannatravel.models.Trajectory;
import com.lookfor.iwannatravel.repositories.TrajectoryRepository;
import com.lookfor.iwannatravel.services.TrajectoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrajectoryServiceImpl implements TrajectoryService {
    private final TrajectoryRepository trajectoryRepository;

    @Override
    public void save(Trajectory trajectory) {
        trajectoryRepository.save(trajectory);
    }
}
