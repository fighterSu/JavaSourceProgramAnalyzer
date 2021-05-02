package main;

import analyzer.Analyzer;
import analyzer.ViewAnalysisResults;
import java.util.Scanner;

/**
 * @author Platina
 */
public class Menu {
    public static void menu(){
        while(true) {
            System.out.println("--------MENU---------");
            System.out.println("1. 分析目录中的源程序文件");
            System.out.println("2. 查看分析结果");
            System.out.println("0. 退出程序");
            System.out.println("---------------------");
            System.out.print("请选择：");

            Scanner input = new Scanner(System.in);

            String choices = input.next();

            if(!choices.matches("[0-2]")){
                System.out.println("输入无效，请输入0-2之间的整数值\n");
                continue;
            }

            switch (Integer.parseInt(choices)){
                case 1:
                    System.out.println("\n\t分析目录中的源程序文件");
                    System.out.print("输入目录名称：");
                    String targetPath = input.next();
                    Analyzer.analyzeJavaSource(targetPath);
                    break;
                case 2:
                    ViewAnalysisResults.viewAnalysisResults();
                    break;
                default:
                    System.out.println("退出成功，感谢您的使用！");
                    System.exit(0);
            }
        }
    }
}
