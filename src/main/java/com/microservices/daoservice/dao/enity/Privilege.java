package com.microservices.daoservice.dao.enity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.microservices.daoservice.dao.enity.enums.PrivilegeType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@Table(name = "privileges", schema = "accounts")
@SequenceGenerator(name = "sq_privileges", sequenceName = "sq_privileges", allocationSize = 1, schema = "accounts")
public class Privilege {

    public Privilege(PrivilegeType pt){
        this.privilegeType = pt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_privileges")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private PrivilegeType privilegeType;

    @ManyToMany(mappedBy = "privileges", targetEntity = Role.class)
    @JsonIgnoreProperties("privileges")
    private Collection<Role> roles;
}