import java.util.List;

public class Solver {
    private char[][] board;
    private List<Block> blocks;
    private long jumlahKasus = 0;

    public Solver(char[][] board, List<Block> blocks) {
        this.board = board;
        this.blocks = blocks;
    }

    public boolean solve() {
        long start = System.currentTimeMillis(); // Start waktu
        transformBoard();
        // Algoritma Brute Force start dari index 0
        if (bruteForce(0) && isBoardFull()) {
            System.out.println("Solusi ditemukan:");
            printBoard();
            long end = System.currentTimeMillis();
            long totalTime = end - start;

            System.out.println("Waktu pencarian: " + totalTime + " ms");
            System.out.println("Banyak kasus yang ditinjau: " + jumlahKasus);
            return true;
        } else {
            System.out.println("Tidak ada solusi.");
            return false;
        }
    }

    private boolean bruteForce(int index) {

        // Semua block digunakan
        if (index == blocks.size()) {
            return true;
        }

        Block block = blocks.get(index); // Mendapatkan block sekarang
        List<char[][]> transformations = block.getVariants(); // Mengambil seluruh variasi block

        // Mencoba meletakkan block pada setiap baris dan kolom yang mungkin
        // Jika tidak bisa, maka lakukan transformasi pada block dan coba kembali
        for (char[][] shape : transformations) {
            for (int r = 0; r <= board.length - shape.length; r++) {
                for (int c = 0; c <= board[0].length - shape[0].length; c++) {
                    jumlahKasus++;

                    // Jika block dapat diletakkan di papan
                    if (validPlace(shape, r, c)) {
                        placeBlock(shape, r, c, block.getLabel());
                        // Jika block selanjutnya tidak dapat diletakkan, maka
                        // hilangkan block sekarang dan ulang proses
                        if (bruteForce(index + 1)) return true;

                        removeBlock(shape, r, c);
                    }
                }
            }
        }

        return false;
    }

    private boolean validPlace(char[][] shape, int row, int col) {
        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[0].length; c++) {
                if (shape[r][c] != ' ') {
                    // Block hanya dapat diletakkan pada '1'
                    if (board[row + r][col + c] != '1') {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void placeBlock(char[][] shape, int row, int col, char label) {
        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[0].length; c++) {
                // Mengganti papan dengan label
                if (shape[r][c] != ' ') {
                    board[row + r][col + c] = label;
                }
            }
        }
    }

    private void removeBlock(char[][] shape, int row, int col) {
        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[0].length; c++) {
                // Mengganti papan dengan '1'
                if (shape[r][c] != ' ') {
                    board[row + r][col + c] = '1';
                }
            }
        }
    }

    // Print jawaban
    private void printBoard() {
        String[] colors = {
                "\u001B[31m",
                "\u001B[32m",
                "\u001B[33m",
                "\u001B[34m",
                "\u001B[35m",
                "\u001B[36m",
                "\u001B[37m",
                "\u001B[90m",
                "\u001B[91m",
                "\u001B[92m",
                "\u001B[93m",
                "\u001B[94m",
                "\u001B[95m",
                "\u001B[96m",
                "\u001B[97m",
                "\u001B[101m",
                "\u001B[102m",
                "\u001B[103m",
                "\u001B[104m",
                "\u001B[105m",
                "\u001B[106m",
                "\u001B[107m",
                "\u001B[38;5;208m",
                "\u001B[38;5;226m",
                "\u001B[38;5;51m",
                "\u001B[38;5;50m",
        };

        for (char[] row : board) {
            for (char cell : row) {
                if (cell == '0') {
                    System.out.print(" ");
                } else {
                    int colorIndex = (Character.toUpperCase(cell) - 'A') % 26;
                    System.out.print(colors[colorIndex] + cell + "\u001B[0m");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    // Mengubah 'X' menjadi '1' dan '.' menjadi '0'
    private void transformBoard() {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (board[r][c] == 'X') {
                    board[r][c] = '1';
                } else {
                    board[r][c] = '0';
                }
            }
        }
    }

    // Cek apakah papan penuh
    private boolean isBoardFull() {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (board[r][c] == '1') {
                    return false;
                }
            }
        }
        return true;
    }
}