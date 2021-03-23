package com.example.project.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class User extends ObjectMongo {

    private String username = "";
    private String name = "", surname = "", phone = " ", fiscalCode = "";
    private String email = "";
    private List<Address> address = new ArrayList<>();
    private LocalDateTime creation;

}
