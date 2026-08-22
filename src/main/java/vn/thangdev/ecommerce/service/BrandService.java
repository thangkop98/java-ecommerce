package vn.thangdev.ecommerce.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.thangdev.ecommerce.model.Brand;
import vn.thangdev.ecommerce.repository.BrandRepository;

@Service
public class BrandService {
	private final BrandRepository brandRepository;

	public BrandService(BrandRepository brandRepository) {
		this.brandRepository = brandRepository;
	}

	public Page<Brand> getListBrand(String keyword, int page, int size) {
		Page<Brand> list;
		Pageable pageable = PageRequest.of(page, size);
		if (keyword != null) {
			list = this.brandRepository.search(keyword, pageable);
		} else {
			list = this.brandRepository.findAll(pageable);
		}

		return list;
	}

	public void createBrand(Brand data) {
		try {
			this.brandRepository.save(data);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("có lôĩ khi tạo hãng sản xuất" + e);
		}
	}

	public Optional<Brand> findBrandById(Long id) {
		return this.brandRepository.findById(id);
	}

	public boolean checkExistedBrand(String name, Long id) {
		return this.brandRepository.existsByNameAndIdNot(name, id);
	}

	public void updateBrand(Brand data) {
		try {
			Brand brand = this.brandRepository.findById(data.getId())
					.orElseThrow(() -> new RuntimeException("Không tìm thấy hãng sản xuất"));

			this.brandRepository.save(data);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("có lôĩ khi cập nhật hãng sản xuất" + e);
		}
	}

	public void deleteById(Long id) {
		this.brandRepository.deleteById(id);
	}
}
