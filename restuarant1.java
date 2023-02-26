import java.util.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.*;

class assignpent3 {
    Scanner sc = new Scanner(System.in);
    ArrayList<ArrayList<String>> a1 = new ArrayList<ArrayList<String>>();
    ArrayList<ArrayList<String>> a2 = new ArrayList<ArrayList<String>>();
    ArrayList<Integer> a3 = new ArrayList<>();
    ArrayList<Integer> a4 = new ArrayList<>();

    void readfile() {
        try {
            Scanner sc1 = new Scanner(new FileReader("menu.csv"));
            Scanner sc2 = new Scanner(new FileReader("billing.csv"));
            String s;
            while (sc1.hasNext()) {
                s = sc1.nextLine();
                String[] str = s.split(",");
                List<String> deal = Arrays.asList(str);
                ArrayList<String> a11 = new ArrayList<>(deal);

                a1.add(a11);
            }
            while (sc2.hasNext()) {
                String ss = sc2.nextLine();
                String[] u = ss.split(",");
                List<String> deal1 = Arrays.asList(u);
                ArrayList<String> a21 = new ArrayList<>(deal1);
                a2.add(a21);

            }
        } catch (Exception e) {
            System.out.println("Running Error");
        }
    }

    void display(ArrayList<ArrayList<String>> a1) {
        int c = a1.size();
        for (int i = 0; i < c; i++) {
            ArrayList<String> bil = a1.get(i);
            for (String string : bil) {
                System.out.print(string + " ");
            }
            System.out.println();
        }
    }

    void menu() {
        String arr[] = { "generatebill new bill", "View the total collection for today", "Cancel the bill" };
        int k = arr.length;
        for (int i = 0; i < k; i++) {
            System.out.print(i + 1 + "-" + arr[i] + "\n");
        }
        System.out.print("Enter Your Choice: ");
    }

    void generatebill() {
        System.out.println("generatebill new bill");
        display(a1);
        orderthemenu();
    }

    void collection() {
        sc.nextLine();
        System.out.println("View the total collection for today");
        System.out.print("Enter date:: ");
        String s = sc.nextLine();
        Double collection = 0.0;
        for (int i = 0; i < (a2.size()); i++) {
            ArrayList<String> sc1 = a2.get(i);
            if ((sc1.get(1)).equals(s)) {
                Double b = Double.parseDouble(sc1.get(2));
                collection += b;
                System.out.println(sc1 + "\n");
            }
        }
        System.out.println("Total collection of day: " + collection);

    }

    void cancelbill() {
        System.out.println("Cancel the bill:: ");
        display(a2);
        System.out.print("Enter the id in Above list:: ");
        int k = sc.nextInt();
        int t = a2.size();
        if (k > t) {
            System.out.println("Enter Valid Id");
        } else {
            k = k - 1;
            (a2.get(k)).set(4, "cancelled");
            try {
                FileWriter file = new FileWriter("billing.csv", false);
                for (int i = 0; i < (a2.size()); i++) {
                    ArrayList<String> u = a2.get(i);
                    String a2 = String.join(",", u);
                    a2 += "\n";
                    FileWriter file1 = new FileWriter("billing.csv", true);
                    file1.write(a2);
                    file1.close();
                }
                file.close();
                display(a2);
                System.out.println("Cancelled orderthemenu");
            } catch (Exception e) {

                System.out.println("Error");
            }
        }

    }

    void detail() {
        int k = sc.nextInt();
        if (k == 1)
            generatebill();
        else if (k == 2)
            collection();
        else if (k == 3)
            cancelbill();
        else
            System.out.println("Enter Valid number");

    }

    void orderthemenu() {
        System.out.print("Enter orderthemenu Id: ");
        int k = sc.nextInt();
        System.out.print("Enter Quantity: ");
        int p = sc.nextInt();
        a3.add(k);
        a4.add(p);
        System.out.print("If you to orderthe menu again Yes->y or No->n: ");
        char ch = sc.next().charAt(0);
        if (ch == 'y') {
            orderthemenu();
        } else {
            int orderthemenucount = a3.size();
            Double total = 0.0;
            System.out.print("check your detail:: ");
            for (int i = 0; i < orderthemenucount; i++) {
                int c = a3.get(i);
                int l = a4.get(i);
                ArrayList<String> bill = a1.get(c - 1);
                Double u = Double.parseDouble(bill.get(2));
                total += u * l;
                System.out.println(a3.get(i) + " ");
            }
            System.out.println("total:  " + total);
            System.out.print("Do you want to confirm orderthemenu Yes-Y or No-k: ");
            char ch1 = sc.next().charAt(0);
            if (ch1 == 'y') {
                String s = ",";
                LocalDate date1 = LocalDate.now();
                DateTimeFormatter time = DateTimeFormatter.ofPattern("d-MMM-yy");
                String date = date1.format(time);
                int x = a2.size() + 1;
                String u = x + s + date + s + total + .00 + s;
                for (int i = 0; i < (a3.size()); i++) {
                    u += a3.get(i) + " ";
                    u += a4.get(i) + " ";
                }
                u += s + "Approved";
                try {
                    File newfile = new File("billing.csv");
                    FileWriter filewrite = new FileWriter(newfile, true);
                    filewrite.write("\n" + u);
                    filewrite.close();
                } catch (Exception e) {

                    System.out.println("error");
                }

                System.out.println("Thank You Visit Again");
            } else {
                menu();
                detail();
            }

        }
    }

}

public class restuarant1 {
    public static void main(String[] args) {
        assignpent3 obj = new assignpent3();
        obj.readfile();
        obj.menu();
        obj.detail();

    }
}