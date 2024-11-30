# Currency Exchange & Discount Calculcation System

This project deals with discount calculation and currency conversion (if applicable) on the bill

- **Flow of the system**
    - Open the code on any editor (IntelliJ, VSCode, etc.)
    - For IntelliJ, run the code by clicking the play button or by going into the class `CurrencyExchangeDiscountCalcApplication.java` and
      clicking the main to run
    - **Note:** While opening the project in other machine and get maven plugin or sync error, just ignore it
      and run the program.
    - Upon starting the application, the database gets initialized with preloaded user with following credentials
        - Email: `hamza@khan.com`
        - Password: `ecxvawA$Tu`
    - Upon starting, one will be able to see database operations printed on the console of the code editor.
    - The console is accessible through the link `http://localhost:7788/h2-console`
    - **Note:** On the console if you find the JDBC URL to be other than `jdbc:h2:mem:cedc`, kindly replace it with the mentioned one.
    - The user related can be fetched using `SELECT * FROM TBL_STORE_USER` on H2 console (url mentioned above)


- **Project Configuration Description**
    - The API integrated for currency conversion is `ExchangeRate-API`
    - Runs on port `7788`. Can be configured in application.properties file.
    - **Note**: If port error occurs, you can use the command `kill -9 $(lsof -ti:7788)` for `Mac`. For `Windows`, first
      enter `netstat -ano | findstr 7788` which will return the result with PID then enter taskkill /F /PID [PID from result like 572]
    - The database is based on H2. The H2 console can be accessed by url
      `http://localhost:7788/h2-console`. Just login into the console, the credentials are already set.
    - The database has been to initialize with import schema from `schema.sql` and import data from `data.sql` on start of the application.
    - The data includes multiple user accounts and items used for placing order
    - A user has been included at the start of initialization of the project with credentials mentioned above
    - An `interceptor` is also implemented used for authenticating user via `Basic Auth`
    - API used for creating a user has been whitelisted: `/api/user/create`
    



- **Project Working Description**

    - After the project starts and the database has been initialized with data, bill calculation request can be made with the following CURL:
        `curl --location 'localhost:7788/api/calculate' \
      --header 'Content-Type: application/json' \
      --header 'Authorization: Basic aGFtemFAa2hhbi5jb206ZWN4dmF3QSRUdQ==' \
      --header 'Cookie: JSESSIONID=866151576DD0A03AD8805D4E2E2DD8CC' \
      --data-raw '{
      "currency":"USD",
      "targetCurrency":"AED",
      "orderItems":[
      {
      "itemId":15,
      "quantity":2
      }
      ],
      "userEmail":"hamza2@khan.com"
      }'`
    - There are 15 items pre-loaded in the db with their respective categories, itemId in the request be changed, 1-15. 
    - There are 3-4 users pre-loaded with different roles so that the discount is applied differently
    - For resetting the password, an API will be called with the email of the user. The user will receive an request
      approval mail with the link in it which approves the password reset request. This mail is sent from an account
      `test.hamzamk@gmail.com` with credentials being set in `application.properties`
    - After the approval, only then the password can be reset



- **APIs Description**
    - For currency exchange and discount calculatin
        - `POST /api/calculate`
    - For creating user
        - `POST /api/user/create`



- **Other details**
    - Following test classes have been implemented:
         - `InterceptorTest.java`
         - `OrderBillServiceTest.java`
         - `StoreUserServiceTest.java`
    - For test coverage, jacoco
    - Implementation is done on the basis of single environment/profile.

- **Test details**
    - Following test classes have been implemented:
        - `InterceptorTest.java`
        - `OrderBillServiceTest.java`
        - `StoreUserServiceTest.java`
        - `OrderCalcControllerTest.java`
        - `StoreUserControllerTest.java`
    - For test coverage, jacoco plugin has been installed
    - To generate the coverage report, run command `mvn clean test`, then go to `target -> site` folder, open the `index.html` in your desired browser which will show the report
    - Implementation is done on the basis of single environment/profile.