package com.filip;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main{
    private static int idCounter = 1;
    private static List<Employee> employeeList = new ArrayList<>();

    public static void main(String[] args) {
	// write your code here
        int exit = 0;

        System.out.println("Hello, user!");

	// define scanner as static variable on class level and access it from there, so you don't create multiple instances
        Scanner myObject = new Scanner(System.in);

        while(exit == 0){

            System.out.println("Please enter command:");
            String command = myObject.nextLine();

            String[] commands = command.trim().split(" ");

            if(commands.length > 2 || commands.length==0){
                System.out.println("You entered too long command!");
            }
            else{
                switch (commands[0]) {
                    case "add":
			// can be combined into one if statement
                        if(commands.length==2 && (commands[1].equals("m") || commands[1].equals("w"))) {
                                System.out.println("\nEmployee entry started.");
                                addEmployee(commands[1]);
                                System.out.println("\nEmployee entry ended.");
                                break;
                        }
                        System.out.println("You entered the wrong add command!");
                        break;
                    case "show":
                        System.out.println("\nID  Name  Hours_worked  Salary");
                        employeeList.stream().map(e -> e.getId() + "   "
                                +e.getName() + "      "
                                +e.getWorkingHours() + "        "
                                +e.calculateSalary(e.getWorkingHours()))
                                .forEach(System.out::println);
                        break;
                    case "remove":
                        if(commands.length==2){
                            removeEmployee(commands[1]);
                            break;
                        }
                        System.out.println("You entered the wrong remove command!");
                        break;
                    case "search":
                        if(commands.length==2){
                            searchEmployee(commands[1]);
                            break;
                        }
                        System.out.println("You entered the wrong search command!");
                        break;
                    case "exit":
                        exit = 1;
                        break;
                    default:
                        System.out.println("You entered the wrong command!");
                        break;
                }
            }
        }
        myObject.close();
    }

    static void addEmployee(String command) {
	// you can define scanner as static variable on class level and access it from there, so you don't create multiple instances
	// also, you forgot to release the scanner at the end of method, that could be potential memory leak
        Scanner myObject = new Scanner(System.in);
        try{
            while(true){

                System.out.println("\nPlease enter employee name:");
                String name = myObject.nextLine();
                if(name.equals("end"))break;

                try{
                    int hours;

                    while(true){
                        System.out.println("Please enter employee workingHours:");
                        hours = myObject.nextInt();
                        if(hours<100 && hours>=0){
                            break;
                        }
                        System.out.println("Employee can't have workingHours higher than 100, or less than 0.");
                    }

                    if(command.equals("w")){
                        Worker worker = new Worker();
                        worker.setName(name);
                        worker.setWorkingHours(hours);
                        worker.setId(idCounter++);
                        employeeList.add(worker);
                    }

                    else{
                        Manager manager = new Manager();
                        manager.setName(name);
                        manager.setWorkingHours(hours);
                        manager.setId(idCounter++);
                        employeeList.add(manager);
                    }
                    //Sort list by name
                    employeeList.sort(Comparator.comparing(Employee::getName));
                    myObject.nextLine();
                }
                catch(Exception e){
                    System.out.println("You didn't enter a number!\n");
                    break;
                }
            }
        }
        catch(Exception e){
            System.out.println("You entered the wrong command!\n");
        }
    }

    static void removeEmployee(String command) {
        try{
	    // you don't need ¸list of employees, since every employee has unique id, and you will always get 1 employee for specified id if same exists
	    // Collection::remove and Colleciton::removeIf methods return boolean that indicates wherer item was removed from list or not
            Employee employee = employeeList.stream().filter(c -> c.getId() == Integer.parseInt(command)).findFirst().orElse(null);

            if(employeeList.remove(employee)){
                System.out.println("\n" + employee.getName() + " has been removed successfully.\n");
            }
        }
        catch(Exception e){
	    System.out.printf("\nEmployee with an ID:%s doesn't exist.%n", command);
            System.out.println("Enter a correct number after remove.");
        }


    }

    static void searchEmployee(String command) {
	// there is a shortcut stream method ifPresentOrElse() so you don't have to check with if if employee is present
        employeeList.stream()
                .filter(c -> c.getName().equals(command))
                .findFirst()
            .ifPresentOrElse(emp -> System.out.println("\n" + command + " worked " + emp.getWorkingHours() + " hours this week.\n"),
                () -> System.out.println(command + " doesn't exist, please add employee first.\n"));
    }
}
