package newscategory.controllers;

import lombok.RequiredArgsConstructor;
import newscategory.dto.CategoryDto;
import newscategory.services.CategoryCRUDService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryCRUDService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        CategoryDto categoryDto = categoryService.getById(id);
        ResponseJson responseJson = new ResponseJson();
        if (categoryDto != null) {
            return ResponseEntity.ok(categoryDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(responseJson.errorCategory(id));
        }
    }

    @GetMapping
    public Collection<CategoryDto> getAllCategories() {
        return categoryService.getAll();
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CategoryDto categoryDto) {
        categoryService.create(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryService.getById(categoryService.lastCategory(categoryDto.getTitle())));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        categoryService.update(categoryDto);
        return ResponseEntity.ok(categoryService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        CategoryDto categoryDto = categoryService.getById(id);
        if (categoryDto != null) {
            categoryService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            ResponseJson responseJson = new ResponseJson();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(responseJson.errorCategory(id));
        }
    }
}
