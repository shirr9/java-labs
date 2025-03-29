import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lab2.dao.OwnerDao;
import org.lab2.dao.PetDao;
import org.lab2.entity.Color;
import org.lab2.entity.Owner;
import org.lab2.entity.Pet;
import org.lab2.services.impl.OwnerServiceImpl;
import org.lab2.services.impl.PetServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @Mock
    private PetDao petDao;

    @InjectMocks
    private PetServiceImpl petService;

    private Owner testOwner;
    private Pet testPet;

    @BeforeEach
    void setUp() {
        testOwner = new Owner("Anna Smith", LocalDate.of(1995, 5, 20));
        testPet = new Pet("Rex", LocalDate.of(2022, 1, 15), "Labrador", Color.BROWN);
        testPet.setOwner(testOwner);
    }

    @Test
    void createPet_ShouldSaveNewPet() {
        when(petDao.save(any(Pet.class))).thenReturn(testPet);

        Pet result = petService.save(testPet);

        assertEquals("Rex", result.getName());
        assertEquals("Labrador", result.getBreed());
        verify(petDao).save(testPet);
    }

    @Test
    void getPetById_ShouldReturnCorrectPet() {
        testPet.setId(1L);
        when(petDao.getById(1L)).thenReturn(testPet);

        Pet result = petService.getById(1L);

        assertEquals(testPet, result);
        verify(petDao).getById(1L);
    }

    @Test
    void updatePet_ShouldChangePetDetails() {
        testPet.setId(1L);
        when(petDao.getById(1L)).thenReturn(testPet);
        when(petDao.save(any(Pet.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Pet updatedPet = new Pet("Max", LocalDate.of(2022, 1, 15), "Golden Retriever", Color.MULTICOLOR);
        updatedPet.setId(1L);
        updatedPet.setOwner(testOwner);

        Pet result = petService.update(updatedPet);

        assertEquals("Max", result.getName());
        assertEquals("Golden Retriever", result.getBreed());
        assertEquals(Color.MULTICOLOR, result.getColor());
        verify(petDao).save(updatedPet);
    }

    @Test
    void deletePetById_ShouldCallDaoDelete() {
        doNothing().when(petDao).deleteById(1L);

        petService.deleteById(1L);

        verify(petDao).deleteById(1L);
    }

    @Test
    void getAllPets_ShouldReturnPetList() {
        List<Pet> pets = List.of(testPet);
        when(petDao.getAll()).thenReturn(pets);

        List<Pet> result = petService.getAll();

        assertEquals(1, result.size());
        assertEquals("Rex", result.get(0).getName());
        verify(petDao).getAll();
    }

    @Test
    void savePetWithoutOwner_ShouldThrowException() {
        Pet petWithoutOwner = new Pet("Ghost", LocalDate.now(), "Unknown", Color.GREEN);

        assertThrows(IllegalArgumentException.class, () -> petService.save(petWithoutOwner));
        verify(petDao, never()).save(any(Pet.class));
    }
}

@ExtendWith(MockitoExtension.class)
class OwnerServiceTest {

    @Mock
    private OwnerDao ownerDao;

    @InjectMocks
    private OwnerServiceImpl ownerService;

    private Owner testOwner;

    @BeforeEach
    void setUp() {
        testOwner = new Owner("John Doe", LocalDate.of(1985, 10, 10));
    }

    @Test
    void createOwner_ShouldSaveNewOwner() {
        when(ownerDao.save(any(Owner.class))).thenReturn(testOwner);

        Owner result = ownerService.save(testOwner);

        assertEquals("John Doe", result.getName());
        verify(ownerDao).save(testOwner);
    }

    @Test
    void getOwnerById_ShouldReturnCorrectOwner() {
        testOwner.setId(1L);
        when(ownerDao.getById(1L)).thenReturn(testOwner);

        Owner result = ownerService.getById(1L);

        assertEquals(testOwner, result);
        verify(ownerDao).getById(1L);
    }

    @Test
    void updateOwner_ShouldChangeOwnerName() {
        testOwner.setId(1L);
        when(ownerDao.getById(1L)).thenReturn(testOwner);
        when(ownerDao.save(any(Owner.class))).thenAnswer(invocation -> invocation.getArgument(0));;

        Owner updatedOwner = new Owner("Jane Doe", LocalDate.of(1985, 10, 10));
        updatedOwner.setId(1L);

        Owner result = ownerService.update(updatedOwner);

        assertEquals("Jane Doe", result.getName());
        verify(ownerDao).save(updatedOwner);
    }

    @Test
    void deleteOwnerById_ShouldCallDaoDelete() {
        doNothing().when(ownerDao).deleteById(1L);

        ownerService.deleteById(1L);

        verify(ownerDao).deleteById(1L);
    }

    @Test
    void getAllOwners_ShouldReturnOwnerList() {
        List<Owner> owners = List.of(testOwner);
        when(ownerDao.getAll()).thenReturn(owners);

        List<Owner> result = ownerService.getAll();

        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
        verify(ownerDao).getAll();
    }

    @Test
    void updateNonExistingOwner_ShouldThrowException() {
        Owner nonExistingOwner = new Owner("Ghost", LocalDate.now());
        nonExistingOwner.setId(999L);
        when(ownerDao.getById(999L)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> ownerService.update(nonExistingOwner));
        verify(ownerDao, never()).save(any(Owner.class));
    }
}