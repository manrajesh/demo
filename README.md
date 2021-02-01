# Getting Started

### Tools 

* Gradle, Spring Boot, JPA, H2, Swagger2
### Guides
The following guides illustrate how to use the Employee API.

#### Run the app with any IDE like Intellij.
#### Use `gradle bootRun` to run the application.
#### Use `gradle test` to run unittests.
##### Open the Swagger at http://localhost:8080/swagger-ui.html
* Working API's to test with params and path variables
    * Update the salary by place
        * curl -X PUT --header 'Content-Type: application/json' --header 'Accept: application/json' 'http://localhost:8080/employee/place/Australia/salary/40'
        * curl -X PUT --header 'Content-Type: application/json' --header 'Accept: application/json' 'http://localhost:8080/employee/place/Canada/salary/30'
        * curl -X PUT --header 'Content-Type: application/json' --header 'Accept: application/json' 'http://localhost:8080/employee/place/Europe/salary/30'
        
    * Get 5 Employees per page by given place
        * curl -X GET --header 'Accept: application/json' 'http://localhost:8080/employee/Australia/0'
        * curl -X GET --header 'Accept: application/json' 'http://localhost:8080/employee/Europe/0'
        * curl -X GET --header 'Accept: application/json' 'http://localhost:8080/employee/Australia/1'
        
    * Get Range(MaxSalary and MinSalary) of salaries for a given competency
        * curl -X GET --header 'Accept: application/json' 'http://localhost:8080/employee/range?competency=Developer'
        * curl -X GET --header 'Accept: application/json' 'http://localhost:8080/employee/range?competency=UX'
        * curl -X GET --header 'Accept: application/json' 'http://localhost:8080/employee/range?competency=Design'