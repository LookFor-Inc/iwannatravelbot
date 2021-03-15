package com.lookfor.iwannatravel.services;

import com.lookfor.iwannatravel.models.Trajectory;

/**
 * Service interface for managing {@link com.lookfor.iwannatravel.models.Trajectory}
 */
public interface TrajectoryService {
    /**
     * Save Trajectory entity
     *
     * @param trajectory source entity
     */
    void save(Trajectory trajectory);
}
