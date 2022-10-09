package com.example.instapic.Facade;

import com.example.instapic.Dto.UsersDto;
import com.example.instapic.Entity.Users;
import org.springframework.stereotype.Component;

@Component
public class UserFacade {
    public UsersDto usersToUsersDto(Users users){
        UsersDto usersDto = new UsersDto();
        usersDto.setId(users.getId());
        usersDto.setPassword(users.getPassword());
        usersDto.setBio(users.getBio());
        usersDto.setName(users.getName());
        usersDto.setUsername(users.getUsername());
        usersDto.setEmail(users.getEmail());
        usersDto.setLastName(users.getLastName());
        return  usersDto;

    }
}
