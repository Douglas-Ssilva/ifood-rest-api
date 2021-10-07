package com.algaworks.algafood.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.UsuarioDTOAssembler;
import com.algaworks.algafood.api.assembler.UsuarioInputDTODisassembler;
import com.algaworks.algafood.api.controller.openapi.UserControllerOpenApi;
import com.algaworks.algafood.api.model.UsuarioDTO;
import com.algaworks.algafood.api.model.input.UsuarioInputDTO;
import com.algaworks.algafood.api.model.input.UsuarioInputSemSenhaDTO;
import com.algaworks.algafood.api.model.input.UsuarioInputUpdateSenhaAntigaENovaDTO;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping(path = "/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController implements UserControllerOpenApi {
	
	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;
	
	@Autowired
	private UsuarioDTOAssembler usuarioDTOAssembler;
	
	@Autowired
	private UsuarioInputDTODisassembler usuarioInputDTODisassembler;
	
	@Override
	@GetMapping
	public CollectionModel<UsuarioDTO> findAll(){
		return usuarioDTOAssembler.toCollectionModelUsuarios(cadastroUsuarioService.findAll());
	}
	
	@Override
	@GetMapping("/{userId}")
	public UsuarioDTO findById(@PathVariable Long userId){
		return usuarioDTOAssembler.toModel(cadastroUsuarioService.findById(userId));
	}
	
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioDTO add(@RequestBody @Valid UsuarioInputDTO dto) {
		var usuario = usuarioInputDTODisassembler.toDomainObject(dto);
		return usuarioDTOAssembler.toModel(cadastroUsuarioService.merge(usuario));
	}
	
	@Override
	@PutMapping("/{userId}")
	public UsuarioDTO update(@RequestBody @Valid UsuarioInputSemSenhaDTO dto, @PathVariable Long userId) {
		var usuario = cadastroUsuarioService.findById(userId);
		usuarioInputDTODisassembler.copyProperties(dto, usuario);
		return usuarioDTOAssembler.toModel(cadastroUsuarioService.merge(usuario));
	}
	
	@Override
	@PutMapping("/{userId}/senha")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateSenha(@RequestBody @Valid UsuarioInputUpdateSenhaAntigaENovaDTO dto, @PathVariable Long userId) {
		cadastroUsuarioService.updateSenha(userId, dto.getSenhaAtual(), dto.getSenhaNova());
	}
	
	@Override
	@DeleteMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long userId) {
		cadastroUsuarioService.delete(userId);
	}
	
}
