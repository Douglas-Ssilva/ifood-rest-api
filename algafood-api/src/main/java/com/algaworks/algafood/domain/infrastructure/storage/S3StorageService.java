package com.algaworks.algafood.domain.infrastructure.storage;

import org.springframework.beans.factory.annotation.Autowired;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.exception.StorageException;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

//@Service
public class S3StorageService implements FotoStorageService {

	@Autowired
	private AmazonS3 amazonS3;
	
	@Autowired
	private StorageProperties properties;

	@Override
	public void armazenar(NovaFoto novaFoto) {
		try {
			var caminhoArquivo = getCaminho(novaFoto.getNome());
			var metadata = new ObjectMetadata();
			metadata.setContentType(novaFoto.getContentType());//pra saber que se trata de uma imagem e não fazer o download direto do file
			var objectRequest = new PutObjectRequest(
					properties.getS3().getBucket(), 
					caminhoArquivo, 
					novaFoto.getInputStream(), 
					metadata)
				.withCannedAcl(CannedAccessControlList.PublicRead); //para que fique visível a todos
			amazonS3.putObject(objectRequest);
		} catch (Exception e) {
			throw new StorageException("Não foi possível armazenar a foto na Amazon S3", e);
		}
	}

	@Override
	public void remover(String nomeFotoPersistido) {
		try {
			var caminhoArquivo = getCaminho(nomeFotoPersistido);
			var deleteObjectRequest = new DeleteObjectRequest(
					properties.getS3().getBucket(), 
					caminhoArquivo);
			amazonS3.deleteObject(deleteObjectRequest);
		} catch (Exception e) {
			throw new StorageException("Não foi possível excluir a foto na Amazon S3", e);
		}
	}

	/**
	 * Não há necessidade de processar uma request na API já que o s3 pode entregar os arquivos diretamente dos servers da Amazon
	 * com muito mais performance. Assim não gastamos nem memória nem processamento
	 */
	@Override
	public FotoRecuperada buscarArquivo(String nomeArquivo) {
		var caminhoArquivo = getCaminho(nomeArquivo);
		var url = amazonS3.getUrl(properties.getS3().getBucket(), caminhoArquivo);//não faz a request, apenas monta
		return FotoRecuperada.builder().url(url.toString()).build();
	}
	
	public String getCaminho(String nomeFoto) {
		return String.format("%s/%s", properties.getS3().getDiretorioFotos(), nomeFoto);
	}
}
