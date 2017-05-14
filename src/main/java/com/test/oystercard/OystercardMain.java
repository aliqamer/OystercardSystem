package com.test.oystercard;

import com.test.oystercard.bean.UserDetails;
import com.test.oystercard.service.OystercardService;
import com.test.oystercard.service.impl.OystercardServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Ali on 5/14/2017.
 */
public class OystercardMain {

    private OystercardService oystercardService = new OystercardServiceImpl();

    public static void main(String args[]) {

        OystercardMain oystercardMain = new OystercardMain();

        UserDetails userDetails = new UserDetails();
        oystercardMain.startJourney(userDetails);

    }

    private void startJourney(UserDetails userDetails) {

        System.out.println("***************** Oyster Card System ********************");
        System.out.println("Choose amount to load card with initial balance");
        System.out.println("1. £10\n2. £20\n3. £30\n4. £40\n5. £50");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        double balance = getBalanceFor(choice);

        userDetails.setBalance(balance);

        System.out.println("Please enter number of trips user has taken");
        int tripCount = scanner.nextInt();

        List<String> userTrips = new ArrayList<>();
        System.out.println("Enter user's trip details");
        String trip ;

        scanner.nextLine();
        for (int i = 1; i <= tripCount; i++) {
            System.out.print("Enter Trip#"+i+": ");
            trip = scanner.nextLine();
            userTrips.add(trip);
        }

        userDetails.setTrips(userTrips);

        System.out.println("\nUser's journey details -->");
        double remainingBalance = oystercardService.calcualateBalance(userDetails);

        System.out.println("Users remaining balance is: "+remainingBalance);

    }

    private double getBalanceFor(int choice) {

        switch (choice){
            case 1:
                return 10;
            case 2:
                return 20;
            case 3:
                return 30;
            case 4:
                return 40;
            case 5:
                return 50;
        }
        return 0;
    }
}
