package com.rasitesdmr.spring_boot_graphQl_monolithic.domain.exception;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class GraphqlExceptionResolver extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {

        if (ex instanceof BaseGraphQLException baseEx) {
            return buildGraphQLError(baseEx, env);
        }

        return buildGraphQLError(
                new BaseGraphQLException("Bilinmeyen hata oluştu", HttpStatus.INTERNAL_SERVER_ERROR) {
                },
                env
        );
    }

    private GraphQLError buildGraphQLError(BaseGraphQLException ex, DataFetchingEnvironment env) {

        Map<String, Object> extensions = new HashMap<>();
        extensions.put("code", ex.getStatus().name());
        extensions.put("status", ex.getStatus().value());
        return GraphqlErrorBuilder.newError(env)
                .message(ex.getMessage())
                .extensions(extensions)
                .build();
    }
}
