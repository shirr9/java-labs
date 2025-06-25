package com.example.ownerservice.listener;

import com.example.ownerservice.DTO.OwnerCreateDTO;
import com.example.ownerservice.DTO.OwnerResponseDTO;
import com.example.ownerservice.DTO.OwnerUpdateDto;
import com.example.ownerservice.service.OwnerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class OwnerMessageListener {

    private final OwnerService ownerService;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public OwnerMessageListener(OwnerService ownerService, RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.ownerService = ownerService;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @SuppressWarnings("unchecked")
    @RabbitListener(queues = "owners.queue")
    public void handleMessage(String message) throws Exception {
        Map<String, Object> request = objectMapper.readValue(message, Map.class);

        String command = (String) request.get("command");
        String replyTo = (String) request.get("replyTo");

        Object response;

        switch (command) {
            case "save":
                OwnerCreateDTO createDTO = objectMapper.convertValue(request.get("owner"), OwnerCreateDTO.class);
                response = ownerService.save(createDTO);
                break;

            case "deleteById":
                Long deleteId = ((Number) request.get("id")).longValue();
                ownerService.deleteById(deleteId);
                response = "Deleted owner with id " + deleteId;
                break;

            case "deleteAll":
                ownerService.deleteAll();
                response = "All owners deleted";
                break;

            case "update":
                Long updateId = ((Number) request.get("id")).longValue();
                OwnerUpdateDto updateDto = objectMapper.convertValue(request.get("owner"), OwnerUpdateDto.class);
                response = ownerService.update(updateId, updateDto);
                break;

            case "getById":
                Long id = ((Number) request.get("id")).longValue();
                response = ownerService.getById(id);
                break;

            case "getAll":
                List<OwnerResponseDTO> owners = ownerService.getAll();
                response = owners;
                break;

            case "getFilteredOwners":
                String name = (String) request.get("name");
                String birthDateStr = (String) request.get("birthDate");
                LocalDate birthDate = null;
                if (birthDateStr != null && !birthDateStr.isEmpty()) {
                    birthDate = LocalDate.parse(birthDateStr, DateTimeFormatter.ISO_DATE);
                }
                int page = request.get("page") != null ? ((Number) request.get("page")).intValue() : 0;
                int size = request.get("size") != null ? ((Number) request.get("size")).intValue() : 10;
                Pageable pageable = PageRequest.of(page, size);
                Page<OwnerResponseDTO> filteredPage = ownerService.getFilteredOwners(name == null ? "" : name, birthDate, pageable);
                response = filteredPage;
                break;

            default:
                response = "Unknown command: " + command;
        }

        if (replyTo != null) {
            String jsonResponse = objectMapper.writeValueAsString(response);
            rabbitTemplate.convertAndSend(replyTo, jsonResponse);
        }
    }
}
