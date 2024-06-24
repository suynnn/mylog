package org.mylog.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UploadFile {
    private String uploadFileName; // 유저가 업로드한 파일명
    private String storeFileName; // 서버 내부에서 관리하는 파일명
}
