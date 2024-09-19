package newscategory.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import newscategory.dto.NewsDto;


public class ResponseJson {
    private final ObjectMapper mapper;

    public ResponseJson(){
        mapper = new ObjectMapper();
    }
    public ObjectNode parserJsonNews(NewsDto newsDto){
        ObjectNode jsonData = mapper.createObjectNode();
        jsonData.put("id", newsDto.getId());
        jsonData.put("title", newsDto.getTitle());
        jsonData.put("text", newsDto.getText());
        jsonData.put("date", String.valueOf(newsDto.getDate()));
        jsonData.put("category", newsDto.getCategory());
        return jsonData;
    }

    public ObjectNode errorNews(Long id){
        ObjectNode jsonData = mapper.createObjectNode();
        jsonData.put("message", "Новость с id " + id + " не найдена.");
        return jsonData;
    }

    public ObjectNode errorCategory(Long id){
        ObjectNode jsonData = mapper.createObjectNode();
        jsonData.put("message", "Категория с id " + id + " не найдена.");
        return jsonData;
    }
}
