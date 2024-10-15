package org.marino.server.spring.controller;

import org.marino.server.data.models.Receipt;
import org.marino.server.domain.services.ServicesReceipt;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/receipts")
public class RestReceiptController {

    private final ServicesReceipt sR;

    public RestReceiptController(ServicesReceipt sR) {
        this.sR = sR;
    }

    @GetMapping("/of_group")
    public List<Receipt> getAllOfGroup(@RequestParam int group) {
        return sR.getAllOfGroup(group);
    }

    @GetMapping("/{id}")
    public  Receipt get(@PathVariable int id) {
        return sR.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Receipt add(@RequestBody Receipt receipt) {
        return sR.add(receipt);
    }
}
