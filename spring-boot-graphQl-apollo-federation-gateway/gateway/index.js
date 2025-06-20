const { ApolloGateway, IntrospectAndCompose } = require('@apollo/gateway');
const { ApolloServer } = require('@apollo/server');
const { startStandaloneServer } = require('@apollo/server/standalone');

const gateway = new ApolloGateway({
    supergraphSdl: new IntrospectAndCompose({
        subgraphs: [
            { name: 'users', url: 'http://localhost:8080/graphql' },
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