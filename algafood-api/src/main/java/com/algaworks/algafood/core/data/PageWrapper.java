package com.algaworks.algafood.core.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

/**
 * solução encontrada para links da paginação, pois estava montando as URLs com nome da propriedade que foi traduzida no handlePageable
 */
public class PageWrapper<T> extends PageImpl<T> {

	private static final long serialVersionUID = 1L;
	private Pageable pageable;
	
	public PageWrapper(Page<T> page, Pageable pageable) {
		super(page.getContent(), pageable, page.getTotalElements());
		this.pageable = pageable;
	}
	
	@Override
	public Pageable getPageable() {
		return this.pageable;
	}

}
