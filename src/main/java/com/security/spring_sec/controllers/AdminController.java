package com.security.spring_sec.controllers;


import com.security.spring_sec.dto.UserDto;
import com.security.spring_sec.model.User;
import com.security.spring_sec.service.UserServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserServices userServices;

    @Autowired
    public AdminController(UserServices userService) {
        this.userServices = userService;
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>>  getUsers(){
        return new ResponseEntity<>(userServices.getListUser().stream().map(this::convertToUserDto).collect(Collectors.toList())
        , HttpStatus.OK) ;
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable ("id") Long id){
        return new ResponseEntity<>(convertToUserDto(userServices.getById(id)), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto, BindingResult result) {

        if(result.hasErrors()) {
            throw new IllegalArgumentException();
        }
        userServices.add(convertToUser(userDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> update(@RequestBody UserDto userDto, @PathVariable("id") Long id) {
        userServices.update(id,convertToUser(userDto));
        return new ResponseEntity<>(convertToUser(userDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> delete(@PathVariable ("id") Long id) {
        User user = userServices.getById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        userServices.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }



//
//    @PostMapping(value = "/create")
//    public String create(@ModelAttribute("users") User user, @RequestParam("role") List<String> values){
//        userServices.add(user, values);
//        return "redirect:/admin";
//    }
//
//    @PatchMapping("/{id}/edit")
//    public String update(@ModelAttribute("users") User user, @PathVariable("id") Long id) {
//        userServices.update(id, user);
//        return "redirect:/admin";
//    }
//
//    @DeleteMapping("/{id}/delete")
//    public String delete(@PathVariable("id") Long id) {
//        userServices.delete(id);
//        return "redirect:/admin";
//    }


    public User convertToUser(UserDto userDto) {

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(userDto, User.class);

    }

    public UserDto convertToUserDto(User user) {

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(user, UserDto.class);

    }


}
