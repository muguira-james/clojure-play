var mysql = require('mysql')

let Parser = require('rss-parser');
// let feeds = require('./feeds.json')

let parser = new Parser();



let connection = mysql.createConnection({
    host: 'localhost',
    user: 'magoo',
    password: 'ipoet',
    database: 'seeking'
})

const mnews = {
    title: "wowo",
    publish_date: "today"
}
connection.connect()

module.exports = {
    Query: {
        hello: () => { return "Hello james" },

        answer: (root, parent, context, info) => { return mnews },

        getnews: async (root, parent, context, info) => {
            let myPromise = new Promise((resolve, reject) => {

                connection.query('SELECT * FROM rss', function (error, results) {
                    if (error) {
                        reject(new Error("query failed"))
                    } else {
                        if (results.length > 0) {
                            resolve(results)
                            console.log("-->", results)
                        }

                    }
                })
                // connection.end()
            })
            let res
            try {
                res = await myPromise

                return res
            } catch (error) {
                return error
            }


        },

        getsents: async (root, parent, content, info) => {

            const aFeed = await parser.parseURL("https://seekingalpha.com/api/sa/combined/AAPL.xml")

            aFeed.items.forEach(item => {
                console.log(item.title + '----->' + item.link)
            })

            return aFeed.items

        }


    }
}


