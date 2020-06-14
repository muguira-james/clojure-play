var mysql = require('mysql')

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
                    if  (error) {
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
            } catch(error) {
                return error
            }
            

        }
    }
}


