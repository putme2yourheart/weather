package com.nocompany.frank.weather.utils;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.Nullable;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"></uses-permission>
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"></uses-permission>
 */

/**
 * Created by Frank on 2016/12/29 0029.
 * 内存卡相关的类
 * <p>
 * <ui>
 * <li>{@link #isSDCardMounted()} 是否挂载了内存卡</li>
 * <li>{@link #getSDCardBaseDir()} 获取SDCard的根目录</li>
 * <li>{@link #getSDCardFreeSize(int)} 获取内置SD卡的剩余空间大小 </li>
 * <li>{@link #getSDCardAvailableSize(int)} 获取内置SD卡的可用空间大小</li>
 * <li>{@link #getSDCardSize(int)} 获取内置SD卡的完整空间大小</li>
 * <li>{@link #getSDCardPublicDir(String)} 获取SDCard公有目录路径</li>
 * <li>{@link #getSDCardPrivateCacheDir(Context)} 获取内置SD卡私有Cache目录的路径</li>
 * <li>{@link #getSDCardPrivateFilesDir(Context, String)} 获取内置SD卡私有Files目录的路径</li>
 * <li>{@link #removeFileFromSDCard(String)} 从sdcard中删除文件</li>
 * <li>{@link #isFileExists(String)} 判断内置SD卡文件是否存在</li>
 * <li>{@link #saveFileToSDCardPublicDir(byte[], String, String)} 往内置SD卡的公有目录下保存文件</li>
 * <li>{@link #saveFileToSDCardCustomDir(byte[], String, String)} 往内置SD卡的自定义目录下保存文件</li>
 * <li>{@link #saveFileToSDCardPrivateFilesDir(Context, byte[], String, String)} 往内置SD卡的私有Files目录下保存文件</li>
 * <li>{@link #saveFileToSDCardPrivateCacheDir(Context, byte[], String)} 往内置SD卡的私有Cache目录下保存文件</li>
 * <li>{@link #loadFileFromSDCard(String)} 从内置SD卡获取文件</li>
 * </ui>
 */

public class SDCardUtils {

    // 大小为byte
    public static final int SIZE_BYTE = 0;
    // 大小为kb
    public static final int SIZE_KB = 1;
    // 大小为mb
    public static final int SIZE_MB = 2;

    /**
     * 是否挂载了内存卡
     *
     * @return boolean
     */
    public static boolean isSDCardMounted() {
        return android.os.Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取SDCard的根目录
     *
     * @return String
     */
    public static String getSDCardBaseDir() {
        if (isSDCardMounted()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return null;
    }

    /**
     * 获取内置SD卡的剩余空间大小
     *
     * @param unit 返回内存大小的单位，byte，kb，mb
     * @return long
     */
    public static long getSDCardFreeSize(int unit) {
        if (isSDCardMounted()) {
            // 获取SDCard卡文件路径
            StatFs statFs = new StatFs(getSDCardBaseDir());

            long blockSize;
            long blockCount;

            // 大于api 18
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
                blockSize = statFs.getBlockSizeLong();
                blockCount = statFs.getFreeBlocksLong();
            } else {
                blockSize = statFs.getBlockSize();
                blockCount = statFs.getFreeBlocks();
            }

            if (unit == SIZE_BYTE) {
                return blockSize * blockCount;
            } else if (unit == SIZE_KB) {
                return blockSize * blockCount / 1024;
            } else {
                return blockSize * blockCount / 1024 / 1024;
            }
        }
        return 0;
    }

    /**
     * 获取内置SD卡的可用空间大小
     *
     * @param unit 返回内存大小的单位，byte，kb，mb
     * @return long
     */
    public static long getSDCardAvailableSize(int unit) {
        if (isSDCardMounted()) {
            // 获取SDCard卡文件路径
            StatFs statFs = new StatFs(getSDCardBaseDir());

            long blockSize;
            long blockCount;
            // 大于api 18
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
                blockSize = statFs.getBlockSizeLong();
                blockCount = statFs.getAvailableBlocksLong();
            } else {
                blockSize = statFs.getBlockSize();
                blockCount = statFs.getAvailableBlocks();
            }

            if (unit == SIZE_BYTE) {
                return blockSize * blockCount;
            } else if (unit == SIZE_KB) {
                return blockSize * blockCount / 1024;
            } else {
                return blockSize * blockCount / 1024 / 1024;
            }
        }
        return 0;
    }

