package vanier.com.programmingpatternslia.VarEngine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ConcurrentHashMap;

public class Images //Used statically to give devs A BufferedImage ONCE without duplicates to avoid memory leaks
{
    private static final ConcurrentHashMap<String, BufferedImage> loaded = new ConcurrentHashMap<>();

    public static int getCacheSize() {
        return loaded.size();
    }

    public static void clearCache()
    {
        loaded.clear();
    }

    public static void unload(String path) {
        loaded.remove(path);
    }

    public static void unload(Path path) {
        loaded.remove(path.toString());
    }

    public static BufferedImage get(String imgPath)
    {
        return getBufferedImage(imgPath);
    }

    public static BufferedImage get(Path imgPath) {
        String pathString = imgPath.toString();
        return getBufferedImage(pathString);
    }

    private static BufferedImage getBufferedImage(String pathString) {
        return loaded.computeIfAbsent(pathString, path ->
        {
            try {
                File file = new File(path);
                BufferedImage imgFromFile = ImageIO.read(file);
                if (imgFromFile == null) {
                    System.err.println("Unsupported or invalid image: " + path);
                    return null;
                }
                return imgFromFile;
            } catch (IOException e) {
                System.err.println("Error loading the image: " + e.getMessage());
                return null;
            }
        });
    }


}
