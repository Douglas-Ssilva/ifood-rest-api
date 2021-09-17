package com.algaworks.algafood.core.squiggly;

//Referências:
//- https://stackoverflow.com/a/53613678
//- https://tomcat.apache.org/tomcat-8.5-doc/config/http.html
//- https://docs.spring.io/spring-boot/docs/current/reference/html/howto.html#howto-configure-webserver
//- Criado para podermos passar [] nos param a invés de colocar o encoding respecivo deles. [{"key":"campos","value":"codigo,restaurante.nome,cliente[id,nome]","description":""}]
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;

//@Component
public class TomcatCustomizer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

 @Override
 public void customize(TomcatServletWebServerFactory factory) {
     factory.addConnectorCustomizers(connector -> connector.setProperty("relaxedQueryChars", "[]"));
 }
 
}