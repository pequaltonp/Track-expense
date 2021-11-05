package com.example.demo.model;

import com.example.demo.service.PostgreSQLEnumType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "expenses")
@TypeDef(
        name = "expense_type",
        typeClass = PostgreSQLEnumType.class
)
public class Expenses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "expenses_date")
    @NotNull
    private LocalDate date;

    @Column(columnDefinition = "type")
    @Enumerated(EnumType.STRING)
    @Type(type = "expense_type")
    private ExpenseType type;

    @Column(name = "expense")
    private Double expense;

}
