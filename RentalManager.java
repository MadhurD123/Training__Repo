
import java.util.ArrayList;
import java.util.Scanner;

public class RentalManager {

    ArrayList<Vehicle> vehicles = new ArrayList<>();
    ArrayList<Customer> customers = new ArrayList<>();
    ArrayList<Booking> bookings = new ArrayList<>();

    // Add vehicle
    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
        System.out.println("Vehicle added successfully!");
    }

    // Display available vehicles
    public void displayVehicles() {
        for (Vehicle v : vehicles) {
            if (v.available) {
                v.display();
            }
        }
    }

    // Register customer
    public void addCustomer(Customer customer) {
        customers.add(customer);
        System.out.println("Customer registered successfully!");
    }

    // Book vehicle
    public void bookVehicle(Booking booking)
            throws VehicleNotAvailableException, InvalidBookingException {

        if (booking.days <= 0) {
            throw new InvalidBookingException(
                "Rental days must be greater than zero!");
        }

        if (!customers.contains(booking.customer)) {
            throw new InvalidBookingException(
                "Customer is not registered!");
        }

        if (!vehicles.contains(booking.vehicle)) {
            throw new InvalidBookingException(
                "Vehicle does not exist!");
        }

        if (!booking.vehicle.available) {
            throw new VehicleNotAvailableException(
                "Vehicle is not available for booking!");
        }

        bookings.add(booking);
        booking.vehicle.available = false;

        System.out.println("Vehicle booked successfully!");
    }

    // Return vehicle
    public void returnVehicle(int bookingId) {
        for (Booking b : bookings) {

            if (b.bookingId == bookingId) {

                if (b.vehicle.available) {
                    System.out.println("This car has already been returned!");
                    return;
                }

                Scanner sc = new Scanner(System.in);

                System.out.print("Enter extra days (0 if on time): ");
                int extraDays = sc.nextInt();

                if (extraDays < 0) {
                    System.out.println("Invalid number of days!");
                    return;
                }

                double lateFee = b.calculateLateFee(extraDays);
                double totalAmount = b.totalRent + lateFee;

                b.vehicle.available = true;

                System.out.println("\n===== RETURN DETAILS =====");
                System.out.println("Normal Rent: Rs." + b.totalRent);
                System.out.println("Late Fee: Rs." + lateFee);
                System.out.println("Total Amount: Rs." + totalAmount);
                System.out.println("Vehicle returned successfully!");

                return;
            }
        }

        System.out.println("Booking not found!");
    }


    // Search vehicle using Linear Search
    public void searchVehicle(int id) {

        boolean found = false;

        for (Vehicle v : vehicles) {

            if (v.id == id) {
                System.out.println("\nCar Found!");
                v.display();
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Car not found!");
        }
    }

}
