package SortingAlgorithm;
// Author: Nur Faid Prasetyo
// Referensi: https://www.geeksforgeeks.org/heap-sort/

/*
  Implementasi visualisasi algoritma Heap Sort menggunakan GUI.
  Algoritma Heap Sort adalah algoritma pengurutan yang menggunakan struktur data heap
  untuk mempertahankan sifat tertentu saat melakukan pengurutan.
  Proses pengurutan melibatkan pembentukan heap maksimum dari array dan mengurangi ukuran heap secara berulang
  untuk mendapatkan elemen terbesar dan memindahkannya ke akhir array.
  Kompleksitas waktu rata-rata dari Heap Sort adalah O(n log n), dengan n adalah jumlah elemen dalam array.
 */
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class HeapSort extends JFrame {

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

    public HeapSort() {
        setTitle("Heap Sort");
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
        new HeapSort();
    }

    private void startSorting() {
        indexAktif = -1;

        if (arr != null) {
            heapSort(); // Mengurutkan array menggunakan Heap Sort
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

    private void heapSort() {
        int n = arr.length;

        // Membangun heap maksimal
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(n, i);
        }

        // Mengurutkan array menggunakan heapify
        for (int i = n - 1; i > 0; i--) {
            // Menukar elemen teratas (maksimum) dengan elemen terakhir array
            swap(0, i);
            indexAktif = 0;
            draw(arr, indexAktif);
            printArray(arr);

            // Memanggil heapify pada subarray yang belum terurut
            heapify(i, 0);
        }
    }

    private void heapify(int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        // Mengecek apakah elemen anak kiri lebih besar dari elemen akar
        if (left < n && arr[left] > arr[largest])
            largest = left;

        // Mengecek apakah elemen anak kanan lebih besar dari elemen akar
        if (right < n && arr[right] > arr[largest])
            largest = right;

        // Jika akar bukanlah elemen terbesar
        if (largest != i) {
            // Menukar elemen akar dengan elemen terbesar
            swap(i, largest);
            indexAktif = i;
            draw(arr, indexAktif);
            printArray(arr);

            // Memanggil heapify rekursif pada sub-pohon yang terpengaruh
            heapify(n, largest);
        }
    }

    private void swap(int i, int j) {
        int temp = arr[i]; // Menyimpan nilai sementara dari elemen i
        arr[i] = arr[j]; // Menugaskan nilai elemen j ke elemen i
        arr[j] = temp; // Menugaskan nilai sementara ke elemen j
        System.out.println("Perubahan: Angka " + arr[i] + " dipindahkan dari index " + j + " ke index " + i);
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
