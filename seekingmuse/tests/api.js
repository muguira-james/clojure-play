

import axios from 'axios';

const API_URL = 'http://localhost:4000/';


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

export const setupTest = async variables => 
  axios.post(API_URL, {
    query: `
      setupTest {
        missionId
      }
    `,
    variables
  })

export const addMission = async variables => 
  axios.post(API_URL, {
    mutation: ` {
      addMission(sample_data) {
        missionId
      }
    }
    `,
    variables
  })

// export const getChild = async variables => 
//   axios.post(API_URL, {
//     query: `
//       query ($id: ID!){
//         getChild (id: $id) {
//           id
//           name
//           gender
//           age
//         }
//       }
//     `,
//     variables,
//   });
  
  
//   export const getParents = async => 
//   axios.post(API_URL, {
//     query: `
//       {
//         getParents {
//           name
//           id
//           phoneNumber
//           gender
//           children
//         }
//       }
//     `
//   });


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