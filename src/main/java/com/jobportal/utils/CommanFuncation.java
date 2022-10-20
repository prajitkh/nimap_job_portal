package com.jobportal.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.jobportal.entity.UserEntity;
import com.jobportal.repositories.RoleRepository;
import com.jobportal.repositories.UserRepository;
import com.jobportal.repositories.UserRoleRepository;
import com.jobportal.serviceInterface.JwtTokenUtilInterface;

@Component
public class CommanFuncation implements HandlerInterceptor {

	@Autowired
	private JwtTokenUtilInterface tokenUtilInterface;

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserRoleRepository userRoleRepository;

	@Autowired
	RoleRepository roleRepository;

	public final static String CUSTUM_ATTRIBUTE_USER_ID = "X-user-id";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String authHeader = request.getHeader("Authorization");
		String tokenString = (null != authHeader) ? authHeader.split(" ")[1] : null;

		if (null != tokenString) {
			final String emailString = tokenUtilInterface.getUsernameFromToken(tokenString);

			UserEntity userEntity = userRepository.findByEmail(emailString);

			request.setAttribute("X-user-id", userEntity.getId());
		}

		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

}
