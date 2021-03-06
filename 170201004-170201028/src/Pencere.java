
import java.awt.HeadlessException;
import java.util.Scanner;
import javax.swing.JFrame;

interface IROBOT {
    //Robot classi icin gereken degiskenler

    public int getmotorSayisi();

    public void setmotorSayisi(int motorSayisi);

    public double getyukMiktari();

    public void setyukMiktari(double yukMiktari);

    public String getrobotTipi();

    public void setrobotTipi(String robotTipi);

    public double yoluBul(int[] robotHareketleri);

    /*Hibrit robotlar icin sabitlendikten sonra kol hareketlerini de al */
    public double toplamSureyiBul(double yol, double hiz, double ekSure, int engeldenGecme);/*ekSure manipulator robotlar icin kolaGecmeSuresi,gezgin robotlar icin engelGecmeSuresi*/
    public boolean izgaradanCikiyorMu(int[] sonKonumlar, int[] robotHareketleri, int[] robotunKonumlari, int ileri, int geri, int saga, int sola);
}

interface IGEZGIN extends IROBOT {

    public double gethiz();

    public void sethiz(double hiz);

    public double engelGecmeSuresiBul(int motorSayisi, int engeldenGecme);

}

interface IMANIPULATOR extends IROBOT {

    public int getkolUzunlugu();

    public void setkolUzunlugu(int kolUzunlugu);

    public int getkapasite();

    public void setkapasite(int kapasite);

    public double gettasimaHizi();

    public void settasimahHizi(double tasimaHizi);

    public double getkolaGecmeSuresi();

    public void setkolaGecmeSuresi(double kolaGecmeSuresi);

}

class robot implements IROBOT {

    private int motorSayisi = 0; //robot interface 
    private double yukMiktari = 0; //robot interface 
    private String robotTipi = null; //robot interface 

    @Override
    public int getmotorSayisi() {
        return motorSayisi;
    }

    @Override
    public void setmotorSayisi(int motorSayisi) {
        this.motorSayisi = motorSayisi;
    }

    @Override
    public double getyukMiktari() {
        return yukMiktari;
    }

    @Override
    public void setyukMiktari(double yukMiktari) {
        this.yukMiktari = yukMiktari;
    }

    @Override
    public String getrobotTipi() {
        return robotTipi;
    }

    @Override
    public void setrobotTipi(String robotTipi) {
        this.robotTipi = robotTipi;
    }

    @Override
    public double yoluBul(int[] robotHareketleri) {
        return 0;
    }

    @Override
    public double toplamSureyiBul(double yol, double hiz, double ekSure, int engeldenGecme) {
        return 0;
    }

    @Override
    public boolean izgaradanCikiyorMu(int[] sonKonumlar, int[] robotHareketleri, int[] robotunKonumlari, int ileri, int geri, int saga, int sola) {
        /*bu method sonKonumlar dizisinin 0.indisi x eksenindeki son konumu, 1.indisi ise y eksenindeki son konumu getirir
        robotHareketleri dizisinin 0.indisi ileri yonlu hareketi 1.indisi geri yonlu hareketi, 2.indisi saga yonlu hareketi
        3.indisi sola yonlu hareketi getirir ve son konum 200 x 200 pikselleri icinde degilse izgaranin disindadir buna gore
        izgaranin disina cikarsa true, cikmazsa false deger dondurur.*/
        if (robotunKonumlari[0] + saga - sola > 20 || robotunKonumlari[0] + saga - sola < 0) {
            System.out.println("Robot" + " " + sola + " " + "birim sola gittigi icin izgaradan cikmistir.");
            System.out.println("Robot izgaradan ciktigi icin program sonlandi!");
            return true;  //evet izgara disina cikiyor
        } else if (robotunKonumlari[1] + ileri > 20 || robotunKonumlari[1] + ileri < 0) {
            System.out.println("Robot" + " " + ileri + " " + "birim ileri gittigi icin izgaradan cikmistir");
            System.out.println("Robot izgaradan ciktigi icin program sonlandi!");
            return true;
        } else if (robotunKonumlari[1] + ileri - geri > 20 || robotunKonumlari[1] + ileri - geri < 0) {
            System.out.println("Robot" + " " + geri + " " + "birim geri gittigi icin izgaradan cikmistir");
            System.out.println("Robot izgaradan ciktigi icin program sonlandi!");
            return true;
        } else if (robotunKonumlari[0] + saga > 20 || robotunKonumlari[0] + saga < 0) {
            System.out.println("Robot" + " " + saga + " " + "birim saga gittigi icin izgaradan cikmistir");
            System.out.println("Robot izgaradan ciktigi icin program sonlandi!");
            return true;
        } else {

            return false;
        }
    }

    public int engelleKarsilastiMiIleri(int[] engelKonumlariX, int[] engelKonumlariY, int ileri, String robotTipi, int[] robotunKonumlari) {
        int say1 = 0, i, j;

        if (robotTipi.contains("spider")) {
            for (i = 0; i < ileri; i++) {
                for (j = 0; j < engelKonumlariY.length; j++) {
                    if (robotunKonumlari[0] == engelKonumlariX[j] && robotunKonumlari[1] + (i + 1) == engelKonumlariY[j]) {
                        return -1;
                        /*engelle karsilasirsa -1 doner*/
                    } // ./if esit mi
                }
            }
        } else {
            for (i = 0; i < ileri; i++) {
                for (j = 0; j < engelKonumlariY.length; j++) {
                    if (robotunKonumlari[0] == engelKonumlariX[j] && robotunKonumlari[1] + (i + 1) == engelKonumlariY[j]) {
                        say1++;
                    }

                }
            }

            return say1;
        }
        return -2;
    }

    public int engelleKarsilastiMiGeri(int[] engelKonumlariX, int[] engelKonumlariY, int ileri, int geri, String robotTipi, int[] robotunKonumlari) {
        int say2 = 0, i, j;
        if (robotTipi.contains("spider")) {
            for (i = 0; i < geri; i++) {
                for (j = 0; j < engelKonumlariY.length; j++) {
                    if (robotunKonumlari[0] == engelKonumlariX[j] && robotunKonumlari[1] + ileri - (i + 1) == engelKonumlariY[j]) {
                        return -1;
                        /*engelle karsilasirsa -1 doner*/
                    }

                }
            }
        } else {
            for (i = 0; i < geri; i++) {
                for (j = 0; j < engelKonumlariY.length; j++) {
                    if (robotunKonumlari[0] == engelKonumlariX[j] && robotunKonumlari[1] + ileri - (i + 1) == engelKonumlariY[j]) {
                        say2++;
                    }

                }
            }
            return say2;
        }
        return -2;
    }

    public int engelleKarsilastiMiSaga(int[] engelKonumlariX, int[] engelKonumlariY, int ileri, int geri, int saga, String robotTipi, int[] robotunKonumlari) {
        int say3 = 0, i, j;
        if (robotTipi.contains("spider")) {
            for (i = 0; i < saga; i++) {
                for (j = 0; j < engelKonumlariX.length; j++) {
                    if (robotunKonumlari[0] + (i + 1) == engelKonumlariX[j] && robotunKonumlari[1] + ileri - geri == engelKonumlariY[j]) {
                        return -1;
                        /*engelle karsilasirsa -1 doner*/
                    }

                }
            }

        } else {
            for (i = 0; i < saga; i++) {
                for (j = 0; j < engelKonumlariX.length; j++) {
                    if (robotunKonumlari[0] + (i + 1) == engelKonumlariX[j] && robotunKonumlari[1] + ileri - geri == engelKonumlariY[j]) {
                        say3++;
                    }
                }
            }
            return say3;
        }
        return -2;
    }

    public int engelleKarsilastiMiSola(int[] engelKonumlariX, int[] engelKonumlariY, int ileri, int geri, int saga, int sola, String robotTipi, int[] robotunKonumlari) {
        int say4 = 0, i, j;
        if (robotTipi.contains("spider")) {
            for (i = 0; i < sola; i++) {
                for (j = 0; j < engelKonumlariX.length; j++) {
                    if (robotunKonumlari[0] + saga - (i + 1) == engelKonumlariX[j] && robotunKonumlari[1] + ileri - geri == engelKonumlariY[j]) {
                        return -1;
                        /*engelle karsilasirsa -1 doner*/
                    }

                }
            }
        } else {
            for (i = 0; i < sola; i++) {
                for (j = 0; j < engelKonumlariX.length; j++) {
                    if (robotunKonumlari[0] + saga - (i + 1) == engelKonumlariX[j] && robotunKonumlari[1] + ileri - geri == engelKonumlariY[j]) {
                        say4++;
                    }

                }
            }
            return say4;
        }
        return -2;
    }

}

class gezgin extends robot implements IGEZGIN {

    private double hiz = 0;

    @Override
    public double engelGecmeSuresiBul(int motorSayisi, int engeldenGecme) {
        return 0;
    }

    public boolean hizlariKarsilastir(int tekerlekliHiz, int paletliHiz, int spiderHiz) {
        /*gezgin robot sinifinda bulunan robotlarin hiz siralamalarini yapmak icin bu metodu olusturduk*/ {

            if ((tekerlekliHiz != 0 && paletliHiz != 0 && spiderHiz != 0) && (tekerlekliHiz > paletliHiz && paletliHiz > spiderHiz)) {

                System.out.println("Hizlar Sirali.");
                return true;
            } else if ((tekerlekliHiz != 0 && paletliHiz != 0 && spiderHiz == 0) && (paletliHiz > tekerlekliHiz)) {
                System.out.println("Tekerlekli robotun hizi paletli robottan buyuk olmalidir!");
                return false;
            } else if ((tekerlekliHiz != 0 && paletliHiz != 0 && spiderHiz == 0) && (paletliHiz < tekerlekliHiz)) {
                System.out.println("Hizlar sirali!");
                return true;
            } else if ((tekerlekliHiz != 0 && paletliHiz == 0 && spiderHiz != 0) && (spiderHiz > tekerlekliHiz)) {
                System.out.println("Tekerlekli robotun hizi spider robottan buyuk olmalidir!");
                return false;
            } else if ((tekerlekliHiz != 0 && paletliHiz == 0 && spiderHiz != 0) && (spiderHiz < tekerlekliHiz)) {
                System.out.println("Hizlar sirali!");
                return true;
            } else if ((tekerlekliHiz == 0 && paletliHiz != 0 && spiderHiz != 0) && (spiderHiz > paletliHiz)) {
                System.out.println("Paletli robotun hizi spider robottan buyuk olmalidir!");
                return false;
            } else if ((tekerlekliHiz == 0 && paletliHiz != 0 && spiderHiz != 0) && (spiderHiz < paletliHiz)) {
                System.out.println("Hizlar sirali!");
                return true;
            } else {
                System.out.println("Tekerlekli robot paletli robottan, paletli robot spider robottan hizli olmalidir!");
                return false;
            }
        }

    }

    @Override
    public double gethiz() {
        return hiz;
    }

    @Override
    public void sethiz(double hiz) {
        this.hiz = hiz;
    }

    @Override
    public double yoluBul(int[] robotHareketleri) {
        /*Bu metod robotun yaptigi tum hareketleri toplar*/

        return (robotHareketleri[0] + robotHareketleri[1] + robotHareketleri[2] + robotHareketleri[3]) * 10;/*metre cinsinden oldugu inic *10 yaptik*/
    }

    @Override
    public double toplamSureyiBul(double yol, double hiz, double engelSuresi, int engeldenGecme) {
        double toplamSure = yol / hiz;
        return toplamSure + engelSuresi;
    }

}

class tekerlekli extends gezgin {

    private int tekerlekSayisi = 0;//tekerlekSayisi bu class'a ozgu bir ozelliktir.

    @Override
    public double engelGecmeSuresiBul(int motorSayisi, int engeldenGecme) {
        return (motorSayisi * (0.5)) * engeldenGecme;
    }

    public int getTekerlekSayisi() {
        return tekerlekSayisi;
    }

    public void setTekerlekSayisi(int tekerlekSayisi) {
        this.tekerlekSayisi = tekerlekSayisi;
    }

}

class paletli extends gezgin {

    private int paletSayisi = 0;

    @Override
    public double engelGecmeSuresiBul(int motorSayisi, int engeldenGecme) {
        return (motorSayisi * 3) * engeldenGecme;
    }

    public int getPaletSayisi() {
        return paletSayisi;
    }

    public void setPaletSayisi(int paletSayisi) {
        this.paletSayisi = paletSayisi;
    }

}

class spider extends gezgin {

    private int bacakSayisi = 0;

    public int getBacakSayisi() {
        return bacakSayisi;
    }

    public void setBacakSayisi(int bacakSayisi) {
        this.bacakSayisi = bacakSayisi;
    }
}

class manipulator extends robot implements IMANIPULATOR {

    private int kolUzunlugu; //manipulator interface
    private int kapasite;  //manipulator interface
    private double tasimaHizi;  //manipulator interface
    private double kolaGecmeSuresi;  //manipulator interface

    public boolean yukKapasitesiKarsilastir(int seriYukKapasitesi, int paralelYukKapasitesi) {
        if ((seriYukKapasitesi != 0 && paralelYukKapasitesi != 0) && seriYukKapasitesi > paralelYukKapasitesi) {
            System.out.println("Paralel robotun yuk kapasitesi seri robottan buyuk olmalidir.");
            return false;
        } else if ((seriYukKapasitesi != 0 && paralelYukKapasitesi != 0) && seriYukKapasitesi < paralelYukKapasitesi) {
            System.out.println("Yuk kapasitleri kontrolden geÁti!");
            return true;
        } else {
            System.out.println("Yuk kapasiteleri kontrolden gecti!");
            return false;
        }
    }

    @Override
    public int getkolUzunlugu() {
        return kolUzunlugu;
    }

    @Override
    public void setkolUzunlugu(int kolUzunlugu) {
        this.kolUzunlugu = kolUzunlugu;
    }

    @Override
    public int getkapasite() {
        return kapasite;
    }

    @Override
    public void setkapasite(int kapasite) {
        this.kapasite = kapasite;
    }

    @Override
    public double gettasimaHizi() {
        return tasimaHizi;
    }

    @Override
    public void settasimahHizi(double tasimaHizi) {
        this.tasimaHizi = tasimaHizi;
    }

    @Override
    public double getkolaGecmeSuresi() {
        return kolaGecmeSuresi;
    }

    @Override
    public void setkolaGecmeSuresi(double kolaGecmeSuresi) {
        this.kolaGecmeSuresi = kolaGecmeSuresi;
    }

    @Override
    public double yoluBul(int[] robotHareketleri) {
        return (robotHareketleri[0] + robotHareketleri[1] + robotHareketleri[2] + robotHareketleri[3]) * 10;
        /*metre cinsinden oldugu inic *10 yaptik*/
    }

    @Override
    public double toplamSureyiBul(double yol, double hiz, double kolaGecmeSuresi, int engeldenGecme) {
        double toplamSure = yol / hiz;
        return toplamSure + kolaGecmeSuresi;
    }

