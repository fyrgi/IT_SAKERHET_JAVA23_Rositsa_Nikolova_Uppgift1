package com.taskone.delivery.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Client {
    private static final String BASE_URL = "http://localhost:8080/v1/users";
    private static final HttpClient httpClient = HttpClient.newHttpClient();
    public void userMenu(){

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(">>>Choose from menu");
            System.out.println("1. Create user");
            System.out.println("2. See user");
            System.out.println("3. Delete account");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (choice) {
                case 1:
                    createUser(scanner);
                    break;
                case 2:
                    getUser(scanner);
                    break;
                case 3:
                    deleteUser(scanner);
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.out.println("ERROR: Wrong choice! Please try again.");
            }
        }
    }

    private static void createUser(Scanner scanner) {
        System.out.print("Enter your email>> ");
        String email = scanner.nextLine();
        System.out.print("Enter password>> ");
        String password = scanner.nextLine();
        System.out.print("Enter full name>> ");
        String name = scanner.nextLine();
        System.out.print("Enter your phone ");
        String phone = scanner.nextLine();
        System.out.println("Enter your address\n(street, nr, postcode, city, country, comment) ");
        String address = scanner.nextLine();
        System.out.println("Enter your Address (Line 1)");
        System.out.println("We store your data to be able to deliver.\nYou have right to see the data we store and to delete it.\nDo you agree to the terms. Write true or false");
        String agreement = scanner.nextLine();
        String json = String.format("{\"address\":\"%s\", \"agreement\":\"%s\", \"email\":\"%s\", \"name\":\"%s\", \"password\":\"%s\", \"phone\":\"%s\"}", address, agreement, email, name, password, phone);
        System.out.println("JSON>>>>> "+json);
        // get the endpoint with postmapping for user add and add the json
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/new"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println("The user was created: " + response.body());
            } else if(response.statusCode() == 429){
                System.out.println("Error! Too many requests. The user was not created." + response.body());
            } else {
                System.out.println("The user was not created.: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void getUser(Scanner scanner) {
        System.out.print("id ");
        long userId = scanner.nextLong();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/user/" + userId))
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.printf("Information about user with id %s\n%s", userId, response.body());
            } else {
                System.out.printf("The user was not found.\nError code " + response.statusCode() + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void deleteUser(Scanner scanner) {
        System.out.print("User to be deleted ");
        long userId = scanner.nextLong();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/delete/" + userId))
                .DELETE()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.printf("The user with id %s was deleted: \n", userId);
            } else {
                System.out.println("The user was not deleted.\nError code " + response.statusCode() + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
