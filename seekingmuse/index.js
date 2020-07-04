
// 
// Roll the schema and resolver into a single file.
//
// This uses Apollo GraphQL to present a simple backend

const { ApolloServer } = require('apollo-server');
const MongoClient = require('mongodb').MongoClient;


let mongoURI = "mongodb://root:mysql@localhost:27017/muse?authSource=admin"
//
// This will decluster the db from the mongURI parameter in the process.env
// const { mongoURI: db } = process.env
console.log("db", mongoURI)


const client = new MongoClient(mongoURI, { useNewUrlParser: true, useUnifiedTopology: true });
let db = null
client.connect(function (err) {
    console.log("MONGOdb connected");
    db = client.db("muse"); //mongodb database name
});

const typeDefs = require('./schema');




const sampleData =
{
    "missionId": "DCAS",
    "scenario": [
        {
            "category": "Environment",
            "specEntries": [
                {
                    "iconImage": "weather.png",
                    "title": "Weather",
                    "description": "Lorem ipsum ad infinitum",
                    "bodyImage": "weatherMap.jpg",
                    "color": "red"
                },
                {
                    "iconImage": "terrain.png",
                    "title": "Terrain",
                    "description": "Lorem ipsum ad infinitum",
                    "bodyImage": "ambushMap.png",
                    "color": "green"
                },
                {
                    "iconImage": "weather.png",
                    "title": "Lorem Ipsum3",
                    "description": "Lorem ipsum ad infinitum",
                    "bodyImage": "weatherMap.jpg",
                    "color": "blue"
                }
            ]
        },
        {
            "category": "Forces",
            "specEntries": [
                {
                    "iconImage": "error.png",
                    "title": "Possible Enemy Armaments",
                    "description": "MP5, AK-47, SCAR, RPG, Vector",
                    "bodyImage": "arms.jpg",
                    "color": "red"
                },
                {
                    "iconImage": "knowledgeBase.png",
                    "title": "Intel Analysis",
                    "description": "Still waiting on final results",
                    "bodyImage": "analysis.jpg",
                    "color": "green"
                },
                {
                    "iconImage": "key.png",
                    "title": "Auth Chain",
                    "description": "Command chain to be deteremined soon",
                    "bodyImage": "faces/Desert.png",
                    "color": "purple"
                }
            ]
        },
        {
            "category": "Operations",
            "specEntries": [
                {
                    "iconImage": "upload.png",
                    "title": "Prepartion",
                    "description": "This mission requires these steps to prepare",
                    "bodyImage": "faces/Woman.png",
                    "color": "blue"
                },
                {
                    "iconImage": "download.png",
                    "title": "Deployment",
                    "description": "These units will be deployed here",
                    "bodyImage": "faces/Tank.png",
                    "color": "purple"
                }
            ]
        },
        {
            "category": "Components",
            "specEntries": [
                {
                    "iconImage": "gear.png",
                    "title": "Units",
                    "description": "Combat Ready Units",
                    "bodyImage": "faces/MilitaryGuy.png",
                    "color": "orange"
                }
            ]
        }
    ],
    "threads": [
        {
            "task number": 1,
            "task": "Scout detects hostile unit",
            "subjectUnit": "Plt member",
            "qualifier": "",
            "metric": "< 2 min"
        },
        {
            "task number": 2,
            "task": "Scout Notifies tactical unit commander",
            "subjectUnit": "plt member",
            "qualifier": "",
            "metric": " < 1 min"
        },
        {
            "task number": 3,
            "task": "Tactical unit commander passes",
            "subjectUnit": "Plt member",
            "qualifier": "",
            "metric": ""
        }
    ],
    "personnel": [
        {
            "name" : "George Clinton" ,
        },
        {
            "name" : "Tom Shultz" ,
        },
        {
            "name" : "Jimmy Page" ,
        },
        {
            "name" : "Jon Bonham" ,
        }
    ]
}

