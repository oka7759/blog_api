package oka_tech.blog.api.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oka_tech.blog.api.dto.PostDto;
import oka_tech.blog.api.dto.SearchResp;
import oka_tech.blog.api.dto.SeriesDto;
import oka_tech.blog.api.dto.SeriesResp;
import oka_tech.blog.api.exception.CustomException;
import oka_tech.blog.api.exception.ErrorCode;
import oka_tech.blog.api.repository.PostJpaRepository;
import oka_tech.blog.api.repository.SeriesJpaRepository;
import oka_tech.blog.api.repository.SeriesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeriesService {
    private final SeriesRepository seriesRepository;
    private final PostJpaRepository postJpaRepository;
    private final SeriesJpaRepository seriesJpaRepository;

    public SeriesResp findSeriesDetail(Long seriesId) {
        return seriesRepository.findById(seriesId)
                .map(series -> {
                    List<PostDto> posts = postJpaRepository.findBySeriesId(seriesId);
                    return new SeriesResp(series, posts);
                })
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_PARAMETER));
    }

    public SearchResp findSearchByTitle(String title) {
        List<SeriesDto> series = seriesJpaRepository.findBySeriesByTitle(title);
        List<PostDto> posts = postJpaRepository.findPostByTitle(title);
        return new SearchResp(series, posts);
    }
}
