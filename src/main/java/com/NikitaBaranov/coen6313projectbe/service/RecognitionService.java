package com.NikitaBaranov.coen6313projectbe.service;

import com.NikitaBaranov.coen6313projectbe.client.MlEngineClient;
import com.NikitaBaranov.coen6313projectbe.domain.dto.RecognitionEntity;
import com.NikitaBaranov.coen6313projectbe.domain.dto.RecognitionResult;
import com.NikitaBaranov.coen6313projectbe.domain.repository.RecognitionEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class RecognitionService {

    @Autowired
    RecognitionEntityRepository recognitionEntityRepository;

    @Autowired
    MlEngineClient mlEngineClient;

    public RecognitionResult recognise(RecognitionEntity recognitionEntity) throws IOException {
        RecognitionEntity savedRecognitionEntity = recognitionEntityRepository.save(recognitionEntity);
        RecognitionResult result = mlEngineClient.getPrediction(recognitionEntity.image);
        savedRecognitionEntity.setRecognitionResult(result);
        recognitionEntityRepository.save(savedRecognitionEntity);
        return result;
    }

    public List<RecognitionEntity> getAll() {
        List<RecognitionEntity> recognitionEntityList = recognitionEntityRepository.findAll();
        return recognitionEntityList;
    }

    public RecognitionEntity getOne(String id) {
        RecognitionEntity recognitionEntity = recognitionEntityRepository.findOne(id);
        return recognitionEntity;
    }

}
