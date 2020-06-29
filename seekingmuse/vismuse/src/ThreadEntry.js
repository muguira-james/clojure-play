
import React from 'react';


export default function ThreadEntry({ task, subjectUnit, metric, qualifier }) {
    console.log("cat-->", task, subjectUnit)
    return (
        <div>
            {task}
            {subjectUnit} 
            {metric}
            {qualifier}
            
        </div>

    )
}
