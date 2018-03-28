package com.crowdevents.faq;

import com.crowdevents.project.Project;
import com.crowdevents.project.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class FaqRepositoryServiceTest {
    @Mock
    private FaqRepository mockFaqRepository;
    @Mock
    private ProjectRepository mockProjectRepository;

    @InjectMocks
    private FaqRepositoryService faqService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void create_WithProperParams_ShouldCreateNewFaq() {
        Project mockProject = new Project("name", "description", null);
        Faq mockFaq = new Faq(mockProject, "Mock question", "Mock answer");
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockProject));
        Mockito.when(mockFaqRepository.save(Mockito.any()))
                .thenReturn(mockFaq);

        Faq result = faqService.create(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                "Mock question", "Mock answer");

        assertEquals(mockFaq, result);
    }

    @Test
    public void create_WithWrongProjectId_ShouldThrowException() {
        Faq mockFaq = new Faq(null, "Mock question", "Mock answer");
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());
        Mockito.when(mockFaqRepository.save(Mockito.any()))
                .thenReturn(mockFaq);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            faqService.create(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    "Mock question", "Mock answer");
        });

        assertEquals("Invalid project id: 00000000-0000-0000-0000-000000000001",
                exception.getMessage());
    }

    @Test
    public void get_WithExistingId_ShouldReturnExistingFaq() {
        Faq mockFaq = new Faq(null, "Mock question", "Mock answer");
        Mockito.when(mockFaqRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockFaq));

        Optional<Faq> result = faqService.get(UUID.fromString("00000000-0000-0000-0000-000000000001"));

        assertEquals(Optional.of(mockFaq), result);
    }

    @Test
    public void get_WithWrongId_ShouldReturnEmptyValue() {
        Mockito.when(mockFaqRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());

        Optional<Faq> result = faqService.get(UUID.fromString("00000000-0000-0000-0000-000000000001"));

        assertEquals(Optional.empty(), result);
    }

    @Test
    public void delete_WithExistingId_ShouldDeleteComment() {
        faqService.delete(UUID.fromString("00000000-0000-0000-0000-000000000001"));

        Mockito.verify(mockFaqRepository, Mockito.times(1))
                .deleteById(UUID.fromString("00000000-0000-0000-0000-000000000001"));
    }

    @Test
    public void changeQuestion_WithProperParams_ShouldChangeQuestion() {
        Faq mockFaq = new Faq(null, "Mock question", "Mock answer");
        Mockito.when(mockFaqRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockFaq));

        assertEquals("Mock question", mockFaq.getQuestion());
        faqService.changeQuestion(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                "Another question");
        assertEquals("Another question", mockFaq.getQuestion());
    }

    @Test
    public void changeQuestion_WithWrongFaqId_ShouldThrowException() {
        Mockito.when(mockFaqRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            faqService.changeQuestion(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    "Another question");
        });

        assertEquals("Invalid faq id: 00000000-0000-0000-0000-000000000001",
                exception.getMessage());
    }

    @Test
    public void changeAnswer_WithProperParams_ShouldChangeAnswer() {
        Faq mockFaq = new Faq(null, "Mock question", "Mock answer");
        Mockito.when(mockFaqRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockFaq));

        assertEquals("Mock answer", mockFaq.getAnswer());
        faqService.changeAnswer(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                "Another answer");
        assertEquals("Another answer", mockFaq.getAnswer());
    }

    @Test
    public void changeAnswer_WithWrongFaqId_ShouldThrowException() {
        Mockito.when(mockFaqRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            faqService.changeAnswer(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    "Another answer");
        });

        assertEquals("Invalid faq id: 00000000-0000-0000-0000-000000000001",
                exception.getMessage());
    }
}