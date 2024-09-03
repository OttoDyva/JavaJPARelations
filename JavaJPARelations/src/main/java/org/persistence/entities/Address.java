package org.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;

    @OneToMany(mappedBy = "address")
    private Set<Employee> employees = new HashSet();

    public void addEmployee(Employee emp) {
        if(employees == null)
            this.employees = new HashSet();
        this.employees.add(emp);
        emp.setAddress(this);
    }

    public void removeEmployee(Employee emp) {
        this.employees.remove(emp);
        emp.setAddress(null);
    }
}
