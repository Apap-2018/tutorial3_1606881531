package com.apap.tutorial3.controller;

import com.apap.tutorial3.model.PilotModel;
import com.apap.tutorial3.service.PilotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PilotController {
    @Autowired
    private PilotService pilotService;

    //add hanya ke model (ram), bukan database (local)
    @RequestMapping("/pilot/add")
    public String add(@RequestParam(value = "id", required = true) String id,
                      @RequestParam(value = "licenseNumber", required = true) String licenseNumber,
                      @RequestParam(value = "name", required = true) String name,
                      @RequestParam(value = "flyHour", required = true) int flyHour) {

        pilotService.addPilot(new PilotModel(id, licenseNumber, name, flyHour));

        return "add"; //add.html
    }

    //hanya akses database saja
    @RequestMapping("/pilot/view")
    public String view(@RequestParam("licenseNumber") String licenseNumber, Model model) {
        PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
        model.addAttribute("pilot", archive);
        return "view-pilot";
    }

    //view semua
    @RequestMapping("/pilot/viewall")
    public String viewAll(Model model) {
        List<PilotModel> archives = pilotService.getPilotList();
        model.addAttribute("pilotList", archives);
        return "viewall-pilot";
    }

    //View pilot model by its license number
    @RequestMapping(value = {"/pilot/view/license-number/{licenseNumber}"})
    public String viewLicense(@PathVariable("licenseNumber") String ln, Model model) {
        PilotModel sameLicenseNumber = pilotService.getPilotDetailByLicenseNumber(ln);
        if (sameLicenseNumber == null){
            model.addAttribute("searchSuccess", false);
        } else {
            model.addAttribute("searchSuccess", true);
            model.addAttribute("pilot", sameLicenseNumber);
        }
        return "viewlicense-pilot";
    }

    @RequestMapping(value = {"/pilot/update/license-number/{licenseNumber}/fly-hour/{flyHour}"})
    public String updateFlyHours(@PathVariable("licenseNumber") String ln,
                              @PathVariable("flyHour") int fh, Model model) {
        //Di template ada updateSuccess dan pilot
        PilotModel sameLicenseNumber = pilotService.getPilotDetailByLicenseNumber(ln);
        if (sameLicenseNumber == null) {
            model.addAttribute("updateSuccess", false);
        } else {
            model.addAttribute("updateSuccess", true);
            sameLicenseNumber.setFlyHour(sameLicenseNumber.getFlyHour() + fh);
            model.addAttribute("pilot", sameLicenseNumber);
        }

        return "updateFlyHours";
    }

    @RequestMapping(value = {"/pilot/delete/id/{id}"})
    public String deleteID(@PathVariable("id") String id, Model model) {
        //ada attribute deleteSuccess
        PilotModel pm = null;
        List<PilotModel> pms = pilotService.getPilotList();

        for (int i = 0; i < pms.size(); i++) {
            PilotModel now = pms.get(i);
            if (now.getId().equals(id)) {
                pm = now;
                pms.remove(i);
                break;
            }
        }

        if (pm == null) {
            model.addAttribute("deleteSuccess", false);
        } else {
            model.addAttribute("deleteSuccess", true);
        }

        return "deleteID-pilot";
    }
}
