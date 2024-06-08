package com.carbonara.game.managers;

import com.carbonara.game.main.GlobalSimpleApplication;
import com.jme3.asset.AssetNotFoundException;
import com.jme3.export.binary.BinaryExporter;
import com.jme3.scene.Node;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Optional;

public class GameSavesManager {
    private static boolean loadSavePoint = false; //
    private static final String pathSaves = "src/main/resources/Saves/";
    private static final String fileType = ".j3o";
    private static final BinaryExporter binaryExporter = new BinaryExporter();

    public static void setLoadSavePoint(boolean loadSavePoint) {
        GameSavesManager.loadSavePoint = loadSavePoint;
    }

    public static void save(Node data) throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy_HH-mm-ss");

        String timestamp = dateFormat.format(new Date());
        File file = new File(pathSaves + timestamp + fileType);
        try {
            binaryExporter.save(data, file);
            System.out.println("Файл успешно сохранён: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении файла: " + e.getMessage());
            throw e; // Перебрасываем исключение дальше
        }
    }

    public static void load(String savepointName){
        try {
            String path = "Saves/" + savepointName + fileType;
            Node data = (Node) GlobalSimpleApplication.getApp().getAssetManager().loadModel(path);
            dateLoaded = Optional.of(data);
            System.out.println("Файл успешно загружен: " + path);
        } catch (AssetNotFoundException e) {
            System.err.println("Файл не найден: " + e.getMessage());
            dateLoaded = Optional.empty(); // Обновляем, так как загрузка не удалась
        }
    }

    public static Optional<Node> dateLoaded = Optional.empty();

    public static Optional<Node> getDataLoaded() {
        return dateLoaded;
    }

    public static Optional<String> getLatestSaveFileName() {
        Optional<String> latestSaveFileName = Optional.empty();
        File savesDir = new File(pathSaves);
        File[] saveFiles = savesDir.listFiles((dir, name) -> name.endsWith(fileType));
        if (saveFiles == null || saveFiles.length == 0) {
            return latestSaveFileName; // Нет сохранённых файлов
        }
        Arrays.sort(saveFiles, Comparator.comparingLong(File::lastModified).reversed());
        String text = saveFiles[0].getName();
        latestSaveFileName =  Optional.of(text.substring(0, text.lastIndexOf('.')));

        return latestSaveFileName; // Возвращает имя самого последнего файла
    }

    public static boolean isLoadSavePoint() {
        return loadSavePoint;
    }
}
