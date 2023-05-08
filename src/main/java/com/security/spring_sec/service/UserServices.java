package com.security.spring_sec.service;
import com.security.spring_sec.model.Role;
import com.security.spring_sec.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Set;

public interface UserServices extends UserDetailsService {
    void add(User user);

    List<User> getListUser();

    User getById(Long id);

    void update(Long id, User user);

    void delete(Long id);

    User findByUsername(String username);

    public List<Role> getAllRoles();
    public Set<Role> getListRoleAsSet(List<String> list);
}
