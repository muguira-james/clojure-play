import React from 'react';


export default function ScenarioEntry({ category, specEntries }) {
    console.log("cat-->", category, specEntries)
    return (
        <div>
            {category}
            {
                specEntries.map( (ent, index) => {
                    return (
                        <div key={index}>
                            {
                                ent.title
                            }
                        </div>
                    )
                })
            }
        </div>

    )
}
