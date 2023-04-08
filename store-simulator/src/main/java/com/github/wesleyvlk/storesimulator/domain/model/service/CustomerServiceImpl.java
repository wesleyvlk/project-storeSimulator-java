    package com.github.wesleyvlk.storesimulator.domain.model.service;

    import com.github.wesleyvlk.storesimulator.domain.model.Customer;
    import com.github.wesleyvlk.storesimulator.domain.model.Sale;
    import com.github.wesleyvlk.storesimulator.domain.repository.CustomerRepository;
    import jakarta.transaction.Transactional;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    @Service
    public class CustomerServiceImpl implements CustomerService {

        @Autowired
        private CustomerRepository customerRepository;

        @Override
        @Transactional
        public Customer createCustomer(Customer customer) {
            Sale sale = new Sale();
            sale.setCustomer(customer);
            return customerRepository.save(customer);
        }

        @Override
        public Customer findCustomerByCpf(String cpf) {
            return customerRepository.findByCpf(cpf);
        }

    }