    public boolean tasimaHiziKarsilastir(int seriTasimaHizi, int paralelTasimaHizi) {
        /*Bu methoda gelen seriTasimaHizi ve paralelTasimaHizi*/
        if ((seriTasimaHizi != 0 && paralelTasimaHizi != 0) && seriTasimaHizi > paralelTasimaHizi) {
            System.out.println("HATA! Paralel robotun tasima hizi seri robotun tasima hizindan buyuk olmalidir.");
            return false;
        } else if ((seriTasimaHizi != 0 && paralelTasimaHizi != 0) && seriTasimaHizi < paralelTasimaHizi) {
            System.out.println("Tasima hizlari kontrolden gecti!");
            return true;
        } else {
            System.out.println("Tasima hizlari kontrolden gecti!");
            return false;
        }
    }

    ////manipulator icersine kol uzunlugu yetiyor mu 
    public boolean kolUzunluguYetiyorMu(double yol, int kolUzunlugu) {
        yol = yol / 10;
        if (kolUzunlugu > yol || kolUzunlugu == yol) {
            return true;// evet yetiyor
        } else {
            return false; // hayir yetmiyor
        }
    }

    public boolean tasimaHiziKarsilastir(double seriTasimaHizi, double paralelTasimaHizi) {
        /*Bu methoda gelen seriTasimaHizi ve paralelTasimaHizi*/
        if ((seriTasimaHizi != 0 && paralelTasimaHizi != 0) && seriTasimaHizi > paralelTasimaHizi) {
            System.out.println("HATA! Paralel robotun tasima hizi seri robotun tasima hizindan buyuk olmalidir.");
            return false;
        } else if ((seriTasimaHizi != 0 && paralelTasimaHizi != 0) && seriTasimaHizi < paralelTasimaHizi) {
            System.out.println("Tasima hizlari kontrolden gecti!");
            return true;
        } else {
            System.out.println("Tasima hizlari kontrolden gecti!");
            return false;
        }
    }

}

class seri extends manipulator {

}

class paralel extends manipulator {

}

class hibrit implements IGEZGIN, IMANIPULATOR {

    private double gecisSuresi;//gecisSuresi bu class'a ozgu bir ozelliktir.
    private double sabitlenmeSuresi;//sabitlenmeSuresi bu class'a ozgu bir ozelliktir.
    private int motorSayisi; //robot interface 
    private double yukMiktari; //robot interface 
    private String robotTipi; //robot interface 
    private double hiz; //gezgin interface 
    private int kolUzunlugu; //manipulator interface
    private int kapasite;  //manipulator interface
    private double tasimaHizi;  //manipulator interface
    private double kolaGecmeSuresi;  //manipulator interface

    @Override
    public double engelGecmeSuresiBul(int motorSayisi, int engeldenGecme) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getmotorSayisi() {
        return motorSayisi;
    }

    @Override
    public void setmotorSayisi(int motorSayisi) {
        this.motorSayisi = motorSayisi;
    }

    @Override
    public double getyukMiktari() {
        return yukMiktari;
    }

    @Override
    public void setyukMiktari(double yukMiktari) {
        this.yukMiktari = yukMiktari;
    }

    @Override
    public String getrobotTipi() {
        return robotTipi;
    }

    @Override
    public void setrobotTipi(String robotTipi) {
        this.robotTipi = robotTipi;
    }

    @Override
    public double gethiz() {
        return hiz;
    }

    @Override
    public void sethiz(double hiz) {
        this.hiz = hiz;
    }

    @Override
    public int getkolUzunlugu() {
        return kolUzunlugu;
    }

    @Override
    public void setkolUzunlugu(int kolUzunlugu) {
        this.kolUzunlugu = kolUzunlugu;
    }

    @Override
    public int getkapasite() {
        return kapasite;
    }

    @Override
    public void setkapasite(int kapasite) {
        this.kapasite = kapasite;
    }

    @Override
    public double gettasimaHizi() {
        return tasimaHizi;
    }

    @Override
    public void settasimahHizi(double tasimaHizi) {
        this.tasimaHizi = tasimaHizi;
    }

    @Override
    public double getkolaGecmeSuresi() {
        return kolaGecmeSuresi;
    }

    @Override
    public void setkolaGecmeSuresi(double kolaGecmeSuresi) {
        this.kolaGecmeSuresi = kolaGecmeSuresi;
    }

    public double yoluBul(int[] robotHareketleri, int[] hibritinKonumlari) {
        return (robotHareketleri[0] + robotHareketleri[1] + robotHareketleri[2] + robotHareketleri[3]) * 10 + (hibritinKonumlari[0] + hibritinKonumlari[1] + hibritinKonumlari[2] + hibritinKonumlari[3]) * 10;
    }

    @Override
    public double toplamSureyiBul(double yol, double hiz, double ekSure, int engeldenGecme) {
        return 0;
    }

    public boolean minSorgula(int minGezginHiz, int hibritHiz) {
        /*Burada minGezginHiz tekerlekli,paletli veya spider hizin min hizini, hibrit hiz ise hibrit robotun current hizini temsil eder*/
        if (minGezginHiz > hibritHiz) {
            return false; // min hizin altinda kalindi
        } else //min hizin uzerinde girildi
        {
            return true;
        }
    }

    public double toplamSureyiBul(int[] robotHareketleri, int[] hibritinKonumlari, double hiz, double ekSure, String robotTipi, int motorSayisi, int hareketliHiz, int engeldenGecme) {

        if (robotTipi.contains("tekerlekli")) {
            System.out.println("Hareketlli hiz:" + hareketliHiz);
            System.out.println("Secilen tasima hizi:" + hiz);
            System.out.println("tekerleklidesiniz");
            return (((robotHareketleri[0] + robotHareketleri[1] + robotHareketleri[2] + robotHareketleri[3]) * 10) / hareketliHiz) + ((hibritinKonumlari[0] + hibritinKonumlari[1] + hibritinKonumlari[2] + hibritinKonumlari[3]) * 10) / hiz + ((motorSayisi * (0.5) * engeldenGecme) + ekSure);
        } else if (robotTipi.contains("paletli")) {
            System.out.println("Hareketlli hiz:" + hareketliHiz);
            System.out.println("Secilen tasima hizi:" + hiz);
            System.out.println("paletlidesiniz");
            return (((robotHareketleri[0] + robotHareketleri[1] + robotHareketleri[2] + robotHareketleri[3]) * 10) / hareketliHiz) + ((hibritinKonumlari[0] + hibritinKonumlari[1] + hibritinKonumlari[2] + hibritinKonumlari[3]) * 10) / hiz + ((motorSayisi * (3) * engeldenGecme) + ekSure);
        } else {
            System.out.println("Hareketlli hiz:" + hareketliHiz);
            System.out.println("Secilen tasima hizi:" + hiz);
            System.out.println("spiderdasiniz");
            return (((robotHareketleri[0] + robotHareketleri[1] + robotHareketleri[2] + robotHareketleri[3]) * 10) / hareketliHiz) + ((hibritinKonumlari[0] + hibritinKonumlari[1] + hibritinKonumlari[2] + hibritinKonumlari[3]) * 10) / hiz + ekSure;
        }

    }

    ///hibrit icerisine 
    public boolean kolUzunluguYetiyorMu(int[] hibritinKonumlari, int kolUzunlugu) {
        int yol = 0;
        for (int i = 0; i < 4; i++) {
            yol += hibritinKonumlari[i];
        }
        if (kolUzunlugu > yol || kolUzunlugu == yol) {
            return true;// evet yetiyor
        } else {
            return false; // hayir yetmiyor
        }
    }

    @Override
    public boolean izgaradanCikiyorMu(int[] sonKonumlar, int[] robotHareketleri, int[] robotunKonumlari, int ileri, int geri, int saga, int sola) {
        /*bu method sonKonumlar dizisinin 0.indisi x eksenindeki son konumu, 1.indisi ise y eksenindeki son konumu getirir
        robotHareketleri dizisinin 0.indisi ileri yonlu hareketi 1.indisi geri yonlu hareketi, 2.indisi saga yonlu hareketi
        3.indisi sola yonlu hareketi getirir ve son konum 200 x 200 pikselleri icinde degilse izgaranin disindadir buna gore
        izgaranin disina cikarsa true, cikmazsa false deger dondurur.*/
        if (robotunKonumlari[0] + saga - sola > 20 || robotunKonumlari[0] + saga - sola < 0) {
            System.out.println("Robot" + " " + sola + " " + "birim sola gittigi icin izgaradan cikmistir.");
            System.out.println("Robot izgaradan ciktigi icin program sonlandi!");
            return true;  //evet izgara disina cikiyor
        } else if (robotunKonumlari[1] + ileri > 20) {
            System.out.println("Robot" + " " + ileri + " " + "birim ileri gittigi icin izgaradan cikmistir");
            System.out.println("Robot izgaradan ciktigi icin program sonlandi!");
            return true;
        } else if (robotunKonumlari[1] + ileri - geri > 20) {
            System.out.println("Robot" + " " + geri + " " + "birim geri gittigi icin izgaradan cikmistir");
            System.out.println("Robot izgaradan ciktigi icin program sonlandi!");
            return true;
        } else if (robotunKonumlari[0] + saga > 20) {
            System.out.println("Robot" + " " + saga + " " + "birim saga gittigi icin izgaradan cikmistir");
            System.out.println("Robot izgaradan ciktigi icin program sonlandi!");
            return true;
        } else {
            return false;
        }
    }

    @Override
    public double yoluBul(int[] robotHareketleri) {
        return 0;
    }

}// ./hibrit classinin sonu

public class Pencere extends JFrame {

    public Pencere(String string) throws HeadlessException {
        super(string);
    }

    public static void robotlariTanit() {
        System.out.println("      ------------------------------ ROBOTLAR ----------------------------");
        System.out.println("      | \t\t\t\t|\t\t\t\t |");
        System.out.println("      | \t\t\t\t|\t\t\t\t |");
        System.out.print("Gezgin Robotlar\t\t\t");
        System.out.print("Hibrit Robotlar\t\t\t");
        System.out.println("Manipulator Robotlar");
        System.out.println("");
        System.out.println("-Tekerlekli\t\t\t-Tekerlekli Seri\t\t\t-Seri");
        System.out.println("-Paletli\t\t\t-Tekerlekli Paralel\t\t\t-Paralel");
        System.out.println("-Spider\t\t\t\t-Paletli Seri\t\t\t");
        System.out.println("       \t\t\t\t-Paletli Paralel\t\t\t");
        System.out.println("       \t\t\t\t-Spider Seri\t\t\t");
        System.out.println("       \t\t\t\t-Spider Paralel\t\t\t");
        System.out.println("");
        System.out.println("Minimum hiz degerleri:\n  *Tekerlekli robot : 15m/s\n  *Paletli robot : 10m/s\n  *Spider robot : 5m/s");
    }

    public static int[] engelYerlestirX(int engelSayisi, int sayi, int boyut) {  //Kullanicidan alacagimiz engel sayisi adedince kullaniciya x degerini soracak bu x degerlerini engelX int dizine atip return ile onu diziyi dondurecek 
        Scanner scanner = new Scanner(System.in);
        int[] engelX = new int[boyut];                                            //engelX dizisini olusturduk
        int arttir = 0;                                                           //while dongumuzun devamini saglayacak kontrol degiskenimize 0 atayarak olusturduk

        while (arttir != engelSayisi) {
            System.out.println("Lutfen" + " " + sayi + ".engelin x degerini giriniz:");
            engelX[sayi - 1] = scanner.nextInt();                                   //girilen x degerini engelX dizisine atadik
            arttir++;                                                           //while dongumuzun donmesini saglayan degiskenimizin degeri 1 arttirdik
            sayi++;                                                             //dizimizin indisini 1 arttirdik
        }

        return engelX;                                                          //engelX dizisini sonradan kullacagimiz icin metodumuzdan dondurduk

    }

    public static int[] engelYerlestirY(int engelSayisi, int sayi, int boyut) //Kullanicidan alacagimiz engel sayisi adedince kullaniciya y degerini soracak bu y degerlerini engelY int dizine atip return ile onu diziyi dondurecek 
    {
        Scanner scanner = new Scanner(System.in);
        int[] engelY = new int[boyut];                                            //engelY dizisini olusturduk
        int arttir = 0;                                                           //while dongumuzun kac defa doncegini kontrol edecek degiskenimize 0 degerini vererek olusturduk

        while (arttir != engelSayisi) {
            System.out.println("Lutfen" + " " + sayi + ".engelin y degerini giriniz:");
            engelY[sayi - 1] = scanner.nextInt();                                   // girilen y degerlerini engelY dizisine atadik                   
            arttir++;                                                           //dongunun devam etmesi icin degiskenimizi 1 arttirdik
            sayi++;                                                             //dizinin indisini 1 arttirdik
        }

        return engelY;                                                          //engelY dizisini sonradan kullacagimiz icin metodumuzdan dondurduk

    }

    public static String[] robotlariAta(int robotSayisi) {
        Scanner scanner = new Scanner(System.in);
        String[] robotDizisi = new String[25];
        int arttir = 0;

        while (arttir != robotSayisi) {
            System.out.println("Lutfen" + " " + (arttir + 1) + ".robotun tipini giriniz:");
            robotDizisi[arttir] = scanner.nextLine();
            arttir++;
        }

        return robotDizisi;

    }

    public static int[] robotunIlkKonumlari() {
        Scanner scanner = new Scanner(System.in);
        int[] robotunKoordinatlari = new int[2];

        System.out.println("Izgara yerlestirmek istediginiz robotun x degerini giriniz:");
        robotunKoordinatlari[0] = scanner.nextInt();
        System.out.println("Izgara yerlestirmek istediginiz robotun y degerini giriniz:");
        robotunKoordinatlari[1] = scanner.nextInt();

        return robotunKoordinatlari;
    }

    public static int[] robotuHareketEttir(int ileri, int geri, int saga, int sola) {
        int[] hareketler = new int[4];
        hareketler[0] = ileri;
        hareketler[1] = geri;
        hareketler[2] = saga;
        hareketler[3] = sola;
        return hareketler;
    }

    public static int[] sonKonumuTut(int[] robotHareketleri, int[] robotunKonumlari) /*Bu methodda robotlarin ilk konumlarina robotlarin hareketleri eklenir ve 
        geri donus degeri olarak da degerler[] dizisini dondurur bu dizinin 0.indisi
        x eksenindeki son konumu, 1.indisi ise y eksenindeki son konumu dondurur.*/ {
        int ileri = robotHareketleri[0];
        int geri = robotHareketleri[1];
        int saga = robotHareketleri[2];
        int sola = robotHareketleri[3];
        int[] degerler = new int[2];
        int robotYonX = robotunKonumlari[0];
        int robotYonY = robotunKonumlari[1];
        if (ileri > geri && saga > sola) {
            degerler[0] = 10 * (saga - sola + robotYonX);
            degerler[1] = 10 * (ileri - geri + robotYonY);
        } else if (ileri > geri && saga < sola) {
            degerler[0] = 10 * (saga - sola + robotYonX);
            degerler[1] = (robotYonY - (geri - ileri)) * 10;
        } else if (ileri < geri && saga > sola) {
            degerler[0] = 10 * (robotYonX - (sola - saga));
            degerler[1] = (ileri - geri + robotYonY) * 10;
        } else if (ileri < geri && saga < sola) {
            degerler[0] = 10 * (robotYonX - (sola - saga));
            degerler[1] = (robotYonY - (geri - ileri)) * 10;
        }

        return degerler;
    }

