package org.marino.server.spring.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.marino.server.data.models.Member;
import org.marino.server.domain.services.ServicesMember;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class RestMemberController {

    private final ServicesMember sM;

    public RestMemberController(ServicesMember sM) {
        this.sM = sM;
    }

    @GetMapping("/of_group")
    public List<Member> getAllOfGroup(@RequestParam int group) {
        return sM.getAllOfGroup(group);
    }

    @GetMapping("/{id}")
    public Member get(@PathVariable int id) {
        return sM.get(id);
    }

    @GetMapping("/{id}/balance")
    public Double getBalanceOfMember(@PathVariable int id) {
        return sM.getBalanceOfMember(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Member add(@RequestBody Member member) {
        return sM.add(member);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}/set_user")
    public void setUserToMember(@PathVariable int id, HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        sM.setUserToMember(id, email);
    }
}
