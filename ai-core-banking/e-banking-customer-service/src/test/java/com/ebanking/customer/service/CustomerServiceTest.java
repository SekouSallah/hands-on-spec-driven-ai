package com.ebanking.customer.service;

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
import com.ebanking.customer.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for CustomerServiceImpl.
 */
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer sampleCustomer;
    private CustomerResponse sampleResponse;
    private CreateCustomerRequest createRequest;
    private UpdateCustomerRequest updateRequest;

    @BeforeEach
    void setUp() {
        sampleCustomer = Customer.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("+33612345678")
                .status(CustomerStatus.ACTIVE)
                .kycStatus(KycStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        sampleResponse = CustomerResponse.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("+33612345678")
                .status(CustomerStatus.ACTIVE)
                .kycStatus(KycStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        createRequest = CreateCustomerRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("+33612345678")
                .build();

        updateRequest = UpdateCustomerRequest.builder()
                .firstName("Jane")
                .lastName("Smith")
                .phone("+33698765432")
                .build();
    }

    @Nested
    @DisplayName("Create Customer")
    class CreateCustomerTests {

        @Test
        @DisplayName("should create customer successfully")
        void shouldCreateCustomer() {
            when(customerRepository.existsByEmail(anyString())).thenReturn(false);
            when(customerMapper.toEntity(any(CreateCustomerRequest.class))).thenReturn(sampleCustomer);
            when(customerRepository.save(any(Customer.class))).thenReturn(sampleCustomer);
            when(customerMapper.toResponse(any(Customer.class))).thenReturn(sampleResponse);

            CustomerResponse result = customerService.createCustomer(createRequest);

            assertThat(result).isNotNull();
            assertThat(result.getEmail()).isEqualTo("john.doe@example.com");
            assertThat(result.getStatus()).isEqualTo(CustomerStatus.ACTIVE);
            verify(customerRepository).save(any(Customer.class));
        }

        @Test
        @DisplayName("should throw exception when email already exists")
        void shouldThrowWhenEmailExists() {
            when(customerRepository.existsByEmail(anyString())).thenReturn(true);

            assertThatThrownBy(() -> customerService.createCustomer(createRequest))
                    .isInstanceOf(BusinessRuleException.class)
                    .hasMessageContaining("already exists");

            verify(customerRepository, never()).save(any(Customer.class));
        }
    }

    @Nested
    @DisplayName("Update Customer")
    class UpdateCustomerTests {

        @Test
        @DisplayName("should update customer successfully")
        void shouldUpdateCustomer() {
            when(customerRepository.findById(1L)).thenReturn(Optional.of(sampleCustomer));
            when(customerRepository.save(any(Customer.class))).thenReturn(sampleCustomer);
            when(customerMapper.toResponse(any(Customer.class))).thenReturn(sampleResponse);

            CustomerResponse result = customerService.updateCustomer(1L, updateRequest);

            assertThat(result).isNotNull();
            verify(customerRepository).save(any(Customer.class));
        }

        @Test
        @DisplayName("should throw exception when customer not found")
        void shouldThrowWhenCustomerNotFound() {
            when(customerRepository.findById(99L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> customerService.updateCustomer(99L, updateRequest))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessageContaining("Customer");
        }

        @Test
        @DisplayName("should throw exception when customer is inactive")
        void shouldThrowWhenCustomerInactive() {
            sampleCustomer.setStatus(CustomerStatus.INACTIVE);
            when(customerRepository.findById(1L)).thenReturn(Optional.of(sampleCustomer));

            assertThatThrownBy(() -> customerService.updateCustomer(1L, updateRequest))
                    .isInstanceOf(BusinessRuleException.class)
                    .hasMessageContaining("INACTIVE");
        }
    }

    @Nested
    @DisplayName("Deactivate Customer")
    class DeactivateCustomerTests {

        @Test
        @DisplayName("should deactivate customer successfully")
        void shouldDeactivateCustomer() {
            CustomerResponse deactivatedResponse = CustomerResponse.builder()
                    .id(1L)
                    .status(CustomerStatus.INACTIVE)
                    .build();

            when(customerRepository.findById(1L)).thenReturn(Optional.of(sampleCustomer));
            when(customerRepository.save(any(Customer.class))).thenReturn(sampleCustomer);
            when(customerMapper.toResponse(any(Customer.class))).thenReturn(deactivatedResponse);

            CustomerResponse result = customerService.deactivateCustomer(1L);

            assertThat(result.getStatus()).isEqualTo(CustomerStatus.INACTIVE);
            verify(customerRepository).save(any(Customer.class));
        }

        @Test
        @DisplayName("should throw exception when already inactive")
        void shouldThrowWhenAlreadyInactive() {
            sampleCustomer.setStatus(CustomerStatus.INACTIVE);
            when(customerRepository.findById(1L)).thenReturn(Optional.of(sampleCustomer));

            assertThatThrownBy(() -> customerService.deactivateCustomer(1L))
                    .isInstanceOf(BusinessRuleException.class)
                    .hasMessageContaining("already inactive");
        }
    }

    @Nested
    @DisplayName("Find Customer")
    class FindCustomerTests {

        @Test
        @DisplayName("should find customer by ID")
        void shouldFindById() {
            when(customerRepository.findById(1L)).thenReturn(Optional.of(sampleCustomer));
            when(customerMapper.toResponse(any(Customer.class))).thenReturn(sampleResponse);

            CustomerResponse result = customerService.findById(1L);

            assertThat(result).isNotNull();
            assertThat(result.getId()).isEqualTo(1L);
        }

        @Test
        @DisplayName("should throw exception when customer not found")
        void shouldThrowWhenNotFound() {
            when(customerRepository.findById(99L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> customerService.findById(99L))
                    .isInstanceOf(ResourceNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("Search Customers")
    class SearchCustomerTests {

        @Test
        @DisplayName("should search customers by term")
        void shouldSearchByTerm() {
            Pageable pageable = PageRequest.of(0, 20);
            Page<Customer> customerPage = new PageImpl<>(List.of(sampleCustomer));

            when(customerRepository.searchByTerm("John", pageable)).thenReturn(customerPage);
            when(customerMapper.toResponse(any(Customer.class))).thenReturn(sampleResponse);

            Page<CustomerResponse> result = customerService.searchCustomers("John", pageable);

            assertThat(result.getContent()).hasSize(1);
        }

        @Test
        @DisplayName("should return all when search term is blank")
        void shouldReturnAllWhenBlank() {
            Pageable pageable = PageRequest.of(0, 20);
            Page<Customer> customerPage = new PageImpl<>(List.of(sampleCustomer));

            when(customerRepository.findAll(pageable)).thenReturn(customerPage);
            when(customerMapper.toResponse(any(Customer.class))).thenReturn(sampleResponse);

            Page<CustomerResponse> result = customerService.searchCustomers("", pageable);

            assertThat(result.getContent()).hasSize(1);
            verify(customerRepository).findAll(pageable);
        }
    }

    @Nested
    @DisplayName("KYC Status")
    class KycStatusTests {

        @Test
        @DisplayName("should update KYC status")
        void shouldUpdateKycStatus() {
            when(customerRepository.findById(1L)).thenReturn(Optional.of(sampleCustomer));
            when(customerRepository.save(any(Customer.class))).thenReturn(sampleCustomer);
            when(customerMapper.toResponse(any(Customer.class))).thenReturn(sampleResponse);

            CustomerResponse result = customerService.updateKycStatus(1L, "VERIFIED");

            assertThat(result).isNotNull();
            verify(customerRepository).save(any(Customer.class));
        }

        @Test
        @DisplayName("should throw on invalid KYC status")
        void shouldThrowOnInvalidStatus() {
            when(customerRepository.findById(1L)).thenReturn(Optional.of(sampleCustomer));

            assertThatThrownBy(() -> customerService.updateKycStatus(1L, "INVALID"))
                    .isInstanceOf(BusinessRuleException.class)
                    .hasMessageContaining("Invalid KYC status");
        }
    }

    @Nested
    @DisplayName("Customer Stats")
    class StatsTests {

        @Test
        @DisplayName("should return customer statistics")
        void shouldReturnStats() {
            when(customerRepository.count()).thenReturn(100L);
            when(customerRepository.countByStatus(CustomerStatus.ACTIVE)).thenReturn(80L);
            when(customerRepository.countByStatus(CustomerStatus.INACTIVE)).thenReturn(15L);
            when(customerRepository.countByStatus(CustomerStatus.SUSPENDED)).thenReturn(5L);

            CustomerStatsResponse stats = customerService.getStats();

            assertThat(stats.getTotalCustomers()).isEqualTo(100);
            assertThat(stats.getActiveCustomers()).isEqualTo(80);
            assertThat(stats.getInactiveCustomers()).isEqualTo(15);
            assertThat(stats.getSuspendedCustomers()).isEqualTo(5);
        }
    }
}
