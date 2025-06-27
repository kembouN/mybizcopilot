package com.mybizcopilot.services;

import com.mybizcopilot.dto.requests.ChangePasswordRequest;
import com.mybizcopilot.dto.requests.LoginRequest;
import com.mybizcopilot.dto.requests.RegisterRequest;
import com.mybizcopilot.dto.requests.UpdateUserRequest;
import com.mybizcopilot.dto.responses.LoginResponse;

public interface IUtilisateurService {

    Void register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    Void changePassword(Integer idUser, ChangePasswordRequest request);

    LoginResponse changeUserInfo(Integer isUser, UpdateUserRequest request);

}
