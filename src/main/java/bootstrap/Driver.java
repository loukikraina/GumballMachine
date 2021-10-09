package bootstrap;

import java.time.Instant;
import java.util.*;
import domain.Machine;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.EnhancedPatternLayout;
import org.apache.log4j.Level;
import org.apache.log4j.Priority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.InputMismatchException;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class Driver {
    static Logger logger = LoggerFactory.getLogger(Driver.class);
    public static void main(String[] args) throws Exception{
        configureLogging("candyLogs.log","INFO");

        System.out.println("Welcome to the GUMBALL: The Vending Machine!");

        Machine machine = new Machine();
        machine.setSweetsName(new String[]{"Rasgulla", "kheer", "kajukatli", "Jalebi"});
        machine.setSweetsCost(new double[]{2.5, 1, 6.25, 10});
        machine.setSweetsStock(new int[]{10, 10, 10, 10});
        machine.setDenominations(new int[]{50, 50, 50, 50});

        String password = "LoukikRaina";

        File f = new File("vendor.dat");
        if(f.exists()) {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("vendor.dat"));
            machine = (Machine) objectInputStream.readObject();
        }
        while(true) {

    try {
        // Open given file in append mode.
        Scanner sc= new Scanner(System.in);
        BufferedWriter out = new BufferedWriter(
                new FileWriter("Vending-logs.txt", true));


        System.out.println("Here is our menu, enter choice:\n" +
                "1. Rasgulla        Price: 2.5 Rupee\n" +
                "2. Kheer           Price: 1 Rupee\n" +
                "3. Kajukatli       Price: 6.25 Rupee\n" +
                "4. Jalebi          Price: 10 Rupee\n" +
                "To Exit from this vending machine, Press 5.");
        int choice = sc.nextInt();
        if (choice == 5) {
            System.out.println("Thank You for using this machine!");
            out.close();
            break;
        }
        if (choice == 777) {
            System.out.println("Welcome Admin/Vendor Owner, please enter the admin password\n");
            sc.nextLine();
            String pass = sc.nextLine();
            if (password.equals(pass)) {
                System.out.println("You are in !!!\n" +
                        "Press 1 to add stocks of sweets\n" +
                        "Press 2 to add money denominations");
                int ch = sc.nextInt();
                if (ch == 1) {
                    System.out.println("Enter 4 integers all representing each sweet stocks to be added: ");
                    for (int z = 0; z < 4; z++) {
                        System.out.println(machine.getSweetsName()[z]);
                        machine.getSweetsStock()[z] += sc.nextInt();
                    }
                } else if (ch == 2) {
                    System.out.println("Enter 4 integers all representing each denomination coins to be added: ");
                    for (int z = 0; z < 4; z++) {
                        machine.getDenominations()[z] += sc.nextInt();
                    }
                } else {
                    System.out.println("Wrong choice, program terminating!");
                }
            } else {
                System.out.println("Wrong password imposter, program terminating!");
            }
            out.write("Vending Machine refilled by admin!!\n-------------------------------------\n");
            out.close();
            break;
        }
        System.out.println("Enter Quantity required:");
        int quantity = sc.nextInt();
        System.out.println("Deposit cash(Enter amount):");
        double cash = sc.nextDouble();
        String response;
        response = machine.returncashsuccess(cash, choice, quantity);
        System.out.println(response);
        System.out.println("----------------------------------------------\n");

        Instant instant = Instant.now();
        out.write("Time of transaction: " + instant.toString() + "\n" + response +
                "\nYou gave an amount of " + cash + " Ruppee." +
                "\n----------------------------------------------\n");
        logger.info("Time of transaction: " + instant.toString() + "   " + response +
                "You gave an amount of " + cash + " Ruppee." +
                "\n----------------------------------------------\n");
        out.close();
    }

            catch (IOException e) {
                logger.error("There was an error " + e);
                System.out.println("ERROR (Input exception)");
            }catch (InputMismatchException m){
                logger.error("There was an error " + m);
                System.out.println("ERROR (Input Type Mismatch)");
            }


        }
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("vendor.dat"));
        objectOutputStream.writeObject(machine);


    }
    public static String configureLogging(String logFile,String logLevel){
        DailyRollingFileAppender dailyRollingFileAppender = new DailyRollingFileAppender();

        String logFilename="";
        switch(logLevel){
            case "DEBUG":{
                dailyRollingFileAppender.setThreshold(Level.toLevel(Priority.DEBUG_INT));
            }
            case "WARN":{
                dailyRollingFileAppender.setThreshold(Level.toLevel(Priority.WARN_INT));
            }
            case "ERROR":{
                dailyRollingFileAppender.setThreshold(Level.toLevel(Priority.ERROR_INT));
            }
            default:{
                dailyRollingFileAppender.setThreshold(Level.toLevel(Priority.INFO_INT));
            }
            break;
        }
        System.out.println("Log files written out at " + logFile);
        dailyRollingFileAppender.setFile(logFile);
        dailyRollingFileAppender.setLayout(new EnhancedPatternLayout("%d [%t] %-5p %c - %m%n"));

        dailyRollingFileAppender.activateOptions();
        org.apache.log4j.Logger.getRootLogger().addAppender(dailyRollingFileAppender);
        return dailyRollingFileAppender.getFile();
    }
}
