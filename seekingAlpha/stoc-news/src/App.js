import React from 'react';

import './App.css';
import { useQuery } from '@apollo/react-hooks'
import gql from 'graphql-tag'
import NewsCard from './components/NewsCard'

const GET_NEWS = gql`
query {
	getnews{
    title
    publish_date
    link
  }
}
`;


 
function App() {
  const { data, loading, error } = useQuery(GET_NEWS)

    if (loading) return <div><p>data is still loading</p></div>
    if (error) return <div><span>found network errro</span></div>

    if (!data) return <p>Not found?</p>

    console.log("my data-->", data.getnews)
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

export default App;


/*


const newsList = [
  {
    title: "hello", published_date: "now"
  },
  {
    title: "hello", published_date: "now"
  },
  {
    title: "hello", published_date: "now"
  }
  
]

*/