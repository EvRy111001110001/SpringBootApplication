package newscategory.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import newscategory.dto.CategoryDto;
import newscategory.entity.Category;
import newscategory.repository.CategoryRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryCRUDService implements CRUDService<CategoryDto> {

    private final CategoryRepository repository;

    @Override
    public CategoryDto getById(Long id) {
        log.info("Get by category id " + id);
        Optional<Category> opt = repository.findById(id);
        if (opt.isEmpty()) {
            return null;
        } else {
            Category category = opt.get();
            return mapToDto(category);
        }
    }

    @Override
    public Collection<CategoryDto> getAll() {
        log.info("Get all category");
        return repository.findAll()
                .stream()
                .map(CategoryCRUDService::mapToDto)
                .toList();
    }

    @Override
    public void create(CategoryDto categoryDto) {
        log.info("Create category");
        try {
            repository.save(mapToEntity(categoryDto));
        } catch (DataIntegrityViolationException e) {
            log.error("Such a category already exists. Come up with a new one.");
        }

    }

    @Override
    public void update(CategoryDto categoryDto) {
        log.info("Update category");
        repository.save(mapToEntity(categoryDto));

    }

    @Override
    public void deleteById(Long id) {
        log.info("Delete by category id " + id);
        repository.deleteById(id);
    }

    public Long lastCategory(String nameCategory) {
        List<Category> allCategories = repository.findAll();
        Long lastCategoryId = null;
        for (Category category : allCategories) {
            if (category.getTitle().equals(nameCategory)) {
                lastCategoryId = category.getId();
            }
        }
        return lastCategoryId;
    }

    public static CategoryDto mapToDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setTitle(category.getTitle());
        return categoryDto;
    }

    public static Category mapToEntity(CategoryDto categoryDto) {
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setTitle(categoryDto.getTitle());
        return category;
    }

}