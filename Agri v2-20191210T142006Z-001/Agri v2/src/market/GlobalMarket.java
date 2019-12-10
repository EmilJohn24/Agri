package market;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GlobalMarket {
    //static-stuff
    private static Market globalMarket;

    public static Market getGlobalMarket() {
        return globalMarket;
    }

    private static void preloadStuff() throws IOException {
        preloadItems();
    }

    private static void preloadItems() throws IOException {
        BufferedReader defaultCropFile = new BufferedReader(new FileReader("defaultCrops.txt"));
        String cropName;
        while ((cropName = defaultCropFile.readLine()) != null){
            System.out.println(cropName);
            globalMarket.addIfAbsent(new Product(cropName));
        }
    }



    static{
        globalMarket = new Market();
        try {
            preloadStuff();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
