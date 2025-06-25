package com.example.webgateway.service;

import com.example.webgateway.config.RabbitConfig;
import com.example.webgateway.dto.OwnerCreateDTO;
import com.example.webgateway.dto.OwnerResponseDTO;
import com.example.webgateway.dto.OwnerUpdateDto;
import com.example.webgateway.dto.PageDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class OwnerGatewayService {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public OwnerGatewayService(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public OwnerResponseDTO save(OwnerCreateDTO ownerCreateDTO) throws Exception {
        Map<String, Object> request = new HashMap<>();
        request.put("command", "save");
        request.put("owner", ownerCreateDTO);
        request.put("replyTo", RabbitConfig.REPLY_QUEUE);

        String requestJson = objectMapper.writeValueAsString(request);
        String responseJson = (String) rabbitTemplate.convertSendAndReceive(RabbitConfig.OWNERS_QUEUE, requestJson);

        return objectMapper.readValue(responseJson, OwnerResponseDTO.class);
    }

    public void deleteById(Long id) throws Exception {
        Map<String, Object> request = new HashMap<>();
        request.put("command", "deleteById");
        request.put("id", id);
        request.put("replyTo", RabbitConfig.REPLY_QUEUE);

        rabbitTemplate.convertSendAndReceive(RabbitConfig.OWNERS_QUEUE, objectMapper.writeValueAsString(request));
    }

    public void deleteAll() throws Exception {
        Map<String, Object> request = new HashMap<>();
        request.put("command", "deleteAll");
        request.put("replyTo", RabbitConfig.REPLY_QUEUE);

        rabbitTemplate.convertSendAndReceive(RabbitConfig.OWNERS_QUEUE, objectMapper.writeValueAsString(request));
    }

    public OwnerResponseDTO update(Long id, OwnerUpdateDto ownerUpdateDTO) throws Exception {
        Map<String, Object> request = new HashMap<>();
        request.put("command", "update");
        request.put("id", id);
        request.put("owner", ownerUpdateDTO);
        request.put("replyTo", RabbitConfig.REPLY_QUEUE);

        String requestJson = objectMapper.writeValueAsString(request);
        String responseJson = (String) rabbitTemplate.convertSendAndReceive(RabbitConfig.OWNERS_QUEUE, requestJson);

        return objectMapper.readValue(responseJson, OwnerResponseDTO.class);
    }

    public OwnerResponseDTO getById(Long id) throws Exception {
        Map<String, Object> request = new HashMap<>();
        request.put("command", "getById");
        request.put("id", id);
        request.put("replyTo", RabbitConfig.REPLY_QUEUE);

        String requestJson = objectMapper.writeValueAsString(request);
        String responseJson = (String) rabbitTemplate.convertSendAndReceive(RabbitConfig.OWNERS_QUEUE, requestJson);

        return objectMapper.readValue(responseJson, OwnerResponseDTO.class);
    }

    public Page<OwnerResponseDTO> getFilteredOwners(String name, LocalDate birthDate, Pageable pageable) throws Exception {
        Map<String, Object> request = new HashMap<>();
        request.put("command", "getFilteredOwners");
        request.put("name", name);
        request.put("birthDate", birthDate != null ? birthDate.format(DateTimeFormatter.ISO_DATE) : null);
        request.put("page", pageable.getPageNumber());
        request.put("size", pageable.getPageSize());
        request.put("replyTo", RabbitConfig.REPLY_QUEUE);

        String requestJson = objectMapper.writeValueAsString(request);
        String responseJson = (String) rabbitTemplate.convertSendAndReceive(RabbitConfig.OWNERS_QUEUE, requestJson);

        PageDTO<OwnerResponseDTO> pageDTO = objectMapper.readValue(responseJson,
                objectMapper.getTypeFactory().constructParametricType(PageDTO.class, OwnerResponseDTO.class));

        return new PageImpl<>(pageDTO.getContent(),
                PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize()), pageDTO.getTotalElements());
    }

    public java.util.List<OwnerResponseDTO> getAll() throws Exception {
        Map<String, Object> request = new HashMap<>();
        request.put("command", "getAll");
        request.put("replyTo", RabbitConfig.REPLY_QUEUE);

        String requestJson = objectMapper.writeValueAsString(request);
        String responseJson = (String) rabbitTemplate.convertSendAndReceive(RabbitConfig.OWNERS_QUEUE, requestJson);

        return objectMapper.readValue(responseJson,
                objectMapper.getTypeFactory().constructCollectionType(java.util.List.class, OwnerResponseDTO.class));
    }
}
