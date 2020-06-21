import React, { useState } from 'react';

import './App.css';
import { useQuery } from '@apollo/react-hooks'
import gql from 'graphql-tag'
import NewsCard from './components/NewsCard'

import EditPortfolio from './components/EditPortfolio'

const GET_NEWS = gql`
query {
	getnews{
    title
    publish_date
    link
  }
}
`;



function StockContainer() {
  const { data, loading, error } = useQuery(GET_NEWS)

  if (loading) return <div><p>data is still loading</p></div>
  if (error) return <div><span>found network errro</span></div>

  if (!data) return <p>Not found?</p>

  // console.log("my data-->", data.getnews)
  return (
    <div>
      {
        data.getnews.map((item, index) => {
          return (
            <NewsCard key={index} title={item.title} publish_date={item.publish_date} />
          )
        })
      }
    </div>
    
  )
}
function App() {
  const [page, setPage ] = useState(1)


  function switchPage() {
    // console.log("switchpage -->", page)
    let thing = null

    switch(page) {
      case 1: {
        thing = <StockContainer />
        break
      }
      case 2: { thing = <EditPortfolio />;  break }
      case 3: { thing =  <p>nothing implemented yet</p>; break }
    }
    return thing

  }
  function NavBar() {
    return (
      <div className="w3-bar w3-blue">
        <button className="w3-bar-item w3-button" onClick={() => { setPage(1) } }>General News</button>
        <button className="w3-bar-item w3-button" onClick={() => { setPage(2) } }>Portfolio</button>
        <button className="w3-bar-item w3-button" onClick={() => { setPage(3) } }>Sentiment</button>
  
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

  )

}

export default App;

