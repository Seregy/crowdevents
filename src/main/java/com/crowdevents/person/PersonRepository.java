package com.crowdevents.person;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface PersonRepository extends PagingAndSortingRepository<Person, UUID> {
}
