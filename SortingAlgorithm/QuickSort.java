package SortingAlgorithm;
// Author: Nur Faid Prasetyo
// Referensi: https://www.geeksforgeeks.org/quick-sort/

/*
  Implementasi visualisasi algoritma Quick Sort menggunakan GUI.
  Algoritma Quick Sort adalah algoritma pengurutan dengan metode pemisahan dan penakjuban (divide and conquer).
  Prinsip kerjanya adalah dengan memilih elemen pivot dari array yang akan diurutkan,
  kemudian mempartisi array menjadi dua bagian: elemen-elemen yang lebih kecil daripada pivot
  dan elemen-elemen yang lebih besar daripada pivot.
  Proses ini diulang pada kedua bagian secara rekursif hingga seluruh array terurut.
  Kompleksitas waktu rata-rata dari Quick Sort adalah O(n log n), dengan n adalah jumlah elemen dalam array.
 */

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class QuickSort extends JFrame {

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

    public QuickSort() {
        setTitle("Quicksort");
        setSize(TINGGI_WINDOW, LEBAR_WINDOW);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setBackground(Color.WHITE);
        getContentPane().add(panel, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputField = new JTextField(20);
        inputPanel.add(inputField);

        inputField.setToolTipText("Masukan Angka (Spasi Untuk Membatasi Angka)");


        JButton randomButton = new JButton("Sort");
        randomButton.addActionListener(e -> {
            generateRandomArray();
            draw(arr, -1);
            startSorting();
        });
        inputPanel.add(randomButton);

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
        new QuickSort();
    }

    private void startSorting() {
        indexAktif = -1;

        if (arr != null) {
            int lastIndex = arr.length - 1;
            while (lastIndex >= 0 && arr[lastIndex] == 0) {
                lastIndex--; // Mencari indeks terakhir elemen yang bukan 0
            }
            if (lastIndex >= 0) {
                sort(0, lastIndex); // Hanya mengurutkan elemen yang valid
            }
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
        if (!Arrays.equals(arraySebelumnya, arr)) { //Membandingkan dengan Array Sebelumnya
            System.out.println("Array Sekarang:");
            for (int i = 0; i < arr.length; i++) {
                System.out.print(arr[i] + "[" + i + "] ");
            }
            System.out.println();

            arraySebelumnya = arr.clone(); // Menyimpan array sekarang kedalam array sebelumnya
        }
    }



    private void sort(int low, int high) {
        if (low < high) {
            int pivotIndex = partition(low, high); // Memilih pivot dan mendapatkan indeks pivot
            sort(low, pivotIndex - 1); // Mengurutkan bagian kiri dari pivot
            sort(pivotIndex + 1, high); // Mengurutkan bagian kanan dari pivot
        }
    }

    private int partition(int low, int high) {
        int pivot = arr[high]; // Memilih elemen pivot
        int i = low - 1; // Indeks untuk elemen yang lebih kecil dari pivot
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) { // Jika elemen saat ini lebih kecil dari pivot
                i++;
                swap(i, j); // Menukar elemen saat ini dengan elemen di sebelahnya yang lebih kecil
                indexAktif = i; // Mengatur indeks aktif saat ini untuk tujuan visualisasi
                draw(arr, indexAktif); // Menggambar array setelah pertukaran elemen
                printArray(arr); // Print array setelah melakukan swap element
            }
        }
        swap(i + 1, high); // Menukar elemen pivot dengan elemen yang tepat setelahnya
        indexAktif = i + 1; // Mengatur indeks aktif saat ini untuk tujuan visualisasi
        draw(arr, indexAktif); // Menggambar array setelah pertukaran pivot
        printArray(arr); // Print array after swapping pivot
        return i + 1; // Mengembalikan indeks pivot yang baru
    }

    private void swap(int i, int j) {
        int temp = arr[i]; // Menyimpan nilai sementara dari elemen i
        arr[i] = arr[j]; // Menugaskan nilai elemen j ke elemen i
        arr[j] = temp; // Menugaskan nilai sementara ke elemen j
        System.out.println("Perubahan: Angka " + arr[i] + " dipindahkan dari index " + j + " ke index " + i);
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
