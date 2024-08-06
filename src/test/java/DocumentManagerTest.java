import org.example.DocumentManager;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DocumentManagerTest {

    @Test
    public void testSave() {
        DocumentManager documentManager = new DocumentManager();
        DocumentManager.Author author = DocumentManager.Author.builder()
                .name("Some Name")
                .build();
        DocumentManager.Document document = DocumentManager.Document.builder()
                .id("1")
                .title("Title")
                .content("Some content")
                .author(author)
                .created(Instant.now())
                .build();

        DocumentManager.Document result = documentManager.save(document);

        assertEquals(document, result);
    }
}
