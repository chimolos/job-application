package com.chidee.back.appuser.uploadtypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/uploads")
public class FileController {

    @Autowired
    FileStorageService storageService;

    @Autowired
    FileDBRepository fileDBRepository;

    @GetMapping
    public ResponseEntity<List<ResponseFile>> getListFiles() {
        List<ResponseFile> files = storageService.getAllFiles().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/uploads/")
                    .path(dbFile.getId())
                    .toUriString();

            return new ResponseFile(
                    fileDownloadUri,
                    dbFile.getPassportPhoto(),
                    dbFile.getPassportType(),
                    dbFile.getPassportData().length,
                    dbFile.getResume(),
                    dbFile.getResumeType(),
                    dbFile.getResumeData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

//    @GetMapping("/{fileId}")
//    public Optional<FileDB> findUpload(@PathVariable String fileId) {
//        return fileDBRepository.findById(fileId);
//    }
    @GetMapping("/{fileId}")
    public String findUpload(@PathVariable("fileId") String fileId, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String check = (String) session.getAttribute("userName");
        if (check.isEmpty()) {
            return "login_form";
        }
        FileDB fileDB = fileDBRepository.findById(fileId).get();
        model.addAttribute("file", fileDB);

        return "view_userfiles";
    }

    @GetMapping("/{fileId}/passport")
    public ResponseEntity<Resource> downloadPassport(@PathVariable String fileId, HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        String check = (String) session.getAttribute("userName");
        if (check.isEmpty()) {
            return null;
        }
//        load file from database
        FileDB fileDB = storageService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileDB.getPassportType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getPassportPhoto() + "\"")
                .body(new ByteArrayResource(fileDB.getPassportData()));
    }

    @GetMapping("/{fileId}/resume")
    public ResponseEntity<Resource> downloadResume(@PathVariable String fileId, HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        String check = (String) session.getAttribute("userName");
        if (check.isEmpty()) {
            return null;
        }
//        load file from database
        FileDB fileDB = storageService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileDB.getResumeType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getResume() + "\"")
                .body(new ByteArrayResource(fileDB.getResumeData()));
    }
}


























































































































