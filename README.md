# kotlin-kafka-choreography-saga
A very basic choreography saga implementation. 

The details are mentioned in the blog post:

--> https://pagidas.github.io/posts/choreography_saga/

### How to run

#### Requirements
- docker
- docker-compose

#### Start
You can run the infrastructure and run the two applications using your IDE:

To do so, simply run `./start_infra.sh` -- spins kafka, postgres

Or you can run the whole stack: `./start_all.sh` -- spins kafka, postgres and the two services

#### Playground
As soon as you get yourself up and running...

You need to create an order like so:
```http request
POST /orders
{
    "pantryItemId": "2b563dc6-9bc1-4497-8e01-4d848a2989f7",
    "pantryItemQuantity": 1
}
```
But in order to know the `pantryItemId` you need to first look in postgres database, **pantry_app** schema, **items** table.
Upon starting, that table is populated with some **pantry_items** so you can begin playing around quickly.

If not, you can always create your own **pantry_item**
```http request
POST /pantry-items
{
    "name": "eggplant",
    "quantityLimit": 4
}
```
