package org.myapp.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.myapp.dao.CatDao;
import org.myapp.dao.OwnerDao;
import org.myapp.dto.CatDto;
import org.myapp.entity.Cat;
import org.myapp.entity.Owner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CatServiceTest {
    @Mock(name = "catDao")
    private CatDao catDao;
    @Mock(name = "ownerDao")
    private OwnerDao ownerDao;

    private CatService catService;

    @BeforeEach
    public void init() {
        catService = new CatService(catDao, ownerDao);
    }

    @Test
    void createSuccessful() {
        CatDto catDto = new CatDto.Builder()
                .setName("cat")
                .setColor("black")
                .setWeight(4)
                .setHeight(20)
                .setOwnerName("test_owner")
                .setOwnerAge(23)
                .build();
        Mockito.doReturn(1L).when(catDao).create(any(Cat.class));

        assertEquals(1L, catService.create(catDto));
    }

    @Test
    void createFailed() {
        CatDto catDto = new CatDto.Builder()
                .setName("cat")
                .setColor("black")
                .setWeight(4)
                .setHeight(20)
                .setOwnerName("test_owner")
                .setOwnerAge(23)
                .build();
        Mockito.doReturn(-1L).when(catDao).create(any(Cat.class));

        assertEquals(-1L, catService.create(catDto));
    }

    @Test
    void searchSuccessful() {
        ArrayList<Cat> cats = new ArrayList<>();
        ArrayList<CatDto> expected = new ArrayList<>();
        Mockito.doReturn(cats).when(catDao).search();

        List<CatDto> result = catService.search();

        assertNotNull(result);
        assertEquals(expected, result);
        verify(catDao, times(1)).search();
        verifyNoMoreInteractions(catDao);
    }

    @Test
    void searchFailed() {
        ArrayList<Cat> cats = new ArrayList<>();
        Mockito.doReturn(cats).when(catDao).search();

        List<CatDto> result = catService.search();

        assertNotEquals(null, result);
        verify(catDao, times(1)).search();
        verifyNoMoreInteractions(catDao);
    }

    @Test
    void updateSuccessful() {
        CatDto catDto = new CatDto.Builder()
                .setName("cat")
                .setColor("black")
                .setWeight(4)
                .setHeight(20)
                .setOwnerName("test_owner")
                .setOwnerAge(23)
                .build();

        Mockito.doReturn(Optional.of(new Cat.Builder().build())).when(catDao).searchBy(any(long.class));
        Mockito.doReturn(Optional.of(new Owner.Builder().setName("name").setAge(1).build())).when(ownerDao).searchBy(any(long.class));
        Mockito.doReturn(1L).when(catDao).update(any(Cat.class));

        assertEquals(1L, catService.update(catDto));
        verify(catDao, times(1)).update(any(Cat.class));
        verifyNoMoreInteractions(catDao);
    }

    @Test
    void updateFailed() {
        CatDto catDto = new CatDto.Builder()
                .setName("cat")
                .setColor("black")
                .setWeight(4)
                .setHeight(20)
                .setOwnerName("test_owner")
                .setOwnerAge(23)
                .build();

        Mockito.doReturn(Optional.of(new Cat.Builder().build())).when(catDao).searchBy(any(long.class));
        Mockito.doReturn(Optional.of(new Owner.Builder().setName("name").setAge(1).build())).when(ownerDao).searchBy(any(long.class));
        Mockito.doReturn(-1L).when(catDao).update(any(Cat.class));

        assertEquals(-1L, catService.update(catDto));
        verify(catDao, times(1)).update(any(Cat.class));
        verifyNoMoreInteractions(catDao);
    }

    @Test
    void deleteSuccessful() {
        Mockito.doReturn(true).when(catDao).delete(any(long.class));
        assertTrue(catService.delete(any(long.class)));

        verify(catDao, times(1)).delete(any(long.class));
        verifyNoMoreInteractions(catDao);
    }

    @Test
    void deleteFailed() {
        Mockito.doReturn(false).when(catDao).delete(any(long.class));
        assertFalse(catService.delete(any(long.class)));

        verify(catDao, times(1)).delete(any(long.class));
        verifyNoMoreInteractions(catDao);
    }
}