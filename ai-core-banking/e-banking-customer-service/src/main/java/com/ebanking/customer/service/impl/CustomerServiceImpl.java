package com.ebanking.customer.service.impl;

import com.ebanking.customer.dto.request.CreateCustomerRequest;
import com.ebanking.customer.dto.request.UpdateCustomerRequest;
import com.ebanking.customer.dto.response.CustomerResponse;
import com.ebanking.customer.dto.response.CustomerStatsResponse;
import com.ebanking.customer.entity.Customer;
import com.ebanking.customer.enums.CustomerStatus;
import com.ebanking.customer.enums.KycStatus;
import com.ebanking.customer.exception.BusinessRuleException;
import com.ebanking.customer.exception.ResourceNotFoundException;
import com.ebanking.customer.mapper.CustomerMapper;
import com.ebanking.customer.repository.CustomerRepository;
import com.ebanking.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Implementation of CustomerService.
 * Contains all business logic for customer management.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    @Transactional
    public CustomerResponse createCustomer(CreateCustomerRequest request) {
        log.info("Creating customer with email: {}", request.getEmail());

        // Check email uniqueness
        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new BusinessRuleException("CUSTOMER_EMAIL_EXISTS",
                    "A customer with email '" + request.getEmail() + "' already exists");
        }

        Customer customer = customerMapper.toEntity(request);
        customer.setStatus(CustomerStatus.ACTIVE);
        customer.setKycStatus(KycStatus.PENDING);

        Customer saved = customerRepository.save(customer);
        log.info("Customer created with id: {}", saved.getId());

        return customerMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public CustomerResponse updateCustomer(Long id, UpdateCustomerRequest request) {
        log.info("Updating customer with id: {}", id);

        Customer customer = findCustomerById(id);

        // Only ACTIVE customers can be modified
        if (customer.getStatus() != CustomerStatus.ACTIVE) {
            throw new BusinessRuleException("CUSTOMER_INACTIVE",
                    "Cannot modify a customer with status: " + customer.getStatus());
        }

        // Update only provided fields (email is NOT modifiable)
        if (request.getFirstName() != null) {
            customer.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            customer.setLastName(request.getLastName());
        }
        if (request.getPhone() != null) {
            customer.setPhone(request.getPhone());
        }

        Customer saved = customerRepository.save(customer);
        log.info("Customer updated with id: {}", saved.getId());

        return customerMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public CustomerResponse deactivateCustomer(Long id) {
        log.info("Deactivating customer with id: {}", id);

        Customer customer = findCustomerById(id);

        if (customer.getStatus() == CustomerStatus.INACTIVE) {
            throw new BusinessRuleException("CUSTOMER_INACTIVE",
                    "Customer is already inactive");
        }

        customer.setStatus(CustomerStatus.INACTIVE);
        Customer saved = customerRepository.save(customer);
        log.info("Customer deactivated with id: {}", saved.getId());

        return customerMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponse findById(Long id) {
        log.debug("Finding customer by id: {}", id);
        Customer customer = findCustomerById(id);
        return customerMapper.toResponse(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerResponse> searchCustomers(String search, Pageable pageable) {
        log.debug("Searching customers with term: {}", search);

        if (search == null || search.isBlank()) {
            return customerRepository.findAll(pageable)
                    .map(customerMapper::toResponse);
        }

        return customerRepository.searchByTerm(search, pageable)
                .map(customerMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerResponse> findAll(Pageable pageable) {
        log.debug("Finding all customers");
        return customerRepository.findAll(pageable)
                .map(customerMapper::toResponse);
    }

    @Override
    @Transactional
    public CustomerResponse updateKycStatus(Long id, String kycStatus) {
        log.info("Updating KYC status for customer {}: {}", id, kycStatus);

        Customer customer = findCustomerById(id);

        try {
            KycStatus newStatus = KycStatus.valueOf(kycStatus.toUpperCase());
            customer.setKycStatus(newStatus);
        } catch (IllegalArgumentException e) {
            throw new BusinessRuleException("CUSTOMER_VALIDATION_ERROR",
                    "Invalid KYC status: " + kycStatus + ". Valid values: PENDING, VERIFIED, REJECTED");
        }

        Customer saved = customerRepository.save(customer);
        log.info("KYC status updated for customer {}: {}", id, kycStatus);

        return customerMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerStatsResponse getStats() {
        log.debug("Generating customer statistics");

        long total = customerRepository.count();
        long active = customerRepository.countByStatus(CustomerStatus.ACTIVE);
        long inactive = customerRepository.countByStatus(CustomerStatus.INACTIVE);
        long suspended = customerRepository.countByStatus(CustomerStatus.SUSPENDED);

        return CustomerStatsResponse.builder()
                .timestamp(LocalDateTime.now())
                .totalCustomers(total)
                .activeCustomers(active)
                .inactiveCustomers(inactive)
                .suspendedCustomers(suspended)
                .additionalData(Map.of(
                        "activePercentage", total > 0 ? (double) active / total * 100 : 0
                ))
                .build();
    }

    /**
     * Internal helper to find a customer or throw ResourceNotFoundException.
     */
    private Customer findCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
    }
}
