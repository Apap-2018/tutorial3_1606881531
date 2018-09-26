package com.apap.tutorial3.service;

import com.apap.tutorial3.model.PilotModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PilotInMemoryService implements PilotService {
    List<PilotModel> archivePilot;

    public PilotInMemoryService() {
        this.archivePilot = new ArrayList<>();
    }

    @Override
    public void addPilot(PilotModel pilot) {
        archivePilot.add(pilot);
    }

    @Override
    public List<PilotModel> getPilotList() {
        return archivePilot;
    }

    @Override
    public PilotModel getPilotDetailByLicenseNumber(String licenseNumber) {
        // Naive approach but effective so i'll let it be
        for (PilotModel p : archivePilot) {
            if (p.getLicenseNumber().equals(licenseNumber)) {
                return p;
            }
        }
        return null;
    }
}
