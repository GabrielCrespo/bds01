package com.devsuperior.bds01.services;

import com.devsuperior.bds01.dto.EmployeeDTO;
import com.devsuperior.bds01.entities.Department;
import com.devsuperior.bds01.entities.Employee;
import com.devsuperior.bds01.repositories.DepartmentRepository;
import com.devsuperior.bds01.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Transactional(readOnly = true)
    public Page<EmployeeDTO> findAllPaged(Pageable pageable) {
        return repository
                .findAll(pageable)
                .map(employee -> new EmployeeDTO(employee));
    }

    @Transactional
    public EmployeeDTO create(EmployeeDTO dto) {
        Employee employee = new Employee();
        copyDtoToEntity(dto, employee);
        employee = repository.save(employee);
        return new EmployeeDTO(employee);
    }

    private void copyDtoToEntity(EmployeeDTO dto, Employee employee) {

        Department department = departmentRepository.findById(dto.getDepartmentId()).get();

        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setDepartment(department);
    }

}
