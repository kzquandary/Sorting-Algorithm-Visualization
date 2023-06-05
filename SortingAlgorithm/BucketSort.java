package SortingAlgorithm;
// Author: Nur Faid Prasetyo
// Referensi: https://www.geeksforgeeks.org/bucket-sort-2/

/*
  Implementasi visualisasi algoritma Bucket Sort menggunakan GUI.
  Algoritma Bucket Sort adalah algoritma pengurutan yang mempartisi array menjadi beberapa "ember" atau "bucket"
  berdasarkan rentang nilai tertentu.
  Setiap elemen array ditempatkan ke dalam bucket yang sesuai berdasarkan nilai elemen tersebut.
  Kemudian, setiap bucket diurutkan secara terpisah, dan elemen-elemen dari semua bucket digabungkan menjadi satu array terurut.
  Kompleksitas waktu rata-rata dari Bucket Sort adalah O(n+k), dengan n adalah jumlah elemen dalam array
  dan k adalah jumlah bucket yang digunakan.
 */
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BucketSort extends JFrame {

    private static final int TINGGI_WINDOW = 800;
    private static final int LEBAR_WINDOW = 600;
    private static final int LEBAR_KOTAK = 15;
    private static final int FAKTOR_TINGGI_BATANG = 10;
    private static final int DELAY_MILISECOND = 50;

    private final JPanel panel;
    private final JTextField inputField;
    private List<Integer>[] buckets;
    private int[] arr;
    private int indexAktif;
    private int[] arraySebelumnya;

    public BucketSort() {
        setTitle("Bucket Sort");
        setSize(TINGGI_WINDOW, LEBAR_WINDOW);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setBackground(Color.WHITE);
        getContentPane().add(panel, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputField = new JTextField(20);
        inputPanel.add(inputField);

        inputField.setToolTipText("Masukkan Angka (Spasi Untuk Membatasi Angka)");

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
        new BucketSort();
    }

    private void startSorting() {
        indexAktif = -1;

        if (arr != null) {
            bucketSort();
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
                return false;
            }
        }
        return true;
    }

    private void bucketSort() {
        int n = arr.length;
        int maxVal = getMaxValue();

        List<Integer>[] buckets = new List[n];
        for (int i = 0; i < n; i++) {
            buckets[i] = new ArrayList<>();
        }

        for (int j : arr) {
            int index = (j * n) / (maxVal + 1);
            buckets[index].add(j);
        }

        for (List<Integer> bucket : buckets) {
            Collections.sort(bucket);
        }

        int index = 0;
        for (List<Integer> bucket : buckets) {
            for (int value : bucket) {
                arr[index] = value;
                indexAktif = index;
                draw(arr, indexAktif);
                printArray(arr);
                index++;
            }
        }
    }

    private int getMaxValue() {
        int max = Integer.MIN_VALUE;
        for (int value : arr) {
            if (value > max) {
                max = value;
            }
        }
        return max;
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

        arraySebelumnya = null;
    }

    private void printArray(int[] arr) {
        if (!Arrays.equals(arraySebelumnya, arr)) {
            System.out.println("Array Sekarang:");
            for (int i = 0; i < arr.length; i++) {
                System.out.print(arr[i] + "[" + i + "] ");
            }
            System.out.println();

            arraySebelumnya = arr.clone();
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
        Arrays.fill(arr, 0);
        inputField.setText("");
        panel.repaint();
    }
}
