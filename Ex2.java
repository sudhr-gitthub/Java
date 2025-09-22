import java.util.ArrayList;
import java.util.Scanner;

class Ex2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> contacts = new ArrayList<>();
        int ch;

        while (true) {
            System.out.println("\n1.Add  2.View  3.Search  4.Delete  5.Modify  6.Exit");
            System.out.print("Enter choice: ");
            ch = scanner.nextInt();
            scanner.nextLine();

            if (ch == 1) {
                System.out.print("Enter name: ");
                String name = scanner.nextLine();
                System.out.print("Enter number: ");
                String number = scanner.nextLine();
                contacts.add(name + " - " + number);

            } else if (ch == 2) {
                if (contacts.isEmpty())
                    System.out.println("No contacts found.");
                else
                    System.out.println("Contacts: " + contacts);

            } else if (ch == 3) {
                System.out.print("Enter name or number to search: ");
                String search = scanner.nextLine();
                boolean found = false;
                for (String c : contacts) {
                    if (c.toLowerCase().contains(search.toLowerCase())) {
                        System.out.println("Found: " + c);
                        found = true;
                    }
                }
                if (!found) System.out.println("No match found.");

            } else if (ch == 4) {
                System.out.print("Enter exact contact to delete (Name - Number): ");
                String del = scanner.nextLine();
                if (contacts.remove(del))
                    System.out.println("Deleted successfully.");
                else
                    System.out.println("Contact not found.");

            } else if (ch == 5) {
                System.out.print("Enter exact contact to modify (Name - Number): ");
                String oldContact = scanner.nextLine();
                int index = contacts.indexOf(oldContact);
                if (index != -1) {
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter new number: ");
                    String newNumber = scanner.nextLine();
                    contacts.set(index, newName + " - " + newNumber);
                    System.out.println("Contact updated.");
                } else {
                    System.out.println("Contact not found.");
                }

            } else if (ch == 6) {
                break;
            }
        }
        scanner.close();
    }
}
