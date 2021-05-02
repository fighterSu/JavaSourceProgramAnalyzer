package analyzer;

import java.io.*;
import java.util.Objects;
import java.util.Scanner;

/**
 * @author Platina
 */
public class ViewAnalysisResults {
    /**
     * 查看历史分析结果
     */
    public static void viewAnalysisResults() {
        File folder = new File("result");
        File[] files = folder.listFiles();
        if (!folder.exists() || Objects.requireNonNull(files).length == 0) {
            System.out.println("还没有分析结果!\n");
            return;
        }

        File[] resultsFiles = new File[files.length];
        int idOfResultFile = 0;

        for (File file : files) {
            if (file.getName().toLowerCase().endsWith("txt")) {
                resultsFiles[idOfResultFile++] = file;
            }
        }

        while (true) {
            idOfResultFile = 1;
            System.out.println("\n可以查看的结果文件有：");
            System.out.println("--------------------");

            for (File file : resultsFiles) {
                System.out.println(idOfResultFile + "." + file.getName());
                idOfResultFile++;
            }
            System.out.println("--------------------");

            System.out.print("选择要查看的结果文件(0表示放弃):");

            Scanner input = new Scanner(System.in);
            String choices = input.next();

            int length = resultsFiles.length;

            if (!choices.matches("[0-9]+") || Integer.parseInt(choices) > length) {
                System.out.println("输入无效，请输入[0-" + length + "]之间的整数值");
                continue;
            }

            if (Integer.parseInt(choices) == 0) {
                System.out.println("已返回主菜单！");
                break;
            }

            System.out.printf("\n第 %d 个结果文件信息如下：\n", Integer.parseInt(choices));

            String line;
            try (BufferedReader bufferedReader =
                         new BufferedReader(new FileReader(resultsFiles[Integer.parseInt(choices) - 1]))) {
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);
                }
                System.out.println("\n本次查看结束\n");
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("是否要继续查看其它分析结果文件,是则输入'Y'后按回车键，否则输入任意值按回车返回主菜单");
            if (input.next().charAt(0) != 'Y') {
                System.out.println("已返回主菜单!\n");
                break;
            }
        }
    }
}
