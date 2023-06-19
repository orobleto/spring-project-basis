package com.octaviorobleto.utilities.core.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Proporciona una interfaz generica para controladores en la API
 * 
 * @author <a href="https://octaviorobleto.com" target="_blank">Octavio
 *         Robleto</a>
 * @version 1.0
 * @date 2022-07-13
 * @class GenericRestController
 * 
 * @param <E>  Objeto que se espera como parametro en los metodos
 * @param <ID> Identificador(Clave Primaria,Key) del Objeto E
 */
public interface GenericRestController<E, ID> {

	@GetMapping(path = { "/findById/{id}" })
	ResponseEntity<E> findById(@PathVariable(name = "id") ID id);

	@PostMapping(path = { "/insert" }, consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<E> insert(@RequestBody @Valid E e, BindingResult bindingResult);

	@PutMapping(path = { "/update" }, consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<E> update(@RequestBody @Valid E e, BindingResult bindingResult);

	@DeleteMapping(path = { "/delete" }, consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<E> delete(@RequestBody @Valid E e, BindingResult bindingResult);

	@GetMapping(path = { "/findAll" })
	ResponseEntity<List<E>> findAll();

}