    public static int[] hibritinHareketleriniAl() {
        int[] hareket = new int[4];
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nHibrit robotun sabitlendikten sonraki konumlarini giriniz");
        System.out.println("Hareket etmesini istemediginiz yon icin 0 degerini giriniz!!");
        System.out.print("İleri:");
        hareket[0] = scanner.nextInt();
        System.out.print("Geri:");
        hareket[1] = scanner.nextInt();
        System.out.print("Saga:");
        hareket[2] = scanner.nextInt();
        System.out.print("Sola:");
        hareket[3] = scanner.nextInt();
        return hareket;
    }

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        int engel, secilenYukMiktari = 0, robotSayisi, x, y, hangiRobot, secilenMotorSayisi = 0;
        int[] robotunKonumlari = new int[4];
        int[] robotHareketleri = new int[4];
        int[] konumlar = new int[2];
        int[] hibritinKonumlari = new int[4];
        int[] yukMiktari = new int[25];
        int[] motorSayisi = new int[25];
        int[] engelKonumlariX = new int[200];
        int[] engelKonumlariY = new int[200];
        int minTekerlekli = 15, minPaletli = 10, minSpider = 5, engelKontrolu, engeldenGecme = 0, ileriEngel, geriEngel, sagEngel, solEngel;
        double engelSuresi = 0, yol = 0, secilenTasimaHizi = 0;
        double[] tasimaHizi = new double[25];
        int tekerlekSayisi, bacakSayisi, paletSayisi, kolUzunlugu = 0, kolaGecmeSuresi = 0;
        int tekerlekliHiz = 0, paletliHiz = 0, spiderHiz = 0, seriYukKapasitesi = 0, paralelYukKapasitesi = 0;
        int sayi = 1, boyut = 400, say = 0; //engelYerlestirX,engelYerlestirY metodlarina gonderilecek degiskenleri olusturduk
        String[] robotIsimleri;
        String cevap, robotTipi, secilen;
        String[] hibritHareketli = new String[6], hibritHareketsiz = new String[6];
        int hibritSayac = 0, ileri = 0, geri = 0, saga = 0, sola = 0;
        int tsHiz = 0, tpHiz = 0, psHiz = 0, ppHiz = 0, ssHiz = 0, spHiz = 0;
        int tsKapasite = 0, tpKapasite = 0, psKapasite = 0, ppKapasite = 0, ssKapasite = 0, spKapasite = 0;
        /*degiskenleri tanimlerk ilk harf gezgin robotlarin bas harflerini, ikinci harf manipulator robotlarin bas harflerini tutar*/
        double seriTasimaHiz = 0, paralelTasimaHiz = 0, tsTasimaHiz = 0, tpTasimaHiz = 0, psTasimaHiz = 0, ppTasimaHiz = 0, ssTasimaHiz = 0, spTasimaHiz = 0;

        //objeler 
        robot robotRobot = new robot();
        gezgin gezginRobot = new gezgin();
        manipulator manipulatorRobot = new manipulator();
        hibrit hibritRobot = new hibrit();

        Grafik grafik = new Grafik();
        Pencere ekran = new Pencere("Robotlar Projesi");
        Pencere ekran2 = new Pencere("Robotlar Projesi");
        Pencere ekran3 = new Pencere("Robotlar Projesi");
        Pencere ekran4 = new Pencere("Robotlar Projesi");
        Pencere ekran5 = new Pencere("Robotlar Projesi");
        Pencere ekran6 = new Pencere("Robotlar Projesi");/*robotun kac izgara ileri gittigini gosterir*/
        Pencere ekran7 = new Pencere("Robotlar Projesi");/*robotun kac izgara geri gittigini gosterir*/
        Pencere ekran8 = new Pencere("Robotlar Projesi");/*robotun kac izgara saga gittigini gosterir*/

        engelYerlestir engelYerlestir = new engelYerlestir(); //engelYerlestir classindan nesne olusturduk
        robotunIlkKonumu ilkKonum = new robotunIlkKonumu();   //robotunIlkKonumu classindan nesne olusturduk
        robotunSonKonumu sonKonum = new robotunSonKonumu();
        hibritinHareketi hibritKonum = new hibritinHareketi();
        robotIleri ileriHareket = new robotIleri();
        robotGeri geriHareket = new robotGeri();
        robotSaga sagaHareket = new robotSaga();

        ekran.setVisible(true);  //JFramemin gozukmesi icin girilmis bir komut
        ekran.setResizable(true); //Pencere boyutu degistirilebilir nitelikte olması için yazildi
        ekran.setSize(700, 700);
        ekran.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ekran.add(grafik);

        robotlariTanit();
        System.out.println("\nProjemize hosgeldiniz..\n");
        System.out.println("Tanimlanacak robot sayisi:");
        robotSayisi = scanner.nextInt();
        scanner.nextLine();
        String[] robotTipi2 = new String[robotSayisi];/*bu dizi robot tiplerini tutup sonrasinda secilen siraya gore obje olusturmak icin tanimlanmistir*/
        robotIsimleri = robotlariAta(robotSayisi);
        for (int i = 0; i < robotSayisi; i++) {
            /*bu blok icerisinde sayisi ve turleri belirlenen robotlarin tipleri
            alinarak bir diziye kaydedilir bu dizi daha sonra secilen robotun tipine 
            gore objesini olusturmada yardimci olacaktir. Dikkat! bu dizi robotIsimleri
            dizisi ile karistirilmamalidir, robotIsimleri dizisi farkli islem yapmaktadir.*/
            if (robotIsimleri[i].equalsIgnoreCase("hibrit robot")) {

                System.out.print("Hibrit robot icin hareketli kismi girin: ");
                hibritHareketli[hibritSayac] = scanner.nextLine();
                System.out.print("Hibrit robot icin hareketsiz kismi girin: ");
                hibritHareketsiz[hibritSayac] = scanner.nextLine();
                //robotTipi2[i] = "hibrit robot";
                robotTipi2[i] = hibritHareketli[hibritSayac] + " " + hibritHareketsiz[hibritSayac] + " robot";
                /*hibrit robotlarda isim birletirildi*/
                hibritSayac++;
            } else if (robotIsimleri[i].equalsIgnoreCase("gezgin robot")) {
                System.out.print("Gezgin robotun tipini girin: ");
                robotTipi2[i] = scanner.nextLine();
            } else if (robotIsimleri[i].equalsIgnoreCase("manipulator robot")) {
                System.out.print("Manipulator robotun tipini girin: ");
                robotTipi2[i] = scanner.nextLine();
            }
        }// ./robot tipleri alma
        hibritSayac = 0;
        for (int i = 0; i < robotSayisi; i++) {
            if (robotTipi2[i].equalsIgnoreCase("tekerlekli robot")) {
                System.out.print("Tekerlekli robotun hizi:");
                tekerlekliHiz = scanner.nextInt();
                System.out.print(robotTipi2[i] + "un yuk miktari:");
                yukMiktari[i] = scanner.nextInt();
                System.out.print(robotTipi2[i] + "un motor sayisini giriniz:");
                motorSayisi[i] = scanner.nextInt();
                scanner.nextLine();
                tasimaHizi[i] = 0;
            } else if (robotTipi2[i].equalsIgnoreCase("paletli robot")) {
                System.out.print("Paletli robotun hizi:");
                paletliHiz = scanner.nextInt();
                System.out.print(robotTipi2[i] + "un yuk miktari:");
                yukMiktari[i] = scanner.nextInt();
                System.out.print(robotTipi2[i] + "un motor sayisini giriniz:");
                motorSayisi[i] = scanner.nextInt();
                scanner.nextLine();
                tasimaHizi[i] = 0;
            } else if (robotTipi2[i].equalsIgnoreCase("spider robot")) {
                System.out.print("Spider robotun hizi:");
                spiderHiz = scanner.nextInt();
                System.out.print(robotTipi2[i] + "un yuk miktari:");
                yukMiktari[i] = scanner.nextInt();
                System.out.print(robotTipi2[i] + " un motor sayisini giriniz:");
                motorSayisi[i] = scanner.nextInt();
                scanner.nextLine();
                tasimaHizi[i] = 0;
            } else if (robotTipi2[i].equalsIgnoreCase("seri robot")) {
                System.out.print("Seri robotun yuk kapasitesi:");
                seriYukKapasitesi = scanner.nextInt();
                scanner.nextLine();
                System.out.print(robotTipi2[i] + "un yuk miktari:");
                yukMiktari[i] = scanner.nextInt();
                System.out.print(robotTipi2[i] + "un motor sayisini giriniz:");
                motorSayisi[i] = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Seri robotun yuku tasima hizini yaziniz:");
                tasimaHizi[i] = scanner.nextDouble();
                scanner.nextLine();
            } else if (robotTipi2[i].equalsIgnoreCase("paralel robot")) {
                System.out.print("Paralel robotun yuk kapasitesi:");
                paralelYukKapasitesi = scanner.nextInt();
                System.out.print(robotTipi2[i] + "un yuk miktari:");
                yukMiktari[i] = scanner.nextInt();
                System.out.print(robotTipi2[i] + "un motor sayisini giriniz:");
                motorSayisi[i] = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Paralel robotun yuku tasima hizini yaziniz:");
                tasimaHizi[i] = scanner.nextDouble();
                scanner.nextLine();
            } else if (robotTipi2[i].equalsIgnoreCase("tekerlekli seri robot")) {
                System.out.print(robotTipi2[i] + ", " + hibritHareketli[hibritSayac] + " hizi:");
                tsHiz = scanner.nextInt();
                boolean tamamMi = hibritRobot.minSorgula(minTekerlekli, tsHiz);
                if (tamamMi == true); else {
                    while (tamamMi == false) {
                        System.out.println("HATA!" + robotTipi2[i] + " hareketli kısmı minimum degerin altinda kaldi.");
                        System.out.print(robotTipi2[i] + ", " + hibritHareketli[hibritSayac] + " hizi:");
                        tsHiz = scanner.nextInt();
                        tamamMi = hibritRobot.minSorgula(minTekerlekli, tsHiz);
                    }
                }
                System.out.print(robotTipi2[i] + ", " + hibritHareketsiz[hibritSayac] + " yuk kapasitesi:");
                tsKapasite = scanner.nextInt();
                System.out.println(robotTipi2[i] + "un yuk miktari:");
                yukMiktari[i] = scanner.nextInt();
                System.out.println(robotTipi2[i] + "un motor sayisini giriniz:");
                motorSayisi[i] = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Seri bolumunun yuku tasima hizini yaziniz:");
                tasimaHizi[i] = scanner.nextDouble();
                scanner.nextLine();
                hibritSayac++;
            } else if (robotTipi2[i].equalsIgnoreCase("tekerlekli paralel robot")) {
                System.out.print(robotTipi2[i] + ", " + hibritHareketli[hibritSayac] + " hizi:");
                tpHiz = scanner.nextInt();
                boolean tamamMi = hibritRobot.minSorgula(minTekerlekli, tpHiz);
                if (tamamMi == true); else {
                    while (tamamMi == false) {
                        System.out.println("HATA!" + robotTipi2[i] + " hareketli kısmı minimum degerin altinda kaldi.");
                        System.out.print(robotTipi2[i] + ", " + hibritHareketli[hibritSayac] + " hizi:");
                        tpHiz = scanner.nextInt();
                        tamamMi = hibritRobot.minSorgula(minTekerlekli, tpHiz);
                    }
                }
                System.out.print(robotTipi2[i] + ", " + hibritHareketsiz[hibritSayac] + " yuk kapasitesi:");
                tpKapasite = scanner.nextInt();
                System.out.println(robotTipi2[i] + "un yuk miktari:");
                yukMiktari[i] = scanner.nextInt();
                System.out.println(robotTipi2[i] + "un motor sayisini giriniz:");
                motorSayisi[i] = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Paralel bolumunun yuku tasima hizini yaziniz:");
                tasimaHizi[i] = scanner.nextDouble();
                scanner.nextLine();
                hibritSayac++;
            } else if (robotTipi2[i].equalsIgnoreCase("paletli seri robot")) {
                System.out.print(robotTipi2[i] + ", " + hibritHareketli[hibritSayac] + " hizi:");
                psHiz = scanner.nextInt();
                boolean tamamMi = hibritRobot.minSorgula(minPaletli, psHiz);
                if (tamamMi == true); else {
                    while (tamamMi == false) {
                        System.out.println("HATA!" + robotTipi2[i] + " hareketli kısmı minimum degerin altinda kaldi.");
                        System.out.print(robotTipi2[i] + ", " + hibritHareketli[hibritSayac] + " hizi:");
                        psHiz = scanner.nextInt();
                        tamamMi = hibritRobot.minSorgula(minPaletli, psHiz);
                    }
                }
                System.out.print(robotTipi2[i] + ", " + hibritHareketsiz[hibritSayac] + " yuk kapasitesi:");
                psKapasite = scanner.nextInt();
                System.out.println(robotTipi2[i] + "un yuk miktari:");
                yukMiktari[i] = scanner.nextInt();
                System.out.println(robotTipi2[i] + "un motor sayisini giriniz:");
                motorSayisi[i] = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Seri bolumunun yuku tasima hizini yaziniz:");
                tasimaHizi[i] = scanner.nextDouble();
                scanner.nextLine();
                hibritSayac++;
            } else if (robotTipi2[i].equalsIgnoreCase("paletli paralel robot")) {
                System.out.print(robotTipi2[i] + ", " + hibritHareketli[hibritSayac] + " hizi:");
                ppHiz = scanner.nextInt();
                boolean tamamMi = hibritRobot.minSorgula(minPaletli, ppHiz);
                if (tamamMi == true); else {
                    while (tamamMi == false) {
                        System.out.println("HATA!" + robotTipi2[i] + " hareketli kısmı minimum degerin altinda kaldi.");
                        System.out.print(robotTipi2[i] + ", " + hibritHareketli[hibritSayac] + " hizi:");
                        ppHiz = scanner.nextInt();
                        tamamMi = hibritRobot.minSorgula(minPaletli, ppHiz);
                    }
                }
                System.out.print(robotTipi2[i] + ", " + hibritHareketsiz[hibritSayac] + " yuk kapasitesi:");
                ppKapasite = scanner.nextInt();
                System.out.println(robotTipi2[i] + "un yuk miktari:");
                yukMiktari[i] = scanner.nextInt();
                System.out.println(robotTipi2[i] + "un motor sayisini giriniz:");
                motorSayisi[i] = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Paralel bolumunun yuku tasima hizini yaziniz:");
                tasimaHizi[i] = scanner.nextDouble();
                scanner.nextLine();
                hibritSayac++;
            } else if (robotTipi2[i].equalsIgnoreCase("spider seri robot")) {
                System.out.print(robotTipi2[i] + ", " + hibritHareketli[hibritSayac] + " hizi:");
                ssHiz = scanner.nextInt();
                boolean tamamMi = hibritRobot.minSorgula(minSpider, ssHiz);
                if (tamamMi == true); else {
                    while (tamamMi == false) {
                        System.out.println("HATA!" + robotTipi2[i] + " hareketli kısmı minimum degerin altinda kaldi.");
                        System.out.print(robotTipi2[i] + ", " + hibritHareketli[hibritSayac] + " hizi:");
                        ssHiz = scanner.nextInt();
                        tamamMi = hibritRobot.minSorgula(minSpider, ssHiz);
                    }
                }
                System.out.print(robotTipi2[i] + ", " + hibritHareketsiz[hibritSayac] + " yuk kapasitesi:");
                ssKapasite = scanner.nextInt();
                System.out.println(robotTipi2[i] + "un yuk miktari:");
                yukMiktari[i] = scanner.nextInt();
                System.out.println(robotTipi2[i] + "un motor sayisini giriniz:");
                motorSayisi[i] = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Seri bolumunun yuku tasima hizini yaziniz:");
                tasimaHizi[i] = scanner.nextDouble();
                scanner.nextLine();
                hibritSayac++;
            } else if (robotTipi2[i].equalsIgnoreCase("spider paralel robot")) {
                System.out.print(robotTipi2[i] + ", " + hibritHareketli[hibritSayac] + " hizi:");
                spHiz = scanner.nextInt();
                boolean tamamMi = hibritRobot.minSorgula(minSpider, spHiz);
                if (tamamMi == true); else {
                    while (tamamMi == false) {
                        System.out.println("HATA!" + robotTipi2[i] + " hareketli kısmı minimum degerin altinda kaldi.");
                        System.out.print(robotTipi2[i] + ", " + hibritHareketli[hibritSayac] + " hizi:");
                        spHiz = scanner.nextInt();
                        tamamMi = hibritRobot.minSorgula(minSpider, spHiz);
                    }
                }
                System.out.print(robotTipi2[i] + ", " + hibritHareketsiz[hibritSayac] + " yuk kapasitesi:");
                spKapasite = scanner.nextInt();
                System.out.println(robotTipi2[i] + "un yuk miktari:");
                yukMiktari[i] = scanner.nextInt();
                System.out.println(robotTipi2[i] + "un motor sayisini giriniz:");
                motorSayisi[i] = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Paralel bolumunun yuku tasima hizini yaziniz:");
                tasimaHizi[i] = scanner.nextDouble();
                scanner.nextLine();
                hibritSayac++;
            }
        }

