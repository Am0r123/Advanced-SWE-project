package com.example.demo.Controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.example.demo.models.timeline;
import com.example.demo.repostitories.timelinerepository;

@Controller
@RequestMapping("/")
public class TimelineControllers {

    @Autowired
    private timelinerepository timelinerepository;

    @GetMapping("")
    public ModelAndView home() {
        return new ModelAndView("home.html");
    }
    @GetMapping("/notification")
    public ModelAndView not() {
        return new ModelAndView("notification.html");
    }

    @GetMapping("/timeline")
    public ModelAndView timeline() {
        return new ModelAndView("timeline.html");
    }

    @GetMapping("/calendar")
    public ModelAndView calendar() {
        return new ModelAndView("calendar.html");
    }

    // @GetMapping("/timeline/list")
    // public ModelAndView timelines() {
    //     ModelAndView mav = new ModelAndView("listtimeline.html");
    //     List<timeline> timelines = timelinerepository.findAll();
    //     mav.addObject("timelines", timelines);
    //     return mav;
    // }

    // @GetMapping("/timeline/add")
    // public ModelAndView addcate() {
    //     ModelAndView mav = new ModelAndView("addtimeline.html");
    //     mav.addObject("timelines", timelinerepository.findAll());
    //     return mav;
    // }

    // @PostMapping("/timeline/save")
    // public String savetimeline(@ModelAttribute timeline timeline) {
    //     timelinerepository.save(timeline);
    //     return "redirect:/timeline/list";
    // }
}
