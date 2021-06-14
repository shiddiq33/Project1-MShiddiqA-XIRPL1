package main;
import classes.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainAplikasiKasir {

    public DaftarMenu daftarMenu;
    public static double PAJAK_PPN = 0.10;
    public static double BIAYA_SERVICE = 0.05;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        String noTransaksi, namaPemesan, tanggal, noMeja = "";
        String transaksiLagi = "", pesanLagi = "", keterangan = "", makanDitempat;
        int jumlahPesanan, noMenu;

        MainAplikasiKasir application = new MainAplikasiKasir();
        application.generateDaftarMenu();

//        Mulai Transaksi
        System.out.println("============ TRANSAKSI ============");
        do {

//        Ambil data Transaksi
            System.out.print("No Transaksi : ");
            noTransaksi = input.next();

            System.out.print("Pemesan : ");
            namaPemesan = input.next();

            System.out.print("Tanggal : [dd-mm-yyyy] \t");
            tanggal = input.next();

            System.out.print("Makan ditempat? [Y/N]");
            makanDitempat = input.next();
            if (makanDitempat.equalsIgnoreCase("Y")) {
                System.out.print("Nomor Meja : ");
                noMeja = input.next();
            }

//        Buat Transaksi Baru
            Transaksi trans = new Transaksi(noTransaksi, namaPemesan, tanggal, noMeja);
            System.out.println("============ PESANAN ============");
            int noKuah;
            do {
                Menu menuYangDipilih = application.daftarMenu.pilihMenu();
                jumlahPesanan = (int) application.cekInputNumber("Jumlah : ");

//                Buat Pesanan
                Pesanan pesanan = new Pesanan(menuYangDipilih, jumlahPesanan);
                trans.tambahPesanan(pesanan);
//                Khusus untuk menu ramen, pesanan kuahnya langsung diinput juga
                if (menuYangDipilih.getKategori().equals("Ramen")) {
//                        Looping sesuai jumlah pesanan ramen
                    int jumlahRamen = jumlahPesanan;

                    do {
//                         Ambil objek menu berdasarkan nomor yang dipilih
                        Menu kuahYangDipilih = application.daftarMenu.pilihKuah();

                        System.out.print("Level: [0-5] : ");
                        String level = input.next();

//                            Validasi jumlah kuah tidak boleh lebih besar dari jumlahRamen
                        int jumlahKuah = 0;

                        do {
                            jumlahKuah = (int) application.cekInputNumber("Jumlah : ");
                            if (jumlahKuah > jumlahRamen) {
                                System.out.println("[Err] Jumlah kuah melebihi jumlah ramen yang sudah dipesan");
                            } else {
                                break;
                            }
                        } while (jumlahKuah > jumlahRamen);
//                           Set Pesanan kuah
                        Pesanan pesananKuah = new Pesanan(kuahYangDipilih, jumlahKuah);
                        pesananKuah.setKeterangan("Level " + level);

//                            Tambahkan pesanan kuah ke transaksi
                        trans.tambahPesanan(pesananKuah);

//                            Hitung jumlah ramen yang belum dipesan kuahnya
                        jumlahRamen -= jumlahKuah;
                    } while (jumlahRamen > 0);
                } else {
                    System.out.print("Keterangan [- Jika kosong]: ");
                    keterangan = input.next();
                }

//                    Cek jika keterangan diisi selain "-" set ke pesanan
                if (!keterangan.equals("-")) {
                    pesanan.setKeterangan(keterangan);
                }

//                Konfirmasi, Mau tambah pesanan atau tidak
                System.out.print("Tambah Pesanan Lagi? [Y/N] : ");
                pesanLagi = input.next();
            } while (pesanLagi.equalsIgnoreCase("Y"));
//            Cetak Struk
            trans.cetakStruk();

//        Hitung total harga
            double totalPesanan = trans.hitungTotalPesanan();
            System.out.println("========================");
            System.out.println("Total : \t\t" + totalPesanan);

//        Hitung Pajak
//        Jika makan ditempat, biaya pajak = 10% ppn + 5% service
            trans.setPajak(PAJAK_PPN);
            double ppn = trans.hitungPajak();
            System.out.println("Pajak 10% : \t\t" + ppn);

            double biayaService = 0;
            if (makanDitempat.equalsIgnoreCase("Y")) {
                trans.setBiayaService(BIAYA_SERVICE);
                biayaService = trans.hitungBiayaService();
                System.out.println("Biaya Service 5% : \t" + biayaService);
            }

//        Tampilkan total bayar
            System.out.println("Total : \t\t" + trans.hitungTotalBayar(ppn, biayaService));

//        Cek uang bayar, apakah > total bayar atau tidak
            double kembalian = 0;
            do {
                double uangBayar = application.cekInputNumber("Uang Bayar : \t\t");

                kembalian = trans.hitungKembalian(uangBayar);
                if (kembalian < 0) {
                    System.out.println("[Err] Uang anda kurang");
                } else {
                    System.out.println("Kembalian : \t\t" + kembalian);
                    break;
                }
            } while (kembalian < 0);
            System.out.println("Lakukan Transaksi Lagi ? [Y/N]: ");
            transaksiLagi = input.next();
        } while (transaksiLagi.equalsIgnoreCase("Y"));
        System.out.println("============ TERIMA KASIH ============");
    }

    public void generateDaftarMenu(){
        daftarMenu = new DaftarMenu();

//        Membuat Menu Ramen
        daftarMenu.tambahMenu(new Ramen("Ramen Seafood", 25000));
        daftarMenu.tambahMenu(new Ramen("Ramen Original", 18000));
        daftarMenu.tambahMenu(new Ramen("Ramen Vegetarian", 22000));
        daftarMenu.tambahMenu(new Ramen("Ramen Karnivor", 28000));

//        Membuat Menu Kuah
        daftarMenu.tambahMenu(new Kuah("Kuah Orisinil"));
        daftarMenu.tambahMenu(new Kuah("Kuah Internasional"));
        daftarMenu.tambahMenu(new Kuah("Kuah Spicy Lada"));
        daftarMenu.tambahMenu(new Kuah("Kuah Soto Padang"));

//        Membuat Menu Toping
        daftarMenu.tambahMenu(new Toping("Crab Stick Bakar", 6000));
        daftarMenu.tambahMenu(new Toping("Chicken Katsu", 8000));
        daftarMenu.tambahMenu(new Toping("Gyoza Goreng", 4000));
        daftarMenu.tambahMenu(new Toping("Bakso Goreng", 7000));
        daftarMenu.tambahMenu(new Toping("Enoki Goreng", 5000));

//        Membuat Menu Minuman
        daftarMenu.tambahMenu(new Minuman("Jus Aplukat SPC", 10000));
        daftarMenu.tambahMenu(new Minuman("Jus Stroberi", 11000));
        daftarMenu.tambahMenu(new Minuman("Cappucino Coffee", 15000));
        daftarMenu.tambahMenu(new Minuman("Vietnam Dripp", 14000));

        daftarMenu.tampilDaftarMenu();
    }

    public double cekInputNumber(String label) {
        try {
            Scanner getInput = new Scanner(System.in);
            System.out.print(label);
            double nilai = getInput.nextDouble();

            return nilai;
        } catch (InputMismatchException ex){
            System.out.println("[Err] Harap Masukkan angka");
            return cekInputNumber(label);
        }
    }

}