        for (int i = 0; i < robotSayisi; i++) {
            if (robotTipi2[i].equals("tekerlekli robot")) {
                while (minTekerlekli > tekerlekliHiz) {
                    System.out.println("HATA! Tekerlekli Robot icin minimum hiz(15m/s) sınırının altında kalındı!");
                    System.out.print("Tekerlekli robotun hizi:");
                    tekerlekliHiz = scanner.nextInt();
                }
            }
            if (robotTipi2[i].equals("paletli robot")) {
                while (minPaletli > paletliHiz) {
                    System.out.println("HATA! Paletli Robot icin minimum hiz(10m/s) sınırının altında kalındı!");
                    System.out.print("Paletli robotun hizi:");
                    paletliHiz = scanner.nextInt();
                }
            }
            if (robotTipi2[i].equals("spider robot")) {
                while (minSpider > spiderHiz) {
                    System.out.println("HATA! Spider Robot icin minimum hiz(5m/s) sınırının altında kalındı!");
                    System.out.print("Spider robotun hizi:");
                    spiderHiz = scanner.nextInt();
                }
            }
        }

        for (int i = 0; i < robotSayisi; i++) {
            if (robotTipi2[i].equals("seri robot")) {
                while (yukMiktari[i] > seriYukKapasitesi) {
                    System.out.println("HATA! Seri Robot icin yuk kapasitesi asildi!");
                    System.out.print("Seri robotun yuk kapasitesi:");
                    seriYukKapasitesi = scanner.nextInt();
                }
            }
            if (robotTipi2[i].equals("paralel robot")) {
                if (yukMiktari[i] > paralelYukKapasitesi) {
                    while (yukMiktari[i] > paralelYukKapasitesi) {
                        System.out.println("HATA! Paralel Robot icin yuk kapasitesi asildi!");
                        System.out.print("Paralel robotun yuk kapasitesi:");
                        paralelYukKapasitesi = scanner.nextInt();
                    }
                }
            }
        }

        /*hicbirine hiz girilmediyse veya sadece birtanesine girildiyse 
        karsilastirmaya gitmez*/
        if ((tekerlekliHiz == 0 && paletliHiz == 0 && spiderHiz == 0)); else if ((tekerlekliHiz != 0 && paletliHiz == 0 && spiderHiz == 0)); else if ((tekerlekliHiz == 0 && paletliHiz != 0 && spiderHiz == 0)); else if ((tekerlekliHiz == 0 && paletliHiz == 0 && spiderHiz != 0)); else {
            boolean tamamMi = gezginRobot.hizlariKarsilastir(tekerlekliHiz, paletliHiz, spiderHiz);
            if (tamamMi == true); else {
                while (tamamMi == false) {
                    for (int i = 0; i < robotSayisi; i++) {
                        if (robotTipi2[i].equals("tekerlekli robot")) {
                            System.out.println("Tekerlekli robotun hizi:");
                            tekerlekliHiz = scanner.nextInt();
                        } else if (robotTipi2[i].equals("paletli robot")) {
                            System.out.println("Paletli robotun hizi:");
                            paletliHiz = scanner.nextInt();
                        } else if (robotTipi2[i].equals("spider robot")) {
                            System.out.println("Spider robotun hizi:");
                            spiderHiz = scanner.nextInt();
                            scanner.nextLine();
                        }
                    }//./for dongusu 
                    tamamMi = gezginRobot.hizlariKarsilastir(tekerlekliHiz, paletliHiz, spiderHiz);
                    if (tamamMi == true) {
                        break;
                    }
                }
            }
        }
        if (seriYukKapasitesi != 0 && paralelYukKapasitesi != 0) {

            boolean tamamMi = manipulatorRobot.yukKapasitesiKarsilastir(seriYukKapasitesi, paralelYukKapasitesi);
            if (tamamMi == true); else {
                while (tamamMi == false) {
                    for (int i = 0; i < robotSayisi; i++) {
                        if (robotTipi2[i].equals("seri robot")) {
                            System.out.print("Seri robotun yuk kapasitesi:");
                            seriYukKapasitesi = scanner.nextInt();
                            scanner.nextLine();
                        } else if (robotTipi2[i].equals("paralel robot")) {
                            System.out.print("Paralel robotun yuk kapasitesi:");
                            paralelYukKapasitesi = scanner.nextInt();
                            scanner.nextLine();
                        }
                    }// ./for dongusu bitis  
                    System.out.println("seri :" + seriYukKapasitesi);
                    System.out.println("paralel :" + paralelYukKapasitesi);
                    tamamMi = manipulatorRobot.yukKapasitesiKarsilastir(seriYukKapasitesi, paralelYukKapasitesi);
                    if (tamamMi == true) {
                        break;
                    }
                }// ./false oldugu surece tekrar sor bitis
            }
        }

        if (seriTasimaHiz != 0 && paralelTasimaHiz != 0) {
            boolean tamamMi = manipulatorRobot.tasimaHiziKarsilastir(seriTasimaHiz, paralelTasimaHiz);
            if (tamamMi == true); else {
                while (tamamMi == false) {
                    for (int i = 0; i < robotSayisi; i++) {
                        if (robotTipi2[i].equals("seri robot")) {
                            System.out.print("Seri robotun tasima hizi:");
                            tasimaHizi[i] = scanner.nextInt();
                            seriTasimaHiz = tasimaHizi[i];
                            scanner.nextLine();
                        } else if (robotTipi2[i].equals("paralel robot")) {
                            System.out.print("Paralel robotun tasima hizi:");
                            tasimaHizi[i] = scanner.nextInt();
                            paralelTasimaHiz = tasimaHizi[i];
                            scanner.nextLine();
                        }
                    }// ./for dongusu bitis  
                    System.out.println("seri :" + seriTasimaHiz);
                    System.out.println("paralel :" + paralelTasimaHiz);
                    tamamMi = manipulatorRobot.tasimaHiziKarsilastir(seriTasimaHiz, paralelTasimaHiz);
                    if (tamamMi == true) {
                        break;
                    }
                }// ./false oldugu surece tekrar sor bitis
            }
        }

//        String problemNe = hibritHareketli + " " + hibritHareketsiz;
        /*Hibrit robutun hareketli ve haraketsiz kismi birlestirilip string degiskene atildi */
//        System.out.println("\nHibritin tam adi:" + problemNe);
        System.out.println("Hangi siradaki robot hareket ettirilecek: ");
        hangiRobot = scanner.nextInt();//kacinci sirada girdigi robot hareket edecek 
        scanner.nextLine();
        if (hangiRobot > robotSayisi || hangiRobot < 1) {
            System.out.println("HATA! " + hangiRobot + ". robot yoktur!");
            System.out.println("Program sonlandiriliyor...");
            System.out.println("Cikmak icin herhangi bir tusa basin.");
            scanner.next();
            System.exit(0);
        }
        robotTipi = robotTipi2[hangiRobot - 1];/*indexin 1 eksigi olmasi gerek cunku yerlestrimeye 0.indexten baslandi.*/
        secilenYukMiktari = yukMiktari[hangiRobot - 1];
        secilenMotorSayisi = motorSayisi[hangiRobot - 1];
        secilenTasimaHizi = tasimaHizi[hangiRobot - 1];

        ilkKonum.robotTipi = robotTipi;
        /* izgaraya yerlestirilmek istenilen robotu izgarada gosterilmesi icin robotunIlkKonumu adli classa gonderdik */
        ileriHareket.robotTipi = robotTipi;
        geriHareket.robotTipi = robotTipi;
        sagaHareket.robotTipi = robotTipi;
        sonKonum.robotTipi = robotTipi;
        /* izgaraya yerlestirilmek istenilen robotu izgarada gosterilmesi icin robotunSonKonumu adli classa gonderdik */
        hibritKonum.robotTipi = robotTipi;
        /* izgaraya yerlestirilmek istenilen robotu izgarada gosterilmesi icin hibritinHareketi adli classa gonderdik */

        System.out.println("Izgarada engelin bulunmasini ister misiniz ? ");
        cevap = scanner.nextLine();
        ilkKonum.yanit = cevap;
        ileriHareket.yanit = cevap;/*robotIleri classina cevapi gonderdik*/
        geriHareket.yanit = cevap;
        sagaHareket.yanit = cevap;
        sonKonum.yanit = cevap;
        hibritKonum.yanit = cevap;

