package website.chatx.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import website.chatx.core.common.CommonAuthContext;
import website.chatx.core.entities.FileUpEntity;
import website.chatx.core.exception.BusinessLogicException;
import website.chatx.core.mapper.FileUpMapper;
import website.chatx.dto.res.entity.FileUpEntityRes;
import website.chatx.repositories.jpa.FileUpJpaRepository;
import website.chatx.service.FileUpService;

import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class FileUpServiceImpl implements FileUpService {

    private final FileUpJpaRepository fileUpJpaRepository;

    private final CommonAuthContext authContext;

    private final AmazonS3 amazonS3;

    private final FileUpMapper fileUpMapper;

    @Value("${application.bucket.public}")
    private String BUCKET_PUBLIC;

    @Value("${cloud.aws.region.static}")
    private String REGION;

    private final Log LOGGER = LogFactory.getLog(getClass());

    @Override
    public FileUpEntityRes uploadFile(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String fileExtension = "";
        if (originalFilename != null) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        }
        if (fileExtension.isEmpty()) {
            throw new BusinessLogicException(-8);
        }

        FileUpEntity fileUpEntity = fileUpJpaRepository.save(FileUpEntity.builder()
                .name(originalFilename.toLowerCase())
                .size(file.getSize())
                .contentType(file.getContentType())
                .user(authContext.getUserEntity())
                .build());
        fileUpEntity.setUrl("https://s3."
                + REGION
                + ".amazonaws.com/"
                + BUCKET_PUBLIC
                + "/"
                + authContext.getUserEntity().getId()
                + "/"
                + fileUpEntity.getId()
                + "."
                + fileExtension.toLowerCase());
        fileUpJpaRepository.save(fileUpEntity);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        LOGGER.info("content-type: " + file.getContentType());
        try {
            amazonS3.putObject(BUCKET_PUBLIC,
                    authContext.getUserEntity().getId()
                            + "/"
                            + fileUpEntity.getId()
                            + "."
                            + fileExtension.toLowerCase(),
                    file.getInputStream(),
                    metadata);
        } catch (IOException e) {
            throw new BusinessLogicException(-9);
        }
        return fileUpMapper.toFileUpRes(fileUpEntity);
    }

    @Override
    public void deleteFile(String fileId) {
        FileUpEntity fileUpEntity = fileUpJpaRepository.findByIdAndUser(fileId, authContext.getUserEntity());
        if (fileUpEntity == null) {
            throw new BusinessLogicException(-10);
        }
        fileUpJpaRepository.deleteById(fileId);
        amazonS3.deleteObject(BUCKET_PUBLIC,
                authContext.getUserEntity().getId()
                        + "/"
                        + fileUpEntity.getId()
                        + fileUpEntity.getName().substring(fileUpEntity.getName().lastIndexOf("."))
        );
    }
}