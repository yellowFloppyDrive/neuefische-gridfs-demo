package de.neuefische.neuefischegridfsdemo;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping
    public FileMetadata uploadFile (
        @RequestParam("file") MultipartFile file
    ) throws IOException {
        return fileService.saveFile(file);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InputStreamResource> getFile (
        @PathVariable String id
    ) throws IOException {
        GridFsResource gridFsResource = fileService.getResource(id);

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(gridFsResource.getContentType()))
                .body(new InputStreamResource(gridFsResource.getInputStream()));
    }

    @GetMapping("/{id}/metadata")
    public FileMetadata getFileMetadata (
        @PathVariable String id
    ) {
        return fileService.getFileMetadata(id);
    }
}
