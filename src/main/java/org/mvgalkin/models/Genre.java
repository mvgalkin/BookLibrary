package org.mvgalkin.models;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Table("genres")
@Data
public class Genre {
    @Id
    private Long id;

    private String name;

}
