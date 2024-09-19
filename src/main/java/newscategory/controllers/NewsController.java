package newscategory.controllers;

import lombok.RequiredArgsConstructor;
import newscategory.dto.NewsDto;
import newscategory.services.NewsCRUDService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsCRUDService newsService;


    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        NewsDto foundNews = newsService.getById(id);
        ResponseJson responseJson = new ResponseJson();
        if (foundNews != null) {

            return ResponseEntity.ok(responseJson.parserJsonNews(foundNews));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(responseJson.errorNews(id));
        }
    }

    @GetMapping
    public Collection<NewsDto> getAll() {
        return newsService.getAll();
    }

    @PostMapping
    public ResponseEntity<NewsDto> create(@RequestBody NewsDto newsDto) {
        newsService.create(newsDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newsService.getById(newsService.lastNews(newsDto.getTitle())));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewsDto> update(@PathVariable Long id, @RequestBody NewsDto newsDto) {
        newsService.update(newsDto);
        return ResponseEntity.ok(newsService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        NewsDto foundNews = newsService.getById(id);
        if (foundNews != null) {
            newsService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // Возвращаем новость с кодом 204 (No Content)
        } else {
            ResponseJson responseJson = new ResponseJson();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(responseJson.errorNews(id)); // Возвращаем ошибку 404 (Not Found)
        }
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<?> getNewsByCategoryId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(newsService.listNews(id));
    }
}
