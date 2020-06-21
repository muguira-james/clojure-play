

import React from 'react';


function EditPortfolio() {

    return (
        <div>
            <div className="w3-container">
                <div className="w3-cell-row">
                    <label className="w3-cell" >Symbol Name</label>
                    <input className="w3-cell" type="text"></input>
                </div>
            </div>


            <div className="w3-container">
                <div className="w3-cell-row">
                    <label className="w3-cell">Number of Shares</label>
                    <input type="text"></input>
                </div>
            </div>



            <label className="w3-container">Date Purchased</label>
            <input type="text"></input>

            <label className="w3-container">Current Price</label>
            <input type="text"></input>

            <label className="w3-container">Purchase Price</label>
            <input type="text"></input>

        </div>
    )


}

export default EditPortfolio;

