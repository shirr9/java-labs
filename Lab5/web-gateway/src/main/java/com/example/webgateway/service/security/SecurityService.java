package com.example.webgateway.service.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.webgateway.config.RabbitConfig;
import com.example.webgateway.dto.OwnerResponseDTO;
import com.example.webgateway.dto.PetResponseDTO;
import com.example.webgateway.model.MyUser;
import com.example.webgateway.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    private final RabbitTemplate rabbitTemplate;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public SecurityService(RabbitTemplate rabbitTemplate,
                           UserRepository userRepository,
                           ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    public boolean isOwner(Long ownerId) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        MyUser user = userRepository.findByName(username).orElse(null);
        if (user == null) {
            return false;
        }

        OwnerResponseDTO owner = getOwnerByUsername(username);
        if (owner == null) {
            return false;
        }

        return owner.getId().equals(ownerId);
    }

    public boolean isOwnerOfPet(Long petId) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        MyUser user = userRepository.findByName(username).orElse(null);
        if (user == null) {
            return false;
        }

        OwnerResponseDTO owner = getOwnerByUsername(username);
        if (owner == null) {
            return false;
        }

        PetResponseDTO pet = getPetById(petId);
        if (pet == null) {
            return false;
        }

        return pet.getOwnerId().equals(owner.getId());
    }

    private OwnerResponseDTO getOwnerByUsername(String username) throws Exception {
        var request = new java.util.HashMap<String, Object>();
        request.put("command", "getByUserName");
        request.put("username", username);
        request.put("replyTo", RabbitConfig.REPLY_QUEUE);

        String requestJson = objectMapper.writeValueAsString(request);
        String responseJson = (String) rabbitTemplate.convertSendAndReceive(RabbitConfig.OWNERS_QUEUE, requestJson);

        if (responseJson == null || responseJson.isEmpty()) {
            return null;
        }
        return objectMapper.readValue(responseJson, OwnerResponseDTO.class);
    }

    private PetResponseDTO getPetById(Long petId) throws Exception {
        var request = new java.util.HashMap<String, Object>();
        request.put("command", "getById");
        request.put("id", petId);
        request.put("replyTo", RabbitConfig.REPLY_QUEUE);

        String requestJson = objectMapper.writeValueAsString(request);
        String responseJson = (String) rabbitTemplate.convertSendAndReceive(RabbitConfig.PETS_QUEUE, requestJson);

        if (responseJson == null || responseJson.isEmpty()) {
            return null;
        }
        return objectMapper.readValue(responseJson, PetResponseDTO.class);
    }
}