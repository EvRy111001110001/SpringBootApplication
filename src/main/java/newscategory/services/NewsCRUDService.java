package newscategory.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import newscategory.dto.CategoryDto;
import newscategory.dto.NewsDto;
import newscategory.entity.Category;
import newscategory.entity.News;
import newscategory.repository.CategoryRepository;
import newscategory.repository.NewsRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class NewsCRUDService implements CRUDService<NewsDto> {
    private final NewsRepository newsRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public NewsDto getById(Long id) {
        log.info("Get by news id " + id);
        Optional<News> opt = newsRepository.findById(id);
        if (opt.isEmpty()) {
            return null;
        } else {
            News news = opt.get();
            return mapToDto(news);
        }
    }

    @Override
    public Collection<NewsDto> getAll() {
        log.info("Get all news");
        return newsRepository.findAll()
                .stream()
                .map(NewsCRUDService::mapToDto)
                .toList();
    }

    @Override
    public void create(NewsDto newsDto) {
        log.info("Create news");
        Category category = newsRepository.getCategoryByName(newsDto.getCategory(), categoryRepository);
        News news = mapToEntity(newsDto, category);
        newsRepository.save(news);
    }

    @Override
    public void update(NewsDto newsDto) {
        log.info("Update news");
        Category category = newsRepository.getCategoryByName(newsDto.getCategory(), categoryRepository);
        News news = mapToEntity(newsDto, category);
        newsRepository.save(news);
    }

    @Override
    public void deleteById(Long id) {
        log.info("Delete by news id " + id);
        newsRepository.deleteById(id);
    }


    public Long lastNews(String nameNews) {

        List<News> allNews = newsRepository.findAll();
        Long lastNewsId = null;
        for (News news : allNews) {
            if (news.getTitle().equals(nameNews)) {
                lastNewsId = news.getId();
            }
        }
        return lastNewsId;

    }

    public List<NewsDto> listNews(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow();
        return newsRepository.findAll()
                .stream().filter(news -> news.getCategory().equals(category))
                .map(NewsCRUDService::mapToDto)
                .toList();

    }

    public static NewsDto mapToDto(News news) {
        NewsDto newsDto = new NewsDto();
        newsDto.setId(news.getId());
        newsDto.setTitle(news.getTitle());
        newsDto.setText(news.getText());
        newsDto.setDate(news.getDate());
        newsDto.setCategory(news.getCategory().getTitle());
        return newsDto;
    }

    public static News mapToEntity(NewsDto newsDto, Category category) {
        News news = new News();
        news.setId(newsDto.getId());
        news.setText(newsDto.getText());
        news.setTitle(newsDto.getTitle());
        news.setDate(Instant.now());
        news.setCategory(category);
        return news;
    }
}