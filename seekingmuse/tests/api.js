

import axios from 'axios';

const API_URL = 'http://localhost:4000';

export const hello = variables => {
  axios.post(API_URL, {
    query: `
    query {
      hello 
    }`, variables,
  })
}

export const getMissions = async variables =>
  axios.post(API_URL, {
    query: `
      query {
        getMissions {
          missionId
        }
      }
    `,
    variables,
  });

export const getMissionPersonnel = async variables =>
  await axios({
    url: API_URL,
    method: 'post',
    headers: { 'Content-Type': 'application/json' },
    data: {
      query: `
      query 
      
      ($missionId: String!) {
        getMissionPersonnel(missionId: $missionId)  {
          
          name
        }
      }
    `,
      variables 
    }
    })

export const getMissionThreads = async variables =>
  await axios({
    url: API_URL,
    method: 'post',
    headers: { 'Content-Type': 'application/json' },
    data: {
      query: `
      query 
      
      ($missionId: String!) {
        getMissionThreads(missionId: $missionId)  {
          
          task
        }
      }
    `,
      variables 
    }
    })


export const setupTest = async variables => {
  console.log("--variable from test-->", typeof (variables))
  await axios.post(API_URL, {
    query: `
      mutation {
        $content: String

        setupTest {
          missionId
        }
      }
    `,
    variables
  })
}

export const addMission = async variables =>
  await axios({
    url: API_URL,
    method: 'post',
    headers: { 'Content-Type': 'application/json' },
    data: {
      query: ` 
        mutation(
          $missionSpec: String
        )  {
          addMission(missionSpec: $missionSpec)
        }
    `,
      variables
    }
  })



export const deleteMission = async variables =>
  await axios({
    url: API_URL,
    method: 'post',
    headers: { 'Content-Type': 'application/json' },
    data: {
      query: ` 
      mutation(
        $missionId: String
      )  {
        deleteMission(missionId: $missionId)
      }
  `,
      variables
    }
  })


export const modMissionScenarioCategories = async variables =>
  await axios({
    url: API_URL,
    method: 'post',
    headers: { 'Content-Type': 'application/json' },
    data: {
      query: ` 
        mutation
        (
          $missionId: String,
          $newCategories: ScenarioTypeInput
        )
        {
          modMissionScenarioCategories(missionId: $missionId, newCategories: $newCategories) {
            category
          }
        }
    `,
      variables
    }
  })

export const addPerson = async variables =>
  await axios({
    url: API_URL,
    method: 'post',
    headers: { 'Content-Type': 'application/json' },
    data: {
      query: ` 
        mutation
          
        {
          addPerson(missionId: "ZCAS", name: "FOOD") {
            name
          }
        }
    `,
      variables: { missionId: "ZCAS", name: "fruit" }
    }
  })

  export const addThread = async variables =>
  await axios({
    url: API_URL,
    method: 'post',
    headers: { 'Content-Type': 'application/json' },
    data: {
      query: ` 
        mutation
        (  
          $missionId: String, 
          $thread: ThreadInput
        )
        {
          addThread(missionId: $missionId, thread: $thread ) {
            taskNumber
            subjectUnit
            metric
            qualifier
          }
        }
    `,
      variables
    }
  })
// export const getChildByParams = async variables =>
//   axios.post(API_URL, {
//     query: `
//       query ($name: String!, $age: Int!, $gender: String!){
//         getChildByParams (name: $name, age: $age, gender: $gender) {
//           name
//           age
//           gender
//           id
//         }
//       }
//     `,
//     variables,
//   })

// export const getParentByParams = async variables => 
//   axios.post(API_URL, {
//     query: `
//       query ($name: String!, $age: Int!, $gender: String!){
//         getParentByParams (name: $name, age: $age, gender: $gender) {
//           name
//           age
//           gender
//           id
//         }
//       }
//     `,
//     variables,
//   })

// export const createParent = async variables =>
//   axios.post(API_URL, {
//     query: `
//       mutation (
//         $name: String, 
//         $email: String, 
//         $password: String, 
//         $age: Int, 
//         $gender: String, 
//         $phoneNumber: String, 
//         $streetAddress: String) {

//         createParent(name: $name, email: $email, password: $password, age: $age, gender: $gender, phoneNumber: $phoneNumber, streetAddress: $streetAddress) {
//           name
//           email
//           age
//           gender
//           phoneNumber
//           streetAddress
//         }
//       }
//     `,
//     variables,
//   })
// export const createChild = async variables => 
//   axios.post(API_URL, {
//     query: `
//       mutation ($name: String!, $age: Int!, $gender: String!, $phoneNumber: String, $streetAddress: String) {
//         createChild(name: $name, age: $age, gender: $gender, phoneNumber: $phoneNumber, streetAddress: $streetAddress) {
//           name
//           age
//           gender
//         }
//       }

//     `,
//     variables,
//   })

// export const getChildren = async => 
//   axios.post(API_URL, {
//     query: `
//     {
//       getChildren {
//         name
//         age
//         gender
//         id
//       }
//     }
//   `,
//   });

// export const createHost = async variables =>
//   axios.post(API_URL, {
//     query: `
//       mutation (
//         $name: String, 
//         $email: String, 
//         $age: Int, 
//         $gender: String, 
//         $phoneNumber: String, 
//         $streetAddress: String) {

//         createHost(
//           name: $name, 
//           email: $email, 
//           age: $age, 
//           gender: $gender, 
//           phoneNumber: $phoneNumber, 
//           streetAddress: $streetAddress) 
//           {
//             name
//             age
//             gender
//             phoneNumber
//             streetAddress
//             email
//             managingChildren
//           }
//       }
//     `,
//     variables,
//   });

// export const getHosts = async => 
//     axios.post(API_URL, {
//       query: `
//          {
//           getHosts {
//             name
//             id
//             gender

//           }
//         }
//     `
//   })

// export const getHost = async variables => 
//   axios.post(API_URL, {
//     query: `
//       query ($id: ID!){
//         getHost (id: $id) {
//           id
//           name
//           gender
//           age
//         }
//       }
//     `,
//     variables,
//   });

// export const getHostByParams = async variables => 
//   axios.post(API_URL, {
//     query: `
//     query ($name: String!, $age: Int!, $gender: String!){
//       getHostByParams (name: $name, age: $age, gender: $gender) {
//           name
//           age
//           gender
//           id
//         }
//       }
//     `,
//     variables,
//   });

// export const addChildToHost = async variables => 
//   axios.post(API_URL, {
//     query: `
//       mutation ($childID: ID!, $hostID: ID!) {
//         addChildToHost (childID: $childID, hostID: $hostID) {
//           name
//           managingChildren
//         }
//       }
//     `,
//     variables
//   })

// export const removeChildFromHost = async variables => 
//   axios.post(API_URL, {
//     query: `
//       mutation ($childID: ID!, $hostID: ID!) {
//         removeChildFromHost (childID: $childID, hostID: $hostID) {
//           name
//           managingChildren
//         }
//       }
//     `,
//     variables
//   })