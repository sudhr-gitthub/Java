
import java.util.*;

class SalEx extends Exception {
    public SalEx(String msg) { super(msg); }
}

class E {
    String n; int i; double b, a, d, s;

    public E(String n, int i, double b, double a, double d) throws SalEx {
        if (b < 0 || a < 0 || d < 0) throw new SalEx("Salary components must not be negative.");
        this.n = n; this.i = i; this.b = b; this.a = a; this.d = d;
        this.s = b + a - d;
        if (s < 0) throw new SalEx("Net salary cannot be negative.");
    }

    @Override
    public String toString() {
        return "ID: " + i + ", " + n + ", ₹" + s;
    }
}

interface St<T> {
    void push(T item);
    T pop();
    boolean isEmpty();
    int size();
}

class AS<T> implements St<T>, Iterable<T> {
    private T[] arr; private int t = -1, c;

    @SuppressWarnings("unchecked")
    public AS(int size) { c = size; arr = (T[]) new Object[size]; }

    public void push(T item) { if (t < c - 1) arr[++t] = item; }
    public T pop() { return t >= 0 ? arr[t--] : null; }
    public boolean isEmpty() { return t == -1; }
    public int size() { return t + 1; }

    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int idx = t;
            public boolean hasNext() { return idx >= 0; }
            public T next() { return arr[idx--]; }
        };
    }
}

public class Payroll {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        St<E> h = new AS<>(100); 
        
        while (true) {
            System.out.println("\n1. Add\n2. Undo\n3. View\n4. Exit");
            System.out.print("Choice: ");
            int ch = Integer.parseInt(sc.nextLine());

            switch (ch) {
                case 1:
                    try {
                        System.out.print("Enter ID, Name, Basic, Allowance, Deduction: ");
                        String[] input = sc.nextLine().split(",");
                        int id = Integer.parseInt(input[0].trim());
                        String name = input[1].trim();
                        double basic = Double.parseDouble(input[2].trim());
                        double allowance = Double.parseDouble(input[3].trim());
                        double deduction = Double.parseDouble(input[4].trim());

                        E emp = new E(name, id, basic, allowance, deduction);
                        h.push(emp);
                        System.out.println("Added: " + emp);
                    } catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
                    break;
                
                case 2:
                    if (!h.isEmpty()) System.out.println("Removed: " + h.pop());
                    else System.out.println("No entries to undo.");
                    break;

                case 3:
                    if (h.isEmpty()) {
                        System.out.println("No entries.");
                    } else {
                        System.out.println("Payroll:");
                        AS<E> stack = (AS<E>) h;
                        Iterator<E> it = stack.iterator();
                        while (it.hasNext()) {
                            System.out.println(it.next());
                        }
                    }
                    break;

                case 4:
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}

