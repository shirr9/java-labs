package com.example.webgateway.service;

import com.example.webgateway.config.RabbitConfig;
import com.example.webgateway.dto.PageDTO;
import com.example.webgateway.dto.PetCreateUpdateDTO;
import com.example.webgateway.dto.PetResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PetGatewayService {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public PetGatewayService(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public PetResponseDTO save(PetCreateUpdateDTO petCreateUpdateDTO) throws Exception {
        Map<String, Object> request = new HashMap<>();
        request.put("command", "save");
        request.put("pet", petCreateUpdateDTO);
        request.put("replyTo", RabbitConfig.REPLY_QUEUE);

        String requestJson = objectMapper.writeValueAsString(request);
        String responseJson = (String) rabbitTemplate.convertSendAndReceive(RabbitConfig.PETS_QUEUE, requestJson);

        return objectMapper.readValue(responseJson, PetResponseDTO.class);
    }

    public void deleteById(Long id) throws Exception {
        Map<String, Object> request = new HashMap<>();
        request.put("command", "deleteById");
        request.put("id", id);
        request.put("replyTo", RabbitConfig.REPLY_QUEUE);

        rabbitTemplate.convertSendAndReceive(RabbitConfig.PETS_QUEUE, objectMapper.writeValueAsString(request));
    }

    public void deleteAll() throws Exception {
        Map<String, Object> request = new HashMap<>();
        request.put("command", "deleteAll");
        request.put("replyTo", RabbitConfig.REPLY_QUEUE);

        rabbitTemplate.convertSendAndReceive(RabbitConfig.PETS_QUEUE, objectMapper.writeValueAsString(request));
    }

    public PetResponseDTO update(Long id, PetCreateUpdateDTO petCreateUpdateDTO) throws Exception {
        Map<String, Object> request = new HashMap<>();
        request.put("command", "update");
        request.put("id", id);
        request.put("pet", petCreateUpdateDTO);
        request.put("replyTo", RabbitConfig.REPLY_QUEUE);

        String requestJson = objectMapper.writeValueAsString(request);
        String responseJson = (String) rabbitTemplate.convertSendAndReceive(RabbitConfig.PETS_QUEUE, requestJson);

        return objectMapper.readValue(responseJson, PetResponseDTO.class);
    }

    public PetResponseDTO getById(Long id) throws Exception {
        Map<String, Object> request = new HashMap<>();
        request.put("command", "getById");
        request.put("id", id);
        request.put("replyTo", RabbitConfig.REPLY_QUEUE);

        String requestJson = objectMapper.writeValueAsString(request);
        String responseJson = (String) rabbitTemplate.convertSendAndReceive(RabbitConfig.PETS_QUEUE, requestJson);

        return objectMapper.readValue(responseJson, PetResponseDTO.class);
    }

    public Page<PetResponseDTO> getFilteredPets(String name, String breed, Integer tailLength,
                                                LocalDate birthDate, Pageable pageable) throws Exception {
        Map<String, Object> request = new HashMap<>();
        request.put("command", "getFilteredPets");
        request.put("name", name);
        request.put("breed", breed);
        request.put("tailLength", tailLength);
        request.put("birthDate", birthDate != null ? birthDate.format(DateTimeFormatter.ISO_DATE) : null);
        request.put("page", pageable.getPageNumber());
        request.put("size", pageable.getPageSize());
        request.put("replyTo", RabbitConfig.REPLY_QUEUE);

        String requestJson = objectMapper.writeValueAsString(request);
        String responseJson = (String) rabbitTemplate.convertSendAndReceive(RabbitConfig.PETS_QUEUE, requestJson);

        PageDTO<PetResponseDTO> pageDTO = objectMapper.readValue(responseJson,
                objectMapper.getTypeFactory().constructParametricType(PageDTO.class, PetResponseDTO.class));

        return new PageImpl<>(pageDTO.getContent(),
                PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize()), pageDTO.getTotalElements());
    }

    public List<PetResponseDTO> getAll() throws Exception {
        Map<String, Object> request = new HashMap<>();
        request.put("command", "getAll");
        request.put("replyTo", RabbitConfig.REPLY_QUEUE);

        String requestJson = objectMapper.writeValueAsString(request);
        String responseJson = (String) rabbitTemplate.convertSendAndReceive(RabbitConfig.PETS_QUEUE, requestJson);

        return objectMapper.readValue(responseJson,
                objectMapper.getTypeFactory().constructCollectionType(java.util.List.class, PetResponseDTO.class));
    }
}