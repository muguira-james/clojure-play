

import React from 'react';

 
function Sentiment({ title, link }) {

    return (
        <div>
            {
                <div className="w3-card-4" style={{ width: "50%" }} >
      
                    <a href={link} target="_blank">{title}</a> 
                </div>
            }
        </div>
    )


}

export default Sentiment;

/*

              <div className="w3-container" >
                        <p>{title}</p>
                    </div>
                    <div className="w3-container" >
                        <p>{link}</p>
                    </div>

                    */