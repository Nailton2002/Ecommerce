package com.api.restfull.ecommerce.application.service_imple;

import com.api.restfull.ecommerce.application.request.ClientRequest;
import com.api.restfull.ecommerce.application.response.ClientResponse;
import com.api.restfull.ecommerce.application.service.ClientService;
import com.api.restfull.ecommerce.domain.entity.Client;
import com.api.restfull.ecommerce.domain.exception.BusinessRuleException;
import com.api.restfull.ecommerce.domain.exception.ResourceNotFoundException;
import com.api.restfull.ecommerce.domain.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;

    @Override
    public ClientResponse createClient(ClientRequest request) {
        // Verifica se o CPF já existe no banco de dados
        if (repository.findByCpf(request.cpf()).isPresent()) {
            throw new ResourceNotFoundException("Não é possível criar o cliente. O CPF já existe na base de dados.");
        }
        //Verificando se o emai já existe no banco de dados
        if (repository.findByEmail(request.email()).isPresent()) {
            throw new ResourceNotFoundException("Não é possível criar o cliente. O email já existe na base de dados.");
        }
        // Cria uma nova instância de Client usando o DTO
        Client client = new Client(request);
        return new ClientResponse(repository.save(client));
    }

    @Override
    public ClientResponse getByIdClient(Long id) {
        Client client = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID não encontrado! ID: " + id + ", "));
        return new ClientResponse(client);
    }

    @Override
    public Page<ClientResponse> getAllPagedClients(int page, int size) {
        // Cria uma Pageable para paginar os dados
        Pageable pageable = PageRequest.of(page, size);
        // Chama o repositório para obter a página de clientes
        Page<Client> clientsPage = repository.findAll(pageable);
        // Converte os clientes em ClientResponse
        return clientsPage.map(ClientResponse::new);
    }

    @Override
    public ClientResponse updateClient(ClientRequest request) {
        // Busca o cliente pelo ID no banco de dados
        Client client = repository.findById(request.id()).orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o ID: " + request.id()));
        // Verifica se o CPF fornecido já pertence a outro cliente
        repository.findByCpf(request.cpf()).ifPresent(existingClient -> {
            if (!existingClient.getId().equals(request.id())) {
                throw new BusinessRuleException("CPF já está associado a outro cliente.");
            }
        });
        //Verifica se o EMAIL fornecido já pertence a outro cliente
        Optional<Client> email = repository.findByEmail(request.email());
        if (email.isPresent() && !email.get().getId().equals(request.id())) {
            throw new BusinessRuleException("EMAIL já está associado a outro cliente.");
        }
        client.updateClient(request);
        return new ClientResponse(repository.save(client));
    }

    @Override
    public List<ClientResponse> searchByNameClients(String name) {
        List<Client> clients = repository.findByNameContainingIgnoreCase(name);
        return clients.stream().map(ClientResponse::new).toList();
    }

    @Override
    public List<ClientResponse> findByDateOfBirthLikeClient(String dateOfBirth) {
//        String datePartial = "1985-02"; // Procurando por fevereiro de 1985
        List<Client> clients = repository.findByDateOfBirthLike(dateOfBirth);
        return clients.stream().map(ClientResponse::new).toList();
    }

    @Override
    public ClientResponse getByCpfClient(String cpf) {
        Optional<Client> byCpf = repository.findByCpf(cpf);
        if (byCpf.isEmpty()) {
            throw new BusinessRuleException("Cliente não encontrado com CPF começando por: " + cpf);
        }
        return new ClientResponse(byCpf.get());
    }

    @Override
    public ClientResponse getByEmailClient(String email) {
        Optional<Client> byEmail = repository.findByEmail(email);
        if (byEmail.isPresent()) {
            return new ClientResponse(byEmail.get());
        }
        throw new BusinessRuleException("Cliente não encontrado com E-MAIL: " + email);
    }

    @Override
    public ClientResponse desableClient(Long id) {
        Client client = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + id));
        client.clientDesactive();
        return new ClientResponse(repository.save(client));
    }

    @Override
    public List<ClientResponse> getByStatusClients(Boolean active) {
        List<Client> byActives = repository.findByActives(active);
        return byActives.stream().map(ClientResponse::new).toList();
    }

    @Override
    public void deleteClient(Long id) {
        Client client = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + id));
        if (client.isActive()) {
            throw new ResourceNotFoundException("Cliente ativo, não pode ser excluido!");
        }
        repository.delete(client);
    }
}
