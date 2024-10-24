package org.marino.server.spring.controller;

import org.marino.server.data.models.Group;
import org.marino.server.domain.services.ServicesGroup;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class RestGroupController {

    private final ServicesGroup sG;

    public RestGroupController(ServicesGroup sG) {
        this.sG = sG;
    }

    @GetMapping()
    public List<Group> getAll() {
        return sG.getAll();
    }

    @GetMapping("/{id}")
    public Group get(@PathVariable int id) {
        return sG.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Group add(@RequestBody Group group) {
        return sG.add(group);
    }
}
