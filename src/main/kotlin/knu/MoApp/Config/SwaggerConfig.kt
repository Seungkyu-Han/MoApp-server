package knu.MoApp.Config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.ApiKey
import springfox.documentation.service.AuthorizationScope
import springfox.documentation.service.SecurityReference
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .produces(getProduceContentTypes())
            .useDefaultResponseMessages(true)
            .securityContexts(listOf(securityContext()))
            .securitySchemes(listOf(apiKey()))
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("knu.MoApp"))
            .paths(PathSelectors.any())
            .build()
    }

    private fun getProduceContentTypes():Set<String>{
        val produces:Set<String> = HashSet()
        produces.plus("application/json;charset=UTF-8")
        return produces
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
            .title("MoApp")
            .description("23-2 MoApp Swagger입니다")
            .version("1.0.0")
            .build()
    }

    private fun apiKey(): ApiKey {
        return ApiKey("Authorization", "Authorization", "header")
    }

    private fun securityContext(): SecurityContext {
        return SecurityContext.builder()
            .securityReferences(defaultAuth())
            .build()
    }

    fun defaultAuth(): List<SecurityReference> {
        val authorizationScope = AuthorizationScope("global", "accessEverything")
        val authorizationScopes = arrayOf(authorizationScope)
        return listOf(SecurityReference("Authorization", authorizationScopes))
    }
}