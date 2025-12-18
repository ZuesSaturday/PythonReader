package DAROARA.Application.Controller;


import DAROARA.Application.Model.RunResult;
import DAROARA.Application.Service.SaturdayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class SaturdayController {

    @Autowired
    private SaturdayService saturdayService;

    @PostMapping("/run")
    public RunResult runCode(@RequestBody Map<String, String> payload) {
        String code = payload.getOrDefault("code", "");
        return saturdayService.execute(code);
    }
}
