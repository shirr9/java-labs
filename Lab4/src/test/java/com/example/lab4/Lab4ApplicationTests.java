package com.example.lab4;

import com.example.lab4.DTO.owner.OwnerCreateDTO;
import com.example.lab4.DTO.owner.OwnerResponseDTO;
import com.example.lab4.DTO.owner.OwnerUpdateDto;
import com.example.lab4.DTO.pet.PetCreateUpdateDTO;
import com.example.lab4.DTO.pet.PetResponseDTO;
import com.example.lab4.service.OwnerService;
import com.example.lab4.service.PetService;
import com.example.lab4.service.SecurityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityControllerTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private OwnerService ownerService;

    @MockitoBean
    private PetService petService;

    @MockitoBean
    private SecurityService securityService;

    private OwnerResponseDTO dummyOwner;
    private PetResponseDTO dummyPet;
    private PetCreateUpdateDTO petDto;

    @BeforeEach
    void setup() {
        dummyOwner = new OwnerResponseDTO();
        dummyOwner.setId(1L);
        dummyOwner.setName("Ivan");
        dummyOwner.setBirthDate(LocalDate.of(1990, 1, 1));
        dummyOwner.setPets(Collections.emptyList());

        given(ownerService.getById(1L)).willReturn(dummyOwner);
        given(ownerService.getFilteredOwners(anyString(), any(), any()))
                .willReturn(new PageImpl<>(Collections.singletonList(dummyOwner)));
        given(ownerService.save(any(OwnerCreateDTO.class))).willReturn(dummyOwner);
        given(ownerService.update(eq(1L), any(OwnerUpdateDto.class))).willReturn(dummyOwner);

        dummyPet = new PetResponseDTO();
        dummyPet.setId(1L);
        dummyPet.setName("Barsik");
        dummyPet.setBirthDate(LocalDate.of(2020, 1, 1));
        dummyPet.setBreed("Siam");
        dummyPet.setColor("Gray");
        dummyPet.setTailLength(10);
        dummyPet.setOwnerId(1L);
        dummyPet.setFriendsId(Collections.emptySet());

        petDto = new PetCreateUpdateDTO();
        petDto.setName("Barsik");
        petDto.setBirthDate(LocalDate.of(2020, 1, 1));
        petDto.setBreed("Siam");
        petDto.setColor("Gray");
        petDto.setTailLength(10);
        petDto.setOwnerId(1L);
        petDto.setFriendsId(Collections.emptySet());

        given(petService.getById(1L)).willReturn(dummyPet);
        given(petService.getFilteredPets(anyString(), anyString(), any(), any(), any()))
                .willReturn(new PageImpl<>(Collections.singletonList(dummyPet)));
        given(petService.save(any(PetCreateUpdateDTO.class))).willReturn(dummyPet);
        given(petService.update(eq(1L), any(PetCreateUpdateDTO.class))).willReturn(dummyPet);
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void getOwners_AsUser_Should200() throws Exception {
        mvc.perform(get("/owners"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void getOwnerById_AsAdmin_Should200() throws Exception {
        mvc.perform(get("/owners/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void createOwner_AsUser_Should403() throws Exception {
        mvc.perform(post("/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Ivan\",\"birthDate\":\"1990-01-01\",\"userId\":1}"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void createOwner_AsAdmin_Should200() throws Exception {
        mvc.perform(post("/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Ivan\",\"birthDate\":\"1990-01-01\",\"userId\":1}"))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(roles = {"USER"})
    void updateOwner_AsNonOwner_Should403() throws Exception {
        given(securityService.isOwner(1L)).willReturn(false);
        mvc.perform(put("/owners/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Ivan\",\"birthDate\":\"1990-01-01\"}"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void updateOwner_AsOwner_Should200() throws Exception {
        given(securityService.isOwner(1L)).willReturn(true);
        mvc.perform(put("/owners/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Ivan\",\"birthDate\":\"1990-01-01\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void updateOwner_AsAdmin_Should200() throws Exception {
        mvc.perform(put("/owners/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Ivan\",\"birthDate\":\"1990-01-01\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void deleteOwner_AsNonOwner_Should403() throws Exception {
        given(securityService.isOwner(1L)).willReturn(false);
        mvc.perform(delete("/owners/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void deleteOwner_AsOwner_Should200() throws Exception {
        given(securityService.isOwner(1L)).willReturn(true);
        mvc.perform(delete("/owners/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void deleteOwner_AsAdmin_Should200() throws Exception {
        mvc.perform(delete("/owners/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void deleteAllOwners_AsUser_Should403() throws Exception {
        mvc.perform(delete("/owners"))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithMockUser(roles = {"ADMIN"})
    void deleteAllOwners_AsAdmin_Should200() throws Exception {
        mvc.perform(delete("/owners"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void getPets_AsUser_Should200() throws Exception {
        mvc.perform(get("/pets"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void getPetById_AsAdmin_Should200() throws Exception {
        mvc.perform(get("/pets/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void createPet_AsNonOwner_Should403() throws Exception {
        given(securityService.isOwner(petDto.getOwnerId())).willReturn(false);
        mvc.perform(post("/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(petDto)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void createPet_AsOwner_Should200() throws Exception {
        given(securityService.isOwner(petDto.getOwnerId())).willReturn(true);
        mvc.perform(post("/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(petDto)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void createPet_AsAdmin_Should200() throws Exception {
        mvc.perform(post("/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(petDto)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void updatePet_AsNonOwnerOfPet_Should403() throws Exception {
        given(securityService.isOwnerOfPet(1L)).willReturn(false);
        mvc.perform(put("/pets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(petDto)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void updatePet_AsOwnerOfPet_Should200() throws Exception {
        given(securityService.isOwnerOfPet(1L)).willReturn(true);
        mvc.perform(put("/pets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(petDto)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void updatePet_AsAdmin_Should200() throws Exception {
        mvc.perform(put("/pets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(petDto)))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(roles = {"USER"})
    void deletePet_AsNonOwnerOfPet_Should403() throws Exception {
        given(securityService.isOwnerOfPet(1L)).willReturn(false);
        mvc.perform(delete("/pets/1"))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithMockUser(roles = {"USER"})
    void deletePet_AsOwnerOfPet_Should200() throws Exception {
        given(securityService.isOwnerOfPet(1L)).willReturn(true);
        mvc.perform(delete("/pets/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void deletePet_AsAdmin_Should200() throws Exception {
        mvc.perform(delete("/pets/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void deleteAllPets_AsUser_Should403() throws Exception {
        mvc.perform(delete("/pets"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void deleteAllPets_AsAdmin_Should200() throws Exception {
        mvc.perform(delete("/pets"))
                .andExpect(status().isOk());
    }
}