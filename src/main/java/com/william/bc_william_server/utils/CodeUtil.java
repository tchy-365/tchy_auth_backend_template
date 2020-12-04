package com.william.bc_william_server.utils;


import java.util.Random;

public class CodeUtil {

    private static char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
    
    /**
     * 生成一个map集合
     * code为生成的验证码
     * codePic为生成的验证码BufferedImage对象
     * @return
     */
    public static String generateCode() {
        StringBuilder result = new StringBuilder();
        int length = codeSequence.length;
        Random random = new Random();
        for (int i = 0;i < 4;i++){
            int index = random.nextInt(length);
            if(i != 3){
                result.append(codeSequence[index] + ",");
            }else{
                result.append(codeSequence[index]);
            }
        }
        return result.toString();
    }

}