        if ("evet".equals(cevap)) {
            System.out.println("Izgaraya kac adet engel yerlestirmek istersiniz?");
            engel = scanner.nextInt();
            engelYerlestir.engelSayisi = engel; //engelYerlestir classinin engelSayisi degiskenine kullanican aldigimiz engel sayisini gonderdik
            ilkKonum.engelSayisi = engel;       //ilkKonum classinin engelSayisi degiskenine kullanicidan aldigimiz engel sayisini gonderdik
            ileriHareket.engelSayisi = engel;
            /*robotIleri classina engelSayisini gonderdik*/
            geriHareket.engelSayisi = engel;
            sagaHareket.engelSayisi = engel;
            sonKonum.engelSayisi = engel;
            hibritKonum.engelSayisi = engel;
            engelKonumlariX = engelYerlestirX(engel, sayi, boyut);       //engellerin x degerlerini engelKonumlariX dizisine gonderdik
            engelKonumlariY = engelYerlestirY(engel, sayi, boyut);       //engellerin y degerlerini engelKonumlariY dizisine gonderdik
            engelYerlestir.engelX = engelKonumlariX;   //engelYerlestirX metodunun dondurdugu engelX i engelYerlestir classinin icinde bulunan engelX int dizisine atadik
            engelYerlestir.engelY = engelKonumlariY;   //engelYerlestirY metodunun dondurdugu engelY i engelYerlestir classinin icinde bulunan engelY int dizisine atadik
            ilkKonum.engelX = engelYerlestir.engelX;                      //engelYerlestirX metodunun dondurdugu engelX i robotunIlkKonumu classinin icinde bulunan engelX int dizisine atadik
            ilkKonum.engelY = engelYerlestir.engelY;                      //engelYerlestirX metodunun dondurdugu engelY i robotunIlkKonumu classinin icinde bulunan engelY int dizisine atadik
            ileriHareket.engelX = engelYerlestir.engelX;
            ileriHareket.engelY = engelYerlestir.engelY;
            geriHareket.engelX = engelYerlestir.engelX;
            geriHareket.engelY = engelYerlestir.engelY;
            sagaHareket.engelX = engelYerlestir.engelX;
            sagaHareket.engelY = engelYerlestir.engelY;
            sonKonum.engelX = engelYerlestir.engelX;
            sonKonum.engelY = engelYerlestir.engelY;
            hibritKonum.engelX = engelYerlestir.engelX;
            hibritKonum.engelY = engelYerlestir.engelY;
            ekran.setVisible(false);                                      //1.pencereyi kapattik
            ekran2.setVisible(true);  //JFramemin gozukmesi icin girilmis bir komut
            ekran2.setResizable(true); //Pencere boyutu degistirilebilir nitelikte olması için yazıldı
            ekran2.setSize(700, 700);
            ekran2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ekran2.add(engelYerlestir);

            System.out.println("");
            System.out.println(robotTipi.toUpperCase() + " " + "izgaraya cikariliyor..\n");
            /*kullaniciya hangi robotun izgaraya cikarilacagi bilgisi yazdirildi */
            robotunKonumlari = robotunIlkKonumlari();
            /* robotunIlkKonumlari adli metodun dondurdugu robotun yer bilgisi robotunKonumlari adli int diziye atandi */
            ilkKonum.robotYonX = robotunKonumlari[0];
            /* robotunKonumlari adli metodun dondurdugu dizinin 0. indisi x degeri gosterir */
            ilkKonum.robotYonY = robotunKonumlari[1];
            /* robotunKonumlari adli metodun dondurdugu dizinin 1. indisi y degeri gosterir */
            ileriHareket.robotYonX = robotunKonumlari[0];
            ileriHareket.robotYonY = robotunKonumlari[1];
            geriHareket.robotYonX = robotunKonumlari[0];
            geriHareket.robotYonY = robotunKonumlari[1];
            /*robotGeri classinin son y konumunu hYSon degiskenine gonderdik*/
            sagaHareket.robotYonX = robotunKonumlari[0];
            sagaHareket.robotYonY = robotunKonumlari[1];
            sonKonum.robotYonX = robotunKonumlari[0];
            sonKonum.robotYonY = robotunKonumlari[1];
            hibritKonum.robotYonX = robotunKonumlari[0];
            hibritKonum.robotYonY = robotunKonumlari[1];
            ekran2.setVisible(false);
            /* 2. pencere kapatildi */
            ekran3.setVisible(true);  //JFramemin gozukmesi icin girilmis bir komut
            ekran3.setResizable(true); //Pencere boyutu degistirilebilir nitelikte olması için yazıldı
            ekran3.setSize(700, 700);
            ekran3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ekran3.add(ilkKonum);
            /*3. pencenin grafigi ekrana bastirilmak uzere cagirildi  */

            System.out.println("\nLutfen robotun hareket yonlerini giriniz:");
            System.out.println("Hareket etmesini istemediginiz yon icin 0 degerini giriniz!!");
            System.out.print("Ileri:");
            ileri = scanner.nextInt();
            ileriHareket.hIleri = ileri;/*robotIleri classina ileri degerini gonderdik*/
            boolean izgaradanCikiyorMu = robotRobot.izgaradanCikiyorMu(konumlar, robotHareketleri, robotunKonumlari, ileri, geri, saga, sola);

            if (izgaradanCikiyorMu) {
                if ("tekerlekli robot".equals(robotTipi)) {
                    tekerlekli kontrol1 = new tekerlekli();
                    yol = ileri * 10;
                    System.out.println("Toplam sureniz:" + kontrol1.toplamSureyiBul(yol, tekerlekliHiz, engelSuresi, engeldenGecme));
                } else if ("paletli robot".equals(robotTipi)) {
                    paletli kontrol2 = new paletli();
                    yol = ileri * 10;
                    System.out.println("Toplam sureniz:" + kontrol2.toplamSureyiBul(yol, paletliHiz, engelSuresi, engeldenGecme));
                } else if ("seri robot".equals(robotTipi) || "paralel robot".equals(robotTipi)) {
                    seri kontrol4 = new seri();
                    yol = ileri * 10;
                    System.out.println("Toplam sureniz:" + kontrol4.toplamSureyiBul(yol, secilenTasimaHizi, kolaGecmeSuresi, engeldenGecme));
                } else if ("tekerlekli seri robot".equals(robotTipi)) {
                    tekerlekli kontrol3 = new tekerlekli();
                    yol = ileri * 10;
                    System.out.println("Toplam sureniz:" + kontrol3.toplamSureyiBul(yol, tsHiz, engelSuresi, engeldenGecme));
                } else if ("tekerlekli paralel robot".equals(robotTipi)) {
                    tekerlekli kontrol3 = new tekerlekli();
                    yol = ileri * 10;
                    System.out.println("Toplam sureniz:" + kontrol3.toplamSureyiBul(yol, tpHiz, engelSuresi, engeldenGecme));
                } else if ("paletli seri robot".equals(robotTipi)) {
                    tekerlekli kontrol3 = new tekerlekli();
                    yol = ileri * 10;
                    System.out.println("Toplam sureniz:" + kontrol3.toplamSureyiBul(yol, psHiz, engelSuresi, engeldenGecme));
                } else if ("paletli paralel robot".equals(robotTipi)) {
                    tekerlekli kontrol3 = new tekerlekli();
                    yol = ileri * 10;
                    System.out.println("Toplam sureniz:" + kontrol3.toplamSureyiBul(yol, ppHiz, engelSuresi, engeldenGecme));
                }

                System.out.println("Programdan cikmak icin bir tusa basin");
                scanner.nextLine();
                scanner.nextLine();
                System.exit(0);
            }
            engelKontrolu = robotRobot.engelleKarsilastiMiIleri(engelKonumlariX, engelKonumlariY, ileri, robotTipi, robotunKonumlari);
            if (engelKontrolu == -1) {
                System.out.println("UYARI! Spider robot ileri hareketinde engelle karsilastigi icin hareket edemez ve diger komutlar calismamaktadir..");
                System.out.print("Programdan cikmak icin bir tusa basin");
                scanner.nextLine();
                scanner.nextLine();
                System.exit(0);
            }
            ileriEngel = engelKontrolu;
            /*ileri gittiginde kac tane engelle karsilastigini ileriEngel degiskenine atadik*/
            ekran3.setVisible(false);/* 3.pencere kapatildi */
            ekran6.setVisible(true);  //JFramemin gozukmesi icin girilmis bir komut
            ekran6.setResizable(true); //Pencere boyutu degistirilebilir nitelikte olması için yazıldı
            ekran6.setSize(700, 700);
            ekran6.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ekran6.add(ileriHareket);
            System.out.print("Geri:");
            geri = scanner.nextInt();
            geriHareket.hGeri = geri;/*robotGeri classina geri degerini gonderdik*/
            geriHareket.hYson = robotunKonumlari[1] + ileri;
            izgaradanCikiyorMu = robotRobot.izgaradanCikiyorMu(konumlar, robotHareketleri, robotunKonumlari, ileri, geri, saga, sola);
            if (izgaradanCikiyorMu) {
                if ("tekerlekli robot".equals(robotTipi)) {
                    tekerlekli kontrol1 = new tekerlekli();
                    yol = (ileri + geri) * 10;
                    System.out.println("Toplam sureniz:" + kontrol1.toplamSureyiBul(yol, tekerlekliHiz, engelSuresi, engeldenGecme));
                } else if ("paletli robot".equals(robotTipi)) {
                    paletli kontrol2 = new paletli();
                    yol = (ileri + geri) * 10;
                    System.out.println("Toplam sureniz:" + kontrol2.toplamSureyiBul(yol, paletliHiz, engelSuresi, engeldenGecme));
                } else if ("seri robot".equals(robotTipi) || "paralel robot".equals(robotTipi)) {
                    seri kontrol4 = new seri();
                    yol = (ileri + geri) * 10;
                    System.out.println("Toplam sureniz:" + kontrol4.toplamSureyiBul(yol, secilenTasimaHizi, kolaGecmeSuresi, engeldenGecme));
                } else if ("tekerlekli seri robot".equals(robotTipi)) {
                    tekerlekli kontrol3 = new tekerlekli();
                    yol = (ileri + geri) * 10;
                    System.out.println("Toplam sureniz:" + kontrol3.toplamSureyiBul(yol, tsHiz, engelSuresi, engeldenGecme));
                } else if ("tekerlekli paralel robot".equals(robotTipi)) {
                    tekerlekli kontrol3 = new tekerlekli();
                    yol = (ileri + geri) * 10;
                    System.out.println("Toplam sureniz:" + kontrol3.toplamSureyiBul(yol, tpHiz, engelSuresi, engeldenGecme));
                } else if ("paletli seri robot".equals(robotTipi)) {
                    tekerlekli kontrol3 = new tekerlekli();
                    yol = (ileri + geri) * 10;
                    System.out.println("Toplam sureniz:" + kontrol3.toplamSureyiBul(yol, psHiz, engelSuresi, engeldenGecme));
                } else if ("paletli paralel robot".equals(robotTipi)) {
                    tekerlekli kontrol3 = new tekerlekli();
                    yol = (ileri + geri) * 10;
                    System.out.println("Toplam sureniz:" + kontrol3.toplamSureyiBul(yol, ppHiz, engelSuresi, engeldenGecme));
                }
                System.out.println("Programdan cikmak icin bir tusa basin.");
                scanner.nextLine();
                scanner.nextLine();
                System.exit(0);
            }
            engelKontrolu = robotRobot.engelleKarsilastiMiGeri(engelKonumlariX, engelKonumlariY, ileri, geri, robotTipi, robotunKonumlari);
            if (engelKontrolu == -1) {
                System.out.println("UYARI! Spider robot geri hareketinde engelle karsilastigi icin hareket edemez ve diger komutlar calismamaktadir..");
                System.out.println("Programdan cikmak icin bir tusa basin");
                scanner.nextLine();
                scanner.nextLine();
                System.exit(0);
            }
            geriEngel = engelKontrolu;/*geri gittiginde kac tane engelle karsilastigini geriEngel degiskenine atadik*/
            ekran6.setVisible(false);
            ekran7.setVisible(true);  //JFramemin gozukmesi icin girilmis bir komut
            ekran7.setResizable(true); //Pencere boyutu degistirilebilir nitelikte olması için yazıldı
            ekran7.setSize(700, 700);
            ekran7.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ekran7.add(geriHareket);
            System.out.print("Saga:");
            saga = scanner.nextInt();
            sagaHareket.hSaga = saga;/*robotSaga classinin icinde bulunan hSaga degiskenine robotun kac izgara saga gittini gonderdik*/
            sagaHareket.sonY = robotunKonumlari[1] + ileri - geri;/*son y degerini robotSaga classina gonderdik*/
            izgaradanCikiyorMu = robotRobot.izgaradanCikiyorMu(konumlar, robotHareketleri, robotunKonumlari, ileri, geri, saga, sola);
            if (izgaradanCikiyorMu) {
                if ("tekerlekli robot".equals(robotTipi)) {
                    tekerlekli kontrol1 = new tekerlekli();
                    yol = (ileri + geri + saga) * 10;
                    System.out.println("Toplam sureniz:" + kontrol1.toplamSureyiBul(yol, tekerlekliHiz, engelSuresi, engeldenGecme));
                } else if ("paletli robot".equals(robotTipi)) {
                    paletli kontrol2 = new paletli();
                    yol = (ileri + geri + saga) * 10;
                    System.out.println("Toplam sureniz:" + kontrol2.toplamSureyiBul(yol, paletliHiz, engelSuresi, engeldenGecme));
                } else if ("seri robot".equals(robotTipi) || "paralel robot".equals(robotTipi)) {
                    seri kontrol4 = new seri();
                    yol = (ileri + geri + saga) * 10;
                    System.out.println("Toplam sureniz:" + kontrol4.toplamSureyiBul(yol, secilenTasimaHizi, kolaGecmeSuresi, engeldenGecme));
                } else if ("tekerlekli seri robot".equals(robotTipi)) {
                    tekerlekli kontrol3 = new tekerlekli();
                    yol = (ileri + geri + saga) * 10;
                    System.out.println("Toplam sureniz:" + kontrol3.toplamSureyiBul(yol, tsHiz, engelSuresi, engeldenGecme));
                } else if ("tekerlekli paralel robot".equals(robotTipi)) {
                    tekerlekli kontrol3 = new tekerlekli();
                    yol = (ileri + geri + saga) * 10;
                    System.out.println("Toplam sureniz:" + kontrol3.toplamSureyiBul(yol, tpHiz, engelSuresi, engeldenGecme));
                } else if ("paletli seri robot".equals(robotTipi)) {
                    tekerlekli kontrol3 = new tekerlekli();
                    yol = (ileri + geri + saga) * 10;
                    System.out.println("Toplam sureniz:" + kontrol3.toplamSureyiBul(yol, psHiz, engelSuresi, engeldenGecme));
                } else if ("paletli paralel robot".equals(robotTipi)) {
                    tekerlekli kontrol3 = new tekerlekli();
                    yol = (ileri + geri + saga) * 10;
                    System.out.println("Toplam sureniz:" + kontrol3.toplamSureyiBul(yol, ppHiz, engelSuresi, engeldenGecme));
                }
                System.out.println("Programdan cikmak icin bir tusa basin.");
                scanner.nextLine();
                scanner.nextLine();
                System.exit(0);
            }
            engelKontrolu = robotRobot.engelleKarsilastiMiSaga(engelKonumlariX, engelKonumlariY, ileri, geri, saga, robotTipi, robotunKonumlari);
            if (engelKontrolu == -1) {
                System.out.println("UYARI! Spider robot saga hareketinde engelle karsilastigi icin hareket edemez ve diger komutlar calismamaktadir..");
                System.out.println("Programdan cikmak icin bir tusa basin");
                scanner.nextLine();
                scanner.nextLine();
                System.exit(0);
            }
            sagEngel = engelKontrolu;
            ekran7.setVisible(false);
            ekran8.setVisible(true);  //JFramemin gozukmesi icin girilmis bir komut
            ekran8.setResizable(true); //Pencere boyutu degistirilebilir nitelikte olması için yazıldı
            ekran8.setSize(700, 700);
            ekran8.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ekran8.add(sagaHareket);
            System.out.print("Sola:");
            sola = scanner.nextInt();
            scanner.nextLine();/*integer bir degerden sonra kullanicidan string bir deger girilmesi istenirse bolsluga almasi icin atadik*/
            izgaradanCikiyorMu = robotRobot.izgaradanCikiyorMu(konumlar, robotHareketleri, robotunKonumlari, ileri, geri, saga, sola);
            if (izgaradanCikiyorMu) {
                if ("tekerlekli robot".equals(robotTipi)) {
                    tekerlekli kontrol1 = new tekerlekli();
                    yol = (ileri + geri + saga + sola) * 10;
                    System.out.println("Toplam sureniz:" + kontrol1.toplamSureyiBul(yol, tekerlekliHiz, engelSuresi, engeldenGecme));
                } else if ("paletli robot".equals(robotTipi)) {
                    paletli kontrol2 = new paletli();
                    yol = (ileri + geri + saga + sola) * 10;
                    System.out.println("Toplam sureniz:" + kontrol2.toplamSureyiBul(yol, paletliHiz, engelSuresi, engeldenGecme));
                } else if ("seri robot".equals(robotTipi) || "paralel robot".equals(robotTipi)) {
                    seri kontrol4 = new seri();
                    yol = (ileri + geri + saga + sola) * 10;
                    System.out.println("Toplam sureniz:" + kontrol4.toplamSureyiBul(yol, secilenTasimaHizi, kolaGecmeSuresi, engeldenGecme));
                } else if ("tekerlekli seri robot".equals(robotTipi)) {
                    tekerlekli kontrol3 = new tekerlekli();
                    yol = (ileri + geri + saga + sola) * 10;
                    System.out.println("Toplam sureniz:" + kontrol3.toplamSureyiBul(yol, tsHiz, engelSuresi, engeldenGecme));
                } else if ("tekerlekli paralel robot".equals(robotTipi)) {
                    tekerlekli kontrol3 = new tekerlekli();
                    yol = (ileri + geri + saga + sola) * 10;
                    System.out.println("Toplam sureniz:" + kontrol3.toplamSureyiBul(yol, tpHiz, engelSuresi, engeldenGecme));
                } else if ("paletli seri robot".equals(robotTipi)) {
                    tekerlekli kontrol3 = new tekerlekli();
                    yol = (ileri + geri + saga + sola) * 10;
                    System.out.println("Toplam sureniz:" + kontrol3.toplamSureyiBul(yol, psHiz, engelSuresi, engeldenGecme));
                } else if ("paletli paralel robot".equals(robotTipi)) {
                    tekerlekli kontrol3 = new tekerlekli();
                    yol = (ileri + geri + saga + sola) * 10;
                    System.out.println("Toplam sureniz:" + kontrol3.toplamSureyiBul(yol, ppHiz, engelSuresi, engeldenGecme));
                }
                System.out.println("Programdan cikmak icin bir tusa basin.");
                scanner.nextLine();
                scanner.nextLine();
                System.exit(0);
            }
            engelKontrolu = robotRobot.engelleKarsilastiMiSola(engelKonumlariX, engelKonumlariY, ileri, geri, saga, sola, robotTipi, robotunKonumlari);
            if (engelKontrolu == -1) {
                System.out.println("UYARI! Spider robot sola hareketinde engelle karsilastigi icin hareket edemez ve diger komutlar calismamaktadir..");
                System.out.println("Programdan cikmak icin bir tusa basin");
                scanner.nextLine();
                scanner.nextLine();
                System.exit(0);
            }
            solEngel = engelKontrolu;/*sola gittiginde kac tane engelden gectigini solEngel degiskenine atadik*/
            engeldenGecme = ileriEngel + geriEngel + solEngel + sagEngel;/*robotun hareketleri sonucu kac adet engelden gectigini engeldenGecme degiskenine atadik*/
            System.out.println("Robotunuz" + " " + engeldenGecme + " " + "kez engelden gecmistir!");
            ekran8.setVisible(false);

        } else {
            System.out.println("");
            System.out.println(robotTipi.toUpperCase() + " " + "izgaraya cikariliyor..\n");
            robotunKonumlari = robotunIlkKonumlari();
            ilkKonum.robotYonX = robotunKonumlari[0];
            ilkKonum.robotYonY = robotunKonumlari[1];
            ileriHareket.robotYonX = robotunKonumlari[0];
            ileriHareket.robotYonY = robotunKonumlari[1];
            geriHareket.robotYonX = robotunKonumlari[0];
            geriHareket.robotYonY = robotunKonumlari[1];
            geriHareket.hYson = robotunKonumlari[1] + robotHareketleri[0];
            sagaHareket.robotYonX = robotunKonumlari[0];
            sagaHareket.robotYonY = robotunKonumlari[1];
            sagaHareket.hSaga = robotHareketleri[2];
            sagaHareket.sonY = geriHareket.sonYdegeri;
            sonKonum.robotYonX = robotunKonumlari[0];
            sonKonum.robotYonY = robotunKonumlari[1];
            hibritKonum.robotYonX = robotunKonumlari[0];
            hibritKonum.robotYonY = robotunKonumlari[1];

            ekran.setVisible(false);
            /* 1.pencere kapatildi */
            ekran3.setVisible(true);  //JFramemin gozukmesi icin girilmis bir komut
            ekran3.setResizable(true); //Pencere boyutu degistirilebilir nitelikte olması için yazıldı
            ekran3.setSize(700, 700);
            ekran3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ekran3.add(ilkKonum);

            System.out.println("\nLutfen robotun hareket yonlerini giriniz:");
            System.out.println("Hareket etmesini istemediginiz yon icin 0 degerini giriniz!!");
            System.out.print("Ileri:");
            ileri = scanner.nextInt();
            ileriHareket.hIleri = ileri;/*robotIleri classina ileri degerini gonderdik*/
            boolean izgaradanCikiyorMu = robotRobot.izgaradanCikiyorMu(konumlar, robotHareketleri, robotunKonumlari, ileri, geri, saga, sola);

            if (izgaradanCikiyorMu) {
                if ("tekerlekli robot".equals(robotTipi)) {
                    tekerlekli kontrol1 = new tekerlekli();
                    yol = (ileri) * 10;
                    System.out.println("Toplam sureniz:" + kontrol1.toplamSureyiBul(yol, tekerlekliHiz, engelSuresi, engeldenGecme));
                } else if ("paletli robot".equals(robotTipi)) {
                    paletli kontrol2 = new paletli();
                    yol = (ileri) * 10;
                    System.out.println("Toplam sureniz:" + kontrol2.toplamSureyiBul(yol, paletliHiz, engelSuresi, engeldenGecme));
                } else if ("seri robot".equals(robotTipi) || "paralel robot".equals(robotTipi)) {
                    seri kontrol4 = new seri();
                    yol = (ileri) * 10;
                    System.out.println("Toplam sureniz:" + kontrol4.toplamSureyiBul(yol, secilenTasimaHizi, kolaGecmeSuresi, engeldenGecme));
                } else if ("tekerlekli seri robot".equals(robotTipi)) {
                    tekerlekli kontrol3 = new tekerlekli();
                    yol = (ileri) * 10;
                    System.out.println("Toplam sureniz:" + kontrol3.toplamSureyiBul(yol, tsHiz, engelSuresi, engeldenGecme));
                } else if ("tekerlekli paralel robot".equals(robotTipi)) {
                    tekerlekli kontrol3 = new tekerlekli();
                    yol = (ileri) * 10;
                    System.out.println("Toplam sureniz:" + kontrol3.toplamSureyiBul(yol, tpHiz, engelSuresi, engeldenGecme));
                } else if ("paletli seri robot".equals(robotTipi)) {
                    tekerlekli kontrol3 = new tekerlekli();
                    yol = (ileri) * 10;
                    System.out.println("Toplam sureniz:" + kontrol3.toplamSureyiBul(yol, psHiz, engelSuresi, engeldenGecme));
                } else if ("paletli paralel robot".equals(robotTipi)) {
                    tekerlekli kontrol3 = new tekerlekli();
                    yol = (ileri) * 10;
                    System.out.println("Toplam sureniz:" + kontrol3.toplamSureyiBul(yol, ppHiz, engelSuresi, engeldenGecme));
                }
                System.out.println("Programdan cikmak icin bir tusa basin.");
                scanner.nextLine();
                scanner.nextLine();
                System.exit(0);
            }
            ekran3.setVisible(false);/* 3.pencere kapatildi */
            ekran6.setVisible(true);  //JFramemin gozukmesi icin girilmis bir komut
            ekran6.setResizable(true); //Pencere boyutu degistirilebilir nitelikte olması için yazıldı
            ekran6.setSize(700, 700);
            ekran6.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ekran6.add(ileriHareket);
            System.out.print("Geri:");
            geri = scanner.nextInt();
            geriHareket.hGeri = geri;/*robotGeri classina geri degerini gonderdik*/
            geriHareket.hYson = robotunKonumlari[1] + ileri;
            izgaradanCikiyorMu = robotRobot.izgaradanCikiyorMu(konumlar, robotHareketleri, robotunKonumlari, ileri, geri, saga, sola);
            if (izgaradanCikiyorMu) {
                if ("tekerlekli robot".equals(robotTipi)) {
                    tekerlekli kontrol1 = new tekerlekli();
                    yol = (ileri + geri) * 10;
                    System.out.println("Toplam sureniz:" + kontrol1.toplamSureyiBul(yol, tekerlekliHiz, engelSuresi, engeldenGecme));
                } else if ("paletli robot".equals(robotTipi)) {
                    paletli kontrol2 = new paletli();
                    yol = (ileri + geri) * 10;
                    System.out.println("Toplam sureniz:" + kontrol2.toplamSureyiBul(yol, paletliHiz, engelSuresi, engeldenGecme));
                } else if ("seri robot".equals(robotTipi) || "paralel robot".equals(robotTipi)) {
                    seri kontrol4 = new seri();
                    yol = (ileri + geri) * 10;
                    System.out.println("Toplam sureniz:" + kontrol4.toplamSureyiBul(yol, secilenTasimaHizi, kolaGecmeSuresi, engeldenGecme));
                } else if ("tekerlekli seri robot".equals(robotTipi)) {
                    tekerlekli kontrol3 = new tekerlekli();
                    yol = (ileri + geri) * 10;
                    System.out.println("Toplam sureniz:" + kontrol3.toplamSureyiBul(yol, tsHiz, engelSuresi, engeldenGecme));
                } else if ("tekerlekli paralel robot".equals(robotTipi)) {
                    tekerlekli kontrol3 = new tekerlekli();
                    yol = (ileri + geri) * 10;
                    System.out.println("Toplam sureniz:" + kontrol3.toplamSureyiBul(yol, tpHiz, engelSuresi, engeldenGecme));
                } else if ("paletli seri robot".equals(robotTipi)) {
                    tekerlekli kontrol3 = new tekerlekli();
                    yol = (ileri + geri) * 10;
                    System.out.println("Toplam sureniz:" + kontrol3.toplamSureyiBul(yol, psHiz, engelSuresi, engeldenGecme));
                } else if ("paletli paralel robot".equals(robotTipi)) {
                    tekerlekli kontrol3 = new tekerlekli();
                    yol = (ileri + geri) * 10;
                    System.out.println("Toplam sureniz:" + kontrol3.toplamSureyiBul(yol, ppHiz, engelSuresi, engeldenGecme));
                }
                System.out.println("Programdan cikmak icin bir tusa basin.");
                scanner.nextLine();
                scanner.nextLine();
                System.exit(0);
            }
            ekran6.setVisible(false);
            ekran7.setVisible(true);  //JFramemin gozukmesi icin girilmis bir komut
            ekran7.setResizable(true); //Pencere boyutu degistirilebilir nitelikte olması için yazıldı
            ekran7.setSize(700, 700);
            ekran7.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ekran7.add(geriHareket);
            System.out.print("Saga:");
            saga = scanner.nextInt();
            sagaHareket.hSaga = saga;/*robotSaga classinin icinde bulunan hSaga degiskenine robotun kac izgara saga gittini gonderdik*/
            sagaHareket.sonY = robotunKonumlari[1] + ileri - geri;/*son y degerini robotSaga classina gonderdik*/
            izgaradanCikiyorMu = robotRobot.izgaradanCikiyorMu(konumlar, robotHareketleri, robotunKonumlari, ileri, geri, saga, sola);
            if (izgaradanCikiyorMu) {
                if ("tekerlekli robot".equals(robotTipi)) {
                    tekerlekli kontrol1 = new tekerlekli();
                    yol = (ileri + geri + saga) * 10;
                    System.out.println("Toplam sureniz:" + kontrol1.toplamSureyiBul(yol, tekerlekliHiz, engelSuresi, engeldenGecme));
                } else if ("paletli robot".equals(robotTipi)) {
                    paletli kontrol2 = new paletli();
                    yol = (ileri + geri + saga) * 10;
                    System.out.println("Toplam sureniz:" + kontrol2.toplamSureyiBul(yol, paletliHiz, engelSuresi, engeldenGecme));
                } else if ("seri robot".equals(robotTipi) || "paralel robot".equals(robotTipi)) {
                    seri kontrol4 = new seri();
                    yol = (ileri + geri + saga) * 10;
                    System.out.println("Toplam sureniz:" + kontrol4.toplamSureyiBul(yol, secilenTasimaHizi, kolaGecmeSuresi, engeldenGecme));
                } else if ("tekerlekli seri robot".equals(robotTipi)) {
                    tekerlekli kontrol3 = new tekerlekli();
                    yol = (ileri + geri + saga) * 10;
                    System.out.println("Toplam sureniz:" + kontrol3.toplamSureyiBul(yol, tsHiz, engelSuresi, engeldenGecme));
                } else if ("tekerlekli paralel robot".equals(robotTipi)) {
                    tekerlekli kontrol3 = new tekerlekli();
                    yol = (ileri + geri + saga) * 10;
                    System.out.println("Toplam sureniz:" + kontrol3.toplamSureyiBul(yol, tpHiz, engelSuresi, engeldenGecme));
                } else if ("paletli seri robot".equals(robotTipi)) {
                    tekerlekli kontrol3 = new tekerlekli();
                    yol = (ileri + geri + saga) * 10;
                    System.out.println("Toplam sureniz:" + kontrol3.toplamSureyiBul(yol, psHiz, engelSuresi, engeldenGecme));
                } else if ("paletli paralel robot".equals(robotTipi)) {
                    tekerlekli kontrol3 = new tekerlekli();
                    yol = (ileri + geri + saga) * 10;
                    System.out.println("Toplam sureniz:" + kontrol3.toplamSureyiBul(yol, ppHiz, engelSuresi, engeldenGecme));
                }
                System.out.println("Programdan cikmak icin bir tusa basin.");
                scanner.nextLine();
                scanner.nextLine();
                System.exit(0);
            }
            ekran7.setVisible(false);
            ekran8.setVisible(true);  //JFramemin gozukmesi icin girilmis bir komut
            ekran8.setResizable(true); //Pencere boyutu degistirilebilir nitelikte olması için yazıldı
            ekran8.setSize(700, 700);
            ekran8.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ekran8.add(sagaHareket);
            System.out.print("Sola:");
            sola = scanner.nextInt();
            scanner.nextLine();/*integer bir degerden sonra kullanicidan string bir deger girilmesi istenirse bolsluga almasi icin atadik*/
            izgaradanCikiyorMu = robotRobot.izgaradanCikiyorMu(konumlar, robotHareketleri, robotunKonumlari, ileri, geri, saga, sola);
            if (izgaradanCikiyorMu) {
                if ("tekerlekli robot".equals(robotTipi)) {
                    tekerlekli kontrol1 = new tekerlekli();
                    yol = (ileri + geri + saga + sola) * 10;
                    System.out.println("Toplam sureniz:" + kontrol1.toplamSureyiBul(yol, tekerlekliHiz, engelSuresi, engeldenGecme));
                } else if ("paletli robot".equals(robotTipi)) {
                    paletli kontrol2 = new paletli();
                    yol = (ileri + geri + saga + sola) * 10;
                    System.out.println("Toplam sureniz:" + kontrol2.toplamSureyiBul(yol, paletliHiz, engelSuresi, engeldenGecme));
                } else if ("seri robot".equals(robotTipi) || "paralel robot".equals(robotTipi)) {
                    seri kontrol4 = new seri();
                    yol = (ileri + geri + saga + sola) * 10;
                    System.out.println("Toplam sureniz:" + kontrol4.toplamSureyiBul(yol, secilenTasimaHizi, kolaGecmeSuresi, engeldenGecme));
                } else if ("tekerlekli seri robot".equals(robotTipi)) {
                    tekerlekli kontrol3 = new tekerlekli();
                    yol = (ileri + geri + saga + sola) * 10;
                    System.out.println("Toplam sureniz:" + kontrol3.toplamSureyiBul(yol, tsHiz, engelSuresi, engeldenGecme));
                } else if ("tekerlekli paralel robot".equals(robotTipi)) {
                    tekerlekli kontrol3 = new tekerlekli();
                    yol = (ileri + geri + saga + sola) * 10;
                    System.out.println("Toplam sureniz:" + kontrol3.toplamSureyiBul(yol, tpHiz, engelSuresi, engeldenGecme));
                } else if ("paletli seri robot".equals(robotTipi)) {
                    tekerlekli kontrol3 = new tekerlekli();
                    yol = (ileri + geri + saga + sola) * 10;
                    System.out.println("Toplam sureniz:" + kontrol3.toplamSureyiBul(yol, psHiz, engelSuresi, engeldenGecme));
                } else if ("paletli paralel robot".equals(robotTipi)) {
                    tekerlekli kontrol3 = new tekerlekli();
                    yol = (ileri + geri + saga + sola) * 10;
                    System.out.println("Toplam sureniz:" + kontrol3.toplamSureyiBul(yol, ppHiz, engelSuresi, engeldenGecme));
                }
                System.out.println("Programdan cikmak icin bir tusa basin.");
                scanner.nextLine();
                scanner.nextLine();
                System.exit(0);
            }
            ekran8.setVisible(false);

        }
        robotHareketleri = robotuHareketEttir(ileri, geri, saga, sola);
        sonKonum.ileri = robotHareketleri[0];
        sonKonum.geri = robotHareketleri[1];
        sonKonum.saga = robotHareketleri[2];
        sonKonum.sola = robotHareketleri[3];
        hibritKonum.ileri = robotHareketleri[0];
        /*hibritinHareketleri classina gondermek icin hibritKonum nesnesine degerleri esitledik*/
        hibritKonum.geri = robotHareketleri[1];
        hibritKonum.saga = robotHareketleri[2];
        hibritKonum.sola = robotHareketleri[3];

