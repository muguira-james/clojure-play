# hepost

produce a server that can search a network.

The network is hard coded in search.clj.

This uses compojure to handle [ GET, POST ] requests.  The POST request gets its parameters from the body of the requrest

for example in javascript:

fetch ("http://localhost:3000/search", method: POST, body: { "que" : "s" })

The search code has a couple of examples:

* Depth-first search    ; (d-first que save-children)
* Breadth-first search  ; (d-first que save-save-b-children)
* save-children stores items at the front of a queue
* save-b-children stores items at the rear

The queue is a java.util.LinkedList !

You call search:

* (enque 's)  ; first add your starting place in the network to the 'que var
* (search que) ; use the 'que var to search around.  This return visit.

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein ring server-headless

Docker

To use docker -

* docker build -t my-search
* docker run -it --rm --name my-search -p 3000:3000 my-search

* --rm will remove the instances when you stop the container
* --name gives it a name
* the server opens port 3000

## License


