package DAROARA.Saturday.Application.Controller;

import DAROARA.Saturday.Application.Model.RunResult;
import DAROARA.Saturday.Application.Service.SaturdayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/run")
public class SaturdayController {

    @Autowired
    private SaturdayService saturdayService;

    @PostMapping
    public RunResult runCode(@RequestBody String code) {
        return saturdayService.execute(code);
    }
}
