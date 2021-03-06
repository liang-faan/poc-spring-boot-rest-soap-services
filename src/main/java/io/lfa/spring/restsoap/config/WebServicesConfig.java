package io.lfa.spring.restsoap.config;

import io.lfa.spring.restsoap.controller.StammdatenController;
import io.lfa.spring.restsoap.controller.StammdatenSoapController;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.config.annotation.EnableWs;

import javax.xml.ws.Endpoint;

/**
 * Created by idueppe on 06.06.17.
 */
@Configuration
@EnableWs
//@Profile("soap")
public class WebServicesConfig {

    @Bean
    public StammdatenSoapController stammdatenWebService(StammdatenController controller) {
        return new StammdatenSoapController(controller);
    }

    @Bean
    public ServletRegistrationBean wsDispatcherServlet() {
        CXFServlet cxfServlet = new CXFServlet();
        return new ServletRegistrationBean(cxfServlet, "/services/*");
    }

    @Bean(name= Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

    @Bean
    public Endpoint stammdatenEndpoint(SpringBus springBus, StammdatenSoapController stammdatenSoapService) {
        EndpointImpl endpoint = new EndpointImpl(springBus, stammdatenSoapService);
        endpoint.publish("/stammdatenWS");
        return endpoint;
    }

}


