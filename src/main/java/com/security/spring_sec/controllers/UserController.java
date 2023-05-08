package com.security.spring_sec.controllers;

import com.security.spring_sec.dto.UserDto;
import com.security.spring_sec.model.User;
import com.security.spring_sec.service.UserServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserServices userServices;

    @Autowired
    public UserController(UserServices userService) {
        this.userServices = userService;
    }

    @GetMapping("/")
    public ResponseEntity<UserDto> getUser(Principal principal) {
        return new ResponseEntity<>(convertToUserDto(userServices.findByUsername(principal.getName())), HttpStatus.OK);
    }

    public UserDto convertToUserDto(User user) {

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(user, UserDto.class);

    }
}
