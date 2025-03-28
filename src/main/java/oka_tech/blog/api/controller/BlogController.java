package oka_tech.blog.api.controller;

import lombok.RequiredArgsConstructor;
import oka_tech.blog.api.dto.PostDto;
import oka_tech.blog.api.dto.SeriesDto;
import oka_tech.blog.api.entity.Series;
import oka_tech.blog.api.repository.PostJpaRepository;
import oka_tech.blog.api.repository.SeriesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/blog")
public class BlogController {

    private final PostJpaRepository postJpaRepository;
    private final SeriesRepository seriesRepository;


    @GetMapping("/all")
    public Page<PostDto> postList(Pageable pageable) {
        return postJpaRepository.searchPage(pageable);
    }

    @GetMapping("/{id}")
    public PostDto postDetail(@PathVariable(value = "id") Long id) {
        return postJpaRepository.findId(id);
    }

    @GetMapping("/series")
    public List<SeriesDto> seriesList() {
        List<Series> series = seriesRepository.findAllByOrderByCreatedAtDesc();
        return series.stream().map(SeriesDto::new).toList();
    }
    @GetMapping("series/{seriesId}")
    private Optional<Series> seriesDetail(@PathVariable(value = "seriesId") Long seriesId) {
        return seriesRepository.findById(seriesId);
    }
}
