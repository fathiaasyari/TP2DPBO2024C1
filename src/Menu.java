import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Menu extends JFrame{
    public static void main(String[] args) {
        // buat object window
        Menu window = new Menu();

        // atur ukuran window
        window.setSize(480, 560);

        // letakkan window di tengah layar
        window.setLocationRelativeTo(null);
        // isi window
        window.setContentPane(window.mainPanel);
        // ubah warna background
        window.getContentPane().setBackground(Color.PINK);
        // tampilkan window
        window.setVisible(true);
        // agar program ikut berhenti saat window diclose
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // index baris yang diklik
    private int selectedIndex = -1;
    // list untuk menampung semua mahasiswa
//    private ArrayList<Mahasiswa> listMahasiswa;

    private Database database;

    private JPanel mainPanel;
    private JTextField nimField;
    private JTextField namaField;
    private JTable mahasiswaTable;
    private JButton addUpdateButton;
    private JButton cancelButton;
    private JComboBox jenisKelaminComboBox;
    private JComboBox jurusanComboBox;
    private JButton deleteButton;
    private JLabel titleLabel;
    private JLabel nimLabel;
    private JLabel namaLabel;
    private JLabel jenisKelaminLabel;
    private JLabel jurusanLabel;



    // constructor
    public Menu() {
//        // inisialisasi listMahasiswa
//        listMahasiswa = new ArrayList<>();
//
//        // isi listMahasiswa
//        populateList();

        database = new Database();

        // isi tabel mahasiswa
        mahasiswaTable.setModel(setTable());

        // ubah styling title
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));

        // atur isi combo box
        String[] jenisKelaminData = {"", "Laki-Laki", "Perempuan"};
        jenisKelaminComboBox.setModel(new DefaultComboBoxModel(jenisKelaminData));

        String[] jurusanData = {"", "Pend.Ilmu Komputer", "Ilmu Komputer"};
        jurusanComboBox.setModel(new DefaultComboBoxModel(jurusanData));



        // sembunyikan button delete
            deleteButton.setVisible(false);

        // saat tombol add/update ditekan
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex == -1){
                    insertData();
                }else {
                    updateData();
                }
            }
        });
        // saat tombol delete ditekan
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex >= 0){
                    deleteData();
                }


            }
        });
        // saat tombol cancel ditekan
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            clearForm();
            }
        });
        // saat salah satu baris tabel ditekan
        mahasiswaTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // ubah selectedIndex menjadi baris tabel yang diklik
                selectedIndex = mahasiswaTable.getSelectedRow();

                // simpan value textfield dan combo box
                String selectedNim = mahasiswaTable.getModel().getValueAt(selectedIndex, 1).toString();
                String selectedNama = mahasiswaTable.getModel().getValueAt(selectedIndex, 2).toString();
                String selectedJenisKelamin = mahasiswaTable.getModel().getValueAt(selectedIndex, 3).toString();
                String selectedJurusan = mahasiswaTable.getModel().getValueAt(selectedIndex, 4).toString();


                // ubah isi textfield dan combo box
                nimField.setText(selectedNim);
                namaField.setText(selectedNama);
                jenisKelaminComboBox.setSelectedItem(selectedJenisKelamin);
                jurusanComboBox.setSelectedItem(selectedJurusan);

                // ubah button "Add" menjadi "Update"
                addUpdateButton.setText("Update");

                // tampilkan button delete
                deleteButton.setVisible(true);
            }
        });
    }

    public final DefaultTableModel setTable() {
        // tentukan kolom tabel
        Object[] column = {"No", "NIM", "Nama", "Jenis Kelamin", "Jurusan"};

        // buat objek tabel dengan kolom yang sudah dibuat
        DefaultTableModel temp = new DefaultTableModel(null, column);

        try {
            ResultSet resultSet = database.selectQuery("SELECT * FROM mahasiswa");
            int i = 1;
            while(resultSet.next()) {
                Object[] row = new Object[5];
                row[0] = i;
                row[1] = resultSet.getString("nim");
                row[2] = resultSet.getString("nama");
                row[3] = resultSet.getString("jenis_kelamin");
                row[4] = resultSet.getString("jurusan");

                temp.addRow(row);
                i++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return temp;
    }
    public void insertData() {
        // ambil value dari textfield dan combobox
        String nim = nimField.getText();
        String nama = namaField.getText();
        String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString();
        String jurusan = jurusanComboBox.getSelectedItem().toString();

        // memeriksa apakah ada field yang kosong
        if (nim.isEmpty() || nama.isEmpty() || jenisKelamin.isEmpty() || jurusan.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // memeriksa apakah NIM sudah ada dalam tabel
        DefaultTableModel model = (DefaultTableModel) mahasiswaTable.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 1).equals(nim)) {
                JOptionPane.showMessageDialog(null, "NIM sudah ada dalam tabel!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        String sql = "INSERT INTO mahasiswa Values (null,'" + nim + "','" + nama + "', '" + jenisKelamin + "', '" + jurusan + "');";
        database.insertUpdateDeleteQuery(sql);

        // update tabel
        mahasiswaTable.setModel(setTable());

        // bersihkan form
        clearForm();

        // feedback
        System.out.println("Insert Berhasil");
        JOptionPane.showMessageDialog(null, "Data Berhasil Ditambahkan");
    }

    public void updateData() {
        // Ambil data dari form
        String nim = nimField.getText();
        String nama = namaField.getText();
        String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString();
        String jurusan = jurusanComboBox.getSelectedItem().toString();

        // Periksa apakah ada field yang kosong
        if (nim.isEmpty() || nama.isEmpty() || jenisKelamin.isEmpty() || jurusan.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Keluar dari metode jika ada field yang kosong
        }

        // Konfirmasi update
        int confirmUpdate = JOptionPane.showConfirmDialog(null, "Anda yakin ingin mengupdate data?", "Konfirmasi update", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirmUpdate == JOptionPane.YES_OPTION) { // Jika mengkonfirmasi update data
            // Update data di database
            String sql = "UPDATE mahasiswa SET nama = '" + nama + "', jenis_kelamin = '" + jenisKelamin + "', jurusan = '" + jurusan + "' WHERE nim = '" + nim + "'";
            database.insertUpdateDeleteQuery(sql);

            // Update tabel
            mahasiswaTable.setModel(setTable());

            // Bersihkan form
            clearForm();

            // Feedback
            System.out.println("Update berhasil!");
            JOptionPane.showMessageDialog(null, "Data berhasil diupdate!");
        } else { // Jika tidak mengkonfirmasi update data
            // Bersihkan form
            clearForm();
        }
    }

    public void deleteData() {
        String selectedNim = mahasiswaTable.getModel().getValueAt(selectedIndex, 1).toString();

        // konfirmasi penghapusan
        int confirmDel = JOptionPane.showConfirmDialog(null, "Anda yakin ingin menghapus data?", "Konfirmasi penghapusan", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirmDel == JOptionPane.YES_OPTION) { // Jika mengkonfirmasi penghapusan data
            // japus data dari database
            String sql = "DELETE FROM mahasiswa WHERE nim = '" + selectedNim + "'";
            database.insertUpdateDeleteQuery(sql);

            // update tabe;
            mahasiswaTable.setModel(setTable());

            // Bersihkan form
            clearForm();

            // Feedback
            System.out.println("Delete berhasil!");
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
        } else { // Jika tidak mengkonfirmasi penghapusan data
            // Bersihkan form
            clearForm();
        }
    }

    public void clearForm() {
        // kosongkan semua texfield dan combo box
        nimField.setText("");
        namaField.setText("");
        jenisKelaminComboBox.setSelectedItem("");
        jurusanComboBox.setSelectedItem("");



        // ubah button "Update" menjadi "Add"
        addUpdateButton.setText("Add");
        // sembunyikan button delete
        deleteButton.setVisible(false);

        // ubah selectedIndex menjadi -1 (tidak ada baris yang dipilih)
        selectedIndex = -1;
    }

//    private void populateList() {
//        listMahasiswa.add(new Mahasiswa("2203999", "Amelia Zalfa Julianti", "Perempuan", "Pend. Ilmu Komputer"));
//        listMahasiswa.add(new Mahasiswa("2202292", "Muhammad Iqbal Fadhilah", "Laki-laki", "Pend. Ilmu Komputer"));
//        listMahasiswa.add(new Mahasiswa("2202346", "Muhammad Rifky Afandi", "Laki-laki", "Pend. Ilmu Komputer"));
//        listMahasiswa.add(new Mahasiswa("2210239", "Muhammad Hanif Abdillah", "Laki-laki", "Pend. Ilmu Komputer"));
//        listMahasiswa.add(new Mahasiswa("2202046", "Nurainun", "Perempuan", "Pend. Ilmu Komputer"));
//        listMahasiswa.add(new Mahasiswa("2205101", "Kelvin Julian Putra", "Laki-laki", "Pend. Ilmu Komputer"));
//        listMahasiswa.add(new Mahasiswa("2200163", "Rifanny Lysara Annastasya", "Perempuan", "Pend. Ilmu Komputer"));
//        listMahasiswa.add(new Mahasiswa("2202869", "Revana Faliha Salma", "Perempuan", "Pend. Ilmu Komputer"));
//        listMahasiswa.add(new Mahasiswa("2209489", "Rakha Dhifiargo Hariadi", "Laki-laki", "Pend. Ilmu Komputer"));
//        listMahasiswa.add(new Mahasiswa("2203142", "Roshan Syalwan Nurilham", "Laki-laki", "Pend. Ilmu Komputer"));
//        listMahasiswa.add(new Mahasiswa("2200311", "Raden Rahman Ismail", "Laki-laki", "Pend. Ilmu Komputer"));
//        listMahasiswa.add(new Mahasiswa("2200978", "Ratu Syahirah Khairunnisa", "Perempuan", "Pend. Ilmu Komputer"));
//        listMahasiswa.add(new Mahasiswa("2204509", "Muhammad Fahreza Fauzan", "Laki-laki", "Pend. Ilmu Komputer"));
//        listMahasiswa.add(new Mahasiswa("2205027", "Muhammad Rizki Revandi", "Laki-laki", "Pend. Ilmu Komputer"));
//        listMahasiswa.add(new Mahasiswa("2203484", "Arya Aydin Margono", "Laki-laki", "Pend. Ilmu Komputer"));
//        listMahasiswa.add(new Mahasiswa("2200481", "Marvel Ravindra Dioputra", "Laki-laki", "Pend. Ilmu Komputer"));
//        listMahasiswa.add(new Mahasiswa("2209889", "Muhammad Fadlul Hafiizh", "Laki-laki", "Pend. Ilmu Komputer"));
//        listMahasiswa.add(new Mahasiswa("2206697", "Rifa Sania", "Perempuan", "Pend. Ilmu Komputer"));
//        listMahasiswa.add(new Mahasiswa("2207260", "Imam Chalish Rafidhul Haque", "Laki-laki", "Pend. Ilmu Komputer"));
//        listMahasiswa.add(new Mahasiswa("2204343", "Meiva Labibah Putri", "Perempuan", "Pend. Ilmu Komputer"));
//
//    }
}
