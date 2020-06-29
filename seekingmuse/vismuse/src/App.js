import React, { useState } from 'react';

import './App.css';

import ScenarioContainer from './ScenarioContainer'
import ThreadContainer from './ThreadContainer'
import PersonnelContainer from './PersonnelContainer'

function App() {
  const [page, setPage ] = useState(1)

  function switchPage() {
    // console.log("switchpage -->", page)
    let thing = null

    switch(page) {
      case 1: {
        thing = <ScenarioContainer />
        break
      }
      case 2: { thing = <ThreadContainer />;  break }
      case 3: { thing = <PersonnelContainer />; break }
      default: { thing = null; break }
      
    }
    return thing

  }
  function NavBar() {
    return (
      <div className="w3-bar w3-blue">
        <button className="w3-bar-item w3-button" onClick={() => { setPage(1) } }>Scenario</button>
        <button className="w3-bar-item w3-button" onClick={() => { setPage(2) } }>Threads</button>
        <button className="w3-bar-item w3-button" onClick={() => { setPage(3) } }>Personnel</button>

      </div>
    )
  }

  return (
    <div>
      <NavBar />
      {
        switchPage()
      }
    </div>
  );
}

export default App;
