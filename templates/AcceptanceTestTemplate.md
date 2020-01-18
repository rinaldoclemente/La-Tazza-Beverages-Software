# Acceptance Testing Documentation template

Authors: Marco Testa, Giovanni Camarda, Rinaldo Clemente, Antonio Santoro

Date: 25/05/2019

Version: 1.0

# Contents

- [Scenarios](#scenarios)

- [Coverage of scenarios](#scenario-coverage)
- [Coverage of non-functional requirements](#nfr-coverage)



# Scenarios


| Scenario ID: SC1 | Corresponds to UC1                             |
| ---------------- | ---------------------------------------------- |
| Description      | Colleague uses one capsule of type 1           |
| Precondition     | account of C has enough money to buy capsule T |
| Postcondition    | account of C updated, count of T updated       |
| Step#            | Step description                               |
| 1                | Administrator selects capsule type T           |
| 2                | Administrator selects colleague C              |
| 3                | Deduce one for quantity of capsule T           |
| 4                | Deduce price of T from account of C            |

| Scenario ID: SC1.1 | Corresponds to UC2                             |
| ---------------- | ---------------------------------------------- |
| Description      | Visitor uses one capsule of type 1           |
| Precondition     | There are enough capsules of type 1 |
| Postcondition    | Record consumption with ID: 0, update LaTazzaAccount.Balance |
| Step#            | Step description                               |
| 1                | Administrator selects capsule type T           |
| 2                | Administrator selects Visitor from GUI|
| 3                | The amount of capsules of type 1 decreases of one |
| 4                | LaTazzaAccount.Balance is updated adding the price that Visitor paid |

| Scenario ID: SC2 | Corresponds to UC1                                     |
| ---------------- | ------------------------------------------------------ |
| Description      | Colleague uses one capsule of type T, account negative |
| Precondition     | account of C has not enough money to buy capsule T     |
| Postcondition    | account of C updated, count of T updated               |
| Step#            | Step description                                       |
| 1                | Administrator selects capsule type T                   |
| 2                | Administrator selects colleague C                      |
| 3                | Deduce one for quantity of capsule T                   |
| 4                | Deduce price of T from account of C                    |
| 5                | Account of C is negative, issue warning                |

| Scenario ID: SC3 | Corresponds to UC3 |
| ---------------- | ------------------ |
| Description      | Colleagues asks for his Account Recharge |
| Precondition     | Account A exists                |
| Postcondition    | A.Balance is updated, LaTazzaAccount.Balance updated               |
| Step#            | Step description               |
| 1                | Administrator selects account A of colleague C |
| 2                | Administrator update A.amount |

| Scenario ID: SC4 | Corresponds to UC4 |
| ---------------- | ------------------ |
| Description      | Administrator purchases capsules |
| Precondition     | Capsule type CT exists           |
| Postcondition    | CT.quantity is updated, LaTazzaAccount.Balance is updated |
| Step#            | Step description               |
| 1                | Administrator selects CapsuleType CT |
| 2                | Administrator selects CT.amount to purchase |
| 3                | Updates CT.amount purchased |
| 4                | LaTazzaAccount.Balance decreases |

| Scenario ID: SC5 | Corresponds to UC5 |
| ---------------- | ------------------ |
| Description      | LaTazza produces report on consumption of colleague |
| Precondition     | Colleague C exists |
| Postcondition    | Report is produced |
| Step#            | Step description |
| 1                | Administrator selects Colleague C |
| 2                | Administrator defines a time range |
| 3                | LaTazza collects recharges for C in that time range |
| 4                | LaTazza collects capsules taken for C in the time range |
| 5                | LaTazza presents the transacions report |

| Scenario ID: SC6 | Corresponds to UC6 |
| ---------------- | ------------------ |
| Description      | LaTazza procudes report on all consumptions |
| Precondition     | Colleagues C(i) exist, consumptions exist in the time range |
| Postcondition    | Report is produced |
| Step#            | Step description               |
| 1                | Administrator selects "Generate consumption Report" |
| 2                | Administrator defines a time range |
| 3                | LaTazza collects recharges for C(i) in that time range |
| 4                | LaTazza collects capsules taken for C(i), V(i) in the time range |
| 5                | LaTazza presents the transactions report |


# Coverage of Scenarios

### 

| Scenario ID | Functional Requirements covered | API Test(s) | GUI Test(s) |
| ----------- | ------------------------------- | ----------- | ----------- |
| SC1           | FR1                           |  TestDataImpl.sellCapsulesTC1<br/>TestDataImpl.sellCapsulesTC           |   TestSC1   |
| SC1.1         | FR2                           |  TestDataImpl.sellCapsulesToVisitorTC(1-4)         |   TestSC1.1 |
| SC2           | FR1                           |  TestDataImpl.sellCapsules          |   TestSC2   |
| SC3           | FR3, FR8                      |  TestDataImpl.updateEmployeeTC1<br/>TestDataImpl.rechargeAccountTC1          |   TestSC3   |
| SC4           | FR4, FR7                      |  TestDataImpl.buyBoxesTC(1-4)           |   TestSC4   |
| SC5           | FR5                           |  TestDataImpl.getEmployeeReportTC(1-4)  |   TestSC5   |
| SC6           | FR6                           |  TestDataImpl.getReportTC(1-4)<br/> TestDataImpl.getEmployeeReportWTC(1-4)           |   TestSC6   |




# Coverage of Non Functional Requirements


### 

| Non Functional Requirement | Test name |
| -------------------------- | --------- |
| NFR2: Performance, "All functions should complete in < 0.5 sec"| TestNFR2 |

