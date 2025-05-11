package com.example.indie91.Services;

import com.example.indie91.Models.Content;
import com.example.indie91.Repositories.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContentService {

    @Autowired
    private ContentRepository contentRepository;

    public List<Content> getAllContent() {
        return contentRepository.findAll();
    }

    public Optional<Content> getContentById(String id) {
        return contentRepository.findById(id);
    }

    public Content createContent(Content content) {
        return contentRepository.save(content);
    }

    public void deleteContent(String id) {
        contentRepository.deleteById(id);
    }
}
