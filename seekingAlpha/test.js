const OrientDBClient = require("orientjs").OrientDBClient;

OrientDBClient.connect({
  host: "localhost",
  port: 2424
}).then(client => {
  console.log("client open")
  client.session({ name: "testJAM", username: "root", password: "root" })
.then(session => {
	// use the session
	... 
	// close the session
	return session.close();
});
    return client.close();

}).then(()=> {
   console.log("Client closed");
});