package com.vtes.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vtes.entity.Department;
import com.vtes.entity.User;
import com.vtes.payload.request.PasswordResetEmailRequest;
import com.vtes.payload.request.PasswordResetRequest;
import com.vtes.payload.request.UpdateInfoRequest;
import com.vtes.payload.response.MessageResponse;
import com.vtes.repository.DepartmentRepository;
import com.vtes.repository.UserRepository;
import com.vtes.sercurity.services.UserDetailsImpl;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	EmailService emailService;

	@Override
	public ResponseEntity<?> activeUser(String token) {
		// TODO Auto-generated method stub
		User user = new User();
		if (!userRepository.findByVerifyCode(token).isEmpty()) {
			user = userRepository.findByVerifyCode(token).get();
			user.setVerifyCode(null);
			user.setStatus((short) 1);
			userRepository.save(user);
			return ResponseEntity.ok(new MessageResponse("Account verify successfully", "INFO", "200"));
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Verify code incorrect", "ERROR", "API005_ER"));
		}

	}

	@Override
	public ResponseEntity<?> updateUser(UpdateInfoRequest updateInfoRequest, UserDetailsImpl userDetailsImpl) {
		// TODO Auto-generated method stub

		User user = new User();

		user = userRepository.findByEmail(userDetailsImpl.getEmail()).get();
		if (departmentRepository.findById(updateInfoRequest.getDepartmentId()).isEmpty()) {
			return ResponseEntity.badRequest().body(new MessageResponse("Department does not exits!", "ERROR", "XXX"));
		}

		Department department = new Department();
		department = departmentRepository.findById(updateInfoRequest.getDepartmentId()).get();

		if (updateInfoRequest.getPassword() == null) {

			user.setDepartment(department);
			user.setFullName(updateInfoRequest.getFullName());
		} else {
			if (!encoder.matches(updateInfoRequest.getPassword(), user.getPassword())) {
				return ResponseEntity.badRequest().body(new MessageResponse("Invalid password!", "ERROR", "AE08"));
			}
			if (updateInfoRequest.getPassword().equals(updateInfoRequest.getNewPassword())) {
				return ResponseEntity.badRequest().body(new MessageResponse(
						"The new password cannot be the same as the old password!", "ERROR", "AE08"));
			}

			user.setDepartment(department);
			user.setFullName(updateInfoRequest.getFullName());
			user.setPassword(encoder.encode(updateInfoRequest.getPassword()));
		}

		userRepository.save(user);

		return new ResponseEntity<>(new MessageResponse("Update Successfull", "INFO", "200"), HttpStatus.OK);
	}

	@Override
	public User getUser(String email) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(email).get();
	}

	@Override
	public ResponseEntity<?> sendResetPasswordViaEmail(PasswordResetEmailRequest passwordResetEmailRequest) {
		// TODO Auto-generated method stub
		if (!userRepository.existsByEmail(passwordResetEmailRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Email  invalid", "ERROR", "API_ER04"));
		}

		User user = new User();
		user = userRepository.findByEmail(passwordResetEmailRequest.getEmail()).get();

		if (user.getStatus() == 0) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("Error: User is not activated!", "ERROR", "XXX"));
		}
		user.setVerifyCode(UUID.randomUUID().toString());
		userRepository.save(user);

		emailService.sendResetPasswordViaEmail(passwordResetEmailRequest.getEmail(), user.getVerifyCode());

		return new ResponseEntity<>(
				new MessageResponse("We have sent an email. Please check email to reset password!", "INFO", "200"),
				HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> resetPassword(PasswordResetRequest passwordResetRequest) {
		// TODO Auto-generated method stub
		if (userRepository.findByVerifyCode(passwordResetRequest.getToken()).isEmpty()) {
			return ResponseEntity.badRequest().body(new MessageResponse("Verify code does not exist!", "ERROR", "XXX"));
		}
		User user = new User();
		user = userRepository.findByVerifyCode(passwordResetRequest.getToken()).get();

		if (user.getStatus() == 0) {
			return ResponseEntity.badRequest().body(new MessageResponse("User is not activated!", "ERROR", "XXX"));
		}

		user.setPassword(encoder.encode(passwordResetRequest.getNewPassword()));
		user.setVerifyCode(null);

		return new ResponseEntity<>(new MessageResponse("Reset password successfully!", "INFO", "200"), HttpStatus.OK);
	}

}
