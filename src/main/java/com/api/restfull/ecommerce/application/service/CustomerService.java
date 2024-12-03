package com.api.restfull.ecommerce.application.service;

import com.api.restfull.ecommerce.application.request.customer.CustomerRequest;
import com.api.restfull.ecommerce.application.request.customer.CustomerUpRequest;
import com.api.restfull.ecommerce.application.response.customer.CustomerListResponse;
import com.api.restfull.ecommerce.application.response.customer.CustomerResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {

    @Transactional
    CustomerListResponse createCustomer(CustomerRequest request);

    @Transactional
    CustomerListResponse getByIdCustomer(Long id);

    @Transactional
    Page<CustomerResponse> getAllPagedCustomers(int page, int size);

    @Transactional
    CustomerListResponse updateCustomer(CustomerUpRequest request);

    @Transactional
    List<CustomerResponse> searchByNameCustomers(String name);

    @Transactional
    List<CustomerResponse> findByDateOfBirthLikeCustomer(String dateOfBirth);

    @Transactional
    CustomerListResponse getByCpfCustomer(String cpf);

    @Transactional
    CustomerListResponse getByEmailCustomer(String email);

    @Transactional
    CustomerResponse desableCustomer(Long id);

    @Transactional
    List<CustomerResponse> getByStatusCustomers(Boolean active);

    @Transactional
    void deleteCustomer(Long id);
}
