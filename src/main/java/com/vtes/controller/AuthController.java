package com.vtes.controller;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vtes.entity.Department;
import com.vtes.entity.RefreshToken;
import com.vtes.entity.User;
import com.vtes.exception.TokenRefreshException;
import com.vtes.payload.request.LoginRequest;
import com.vtes.payload.request.SignupRequest;
import com.vtes.payload.request.TokenRefreshRequest;
import com.vtes.payload.response.MessageResponse;
import com.vtes.payload.response.TokenRefreshResponse;
import com.vtes.repository.DepartmentRepository;
import com.vtes.repository.UserRepository;
import com.vtes.sercurity.jwt.JwtUtils;
import com.vtes.sercurity.services.RefreshTokenService;
import com.vtes.sercurity.services.UserDetailsImpl;
import com.vtes.service.EmailService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	EmailService emailService;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	RefreshTokenService refreshTokenService;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse res) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		User user = new User();
		user = userRepository.findById(userDetails.getId()).get();
		if (user.getStatus() == 0) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("This account is not active yet", "ERROR", "API001_ER02"));
		}

		String jwt = jwtUtils.generateJwtToken(userDetails);

		if (loginRequest.getRemember()) {
			RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

			Cookie cookieAccessToken = new Cookie("accessToken", jwt);
			cookieAccessToken.setHttpOnly(true);
			cookieAccessToken.setMaxAge(6000);
			cookieAccessToken.setSecure(false);
			res.addCookie(cookieAccessToken);

			Cookie cookieRefreshToken = new Cookie("refreshToken", refreshToken.getToken());
			cookieRefreshToken.setHttpOnly(true);
			cookieRefreshToken.setMaxAge(60000);
			cookieRefreshToken.setSecure(false);
			res.addCookie(cookieRefreshToken);
		} else {
			Cookie cookieAccessToken = new Cookie("accessToken", jwt);
			cookieAccessToken.setHttpOnly(true);
			cookieAccessToken.setMaxAge(6000);
			cookieAccessToken.setSecure(false);
			res.addCookie(cookieAccessToken);
		}

		return ResponseEntity.ok().body(new MessageResponse("Authentication successfull", "INFO", "200"));
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("This email has already been used", "ERROR", "API002_ER"));
		}

		if (departmentRepository.findById(signUpRequest.getDepartmentId()).isEmpty()) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("Department does not exits", "ERROR", "API_ER04"));
		}

		Department department = new Department();
		department = departmentRepository.findById(signUpRequest.getDepartmentId()).get();

		// Create new user's account
		User user = new User(signUpRequest.getFullName(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()), department);
		user.setStatus((short) 0);
		user.setVerifyCode(UUID.randomUUID().toString());
		emailService.sendRegistrationUserConfirm(signUpRequest.getEmail(), user.getVerifyCode());

		userRepository.save(user);

		return ResponseEntity.ok().body(new MessageResponse("Register successful", "INFO", "200"));
	}

	@PostMapping("/refreshtoken")
	public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request, HttpServletResponse res) {
		String requestRefreshToken = request.getRefreshToken();

		return refreshTokenService.findByToken(requestRefreshToken).map(refreshTokenService::verifyExpiration)
				.map(RefreshToken::getUser).map(user -> {
					String token = jwtUtils.generateTokenFromUsername(user.getFullName());
					Cookie cookieAccessToken = new Cookie("accessToken", token);
					cookieAccessToken.setHttpOnly(true);
					cookieAccessToken.setMaxAge(6000);
					cookieAccessToken.setSecure(false);
					res.addCookie(cookieAccessToken);
					return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
				})
				.orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!"));
	}

	@PostMapping("/signout")
	public ResponseEntity<?> logoutUser() {
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long userId = userDetails.getId();
		refreshTokenService.deleteByUserId(userId);
		return ResponseEntity.ok(new MessageResponse("Jwt Token deleted", "INFO", "200"));
	}

}
