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

      let page = `{ ${urlR} }`;
      let URL = "http://localhost:8000/test";
      console.log("--> ", URL, " --> ", page);
      fetch(
	  URL,
	  {
	      method: 'post',
	      headers: { 'Content-Type' : 'application/json' },	  
	      body: JSON.stringify(page)
	  }
      ).then((response) => {
	  return response.json()
    }).then((data) => {
	console.log("--RR-->", data);


    })
  }, [urlR]) // myId init set to 0; since it never changes useEffect is only called 1 time

  if (fout != null) {
      cntnt = fout.map((i, ndx) =>
	  <tr key={ndx}><td>{i.linktext}</td><td>{i.address}</td></tr>)
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

          
        </tbody>

      </Table>
    </div>
  );
}

export default App;
