/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.ribeiro.fitxersvista;

import Utils.FitxerException;
import com.ribeiro.fitxercomponent.controllers.FileController;
import com.ribeiro.fitxercomponent.models.MyFile;
import com.ribeiro.tendacomponent.controllers.TendaController;
import com.ribeiro.tendacomponent.models.Product;
import java.util.List;

/**
 *
 * @author pedro
 */
public class Fitxersvista {

    public static void main(String[] args) {

        llegirDirectori(System.getProperty("user.dir"));
        escriureILlegirProductes();
        escriureJson();

    }

    private static void escriureJson() {
        try {

            TendaController tController = new TendaController();
            FileController fController = new FileController();

            List<Product> productes = tController.GetProducts();
            Product product = productes.get(0);
            String path = System.getProperty("user.dir") + "/json.json";
            fController.generarJson(product, path);
            List<Product> pList = fController.getProductsByPath(path);
            for(Product p : pList)
                System.out.println(p.getName() + " - " +  p.getPrice());
            
        } catch (FitxerException fe) {
            System.out.println(fe.getMessage());
        }
    }

    private static void escriureILlegirProductes() {
        try {

            FileController fController = new FileController();
            fController.createListProducts();

            TendaController tController = new TendaController();
            List<Product> products = tController.GetProducts();

            String path = System.getProperty("user.dir") + "/products2.txt";
            fController.saveListProducts(products, path);
            List<Product> products2 = fController.getProductsByPath(path);
            for (Product p : products2) {
                System.out.println(p.getName() + " - " + p.getPrice());
            }

        } catch (FitxerException ex) {
            System.out.println(ex.getMessage());
        }

    }

    private static void llegirDirectori(String path) {

        try {

            FileController fController = new FileController();
            List<MyFile> file = fController.checkDirectory(path);

            String res = "";

            for (MyFile f : file) {

                if (f.esDirectori()) {
                    res += f.getNom() + " +++ dir +++ \n";
                    continue;
                }

                String fileString = f.getNom() + " ";

                if (f.esLectura()) {
                    fileString += "r";
                } else {
                    fileString += "-";
                }

                if (f.esEscriptura()) {
                    fileString += "w";
                } else {
                    fileString += "-";
                }

                if (f.esExecutable()) {
                    fileString += "x";
                } else {
                    fileString += "-";
                }

                if (f.esOcult()) {
                    fileString += " h ";
                }

                fileString += " " + f.getPropietari() + " " + f.getUltimaModificacio();

                res += fileString + "\n";

            }

            System.out.println(res);

        } catch (FitxerException fe) {
            System.out.println("Error al llegir directori");
        }
    }
}
