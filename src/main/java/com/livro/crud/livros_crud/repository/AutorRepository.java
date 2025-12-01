package com.livro.crud.livros_crud.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.livro.crud.livros_crud.entity.Autor;

public interface AutorRepository extends JpaRepository<Autor, Long>{

    public Optional<Autor> findById(Long id);


} 