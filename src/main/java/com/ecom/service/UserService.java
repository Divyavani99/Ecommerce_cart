package com.ecom.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecom.model.*;
import com.ecom.repository.*;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;
	
    
	@Autowired
	private PasswordEncoder passwordEncoder;
	
    
	public List<User> getAllUser()
	{
		Iterable<User> findAll=userRepo.findAll();
		return (List<User>)findAll;
	}
	
	public void saveUser(User user) {
        // Encode the password
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepo.save(user);
    }

	public User getUserById(Integer userId) {
		
		Optional<User> u=userRepo.findById(userId);
		return u.orElse(null);
		
	}

	public User getUserByEmail(String email) {
		return userRepo.findByEmail(email);
	}
}