        ekran4.setVisible(true);  //JFramemin gozukmesi icin girilmis bir komut
        ekran4.setResizable(true); //Pencere boyutu degistirilebilir nitelikte olması için yazıldı
        ekran4.setSize(700, 700);
        ekran4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ekran4.add(sonKonum);
        /*4. pencenin grafigi ekrana bastirilmak uzere cagirildi */

        konumlar = sonKonumuTut(robotHareketleri, robotunKonumlari);
        /* Konumlar dizisine sonKonumuTut metodunun dondurdugu degerleri atadik */
        System.out.println("X son=" + konumlar[0]);
        /*silinmesi gerekiyor */
        System.out.println("Y son=" + konumlar[1]);
        /*silinmesi gerekiyor */
        hibritKonum.sabitlenmeX = konumlar[0] / 10;
        /*hibrit sabitlendikten sonra kollari hareket edecegi icin hareketinin son X konumunu hibritKonum nesnesiyle hibritinHareketleri classina gonderdik*/
        hibritKonum.sabitlenmeY = konumlar[1] / 10;
        /*hibrit sabitlendikten sonra kollari hareket edecegi icin hareketinin son Y konumunu hibritKonum nesnesiyle hibritinHareketleri classina gonderdik*/

        switch (robotTipi) {
            case "tekerlekli robot":
                tekerlekli tekerlekliRobot = new tekerlekli();
                System.out.println("\nSectiginiz robot" + " " + robotTipi.toUpperCase() + " " + "bu nedenle" + " " + "programimiz 1. PROBLEMI cozecektir...");
                System.out.println("Tekerlekli robotun tekerlek sayisini giriniz:");
                tekerlekSayisi = scanner.nextInt();
                scanner.nextLine();
                tekerlekliRobot.setTekerlekSayisi(tekerlekSayisi);
                tekerlekliRobot.sethiz(tekerlekliHiz);
                System.out.println("Hiziniz:" + tekerlekliRobot.gethiz());
                /*kontrol silinecek*/
                tekerlekliRobot.setmotorSayisi(secilenMotorSayisi);
                System.out.println("Motor sayisi:" + tekerlekliRobot.getmotorSayisi());
                /*kontrol silinecek*/
                tekerlekliRobot.setyukMiktari(secilenYukMiktari);
                yol = tekerlekliRobot.yoluBul(robotHareketleri);
                System.out.println("Yol:" + tekerlekliRobot.yoluBul(robotHareketleri));
                /*kontrol silinecek*/
                engelSuresi = tekerlekliRobot.engelGecmeSuresiBul(secilenMotorSayisi, engeldenGecme);
                System.out.println("Engel sureniz:" + engelSuresi);
                /*kontrol silinecek*/
                System.out.println("Toplam sureniz:" + tekerlekliRobot.toplamSureyiBul(yol, tekerlekliHiz, engelSuresi, engeldenGecme));
                /*kontrol silinecek*/
                break;
            case "paletli robot":
                paletli paletliRobot = new paletli();
                System.out.println("\nSectiginiz robot" + " " + robotTipi.toUpperCase() + " " + "bu nedenle" + " " + "programimiz 1. PROBLEMI cozecektir...");
                System.out.println("Paletli robotun palet sayisini giriniz:");
                paletSayisi = scanner.nextInt();
                scanner.nextLine();
                paletliRobot.setPaletSayisi(paletSayisi);
                paletliRobot.sethiz(paletliHiz);
                System.out.println("Hiziniz:" + paletliRobot.gethiz());
                /*kontrol silinecek*/
                paletliRobot.setmotorSayisi(secilenMotorSayisi);
                System.out.println("Motor sayisi:" + paletliRobot.getmotorSayisi());
                /*kontrol silinecek*/
                paletliRobot.setyukMiktari(secilenYukMiktari);
                yol = paletliRobot.yoluBul(robotHareketleri);
                System.out.println("Yol:" + paletliRobot.yoluBul(robotHareketleri));
                /*kontrol silinecek*/
                engelSuresi = paletliRobot.engelGecmeSuresiBul(secilenMotorSayisi, engeldenGecme);
                System.out.println("Toplam sureniz:" + paletliRobot.toplamSureyiBul(yol, paletliHiz, engelSuresi, engeldenGecme));
                /*kontrol silinecek*/
                break;
            case "spider robot":
                spider spiderRobot = new spider();
                System.out.println("\nSectiginiz robot" + " " + robotTipi.toUpperCase() + " " + "bu nedenle" + " " + "programimiz 1. PROBLEMI cozecektir...");
                System.out.println("Spider robotun bacak sayisini giriniz:");
                bacakSayisi = scanner.nextInt();
                scanner.nextLine();
                spiderRobot.setBacakSayisi(bacakSayisi);
                spiderRobot.sethiz(spiderHiz);
                System.out.println("Hiziniz:" + spiderRobot.gethiz());
                /*kontrol silinecek*/
                spiderRobot.setmotorSayisi(secilenMotorSayisi);
                System.out.println("Motor sayisi:" + spiderRobot.getmotorSayisi());
                /*kontrol silinecek*/
                spiderRobot.setyukMiktari(secilenYukMiktari);
                yol = spiderRobot.yoluBul(robotHareketleri);
                System.out.println("Yol:" + spiderRobot.yoluBul(robotHareketleri));
                /*kontrol silinecek*/
                engelSuresi = spiderRobot.engelGecmeSuresiBul(secilenMotorSayisi, engeldenGecme);
                System.out.println("Toplam sureniz:" + spiderRobot.toplamSureyiBul(yol, spiderHiz, engelSuresi, engeldenGecme));
                /*kontrol silinecek*/
                break;
            case "seri robot":
                seri seriRobot = new seri();
                System.out.println("\nSectiginiz robot" + " " + robotTipi.toUpperCase() + " " + "bu nedenle" + " " + "programimiz 2. PROBLEMI cozecektir...");
                seriRobot.setkapasite(seriYukKapasitesi);
                System.out.println("Kapasite:" + seriRobot.getkapasite());/*kontrol silinecek*/
                System.out.println("Seri robotun kol uzunlugunu giriniz:");
                kolUzunlugu = scanner.nextInt();
                seriRobot.setkolUzunlugu(kolUzunlugu);
                System.out.println("Kol Uzunlugu:" + seriRobot.getkolUzunlugu());
                /*kontrol silinecek*/
                System.out.println("Seri robotun yuku kola gecirme suresini yaziniz:");
                kolaGecmeSuresi = scanner.nextInt();
                seriRobot.setkolaGecmeSuresi(kolaGecmeSuresi);
                System.out.println("Kola gecme suresi:" + seriRobot.getkolaGecmeSuresi());/*kontrol silinecek*/
                seriRobot.settasimahHizi(secilenTasimaHizi);
                System.out.println("Seri robotun yuku tasima hizi: " + seriRobot.gettasimaHizi());/*kontrol icin silinecek*/
                yol = seriRobot.yoluBul(robotHareketleri);
                System.out.println("Toplam yol:" + yol);/*kontrol silinecek*/
                /// case seri robot ve case paralel robot(degistirmeyi unutma) toplam yoldan sonra toplam sureden once
                if (seriRobot.kolUzunluguYetiyorMu(yol, kolUzunlugu)) {
                    System.out.println("Kol uzunluğu yola yetiyor");
                } else {
                    System.out.println("HATA! Kol uzunlugu asildi.");
                    System.out.println("Program sonlaniyor... \nProgramdan cikmak icin herhangi bir tusa basin.");
                    scanner.next();
                    System.exit(0);
                }
                System.out.println("Toplam sureniz:" + seriRobot.toplamSureyiBul(yol, secilenTasimaHizi, kolaGecmeSuresi, engeldenGecme));

                break;
            case "paralel robot":
                paralel paralelRobot = new paralel();
                System.out.println("\nSectiginiz robot" + " " + robotTipi.toUpperCase() + " " + "bu nedenle" + " " + "programimiz 2. PROBLEMI cozecektir...");
                paralelRobot.setkapasite(paralelYukKapasitesi);
                System.out.println("Kapasite:" + paralelRobot.getkapasite());
                System.out.println("Paralel robotun kol uzunlugunu giriniz:");
                kolUzunlugu = scanner.nextInt();
                paralelRobot.setkolUzunlugu(kolUzunlugu);
                System.out.println("Kol Uzunlugu:" + paralelRobot.getkolUzunlugu());
                System.out.println("Paralel robotun yuku kola gecirme suresini yaziniz:");
                kolaGecmeSuresi = scanner.nextInt();
                paralelRobot.setkolaGecmeSuresi(kolaGecmeSuresi);
                System.out.println("Kola gecme suresi:" + paralelRobot.getkolaGecmeSuresi());
                paralelRobot.settasimahHizi(secilenTasimaHizi);
                System.out.println("Paralel robotun yuku tasima hizi: " + paralelRobot.gettasimaHizi());
                yol = paralelRobot.yoluBul(robotHareketleri);
                System.out.println("Toplam Yol:" + yol);
                if (paralelRobot.kolUzunluguYetiyorMu(yol, kolUzunlugu)) {
                    System.out.println("Kol uzunluğu yola yetiyor");
                } else {
                    System.out.println("HATA! Kol uzunlugu asildi.");
                    System.out.println("Program sonlaniyor... \nProgramdan cikmak icin herhangi bir tusa basin.");
                    scanner.next();
                    System.exit(0);
                }
                System.out.println("Toplam sureniz:" + paralelRobot.toplamSureyiBul(yol, secilenTasimaHizi, kolaGecmeSuresi, engeldenGecme));
                break;

            case "tekerlekli seri robot":
                System.out.println("\nSectiginiz robot" + " " + robotTipi.toUpperCase() + " " + "bu nedenle" + " " + "programimiz 3. PROBLEMI cozecektir...");
                hibritRobot.sethiz(tsHiz);
                System.out.println("Hibritin hizi:" + hibritRobot.gethiz());
                System.out.println("Tekerlekli seri robotun tekerlek sayisini giriniz:");
                tekerlekSayisi = scanner.nextInt();
                hibritRobot.setmotorSayisi(secilenMotorSayisi);
                System.out.println("Motor sayisi:" + hibritRobot.getmotorSayisi());
                hibritRobot.setkapasite(tsKapasite);
                System.out.println("Kapasite:" + hibritRobot.getkapasite());
                hibritRobot.setyukMiktari(secilenYukMiktari);
                System.out.println("Yuk Miktari:" + hibritRobot.getyukMiktari());
                System.out.println("Tekerlekli seri robotun kol uzunlugunu giriniz:");
                kolUzunlugu = scanner.nextInt();
                hibritRobot.setkolUzunlugu(kolUzunlugu);
                System.out.println("Kol uzunlugu:" + hibritRobot.getkolUzunlugu());
                System.out.println("Tekerlekli seri robotun kola gecme suresini giriniz:");
                kolaGecmeSuresi = scanner.nextInt();
                hibritRobot.setkolaGecmeSuresi(kolaGecmeSuresi);
                System.out.println("Kola gecme suresi:" + hibritRobot.getkolaGecmeSuresi());
                hibritRobot.settasimahHizi(secilenTasimaHizi);
                System.out.println("Tekerlekli seri robotun yuku tasima hizi: " + hibritRobot.gettasimaHizi());
                System.out.println("");
                hibritinKonumlari = hibritinHareketleriniAl();
                hibritKonum.kIleri = hibritinKonumlari[0];/*hibrit robotun kolunun ileri yonu hibritinHareketleri classina aktarildi*/
                hibritKonum.kGeri = hibritinKonumlari[1];/*hibrit robotun kolunun geri yonu hibritinHareketleri classina aktarildi*/
                hibritKonum.kSaga = hibritinKonumlari[2];/*hibrit robotun kolunun saga yonu hibritinHareketleri classina aktarildi*/
                hibritKonum.kSola = hibritinKonumlari[3];/*hibrit robotun kolunun sola yonu hibritinHareketleri classina aktarildi*/
                if (hibritRobot.kolUzunluguYetiyorMu(hibritinKonumlari, kolUzunlugu)) {
                    System.out.println("Kol uzunluğu yola yetiyor");
                } else {
                    System.out.println("HATA! Kol uzunlugu asildi.");
                    System.out.println("Program sonlaniyor... \nProgramdan cikmak icin herhangi bir tusa basin.");
                    scanner.next();
                    System.exit(0);
                }
                ekran4.setVisible(false);
                ekran5.setVisible(true);  //JFramemin gozukmesi icin girilmis bir komut
                ekran5.setResizable(true); //Pencere boyutu degistirilebilir nitelikte olması için yazıldı
                ekran5.setSize(700, 700);
                ekran5.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                ekran5.add(hibritKonum);
                yol = hibritRobot.yoluBul(robotHareketleri, hibritinKonumlari);
                System.out.println("Toplam yol:" + yol);/*kontrol silinecek*/
                System.out.println("Toplam sureniz:" + hibritRobot.toplamSureyiBul(robotHareketleri, hibritinKonumlari, secilenTasimaHizi, kolaGecmeSuresi, robotTipi, hibritRobot.getmotorSayisi(), tsHiz, engeldenGecme));
                break;

            case "tekerlekli paralel robot":
                System.out.println("\nSectiginiz robot" + " " + robotTipi.toUpperCase() + " " + "bu nedenle" + " " + "programimiz 3. PROBLEMI cozecektir...");
                hibritRobot.sethiz(tpHiz);
                System.out.println("Hibritin hizi:" + hibritRobot.gethiz());
                System.out.println("Tekerlekli paralel robotun palet sayisini giriniz:");
                paletSayisi = scanner.nextInt();
                hibritRobot.setmotorSayisi(secilenMotorSayisi);
                System.out.println("Motor sayisi:" + hibritRobot.getmotorSayisi());
                hibritRobot.setkapasite(tpKapasite);
                System.out.println("Kapasite:" + hibritRobot.getkapasite());
                hibritRobot.setyukMiktari(secilenYukMiktari);
                System.out.println("Yuk Miktari:" + hibritRobot.getyukMiktari());
                /*kontrol silinecek*/
                System.out.println("Tekerlekli paralel robotun kol uzunlugunu giriniz:");
                kolUzunlugu = scanner.nextInt();
                hibritRobot.setkolUzunlugu(kolUzunlugu);
                System.out.println("Kol uzunlugu:" + hibritRobot.getkolUzunlugu());
                System.out.println("Tekerlekli paralel robotun kola gecme suresini giriniz:");
                kolaGecmeSuresi = scanner.nextInt();
                hibritRobot.setkolaGecmeSuresi(kolaGecmeSuresi);
                System.out.println("Kola gecme suresi:" + hibritRobot.getkolaGecmeSuresi());
                hibritRobot.settasimahHizi(secilenTasimaHizi);
                System.out.println("Tekerlekli paralel robotun yuku tasima hizi: " + hibritRobot.gettasimaHizi());
                hibritinKonumlari = hibritinHareketleriniAl();
                hibritKonum.kIleri = hibritinKonumlari[0];/*hibrit robotun kolunun ileri yonu hibritinHareketleri classina aktarildi*/
                hibritKonum.kGeri = hibritinKonumlari[1];/*hibrit robotun kolunun geri yonu hibritinHareketleri classina aktarildi*/
                hibritKonum.kSaga = hibritinKonumlari[2];/*hibrit robotun kolunun saga yonu hibritinHareketleri classina aktarildi*/
                hibritKonum.kSola = hibritinKonumlari[3];/*hibrit robotun kolunun sola yonu hibritinHareketleri classina aktarildi*/
                if (hibritRobot.kolUzunluguYetiyorMu(hibritinKonumlari, kolUzunlugu)) {
                    System.out.println("Kol uzunluğu yola yetiyor");
                } else {
                    System.out.println("HATA! Kol uzunlugu asildi.");
                    System.out.println("Program sonlaniyor... \nProgramdan cikmak icin herhangi bir tusa basin.");
                    scanner.next();
                    System.exit(0);
                }
                ekran4.setVisible(false);
                ekran5.setVisible(true);  //JFramemin gozukmesi icin girilmis bir komut
                ekran5.setResizable(true); //Pencere boyutu degistirilebilir nitelikte olması için yazıldı
                ekran5.setSize(700, 700);
                ekran5.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                ekran5.add(hibritKonum);
                yol = hibritRobot.yoluBul(robotHareketleri, hibritinKonumlari);
                System.out.println("Toplam yol:" + yol);/*kontrol silinecek*/
                System.out.println("Toplam sureniz:" + hibritRobot.toplamSureyiBul(robotHareketleri, hibritinKonumlari, secilenTasimaHizi, kolaGecmeSuresi, robotTipi, hibritRobot.getmotorSayisi(), tpHiz, engeldenGecme));
                break;

            case "paletli seri robot":
                System.out.println("\nSectiginiz robot" + " " + robotTipi.toUpperCase() + " " + "bu nedenle" + " " + "programimiz 3. PROBLEMI cozecektir...");
                hibritRobot.sethiz(psHiz);
                System.out.println("Hibritin hizi:" + hibritRobot.gethiz());
                System.out.println("Paletli seri robotun palet sayisini giriniz:");
                paletSayisi = scanner.nextInt();
                hibritRobot.setmotorSayisi(secilenMotorSayisi);
                System.out.println("Motor sayisi:" + hibritRobot.getmotorSayisi());
                hibritRobot.setkapasite(psKapasite);
                System.out.println("Kapasite:" + hibritRobot.getkapasite());
                hibritRobot.setyukMiktari(secilenYukMiktari);
                System.out.println("Yuk Miktari:" + hibritRobot.getyukMiktari());
                System.out.println("Paletli seri robotun kol uzunlugunu giriniz:");
                kolUzunlugu = scanner.nextInt();
                hibritRobot.setkolUzunlugu(kolUzunlugu);
                System.out.println("Kol uzunlugu:" + hibritRobot.getkolUzunlugu());
                System.out.println("Paletli seri robotun kola gecme suresini giriniz:");
                kolaGecmeSuresi = scanner.nextInt();
                hibritRobot.setkolaGecmeSuresi(kolaGecmeSuresi);
                System.out.println("Kola gecme suresi:" + hibritRobot.getkolaGecmeSuresi());
                hibritRobot.settasimahHizi(secilenTasimaHizi);
                System.out.println("Paletli seri robotun yuku tasima hizi: " + hibritRobot.gettasimaHizi());
                hibritinKonumlari = hibritinHareketleriniAl();
                hibritKonum.kIleri = hibritinKonumlari[0];/*hibrit robotun kolunun ileri yonu hibritinHareketleri classina aktarildi*/
                hibritKonum.kGeri = hibritinKonumlari[1];/*hibrit robotun kolunun geri yonu hibritinHareketleri classina aktarildi*/
                hibritKonum.kSaga = hibritinKonumlari[2];/*hibrit robotun kolunun saga yonu hibritinHareketleri classina aktarildi*/
                hibritKonum.kSola = hibritinKonumlari[3];/*hibrit robotun kolunun sola yonu hibritinHareketleri classina aktarildi*/
                if (hibritRobot.kolUzunluguYetiyorMu(hibritinKonumlari, kolUzunlugu)) {
                    System.out.println("Kol uzunluğu yola yetiyor");
                } else {
                    System.out.println("HATA! Kol uzunlugu asildi.");
                    System.out.println("Program sonlaniyor... \nProgramdan cikmak icin herhangi bir tusa basin.");
                    scanner.next();
                    System.exit(0);
                }
                ekran4.setVisible(false);
                ekran5.setVisible(true);  //JFramemin gozukmesi icin girilmis bir komut
                ekran5.setResizable(true); //Pencere boyutu degistirilebilir nitelikte olması için yazıldı
                ekran5.setSize(700, 700);
                ekran5.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                ekran5.add(hibritKonum);
                yol = hibritRobot.yoluBul(robotHareketleri, hibritinKonumlari);
                System.out.println("Toplam yol:" + yol);
                System.out.println("Toplam sureniz:" + hibritRobot.toplamSureyiBul(robotHareketleri, hibritinKonumlari, secilenTasimaHizi, kolaGecmeSuresi, robotTipi, hibritRobot.getmotorSayisi(), psHiz, engeldenGecme));
                break;

            case "paletli paralel robot":
                System.out.println("\nSectiginiz robot" + " " + robotTipi.toUpperCase() + " " + "bu nedenle" + " " + "programimiz 3. PROBLEMI cozecektir...");
                hibritRobot.sethiz(ppHiz);
                System.out.println("Hibritin hizi:" + hibritRobot.gethiz());
                System.out.println("Paletli paralel robotun palet sayisini giriniz:");
                paletSayisi = scanner.nextInt();
                hibritRobot.setmotorSayisi(secilenMotorSayisi);
                System.out.println("Motor sayisi:" + hibritRobot.getmotorSayisi());
                hibritRobot.setkapasite(ppKapasite);
                System.out.println("Kapasite:" + hibritRobot.getkapasite());
                hibritRobot.setyukMiktari(secilenYukMiktari);
                System.out.println("Yuk Miktari:" + hibritRobot.getyukMiktari());
                /*kontrol silinecek*/
                System.out.println("Paletli paralel robotun kol uzunlugunu giriniz:");
                kolUzunlugu = scanner.nextInt();
                hibritRobot.setkolUzunlugu(kolUzunlugu);
                System.out.println("Kol uzunlugu:" + hibritRobot.getkolUzunlugu());
                System.out.println("Paletli paralel robotun kola gecme suresini giriniz:");
                kolaGecmeSuresi = scanner.nextInt();
                hibritRobot.setkolaGecmeSuresi(kolaGecmeSuresi);
                System.out.println("Kola gecme suresi:" + hibritRobot.getkolaGecmeSuresi());
                hibritRobot.settasimahHizi(secilenTasimaHizi);
                System.out.println("Paletli paralel robotun yuku tasima hizi: " + hibritRobot.gettasimaHizi());
                hibritinKonumlari = hibritinHareketleriniAl();
                hibritKonum.kIleri = hibritinKonumlari[0];/*hibrit robotun kolunun ileri yonu hibritinHareketleri classina aktarildi*/
                hibritKonum.kGeri = hibritinKonumlari[1];/*hibrit robotun kolunun geri yonu hibritinHareketleri classina aktarildi*/
                hibritKonum.kSaga = hibritinKonumlari[2];/*hibrit robotun kolunun saga yonu hibritinHareketleri classina aktarildi*/
                hibritKonum.kSola = hibritinKonumlari[3];/*hibrit robotun kolunun sola yonu hibritinHareketleri classina aktarildi*/
                if (hibritRobot.kolUzunluguYetiyorMu(hibritinKonumlari, kolUzunlugu)) {
                    System.out.println("Kol uzunluğu yola yetiyor");
                } else {
                    System.out.println("HATA! Kol uzunlugu asildi.");
                    System.out.println("Program sonlaniyor... \nProgramdan cikmak icin herhangi bir tusa basin.");
                    scanner.next();
                    System.exit(0);
                }
                ekran4.setVisible(false);
                ekran5.setVisible(true);  //JFramemin gozukmesi icin girilmis bir komut
                ekran5.setResizable(true); //Pencere boyutu degistirilebilir nitelikte olması için yazıldı
                ekran5.setSize(700, 700);
                ekran5.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                ekran5.add(hibritKonum);
                yol = hibritRobot.yoluBul(robotHareketleri, hibritinKonumlari);
                System.out.println("Toplam yol:" + yol);/*kontrol silinecek*/
                System.out.println("Toplam sureniz:" + hibritRobot.toplamSureyiBul(robotHareketleri, hibritinKonumlari, secilenTasimaHizi, kolaGecmeSuresi, robotTipi, hibritRobot.getmotorSayisi(), ppHiz, engeldenGecme));
                break;

            case "spider seri robot":
                System.out.println("\nSectiginiz robot" + " " + robotTipi.toUpperCase() + " " + "bu nedenle" + " " + "programimiz 3. PROBLEMI cozecektir...");
                hibritRobot.sethiz(ssHiz);
                System.out.println("Hibritin hizi:" + hibritRobot.gethiz());
                System.out.println("Spider seri robotun bacak sayisini giriniz:");
                bacakSayisi = scanner.nextInt();
                hibritRobot.setmotorSayisi(secilenMotorSayisi);
                System.out.println("Motor sayisi:" + hibritRobot.getmotorSayisi());
                hibritRobot.setkapasite(ssKapasite);
                System.out.println("Kapasite:" + hibritRobot.getkapasite());
                hibritRobot.setyukMiktari(secilenYukMiktari);
                System.out.println("Yuk Miktari:" + hibritRobot.getyukMiktari());
                System.out.println("Spider seri robotun kol uzunlugunu giriniz:");
                kolUzunlugu = scanner.nextInt();
                hibritRobot.setkolUzunlugu(kolUzunlugu);
                System.out.println("Kol uzunlugu:" + hibritRobot.getkolUzunlugu());
                System.out.println("Spider seri robotun kola gecme suresini giriniz:");
                kolaGecmeSuresi = scanner.nextInt();
                hibritRobot.setkolaGecmeSuresi(kolaGecmeSuresi);
                System.out.println("Kola gecme suresi:" + hibritRobot.getkolaGecmeSuresi());
                hibritRobot.settasimahHizi(secilenTasimaHizi);
                System.out.println("Spider seri robotun yuku tasima hizi: " + hibritRobot.gettasimaHizi());
                hibritinKonumlari = hibritinHareketleriniAl();
                hibritKonum.kIleri = hibritinKonumlari[0];/*hibrit robotun kolunun ileri yonu hibritinHareketleri classina aktarildi*/
                hibritKonum.kGeri = hibritinKonumlari[1];/*hibrit robotun kolunun geri yonu hibritinHareketleri classina aktarildi*/
                hibritKonum.kSaga = hibritinKonumlari[2];/*hibrit robotun kolunun saga yonu hibritinHareketleri classina aktarildi*/
                hibritKonum.kSola = hibritinKonumlari[3];/*hibrit robotun kolunun sola yonu hibritinHareketleri classina aktarildi*/
                if (hibritRobot.kolUzunluguYetiyorMu(hibritinKonumlari, kolUzunlugu)) {
                    System.out.println("Kol uzunluğu yola yetiyor");
                } else {
                    System.out.println("HATA! Kol uzunlugu asildi.");
                    System.out.println("Program sonlaniyor... \nProgramdan cikmak icin herhangi bir tusa basin.");
                    scanner.next();
                    System.exit(0);
                }
                ekran4.setVisible(false);
                ekran5.setVisible(true);  //JFramemin gozukmesi icin girilmis bir komut
                ekran5.setResizable(true); //Pencere boyutu degistirilebilir nitelikte olması için yazıldı
                ekran5.setSize(700, 700);
                ekran5.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                ekran5.add(hibritKonum);
                yol = hibritRobot.yoluBul(robotHareketleri, hibritinKonumlari);
                System.out.println("Toplam yol:" + yol);
                System.out.println("Toplam sureniz:" + hibritRobot.toplamSureyiBul(robotHareketleri, hibritinKonumlari, secilenTasimaHizi, kolaGecmeSuresi, robotTipi, hibritRobot.getmotorSayisi(), ssHiz, engeldenGecme));
                break;

            case "spider paralel robot":
                System.out.println("\nSectiginiz robot" + " " + robotTipi.toUpperCase() + " " + "bu nedenle" + " " + "programimiz 3. PROBLEMI cozecektir...");
                hibritRobot.sethiz(spHiz);
                System.out.println("Hibritin hizi:" + hibritRobot.gethiz());
                System.out.println("Spider paralel robotun bacak sayisini giriniz:");
                bacakSayisi = scanner.nextInt();
                hibritRobot.setmotorSayisi(secilenMotorSayisi);
                System.out.println("Motor sayisi:" + hibritRobot.getmotorSayisi());
                hibritRobot.setkapasite(spKapasite);
                System.out.println("Kapasite:" + hibritRobot.getkapasite());
                hibritRobot.setyukMiktari(secilenYukMiktari);
                System.out.println("Yuk Miktari:" + hibritRobot.getyukMiktari());
                System.out.println("Spider paralel robotun kol uzunlugunu giriniz:");
                kolUzunlugu = scanner.nextInt();
                hibritRobot.setkolUzunlugu(kolUzunlugu);
                System.out.println("Kol uzunlugu:" + hibritRobot.getkolUzunlugu());
                System.out.println("Spider paralel robotun kola gecme suresini giriniz:");
                kolaGecmeSuresi = scanner.nextInt();
                hibritRobot.setkolaGecmeSuresi(kolaGecmeSuresi);
                System.out.println("Kola gecme suresi:" + hibritRobot.getkolaGecmeSuresi());
                hibritRobot.settasimahHizi(secilenTasimaHizi);
                System.out.println("Spider paralel robotun yuku tasima hizi: " + hibritRobot.gettasimaHizi());
                hibritinKonumlari = hibritinHareketleriniAl();
                hibritKonum.kIleri = hibritinKonumlari[0];/*hibrit robotun kolunun ileri yonu hibritinHareketleri classina aktarildi*/
                hibritKonum.kGeri = hibritinKonumlari[1];/*hibrit robotun kolunun geri yonu hibritinHareketleri classina aktarildi*/
                hibritKonum.kSaga = hibritinKonumlari[2];/*hibrit robotun kolunun saga yonu hibritinHareketleri classina aktarildi*/
                hibritKonum.kSola = hibritinKonumlari[3];/*hibrit robotun kolunun sola yonu hibritinHareketleri classina aktarildi*/
                if (hibritRobot.kolUzunluguYetiyorMu(hibritinKonumlari, kolUzunlugu)) {
                    System.out.println("Kol uzunluğu yola yetiyor");
                } else {
                    System.out.println("HATA! Kol uzunlugu asildi.");
                    System.out.println("Program sonlaniyor... \nProgramdan cikmak icin herhangi bir tusa basin.");
                    scanner.next();
                    System.exit(0);
                }
                ekran4.setVisible(false);
                ekran5.setVisible(true);  //JFramemin gozukmesi icin girilmis bir komut
                ekran5.setResizable(true); //Pencere boyutu degistirilebilir nitelikte olması için yazıldı
                ekran5.setSize(700, 700);
                ekran5.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                ekran5.add(hibritKonum);
                yol = hibritRobot.yoluBul(robotHareketleri, hibritinKonumlari);
                System.out.println("Toplam yol:" + yol);/*kontrol silinecek*/
                System.out.println("Toplam sureniz:" + hibritRobot.toplamSureyiBul(robotHareketleri, hibritinKonumlari, secilenTasimaHizi, kolaGecmeSuresi, robotTipi, hibritRobot.getmotorSayisi(), spHiz, engeldenGecme));
                break;
        }

    }
}
