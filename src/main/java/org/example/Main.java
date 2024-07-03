package org.example;

import org.example.jct.AnalyzeService;
import org.example.jct.MigrateService;

public class Main {

    private static final AnalyzeService analyzeService = new AnalyzeService();
    private static final MigrateService migrateService = new MigrateService();

    public static void main(String[] args) {
        // 분석 및 변환
        if (args.length == 2) {
            String rulePath = args[0];
            String sourceDirectoryPath = args[1];
            analyzeService.analyze(sourceDirectoryPath);
            migrateService.migrate(sourceDirectoryPath);
        }
        // 분석만
        else if (args.length == 3 && args[0].equals("-a")) {
            String rulePath = args[1];
            String sourceDirectoryPath = args[2];
            analyzeService.analyze(sourceDirectoryPath);
        } else {
            System.out.println("====================================== usage ======================================" +
                    "| 분석 및 변환 : java -jar jct.jar [rule.json file path] [source directory path]     |" +
                    "| 분석만      : java -jar jct.jar -a [rule.json file path] [source directory path]  |" +
                    "====================================== usage ======================================");
        }
    }
}