package com.api.restfull.ecommerce.application.service;

import com.api.restfull.ecommerce.application.request.ClientRequest;
import com.api.restfull.ecommerce.application.response.ClientResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClientService {

    @Transactional
    ClientResponse createClient(ClientRequest request);

    @Transactional
    ClientResponse getByIdClient(Long id);

    @Transactional
    Page<ClientResponse> getAllPagedClients(int page, int size);

    @Transactional
    ClientResponse updateClient(ClientRequest request);

    @Transactional
    List<ClientResponse> searchByNameClients(String name);

    @Transactional
    List<ClientResponse> findByDateOfBirthLikeClient(String dateOfBirth);

    @Transactional
    ClientResponse getByCpfClient(String cpf);

    @Transactional
    ClientResponse getByEmailClient(String email);

    @Transactional
    ClientResponse desableClient(Long id);

    @Transactional
    List<ClientResponse> getByStatusClients(Boolean active);

    @Transactional
    void deleteClient(Long id);
}
