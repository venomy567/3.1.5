package com.security.spring_sec.service;
import com.security.spring_sec.model.Role;
import com.security.spring_sec.model.User;
import com.security.spring_sec.repositories.RoleRepo;
import com.security.spring_sec.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class UserServicesImp implements UserServices {

    private UserRepo userRepo;
    private RoleRepo roleRepo;
    private PasswordEncoder passwordEncoder;


    @Autowired
    public UserServicesImp (UserRepo userRepo, RoleRepo roleRepo){
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    @Autowired
    @Lazy
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    // тут нужен метод который превратит лист в набор ролей
    @Transactional
    @Override
    public void add(User user) {
//        user.setEnabled("1");
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        for (Role r: getListRoleAsSet(list)){
//            user.setRoles(Collections.singleton(r));
//        }

        userRepo.save(user);
    }

    @Transactional
    @Override
    public Set<Role> getListRoleAsSet(List<String> list){
        Set<Role> roles = new HashSet<>();
        for( String str: list) {
            Optional<Role> opRole = roleRepo.findById(Long.parseLong(str));
            roles.add(opRole.orElse(null));
        }
        return roles;
    }

    @Transactional
    @Override
    public List<User> getListUser() {
        return userRepo.findAll();
    }

    @Transactional
    @Override
    public List<Role> getAllRoles(){
        return roleRepo.findAll();
    }

    @Transactional
    @Override
    public User getById(Long id) {
        Optional<User> foundUser = userRepo.findById(id);
        return foundUser.orElse(null);
    }

    @Transactional
    @Override
    public void update(Long id, User user) {
        user.setId(id);
        user.setEnabled("1");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        userRepo.deleteById(id);
    }

    public User findByUsername(String username){
        return userRepo.findByUsername(username);
    }

    @Override

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("Net takogo polzovtelia");

        return user;
    }
}