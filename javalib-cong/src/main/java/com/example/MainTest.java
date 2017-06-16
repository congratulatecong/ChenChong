package com.example;

import java.io.File;

@SuppressWarnings("unchecked")
public class MainTest {

    public static void main(String[] args) {
//        System.out.println(formatNum("34234"));
//        System.out.println(Math.pow(10,-6));
//        System.out.println(new SimpleDateFormat("M月dd日HH:mm").format(1469614064));
//        deleteTempVideoFile();
        System.out.println(DateTimeUtil.formatTime("1495700311"));
    }

    public static void deleteTempVideoFile() {
        File videoDir = new File("E://temp");
        File[] videoFiles = videoDir.listFiles();
        if (videoFiles != null && videoFiles.length > 0) {
            for (File file : videoFiles) {
                if (file.exists() && file.isFile()) {
                    System.out.println(file.getPath());
                    try {
                        if (file.getName().endsWith(".tmp")) {
                            String renameFilePath = file.getPath().substring(0, file.getPath().length() - 4);
                            File renamedTempFile = new File(renameFilePath);
                            System.out.println(renamedTempFile.getPath());
                            System.out.println(renamedTempFile.getName());
                            if (file.renameTo(renamedTempFile)) {
                                renamedTempFile.delete();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static String formatNum(String number) {
        if (number == null) {
            return "10";
        }

        int num;
        try {
            num = Integer.parseInt(number);
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
        return formatNum(num);
    }

    public static String formatNum(Integer number) {
        if (number == null) {
            return "0";
        }
        if (number < 0) {
            return "0";
        } else if (number >= 0 && number < 10000) {
            return Integer.toString(number);
        } else {
            int temp = (int) (number / 10000.0 * 10);
            return (temp % 10) == 0 ? temp / 10 + "万" : temp / 10.0 + "万";
        }
    }

}
