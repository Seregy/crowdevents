package com.crowdevents.faq;

import com.crowdevents.person.Person;
import com.crowdevents.project.Project;
import com.crowdevents.project.ProjectRepository;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
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
        Project mockProject = new Project("name", "description",
                Money.of(CurrencyUnit.USD, 1), new Person("", "", ""));
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
        Mockito.when(mockFaqRepository.existsById(1L))
                .thenReturn(true)
                .thenReturn(false);
        assertTrue(faqService.delete(1L));

        Mockito.verify(mockFaqRepository, Mockito.times(1))
                .deleteById(1L);
    }

    @Test
    public void update_WithNewQuestion_ShouldUpdateQuestion() {
        Faq mockFaq = new Faq(null, "Mock question", "Mock answer");
        Mockito.when(mockFaqRepository.findById(1L))
                .thenReturn(Optional.of(mockFaq));
        Faq updatedFaq = new Faq(null, "New question", "Mock answer");

        assertEquals("Mock question", mockFaq.getQuestion());
        faqService.update(1L, updatedFaq);
        assertEquals("New question", mockFaq.getQuestion());
    }

    @Test
    public void update_WithNewAnswer_ShouldUpdateQuestion() {
        Faq mockFaq = new Faq(null, "Mock question", "Mock answer");
        Mockito.when(mockFaqRepository.findById(1L))
                .thenReturn(Optional.of(mockFaq));
        Faq updatedFaq = new Faq(null, "Mock question", "New answer");

        assertEquals("Mock answer", mockFaq.getAnswer());
        faqService.update(1L, updatedFaq);
        assertEquals("New answer", mockFaq.getAnswer());
    }

    @Test
    public void update_WithNullUpdatedFaq_ShouldThrowException() {
        Faq mockFaq = new Faq(null, "Mock question", "Mock answer");
        Mockito.when(mockFaqRepository.findById(1L))
                .thenReturn(Optional.of(mockFaq));

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            faqService.update(1L, null);
        });

        assertEquals("Updated faq must not be null",
                exception.getMessage());
    }

    @Test
    public void update_WithWrongFaqId_ShouldThrowException() {
        Mockito.when(mockFaqRepository.findById(1L))
                .thenReturn(Optional.empty());
        Faq updatedFaq = new Faq(null, "New question", "New answer");

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            faqService.update(1L, updatedFaq);
        });

        assertEquals("Invalid faq id: 1",
                exception.getMessage());
    }
}