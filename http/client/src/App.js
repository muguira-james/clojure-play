import React, { useState, useEffect } from 'react';

import './App.css';
import { Button, Table } from 'reactstrap'



function App() {
  const [urlR, setUrlR] = useState("")
  const [fout, setFout] = useState([])

  let cntnt = null
  // const [myId, setMyId] = useState(0)

  function handleClick(e) {
    let url = document.getElementById('url').value
    // console.log("-0->", url)
    setUrlR(url)
  }

  useEffect(() => {
    console.log('dom updated')
    
    if (urlR === "") return

    let URL = `http://localhost:3000/scrape?page=${urlR}`

    // console.log("-1->", URL)
    fetch(URL).then((response) => {
      return response.json()
    }).then((data) => {
      // console.log("--RR-->", data, JSON.stringify(data.data[0]))

      setFout(data.links)
    })
  }, [urlR]) // myId init set to 0; since it never changes useEffect is only called 1 time

  if (fout != null) {
    cntnt = fout.map((i, ndx) => <tr key={ndx}><td>{i.linktext}</td><td>{i.address}</td></tr>)
  } else {
    cntnt = null
  }

    
  
  return (
    <div className="App">
      <p>enter a url (like http://cnn.com) </p><input id="url" ></input>
      
      <Button onClick={handleClick}>submit</Button>
      
      <Table>
        <thead>
          <tr>
            <th>Link Text</th>
            <th>address</th>
          </tr>
        </thead>
        <tbody>

          {
            cntnt
          }

        </tbody>

      </Table>
    </div>
  );
}

export default App;
