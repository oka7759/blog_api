package oka_tech.blog.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oka_tech.blog.api.dto.*;
import oka_tech.blog.api.entity.Series;
import oka_tech.blog.api.repository.PostJpaRepository;
import oka_tech.blog.api.repository.SeriesRepository;
import oka_tech.blog.api.repository.TagJpaRepository;
import oka_tech.blog.api.service.SeriesService;
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
@Slf4j
public class BlogController {

    private final PostJpaRepository postJpaRepository;
    private final SeriesRepository seriesRepository;
    private final SeriesService seriesService;
    private final TagJpaRepository tagJpaRepository;


    @GetMapping("/all")
    public Page<PostDto> postList(Pageable pageable) {
        log.info("pageable: {}", pageable);
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

    @GetMapping("/series/{seriesId}")
    private SeriesResp seriesDetail(@PathVariable(value = "seriesId") Long seriesId) {
        return seriesService.findSeriesDetail(seriesId);
    }

    @GetMapping("/tags")
    public List<TagsDto> tagList() {
        return tagJpaRepository.getTags();
    }

    @GetMapping("tags/{tags}")
    public List<PostDto> postList(@PathVariable(value = "tags") String tags) {
        return postJpaRepository.findPostByTagName(tags);
    }

    @GetMapping("search/{title}")
    public SearchResp search(@PathVariable(value = "title") String title) {
       return seriesService.findSearchByTitle(title);
    }
}
