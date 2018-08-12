package com.thoughtworks.training.zhangtian.todoservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
//import com.sun.tools.javac.comp.Todo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class User {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "deleted", columnDefinition = "int default 0")
    private final Boolean deleted = false;

    @Transient
    String suggestion;
}
