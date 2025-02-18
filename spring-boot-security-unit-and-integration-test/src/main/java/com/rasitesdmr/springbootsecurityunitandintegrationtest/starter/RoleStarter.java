package com.rasitesdmr.springbootsecurityunitandintegrationtest.starter;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.domain.enums.RoleEnum;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.role.Role;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.role.repository.RoleRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class RoleStarter implements ApplicationRunner {

    private final RoleRepository roleRepository;

    public RoleStarter(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
       long roleCount = roleRepository.count();
       if (roleCount == 0){
           Role userRole = Role.builder().roleName(RoleEnum.ROLE_USER.name()).createdDate(new Date()).updatedDate(new Date()).build();
           roleRepository.save(userRole);

           Role adminRole = Role.builder().roleName(RoleEnum.ROLE_ADMIN.name()).createdDate(new Date()).updatedDate(new Date()).build();
           roleRepository.save(adminRole);
       }
    }
}
