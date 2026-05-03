package com.nereuvitor.localeatsapi.services;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nereuvitor.localeatsapi.models.Address;
import com.nereuvitor.localeatsapi.repositories.AddressRepository;
import com.nereuvitor.localeatsapi.services.exceptions.DataBaseException;
import com.nereuvitor.localeatsapi.services.exceptions.ObjectNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    @Transactional
    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    @Transactional
    public Address findById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Endereço não encontrado! Id: " + id));
    }

    @Transactional
    public Address insert(Address obj) {
        return addressRepository.save(obj);
    }

    @Transactional
    public Address update(Long id, Address obj) {
        Address entity = findById(id);
        updateData(entity, obj);
        return addressRepository.save(entity);
    }

    @Transactional
    public void delete(Long id) {
        
        if (!addressRepository.existsById(id)) {
            throw new ObjectNotFoundException("Endereço não encontrado! Id: " + id);
        }

        try {
            addressRepository.deleteById(id);            
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Não é possível excluir o endereço pois ele está vinculado a um usuário ou pedido ativo.");
        }        
    }

    private void updateData(Address entity, Address obj) {
        entity.setStreet(obj.getStreet());
        entity.setNumber(obj.getNumber());
        entity.setNeighborhood(obj.getNeighborhood());
        entity.setComplement(obj.getComplement());
        entity.setCity(obj.getCity());
        entity.setState(obj.getState());
        entity.setZipCode(obj.getZipCode());
    }
}
