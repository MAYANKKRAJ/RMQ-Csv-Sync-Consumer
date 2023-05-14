package com.csv.consumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csv.consumer.entities.Employee;
import com.csv.consumer.repository.EmployeeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EmployeeService {
	@Autowired
    private EmployeeRepository employeeRepository;
		
	private static final Logger LOG = LoggerFactory.getLogger(EmployeeService.class);

	@RabbitListener(queues = "employee.queue")
    public void getEmployees(String message) throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
        Employee employee = objectMapper.readValue(message, Employee.class);
        LOG.info("Received employee: " + employee.toString());
        employeeRepository.save(employee);
        LOG.info("employeeId : {} saved ", employee.getEmployeeId());
    }
}
