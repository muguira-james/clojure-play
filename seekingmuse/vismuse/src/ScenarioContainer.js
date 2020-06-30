
// import React from 'react';
// import Scenario from './Scenario'

// export default function ScenarioContainer() {
//     return (
//         <Scenario title="foo" description="goo" color="green" />
//     )
// }


import React from 'react';


import { useQuery } from '@apollo/react-hooks'
import gql from 'graphql-tag'

import ScenarioEntry from './ScenarioEntry'

const GET_SCENARIO_SPEC = gql`
query{
	getMissionScenarioCategories(missionId: "DCAS") {
        category
        specEntries {
            title
        }

  }
}
`;




export default function ScenarioContainer() {
    const { data, loading, error } = useQuery(GET_SCENARIO_SPEC)

    if (loading) return <div><p>data is still loading</p></div>
    if (error) return <div><span>found network errro</span></div>

    if (!data) return <p>Not found?</p>

    console.log("my data-->", data.getMissionScenarioCategories)
    return (
        <div>
            {
                data.getMissionScenarioCategories.map((item, index) => {

                    console.log("--item-->", item.category)
                    return (
                        <ScenarioEntry key={index} category={item.category} specEntries={item.specEntries} />
                    )


                })
            }
        </div>

    )
}




