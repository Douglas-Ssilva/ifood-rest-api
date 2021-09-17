package com.algaworks.algafood.domain.infrastructure.storage;

import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;

import com.algaworks.algafood.domain.exception.StorageException;
import com.algaworks.algafood.domain.service.FotoStorageService;

//@Service
public class LocalStorageService implements FotoStorageService {

	@Value("${algafood.storage.local.diretorio-fotos}")
	private Path diretorioFotos; 
	
	@Override
	public void armazenar(NovaFoto novaFoto) {
		try {
			var pathCompleto = getPathCompleto(novaFoto.getNome());
			//passo o fluxo de entrada e no 2° argumento o fluxo de saída
			FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(pathCompleto));
		} catch (Exception e) {
			throw new StorageException("Não foi possível armazenar a foto no disco local", e);
		}
	}

	@Override
	public void remover(String nomeFotoPersistido) {
		try {
			if (StringUtils.isNoneBlank(nomeFotoPersistido)) {
				Files.delete(getPathCompleto(nomeFotoPersistido));
			}
		} catch (Exception e) {
			throw new StorageException("Não foi possível remover a foto no disco local", e);
		}
	}
	
	@Override
	public FotoRecuperada buscarArquivo(String nomeArquivo) {
		try {
			return FotoRecuperada.builder()
			.inputStream(Files.newInputStream(getPathCompleto(nomeArquivo)))
			.build();
		} catch (Exception e) {
			throw new StorageException("Não foi possível buscar a foto no disco local", e);
		}
	}
	
	private Path getPathCompleto(String nome) {
		return this.diretorioFotos.resolve(Path.of(nome));
	}


}
