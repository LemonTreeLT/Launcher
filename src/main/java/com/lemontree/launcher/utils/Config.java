package com.lemontree.launcher.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.lemontree.launcher.App;
import com.lemontree.launcher.events.ConfigZoomChangeListener;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
public class Config {
    private static final String USER_HOME = System.getProperty("user.home");
    private static final String APP_DATA_PATH = USER_HOME + File.separator + "AppData" + File.separator + "Roaming" + File.separator + ".lemontree" + File.separator + "launcher";
    private static final File CONFIG_FILE = new File(APP_DATA_PATH, "config.json");
    private static final File APP_FILE = new File(APP_DATA_PATH, "app.json");
    private static final File IMAGE_FOLDER = new File(APP_DATA_PATH, "images");
    private static final Config INSTANCE = new Config();

    private final List<ConfigZoomChangeListener> zoomChangeListeners = new ArrayList<>();

    private JSONObject config;
    private JSONObject apps;

    public Config() {
        createFilesInAppData();
        readFilesToJsonObjects();
    }

    public static Config getConfig() {
        return INSTANCE;
    }

    public void addApp(Image icon, String name, String launchPath, String description) {
        addApp(icon, name, launchPath, description, true);
    }

    public void addApp(String name, String launchPath, String description) {
        addApp(null, name, launchPath, description, false);
    }

    public void addApp(AppInfo info) {
        addApp(info.icon(), info.appName(), info.launchPath(), info.description());
    }

    public void removeAppByName(String name) {
        removeApp(findAppByName(name));
    }

    public void removeAppByID(String id) {
        removeApp(findAppById(id));
    }

    public void removeAppByInfo(AppInfo info) throws NoSuchAlgorithmException {
        removeAppByID(generateSHA256(info.appName() + info.launchPath() + info.description()));
    }

    public void removeAllApps() {
        apps = new JSONObject();
        apps.put("apps", new JSONArray());
        writeJsonToFile(APP_FILE, apps);
    }

    public AppInfo[] getAppList(int count) {
        JSONArray appsArray = apps.getJSONArray("apps");
        int arraySize = Math.min(count, appsArray.size());
        AppInfo[] appList = new AppInfo[arraySize];

        for(int i = 0; i < arraySize; i++) {
            JSONObject app = appsArray.getJSONObject(i);
            appList[i] = new AppInfo(
                    app.getString("name"),
                    app.getString("launchPath"),
                    loadImage(app.getString("id")),
                    app.getString("id"),
                    app.getString("description")
            );
        }

        return appList;
    }

    public void addOnZoomChangedListener(ConfigZoomChangeListener litener) {
        zoomChangeListeners.add(litener);
    }

    private void notifyZoomListeners(double value) {
        for(ConfigZoomChangeListener listener : zoomChangeListeners) {
            listener.onConfigChanged(value);
        }
    }


    public AppInfo[] getAppList() {
        return getAppList(Integer.MAX_VALUE);
    }

    public int getMouseOffset() {
        return getConfigValue("mouse_offset", 5);
    }

    public double getZoom() {
        return getConfigValue("zoom", 1.0);
    }

    public void setZoom(double zoom) {
        config.put("zoom", zoom);
        writeJsonToFile(CONFIG_FILE, config);
        notifyZoomListeners(zoom);
    }

    private int getConfigValue(String key, int defaultValue) {
        return config.containsKey(key)? (int) config.get(key): setConfigDefaultValue(key, defaultValue);
    }

    private double getConfigValue(String key, double defaultValue) {
        return config.containsKey(key)? parseConfigDouble(key): setConfigDefaultValue(key, defaultValue);
    }

    private double parseConfigDouble(String key) {
        Object value = config.get(key);
        return value instanceof Number? ((Number) value).doubleValue(): Double.parseDouble(value.toString());
    }

    private int setConfigDefaultValue(String key, int defaultValue) {
        config.put(key, defaultValue);
        writeJsonToFile(CONFIG_FILE, config);
        return defaultValue;
    }

    private double setConfigDefaultValue(String key, double defaultValue) {
        config.put(key, defaultValue);
        writeJsonToFile(CONFIG_FILE, config);
        return defaultValue;
    }