    /**
     * 获取内置SD卡的完整空间大小
     *
     * @param unit 返回内存大小的单位，byte，kb，mb
     * @return long
     */
    public static long getSDCardSize(int unit) {
        if (isSDCardMounted()) {
            StatFs statFs = new StatFs(getSDCardBaseDir());

            long blockSize;
            long blockCount;

            // 大于api 18
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
                blockSize = statFs.getBlockSizeLong();
                blockCount = statFs.getBlockCountLong();
            } else {
                blockSize = statFs.getBlockSize();
                blockCount = statFs.getBlockCount();
            }

            if (unit == SIZE_BYTE) {
                return blockSize * blockCount;
            } else if (unit == SIZE_KB) {
                return blockSize * blockCount / 1024;
            } else {
                return blockSize * blockCount / 1024 / 1024;
            }
        }
        return 0;
    }

    /**
     * 获取内置SD卡公有目录路径
     *
     * @param type 获取类型
     * @return String
     */
    public static String getSDCardPublicDir(String type) {
        return Environment.getExternalStoragePublicDirectory(type).toString();
    }

    /**
     * 获取内置SD卡私有Cache目录的路径
     *
     * @param context Context
     * @return String
     */
    public static String getSDCardPrivateCacheDir(Context context) {
        File file = context.getExternalCacheDir();
        if (file != null) {
            return file.getAbsolutePath();
        } else {
            return null;
        }
    }

    /**
     * 获取内置SD卡私有Files目录的路径
     *
     * @param context Context
     * @param type    获取类型
     * @return String
     */
    public static String getSDCardPrivateFilesDir(Context context, String type) {
        File file = context.getExternalFilesDir(type);
        if (file != null) {
            return file.getAbsolutePath();
        } else {
            return null;
        }
    }

    /**
     * 从sdcard中删除文件
     *
     * @param filePath 文件路径
     * @return boolean
     */
    public static boolean removeFileFromSDCard(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.delete();
    }

    /**
     * 判断内置SD卡文件是否存在
     *
     * @param filePath 文件路径
     * @return boolean
     */
    public static boolean isFileExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * 往内置SD卡的公有目录下保存文件
     *
     * @param data     要保存的数据
     * @param type     保存的类型
     * @param filename 文件名
     * @return boolean
     */
    public static boolean saveFileToSDCardPublicDir(byte[] data, String type, String filename) {
        BufferedOutputStream bos = null;
        if (isSDCardMounted()) {
            File file = Environment.getExternalStoragePublicDirectory(type);
            try {
                // 公有目录下文件输出流
                bos = new BufferedOutputStream(new FileOutputStream(new File(file, filename)));
                // 写入数据
                bos.write(data);
                // 刷新缓冲区
                bos.flush();

                return true;

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bos != null) {
                        bos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 往内置SD卡的自定义目录下保存文件
     *
     * @param data     数据
     * @param dir      自定义目录名
     * @param filename 文件名
     * @return boolean
     */
    public static boolean saveFileToSDCardCustomDir(byte[] data, String dir, String filename) {
        BufferedOutputStream bos = null;

        if (isSDCardMounted()) {
            File file = new File(getSDCardBaseDir() + File.separator + dir);
            L.i("hhh", file.toString());
            if (!file.exists()) {
                // 递归创建自定义目录
                if (file.mkdirs()) {
                    // 创建文件目录失败，返回false
                    return false;
                }
            }
            try {
                bos = new BufferedOutputStream(new FileOutputStream(new File(file, filename)));
                // 写入数据
                bos.write(data);
                // 刷新缓冲区
                bos.flush();

                return true;

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bos != null) {
                    try {
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }

    /**
     * 往内置SD卡的私有Files目录下保存文件
     *
     * @param context  Context
     * @param data     数据
     * @param type     写入类型
     * @param filename 文件名
     * @return boolean
     */
    public static boolean saveFileToSDCardPrivateFilesDir(Context context, byte[] data, String type, String filename) {
        BufferedOutputStream bos = null;

        if (isSDCardMounted()) {
            File file = context.getExternalFilesDir(type);
            try {
                bos = new BufferedOutputStream(new FileOutputStream(new File(file, filename)));
                // 写入数据
                bos.write(data);
                // 刷新缓冲区
                bos.flush();

                return true;

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bos != null) {
                    try {
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }

    /**
     * 往内置SD卡的私有Cache目录下保存文件
     *
     * @param contexts Context
     * @param data     数据
     * @param filename 文件名
     * @return boolean
     */
    public static boolean saveFileToSDCardPrivateCacheDir(Context contexts, byte[] data, String filename) {
        BufferedOutputStream bos = null;

        if (isSDCardMounted()) {
            File file = contexts.getExternalCacheDir();
            try {
                bos = new BufferedOutputStream(new FileOutputStream(new File(file, filename)));
                // 写入数据
                bos.write(data);
                // 刷新缓冲区
                bos.flush();

                return true;

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bos != null) {
                    try {
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }

    /**
     * 从内置SD卡获取文件
     *
     * @param fileDir 文件目录
     * @return byte[]
     */
    @Nullable
    public static byte[] loadFileFromSDCard(String fileDir) {
        BufferedInputStream bis = null;
        byte[] buffer;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        File file = new File(fileDir);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 获取文件大小
        long len = file.length();

        try {
            bis = new BufferedInputStream(new FileInputStream(new File(fileDir)));
            // 文件的大小小于8M，分配文件大小的长度
            // 防止溢出
            if (len >= Integer.MAX_VALUE) {
                buffer = new byte[8 * 1024];
            } else if (len < 8 * 1024) {
                buffer = new byte[len == 0 ? 1 : (int)len];
            } else {
                buffer = new byte[8 * 1024];
            }

            int c;
            while ((c = bis.read(buffer)) != -1) {
                baos.write(buffer, 0, c);
                baos.flush();
            }

            // 转成byte[]
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
