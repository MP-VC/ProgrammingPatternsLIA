package vanier.com.programmingpatternslia.VarEngine.Animation;

import java.util.HashMap;

public class Animation {
    HashMap<Integer, Sheet> sheetRegistry;
    HashMap<Integer, Sheet> loaded;

    public void addSheet(int id, Sheet sheet)
    {
        sheetRegistry.put(id, sheet);
    }

    private void load(int id)
    {
        loaded.put(id, sheetRegistry.get(id));
    }

    private void unload(int id)
    {
        loaded.remove(id);
    }

    private void clearLoad()
    {
        loaded.clear();
    }



}
