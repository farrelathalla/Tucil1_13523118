import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Masukan nama file input
        System.out.print("Masukan nama file input (e.g., input.txt): ");
        String filename = scanner.nextLine().trim();

        filename = "test/" + filename;

        int[] ukuran = new int[2];  // Width dan height
        List<String> newPapan = new ArrayList<>();

        try {
            // Validasi nama file input
            if (!new File(filename).exists()) {
                throw new FileNotFoundException("File " + filename + " tidak ditemukan.");
            }

            // Membaca input dari file
            List<Block> blocks = IO.readFile(filename, ukuran, newPapan);
            char[][] papan = IO.createPapan(newPapan);

            // Solver puzzle
            Solver solver = new Solver(papan, blocks);
            boolean solved = solver.solve();

            if (solved) {
                while (true) {
                    // Menyimpan hasil di .txt
                    System.out.print("Apakah anda ingin menyimpan jawab dalam .txt? (y/n): ");
                    String saveTxt = scanner.nextLine().trim().toLowerCase();
    
                    if (saveTxt.equals("y")) {
                        String txtFilename;
                        while (true) {
                            System.out.print("Masukan nama file .txt (e.g., output.txt): ");
                            txtFilename = scanner.nextLine().trim();
    
                            // Validasi nama output dari txt
                            if (txtFilename.matches("^[a-zA-Z0-9_\\-]+\\.txt$")) {
                                break;
                            } else {
                                System.out.println("Nama file harus berakhir dengan .txt, ulangi kembali!");
                            }
                        }
                        IO.savePapanToTxt(papan, txtFilename);
                        break;
                    } else if (saveTxt.equals("n")) {
                        break;
                    } else {
                        System.out.println("Input salah!");
                    }
                }
    
                while (true) {
                    // Menyimpan hasil di .png / .jpg
                    System.out.print("Apakah anda ingin menyimpan jawab dalam gambar? (y/n): ");
                    String saveImage = scanner.nextLine().trim().toLowerCase();
    
                    if (saveImage.equals("y")) {
                        String imageFilename;
                        while (true) {
                            System.out.print("Masukan nama file gambar (e.g., output.png, output.jpg): ");
                            imageFilename = scanner.nextLine().trim();
    
                            // Validasi nama file gambar
                            if (imageFilename.matches("^[a-zA-Z0-9_\\-]+\\.(png|jpg)$")) {
                                break;
                            } else {
                                System.out.println("Nama file harus berakhir dengan .png atau .jpg, ulangi kembali!");
                            }
                        }
    
                        IO.savePapanToImage(papan, imageFilename);
                        break;
                    } else if (saveImage.equals("n")) {
                        break;
                    } else {
                        System.out.println("Input salah!");
                    }   
                }
            }
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Terjadi kesalahan: " + e.getMessage());
        }
    }
}
