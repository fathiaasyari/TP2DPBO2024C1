# Janji
Saya Syaila Fathia Azzahra dengan NIM 2206272 mengerjakan TP2DPBO2024C1 dalam Praktikum mata kuliah DPBO untuk keberkahan-Nya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamin.

# Desain dan Alur Program
Sebuah program CRUD sederhana terkait dengan data mahasiswa mennggunakan JavaSwing dan terhubung dengan database.
## Alur Program:

1. **Menambah Data Mahasiswa**:
   - Pengguna memasukkan informasi mahasiswa baru seperti NIM, nama, jenis kelamin, dan jurusan melalui antarmuka pengguna.
   - Saat pengguna menekan tombol "Tambah", program melakukan validasi terhadap field-field yang dimasukkan:
     - Memeriksa apakah semua field telah diisi.
     - Memeriksa apakah NIM yang dimasukkan unik (tidak ada duplikat dalam database).
   - Jika validasi berhasil, program menyimpan data baru ke dalam database dan menampilkan data tersebut dalam tabel.
   - Jika validasi gagal, program memberikan pesan kesalahan kepada pengguna dan tidak menyimpan data.

2. **Mengupdate Data Mahasiswa**:
   - Pengguna memilih baris data mahasiswa yang ingin diupdate dari tabel.
   - Program mengambil data dari baris yang dipilih dan menampilkan informasi tersebut pada antarmuka pengguna.
   - Pengguna memperbarui informasi mahasiswa pada antarmuka pengguna.
   - Saat pengguna menekan tombol "Update", program melakukan validasi terhadap field-field yang dimasukkan:
     - Memeriksa apakah semua field telah diisi.
   - Jika validasi berhasil, program melakukan pembaruan data di database dan menampilkan data yang diperbarui dalam tabel.
   - Jika validasi gagal, program memberikan pesan kesalahan kepada pengguna dan tidak melakukan pembaruan data.

3. **Menghapus Data Mahasiswa**:
   - Pengguna memilih baris data mahasiswa yang ingin dihapus dari tabel.
   - Program mengambil data dari baris yang dipilih dan menampilkan informasi tersebut pada antarmuka pengguna.
   - Saat pengguna menekan tombol "Hapus", program menampilkan konfirmasi penghapusan kepada pengguna.
   - Jika pengguna menekan tombol "Ya", program menghapus data mahasiswa dari database dan mengupdate tampilan tabel.
   - Jika pengguna menekan tombol "Tidak", program tidak melakukan penghapusan data dan kembali ke tampilan tabel.

## Desain Program:

1. **Antarmuka Pengguna (GUI)**:
   - Program menggunakan JavaSwing untuk membangun antarmuka pengguna grafis (GUI).
   - GUI terdiri dari elemen-elemen seperti JTextField, JTable, JButton, JComboBox, dan JLabel untuk memfasilitasi input, tampilan data, dan interaksi pengguna.
   
2. **Validasi Input**:
   - Sebelum menambah atau mengupdate data, program melakukan validasi terhadap input pengguna.
   - Validasi meliputi pemeriksaan apakah semua field telah diisi (tidak boleh kosong) dan apakah NIM yang dimasukkan unik.

3. **Interaksi dengan Database**:
   - Program terhubung ke database untuk menyimpan, mengambil, memperbarui, dan menghapus data.
   - Setiap operasi CRUD (Create, Read, Update, Delete) melibatkan eksekusi query SQL yang sesuai.

4. **Tampilan Data**:
   - Program menampilkan data mahasiswa dalam bentuk tabel di antarmuka pengguna.
   - Tabel ini diperbarui setelah operasi tambah, update, atau hapus dilakukan.


## Fitur
1. **Menambah Data Mahasiswa**: Pengguna dapat memasukkan informasi seperti NIM, nama, jenis kelamin, dan jurusan untuk menambahkan data mahasiswa baru, namun field tidak boleh kosong serta harus memasukkan NIM yang berbeda dengan data yang sudah ada. 
2. **Mengupdate Data Mahasiswa**: Pengguna dapat mengedit informasi data mahasiswa yang telah ada, namun field tidak boleh kosong. (+confrimation prompt)
3. **Menghapus Data Mahasiswa**: Pengguna dapat menghapus data mahasiswa dari database(+confrimation prompt)
4. **Menampilkan Data**: Program ini menampilkan data yang sama dengan apa yang ada di dalam database.

# Dokumentasi 
https://github.com/fathiaasyari/TP2DPBO2024C1/assets/136889043/2124ccc3-b801-4820-a2fa-1fd3b457bb49
