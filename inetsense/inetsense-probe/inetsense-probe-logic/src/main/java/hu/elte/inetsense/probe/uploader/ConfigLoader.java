
package hu.elte.inetsense.probe.uploader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigLoader {

    public static Logger        log = LogManager.getLogger();

    private Map<String, String> data;

    public ConfigLoader() {

        loadIni(getDefaultIniStream());

    }

    public ConfigLoader(final String path) {

        log.info("Loading ini file from: " + path);

        try {
            loadIni(new FileInputStream(path));
        } catch (FileNotFoundException ex) {
            log.error("File not found, loadign default ini");
            loadIni(getDefaultIniStream());
        }
    }

    public String get(final String key) {

        return data.get(key);

    }

    //
    private InputStream getDefaultIniStream() {
        return getClass().getResourceAsStream("/config.ini");
    }

    private void loadIni(final InputStream stream) {

        data = new HashMap();

        Scanner sc = new Scanner(stream);

        while (sc.hasNextLine()) {

            String line = sc.nextLine().trim();

            if (line.isEmpty() || line.startsWith(";") || line.startsWith("[")) {
                continue;
            }

            String[] parts = line.split("=", 2);

            data.put(parts[0].trim(), parts[1].trim());

        }

    }

}
