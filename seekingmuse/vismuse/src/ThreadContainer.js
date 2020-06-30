


import React from 'react';


import { useQuery } from '@apollo/react-hooks'
import gql from 'graphql-tag'

import ThreadEntry from './ThreadEntry'

const GET_THREAD_SPEC = gql`
query{
	getMissionThreads(missionId: "DCAS") {
    task
    subjectUnit
    metric
    qualifier
  }
}
`;

export default function ThreadContainer() {

    const { data, loading, error } = useQuery(GET_THREAD_SPEC)

    if (loading) return <div><p>data is still loading</p></div>
    if (error) return <div><span>found network errro</span></div>

    if (!data) return <p>Not found?</p>

    console.log("my data-->", data.getMissionThreads)
    return (
        <div>
            {
                data.getMissionThreads.map((item, index) => {

                    console.log("--item-->", item.task)
                    return (
                        <ThreadEntry key={index} task={item.task} subjectUnit={item.subjectUnit} metric={item.metric} qualifier={item.qualifier} />
                    )


                })
            }
        </div>

    )
}
