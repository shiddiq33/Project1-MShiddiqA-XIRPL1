package classes;

public class Pesanan {
    private Menu menu;
    private int jumlah;
    private String keterangan;

    public Pesanan (Menu menu, int jumlah){
        this.menu = menu;
        this.jumlah = jumlah;
    }

    public Menu getMenu(){
        return menu;
    }

    public int getJumlah(){
        return jumlah;
    }

    public void setJumlah(){

    }

    public void setKeterangan(String keterangan){
        this.keterangan = keterangan;
    }

    public String getKeterangan() {
        return keterangan;
    }
}
