# Test assignment for Junior Back-End Engineer role #


## Task ##
Create backend for a game:
* Player is sending a bet and whole number from 1 to 100 to a server
* Server generates random whole number from 1 to 100, and if the player's number is greater, calculates win and sends it back to the client.
* Win depends on chance: = bet * (99 / (100 - number)), as an example, if player selected the number 50 and bet 40.5, the win would be 80.19

Requirements:
* Java 11 (or up)
* Spring boot 2
* REST + JSON (using WebSocket for communication instead is a plus)
* Unit and Integration tests
* Data validation

Optional task:
To write a test that is going to play 1 million rounds in parallel in 24 threads and will calculate how much money the player is receiving back (RTP)
Example: For 1 million games player has spent 1 million and had won 990000, RTP is going to be 99%


## Solution ##
### Technical prerequisites for current solution ###
- Gradle 8
- Java 17

Recommended:
- Postman v8.5 and above (recommended for testing, when running application locally)

### Running the application ###
1. Run the following commands:
```shell
./gradlew build
./gradlew bootRun
```
When application is running successfully, you can test the WebSocket endpoint with Postman
2. Create new WebSocket request (tutorial can be found [here](https://www.postman.com/postman/workspace/websockets/documentation/14057978-712d684f-c252-4bd9-a7a6-6a893e41adea)).
   - Set WebSocket API URL to `ws://localhost:8080/bets` and click 'Connect'
   - After you've connected, set message type to JSON and set message body to following: 

```json
{
    "bet": 40.5,
    "randomNumber": 100
}
```

3. You can also test REST API endpoint with Postman. If you have the application already up and running, you can create a new HTTP request.
    - Set the request method to POST.
    - API URL: `http://localhost:8080/bet`
    - Request body is `raw` and type JSON. The example above can be used for this request as well. 

### Running the tests ###
#### Unit tests #### 
```shell
./gradlew test
```
#### Integration tests ####
TBD


## Sources for solution ##
- [Spring.io docs about WebSocket](https://docs.spring.io/spring-framework/reference/web/websocket/server.html)
