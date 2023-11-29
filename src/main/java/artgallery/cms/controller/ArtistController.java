package artgallery.cms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import artgallery.cms.dto.ArtistDTO;
import artgallery.cms.exception.ArtistDoesNotExistException;
import artgallery.cms.service.ArtistService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/artists")
@RequiredArgsConstructor
public class ArtistController {
  private final ArtistService artistService;

  @GetMapping("/")
  public ResponseEntity<?> getAllArtists(@RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size) {
    Page<ArtistDTO> artistsPage = artistService.getAllArtists(page, size);
    List<ArtistDTO> artists = artistsPage.getContent();
    HttpHeaders headers = new HttpHeaders();
    headers.add("X-Total-Count", String.valueOf(artistsPage.getTotalElements()));
    return ResponseEntity.ok().headers(headers).body(artists);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getArtistById(@PathVariable("id") long id) throws ArtistDoesNotExistException {
    return ResponseEntity.ok().body(artistService.getArtistById(id));
  }

  @PostMapping
  @PreAuthorize("hasRole('MODERATOR')")
  public ResponseEntity<?> createArtist(@RequestBody ArtistDTO req) {
    return ResponseEntity.status(HttpStatus.CREATED).body(artistService.createArtist(req));
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('MODERATOR')")
  public ResponseEntity<?> updateArtist(@PathVariable("id") long id, @RequestBody ArtistDTO req)
      throws ArtistDoesNotExistException {
    artistService.updateArtist(id, req);
    return ResponseEntity.ok().body("ok");
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('MODERATOR')")
  public ResponseEntity<?> deleteArtist(@PathVariable("id") long id) {
    artistService.deleteArtist(id);
    return ResponseEntity.noContent().build();
  }

}