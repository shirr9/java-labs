package com.Lab3;

import com.Lab3.DTO.pet.PetCreateDTO;
import com.Lab3.DTO.pet.PetResponseDTO;
import com.Lab3.DTO.pet.PetUpdateDTO;
import com.Lab3.controller.PetController;
import com.Lab3.service.PetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PetController.class)
class PetControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockitoBean
	private PetService petService;

	private PetResponseDTO sample;

	@BeforeEach
	void setup() {
		sample = new PetResponseDTO();
		sample.setId(1L);
		sample.setName("Rex");
		sample.setBirthDate(LocalDate.of(2020,1,1));
		sample.setBreed("Labrador");
		sample.setColor("BLACK");
		sample.setTailLength(10);
		sample.setOwnerId(5L);
	}

	@Test
	void getAll_returnsPage() throws Exception {
		given(petService.getFilteredPets(anyString(), anyString(), any(), any(), any(PageRequest.class)))
				.willReturn(new PageImpl<>(List.of(sample)));

		mvc.perform(get("/pets")
						.param("name","")
						.param("breed","")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content[0].name").value("Rex"));
	}

	@Test
	void getById_returnsDto() throws Exception {
		given(petService.getById(1L)).willReturn(sample);

		mvc.perform(get("/pets/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.breed").value("Labrador"));
	}


	@Test
	void deleteById_invokesService() throws Exception {
		mvc.perform(delete("/pets/1"))
				.andExpect(status().isOk());
		verify(petService).deleteById(1L);
	}

	@Test
	void deleteAll_invokesService() throws Exception {
		mvc.perform(delete("/pets"))
				.andExpect(status().isOk());
		verify(petService).deleteAll();
	}

	@Test
	void updatePet_returnsDto() throws Exception {
		String json = """
            {
              "name":"Max",
              "tailLength":12
            }
            """;
		sample.setName("Max");
		sample.setTailLength(12);
		given(petService.update(eq(1L), any(PetUpdateDTO.class))).willReturn(sample);


		mvc.perform(put("/pets/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Max"))
				.andExpect(jsonPath("$.tailLength").value(12));
	}
}
