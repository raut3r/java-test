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
                        if(commands.length==2) {
                            if (commands[1].equals("m") || commands[1].equals("w")){
                                System.out.println("\nEmployee entry started.");
                                addEmployee(commands[1]);
                                System.out.println("\nEmployee entry ended.");
                                break;
                            }
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
            List<Employee> user = employeeList.stream()
                    .filter(c -> c.getId() == Integer.parseInt(command))
                    .collect(Collectors.toList());
            if(user.size()==0){
                System.out.println(String.format("\nEmployee with an ID:%s doesn't exist.", command));
            }
            employeeList.removeIf(s -> s.getId() == Integer.parseInt(command));

            System.out.println("\n" + user.get(0).getName() + " has been removed successfully.\n");
        }
        catch(Exception e){
            System.out.println("Enter a correct number after remove.");
        }


    }

    static void searchEmployee(String command) {
        List<Employee> user = employeeList.stream()
                .filter(c -> c.getName().equals(command))
                .collect(Collectors.toList());
        if(user.size()==0){
            System.out.println(command + " doesn't exist, please add employee first.\n");
        }
        else{
            System.out.println("\n" + command + " worked " + user.get(0).getWorkingHours() + " hours this week.\n" );
        }
    }
}
