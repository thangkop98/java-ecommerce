package vn.thangdev.ecommerce.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.thangdev.ecommerce.model.Category;
import vn.thangdev.ecommerce.repository.CategoryRepository;

@Service
public class CategoryService {
	private final CategoryRepository categoryRepository;

	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public Page<Category> getListCategory(String keyword, int page, int size) {
		Page<Category> list;
		Pageable pageable = PageRequest.of(page, size);
		if (keyword != null) {
			list = this.categoryRepository.search(keyword, pageable);
		} else {
			list = this.categoryRepository.findAll(pageable);
		}

		return list;
	}

	public void createCategory(Category data) {
		try {
			this.categoryRepository.save(data);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("có lôĩ khi tạo danh mục" + e);
		}

	}

	public Optional<Category> findCategoryById(Long id) {
		return this.categoryRepository.findById(id);
	}

	public boolean checkExistedCategory(String name, Long id) {
		return this.categoryRepository.existsByNameAndIdNot(name, id);
	}

	public void updateCategory(Category data) {
		try {
			Category category = this.categoryRepository.findById(data.getId())
					.orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục"));

			this.categoryRepository.save(data);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("có lôĩ khi cập nhật danh mục" + e);
		}
	}

	public void deleteById(Long id) {
		this.categoryRepository.deleteById(id);
	}
}
