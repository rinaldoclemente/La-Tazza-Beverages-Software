# Design Document Template

Authors: Antonio Santoro, Rinaldo Clemente, Marco Testa, Giovanni Camarda

Date: 13/05/2019

Version: 8.1

# Contents

- [Package diagram](#package-diagram)
- [Class diagram](#class-diagram)
- [Verification traceability matrix](#verification-traceability-matrix)
- [Verification sequence diagrams](#verification-sequence-diagrams)

# Package diagram

```plantuml


package GUI
package it.polito.latazza {

package data
package exception
}
package DAO

DAO <.. it.polito.latazza
data ..> exception : <<import>>
GUI <.. it.polito.latazza

note top of DAO : data persistency
```

Architectural patterns used: **MVC** (develops user interfaces that divides an application into three interconnected parts)


# Class diagram

``` plantuml
class DataImpl << (S,#FF7700) singleton >> {
-instance: DataImpl
#DataImpl()
+{static}getInstance(): DataImpl
}

interface DataInterface {
+sellCapsules(employeeId: Integer, beverageId: Integer, numberOfCapsules: Integer, fromAccount: Boolean): Integer
+sellCapsulesToVisitor(beverageId: Integer, numberOfCapsules: Integer): void
+rechargeAccount(id: Integer, amountInCents: Integer): Integer
+buyBoxes(beverageId: Integer, boxQuantity: Integer): void
+getEmployeeReport(employeeId: Integer, startDate, Date endDate: Date): List<String>
+getReport(startDate: Date, endDate: Date): List<String>
+createBeverage(name: String, capsulesPerBox: Integer, boxPrice: Integer): Integer
+updateBeverage(id: Integer, name: String, capsulesPerBox: Integer, boxPrice: Integer): void
+getBeverageName(id: Integer)
+getBeverageCapsulesPerBox(id: Integer): Integer
+getBeverageBoxPrice(id: Integer): Integer
+getBeveragesId(): List<Integer>
+getBeverages(): Map<Integer, String>
+getBeverageCapsules(id: Integer): Integer
+createEmployee(name: String, surname: String): Integer
+updateEmployee(id: Integer, name: String, surname: String): void
+getEmployeeName(id: Integer): String
+getEmployeeSurname(id: Integer): String
+getEmployeeBalance(id: Integer): Integer
+getEmployeesId(): List<Integer>
+getEmployees(): Map<Integer, String>
+getBalance(): Integer
+reset(): void
}

class Colleague {
- colleagueId: Integer
- name: String
- surname: String
+ Colleague(colleagueId: Integer, name: String, surname: String)
+ getName(): String
+ getSurname(): String
}

class PersonalAccount {
- balance: Integer
+ PersonalAccount()
+ getBalance(): Integer
+ getAccountId(): Integer
+ addBalance(amount: Integer): void
+ decBalance(amount: Integer): void
}

class CapsuleType {
- capsuleId: Integer
- name: String
- price: Double
- quantity: Integer
- qtyPerBox: Integer
+ CapsuleType(capsuleId: Integer, name: String, price: Integer, quantity: Integer)
+ getId(): Integer
+ getName(): String
+ getPrice(): String
+ getQuantity(): Integer
+ getQtyPerBox(): Integer
+ addQuantity(amount: Integer): void
+ decQuantity(amount: Integer): void

}

class LaTazzaAccount {
- balance: Integer
+ LaTazzaAccount(balance: Integer)
+ getBalance(): Integer
+ addBalance(amount: Integer): void
+ decBalance(amount: Integer): void
}

class Transaction {
- transactionId: Integer
- date: Date
- amount: Integer
+ Transaction(transactionId: Integer, date: Date, amount: Integer)
+ addTransaction(date: Date, amount: Integer) : void
}

class BoxPurchase {
- quantity: Integer
+ BoxPurchase(boxPurchaseId: Integer, date: Date, amount: Integer, quantity: Integer)
+ getQuantity(): Integer
}

class Recharge {
- colleagueId: Integer
+ Recharge(rechargeId: Integer, date: Date, amount: Integer, colleagueId: Integer)
+ getEmployeeId(): Integer
}

class Consumption {
- quantity: Integer
- colleagueId: Integer
- fromAccount : boolean
+ Consumption(consumptionId: Integer, date: Date, amount: Integer, quantity: Integer)
+ getQuantity(): Integer
}

DataInterface <|.. DataImpl

Transaction <|-- Consumption
Transaction <|-- BoxPurchase
Transaction <|-- Recharge

DataImpl o-- "1" LaTazzaAccount
DataImpl o-- "*" Colleague
DataImpl o-- "*" Transaction
Colleague o-- "1" PersonalAccount

BoxPurchase o-- "1" CapsuleType
Consumption o-- "1" CapsuleType

note "Data persistency is implied" as N
```
Design patterns used: **Singleton** (restricts object creation for a class to only one instance)


# Verification traceability matrix


|  | DataInterface | DataImpl | LaTazzaAccount | BoxPurchase | Consumption | Recharge | Colleague | Transaction | PersonalAccount | CapsuleType |
| ------------- |:-------------:|:-------------:|:-------------:|:-------------:|:-------------:|:-------------:|:-------------:|:-------------:|:-------------:|:-------------:|
| FR1  |     X      |     X     |      X         |             |      X       |         |      X    |      X      |        X        |         X   |
| FR2  |     X      |     X     |      X         |             |      X       |         |           |       X     |                 |         X   |
| FR3  |     X      |     X     |                |             |              |    X    |     X     |       X     |        X        |             |
| FR4  |     X      |     X     |       X        |      X      |              |         |           |       X     |                 |       X     |
| FR5  |     X      |     X     |                |             |        X     |     X   |     X     |      X      |                 |             |
| FR6  |     X      |     X     |                |             |        X     |    X    |           |       X     |                 |             |
| FR7  |     X      |     X     |       X        |             |              |         |           |             |                 |       X     |
| FR8  |     X      |     X     |                |             |              |         |     X     |             |        X        |             |

# Verification sequence diagrams 

*Sequence diagram related to Scenario 1*

```plantuml
actor Administrator as a
participant ": GUI" as gui
participant ": DataInterface" as di
participant ": DAO" as db

a -> gui : "sell capsules"
activate gui
gui -> di : sellCapsules(C, T, 1, true)
activate di
di -> db : getBeverageById(T)
create participant "b: CapsuleType" as b
activate db
db --> b : <<create>>
di -> b : getPrice()
activate b
di <-- b : price
di -> b : decQuantity(1)
di <-- b : quantity
deactivate b
di -> db : updateBeverage(b)
di <-- db
deactivate db

di -> db : getEmployeeAccountById(C)
activate db
create participant "e: PersonalAccount" as e
db --> e : <<create>>
deactivate db
di -> e : decBalance(price*1)
activate e
di <-- e : new_balance
di -> db : updateEmployeeAccount(e)
activate db
di <-- db
deactivate e

db -> e : <<destroy>>
destroy e

create participant "c: Consumption" as c
di --> c : <<create>>
di -> c : addBeverageInfo(b)
activate c
di <-- c
deactivate c

db -> b : <<destroy>>
destroy b
deactivate db

di -> db : addConsumption(c)
activate db
di <-- db
deactivate db
di -> c : <<destroy>>
destroy c

di -> db : getCashAccount()
activate db
create participant "ca: LaTazzaAccount" as ca
db --> ca : <<create>>
deactivate db
di -> ca : addBalance(price*1)
activate ca
di <-- ca : new_balance
deactivate ca
di -> db : updateCashAccount(ca)
activate db
di <-- db
db -> ca : <<destroy>>
destroy ca
deactivate db

gui <-- di : new_balance
deactivate di
a <-- gui : "Cash account balance : new_balance"
deactivate gui
```

*Sequence diagram related to Scenario 2*


```plantuml
actor Administrator as a
participant ": GUI" as gui
participant ": DataInterface" as di
participant ": DAO" as db

a -> gui : "sell capsules"
activate gui
gui -> di : sellCapsules(C, T, 1, true)
activate di
di -> db : getBeverageById(T)
create participant "b: CapsuleType" as b
activate db
db --> b : <<create>>
di -> b : getPrice()
activate b
di <-- b : price
di -> b : decQuantity(1)
di <-- b : quantity
deactivate b
di -> db : updateBeverage(b)
di <-- db
deactivate db

di -> db : getEmployeeAccountById(C)
activate db
create participant "e: PersonalAccount" as e
db --> e : <<create>>
deactivate db
di -> e : decBalance(price*1)
activate e
di <-- e : new_balance
di -> db : updateEmployeeAccount(e)
activate db
di <-- db
deactivate e


db -> e : <<destroy>>
destroy e


create participant "c: Consumption" as c
di --> c : <<create>>
di -> c : addBeverageInfo(b)
activate c
di <-- c
deactivate c



db -> b : <<destroy>>
destroy b
deactivate db

di -> db : addConsumption(c)
activate db
di <-- db
deactivate db
di -> c : <<destroy>>
destroy c

di -> db : getCashAccount()
activate db
create participant "ca: LaTazzaAccount" as ca
db --> ca : <<create>>
deactivate db
di -> ca : addBalance(price*1)
activate ca
di <-- ca : new_balance
deactivate ca
di -> db : updateCashAccount(ca)
activate db
di <-- db
db -> ca : <<destroy>>
destroy ca
deactivate db

gui <-- di : new_balance + "warn message"
deactivate di
a <-- gui : "Cash account balance : new_balance, msg: 'the employee C has a negative account balance' "
deactivate gui
```