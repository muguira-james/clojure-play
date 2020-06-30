




import React from 'react';


import { useQuery } from '@apollo/react-hooks'
import gql from 'graphql-tag'

import PersonEntry from './PersonEntry'

const GET_PERSONNEL_SPEC = gql`
query {
    getMissionPersonnel(missionId: "DCAS"){
      name
    }
  }
`;

export default function PersonnelContainer() {

    const { data, loading, error } = useQuery(GET_PERSONNEL_SPEC)

    if (loading) return <div><p>data is still loading</p></div>
    if (error) return <div><span>found network errro</span></div>

    if (!data) return <p>Not found?</p>

    console.log("my data-->", data.getMissionThreads)
    return (
        <div>
            {
                data.getMissionPersonnel.map((item, index) => {

                    console.log("--item-->", item.name)
                    return (
                        <PersonEntry key={index} name={item.name} />
                    )


                })
            }
        </div>

    )
}
