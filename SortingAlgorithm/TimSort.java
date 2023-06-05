package SortingAlgorithm;
// Author: Nur Faid Prasetyo
// Referensi: https://www.geeksforgeeks.org/timsort/

/*
  Implementasi visualisasi algoritma Tim Sort menggunakan GUI.
  Algoritma Tim Sort adalah algoritma pengurutan yang menggabungkan algoritma Insertion Sort dan Merge Sort.
  Algoritma ini membagi array menjadi beberapa blok atau run yang kemudian diurutkan menggunakan Insertion Sort.
  Selanjutnya, blok-blok tersebut digabungkan menggunakan Merge Sort untuk menghasilkan array yang terurut.
  Kompleksitas waktu rata-rata dari Tim Sort adalah O(n log n), dengan n adalah jumlah elemen dalam array.
 */
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class TimSort extends JFrame {

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

    public TimSort() {
        setTitle("Tim Sort");
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
        new TimSort();
    }

    private void startSorting() {
        indexAktif = -1;

        if (arr != null) {
            timSort(0, arr.length - 1);
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

    private void timSort(int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            timSort(left, mid);
            timSort(mid + 1, right);
            merge(left, mid, right);
        }
    }

    private void merge(int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] leftArr = new int[n1];
        int[] rightArr = new int[n2];

        System.arraycopy(arr, left, leftArr, 0, n1);
        for (int j = 0; j < n2; ++j) {
            rightArr[j] = arr[mid + 1 + j];
        }

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (leftArr[i] <= rightArr[j]) {
                arr[k] = leftArr[i];
                i++;
            } else {
                arr[k] = rightArr[j];
                j++;
            }
            indexAktif = k;
            draw(arr, indexAktif);
            printArray(arr);
            k++;
        }

        while (i < n1) {
            arr[k] = leftArr[i];
            indexAktif = k;
            draw(arr, indexAktif);
            printArray(arr);
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = rightArr[j];
            indexAktif = k;
            draw(arr, indexAktif);
            printArray(arr);
            j++;
            k++;
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
