# Balance

**To run the application you need:**

Install docker 19.03.11 and docker-compose 1.25.0

Install gradle 6.5

Open a console in the project folder

Build application:

> gradle build

Launch the application:

> gradle composeUp

or

> docker-compose up --build

Stop application:

> gradle composeDown

or

> docker-compose down

The application runs on localhost:8080/balance

## Rest API:

### Create account:
POST: localhost:8080/balance/account/create

Request parameters:

accountNumber - account number (size 18 characters)

*Example:*
> curl -X POST -d accountNumber=123456789123456789 localhost:8080/balance/account/create

### Get account balance:
GET: localhost:8080/balance/account/balance

Request parameters:

accountNumber - account number (size 18 characters)

*Example:*
> curl -X GET localhost:8080/balance/account/balance?accountNumber=123456789123456789

### Replenishment to an account:
PUT: localhost:8080/balance/account/replenish

Request parameters:

accountNumber - account number (size 18 characters)

sum - replenishment sum (no more than two decimal places)

*Example:*
> curl -X PUT -d accountNumber=123456789123456789 -d sum=10 localhost:8080/balance/account/replenish

### Withdraw from an account:
PUT: localhost:8080/balance/account/withdraw

Request parameters:

accountNumber - account number (size 18 characters)

sum - withdrawal sum (no more than two decimal places)

*Example:*
> curl -X PUT -d accountNumber=123456789123456789 -d sum=10 localhost:8080/balance/account/withdraw
