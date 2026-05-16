package com.ebanking.customer.service;

import com.ebanking.customer.dto.request.CreateCustomerRequest;
import com.ebanking.customer.dto.request.UpdateCustomerRequest;
import com.ebanking.customer.dto.response.CustomerResponse;
import com.ebanking.customer.dto.response.CustomerStatsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for customer management operations.
 */
public interface CustomerService {

    /**
     * Create a new customer.
     * @param request the creation request
     * @return the created customer
     */
    CustomerResponse createCustomer(CreateCustomerRequest request);

    /**
     * Update an existing customer.
     * Note: email is not modifiable after creation.
     * @param id the customer ID
     * @param request the update request
     * @return the updated customer
     */
    CustomerResponse updateCustomer(Long id, UpdateCustomerRequest request);

    /**
     * Deactivate a customer (set status to INACTIVE).
     * @param id the customer ID
     * @return the deactivated customer
     */
    CustomerResponse deactivateCustomer(Long id);

    /**
     * Find a customer by ID.
     * @param id the customer ID
     * @return the customer
     */
    CustomerResponse findById(Long id);

    /**
     * Search customers by term (firstName, lastName, or email).
     * @param search the search term
     * @param pageable pagination information
     * @return paginated list of customers
     */
    Page<CustomerResponse> searchCustomers(String search, Pageable pageable);

    /**
     * Get all customers with pagination.
     * @param pageable pagination information
     * @return paginated list of customers
     */
    Page<CustomerResponse> findAll(Pageable pageable);

    /**
     * Update KYC status of a customer.
     * @param id the customer ID
     * @param kycStatus the new KYC status
     * @return the updated customer
     */
    CustomerResponse updateKycStatus(Long id, String kycStatus);

    /**
     * Get customer statistics for reporting.
     * @return customer statistics
     */
    CustomerStatsResponse getStats();
}
