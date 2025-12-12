package com.example.demo.service;

import com.example.demo.dto.AuthResponseDto;
import com.example.demo.dto.CreateUserDto;
import com.example.demo.dto.LoginDto;
import com.example.demo.dto.UserDto;

public interface AuthService {

    AuthResponseDto login(LoginDto loginDto);

    UserDto register(CreateUserDto createUserDto);

    UserDto getCurrentUser();

    void logout();
}