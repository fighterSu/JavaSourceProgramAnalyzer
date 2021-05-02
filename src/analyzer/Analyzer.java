package analyzer;

import java.io.*;
import java.util.Objects;

/**
 * @author Platina
 */
public class Analyzer {
    private static int totalNumberOfLines;
    private static int totalNumberOfBlanks;
    private static int numberOfJavaFiles;
    private static long totalSizeOfJavaFiles;
    private static final StringBuilder STRING_BUILDER = new StringBuilder();
    private static final String INDENTATION = "    ";

    /**
     * 分析提供的文件夹里面的 Java 源文件
     *
     * @param targetPath is target path of analysis
     */

    public static void analyzeJavaSource(String targetPath) {
        File file = new File(targetPath);
        if (!file.isDirectory()) {
            System.out.printf("错误：[%s]不是目录名或不存在！\n", targetPath);
            System.out.println("\n即将返回主菜单\n");
            return;
        }

        System.out.println("分析结果如下所示：");

        resetAnalyzer();
        analyzeDirectory(file, 0);
        outputResults(targetPath);
        storeAnalyzedData(file);
    }

    /**
     * 分析位于第 depth 层子目录的文件夹
     *
     * @param folder is target folder
     * @param depth  is the depth of the folder
     */
    private static void analyzeDirectory(File folder, int depth) {
        if (folder.listFiles() != null) {
            STRING_BUILDER.append(INDENTATION.repeat(Math.max(0, depth)));
            String directoryInformation = "+" + folder.getName() + '\n';
            STRING_BUILDER.append(directoryInformation);

            for (File file : Objects.requireNonNull(folder.listFiles())) {
                if (file.isDirectory()) {
                    analyzeDirectory(file, depth + 1);
                }
            }

            for (File file : Objects.requireNonNull(folder.listFiles())) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".java")) {
                    numberOfJavaFiles++;
                    totalSizeOfJavaFiles += file.length();
                    parseJavaFile(file, depth + 1);
                }
            }
        } else {
            System.out.println("获取文件夹信息失败");
            System.exit(0);
        }
    }

    /**
     * 分析指定的Java文件
     *
     * @param file is target file of analysis
     */
    private static void parseJavaFile(File file, int depth) {
        int numberOfLines = 0;
        int numberOfBlank = 0;

        String line;
        try (BufferedReader reader =
                     new BufferedReader(new FileReader(file))) {
            while ((line = reader.readLine()) != null) {
                if ("".equals(line)) {
                    numberOfBlank++;
                }
                numberOfLines++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        totalNumberOfLines += numberOfLines;
        totalNumberOfBlanks += numberOfBlank;

        STRING_BUILDER.append(INDENTATION.repeat(Math.max(0, depth)));

        String analysisResult = String.format("-%-40s Total: %10d," +
                        "Blank:%10d, " +
                        "%10d Bytes\n",
                file.getName(), numberOfLines, numberOfBlank, file.length());

        STRING_BUILDER.append(analysisResult);
    }

    /**
     * 输出分析结果
     *
     * @param targetPath is the target path for analyzer
     */

    private static void outputResults(String targetPath) {
        System.out.printf("[%s]  Result:\n|\nFiles detail:\n", targetPath);
        STRING_BUILDER.append("Total：\n");
        STRING_BUILDER.append(String.format("       %5d Java Files\n",
                numberOfJavaFiles));
        STRING_BUILDER.append(String.format("       %5d lines in files\n",
                totalNumberOfLines));
        STRING_BUILDER.append(String.format("       %5d blank lines\n",
                totalNumberOfBlanks));
        STRING_BUILDER.append(String.format("       %5d bytes", totalSizeOfJavaFiles));
        System.out.println(STRING_BUILDER);
        System.out.println("分析完毕！");
    }

    /**
     * 将分析结果存入txt文件
     *
     * @param file is the targetFile for analysis
     */
    private static void storeAnalyzedData(File file) {
        File folder = new File("result");
        if (!folder.exists() && !folder.mkdir()) {
            System.out.println("创建文件夹失败，数据保存失败！");
            System.exit(0);
        }

        try (
                BufferedWriter writer =
                        new BufferedWriter(
                                new FileWriter("result\\" + file.getName() + ".txt"))
        ) {
            writer.write("[" + file.getAbsolutePath() + "]  Result:\n\nFiles " +
                    "detail:\n");
            writer.write(STRING_BUILDER.toString());
            System.out.println("分析结果保存成功\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 重置分析器
     */
    private static void resetAnalyzer() {
        totalNumberOfLines = 0;
        totalNumberOfBlanks = 0;
        numberOfJavaFiles = 0;
        totalSizeOfJavaFiles = 0;
        STRING_BUILDER.setLength(0);
    }

}
