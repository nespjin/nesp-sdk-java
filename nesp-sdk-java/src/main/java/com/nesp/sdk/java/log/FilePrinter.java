package com.aucma.lang.log;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.*;

public class FilePrinter implements Logger.Printer {

    private final String dirPath;

    /**
     * The name of the log file to write to.
     */
    private String fileName = "log";

    /**
     * The log compress file name format.
     */
    private CompressNameFormat compressNameFormat;
    private long fileMaxSize = 1024 * 1024 * 200; // 200M
    private long fileMaxCount = 10; // 10
    private final Object targetLogFileLock = new Object();

    public FilePrinter(String dirPath) {
        this.dirPath = dirPath;
    }

    public void setFileName(String name) {
        this.fileName = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setCompressNameFormat(CompressNameFormat format) {
        this.compressNameFormat = format;
    }

    private CompressNameFormat getCompressNameFormat() {
        if (compressNameFormat == null) {
            compressNameFormat = (String name, int num) ->
                    String.format("%s-%d.gz", name, num, Locale.CHINA);
        }
        return compressNameFormat;
    }

    public void setFileMaxSize(long size) {
        this.fileMaxSize = size;
    }

    public long getFileMaxSize() {
        return fileMaxSize;
    }

    public long getFileMaxCount() {
        return fileMaxCount;
    }

    public void setFileMaxCount(long count) {
        this.fileMaxCount = count;
    }

    @Override
    public void print(LogRecord record) throws IOException {
        if (dirPath == null) {
            throw new NullPointerException("dirPath is null");
        }

        File dir = new File(dirPath);

        if (!dir.exists() && !dir.mkdirs()) {
            throw new RuntimeException("mkdirs log dir(" + dir.getAbsolutePath() + ") failed");
        }

        if (!dir.isDirectory()) {
            throw new IllegalArgumentException("dirPath is not a directory");
        }

        final TaskLoopRunnable r = new TaskLoopRunnable(dir, record, fileMaxSize, fileMaxCount,
                fileName, targetLogFileLock, getCompressNameFormat());
        try {
            LoggerManager.getTaskLoopThread().addTask(r);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public interface CompressNameFormat {
        String format(String fileName, int num);
    }

    private static class TaskLoopRunnable implements Runnable {

        private final File dir;
        private final LogRecord record;
        private final long logFileMaxSize;
        private final long logFileMaxCount;
        private final String logFileName;
        private final Object targetLogFileLock;
        private final CompressNameFormat compressNameFormat;
        private long compressedSize = 0;

        public TaskLoopRunnable(File dir, LogRecord record, long logFileMaxSize, long logFileMaxCount,
                                String logFileName, Object targetLogFileLock, CompressNameFormat compressNameFormat) {
            this.dir = dir;
            this.record = record;
            this.logFileMaxSize = logFileMaxSize;
            this.logFileMaxCount = logFileMaxCount;
            this.logFileName = logFileName;
            this.targetLogFileLock = targetLogFileLock;
            this.compressNameFormat = compressNameFormat;
        }

        @Override
        public void run() {
            try {
                synchronized (targetLogFileLock) {
                    File targetLogFile = new File(dir, logFileName);
                    byte[] buffer = new byte[1024];
                    int readSize;

                    if (compressedSize == 0 && targetLogFile.exists()) {
                        try (InputStream is = new FileInputStream(targetLogFile)) {
                            while ((readSize = is.read(buffer)) != -1) {
                                compressedSize += GZIPUtils.compress(buffer, 0, readSize).length;
                            }
                        }
                    }

                    if (compressedSize > logFileMaxSize) {
                        compressLogFile(targetLogFile, buffer, dir);
                    }

                    try (OutputStream os = new FileOutputStream(targetLogFile, true)) {
                        byte[] b = (record.messageFormatted + "\n").getBytes(Charset.defaultCharset());
                        compressedSize += GZIPUtils.compress(b, 0, b.length).length;
                        os.write(b);
                        os.flush();
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void compressLogFile(File targetLogFile, byte[] buffer, File dir) throws IOException {
            File[] files = listDirectoryFilesSortByLastModifiedAsc(dir);
            int fileNum = files == null ? 0 : files.length;

            int readSize;
            if (fileNum >= logFileMaxCount) {
                assert files != null;
                final long l = fileNum - logFileMaxCount;
                for (int i = 0; i < l; i++) {
                    files[i].delete();
                }

                files = listDirectoryFilesSortByLastModifiedAsc(dir);
                fileNum = files == null ? 0 : files.length;

                childFileForeach:
                for (int i = 0; i < fileNum - 1; i++) {
                    File f1 = files[i];
                    if (f1.getName().equals(logFileName)) continue;
                    if (!f1.exists()) continue;
                    int num = i + 1;
                    File dest;
                    while ((dest = new File(dir, compressNameFormat.format(logFileName, num))).exists()) {
                        if (f1.getAbsolutePath().equals(dest.getAbsolutePath())) continue childFileForeach;
                        num++;
                    }
                    f1.renameTo(dest);
                }
            }

            int fileNameNum = fileNum;
            File zipFile;
            while ((zipFile = new File(dir, compressNameFormat.format(logFileName, fileNameNum))).exists()) {
                fileNameNum++;
            }

            try (InputStream is = new FileInputStream(targetLogFile);
                 OutputStream os = new FileOutputStream(zipFile)) {
                while ((readSize = is.read(buffer)) != -1) {
                    final byte[] compressBytes = GZIPUtils.compress(buffer, 0, readSize);
                    os.write(compressBytes);
                }
                os.flush();
                compressedSize = 0;
            }

            if (!targetLogFile.delete()) {
                throw new RuntimeException("delete log file failed");
            }
        }
    }

    private static File[] listDirectoryFilesSortByLastModifiedAsc(File dir) {
        if (dir.isFile()) return new File[0];
        File[] files = dir.listFiles();
        if (files != null) {
            Arrays.sort(files, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    final long l = o1.lastModified() - o2.lastModified();
                    if (l == 0) return 0;
                    return l < 0 ? -1 : 1;
                }
            });
        }
        return files;
    }
}
