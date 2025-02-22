# 🧩 IQ Puzzler Pro Solver  
# Tugas Kecil 1 Strategi Algoritma IF2211

## 📌 Deskripsi  
Program ini adalah **solver** untuk permainan **IQ Puzzler Pro**, yang menggunakan algoritma **brute-force** untuk mencari solusi dari susunan block pada papan permainan.  
Program membaca input dari file `.txt`, validasi input, mencoba semua kemungkinan rotasi dan refleksi dari block, serta menampilkan atau menyimpan salah satu solusi dalam bentuk `.txt` dan gambar.  

## 🛠 Struktur Program
Berikut adalah struktur program tugas kecil ini :
```sh
/Tucil1_13523118
├── /bin                # Compiled .class
│   ├── Block.class     
│   ├── IO.class        
│   ├── Main.class      
│   └── Solver.class    
├── /doc                # Laporan Tucil
├── /output             # Hasil output dari program
├── /src                # Source code program
│   ├── Block.java     
│   ├── IO.java        
│   ├── Main.java      
│   └── Solver.java
├── /test               # Test case
└── README.md           # Dokumentasi projek
```

## Getting Started 🌐
Berikut instruksi instalasi dan penggunaan program

### Prerequisites

Pastikan anda sudah memiliki:
- **Java 8 atau lebih baru**
- **IDE atau terminal** untuk menjalankan program

### Installation
1. **Clone repository ke dalam suatu folder**

```bash
  https://github.com/farrelathalla/Tucil1_13523118.git
```

2. **Pergi ke directory /Tucil1_13523118**

```bash
  cd Tucil1_13523118
```

3. **Compile program**

```bash
  javac -d bin -sourcepath src src/*.java
```

4. **Jalankan program**

```bash
  java -cp bin Main
```

## **📌 Cara Penggunaan**

1. **Jalankan program** melalui terminal atau IDE yang mendukung Java.
2. **Masukkan nama file input**, misalnya: input.txt
3. Program akan membaca dan memvalidasi format input serta mencoba menyelesaikan puzzle.
4. Jika solusi ditemukan, hasil akan ditampilkan di terminal dengan warna sesuai setiap blok.
5. Program akan menanyakan apakah solusi ingin disimpan sebagai:
    Teks (.txt),
    Gambar (.png / .jpg)
6. Masukkan nama file untuk menyimpan hasil, misalnya:
    solution.txt untuk menyimpan sebagai teks,
    solution.png untuk menyimpan sebagai gambar

## **✍️ Author**
**👤 Farrel Athalla Putra**

**NIM 13523118**

**Kelas K2**
