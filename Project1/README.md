# Project-1: Employee Reimbursment System (ERS)

## Executive Summary
* The Expense Reimbursement System (ERS) will manage the process of reimbursing employees for expenses incurred while on company time. 
* All employees in the company can login and submit requests for reimbursement and view their past tickets and pending requests. 
* Finance managers can log in and view all reimbursement requests and past history for all employees in the company. 
* Finance managers are authorized to approve and deny requests for expense reimbursement.

#### Employee User Stories 
- An Employee can login
- An Employee can view the Employee Homepage
- An Employee can logout
- An Employee can submit a reimbursement request
- An Employee can view their pending reimbursement requests
- An Employee can view their resolved reimbursement requests
- An Employee can view their information
- An Employee can update their information

#### Manager User Stories
- A Manager can login
- A Manager can view the Manager Homepage
- A Manager can logout
- A Manager can approve/deny pending reimbursement requests
- A Manager can view all pending requests from all employees
- A Manager can view all resolved requests from all employees and see which manager resolved it
- A Manager can view all Employees
- A Manager can view reimbursement requests from a single Employee 

## Technical Requirements

* The back-end system uses `JDBC` to connect to an **AWS RDS Postgres database**. 
* The application deploys onto a Tomcat Server. 
* The middle tier utilizes Servlet technology. 
* The front-end view uses HTML/JavaScript to call server-side components. 
* The middle tier follows proper layered architecture, has reasonable test coverage of the service layer, and implements **log4j** for appropriate logging. 
