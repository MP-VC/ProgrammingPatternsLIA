package vanier.com.programmingpatternslia.VarEngine;

import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

public class Images //Used statically to give devs A BufferedImage ONCE without duplicates to avoid memory leaks
{
    public static HashMap<String, BufferedImage> loaded;

    public BufferedImage get(String imgPath)
    {
        if (loaded == null) loaded = new HashMap<>();
        if (loaded.containsKey(imgPath)) return loaded.get(imgPath);
        else
        {
           //TO FINISH DO NOT TOUCH - DAN
        }
    }
}