    private void addApp(Image icon, String name, String launchPath, String description, boolean saveIcon) {
        try {
            String id = generateSHA256(name + launchPath + description);
            String iconFileName = saveIcon? saveImageToFile(icon, id): id;
            saveAppInfo(name, launchPath, description, iconFileName);
        } catch(IOException | NoSuchAlgorithmException e) {
            showErrorAlert("An error occurred while adding app", e.getMessage());
        }
    }

    private String generateSHA256(String input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(input.getBytes());
        StringBuilder hexString = new StringBuilder(2 * hash.length);

        for(byte b : hash) hexString.append(String.format("%02x", b));
        return hexString.toString();
    }

    private String saveImageToFile(Image image, String name) throws IOException {
        if(IMAGE_FOLDER.mkdirs()) System.out.println("Image directory created successfully.");

        String iconFileName = name + ".png";
        File iconFile = new File(IMAGE_FOLDER, iconFileName);
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        ImageIO.write(bImage, "png", iconFile);

        return iconFileName;
    }

    private void saveAppInfo(String name, String launchPath, String description, String id) throws IOException {
        JSONObject newApp = new JSONObject();
        newApp.put("id", id);
        newApp.put("name", name);
        newApp.put("launchPath", launchPath);
        newApp.put("description", description);

        apps.getJSONArray("apps").add(newApp);
        writeJsonToFile(APP_FILE, apps);
        System.out.println("App added successfully.");
    }

    private void createFilesInAppData() {
        File appDataDir = new File(APP_DATA_PATH);
        if(appDataDir.mkdirs()) System.out.println("AppData directory created successfully.");

        createFileIfNotExists(CONFIG_FILE);
        createFileIfNotExists(APP_FILE);
    }

    private void createFileIfNotExists(File file) {
        if(!file.exists()) {
            writeJsonToFile(file, new JSONObject());
            System.out.println(file.getName() + " created successfully.");
        } else {
            System.out.println(file.getName() + " already exists.");
        }
    }

    private void readFilesToJsonObjects() {
        config = readJsonFromFile(CONFIG_FILE);
        apps = readJsonFromFile(APP_FILE);

        if(config == null) {
            config = new JSONObject();
            writeJsonToFile(CONFIG_FILE, config);
        }

        if(apps == null) {
            apps = new JSONObject();
            apps.put("apps", new JSONArray());
            writeJsonToFile(APP_FILE, apps);
        } else if(apps.getJSONArray("apps") == null) {
            apps.put("apps", new JSONArray());
            writeJsonToFile(APP_FILE, apps);
        }
    }

    private JSONObject readJsonFromFile(File file) {
        try {
            if(file.exists()) {
                String content = new String(Files.readAllBytes(file.toPath()));
                return JSON.parseObject(content);
            } else {
                System.out.println(file.getName() + " does not exist.");
                return new JSONObject();
            }
        } catch(IOException e) {
            showErrorAlert("An error occurred while reading file " + file.getName(), e.getMessage());
            return new JSONObject();
        }
    }

    private void writeJsonToFile(File file, JSONObject jsonObject) {
        try(FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(jsonObject.toJSONString());
        } catch(IOException e) {
            showErrorAlert("An error occurred while writing to file " + file.getName(), e.getMessage());
        }
    }

    private Image loadImage(String iconFileName) {
        File iconFile = new File(IMAGE_FOLDER, iconFileName);
        if(iconFile.exists()) return new Image(iconFile.toURI().toString());
        else return new Image(String.valueOf(App.class.getResource("image/fileNotFound.jpg")));
    }

    private Optional<JSONObject> findAppByName(String name) {
        return apps.getJSONArray("apps").stream()
                .map(obj -> (JSONObject) obj)
                .filter(app -> app.getString("name").equals(name))
                .findFirst();
    }

    private Optional<JSONObject> findAppById(String id) {
        return apps.getJSONArray("apps").stream()
                .map(obj -> (JSONObject) obj)
                .filter(app -> app.getString("id").equals(id))
                .findFirst();
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private void removeApp(Optional<JSONObject> appToRemove) {
        appToRemove.ifPresent(app -> {
            apps.getJSONArray("apps").remove(app);
            writeJsonToFile(APP_FILE, apps);
            System.out.println("App removed successfully.");
        });
    }

    private void showErrorAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
