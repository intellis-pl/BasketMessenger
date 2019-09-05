
===================================================================
AUTHOR COMMENTS:
===================================================================

Due to not so much time for finish this task, I did not make it to cover everything with integrations tests. Firstly I thought, that it would be great to put here BDD tests like Cucumber Framework. But due to the upcoming release in my current project, to save some time, I decided to wrote several simple Spring Boot integration tests. I know very well that this is a bad practice, and I should not leave code implementation in that way.
Anyway there is missing some tests like: 
- checking is "Spring Retryable"  retrying to call services after database connection problems etc.
- checking every side defects scenarios with exception message checking

In Cucumber Framework I would wrote a simple scenarios, that it would be checking every api calls - save and fails checking, and also retrying mechanism.


===================================================================
TASK DESCRIPTION:
===================================================================

Imagine you are working on a shopping Cart component, when user choose a product, it will be published to a message queue.

You are developing a micro service component which receives message from message queue, save it into database. You can use embedded database/jms for illustration purpose.

The code should:

1. should have good test coverage.

2. the app should not lose message if the database connections goes down.

3. The app should be able to auto create table if the table doesn’t exist in database.

4. Self-healing from database issue without human intervention.

5. Good fault tolerate.  

