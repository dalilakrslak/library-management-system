package ba.unsa.etf.transfer;

import ba.unsa.etf.transfer.api.model.Transfer;
import ba.unsa.etf.transfer.core.impl.TransferServiceImpl;
import ba.unsa.etf.transfer.core.mapper.TransferMapper;
import ba.unsa.etf.transfer.core.validation.TransferValidation;
import ba.unsa.etf.transfer.dao.model.TransferEntity;
import ba.unsa.etf.transfer.dao.repository.TransferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferTests {
    @Mock
    private TransferRepository transferRepository;

    @Mock
    private TransferMapper transferMapper;

    @Mock
    private TransferValidation transferValidation;

    @InjectMocks
    private TransferServiceImpl transferService;

    private Transfer transfer;
    private TransferEntity transferEntity;

    @BeforeEach
    void setUp() {
        transfer = new Transfer(1L, "123456789", 1L, 2L, LocalDate.now());
        transferEntity = new TransferEntity(1L, null, null, null, LocalDate.now());
    }

    @Test
    void findAll_ShouldReturnTransfers() {
        when(transferRepository.findAll()).thenReturn(List.of(transferEntity));
        when(transferMapper.entityToDto(transferEntity)).thenReturn(transfer);

        List<Transfer> result = transferService.findAll();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(transfer.getId(), result.get(0).getId());
    }

    @Test
    void findById_ShouldReturnTransfer_WhenExists() {
        when(transferRepository.findById(1L)).thenReturn(Optional.of(transferEntity));
        when(transferMapper.entityToDto(transferEntity)).thenReturn(transfer);

        Transfer result = transferService.findById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void findById_ShouldReturnNull_WhenNotExists() {
        when(transferRepository.findById(1L)).thenReturn(Optional.empty());
        Transfer result = transferService.findById(1L);
        assertNull(result);
    }

    @Test
    void create_ShouldSaveAndReturnTransfer() {
        when(transferMapper.dtoToEntity(transfer)).thenReturn(transferEntity);
        when(transferRepository.save(transferEntity)).thenReturn(transferEntity);
        when(transferMapper.entityToDto(transferEntity)).thenReturn(transfer);

        Transfer result = transferService.create(transfer);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void update_ShouldSaveAndReturnUpdatedTransfer() {
        when(transferRepository.existsById(1L)).thenReturn(true);
        when(transferMapper.dtoToEntity(transfer)).thenReturn(transferEntity);
        when(transferRepository.save(transferEntity)).thenReturn(transferEntity);
        when(transferMapper.entityToDto(transferEntity)).thenReturn(transfer);

        Transfer result = transferService.update(transfer);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void update_ShouldReturnNull_WhenTransferDoesNotExist() {
        when(transferRepository.existsById(1L)).thenReturn(false);
        Transfer result = transferService.update(transfer);
        assertNull(result);
    }

    @Test
    void delete_ShouldCallRepositoryDeleteById() {
        doNothing().when(transferValidation).exists(1L);
        doNothing().when(transferRepository).deleteById(1L);

        transferService.delete(1L);

        verify(transferRepository, times(1)).deleteById(1L);
    }
}