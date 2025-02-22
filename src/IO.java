import java.io.*;
import java.util.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class IO {
    public static List<Block> readFile(String filename, int[] ukuran, List<String> newPapan) throws IOException {
        List<Block> blocks = new ArrayList<>();
        Map<Character, Boolean> blockUsed = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine();

            // Validasi file kosong
            if (line == null) throw new IllegalArgumentException("File kosong.");

            // Baris pertama input (width, height, jumlah blocks)
            String[] params = line.split(" ");
            if (params.length != 3) throw new IllegalArgumentException("Parameter ukuran tidak lengkap!");

            int width = Integer.parseInt(params[0]);
            int height = Integer.parseInt(params[1]);
            int totalBlocks = Integer.parseInt(params[2]);

            // Validasi 0 atau negatif
            if (width <= 0 || height <= 0 || totalBlocks <= 0) {
                throw new IllegalArgumentException("Ukuran papan dan jumlah block harus lebih dari 0!");
            }

            ukuran[0] = width;
            ukuran[1] = height;

            // Baris kedua (jenis papan)
            String tipePapan = br.readLine();
            if (tipePapan == null) throw new IllegalArgumentException("Tidak memiliki jenis papan!");

            // Papan DEFAULT
            if ("DEFAULT".equals(tipePapan)) {
                // Mengisi papan dengan 'X'
                for (int i = 0; i < height; i++) {
                    newPapan.add("X".repeat(width));
                }

                // Membuat blocks baru
                List<String> currentBlock = new ArrayList<>();
                Character currentLabel = null;

                // Validasi baris kosong
                String currentLine;
                while ((currentLine = br.readLine()) != null) {
                    if (currentLine.trim().isEmpty()) {
                        throw new IllegalArgumentException("Terdapat baris block kosong!");
                    }

                    // Validasi block duplicate
                    char label = findLabel(currentLine);
                    // Terdapat block baru
                    if (currentLabel == null || label != currentLabel) {
                        // Block baru
                        if (currentLabel != null) {
                            blocks.add(new Block(currentLabel, currentBlock));
                            blockUsed.put(currentLabel, true);
                        }

                        // Block duplikat
                        if (blockUsed.getOrDefault(label, false)) {
                            throw new IllegalArgumentException("Terdapat duplikat block " + label + ".");
                        }

                        currentLabel = label;
                        currentBlock = new ArrayList<>();
                    }

                    currentBlock.add(currentLine);
                }

                // Masukan block terakhir
                if (currentLabel != null) {
                    blocks.add(new Block(currentLabel, currentBlock));
                }

                // Jumlah block tidak sesuai
                if (blocks.size() != totalBlocks) {
                    throw new IllegalArgumentException("Total block tidak sesuai!");
                }
                return blocks;

            // Papan CUSTOM
            } else if ("CUSTOM".equals(tipePapan)) {
                List<String> tempPapan = new ArrayList<>();
                boolean isPapanValid = true;

                // Membaca papan
                for (int i = 0; i < height; i++) {
                    String barisPapan = br.readLine();
                    if (barisPapan == null) {
                        throw new IllegalArgumentException("Papan kosong!");
                    }
                    tempPapan.add(barisPapan);
                }

                final int widthAwal = width;
                final int heightAwal = height;

                // Validasi papan NxM
                if (tempPapan.size() != heightAwal || tempPapan.stream().anyMatch(lined -> lined.length() != widthAwal || !lined.matches("[X.]+"))) {
                    isPapanValid = false;
                }

                // Jika papan NxM valid
                if (isPapanValid) {
                    newPapan.addAll(tempPapan);

                    // Proses blocks
                    List<String> currentBlock = new ArrayList<>();
                    Character currentLabel = null;

                    String currentLine;
                    while ((currentLine = br.readLine()) != null) {
                        if (currentLine.trim().isEmpty()) {
                            throw new IllegalArgumentException("Terdapat baris block kosong!");
                        }

                        char label = findLabel(currentLine);
                        if (currentLabel == null || label != currentLabel) {
                            // New block detected
                            if (currentLabel != null) {
                                blocks.add(new Block(currentLabel, currentBlock));
                                blockUsed.put(currentLabel, true);
                            }

                            if (blockUsed.getOrDefault(label, false)) {
                                throw new IllegalArgumentException("Terdapat duplikat block " + label + ".");
                            }

                            currentLabel = label;
                            currentBlock = new ArrayList<>();
                        }

                        currentBlock.add(currentLine);
                    }

                    if (currentLabel != null) {
                        blocks.add(new Block(currentLabel, currentBlock));
                    }

                    if (blocks.size() != totalBlocks) {
                        throw new IllegalArgumentException("Total block tidak sesuai!");
                    }
                    return blocks;
                }

                // Jika NxM gagal, coba MxN
                BufferedReader brn = new BufferedReader(new FileReader(filename));
                brn.readLine();
                brn.readLine();

                // Tukar ukuran
                int newWidth = heightAwal;
                int newHeight = widthAwal;
                tempPapan.clear();

                for (int i = 0; i < newHeight; i++) {
                    String barisPapan = brn.readLine();
                    if (barisPapan == null) {
                        throw new IllegalArgumentException("Papan kosong!");
                    }
                    tempPapan.add(barisPapan);
                }

                // Validasi papan MxN
                if (tempPapan.size() != newHeight || tempPapan.stream().anyMatch(lined -> lined.length() != newWidth || !lined.matches("[X.]+"))) {
                    throw new IllegalArgumentException("Ukuran papan salah!");
                }

                // Papan MxN benar
                newPapan.addAll(tempPapan);
                ukuran[0] = newWidth;
                ukuran[1] = newHeight;

                // Proses blocks
                List<String> currentBlock = new ArrayList<>();
                Character currentLabel = null;

                String currentLine;
                while ((currentLine = brn.readLine()) != null) {
                    if (currentLine.trim().isEmpty()) {
                        throw new IllegalArgumentException("Terdapat baris block kosong!");
                    }

                    char label = findLabel(currentLine);
                    if (currentLabel == null || label != currentLabel) {
                        // New block detected
                        if (currentLabel != null) {
                            blocks.add(new Block(currentLabel, currentBlock));
                            blockUsed.put(currentLabel, true);
                        }

                        if (blockUsed.getOrDefault(label, false)) {
                            throw new IllegalArgumentException("Terdapat duplikat block " + label + ".");
                        }

                        currentLabel = label;
                        currentBlock = new ArrayList<>();
                    }

                    currentBlock.add(currentLine);
                }

                if (currentLabel != null) {
                    blocks.add(new Block(currentLabel, currentBlock));
                }

                if (blocks.size() != totalBlocks) {
                    throw new IllegalArgumentException("Total block tidak sesuai!");
                }
                return blocks;
        } else {
                throw new IllegalArgumentException("Jenis papan harus berupa 'DEFAULT' atau 'CUSTOM'!");
            }
        }
    }

    private static char findLabel(String line) {
        for (char c : line.toCharArray()) {
            if (c != ' ') return c;
        }
        throw new IllegalArgumentException("Blocks tidak valid!");
    }

    public static char[][] createPapan(List<String> barisPapan) {
        int height = barisPapan.size();
        int width = barisPapan.get(0).length();
        char[][] papan = new char[height][width];

        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                papan[r][c] = barisPapan.get(r).charAt(c);
            }
        }
        return papan;
    }

    public static void savePapanToTxt(char[][] board, String filename) {
        File outputDir = new File("output");
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        File outputFile = new File(outputDir, filename);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (char[] row : board) {
                StringBuilder rowString = new StringBuilder();
                for (char cell : row) {
                    rowString.append(cell == '.' ? " " : cell);
                }
                writer.write(rowString.toString());
                writer.newLine();
            }
            System.out.println("Solusi tersimpan di: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Gagal menyimpan solusi: " + e.getMessage());
        }
    }


    public static void savePapanToImage(char[][] board, String filename) {
        int cellSize = 50;

        int width = board[0].length * cellSize;
        int height = board.length * cellSize;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        // Menggambar papan
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                char cell = board[i][j];
                Color color = getColorForBlock(cell);

                g2d.setColor(color);
                g2d.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
            }
        }

        g2d.dispose();

        File outputDir = new File("output");
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        File outputFile = new File(outputDir, filename);

        try {
            ImageIO.write(image, "PNG", outputFile);
            System.out.println("Gambar solusi tersimpan di: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Gagal menyimpan gambar: " + e.getMessage());
        }
    }

    private static Color getColorForBlock(char block) {
        int index = (Character.toUpperCase(block) - 'A') % 26;

        // Mapping warna
        switch (index) {
            case 0: return Color.RED;
            case 1: return Color.GREEN;
            case 2: return Color.YELLOW;
            case 3: return Color.BLUE;
            case 4: return Color.MAGENTA;
            case 5: return Color.CYAN;
            case 6: return Color.WHITE;
            case 7: return Color.DARK_GRAY;
            case 8: return Color.PINK;
            case 9: return Color.GREEN.darker();
            case 10: return Color.YELLOW.darker();
            case 11: return Color.BLUE.darker();
            case 12: return Color.MAGENTA.darker();
            case 13: return Color.CYAN.darker();
            case 14: return Color.WHITE;
            case 15: return new Color(255, 0, 0);
            case 16: return new Color(0, 255, 0);
            case 17: return new Color(255, 255, 0);
            case 18: return new Color(0, 0, 255);
            case 19: return new Color(255, 0, 255);
            case 20: return new Color(0, 255, 255);
            case 21: return new Color(255, 255, 255);
            case 22: return new Color(255, 140, 0);
            case 23: return new Color(255, 255, 102);
            case 24: return new Color(135, 206, 235);
            case 25: return new Color(144, 238, 144);
            default: return Color.BLACK;
        }
    }
}
