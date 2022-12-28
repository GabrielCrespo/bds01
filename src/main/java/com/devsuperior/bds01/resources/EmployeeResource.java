package com.devsuperior.bds01.resources;

import com.devsuperior.bds01.dto.EmployeeDTO;
import com.devsuperior.bds01.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/employees")
public class EmployeeResource {

    @Autowired
    private EmployeeService service;

     @GetMapping
     public ResponseEntity<Page<EmployeeDTO>> findAll(Pageable pageable) {
         PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("name"));
         Page<EmployeeDTO> list = service.findAllPaged(pageRequest);
         return ResponseEntity.ok().body(list);
     }

     @PostMapping
     public ResponseEntity<EmployeeDTO> create(@RequestBody EmployeeDTO dto) {
         dto = service.create(dto);

         URI uri = ServletUriComponentsBuilder
                 .fromCurrentRequest()
                 .path("/{id}")
                 .buildAndExpand(dto.getId())
                 .toUri();

         return ResponseEntity.created(uri).body(dto);
     }

}