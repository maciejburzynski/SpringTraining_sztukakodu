package pl.maltoza.tasks.ConfTraining;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("qa")
@RestController
@RequestMapping("/qa")
public class QaController {

    @GetMapping()
    public String tasks() {
        return "Hello world from QA";
    }
}

