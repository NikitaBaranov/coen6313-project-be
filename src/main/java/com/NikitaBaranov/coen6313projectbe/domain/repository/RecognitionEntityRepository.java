package com.NikitaBaranov.coen6313projectbe.domain.repository;

import com.NikitaBaranov.coen6313projectbe.domain.dto.RecognitionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecognitionEntityRepository extends MongoRepository<RecognitionEntity, String> {
}
