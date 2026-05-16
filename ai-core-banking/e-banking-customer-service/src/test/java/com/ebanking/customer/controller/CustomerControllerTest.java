package com.ebanking.customer.controller;

import com.ebanking.customer.dto.request.CreateCustomerRequest;
import com.ebanking.customer.dto.response.CustomerResponse;
import com.ebanking.customer.enums.CustomerStatus;
import com.ebanking.customer.enums.KycStatus;
import com.ebanking.customer.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.bean.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for CustomerController REST endpoints.
 */
@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;

    private CustomerResponse buildSampleResponse() {
        return CustomerResponse.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("+33612345678")
                .status(CustomerStatus.ACTIVE)
                .kycStatus(KycStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("POST /api/customers — should create customer")
    void shouldCreateCustomer() throws Exception {
        CreateCustomerRequest request = CreateCustomerRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("+33612345678")
                .build();

        when(customerService.createCustomer(any(CreateCustomerRequest.class)))
                .thenReturn(buildSampleResponse());

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    @DisplayName("POST /api/customers — should return 400 for invalid data")
    void shouldReturn400ForInvalidData() throws Exception {
        CreateCustomerRequest request = CreateCustomerRequest.builder()
                .firstName("")  // blank — invalid
                .lastName("Doe")
                .email("not-an-email")  // invalid email
                .build();

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/customers/{id} — should return customer")
    void shouldGetCustomerById() throws Exception {
        when(customerService.findById(1L)).thenReturn(buildSampleResponse());

        mockMvc.perform(get("/api/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    @DisplayName("GET /api/customers — should return paginated list")
    void shouldSearchCustomers() throws Exception {
        Page<CustomerResponse> page = new PageImpl<>(List.of(buildSampleResponse()));
        when(customerService.searchCustomers(any(), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/customers")
                        .param("search", "John")
                        .param("page", "0")
                        .param("size", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].firstName").value("John"));
    }

    @Test
    @DisplayName("PATCH /api/customers/{id}/deactivate — should deactivate")
    void shouldDeactivateCustomer() throws Exception {
        CustomerResponse deactivated = buildSampleResponse();
        deactivated.setStatus(CustomerStatus.INACTIVE);
        when(customerService.deactivateCustomer(1L)).thenReturn(deactivated);

        mockMvc.perform(patch("/api/customers/1/deactivate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("INACTIVE"));
    }

    @Test
    @DisplayName("PUT /api/customers/{id} — should update customer")
    void shouldUpdateCustomer() throws Exception {
        when(customerService.updateCustomer(any(), any())).thenReturn(buildSampleResponse());

        String body = """
                {
                    "firstName": "Jane",
                    "lastName": "Smith",
                    "phone": "+33698765432"
                }
                """;

        mockMvc.perform(put("/api/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/customers/stats — should return stats")
    void shouldReturnStats() throws Exception {
        when(customerService.getStats()).thenReturn(
                com.ebanking.customer.dto.response.CustomerStatsResponse.builder()
                        .totalCustomers(100)
                        .activeCustomers(80)
                        .inactiveCustomers(15)
                        .suspendedCustomers(5)
                        .build());

        mockMvc.perform(get("/api/customers/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCustomers").value(100))
                .andExpect(jsonPath("$.activeCustomers").value(80));
    }
}
