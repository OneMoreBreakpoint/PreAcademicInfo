package web_layer.controllers;

import bussiness_layer.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/app")
public class UserRestController {

    @Autowired
    private final IUserService userService;

    public UserRestController(IUserService userService) {
        this.userService = userService;
    }

    @PutMapping("/password")
    public ResponseEntity<?> putPassword(@RequestParam String crtPassword, @RequestParam String newPassword, Principal crtUser) {
        userService.updatePassword(crtUser.getName(), crtPassword, newPassword);
        return ResponseEntity.ok(null);
    }
}
