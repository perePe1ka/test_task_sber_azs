package com.fuelup.azs.service;

import com.fuelup.azs.entity.Station;
import java.util.List;

public interface StationService {
    void loadAndSaveStations();
    List<Station> getAllStations();
    List<Station> getActiveStations();
}