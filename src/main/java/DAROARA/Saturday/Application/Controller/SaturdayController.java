package DAROARA.Saturday.Application.Controller;

import DAROARA.Saturday.Application.Model.RunResult;
import DAROARA.Saturday.Application.Service.SaturdayService;
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
        String code = payload.get("code");
        return saturdayService.execute(code);
    }
}
