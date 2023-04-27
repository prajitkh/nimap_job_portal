package com.jobportal.serviceImpl;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jobportal.dto.EmailOtpDto;
import com.jobportal.dto.ForgotPasswordConfirmDto;
import com.jobportal.dto.UserDto;
import com.jobportal.entity.OtpEntity;
import com.jobportal.entity.UserEntity;
import com.jobportal.excetpion.ResourceNotFoundException;
import com.jobportal.repositories.AuthRepository;
import com.jobportal.repositories.OTPRepository;
import com.jobportal.serviceInterface.AuthInterface;
import com.jobportal.serviceInterface.RolePermissionInterface;
import com.jobportal.utils.CacheOperation;

@Service
public class AuthServiceImpl implements AuthInterface, UserDetailsService {
	private static final Logger LOG = LoggerFactory.getLogger(AuthServiceImpl.class);
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthRepository authRepository;

	@Autowired
	private OTPRepository otpRepository;

	@Autowired
	private RolePermissionInterface rolePermissionInterface;

	@Autowired
	CacheOperation cache;

	@Override
	public UserDetails loadUserByUsername(String email) {
		UserEntity userEntity = new UserEntity();
//		if (!cache.isKeyExist(email, email)) {
		userEntity = this.authRepository.findByEmailContainingIgnoreCase(email);
//
//			cache.addInCache(email, email, userEntity.toString());
//		} else {
//			System.out.println("JSON STRING " + cache.getFromCache(email, email));
//			String jsonString = (String) cache.getFromCache(email, email);
//			try {
//				ObjectMapper mapper = new ObjectMapper();
//				Map<String, Object> map = mapper.readValue(jsonString, Map.class);
//				System.out.println(map.toString());
//				userEntity.setPassword((String) map.get("password"));
//				userEntity.setEmail((String) map.get("email"));
//				userEntity.setId(((Integer) map.get("id")).longValue());
//			} catch (Exception e) {
//				System.out.println("EEOR " + e);
//			}
//			System.out.println("JSON STRING 22 " + userEntity.toString());
//		}
		if (userEntity.getEmail().isEmpty()) {
			throw new ResourceNotFoundException("User OR Password not found");
		}

		return new org.springframework.security.core.userdetails.User(userEntity.getEmail(), userEntity.getPassword(),
				getAuthority(userEntity));
	}

	// for compare password
	@Override
	public Boolean comparePassword(String password, String hashPassword) {

		return passwordEncoder.matches(password, hashPassword);

	}

	@Override
	public void AddUser(UserDto userDto) {
		UserEntity userEntity = new UserEntity();
		userEntity.setEducation(userDto.getEducation());
		userEntity.setEmail(userDto.getEmail());
		userEntity.setExperience(userDto.getExperience());
		userEntity.setName(userDto.getName());
		userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
		userEntity.setMobileNumber(userDto.getMobileNumber());
		this.authRepository.save(userEntity);
	}

	void forgotPassword(EmailOtpDto emailOtpDto) {
		UserEntity userEntity = this.authRepository.findByEmailContainingIgnoreCase(emailOtpDto.getEmail());

	}

	@Override
	public boolean forgotPasswordConfirm(ForgotPasswordConfirmDto passwordConfirmDto, UserEntity userEntity,
			OtpEntity otpEntity) throws Exception {
		LOG.info("AuthServiceImpl >> forgotPasswordConfirm() >> ");

		LOG.info("AuthServiceImpl >> forgotPasswordConfirm() >> check user email");

		userEntity.setPassword(passwordEncoder.encode(passwordConfirmDto.getPassword()));

		this.authRepository.save(userEntity);
		this.otpRepository.deleteAll();
		LOG.info("AuthServiceImpl >> forgotPasswordConfirm() >> Done password changed");
		return false;
	}

	private ArrayList<SimpleGrantedAuthority> getAuthority(UserEntity user) {

		ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
		ArrayList<String> permissions = this.rolePermissionInterface.getPermissionByUserId(user.getId());
		permissions.forEach(e -> {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + e));

		});
		return authorities;
	}

	@Override
	public ArrayList<String> getUserPermission(Long id) {
		ArrayList<String> permissions;
		if (!cache.isKeyExist(id + "permission", id + "permission")) {
			permissions = this.rolePermissionInterface.getPermissionByUserId(id);
			cache.addInCache(id + "permission", id + "permission", permissions);
		} else {
			permissions = (ArrayList<String>) cache.getFromCache(id + "permission", id + "permission");
		}
		return permissions;

	}
}
