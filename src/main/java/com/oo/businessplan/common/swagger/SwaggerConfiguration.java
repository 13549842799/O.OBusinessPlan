package com.oo.businessplan.common.swagger;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	
	    @Bean
	    public Docket api() {
	        ParameterBuilder tokenPar = new ParameterBuilder();
	        List<Parameter> pars = new ArrayList<Parameter>();
	        tokenPar.name("X-Token").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false);
	        pars.add(tokenPar.build());
	        tokenPar.name("X-User").description("用户名").modelRef(new ModelRef("string")).parameterType("header").required(false);
	        pars.add(tokenPar.build());
	  
	        return new Docket(DocumentationType.SWAGGER_2)
	                .select()
	                .apis(RequestHandlerSelectors.any())
	                .paths(PathSelectors.regex("/api/.*"))
	                .build()
	                .globalOperationParameters(pars);
	    }

}
