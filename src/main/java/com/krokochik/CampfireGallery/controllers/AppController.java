package com.krokochik.CampfireGallery.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.IntStream;

@Controller
public class AppController {

    long globalId, globalChangeID;
    String topNav = "topNav";
    final String[] img = {"https://sun9-32.userapi.com/c543101/v543101051/4e88e/ROU4N8eD22U.jpg",
            "https://img2.joyreactor.cc/pics/post/принтер-Комиксы-Мемы-смешные-картинки-697290.jpeg",
            "https://admem.ru/content/images/1390958305.jpg"};

    //get requests to main page
    @GetMapping("/")
    public String main(Model model, @RequestParam(name = "id", required = false) String id, @RequestParam(name = "changeId", required = false) String changeId) {
        //parse variables
        try
        {
            globalId = Long.parseLong(id);
        }
        catch (NumberFormatException ignored) {
            return "redirect:/?id=1";
        }

        try
        {
            globalChangeID = Long.parseLong(changeId);
            globalId += globalChangeID;
        }
        catch (NumberFormatException ignored){}

        //image indication begins from 1
        if (globalId < 1)
            return "redirect:/?id=1";

        //populating model variables
        IntStream.range(0, 11).forEach(i -> model.addAttribute("id" + i, globalId + ""));
        model.addAttribute("topNav", topNav);

        //show page
        if (id.equals(globalId + ""))
            return "main";
        else
            return "redirect:?id=" + globalId;
    }

    //get requests to extended main page
    @GetMapping("/extended")
    public String mainExtended(Model model, @RequestParam(name = "id", required = false) String id, @RequestParam(name = "changeId", required = false) String changeId) {
        try
        {
            globalId = Long.parseLong(id);
        }
        catch (NumberFormatException ignored)
        {
            return "redirect:/extended/?id=1";
        }

        try {
            globalChangeID = Long.parseLong(changeId);
            globalId += globalChangeID;
        }
        catch (NumberFormatException ignored){}

        if (globalId < 1)
            return "redirect:/extended/?id=1";

        //cycle for hyperlinks and images
        IntStream.range(0, 10).forEach(i -> model.addAttribute("id" + i, globalId + ""));
        IntStream.range(10, 16).forEach(i -> model.addAttribute("id" + i, globalId + (i - 10) + ""));
        model.addAttribute("topNav", topNav);

        if (id.equals(globalId + "")) {
            return "extendedMain";
        } else {
            return "redirect:/extended/?id=" + globalId;
        }
    }

    //post requests to
    //main page
    @PostMapping("/")
    public String mainPost(Model model, HttpServletRequest request) {
        //apply input only if numbers are entered
        if(request.getParameter("input").matches("\\d+"))
            return "redirect:?id=" + request.getParameter("input");
        return "redirect:/?id=" + globalId;
    }

    //and extended page
    @PostMapping("/extended")
    public String extendedMainPost(Model model, HttpServletRequest request) {
        if(request.getParameter("input").matches("\\d+"))
            return "redirect:extended/?id=" + request.getParameter("input");
        return "redirect:/extended/?id=" + globalId;
    }

    //switch <nav> elements state
    @GetMapping("/switchTopNav")
    public String switchGet(Model model, HttpServletRequest request) {
        switch (topNav) {
            //expand
            case "topNav" -> topNav = "topNav responsive";
            //collapse
            case "topNav responsive" -> topNav = "topNav";
        }

        model.addAttribute("topNav", topNav);
        return "redirect:?id=" + globalId;
    }

    //equals to /switchTopNav
    @GetMapping("/extended/switchTopNav")
    public String extendedSwitchGet(Model model, HttpServletRequest request) {
        switch (topNav) {
            case "topNav" -> topNav = "topNav responsive";
            case "topNav responsive" -> topNav = "topNav";
        }

        model.addAttribute("topNav", topNav);
        return "redirect:/extended/?id=" + globalId;
    }

    //printers page
    @GetMapping({"/galleryImageControllerFactorySolutionStrategyPrinter", "/strategyPrinter", "/Printer", "/printer"})
    public String strategyPrinter(Model model) {
        model.addAttribute("printerImg", img[(int) (Math.random() * 3)]);
        return "printer";
    }

}