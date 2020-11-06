package com.lti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lti.dto.LoginDto;
import com.lti.dto.LoginStatus;
import com.lti.dto.ResetPasswordDto;
import com.lti.dto.Status;
import com.lti.dto.StatusType;
import com.lti.entity.User;
import com.lti.exception.UserServiceException;
import com.lti.service.UserService;

@RestController
@CrossOrigin
public class UserController {
	
	@Autowired
	private UserService userService;
	

	@PostMapping(path = "/userregister")
	public Status register(@RequestBody User user) {
		try {
			userService.register(user);
			
			Status status = new Status();
			status.setStatus(StatusType.SUCCESS);
			status.setMessage("Registration Successfull");
			
			return status;
			
		} catch (UserServiceException e) {
			
			Status status = new Status();
			status.setStatus(StatusType.FAILURE);
			status.setMessage(e.getMessage());
			
			return status;
		}
	}
	@PostMapping("/resetpassword")
    public ResetPasswordDto reset(@RequestBody ResetPasswordDto user) {
        try {
            userService.resetUserPassword(user.getUserEmail());
            ResetPasswordDto status = new ResetPasswordDto();
            status.setStatus(StatusType.SUCCESS);
            status.setMessage("Password reset Successfull"); 
            status.setUserEmail(user.getUserEmail());
            return status;

        }
        catch (UserServiceException e) {
            ResetPasswordDto status = new ResetPasswordDto();
            status.setStatus(StatusType.FAILURE);
            status.setMessage(e.getMessage());
            return status;
        }
    }
	@PostMapping("/userlogin")
    public LoginStatus login(@RequestBody LoginDto loginDto) {
        try {
            User user = userService.login(loginDto.getUserEmail(), loginDto.getPassword());
            LoginStatus loginStatus = new LoginStatus();
            loginStatus.setStatus(StatusType.SUCCESS);
            loginStatus.setMessage("Login Successful!");
            loginStatus.setUserId(user.getUserId());
            loginStatus.setUsername(user.getFirstName());
            return loginStatus;
        }
        catch(UserServiceException e) {
            LoginStatus loginStatus = new LoginStatus();
            loginStatus.setStatus(StatusType.FAILURE);
            loginStatus.setMessage(e.getMessage());
            return loginStatus;
        }
    }


}
