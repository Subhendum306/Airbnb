package com.airbnb.service;

import com.airbnb.dto.LoginDto;
import com.airbnb.dto.PropertyUserDto;
import com.airbnb.entity.PropertyUser;
import com.airbnb.repository.PropertyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    public UserService() {}
    @Autowired
    private PropertyUserRepository userRepository;
    @Autowired
    private JWTService jwtService;
    @Autowired
    public UserService(PropertyUserRepository userRepository, JWTService jwtService){
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }
    public UserService(PropertyUserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public PropertyUser addUser(PropertyUserDto propertyUserDto){
        PropertyUser user = new PropertyUser();
        user.setFirstName(propertyUserDto.getFirstName());
        user.setLastName(propertyUserDto.getLastName());
        user.setUsername(propertyUserDto.getUsername());
        user.setEmail(propertyUserDto.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode( propertyUserDto.getPassword()));

        user.setUserRole("ROLE_USER");
        PropertyUser savedUser = userRepository.save(user);
        return savedUser;
    }

    public PropertyUser addPropertyOwner(PropertyUserDto propertyUserDto){
        PropertyUser user = new PropertyUser();
        user.setFirstName(propertyUserDto.getFirstName());
        user.setLastName(propertyUserDto.getLastName());
        user.setUsername(propertyUserDto.getUsername());
        user.setEmail(propertyUserDto.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode( propertyUserDto.getPassword()));

        user.setUserRole("PropertyOwner");
        PropertyUser savedUser = userRepository.save(user);
        return savedUser;
    }
    public String verifyLogin(LoginDto loginDto) {
        Optional<PropertyUser> opUser = userRepository.findByUsername(loginDto.getUsername());
        if(opUser.isPresent()){
            PropertyUser propertyUser =opUser.get();
         if( BCrypt.checkpw(loginDto.getPassword(),propertyUser.getPassword())){
             return jwtService.generateToken(propertyUser);
         }
        }
        return null;
    }
}
