package SortingAlgorithm;
// Author: Nur Faid Prasetyo
// Referensi: https://www.geeksforgeeks.org/merge-sort/

/*
  Implementasi visualisasi algoritma Merge Sort menggunakan GUI.
  Algoritma Merge Sort adalah algoritma pengurutan dengan metode pemisahan dan penggabungan (divide and conquer).
  Prinsip kerjanya adalah dengan membagi array menjadi dua bagian secara rekursif, kemudian menggabungkan
  kembali kedua bagian tersebut dengan cara membandingkan dan menggabungkan elemen-elemen secara terurut.
  Proses ini diulang hingga seluruh array terurut.
  Kompleksitas waktu rata-rata dari Merge Sort adalah O(n log n), dengan n adalah jumlah elemen dalam array.
 */
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class MergeSort extends JFrame {

    private static final int TINGGI_WINDOW = 800;
    private static final int LEBAR_WINDOW = 600;
    private static final int LEBAR_KOTAK = 15;
    private static final int FAKTOR_TINGGI_BATANG = 10;
    private static final int DELAY_MILISECOND = 50;

    private final JPanel panel;
    private final JTextField inputField;
    private int[] arr;
    private int[] arraySebelumnya;

    public MergeSort() {
        setTitle("Merge Sort");
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
        new MergeSort();
    }

    private void startSorting() {
        if (arr != null) {
            mergeSort(arr, 0, arr.length - 1);
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

    private void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;

            mergeSort(arr, left, mid); // Mengurutkan setengah bagian kiri array
            mergeSort(arr, mid + 1, right); // Mengurutkan setengah bagian kanan array

            merge(arr, left, mid, right); // Menggabungkan kedua bagian array yang telah diurutkan
        }
    }

    private void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];

        System.arraycopy(arr, left, L, 0, n1);
        for (int j = 0; j < n2; ++j)
            R[j] = arr[mid + 1 + j];

        int i = 0, j = 0;
        int k = left;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
            draw(arr, k - 1);
            printArray(arr);
        }

        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
            draw(arr, k - 1);
            printArray(arr);
            System.out.println("Perubahan: Angka " + arr[k-1] + " dipindahkan dari index " + (k-1) + " ke index " + (k-1));
        }

        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
            draw(arr, k - 1);
            printArray(arr);
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
