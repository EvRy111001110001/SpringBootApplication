package newscategory.repository;

import newscategory.entity.Category;
import newscategory.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Long> {
    @Query(value = "SELECT c.id, c.title " +
            "FROM category c JOIN news n ON " +
            "c.id = n.category_id ", nativeQuery = true)
    List<Long> findAllWithCategory();

    default Category getCategoryByName(String nameCategory, CategoryRepository categoryRepository) {
        Optional<Long> optionalCategory = findAllWithCategory().stream()
                .filter(id -> categoryRepository.getReferenceById(id).getTitle().equals(nameCategory))
                .findFirst();
        if (optionalCategory.isPresent()) {
            return categoryRepository.getReferenceById(optionalCategory.get());
        } else {
            Category category = new Category();
            category.setTitle(nameCategory);
            categoryRepository.save(category);
            return category;
        }
    }
}