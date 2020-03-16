package oop.polymorphism.asis;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CloudFileManager {

    private final FakeRepository db;
    private final BoxService boxService;
    private final DropBoxClient dropBoxClient;

    public CloudFileManager(FakeRepository db, BoxService boxService, DropBoxClient dropBoxClient) {
        this.db = db;
        this.boxService = boxService;
        this.dropBoxClient = dropBoxClient;
    }

    /**
     * 파일 조회 기능
     */
    public List<FileInfo> getFileInfos(CloudId cloudId) {
        if (cloudId == CloudId.DROPBOX) {
            List<DbFile> dbFiles = db.getFiles();
            List<FileInfo> result = new ArrayList<>();
            for (DbFile dbFile : dbFiles) {
                FileInfo fi = new FileInfo();
                fi.setCloudId(CloudId.DROPBOX);
                fi.setFileId(dbFile.getFileId());

                // 생략..

                result.add(fi);
            }
            return result;
        } else if (cloudId == CloudId.BOX) {
            BoxService boxService = this.boxService;

            // 구현 생략..

        }
        return new ArrayList<>();
    }

    /**
     * 다운로드 기능
     */
    public void download(FileInfo file, File localTarget) throws IOException {
        if (file.getCloudId() == CloudId.DROPBOX) {
            FileOutputStream out = new FileOutputStream(localTarget);
            dropBoxClient.copy(file.getFileId(), out);
            out.close();
        } else if (file.getCloudId() == CloudId.BOX) {
            InputStream is = boxService.getInputStream(file.getFileId());
            FileOutputStream out = new FileOutputStream(localTarget);
            CopyUtil.copy(is, out);
        }
    }

    /**
     * 업로드 기능
     */
    public void upload(FileInfo file, CloudId cloudId) throws IOException {
        if (cloudId == CloudId.DROPBOX) {
            // 구현 생략
        } else if (cloudId == CloudId.BOX) {
            // 구현 생략
        }
    }

    /**
     * 삭제 기능
     */
    public void delete(String fieldId, CloudId cloudId) throws IOException {
        if (cloudId == CloudId.DROPBOX) {
            // 구현 생략
        } else if (cloudId == CloudId.BOX) {
            // 구현 생략
        }
    }

    /**
     * 검색 기능
     */
    public void search(String query, CloudId cloudId) throws IOException {
        if (cloudId == CloudId.DROPBOX) {
            // 구현 생략
        } else if (cloudId == CloudId.BOX) {
            // 구현 생략
        }
    }

}
