const { gql } = require('apollo-server');

const typeDefs = gql`
  

    type MarketNews {
        title: String
        publish_date: String
        link: String
    }

    type Query { 
        getnews: [MarketNews]
        hello: String
        answer: MarketNews
    }
`;

module.exports = typeDefs;