package org.example;

import lombok.RequiredArgsConstructor;
import org.example.jct.service.AnalyzeService;
import org.example.jct.service.MigrateService;
import org.example.jct.service.RuleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class Main implements CommandLineRunner {

    private final AnalyzeService analyzeService;
    private final MigrateService migrateService;
    private final RuleService ruleService;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) {
        // 분석 및 변환
        if (args.length == 2) {
            String rulePath = args[0];
            ruleService.initialize(rulePath);
            String sourceDirectoryPath = args[1];
            analyzeService.analyze(sourceDirectoryPath);
            migrateService.migrate(sourceDirectoryPath);
        }
        // 분석만
        else if (args.length == 3 && args[0].equals("-a")) {
            String rulePath = args[1];
            ruleService.initialize(rulePath);
            String sourceDirectoryPath = args[2];
            analyzeService.analyze(sourceDirectoryPath);
        } else {
            System.out.println("====================================== usage ======================================\n" +
                    "| 분석 및 변환 : java -jar jct.jar [rule.json file path] [source directory path]     \n" +
                    "| 분석만      : java -jar jct.jar -a [rule.json file path] [source directory path]  \n" +
                    "====================================== usage ======================================");
        }
        System.out.println("완료되었습니다.");
    }
}