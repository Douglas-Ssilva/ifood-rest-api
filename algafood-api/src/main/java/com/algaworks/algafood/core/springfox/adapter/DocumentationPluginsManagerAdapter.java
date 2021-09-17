package com.algaworks.algafood.core.springfox.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.stereotype.Component;

import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.DefaultsProviderPlugin;
import springfox.documentation.spi.service.ResourceGroupingStrategy;
import springfox.documentation.spi.service.contexts.DocumentationContextBuilder;
import springfox.documentation.spring.web.SpringGroupingStrategy;
import springfox.documentation.spring.web.plugins.DefaultConfiguration;
import springfox.documentation.spring.web.plugins.DocumentationPluginsManager;

/**
 * Workaround para resolver problemas de versão de dependência transitiva entre
 * Spring HATEOAS e Spring fox
 * https://github.com/springfox/springfox/issues/2932
 *
 */

@Primary
@Component
@SuppressWarnings("deprecation")
public class DocumentationPluginsManagerAdapter extends DocumentationPluginsManager {

	@Autowired
	@Qualifier("resourceGroupingStrategyRegistry")
	private PluginRegistry<ResourceGroupingStrategy, DocumentationType> resourceGroupingStrategies;

	@Autowired
	@Qualifier("defaultsProviderPluginRegistry")
	private PluginRegistry<DefaultsProviderPlugin, DocumentationType> defaultsProviders;

	@Override
	public DocumentationContextBuilder createContextBuilder(DocumentationType documentationType,
			DefaultConfiguration defaultConfiguration) {
		return defaultsProviders.getPluginOrDefaultFor(documentationType, defaultConfiguration)
				.create(documentationType).withResourceGroupingStrategy(resourceGroupingStrategy(documentationType));
	}

	@Override
	public ResourceGroupingStrategy resourceGroupingStrategy(DocumentationType documentationType) {
		return resourceGroupingStrategies.getPluginOrDefaultFor(documentationType, new SpringGroupingStrategy());
	}
}