let resolvers = {
    Query: {
        hello: () => { return "Hello james" },
        //
        // return all missions
        getMissions: async () => {

            let ary = []

            let Specs = db.collection('specs')
            let cursor = await Specs.find({}).forEach(item => {
                // console.log("-getmissions-->", item)
                ary.push(item)
            })
            console.log("-2-getMissions-->", ary)
            return ary
        },
        //
        // using the input missionID, get the scenario info from a missionn
        getMissionScenarioCategories: async (root, { missionId }, context, info) => {

            console.log("input-->", missionId)
            let Specs = db.collection('specs')
            let mission = await Specs.findOne({ missionId: missionId })
            //
            // NEED ERROR handling !!!
            //
            console.log("-1-->", mission)

            ret = []
            mission.scenario.map(cat => {
                console.log("---3--->", cat)
                ret.push(cat)
            })
            return ret
        },
        
        //
        // using the input missionId, get the threads for this mission
        getMissionThreads: async (root, { missionId }, context, info) => {
            let retS = {}

            console.log("mis id thread->", missionId)
            let Specs = db.collection('specs')
            let mission = await Specs.findOne({ missionId: missionId })

            console.log("-1-->", mission)

            ret = []
            mission.threads.map( (thread, index) => {
                console.log(`---${index}--->`, thread)
                ret.push(thread)
            })
            return ret
        },
        //
        // using the input person, answer the personnel for this mission
        getMissionPersonnel: async (root, { missionId }, context, info) => {
            
            console.log("person -->", missionId)
            let Specs = db.collection('specs')
            let mission = await Specs.findOne({ missionId: missionId })

            console.log("-1-->", mission)

            ret = []
            mission.personnel.map(person => {
                console.log("---3--->", person) 
                ret.push(person)
            })
            return ret
        }
        
    },
    Mutation: {
        //
        //
        setupTest: async (root, { cont } , context, info) => {

            console.log("--setup-->", cont)
            let Specs = db.collection('specs')
            let ret = await Specs.insertOne(sampleData)
            console.log("--inserting sample data---->", sampleData.missionId)
            return sampleData
        },
        //
        // add a person to a specific mission
        addPerson: async (root, { missionId, name }, context, info) => {
            let Specs = db.collection('specs')

            let ret = await Specs.update( { missionId: missionId }, { $push: { personnel: { name: name }}} )
            console.log("--add person-->", name)
            return ( { name: name } )
        },
        addThread: async (root, { missionId, thread }, conntext, info) => {
            let Specs = db.collection('specs')
            console.log("new thread--->", missionId, JSON.stringify(thread))
            let ret = await Specs.update( { missionId: missionId }, { $push: { threads: thread }} )
            // console.log("--add thread-->", ret)
            
            return thread
        },
        //
        // add a complete mission
        addMission: async (root, { missionSpec }, context, info) => {
            let Specs = db.collection('specs')

            let js = JSON.parse(missionSpec)
            console.log("--add mission-->", js.missionId)

            try {
                await Specs.insertOne(js)
            } catch (err) {
                console.log("err-->", err)
            }
            return js
        },

        //
        // delete a mission
        deleteMission: async (root, {missionId}, context, info) => {

            console.log("--deleteOne-->", missionId)

            let Specs = db.collection('specs')
            
            let ret = await Specs.deleteOne( { missionId: missionId })
            

            return `deleteMission: missionId = ${missionId}`
        },

        modMissionScenarioCategories: async (root, { missionId, newCategories }, context, info) => {
            console.log("input--mod>", missionId, newCategories)
            let Specs = db.collection('specs')
            let mission = await Specs.findOne({ missionId: missionId })
            //
            // NEED ERROR handling !!!
            //
            console.log("-1-->", mission.missionId)

            let newMission = {}
            newMission.missionId = mission.missionId
            newMission.description = mission.description ? mission.description : "no description" 
            newMission.scenario = newCategories
            newMission.threads = mission.threads
            newMission.personnel = mission.personnel

            try {
                await Specs.update({ missionId: missionId }, { $set: { scenario: newCategories }} )
            } catch (err) {
                console.log("error-modMission->", err)
            }
            return newCategories
        }

    }
}




const server = new ApolloServer({
    typeDefs,
    resolvers

    // dataSource: () => {
    //     marketAPI: new MarketAPI()
    // }

});

server.listen().then(({ url }) => {
    console.log(`🚀 Server ready at ${url}`);
});