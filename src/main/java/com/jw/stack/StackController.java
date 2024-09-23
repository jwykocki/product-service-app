package com.jw.stack;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StackController {

    private final StackService stackService;

    @PostMapping("/stack/{fileName}")
    public String updateStackWithFile(@PathVariable String fileName) {
        try {
            stackService.updateStackFromFile(fileName);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "success";
    }
}
