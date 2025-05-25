package com.example.indie91.Services;

import com.example.indie91.Models.Content;
import com.example.indie91.Models.Product;
import com.example.indie91.Repositories.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ContentService {

    @Autowired
    private ContentRepository contentRepository;

    public List<Content> getAllContent() {
        return contentRepository.findAll();
    }

    public Optional<Content> getContentById(UUID id) {
        return contentRepository.findById(id);
    }

    public Content createContent(Content content) {
        return contentRepository.save(content);
    }

    public Content updateContent(UUID id, Content updatedContent) {
        try {
            Optional<Content> existingContentOpt = contentRepository.findById(id);
            if (existingContentOpt.isEmpty()) {
                throw new RuntimeException("Content not found");
            }

            Content existingContent = existingContentOpt.get();

            // Merge updated fields (only if non-null)
            if(updatedContent.getLikesCount() != null){
                existingContent.setLikesCount(updatedContent.getLikesCount());
            }

            if(updatedContent.getShareCount() != null){
                existingContent.setShareCount(updatedContent.getShareCount());
            }

            // Add more field checks as needed
            return contentRepository.save(existingContent);
        } catch (Exception e) {
            throw new RuntimeException("Error updating brand", e);
        }
    }


    public void deleteContent(UUID id) {
        contentRepository.deleteById(id);
    }

    public Content incrementLikeCount(UUID id) {
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Content not found"));

        int currentLikes = content.getLikesCount() != null ? content.getLikesCount() : 0;
        content.setLikesCount(currentLikes + 1);
        return contentRepository.save(content);
    }
}
