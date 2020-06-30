

const { gql } = require('apollo-server');

const typeDefs = gql`
  
    type SpecEntries {
        iconImage: String
        title: String
        description: String
        bodyImage: String
        color: String
    }

    type ScenarioType {
        category: String
        specEntries: [SpecEntries]
    }

    type Thread {
        task: String
        subjectUnit: String
        qualifier: String
        metric: String
    }

    type People {
        name: String
    }

    type MissionSpecification {
        missionId: String
        Description: String
        scenario: [ScenarioType]
        threads: [Thread]
        personnel: [People]
    }

    type Query { 
        getMissions: [MissionSpecification]

        getMissionScenarioCategories(missionId: String!): [ScenarioType]
        getMissionThreads(missionId: String!): [Thread]
        getMissionPersonnel(missionId: String!): [People]

        hello: String
    }
    type Mutation {
        setupTest(content: String): MissionSpecification
        addPerson(missionId: String, name: String!): People
        addMission(missionSpec: String): MissionSpecification
        
    }
`;

module.exports = typeDefs;

iconImage: String
        title: String
        description: String
        bodyImage: String
        color: String