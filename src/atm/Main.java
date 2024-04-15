package atm;

import java.util.Scanner;

import static atm.API.setUtilityAccount;
import static atm.API.utilityAccountExists;

public class Main {

    public static void main(String args[]) {
        Scanner scan = new Scanner(System.in);
        int menuSelect = 0;
        int subMenuSelect = 0;
        System.out.println("Starting Application");

        //login to account
        UtilityAccount utilAcc;
            if(utilityAccountExists()) {
                utilAcc = new UtilityAccount();
                System.out.println("Signing in to Account");
                while(true) {
                    int input;
                    while (true) {
                        System.out.println("Enter 1 to use username, 2 for account number: ");
                        try {
                            input = Integer.parseInt(scan.next());
                            break;
                        } catch (Exception e) {
                            System.out.println("Invalid Input");
                        }
                    }
                    if(input == 1){
                        int i;
                        for(i = 0; i < 3; i++){
                            System.out.println("Input username: ");
                            String username = scan.next();
                            System.out.println("Input password: ");
                            String password = scan.next();
                            if(utilAcc.checkPassword(password) && utilAcc.checkUsername(username)){
                                System.out.println("Successful Sign-in");
                                break;
                            } else{
                                System.out.println("Incorrect Username or Password");
                            }
                        }
                        if(i >= 3){
                            System.out.println("Too many unsuccessful login attempts.");
                            System.out.println("Exiting program...");
                            return;
                        }
                        break;
                    }else if(input == 2){
                        int i;
                        for(i = 0; i < 3; i++){
                            int number;
                            while(true) {
                                try {
                                    System.out.println("Input account number: ");
                                    number = Integer.parseInt(scan.next());
                                    break;
                                } catch (Exception e) {
                                    System.out.println("Invalid Input");
                                }
                            }
                            System.out.println("Input password: ");
                            String password = scan.next();
                            if(utilAcc.checkPassword(password) && utilAcc.checkAccountNum(number)){
                                System.out.println("Successful Sign-in");
                                break;
                            } else{
                                System.out.println("Incorrect Account Number or Password");
                            }
                        }
                        if(i >= 3){
                            System.out.println("Too many unsuccessful login attempts.");
                            System.out.println("Exiting program...");
                            return;
                        }
                        break;
                    }else{
                        System.out.println("Invalid Input");
                    }
                }
            }else {
                System.out.println("Creating new Account");
                System.out.println("Input username: ");
                String username = scan.next();
                System.out.println("Input password: ");
                String password = scan.next();
                utilAcc = new UtilityAccount(username, password);
                setUtilityAccount(utilAcc);

                System.out.println("Account creation successful. Account number is " + utilAcc.getAccountNumber() + ".");
            }

        // retrieving user info from data storage
        CheckingAccount checkAcc = API.getCheckingAccount();
        SavingsAccount saveAcc = API.getSavingsAccount();
        long dayNum = API.getDayNum();

        // Main program loop
        while(true){
            System.out.println("Day: " + dayNum);
            System.out.println("Please select one of the following actions:");
            System.out.println("1: Go to the next Day");
            System.out.println("2: Access Utility Account");
            System.out.println("3: Access ATM");
            System.out.println("4: Quit");

            // Scan Menu Select Input
            while(true) {
                System.out.print("Input: ");
                while (true) {
                    try {
                        menuSelect = scan.nextInt();
                        break;
                    } catch (Exception e) {
                        System.out.println("Invalid Input");
                    }
                }
                System.out.println();
                if (menuSelect < 1 || menuSelect > 4) {
                    System.out.println("Invalid Input");
                } else {
                    break;
                }
            }
              if (menuSelect == 1) { // Increment Day
                dayNum++;
                checkAcc.setAmountDepo(0);
                checkAcc.setAmountWithdrawn(dayNum);
                saveAcc.setAmountDepo(0);
                saveAcc.setAmountWithdrawn(0);
                saveAcc.setAmountTransferred(0);
            }

            else if (menuSelect == 2) { // Utility Menu Access
                while(true){
                    System.out.println("Please select a Utility Account action below:");
                    System.out.println("1: Check Payment History");
                    System.out.println("2: Pay Upcoming Bill");
                    System.out.println("3: Exit");

                    // Scanning menu input select
                    while(true) {
                        while (true) {
                            try {
                                System.out.print("Input: ");
                                subMenuSelect = scan.nextInt();
                                break;
                            } catch (Exception e) {
                                System.out.println("Invalid Input");
                            }
                        }
                        System.out.println();
                        if (subMenuSelect < 1 || subMenuSelect > 3) {
                            System.out.println("Invalid Input");
                        } else {
                            break;
                        }
                    }

                    if (subMenuSelect == 1) { // Payment history
                        Bill[] pastBills = API.getBills();
                        System.out.println("Past Payments:");
                        for (Bill bill : pastBills) {
                            if (bill.getPayStatus()) {
                                System.out.println("Date Paid: " + bill.getPayDate() + ", Amount Paid: " + bill.getPayAmount());
                            }
                        }
                        System.out.println();
                    } else if (subMenuSelect == 2) { // Pay bill
                        Bill[] pastBills = API.getBills();

                        if (pastBills[3].getPayStatus()) {
                            System.out.println("No upcoming bills!");
                            continue;
                        }

                        System.out.println("Upcoming Bill:");
                        System.out.println("Pay Date: " + pastBills[3].getPayDate() + ", Amount Due: " + pastBills[3].getPayAmount());
                        System.out.println("Would you like to pay this bill? 1: Yes, 2: No");

                        while (true) {
                            try {
                                System.out.print("Input: ");
                                subMenuSelect = scan.nextInt();
                                break;
                            } catch (Exception e) {
                                System.out.println("Invalid Input");
                            }
                        }
                        if (subMenuSelect == 1) {
                            if (pastBills[3].getPayAmount() > checkAcc.getBalance()) {
                                System.out.println("You don't have enough funds!");
                            } else {
                                pastBills[3].setPayStatus(true);
                                checkAcc.setBalance(checkAcc.getBalance() - pastBills[3].getPayAmount());
                                System.out.println("Bill successfully paid.");
                            }
                        }

                        API.setBills(pastBills);

                    } else {
                        break;
                    }
                }
            }
            else if (menuSelect == 3) { // ATM Access
                  while(true){
                      System.out.println("Please select an ATM action below:");
                      System.out.println("1: Check Balance");
                      System.out.println("2: Deposit");
                      System.out.println("3: Withdraw");
                      System.out.println("4: Transfer");
                      System.out.println("5: Exit");

                      // Scanning menu select input
                      while(true) {
                          while (true) {
                              try {
                                  System.out.print("Input: ");
                                  subMenuSelect = scan.nextInt();
                                  break;
                              } catch (Exception e) {
                                  System.out.println("Invalid Input");
                              }
                          }
                          System.out.println();
                          if (subMenuSelect < 1 || subMenuSelect > 5) {
                              System.out.println("Invalid Input");
                          } else {
                              break;
                          }
                      }

                      if (subMenuSelect == 1) { // Check balance
                          System.out.println("Which account? \n (1) Checking (2) Savings");
                          while (true) {
                              try {
                                  System.out.print("Input: ");
                                  subMenuSelect = scan.nextInt();
                                  break;
                              } catch (Exception e) {
                                  System.out.println("Invalid Input");
                              }
                          }

                          if (subMenuSelect == 1) { // checking
                              System.out.println("Checking Balance: $" + checkAcc.getBalance());
                          } else { // saving
                              System.out.println("Savings Balance: $" + saveAcc.getBalance());
                          }
                      } else if (subMenuSelect == 2) { // Deposit
                          System.out.println("Which account? \n (1) Checking (2) Savings");
                          while (true) {
                              try {
                                  System.out.print("Input: ");
                                  subMenuSelect = scan.nextInt();
                                  break;
                              } catch (Exception e) {
                                  System.out.println("Invalid Input");
                              }
                          }

                          System.out.println("How much?");
                          int deposit;
                          while (true) {
                              try {
                                  System.out.print("Input: ");
                                  deposit = scan.nextInt();
                                  break;
                              } catch (Exception e) {
                                  System.out.println("Invalid Input");
                              }
                          }

                          if (subMenuSelect == 1) { // checking logic
                              if ((checkAcc.getAmountDepo() + deposit) > 5000) {
                                  System.out.println("Can only deposit " + (5000 - checkAcc.getAmountDepo()) + " more today into Checking.");
                              } else {
                                  System.out.println(deposit + " successfully deposited into Checking.\n");
                                  checkAcc.setBalance(checkAcc.getBalance() + deposit);
                                  checkAcc.setAmountDepo(checkAcc.getAmountDepo() + deposit);
                              }
                          } else { // savings logic
                              if ((saveAcc.getAmountDepo() + deposit) > 5000) {
                                  System.out.println("Can only deposit " + (5000 - saveAcc.getAmountDepo()) + " more today into Savings.");
                              } else {
                                  System.out.println(deposit + " successfully deposited into Savings.\n");
                                  saveAcc.setBalance(saveAcc.getBalance() + deposit);
                                  saveAcc.setAmountDepo(saveAcc.getAmountDepo() + deposit);
                              }
                          }
                      } else if (subMenuSelect == 3) { // Withdraw
                          System.out.println("How much would you like to withdraw from Checking?");
                          int withdraw;
                          while (true) {
                              try {
                                  System.out.print("Input: ");
                                  withdraw = scan.nextInt();
                                  break;
                              } catch (Exception e) {
                                  System.out.println("Invalid Input");
                              }
                          }

                          if ((checkAcc.getAmountWithdrawn() + withdraw) > 500) {
                              System.out.println("Can only withdraw " + (500 - checkAcc.getAmountWithdrawn()) + " more today into Checking.");
                          } else if(checkAcc.getBalance() - withdraw < 0) {
                              System.out.println("Cannot withdraw " + withdraw + ". You only have " + checkAcc.getBalance() + ".");
                          } else {
                              System.out.println(withdraw + " successfully withdrawn from Checking.\n");
                              checkAcc.setBalance(checkAcc.getBalance() - withdraw);
                              checkAcc.setAmountDepo(checkAcc.getAmountWithdrawn() + withdraw);
                          }
                      } else if (subMenuSelect == 4) { // Transfer
                          System.out.println("Which account would you like to transfer FROM? \n (1) Checking (2) Savings");
                          while (true) {
                              try {
                                  System.out.print("Input: ");
                                  subMenuSelect = scan.nextInt();
                                  break;
                              } catch (Exception e) {
                                  System.out.println("Invalid Input");
                              }
                          }

                          System.out.println("How much?");
                          int transfer;
                          while (true) {
                              try {
                                  System.out.print("Input: ");
                                  transfer = scan.nextInt();
                                  break;
                              } catch (Exception e) {
                                  System.out.println("Invalid Input");
                              }
                          }


                          if (subMenuSelect == 1) { // checking logic
                              if (checkAcc.getBalance() < transfer) {
                                  System.out.println("Cannot transfer more than you have! Checking Balance: " + checkAcc.getBalance());
                              } else {
                                  System.out.println(transfer + " successfully transferred into Savings.\n");
                                  checkAcc.setBalance(checkAcc.getBalance() - transfer);
                                  saveAcc.setBalance(saveAcc.getBalance() + transfer);
                              }
                          } else { // saving logic
                              if (saveAcc.getBalance() < transfer) {
                                  System.out.println("Cannot transfer more than you have! Checking Balance: " + checkAcc.getBalance());
                              } else if (saveAcc.getAmountTransferred() + transfer > 100) {
                                  System.out.println("You can only transfer " + (100 - saveAcc.getAmountTransferred()) + " more today.");
                              } else {
                                  System.out.println(transfer + " successfully transferred into Checkings.\n");
                                  checkAcc.setBalance(checkAcc.getBalance() + transfer);
                                  saveAcc.setBalance(saveAcc.getBalance() - transfer);
                                  saveAcc.setAmountTransferred(saveAcc.getAmountTransferred() + transfer);
                              }
                          }
                      } else {
                          break;
                      }
                      System.out.println();
                  }
              }
              else {
                break;
            }
        }
        System.out.println("Exiting App... Goodbye!");

        // Saving data to storage before closing
        API.setCheckingAccount(checkAcc);
        API.setSavingsAccount(saveAcc);
        API.setDayNum(dayNum);
        scan.close();
    }
}
