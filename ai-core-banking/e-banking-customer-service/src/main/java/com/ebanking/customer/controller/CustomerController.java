package com.ebanking.customer.controller;

import com.ebanking.customer.dto.request.CreateCustomerRequest;
import com.ebanking.customer.dto.request.UpdateCustomerRequest;
import com.ebanking.customer.dto.response.CustomerResponse;
import com.ebanking.customer.dto.response.CustomerStatsResponse;
import com.ebanking.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for customer management operations.
 * Base path: /api/customers
 */
@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Tag(name = "Customer Management", description = "Operations for managing bank customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @Operation(summary = "Create a new customer")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Customer created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "409", description = "Email already exists"),
            @ApiResponse(responseCode = "503", description = "System in idle mode")
    })
    public ResponseEntity<CustomerResponse> createCustomer(
            @Valid @RequestBody CreateCustomerRequest request) {
        CustomerResponse created = customerService.createCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing customer")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data or customer inactive"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "503", description = "System in idle mode")
    })
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCustomerRequest request) {
        CustomerResponse updated = customerService.updateCustomer(id, request);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate a customer")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer deactivated successfully"),
            @ApiResponse(responseCode = "400", description = "Customer already inactive"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "503", description = "System in idle mode")
    })
    public ResponseEntity<CustomerResponse> deactivateCustomer(@PathVariable Long id) {
        CustomerResponse deactivated = customerService.deactivateCustomer(id);
        return ResponseEntity.ok(deactivated);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get customer by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer found"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    public ResponseEntity<CustomerResponse> getById(@PathVariable Long id) {
        CustomerResponse customer = customerService.findById(id);
        return ResponseEntity.ok(customer);
    }

    @GetMapping
    @Operation(summary = "Search customers with pagination")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customers retrieved successfully")
    })
    public ResponseEntity<Page<CustomerResponse>> searchCustomers(
            @Parameter(description = "Search term (firstName, lastName, or email)")
            @RequestParam(required = false) String search,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<CustomerResponse> customers = customerService.searchCustomers(search, pageable);
        return ResponseEntity.ok(customers);
    }

    @PatchMapping("/{id}/kyc")
    @Operation(summary = "Update KYC status of a customer")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "KYC status updated"),
            @ApiResponse(responseCode = "400", description = "Invalid KYC status"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "503", description = "System in idle mode")
    })
    public ResponseEntity<CustomerResponse> updateKycStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        CustomerResponse updated = customerService.updateKycStatus(id, status);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/stats")
    @Operation(summary = "Get customer statistics for reporting")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Statistics retrieved successfully")
    })
    public ResponseEntity<CustomerStatsResponse> getStats() {
        CustomerStatsResponse stats = customerService.getStats();
        return ResponseEntity.ok(stats);
    }
}
