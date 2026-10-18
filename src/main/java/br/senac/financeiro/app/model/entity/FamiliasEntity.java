package br.senac.financeiro.app.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "familias")
@Data
@NoArgsConstructor
public class FamiliasEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "limite_gastos_mensal")
    private Double limiteGastosMensal = 0.0;
}