import java.util.Arrays;
import java.util.Scanner;

class Movie {
    private String title;
    private int totalSeats;
    private boolean[] seatsAvailable;
    private boolean[] seatsReserved;

    public Movie(String title, int totalSeats) {
        this.title = title;
        this.totalSeats = totalSeats;
        seatsAvailable = new boolean[totalSeats];
        seatsReserved = new boolean[totalSeats];
        Arrays.fill(seatsAvailable, true); // Initialize all seats as available
    }

    public String getTitle() {
        return title;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public int getAvailableSeats() {
        int count = 0;
        for (boolean seat : seatsAvailable) {
            if (seat) {
                count++;
            }
        }
        return count;
    }

    public boolean reserveSeats(int... seatNumbers) {
        boolean allSeatsAvailable = true;
        for (int seatNumber : seatNumbers) {
            if (seatNumber < 1 || seatNumber > totalSeats || !seatsAvailable[seatNumber - 1]) {
                allSeatsAvailable = false;
                break;
            }
        }
        if (allSeatsAvailable) {
            for (int seatNumber : seatNumbers) {
                seatsAvailable[seatNumber - 1] = false;
                seatsReserved[seatNumber - 1] = true;
            }
            return true;
        }
        return false;
    }

    public void displaySeats(boolean hideReservedSeats) {
        System.out.println("Seat Matrix for " + title + ":");
        int seatNumber = 1;
        for (int i = 0; i < totalSeats; i++) {
            if (hideReservedSeats && seatsReserved[i]) {
                System.out.print("   | ");
            } else {
                if (seatsAvailable[i]) {
                    System.out.print(seatNumber + " | ");
                } else {
                    System.out.print("  | ");
                }
                seatNumber++;
            }
            if ((i + 1) % 10 == 0) {
                System.out.println();
            }
        }
        System.out.println("\n");
    }
}

class MovieTheater {
    private Movie[] movies;
    private Scanner scanner;

    public MovieTheater() {
        movies = new Movie[100];
        movies[0] = new Movie("The Shawshank Redemption", 120);
        movies[1] = new Movie("The Godfather", 150);
        movies[2] = new Movie("The Godfather: Part II", 140);
        // You can add more movies here
        scanner = new Scanner(System.in);
    }

    public void start() {
        boolean continueBooking = true;
        while (continueBooking) {
            displayMovies();
            int choice = getUserChoice();
            if (choice == 0) {
                break;
            }
            if (choice < 1 || choice > movies.length || movies[choice - 1] == null) {
                System.out.println("Invalid choice. Please select a valid movie number.");
                continue;
            }
            Movie selectedMovie = movies[choice - 1];
            bookTickets(selectedMovie);
            continueBooking = promptContinueBooking();
        }
    }

    private void displayMovies() {
        System.out.println("Select a movie:");
        for (int i = 0; i < movies.length; i++) {
            if (movies[i] != null) {
                Movie movie = movies[i];
                System.out.println((i + 1) + ". " + movie.getTitle() + " (" + movie.getAvailableSeats() + " seats available)");
            }
        }
        System.out.println("0. Exit");
    }

    private int getUserChoice() {
        System.out.print("Enter your choice: ");
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Enter a number: ");
            scanner.next(); // discard invalid input
        }
        return scanner.nextInt();
    }

    private void bookTickets(Movie movie) {
        System.out.println("Booking tickets for " + movie.getTitle());
        movie.displaySeats(true); // Hide reserved seats
        System.out.print("Enter the number of seats to reserve: ");
        int numSeats = scanner.nextInt();
        
        System.out.print("Enter the seat numbers separated by spaces: ");
        scanner.nextLine(); // Consume newline
        String input = scanner.nextLine();
        String[] seatNumbers = input.split(" ");
        int[] seats = new int[seatNumbers.length];
        try {
            for (int i = 0; i < seatNumbers.length; i++) {
                seats[i] = Integer.parseInt(seatNumbers[i]);
                if (seats[i] < 1 || seats[i] > movie.getTotalSeats()) {
                    throw new NumberFormatException();
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid seat numbers. Please enter valid seat numbers.");
            return;
        }
        
        if (movie.reserveSeats(seats)) {
            System.out.println("Tickets booked successfully.");
        } else {
            System.out.println("Sorry, one or more seats are not available or invalid.");
        }
    }

    private boolean promptContinueBooking() {
        System.out.print("Do you want to book tickets for another movie? (y/n): ");
        String choice = scanner.next();
        return choice.equalsIgnoreCase("y");
    }
}

public class Main {
    public static void main(String[] args) {
        MovieTheater theater = new MovieTheater();
        theater.start();
    }
}
