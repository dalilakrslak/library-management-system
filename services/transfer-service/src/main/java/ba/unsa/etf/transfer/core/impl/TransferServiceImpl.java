package ba.unsa.etf.transfer.core.impl;

import ba.unsa.etf.transfer.api.model.Transfer;
import ba.unsa.etf.transfer.api.service.TransferService;
import ba.unsa.etf.transfer.core.mapper.TransferMapper;
import ba.unsa.etf.transfer.core.validation.TransferValidation;
import ba.unsa.etf.transfer.dao.model.TransferEntity;
import ba.unsa.etf.transfer.dao.repository.TransferRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransferServiceImpl implements TransferService {
    private final TransferRepository transferRepository;
    private final TransferMapper transferMapper;
    private final TransferValidation transferValidation;

    @Override
    public List<Transfer> findAll() {
        return transferRepository.findAll()
                .stream()
                .map(transferMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Transfer findById(Long id) {
        transferValidation.exists(id);
        return transferRepository.findById(id).map(transferMapper::entityToDto).orElse(null);
    }

    @Override
    @Transactional
    public Transfer create(Transfer transfer) {
        TransferEntity transferEntity = transferMapper.dtoToEntity(transfer);
        transferRepository.save(transferEntity);
        return transferMapper.entityToDto(transferEntity);
    }

    @Override
    @Transactional
    public Transfer update(Transfer transfer) {
        if (!transferRepository.existsById(transfer.getId())) {
            return null;
        }
        TransferEntity transferEntity = transferMapper.dtoToEntity(transfer);
        transferRepository.save(transferEntity);
        return transferMapper.entityToDto(transferEntity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        transferValidation.exists(id);
        transferRepository.deleteById(id);
    }

    @Override
    public List<Transfer> findByBookVersionIn(List<String> bookVersions) {
        List<TransferEntity> transfers = transferRepository.findByBookVersionInAndTransferDateIsNull(bookVersions);

        return transfers.stream().map(transferMapper::entityToDto).collect(Collectors.toList());
    }
}
