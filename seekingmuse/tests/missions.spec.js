

import { expect } from 'chai';
import * as userAPI from './api'



const missionSpec =
{
    "missionId": "ZCAS",
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
            "taskNumber": 1,
            "task": "Scout detects hostile unit",
            "subjectUnit": "Plt member",
            "qualifier": "",
            "metric": "< 2 min"
        },
        {
            "taskNumber": 2,
            "task": "Scout Notifies tactical unit commander",
            "subjectUnit": "plt member",
            "qualifier": "",
            "metric": " < 1 min"
        },
        {
            "taskNumber": 3,
            "task": "Tactical unit commander passes",
            "subjectUnit": "Plt member",
            "qualifier": "",
            "metric": ""
        }
    ],
    "personnel": [
        {
            "name": "George Clinton",
        },
        {
            "name": "Tom Shultz",
        },
        {
            "name": "Jimmy Page",
        },
        {
            "name": "Jon Bonham",
        }
    ]
}
//
// =================== tests start =======================
//

describe("setuptest making and retrieving missions", () => {
    before("make a mission", async () => {

        const resultP = await userAPI.addMission({
            missionSpec: JSON.stringify(missionSpec)
        })
        // console.log("Test: result -setupTest->", resultP)

    })
    it("returns a mission", async () => {

        const result = await userAPI.getMissions()
        // console.log("0: result --->", result['data'])
        // console.log("result -->", Object.values(result['data'])[0].getMissions[0].missionId)

        let result_id = Object.values(result['data'])[0].getMissions[0].missionId
        expect(result_id).to.eql("ZCAS")

    })

    after("get rid of that mission spec", async () => {
        const resP = await userAPI.deleteMission({ missionId: "ZCAS" })
        console.log("delete-->", resP.data)
    })

})


describe("modifying a misison scenario  section", () => {
    before("make a mission", async () => {

        const resultP = await userAPI.addMission({
            missionSpec: JSON.stringify(missionSpec)
        })
        // console.log("Test: result -setupTest->", resultP)

    })
    it("mods a mission scenarion", async () => {

        const res = await userAPI.modMissionScenarioCategories(
            { missionId: "ZCAS", newCategories: { category: "bob", specs: [{ title: "foo", color: "red" }] } }
        )
        console.log("--res-->", res.data)

    })
})

describe("test add person to mission", () => {
    before("make a mission", async () => {

        const resultP = await userAPI.addMission({
            missionSpec: JSON.stringify(missionSpec)
        })
    })
    // console.log("Test: result -setupTest->", resultP)
    it("add a person to a mission", async () => {
        const resultP = await userAPI.addPerson({
            missionId: "ZCAS",
            name: "zoe fruit"
        })
        const result = await userAPI.getMissionPersonnel({ missionId: "ZCAS" })
        // console.log("0: result --->", Object.values(result['data'])[0].getMissionPersonnel)
        // // console.log("result -->", Object.values(result['data'])[0].getMissions[0].missionId)

        let result_len = Object.values(result['data'])[0].getMissionPersonnel.length
        expect(result_len).to.eql(5)
    })
    after("get rid of that mission spec", async () => {
        const resP = await userAPI.deleteMission({ missionId: "ZCAS" })
        console.log("delete-->", resP.data)
    })
})

describe("test add thread", () => {
    before("make a mission", async () => {

        const resultP = await userAPI.addMission({
            missionSpec: JSON.stringify(missionSpec)
        })
    })

    it("add a thread", async () => {
        const resP = await userAPI.addThread(
            { missionId: "ZCAS", thread: { taskNumber: 4, task: "run", subjectUnit: "me", qualifier: "fast", metric: "fast" } }
        )
        const result2 = await userAPI.getMissionThreads( { missionId: "ZCAS" } )
        const result2_len = Object.values(result2['data'])[0].getMissionThreads.length

        console.log("0: result --->", Object.values(result2['data'])[0].getMissionThreads)
        expect(result2_len).to.eql(4)
    })
    after("get rid of that mission spec", async () => {
        const resP = await userAPI.deleteMission({ missionId: "ZCAS" })
        console.log("delete-->", resP.data)
    })
})