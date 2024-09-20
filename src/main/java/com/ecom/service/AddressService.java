package com.ecom.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.model.Address;
import com.ecom.repository.AddressRepository;


@Service
public class AddressService {

	@Autowired
	private AddressRepository addressRepo;
	public Address createAddress(Address address) {
		return addressRepo.save(address);
	}

	public Address updateAddress(Integer addressId, Address address) {
        Address existingAddress = addressRepo.findById(addressId)
                .orElseThrow();
            existingAddress.setStreet(address.getStreet());
            existingAddress.setCity(address.getCity());
            existingAddress.setState(address.getState());
            existingAddress.setPinCode(address.getPinCode());
            return addressRepo.save(existingAddress);
	}

	public Address getAddressById(Integer addressId) {
		return addressRepo.findById(addressId)
	            .orElseThrow();
	}

	public List<Address> getAllAddresses() {
		return addressRepo.findAll();

	}

	public void deleteAddress(Integer addressId) {
		addressRepo.deleteById(addressId);
		
	}

}
