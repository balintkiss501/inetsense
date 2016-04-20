
package hu.elte.inetsense.probe.uploader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class ConfigLoader {
    
    private Map<String, String> data;
    
    public ConfigLoader() {
        
        data = new HashMap();
        
        loadIni();
        
    }
    
    public String get(String key) {
        
        return data.get(key);
        
    }
    
    private void loadIni() {
        
        InputStream stream = getClass().getResourceAsStream("/config.ini");
        
        Scanner sc = new Scanner(stream);
        
        while(sc.hasNextLine()) {
            
            String line = sc.nextLine().trim();
            
            if(line.isEmpty() || line.startsWith(";") || line.startsWith("[")) {
                continue;
            }
            
            String[] parts = line.split("=", 2);
            
            data.put(parts[0].trim(), parts[1].trim());
            
        }
        
    }
    
}
