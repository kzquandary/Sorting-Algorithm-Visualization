package SortingAlgorithm;
// Author: Nur Faid Prasetyo
// Referensi: https://www.geeksforgeeks.org/counting-sort/

/*
  Implementasi visualisasi algoritma Counting Sort menggunakan GUI.
  Algoritma Counting Sort adalah algoritma pengurutan yang bekerja dengan menghitung jumlah kemunculan
  setiap elemen dalam array dan membangun array baru berdasarkan jumlah kemunculan tersebut.
  Proses ini memerlukan pengetahuan tentang rentang nilai elemen dalam array.
  Kompleksitas waktu rata-rata dari Counting Sort adalah O(n+k), dengan n adalah jumlah elemen dalam array
  dan k adalah rentang nilai elemen.
 */
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class CountingSort extends JFrame {

    private static final int TINGGI_WINDOW = 800;
    private static final int LEBAR_WINDOW = 600;
    private static final int LEBAR_KOTAK = 15;
    private static final int FAKTOR_TINGGI_BATANG = 10;
    private static final int DELAY_MILISECOND = 50;

    private final JPanel panel;
    private final JTextField inputField;
    private int[] arr;
    private int indexAktif;
    private int[] arraySebelumnya;

    public CountingSort() {
        setTitle("Counting Sort");
        setSize(TINGGI_WINDOW, LEBAR_WINDOW);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setBackground(Color.WHITE);
        getContentPane().add(panel, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputField = new JTextField(20);
        inputPanel.add(inputField);

        inputField.setToolTipText("Masukkan Angka (Spasi untuk Membatasi Angka)");

        JButton sortButton = new JButton("Sort");
        sortButton.addActionListener(e -> {
            generateRandomArray();
            draw(arr, -1);
            startSorting();
        });
        inputPanel.add(sortButton);

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            resetArray();
            draw(arr, -1);
        });
        inputPanel.add(resetButton);

        getContentPane().add(inputPanel, BorderLayout.SOUTH);

        setVisible(true);
        panel.requestFocusInWindow();
    }

    public static void main(String[] args) {
        new CountingSort();
    }

    private void startSorting() {
        indexAktif = -1;

        if (arr != null) {
            countingSort(); // Mengurutkan elemen menggunakan Counting Sort
        }

        assert arr != null;
        draw(arr, -1);

        if (isSorted(arr)) {
            printArray(arr);
            System.out.println("Array telah diurutkan.");
        } else {
            System.out.println("Array tidak terurut.");
        }
    }

    private boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) {
                return false; // Jika ada elemen yang lebih kecil dari elemen sebelumnya, array tidak terurut
            }
        }
        return true; // Jika tidak ada elemen yang lebih kecil dari elemen sebelumnya, array terurut
    }

    private void countingSort() {
        int max = Arrays.stream(arr)
                .max()
                .orElse(0); // Jika tidak ada elemen dalam array, nilai maksimum diatur menjadi 0

        int[] count = new int[max + 1]; // Membuat array count dengan ukuran (maksimum + 1)

        for (int num : arr) {
            count[num]++; // Menghitung jumlah kemunculan setiap elemen dalam array
        }

        int outputIndex = 0;
        for (int i = 0; i < count.length; i++) {
            while (count[i] > 0) {
                arr[outputIndex++] = i; // Menempatkan elemen ke array sesuai dengan jumlah kemunculannya
                count[i]--;
                indexAktif = outputIndex - 1;
                draw(arr, indexAktif);
                printArray(arr);
                System.out.println("Perpindahan: Angka " + i + " dipindahkan dari indeks " + outputIndex + " ke indeks " + (outputIndex - 1));
            }
        }
    }

    private void generateRandomArray() {
        String input = inputField.getText();
        if (input.isEmpty()) {
            Random rand = new Random();
            int size = panel.getWidth() / LEBAR_KOTAK;
            arr = new int[size];
            for (int i = 0; i < size; i++) {
                arr[i] = rand.nextInt(panel.getHeight() / FAKTOR_TINGGI_BATANG) + 1;
            }
        } else {
            String[] numbers = input.split(" ");
            arr = new int[numbers.length];
            for (int i = 0; i < numbers.length; i++) {
                arr[i] = Integer.parseInt(numbers[i]);
            }
        }

        System.out.println("Array:");
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();

        arraySebelumnya = null; // Reset state array sebelumnya
    }

    private void printArray(int[] arr) {
        if (!Arrays.equals(arraySebelumnya, arr)) { // Membandingkan dengan Array Sebelumnya
            System.out.println("Array Sekarang:");
            for (int i = 0; i < arr.length; i++) {
                System.out.print(arr[i] + "[" + i + "] ");
            }
            System.out.println();

            arraySebelumnya = arr.clone(); // Menyimpan array sekarang kedalam array sebelumnya
        }
    }

    private void draw(int[] arr, int currentIndex) {
        Graphics g = panel.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, panel.getWidth(), panel.getHeight());
        g.setColor(Color.BLUE);
        for (int i = 0; i < arr.length; i++) {
            int x = i * LEBAR_KOTAK;
            int y = panel.getHeight() - (arr[i] * FAKTOR_TINGGI_BATANG);
            int tinggi = arr[i] * FAKTOR_TINGGI_BATANG;
            if (i == currentIndex) {
                g.setColor(Color.GREEN);
            }
            g.fillRect(x, y, LEBAR_KOTAK, tinggi);
            g.setColor(Color.BLUE);
        }
        try {
            Thread.sleep(DELAY_MILISECOND);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void resetArray() {
        Arrays.fill(arr, 0); // Mengisi semua elemen array dengan nilai 0
        inputField.setText("");
        panel.repaint();
    }
}
