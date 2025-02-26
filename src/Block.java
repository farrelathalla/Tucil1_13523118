import java.util.*;

public class Block {
    private char label;
    private char[][] shape; // Bentuk block dalam array
    private List<char[][]> variants; // Seluruh hasil transformasi pada block

    public Block(char label, List<String> inputShape) {
        this.label = label;
        this.shape = shapeToArray(inputShape);

        // Block tidak terhubung
        if (!isConnected()) {
            throw new IllegalArgumentException("Block " + label + " tidak terhubung!");
        }

        // Generate semua transformasi
        generateVariants();
    }

    // Fungsi getter
    public char getLabel() {
        return label;
    }

    public char[][] getShape() {
        return shape;
    }

    public List<char[][]> getVariants() {
        return variants;
    }

    // Mengubah input ke array 2 dimensi
    private char[][] shapeToArray(List<String> inputShape) {
        int rows = inputShape.size();
        int cols = 0;

        // Mencari row terbesar sebagai patokan ukuran
        for (String row : inputShape) {
            cols = Math.max(cols, row.length());
        }

        // Isi spasi dengan ' '
        char[][] result = new char[rows][cols];
        for (int r = 0; r < rows; r++) {
            String row = inputShape.get(r);
            for (int c = 0; c < cols; c++) {
                result[r][c] = (c < row.length()) ? row.charAt(c) : ' ';
            }
        }
        return result;
    }

    private boolean isConnected() {
        int rows = shape.length;
        int cols = shape[0].length;
        boolean[][] visited = new boolean[rows][cols];

        // Cari kemunculan pertama label
        int startX = -1, startY = -1;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (shape[r][c] == label) {
                    startX = r;
                    startY = c;
                    break;
                }
            }
            if (startX != -1) break;
        }

        if (startX == -1) return false; // Blok tidak valid

        // Algoritma BFS
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startX, startY});
        visited[startX][startY] = true;

        int count = 0, total = 0;
        for (char[] row : shape) {
            for (char cell : row) {
                if (cell == label) total++;
            }
        }

        // Cek nyambung
        int[][] directions = {
                {1, 0}, {-1, 0}, {0, 1}, {0, -1}, // Atas, bawah, kiri, kanan
                {1, 1}, {1, -1}, {-1, 1}, {-1, -1} // Diagonal
        };

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            count++;

            for (int[] dir : directions) {
                int newX = curr[0] + dir[0];
                int newY = curr[1] + dir[1];

                if (newX >= 0 && newX < rows && newY >= 0 && newY < cols &&
                        !visited[newX][newY] && shape[newX][newY] == label) {
                    visited[newX][newY] = true;
                    queue.add(new int[]{newX, newY});
                }
            }
        }

        return count == total;
    }

    private void generateVariants() {
        variants = new ArrayList<>();
        char[][] current = shape;

        // Rotasi 90 derajat sebanyak 4 kali
        for (int i = 0; i < 4; i++) {  
            current = rotate(current);
            variants.add(current);
        }

        // Flip horizontal dan lakukan rotasi lagi
        current = flipHorizontal(shape);
        variants.add(current);
        for (int i = 0; i < 3; i++) {
            current = rotate(current);
            variants.add(current);
        }
    }


    private char[][] rotate(char[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        char[][] rotated = new char[cols][rows];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                rotated[c][rows - 1 - r] = matrix[r][c];
            }
        }
        return rotated;
    }

    private char[][] flipHorizontal(char[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        char[][] flipped = new char[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                flipped[r][cols - 1 - c] = matrix[r][c];
            }
        }
        return flipped;
    }
}