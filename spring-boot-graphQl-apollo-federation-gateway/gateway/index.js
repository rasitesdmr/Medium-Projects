const { ApolloGateway, IntrospectAndCompose } = require('@apollo/gateway');
const { ApolloServer } = require('@apollo/server');
const { startStandaloneServer } = require('@apollo/server/standalone');

const gateway = new ApolloGateway({
    supergraphSdl: new IntrospectAndCompose({
        subgraphs: [
            { name: 'users', url: 'http://localhost:8090/graphql' },
            { name: 'photos', url: 'http://localhost:8091/graphql' },
            { name: 'likes', url: 'http://localhost:8092/graphql' },
        ]
    })
});

async function startGateway() {
    const server = new ApolloServer({ gateway });
    const { url } = await startStandaloneServer(server, {
        listen: { port: 4005 }
    });
    console.log(`🚀 Apollo Gateway ready at ${url}`);
}

startGateway().catch(err => {
    console.error('Failed to start Apollo Gateway', err);
});