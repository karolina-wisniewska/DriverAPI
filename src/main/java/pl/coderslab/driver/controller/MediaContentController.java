package pl.coderslab.driver.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.driver.entity.MediaContent;
import pl.coderslab.driver.message.ResponseFile;
import pl.coderslab.driver.message.ResponseMessage;
import pl.coderslab.driver.service.MediaContentService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/media")
@SecurityRequirement(name = "driver-api")
@RequiredArgsConstructor
public class MediaContentController {

  private final MediaContentService mediaContentService;

  @PostMapping(consumes = {
          "multipart/form-data"})
  public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
    String message = "";
    try {
      mediaContentService.save(file);

      message = "Uploaded the file successfully: " + file.getOriginalFilename();
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    } catch (Exception e) {
      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
    }
  }

  @GetMapping
  public ResponseEntity<List<ResponseFile>> getListFiles() {
    List<ResponseFile> files = mediaContentService.findAll().map(dbFile -> new ResponseFile(
            dbFile.getName(),
            dbFile.getType(),
            dbFile.getData().length)).collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK).body(files);
  }

  @GetMapping("/{mediaId}")
  public ResponseEntity<byte[]> getFile(@PathVariable Long mediaId) {
    MediaContent fileDB = mediaContentService.findById(mediaId);

    return ResponseEntity.ok()
            .contentType(MediaType.valueOf(fileDB.getType()))
            .body(fileDB.getData());
  }

  @DeleteMapping("/{mediaId}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteMedia(@PathVariable long mediaId) {
    mediaContentService.deleteById(mediaId);
  }

}
