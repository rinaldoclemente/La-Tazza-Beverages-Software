# Requirements Document Template

Authors: Antonio Santoro, Rinaldo Clemente, Marco Testa, Giovanni Camarda

Date: 10/04/2019

Version: 8.3

# Contents

- [Stakeholders](#stakeholders)
- [Context Diagram and interfaces](#context-diagram-and-interfaces)
	+ [Context Diagram](#context-diagram)
	+ [Interfaces](#interfaces) 
	
- [Stories and personas](#stories-and-personas)
- [Functional and non functional requirements](#functional-and-non-functional-requirements)
	+ [Functional Requirements](#functional-requirements)
	+ [Non functional requirements](#non-functional-requirements)
- [Use case diagram and use cases](#use-case-diagram-and-use-cases)
	+ [Use case diagram](#use-case-diagram)
	+ [Use cases](#use-cases)
	+ [Relevant scenarios](#relevant-scenarios)
- [Glossary](#glossary)
- [System design](#system-design)

# Stakeholders

| Stakeholder name  | Description | 
| ----------------- |:-----------:|
| Employee          | Buy coffee using cash or his account, check his account | 
| Manager	    	| Sells capsules, buy boxes of capsules, manage credit or debit of employee's accounts, check the cash account |

# Context Diagram and interfaces

## Context Diagram

```plantuml
left to right direction
skinparam packageStyle rectangle

actor Manager as m
actor Employee as e

rectangle system {
  (LaTazza System) as lts
  m -- lts
  lts -- e
}
```

## Interfaces

| Actor | Logical Interface | Physical Interface  |
| ------------- |:-------------:| -----:|
|   Manager    | GUI - manage sells and orders | Screen, Keyboard |
|   Employee    | GUI - buy capsules and check account | Screen, Keyboard |

# Stories and personas

* <b>Manager</b>: Is the employee who manages the selling and the re-stock of coffee capsules. He has to interact with LaTazza desktop application logging as "Manager"
every time a new capsule is required or when the capsules are finished. He could monitor the amount of selled capsules and check the cash account for each employee.
* <b>Employee</b>: Person who works in the company and who can buy a type or more types of coffee capsules trough the internal account using credit card or by paying cash. 

* <b>Example of Storia for the Manager Persona:</b> Tom Hansen is the Manager of the Company A. He is married and he has no children. Tom is the responsible in managing LaTazza software in his Company in order to reach the goal the company wanted to. He wakes up in the morning, goes to work at 8:30 and first checks if any capsules order has to be make. When the employees arrive, if some employee wants to recharge his credit balance to buy a capsule, Tom has to recharge the credit on the employee account in order to allow him/her to buy capsules with credit card trough his/her internal account. During the day a new visitor can enter in the Company, so Tom has to take visitor request and sells them capsules using cash money. 
Every time the number of a type of capsules goes below a certain threshold Tom has to order boxes trough LaTazza Desktop. At the end of the day, he has to be sure that all requests have been satisfied. 
Goal: Wants to manage this idea of “sharing coffee” using a shared coffee machine in the easiest way.

* <b>Example of Storia for the Employee Persona: </b> Sarah Smith is an employee of CompanyA, she is married and after she drove children to school she reaches the Company A at 9:00. She is always in hurry, so the first thing she does when arrive is to login with her internal account to LaTazza and checks for her credit left. If there is credit enough, she makes a capsule buy request sent to the Manager. Then she can have her coffee using the capsule given from the Manager, and making it with the shared coffee machine.
Goal: Wants to have the possibility to have coffee using just the software application provided in her office pc.




# Functional and non functional requirements

## Functional Requirements

| ID        | Description  |
| ------------- |:-------------:|
|  FR1     |  Check inventory |
|  FR2     |  Update inventory |
|  FR3     |  Submit restock order |
|  FR4     |  Create employees account |
|  FR4.1   |  Delete employees account |
|  FR4.2   |  Recharge employees account |
|  FR4.3   |  Check employee accounts |
|  FR5     |  Check cash account |
|  FR6     |  Update cash account |
|  FR7     |  Sell capsules with cash |
|  FR8     |  Buy capsules with account |
|  FR9     |  Login |


## Non Functional Requirements

| ID        | Type (efficiency, reliability, ..)           | Description  | Refers to |
| ------------- |:-------------:| :-----:| -----:|
|  NFR1     | Performance 		    | System answer time less then 5 sec | All FRs |
|  NFR2     | Management		    | The manager shall order boxes of capsules when in the warehouse there are only 10 capsules of that type | FR1/FR2 |
|  NFR3     | Security 		        | The system doesn't permit to share employee's personal information | FR4 |
|  NFR4     | Backup 		        | Data Backup once a day | All FRs |
|  NFR5     | Security 		        | Application Interface used only by the manager | All FRs |

# Use case diagram and use cases


## Use case diagram
```plantuml
left to right direction

actor Manager as m
actor Employee as e

(FR2 Update inventory) as ui
(FR7 Sell capsules) as sc
(FR6 Update cash account) as uca
(FR8 Buy capsules) as bc
(FR5 Check cash account) as cca
(FR1 Check inventory) as ci
(FR3 Submit restock order) as rs
(FR4.2 Recharge credits) as rc
(FR4 Register employee account) as rea
(FR4.1 Delete employee account) as dea
(FR4.3 Check employee balance) as ceb
(FR9 Login) as l

sc .> ui : include
uca <. sc : include
rs .> uca : include
rc .> uca : include
bc .> ui : include

ui <-- m
uca <-- m
cca <-- m
ci <-- m
rs <-- m
sc <-- m
dea <-- m
rea <-- m
rc <-- m
ceb <-- m
l <-- m
e --> bc
e --> l
e --> ceb

```
## Use Cases

### Check cash account, UC1

| Actors Involved        | Manager | 
| ------------- |:-------------:|
|  Precondition     | Logged as manager |  
|  Post condition     | The cash account balance amount is displayed |
|  Nominal Scenario     | The system displays the total employee account balance |
|  Variants     | The system will print an error message if it cannot retrieve the cash account data |

### Check employee balance, UC2

| Actors Involved        | Manager, Employee | 
| ------------- |:-------------:|
|  Precondition     | The manager/employee selects an employee from the employee account list |  
|  Post condition     | The selected employee balance is displayed |
|  Nominal Scenario     | The manager/employee selects the employee account in order to check his balance, the system will print a negative number in case of debt |
|  Variants     | The system will print an error message if it cannot retrieve the employee account data |

### Check inventory, UC3

| Actors Involved        | Manager | 
| ------------- |:-------------:|
|  Precondition     | Logged as manager |  
|  Post condition     | A list of all capsule types is displayed |
|  Nominal Scenario     | The system displays the inventory that shows the quantity available for each type of capsule |
|  Variants     | The application will print an error message if it cannot retrieve the inventory data |

### Submit restock order, UC4

| Actors Involved        | Manager | 
| ------------- |:-------------:|
|  Precondition     | The cash account balance is sufficient for the order |  
|  Post condition     | A new restock order is submitted  |
|  Nominal Scenario     | The manager selects the number of boxes and the type of beverage to buy then the system will take the money from the cash account  |
|  Variants     | The system will print an error message if the balance is not sufficient for the payment |

### Register employee account, UC5

| Actors Involved        | Manager | 
| ------------- |:-------------:|
|  Precondition     | Request made from an employee |  
|  Post condition     | The employee account is added to the account list |
|  Nominal Scenario     | The manager creates a new employee account after inserting all the necessary credentials |
|  Variants     | The system will print an error message if it cannot add the employee account in the list |

### Delete employee account, UC6

| Actors Involved        | Manager | 
| ------------- |:-------------:|
|  Precondition     | Request made from an employee |  
|  Post condition     | The employee account is deleted from the list |
|  Nominal Scenario     | The manager selects the employee account to delete from the account list |
|  Variants     | The system will print an error message if it cannot remove the employee account from the list |

### Sell capsules, UC7

| Actors Involved        | Manager | 
| ------------- |:-------------:|
|  Precondition     | At least one type of capsule is in stock |  
|  Post condition     | The inventory and the cash account are updated |
|  Nominal Scenario     | The manager selects the capsule type and the quantity to sell handling the payment accepting cash then the inventory and the cash account will be updated |
|  Variants     | The system should eventually gray out/not display all the unavailable type of capsules |

### Recharge credits, UC8

| Actors Involved        | Manager | 
| ------------- |:-------------:|
|  Precondition     | The employee account exists |  
|  Post condition     | The employee account balance is updated |
|  Nominal Scenario     | The manager selects an employee from the list, choose the number of credits to charge and handles the payment accepting cash, the cash accout is then updated |
|  Variants     | The system will print an error message in case of employee account update failure |

### Buy capsules, UC9

| Actors Involved        | Employee | 
| ------------- |:-------------:|
|  Precondition     | Logged as an employee |  
|  Post condition     | The corresponding order amount is taken from the credit balance |
|  Nominal Scenario     | The employee selects the quantity and the type of capsule to buy, the system then will take the corresponding order amount from the credit balance and updates the cash account and the inventory |
|  Variants     | The system should print an error message if it cannot retrieve the employee account data or not display/gray out all the unavailable capsule types |

### Login, UC10

| Actors Involved        | Manager, Employee | 
| ------------- |:-------------:|
|  Precondition     | The application is open |  
|  Post condition     | Successfully logged into the system |
|  Nominal Scenario     | An employee / the manager puts username and password then log in into the corresponding user interface |
|  Variants     | The system should print an error message if the put credentials are not correct |

### Update cash account, UC11

| Actors Involved        | Manager | 
| ------------- |:-------------:|
|  Precondition     | Logged as manager |  
|  Post condition     | The total cash account balance is updated |
|  Nominal Scenario     | The manager puts the quantity to add or remove from the cash account balance and then confirms the update |
|  Variants     | The system should print an error message if the input amount is not valid |

### Update inventory, UC12

| Actors Involved        | Manager | 
| ------------- |:-------------:|
|  Precondition     | Logged as manager |  
|  Post condition     | The inventory is updated |
|  Nominal Scenario     | The manager selects the capsule type and puts the quantity to add or remove from the inventory, he will then confirms the update |
|  Variants     | The system should print an error message if the input amount is not valid or the data cannot be updated on the server side |

# Relevant scenarios

## Successfull sale of capsules to a visitor

| Scenario ID: SC1        | Corresponds to UC:  Sell capsules, [UC7](#sell-capsules-uc7) |
| ------------- |:-------------:| 
| Step#        | Description  |
|  1     | The manager selects the capsule type to sell |  
|  2     | The manager sets the quantity |
|  3     | The manager handles the payment accepting cash |
| 4 | The manager confirms the payment |
| 5 | The manager updates the cash account |
| 6 | The manager updates the inventory |

## Successfull restock order

| Scenario ID: SC2        | Corresponds to UC: Submit restock order, [UC4](#submit-restock-order-uc4) |
| ------------- |:-------------:| 
| Step#        | Description  |
|  1     | The manager selects the quantity number of capsule boxes to buy |  
|  2     | The manager selects the capsule type |
|  3     | The system takes the corresponding amount from the cash account for the order |
|  4     | The manager updates the inventory after the order has been delivered |

# Glossary

```plantuml
class Capsule {
+ type
+ price
}

class Inventory {
}

class "Cash balance" {
+ amount
}

class Manager {
+ name
+ surname
+ address
}

class "Commercial transaction" {
+ date
+ amount
+ operation
}

class Account {
+ username
+ credit
}

class Employee {
+ name
+ surname
+ address
}

Manager "1" -- "*" Account : creates
Employee "1" -- "0..1" Account
Manager "1" -- "1" Inventory : checks
Manager "1" -- "*" "Cash balance": manages
Capsule "1..*" -- "1" "Commercial transaction" : sale
Inventory "1" -- "0..*" Capsule : lists
"Commercial transaction" "1..*" --o "1" "Cash balance"

note top of Inventory: List of all capsule types\nwith the corresponding\nleft amount
note right of Capsule: Physical product
note top of Manager: Employee committed to\nmanage the capsule sales
note right of "Commercial transaction": Commercial transaction\nbetween manager and visitor\nor employee
note bottom of Account: Employee account made for\nbuying capsules using credits\npreviously recharged by request

```


# System Design

```plantuml
class "LaTazza system" {
  + submit restock order()
  + sell capsules with cash()
  + buy capsules with account()
  + check cash account()
  + update cash account()
}

class Computer {
}


class Server {
  + update inventory()
  + check inventory()
  + create employees account
  + delete employees account()
  + recharge employees account()
  + check employee accounts()
  + login()
  
}

"LaTazza system" o-- Computer
"LaTazza system" o-- Server

```
