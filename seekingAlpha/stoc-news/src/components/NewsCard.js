

import React from 'react';

 
function NewsCard({ title, publish_date }) {

    return (
        <div>
            {
                <div className="w3-card-4" style={{ width: "50%" }} >
                    <div className="w3-container" >
                        <p>{title}</p>
                    </div>
                    <div className="w3-container" >
                        <p>{publish_date}</p>
                    </div>
                </div>
            }
        </div>
    )


}

export default NewsCard;

/*

<div className="w3-card4" ></div>
        <div className="w3-container">
            {
                data.getnews.map((item, index) => {
                    return <div key={index} className="w3-panel w3-card">
                        <p>{item.title} </p>
                        <p>{item.published_date}</p>
                    </div>
                })

            }

        </div>

<Card>
        <CardBody>
          <CardTitle>
            <label>Title</label>{props.title}
            <CardSubtitle>
              <label>Date</label>{ props.publish_date}
            </CardSubtitle>
          </CardTitle>
        </CardBody>

      </Card>

      */