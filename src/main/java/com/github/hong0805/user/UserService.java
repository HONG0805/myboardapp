package com.github.hong0805.user;

import com.github.hong0805.user.dto.request.*;
import com.github.hong0805.user.dto.response.*;

public interface UserService {
	LoginResponse login(LoginRequest request);

	JoinResponse join(JoinRequest request);

	FindIdResponse findId(FindIdRequest request);

	void resetPassword(ResetPasswordRequest request);

	void changePassword(ChangePasswordRequest request);

	UserResponse getUser(String userId);

	String findPassword(FindPasswordRequest request);
}