package com.bitten.file;
import java.io.*;

/**
 * @Author : YangY
 * @Description : 文件拷贝
 * @Time : Created in 16:33 2019/3/31
 */
public class FileCopyUtil {

    public static void main(String[] args) {
        //destFilePath必须是一个具体文件的路径，不能为文件夹；
        cp("E:\\文件测试\\测试\\hello.txt","E:\\文件测试\\测试2\\test.txt");
    }

    /**
     *
     * @param sourceFilePath:源文件
     * @param destFilePath：目的文件
     */
    public static void cp(String sourceFilePath,String destFilePath) {
        checkArgumentNotNull(sourceFilePath,"souce file path must not be NULL!!");
        checkArgumentNotNull(destFilePath,"dest file path must not be NULL!!");
        File souceFile = new File(sourceFilePath);
        checkFile(souceFile,"SouceFile must not be null!!");
        File destFile = new File(destFilePath);
        checkDestFile(destFile);
        dataCopy(souceFile,destFile);

    }
    public static void checkArgumentNotNull(Object args, String msg) {
        if(args == null ) {
            throw new IllegalArgumentException(msg);
        }
    }

    private static void checkDestFile(File file) {
        //如果父目录不存在，则会copy失败，为了避免这，如果不存在，我们给它创建对应的父目录；
        if(!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
    }

    private static void checkFile(File file,String msg) {
        //源文件不能为空，而且必须为文件，是目录的话就不行，得抛出异常；
        if(file == null || !file.exists() ) {
            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * 真正实现拷贝得方法
     * @param souce
     * @param dest
     */
    private static void dataCopy(File souce, File dest) {
        long start = System.currentTimeMillis();
        try(InputStream in = new FileInputStream(souce);
            OutputStream out = new FileOutputStream(dest);
        ){
            byte[] buff = new byte[1024*1024];  //每次传输1024*1024个字节，即1M
            int length = -1;
            //当read方法返回值为-1时，代表已经将数据读取完；
            while((length=in.read(buff)) != -1) {
                out.write(buff,0,length);
            }
            out.flush();
            long end = System.currentTimeMillis();
            System.out.println("COPY SUCCESS!!!"+"耗时："+(end-start));
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
