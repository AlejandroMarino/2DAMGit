package org.marino.server.spring.controller;

import org.marino.server.data.models.Participation;
import org.marino.server.domain.services.ServicesParticipation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/participations")
public class RestParticipationController {

    private final ServicesParticipation sP;

    public RestParticipationController(ServicesParticipation sP) {
        this.sP = sP;
    }

    @GetMapping("/of_receipt")
    public List<Participation> getAllOfReceipt(@RequestParam int receipt) {
        return sP.getAllOfReceipt(receipt);
    }

    @GetMapping
    public Participation get(@RequestParam int member, @RequestParam int receipt) {
        return sP.get(member, receipt);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Participation add(@RequestBody Participation participation) {
        return sP.add(participation);
    }
}
