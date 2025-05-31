package com.example.petservice.listener;

import com.example.petservice.DTO.PetCreateUpdateDTO;
import com.example.petservice.DTO.PetResponseDTO;
import com.example.petservice.service.PetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
public class PetMessageListener {

    private final PetService petService;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public PetMessageListener(PetService petService, RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.petService = petService;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @SuppressWarnings("unchecked")
    @RabbitListener(queues = RabbitConfigPet.PET_QUEUE)
    public void handleMessage(String message) throws Exception {
        Map<String, Object> request = objectMapper.readValue(message, Map.class);

        String command = (String) request.get("command");
        String replyTo = (String) request.get("replyTo");

        Object response;

        switch (command) {
            case "save":
                PetCreateUpdateDTO createDTO = objectMapper.convertValue(request.get("pet"), PetCreateUpdateDTO.class);
                response = petService.save(createDTO);
                break;

            case "deleteById":
                Long deleteId = ((Number) request.get("id")).longValue();
                petService.deleteById(deleteId);
                response = "Deleted pet with id " + deleteId;
                break;

            case "deleteAll":
                petService.deleteAll();
                response = "All pets deleted";
                break;

            case "update":
                Long updateId = ((Number) request.get("id")).longValue();
                PetCreateUpdateDTO updateDTO = objectMapper.convertValue(request.get("pet"), PetCreateUpdateDTO.class);
                response = petService.update(updateId, updateDTO);
                break;

            case "getById":
                Long id = ((Number) request.get("id")).longValue();
                response = petService.getById(id);
                break;

            case "getFilteredPets":
                String name = (String) request.get("name");
                String breed = (String) request.get("breed");
                Integer tailLength = request.get("tailLength") != null ? ((Number) request.get("tailLength")).intValue() : null;
                String birthDateStr = (String) request.get("birthDate");
                LocalDate birthDate = null;
                if (birthDateStr != null && !birthDateStr.isEmpty()) {
                    birthDate = LocalDate.parse(birthDateStr, DateTimeFormatter.ISO_DATE);
                }
                int page = request.get("page") != null ? ((Number) request.get("page")).intValue() : 0;
                int size = request.get("size") != null ? ((Number) request.get("size")).intValue() : 10;
                Pageable pageable = PageRequest.of(page, size);
                Page<PetResponseDTO> filteredPage = petService.getFilteredPets(name == null ? "" : name,
                        breed == null ? "" : breed, tailLength, birthDate, pageable);
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