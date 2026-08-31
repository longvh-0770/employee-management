package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.LoginRequest;
import com.example.employeemanagement.dto.LoginResponse;
import com.example.employeemanagement.dto.RegisterRequest;
import com.example.employeemanagement.dto.UserResponse;
import com.example.employeemanagement.model.Role;
import com.example.employeemanagement.model.User;
import com.example.employeemanagement.repository.UserRepository;
import com.example.employeemanagement.security.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public UserResponse register(RegisterRequest request) {
        userRepository.findByUsername(request.username()).ifPresent(existing -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username đã tồn tại");
        });

        User user = new User(request.username(), passwordEncoder.encode(request.password()), Role.USER);
        User saved = userRepository.save(user);
        log.info("Đã đăng ký user mới: username={}, role={}", saved.getUsername(), saved.getRole());
        return new UserResponse(saved.getId(), saved.getUsername(), saved.getRole().name());
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Sai tài khoản hoặc mật khẩu"));

        if (!passwordEncoder.matches(request.password(), user.getEncodedPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Sai tài khoản hoặc mật khẩu");
        }

        String token = jwtService.generateToken(user.getUsername(), user.getRole().name());
        log.info("Đăng nhập thành công: username={}", user.getUsername());
        return new LoginResponse(token, user.getUsername(), user.getRole().name());
    }

}
