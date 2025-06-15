package com.rasitesdmr.spring_boot_graphQl_monolithic.config;

import graphql.analysis.MaxQueryComplexityInstrumentation;
import graphql.analysis.MaxQueryDepthInstrumentation;
import graphql.execution.instrumentation.ChainedInstrumentation;
import graphql.scalars.ExtendedScalars;
import org.springframework.boot.autoconfigure.graphql.GraphQlSourceBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import java.util.List;

@Configuration
public class GraphQLConfig {

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return builder -> {
            builder.scalar(ExtendedScalars.GraphQLLong);
            builder.scalar(ExtendedScalars.Date);
        };
    }

    @Bean
    public GraphQlSourceBuilderCustomizer complexityInstrumentation() {
        return builder -> builder.configureGraphQl(graphQlBuilder ->
                graphQlBuilder.instrumentation(
                        new ChainedInstrumentation(List.of(
                                new MaxQueryComplexityInstrumentation(3),
                                new MaxQueryDepthInstrumentation(3)
                        ))
                )
        );
    }

}