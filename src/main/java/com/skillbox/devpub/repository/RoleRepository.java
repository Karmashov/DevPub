package com.skillbox.devpub.repository;

import com.skillbox.devpub.model.Role;
import com.skillbox.devpub.model.enumerated.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(ERole name);
}
