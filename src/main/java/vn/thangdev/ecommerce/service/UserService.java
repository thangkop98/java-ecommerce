package vn.thangdev.ecommerce.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import vn.thangdev.ecommerce.dto.CreateUserDTO;
import vn.thangdev.ecommerce.dto.UpdateAccountDTO;
import vn.thangdev.ecommerce.dto.UpdateUserDTO;
import vn.thangdev.ecommerce.model.User;
import vn.thangdev.ecommerce.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder bcryptEncoder;
	private final FileUploadService fileUploadService;

	@Value("${app.user-upload-dir-absolute}")
	private String uploadDirAbsolute;

	@Value("${app.user-upload-dir-relative}")
	private String uploadDirRelative;

	public UserService(UserRepository userRepository, PasswordEncoder bcryptEncoder,
			FileUploadService fileUploadService) {
		this.userRepository = userRepository;
		this.bcryptEncoder = bcryptEncoder;
		this.fileUploadService = fileUploadService;
	}

	public Page<User> getListUser(int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
		return this.userRepository.findAll(pageable);
	}

	public void createUser(CreateUserDTO dto, MultipartFile image) {
		try {
			User user = new User();
			user.setName(dto.getName());
			user.setEmail(dto.getEmail());
			user.setPhone(dto.getPhone());
			user.setAddress(dto.getAddress());
			user.setRole_id(dto.getRole_id());
			String hashPassword = this.bcryptEncoder.encode(dto.getPassword());
			user.setPassword(hashPassword);
			if (image != null && !image.isEmpty()) {
				String path = this.fileUploadService.upload(image, this.uploadDirAbsolute, this.uploadDirRelative,
						"user");
				if (!path.isEmpty()) {
					user.setAvatar(path);
				}
			}
			this.userRepository.save(user);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("có lôĩ khi tạo user" + e);
		}

	}

	public boolean checkExistedEmail(String email) {
		return this.userRepository.existsByEmail(email);
	}

	public boolean checkExistedByEmail(String email) {
		return this.userRepository.existsByEmail(email);
	}

	public boolean checkExistedByEmailForUpdate(String email, Long id) {
		return this.userRepository.existsByEmailAndIdNot(email, id);
	}

	public Optional<User> findUserById(Long id) {
		return this.userRepository.findById(id);
	}

	public void updateUser(UpdateUserDTO dto, MultipartFile image) {
		try {
			User user = this.userRepository.findById(dto.getId())
					.orElseThrow(() -> new RuntimeException("Không tìm thấy user"));
			user.setName(dto.getName());
			user.setEmail(dto.getEmail());
			user.setPhone(dto.getPhone());
			user.setAddress(dto.getAddress());
			user.setRole_id(dto.getRole_id());

			if (image != null && !image.isEmpty()) {
				String path = this.fileUploadService.upload(image, this.uploadDirAbsolute, this.uploadDirRelative,
						"user");
				if (!path.isEmpty()) {
					user.setAvatar(path);
				}
			}

			if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
				user.setPassword(this.bcryptEncoder.encode(dto.getPassword()));
			}
			this.userRepository.save(user);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("có lôĩ khi update user" + e);
		}

	}

	public void deleteUser(Long id) {
		this.userRepository.deleteById(id);
	}

	public Page<User> searchListUser(String keyword, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
		return this.userRepository.search(keyword, pageable);
	}

	public Optional<User> findUserByEmail(String email) {
		return this.userRepository.findUserByEmail(email);
	}

	public void updateAccount(UpdateAccountDTO dto, MultipartFile image) {
		try {
			User user = this.userRepository.findById(dto.getId())
					.orElseThrow(() -> new RuntimeException("Không tìm thấy user"));
			user.setName(dto.getName());
			user.setEmail(dto.getEmail());
			user.setPhone(dto.getPhone());

			if (image != null && !image.isEmpty()) {
				String path = this.fileUploadService.upload(image, this.uploadDirAbsolute, this.uploadDirRelative,
						"user");
				if (!path.isEmpty()) {
					user.setAvatar(path);
				}
			}

			this.userRepository.save(user);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("có lôĩ khi update user" + e);
		}

	}
}
