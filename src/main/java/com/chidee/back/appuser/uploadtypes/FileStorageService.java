package com.chidee.back.appuser.uploadtypes;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    @Autowired
    private FileDBRepository fileDBRepository;
//    private AppUserRepository appUserRepository;
//    private Applicant applicant;
//    private FileDB fileDB;

    public FileDB store(MultipartFile file1, MultipartFile file2) throws IOException {
        String passportPhotoFileName = StringUtils.cleanPath(file1.getOriginalFilename());
        String resumeFileName = StringUtils.cleanPath(file2.getOriginalFilename());

        FileDB FileDb = new FileDB(passportPhotoFileName, file1.getContentType(),file1.getBytes(),resumeFileName, file2.getContentType(), file2.getBytes());
        return fileDBRepository.save(FileDb);
    }

    public Stream<FileDB> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }

//    public FileDB getFile(String fileId) throws IOException {
//        return fileDBRepository.findById(fileId).get();
//    }

    public FileDB getFile(String fileId) throws IOException {
        FileDB fileDb = null;
        Optional<FileDB> opFileDB = fileDBRepository.findById(fileId);

        if (opFileDB.isPresent()) {
            fileDb = opFileDB.get();
            return fileDb;
        }
        return fileDb;
    }


//.orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));

}
