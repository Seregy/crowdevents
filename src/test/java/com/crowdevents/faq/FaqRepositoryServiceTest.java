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
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.of(mockProject));
        Mockito.when(mockFaqRepository.save(Mockito.any()))
                .thenReturn(mockFaq);

        Faq result = faqService.create(1L,
                "Mock question", "Mock answer");

        assertEquals(mockFaq, result);
    }

    @Test
    public void create_WithWrongProjectId_ShouldThrowException() {
        Faq mockFaq = new Faq(null, "Mock question", "Mock answer");
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.empty());
        Mockito.when(mockFaqRepository.save(Mockito.any()))
                .thenReturn(mockFaq);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            faqService.create(1L,
                    "Mock question", "Mock answer");
        });

        assertEquals("Invalid project id: 1",
                exception.getMessage());
    }

    @Test
    public void get_WithExistingId_ShouldReturnExistingFaq() {
        Faq mockFaq = new Faq(null, "Mock question", "Mock answer");
        Mockito.when(mockFaqRepository.findById(1L))
                .thenReturn(Optional.of(mockFaq));

        Optional<Faq> result = faqService.get(1L);

        assertEquals(Optional.of(mockFaq), result);
    }

    @Test
    public void get_WithWrongId_ShouldReturnEmptyValue() {
        Mockito.when(mockFaqRepository.findById(1L))
                .thenReturn(Optional.empty());

        Optional<Faq> result = faqService.get(1L);

        assertEquals(Optional.empty(), result);
    }

    @Test
    public void delete_WithExistingId_ShouldDeleteComment() {
        faqService.delete(1L);

        Mockito.verify(mockFaqRepository, Mockito.times(1))
                .deleteById(1L);
    }

    @Test
    public void changeQuestion_WithProperParams_ShouldChangeQuestion() {
        Faq mockFaq = new Faq(null, "Mock question", "Mock answer");
        Mockito.when(mockFaqRepository.findById(1L))
                .thenReturn(Optional.of(mockFaq));

        assertEquals("Mock question", mockFaq.getQuestion());
        faqService.changeQuestion(1L,
                "Another question");
        assertEquals("Another question", mockFaq.getQuestion());
    }

    @Test
    public void changeQuestion_WithWrongFaqId_ShouldThrowException() {
        Mockito.when(mockFaqRepository.findById(1L))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            faqService.changeQuestion(1L,
                    "Another question");
        });

        assertEquals("Invalid faq id: 1",
                exception.getMessage());
    }

    @Test
    public void changeAnswer_WithProperParams_ShouldChangeAnswer() {
        Faq mockFaq = new Faq(null, "Mock question", "Mock answer");
        Mockito.when(mockFaqRepository.findById(1L))
                .thenReturn(Optional.of(mockFaq));

        assertEquals("Mock answer", mockFaq.getAnswer());
        faqService.changeAnswer(1L,
                "Another answer");
        assertEquals("Another answer", mockFaq.getAnswer());
    }

    @Test
    public void changeAnswer_WithWrongFaqId_ShouldThrowException() {
        Mockito.when(mockFaqRepository.findById(1L))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            faqService.changeAnswer(1L,
                    "Another answer");
        });

        assertEquals("Invalid faq id: 1",
                exception.getMessage());
    }
}