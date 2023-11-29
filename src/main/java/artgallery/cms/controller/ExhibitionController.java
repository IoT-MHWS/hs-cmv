package artgallery.cms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import artgallery.cms.dto.ExhibitionDTO;
import artgallery.cms.exception.ExhibitionDoesNotExistException;
import artgallery.cms.exception.GalleryDoesNotExistException;
import artgallery.cms.service.ExhibitionService;

@RestController
@RequestMapping("/api/v1/exhibitions")
@RequiredArgsConstructor
public class ExhibitionController {
  private final ExhibitionService exhibitionService;

  @GetMapping("/")
  public ResponseEntity<?> getAllExhibitions(@RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size) {
    return ResponseEntity.ok().body(exhibitionService.getAllExhibitions(page, size));
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getExhibitionById(@PathVariable("id") long id) throws ExhibitionDoesNotExistException {
    return ResponseEntity.ok().body(exhibitionService.getExhibitionById(id));
  }

  @PostMapping
  public ResponseEntity<?> createExhibition(@RequestBody ExhibitionDTO req) throws GalleryDoesNotExistException {
    return ResponseEntity.status(HttpStatus.CREATED).body(exhibitionService.createExhibition(req));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateExhibition(@PathVariable("id") long id, @RequestBody ExhibitionDTO req)
      throws ExhibitionDoesNotExistException, GalleryDoesNotExistException {
    exhibitionService.updateExhibition(id, req);
    return ResponseEntity.ok().body("ok");
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteExhibition(@PathVariable("id") long id) {
    exhibitionService.deleteExhibition(id);
    return ResponseEntity.noContent().build();
  }

}
