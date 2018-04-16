package io.github.mstachniuk.graphqljavaexample;

import graphql.schema.DataFetchingEnvironment;
import graphql.schema.FieldDataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.util.Map;

@SpringBootApplication
public class Main {

	@Autowired
	private CustomerDataFetcher customerDataFetcher;
	@Autowired
	private CompanyDataFetcher companyDataFetcher;
	@Autowired
	private OrderDataFetcher orderDataFetcher;
	@Autowired
	private ItemDataFetcher itemDataFetcher;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public GraphQLSchema schema() {
        SchemaParser schemaParser = new SchemaParser();
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("graphql/model.graphqls").getFile());
        TypeDefinitionRegistry registry = schemaParser.parse(file);
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
                .type("Query", builder ->
                        builder.dataFetcher("customers", customerDataFetcher))
                .type("Customer", builder ->
                        builder.dataFetcher("company", companyDataFetcher))
                .type("Customer", builder ->
                        builder.dataFetcher("orders", orderDataFetcher))
		        .type("Order", builder ->
		                builder.dataFetcher("items", itemDataFetcher))
                .build();
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(registry, runtimeWiring);
        return graphQLSchema;
    }
}
