package com.jdbc.bus_travel_agency;
	import java.sql.*;
	import java.util.Scanner;

	public class BusTravelAgency {

	    private static final String URL = "jdbc:mysql://localhost:3307/BusTravelAgency";
	    private static final String USER = "root";
	    private static final String PASSWORD = "root";  // Update with your MySQL password
	    
	    private static Connection connection;

	    public static void main(String[] args) {
	        try {
	            connection = DriverManager.getConnection(URL, USER, PASSWORD);
	            Scanner scanner = new Scanner(System.in);
	            int choice;

	            do {
	                System.out.println("----- Bus Travel Agency -----");
	                System.out.println("1. Add Bus");
	                System.out.println("2. View Buses");
	                System.out.println("3. Book Ticket");
	                System.out.println("4. View Bookings");
	                System.out.println("5. Exit");
	                System.out.print("Enter your choice: ");
	                choice = scanner.nextInt();
	                
	                switch (choice) {
	                    case 1:
	                        addBus();
	                        break;
	                    case 2:
	                        viewBuses();
	                        break;
	                    case 3:
	                        bookTicket();
	                        break;
	                    case 4:
	                        viewBookings();
	                        break;
	                    case 5:
	                        System.out.println("Exiting...");
	                        break;
	                    default:
	                        System.out.println("Invalid choice. Please try again.");
	                }
	            } while (choice != 5);

	            connection.close();
	            scanner.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    private static void addBus() throws SQLException {
	        Scanner scanner = new Scanner(System.in);
	        
	        System.out.print("Enter Bus Name: ");
	        String busName = scanner.nextLine();
	        
	        System.out.print("Enter Source: ");
	        String source = scanner.nextLine();
	        
	        System.out.print("Enter Destination: ");
	        String destination = scanner.nextLine();
	        
	        System.out.print("Enter Departure Time (HH:MM:SS): ");
	        String departureTime = scanner.nextLine();
	        
	        System.out.print("Enter Arrival Time (HH:MM:SS): ");
	        String arrivalTime = scanner.nextLine();
	        
	        System.out.print("Enter Total Seats: ");
	        int totalSeats = scanner.nextInt();
	        
	        String query = "INSERT INTO Bus (bus_name, source, destination, departure_time, arrival_time, total_seats) VALUES (?, ?, ?, ?, ?, ?)";
	        PreparedStatement pstmt = connection.prepareStatement(query);
	        pstmt.setString(1, busName);
	        pstmt.setString(2, source);
	        pstmt.setString(3, destination);
	        pstmt.setString(4, departureTime);
	        pstmt.setString(5, arrivalTime);
	        pstmt.setInt(6, totalSeats);
	        
	        int rowsInserted = pstmt.executeUpdate();
	        if (rowsInserted > 0) {
	            System.out.println("Bus added successfully!");
	        } else {
	            System.out.println("Error adding the bus.");
	        }
	    }

	    private static void viewBuses() throws SQLException {
	        String query = "SELECT * FROM Bus";
	        Statement stmt = connection.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        
	        System.out.println("----- Available Buses -----");
	        while (rs.next()) {
	            int busId = rs.getInt("bus_id");
	            String busName = rs.getString("bus_name");
	            String source = rs.getString("source");
	            String destination = rs.getString("destination");
	            String departureTime = rs.getString("departure_time");
	            String arrivalTime = rs.getString("arrival_time");
	            int totalSeats = rs.getInt("total_seats");

	            System.out.println("Bus ID: " + busId);
	            System.out.println("Bus Name: " + busName);
	            System.out.println("Source: " + source);
	            System.out.println("Destination: " + destination);
	            System.out.println("Departure Time: " + departureTime);
	            System.out.println("Arrival Time: " + arrivalTime);
	            System.out.println("Total Seats: " + totalSeats);
	            System.out.println("----------------------------");
	        }
	    }

	    private static void bookTicket() throws SQLException {
	        Scanner scanner = new Scanner(System.in);
	        
	        System.out.print("Enter Bus ID: ");
	        int busId = scanner.nextInt();
	        scanner.nextLine();  // Consume newline
	        
	        System.out.print("Enter Passenger Name: ");
	        String passengerName = scanner.nextLine();
	        
	        System.out.print("Enter Passenger Contact: ");
	        String passengerContact = scanner.nextLine();
	        
	        System.out.print("Enter Seats to Book: ");
	        int seatsBooked = scanner.nextInt();
	        
	        String query = "INSERT INTO Booking (bus_id, passenger_name, passenger_contact, seats_booked, booking_date) VALUES (?, ?, ?, ?, CURDATE())";
	        PreparedStatement pstmt = connection.prepareStatement(query);
	        pstmt.setInt(1, busId);
	        pstmt.setString(2, passengerName);
	        pstmt.setString(3, passengerContact);
	        pstmt.setInt(4, seatsBooked);
	        
	        int rowsInserted = pstmt.executeUpdate();
	        if (rowsInserted > 0) {
	            System.out.println("Ticket booked successfully!");
	        } else {
	            System.out.println("Error booking the ticket.");
	        }
	    }

	    private static void viewBookings() throws SQLException {
	        String query = "SELECT * FROM Booking";
	        Statement stmt = connection.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        
	        System.out.println("----- Booking Details -----");
	        while (rs.next()) {
	            int bookingId = rs.getInt("booking_id");
	            int busId = rs.getInt("bus_id");
	            String passengerName = rs.getString("passenger_name");
	            String passengerContact = rs.getString("passenger_contact");
	            int seatsBooked = rs.getInt("seats_booked");
	            Date bookingDate = rs.getDate("booking_date");

	            System.out.println("Booking ID: " + bookingId);
	            System.out.println("Bus ID: " + busId);
	            System.out.println("Passenger Name: " + passengerName);
	            System.out.println("Passenger Contact: " + passengerContact);
	            System.out.println("Seats Booked: " + seatsBooked);
	            System.out.println("Booking Date: " + bookingDate);
	            System.out.println("----------------------------");
	        }
	    }
	}
