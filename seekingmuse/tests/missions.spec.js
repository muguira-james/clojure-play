

import { expect } from 'chai';
import * as userAPI from './api'

const API_URL = 'http://localhost:4000';


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
//
// =================== tests start =======================
//
describe("setuptest making and retrieving missions", () => {
  before("make a mission", async () => {

    const resultP = await userAPI.addMission(sampleData)
    console.log("result -->", resultP.data)

    

  })
  it("returns a mission", async () => {

    const result = await userAPI.getMissions()
    console.log("result -->", Object.values(result['data'])[0].getMissions[0].missionId )
    
    let result_id = Object.values(result['data'])[0].getMissions[0].missionId 
    expect(result_id).to.eql("DCAS")
    
  })



})
