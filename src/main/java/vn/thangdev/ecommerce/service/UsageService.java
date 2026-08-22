package vn.thangdev.ecommerce.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.thangdev.ecommerce.model.Useage;
import vn.thangdev.ecommerce.repository.UsageRepository;

@Service
public class UsageService {
	private final UsageRepository usageRepository;

	public UsageService(UsageRepository usageRepository) {
		this.usageRepository = usageRepository;
	}

	public Page<Useage> getListUsage(String keyword, int page, int size) {
		Page<Useage> list;
		Pageable pageable = PageRequest.of(page, size);
		if (keyword != null) {
			list = this.usageRepository.search(keyword, pageable);
		} else {
			list = this.usageRepository.findAll(pageable);
		}

		return list;
	}

	public void createUsage(Useage data) {
		try {
			this.usageRepository.save(data);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("có lôĩ khi tạo mục đích sử dụng" + e);
		}
	}

	public Optional<Useage> findUsageById(Long id) {
		return this.usageRepository.findById(id);
	}

	public boolean checkExistedUsage(String name, Long id) {
		return this.usageRepository.existsByNameAndIdNot(name, id);
	}

	public void updateUsage(Useage data) {
		try {
			Useage useage = this.usageRepository.findById(data.getId())
					.orElseThrow(() -> new RuntimeException("Không tìm thấy mục đích sử dụng"));

			this.usageRepository.save(data);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("có lôĩ khi cập nhật mục đích sử dụng" + e);
		}
	}

	public void deleteById(Long id) {
		this.usageRepository.deleteById(id);
	}
}
