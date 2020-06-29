const { gql } = require('apollo-server');

const typeDefs = gql`
  

    type MarketNews {
        title: String
        publish_date: String
        link: String
    }

    type Sents {
        title: String
        link: String
    }

    type Query { 
        getnews: [MarketNews]
        hello: String
        answer: MarketNews

        getsents: [Sents]
    }
`;

module.exports = typeDefs;