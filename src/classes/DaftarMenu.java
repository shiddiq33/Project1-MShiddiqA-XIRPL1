package classes;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DaftarMenu {
    private ArrayList<Menu> daftarMenu;

    public DaftarMenu() {
        daftarMenu = new ArrayList<>();
    }

    public void tambahMenu(Menu menu){
        daftarMenu.add(menu);
    }

    public void getMenuByKategori(String kategori){
        System.out.println("======== " + kategori + " ========");

        for (int i = 0; i <daftarMenu.size(); i++){
            Menu m = daftarMenu.get(i);
            if(m.getKategori().equals(kategori)){
                System.out.println((i + 1) + ". " + m.getNamaMenu() + "\t" + m.getHarga());
            }

        }
    }

    public void tampilDaftarMenu() {
        System.out.println("======== RAFZYRAMEN ========");
        getMenuByKategori("Ramen");
        getMenuByKategori("Kuah");
        getMenuByKategori("Toping");
        getMenuByKategori("Minuman");
    }

    public Menu pilihMenu(){
        try {
            Scanner input = new Scanner(System.in);

            System.out.print("Nomor Menu yang dipesan : ");
            int noMenu = input.nextInt();

//            Ambil Menu berdasarkan noMenu, di -1 karena arrayList mulai dari 0
            Menu m = daftarMenu.get(noMenu-1);

//            Cek Apakah menu kuah?
            if(!m.getKategori().equalsIgnoreCase("Kuah")){
                return m;
            } else {
                System.out.println("[Err] Pesan dulu Menu Ramen");
                return pilihMenu();
            }
        } catch (IndexOutOfBoundsException err){
//            Jika noMenu tidak ada, maka akan masuk ke section ini
//            noMenu dianggap tidak ada ketika noMenu diluar dari index

            System.out.println("[Err] Pesanan Tidak Tersedia!");
//            Jika tidak ada, maka user akan diminta untuk mengulang memasukkan nomer menu
//            Teknik ini Disebut dengan rekursif
            return pilihMenu();
        } catch (InputMismatchException err){
//            Jika Input bukan berupa angka akan masuk kesini
            System.out.println("[Err] Mohon masukkan nomor menu");
            return pilihMenu();
        }
    }

    public Menu pilihKuah(){
        try {
            Scanner input = new Scanner(System.in);

            System.out.print("Kuah [sesuai nomor menu] : ");
            int noMenu = input.nextInt();

//            get menu berdasarkan noMenu, di -1 karena array List mulai dari 0
            Menu m = daftarMenu.get(noMenu-1);

//            cek apakah menu kuah?
            if(m.getKategori().equalsIgnoreCase("Kuah")){
                return m;
            } else {
                System.out.println("[Err] Bukan Menu Kuah!");
                return pilihKuah();
            }
        } catch (IndexOutOfBoundsException err){
//            Jika noMenu tidak ada, maka akan masuk kesini
//            noMenu dianggap tidak ada ketika noMenu diluar dari index pada arrayList
            System.out.println("[Err] Pesanan Tidak Tersedia");
//            Jika tidak ada, maka user akan diminta untuk mengulang memasukkan nomer menu
//            Teknik ini Disebut dengan rekursif
            return pilihKuah();
        } catch (InputMismatchException err){
//            Jika input bukan beruppa angka akan masuk kesini
            System.out.println("[Err] Mohon masukkan nomor kuah");
            return pilihKuah();
        }
    }
}
