package website.chatx.service;

import org.springframework.web.multipart.MultipartFile;
import website.chatx.dto.res.entity.FileUpEntityRes;

public interface FileUpService {

    FileUpEntityRes uploadFile(MultipartFile file);

    void deleteFile(String fileId);
}
