package net.project.login.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.project.login.exception.ResourceNotFoundException;
import net.project.login.model.User;
import net.project.login.repository.UserRepository;

@CrossOrigin(origins = "http:localhost:4200")//CORS of my computer, change if needed
@RestController
@RequestMapping("/main")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/users")
	public  List<User> getAllUsers(){
		return userRepository.findAll();
	}//List all users
	
	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUser(@PathVariable(value="id") long userId) throws ResourceNotFoundException
	{
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));
		return ResponseEntity.ok().body(user);
		
		//Get a single user ID, peek into the exception package to inform the user if they don't exist
	}
	
	@PostMapping("/users")
	public User createUser(@Valid @RequestBody User user)
	{
		return userRepository.save(user);
	}
	
	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable(value="id") Long userId, @Valid @RequestBody User userDetails) throws ResourceNotFoundException
	{
		User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found " + userId));
		user.setUserName(userDetails.getUserName());
		user.setPassword(userDetails.getPassword());
		final User updatedUser = userRepository.save(user);
		return ResponseEntity.ok(updatedUser);
	}
	
	@DeleteMapping("/users/{id}")
	public Map<String, Boolean> deleteUsers(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException
	{
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found " + userId));
		
		userRepository.delete(user);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted",  Boolean.TRUE);
		return response;
	}
}
