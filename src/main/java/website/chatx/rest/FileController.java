package website.chatx.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import website.chatx.core.common.CommonResponse;
import website.chatx.service.FileUpService;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/files")
public class FileController {

    private final FileUpService fileService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse> uploadFile(@RequestPart MultipartFile file) {
        return CommonResponse.success(fileService.uploadFile(file));
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<CommonResponse> deleteFile(@PathVariable String fileId) {
        fileService.deleteFile(fileId);
        return CommonResponse.success("");
    }
}
