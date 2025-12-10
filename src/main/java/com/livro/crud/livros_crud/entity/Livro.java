package com.livro.crud.livros_crud.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Livro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Nonnull
    private String titulo;
    private int ano;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("livros")
    //@JoinColumun(name = "autor_id")
    private Autor autor;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("livros")
    //@JoinColumn(name = "editora_id")
    private Editora editora;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "livro_proprietario")
    private List<Proprietario> proprietarios;

}
