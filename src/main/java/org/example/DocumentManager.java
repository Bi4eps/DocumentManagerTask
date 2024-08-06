package org.example;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * For implement this task focus on clear code, and make this solution as simple readable as possible
 * Don't worry about performance, concurrency, etc
 * You can use in Memory collection for sore data
 * <p>
 * Please, don't change class name, and signature for methods save, search, findById
 * Implementations should be in a single class
 * This class could be auto tested
 */
public class DocumentManager {

    private final List<Document> storage = new ArrayList<>();

    /**
     * Implementation of this method should upsert the document to your storage
     * And generate unique id if it does not exist, don't change [created] field
     *
     * @param document - document content and author data
     * @return saved document
     */
    public Document save(Document document) {
        // generate id if document does not have it
        if (document.getId() == null || document.getId().isEmpty()) {
            document.setId(String.valueOf(storage.size() + 1));
        } else {
            for (int i = 0; i < storage.size(); i++) {
                if (storage.get(i).getId().equals(document.getId())) {
                    storage.set(i, document);
                    return document;
                }
            }
            storage.add(document);
        }

        storage.add(document);

        return document;
    }

    /**
     * Implementation this method should find documents which match with request
     *
     * @param request - search request, each field could be null
     * @return list matched documents
     */
    public List<Document> search(SearchRequest request) {
        if (request == null) {
            return Collections.emptyList();
        }

        List<Document> foundDocs = new ArrayList<>();

        for (Document doc : storage) {
            if (doesMatch(doc, request)) {
                foundDocs.add(doc);
            }
        }

        return foundDocs;
    }

    private boolean doesMatch(Document doc, SearchRequest request) {
        if (request.getCreatedFrom() != null) {
            if (doc.getCreated() == null || doc.getCreated().isBefore(request.getCreatedFrom())) {
                return false;
            }
        }

        if (request.getCreatedTo() != null) {
            if (doc.getCreated() == null || doc.getCreated().isAfter(request.getCreatedTo())) {
                return false;
            }
        }

        for (String prefix : request.getTitlePrefixes()) {
            if(doc.getTitle().contains(prefix)) {
                return true;
            }
        }

        for (String content : request.getContainsContents()) {
            if(doc.getTitle().contains(content)) {
                return true;
            }
        }

        for (String authorId : request.getAuthorIds()) {
            if (doc.getAuthor().getId().equals(authorId)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Implementation this method should find document by id
     *
     * @param id - document id
     * @return optional document
     */
    public Optional<Document> findById(String id) {

        for (Document doc : storage) {
            if(doc.getId().equals(id)) {
                return Optional.of(doc);
            }
        }

        return Optional.empty();
    }

    @Data
    @Builder
    public static class SearchRequest {
        private List<String> titlePrefixes;
        private List<String> containsContents;
        private List<String> authorIds;
        private Instant createdFrom;
        private Instant createdTo;
    }

    @Data
    @Builder
    public static class Document {
        private String id;
        private String title;
        private String content;
        private Author author;
        private Instant created;
    }

    @Data
    @Builder
    public static class Author {
        private String id;
        private String name;
    }
}