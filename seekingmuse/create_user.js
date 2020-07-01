db.createUser(
    {
        user: "magoo",
        pwd: "q1w2e3r4t5y6",
        roles: [
          
          { role: "readWrite", db: "muse" }
        ]
    });