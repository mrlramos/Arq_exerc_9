package com.example.restservice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RestserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestserviceApplication.class, args);
	}
	
	private DAO dao;
	
	public RestserviceApplication() {
		dao = new DAO();
	}
	
	@GetMapping("/cidade")
	public List<Cidade> getCidade() {
		return dao.getCidades();
	}

	@PostMapping("/cidade")
	public ResponseEntity<?> addCidade(@RequestBody Cidade cidade) {
		
		Cidade retorno = dao.addCidade(cidade);
		if (retorno != null) {
			return ResponseEntity.ok(cidade);
		}
		return ResponseEntity.badRequest().body("ID já cadastrado");
	}

	@GetMapping("/cidade/{id}")
	public Cidade getCidade(@PathVariable("id") Long id) {
		return dao.findCidadeById(id);
	}

	@PutMapping("/cidade/{id}")
	public Cidade updateCidade(@PathVariable("id") Long id, @RequestBody Cidade cidade) {
		return dao.updateCidade(id, cidade);
	}


	
	@DeleteMapping("/cidade/{id}")
	public HttpStatus deleteCidade(@PathVariable("id") Long id) {
		if(dao.deleteCidade(id)){
			return HttpStatus.OK;
		}
		return HttpStatus.BAD_REQUEST;
	}


	
	
	
}

class Cidade {
	
	private Long id;
	private String nome;
	private Estado estado;
	
	
	public Cidade() {
		super();
	}

	public Cidade(Long id, String nome, Estado estado) {
		super();
		this.id = id;
		this.nome = nome;
		this.estado = estado;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	
}

class Estado {
	
	private Long id;
	private String nome;
	public Estado(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}
	public Estado() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
}

class DAO {
	
	public DAO() {
		super();
		this.cidades = new ArrayList<>();
	}

	private List<Cidade> cidades;

	public List<Cidade> getCidades() {
		return cidades;
	}
	
	public Cidade addCidade(Cidade cidade) {
		if(!cidades.stream().filter(f -> f.getId().equals(cidade.getId())).findAny().isPresent()) {
			cidades.add(cidade);
			return cidade;
		}
		return null;
	}
	
	public Cidade findCidadeById(Long id) {
		return cidades.stream().filter(f -> f.getId().equals(id)).collect(Collectors.toList()).get(0);
	}
	
	public Cidade updateCidade(Long id, Cidade cidade) {
		Cidade func = cidades.stream().filter(f -> f.getId().equals(id)).findFirst().get();
		int index = cidades.indexOf(func);
		cidades.set(index, cidade);
		return cidades.get(index);
	}
	
	public Boolean deleteCidade(Long id) {
		return cidades.remove(cidades.stream().filter(f -> f.getId().equals(id)).findFirst().get());
	}
}


